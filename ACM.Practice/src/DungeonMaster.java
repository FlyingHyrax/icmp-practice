import java.util.*;

public class DungeonMaster {
	static final boolean debug = false;

	public static void main(String[] args) {
		boolean done = false;
		int l;
		int r;
		int c;
		Scanner scin = new Scanner(System.in);
		while (!done) {
			l = scin.nextInt();
			r = scin.nextInt();
			c = scin.nextInt();
			scin.nextLine();	// eats newline
			if (l == 0) {
				done = true;
				break;
			}
			else {
				solveOneDungeon(l, r, c, scin);
			}
		}
		scin.close();

	}
	
	private static void solveOneDungeon(int lev, int row, int col, Scanner scan) {
		char[][][] mazeChars = readMaze(lev, row, col, scan);
		// find start and end
		Node start = null;
		Node exit = null;
		for (int i = 0; i < lev; i++) {
			for (int j = 0; j < row; j++) {
				for (int k = 0; k < col; k++) {
					char current = mazeChars[i][j][k];
					if (current == 'S') {
						start = new Node(i, j, k); 
					}
					else if (current == 'E') {
						exit = new Node(i, j, k);
					}
				}
			}
		}
		// search the maze
		Map<Node, Integer> distances = search(start, mazeChars);
		// results
		if (distances.containsKey(exit)) {
			System.out.println("Escaped in " + distances.get(exit) + " minute(s).");
		}
		else {
			System.out.println("Trapped!");
		}
	}
	
	private static Map<Node, Integer> search(Node start, char[][][] maze) {
		Queue<Node> toVisit = new LinkedList<Node>();
		Map<Node, Integer> distances = new HashMap<Node, Integer>();
		toVisit.add(start);
		distances.put(start, 0);
		while (!toVisit.isEmpty()) {
			Node current = toVisit.remove();
			for (Node peer : getPeers(current, maze)) {
				if (!distances.containsKey(peer)) {	// causes infinite loop of increasing toVisit
					toVisit.add(peer);
					distances.put(peer, (distances.get(current) + 1));
				}
			}
			if (debug) {
				System.err.println("toVisit contains " + toVisit.size() + " items");
			}
		}
		
		return distances;
	}
	
	private static Set<Node> getPeers(Node cur, char[][][] maze) {
		Set<Node> peers = new HashSet<Node>();
		// current cords:
		int l = cur.lev;
		int r = cur.row;
		int c = cur.col;
		if (safeGet(maze, l+1, r, c) != '#') {
			peers.add(new Node(l+1, r, c));
		}
		if (safeGet(maze, l-1, r, c) != '#') {
			peers.add(new Node(l-1, r, c));
		}
		if (safeGet(maze, l, r+1, c) != '#') {
			peers.add(new Node(l, r+1, c));
		}
		if (safeGet(maze, l, r-1, c) != '#') {
			peers.add(new Node(l, r-1, c));
		}
		if (safeGet(maze, l, r, c+1) != '#') {
			peers.add(new Node(l, r, c+1));
		}
		if (safeGet(maze, l, r, c-1) != '#') {
			peers.add(new Node(l, r, c-1));
		}
		return peers;
	}
	
	private static char safeGet(char[][][] arr, int lev, int row, int col) {
		boolean inBounds = true;
		if (0 > lev || lev >= arr.length) {
			inBounds = false;
		}
		else if (0 > row || row >= arr[lev].length) {
			inBounds = false;
		}
		else if (0 > col || col >= arr[lev][row].length) {
			inBounds = false;
		}
		return (inBounds ? arr[lev][row][col] : '#');
	}
	
	private static char[][][] readMaze(int lev, int row, int col, Scanner scan) {
		char[][][] chars = new char[lev][row][col];
		for (int i = 0; i < lev; i++) {
			for (int j = 0; j < row; j++) {
				chars[i][j] = scan.nextLine().toCharArray();
			}
			scan.nextLine();	// eat empty line
		}
		return chars;
	}

}

class Node {
	int lev;
	int row;
	int col;
	
	public Node(int l, int r, int c) {
		this.lev = l;
		this.row = r;
		this.col = c;
	}
	
	public boolean equals(Object other) {
		if (other == null) {	// other is null reference
			return false;
		}
		else if (this.getClass() != other.getClass()) {	// compare class types
			return false;
		}
		else if (this == other) {	// compare addresses/references
			return true;
		}
		else {	// compare values
			Node otherNode = (Node) other;
			return (this.lev == otherNode.lev &&
					this.row == otherNode.row &&
					this.col == otherNode.col);
		}
	}
	
	// ??? 
	public int hashCode() {
		return ((lev * 31 + row) * 31 + col);
	}
}
