package regional2014;

import java.util.*;

/**
 * ICPC MidAtl Regional  2014 
 * Problem F: "Tight Knight"
 * 
 * For each case:
 * <ol>
 * <li>Given the input obstacles, try to find a path from start to end.</li>
 * <li>If a path exists<ol>
 * 		<li>Add all the stops in the path to the list of obstacles, and search again</li>
 * 		<li>If we find another path, then we have found two *completely unique* paths</li></ol>
 * </li>
 * <li>If we can't find even a single path, then answer is "yes" b/c the knight is already blocked</li>
 * </ol>
 * 
 * Completes the largest test case I can think of in ~15 seconds, which is fast enough unless there
 * were several very large cases all together.
 */
public class ProblemF {
	
	public static void main(String[] args) {
		Scanner scin = new Scanner(System.in);
		
		boolean done = false;
		while (!done) {
			int n, m, i, j, k, l, c;
			
			n = scin.nextInt();
			m = scin.nextInt();
			
			i = scin.nextInt();
			j = scin.nextInt();
			
			k = scin.nextInt();
			l = scin.nextInt();
			
			c = scin.nextInt();
			
			scin.nextLine(); // eat trailing newline
			
			if (n != 0) {
				Board b = new Board(n, m, i, j, k, l, c, scin);
				System.out.printf("%s%n", b.canBlock() ? "Yes" : "No");
			}
			else {
				done = true;
			}
		}
		
		scin.close();
	}
	
	// utility method to print collection contents
	public static <T> void printCollection(Collection<T> things) {
		System.out.print("[ ");
		for (T thing : things) {
			System.out.print(thing.toString() + ", ");
		}
		System.out.println("]");
	}
	
}

/**
 * Represents the board - start position, goal position, boundaries, and set of obstacles.
 * Note that the lowest valid coordinate is 1, not 0!
 */
class Board {
	/* Initial obstacles read from standard input */
	private Set<Position> obstacles = new HashSet<>();
	
	/* Upper bounds, start coordinates, goal coordinates */
	private int max_x, max_y, start_x, start_y, end_x, end_y;
	
	/* Create a board.  Will try to read `c` obstacles from the specified `input` scanner */
	public Board(int n, int m, int i, int j, int k, int l, int c, Scanner input) {
		max_x = n;
		max_y = m;
		start_x = i;
		start_y = j;
		end_x = k;
		end_y = l;
		
		obstacles = new HashSet<Position>(c);
		for (int o = 0; o < c; o += 1) {
			obstacles.add(Position.read(input));
		}
	}
	
	/* Check if the knight can be prevented from reaching the end position from the start position
	 * by adding a single additional obstacle.  Note that if the knight can't reach the end position
	 * regardless, this returns true b/c the knight is already blocked. */
	public boolean canBlock() {
		List<Position> path = getPath();
		
		if (path != null) {
			// a path exists, so check if it is blockable by re-searching for a path
			// after adding all the cells in the first path to the obstacle set
			Set<Position> moreObstacles = new HashSet<>(path);
			// if we can no longer find a path, then the original can be blocked by a single additional obstacle?
			return getPath(moreObstacles) == null;
		}
		else {
			// we cannot reach the finish location at all, therefore we can block the knight by adding
			// "at most one additional obstacle" b/c the path is already blocked...
			return true;
		}
	}
	
	/**
	 * Find a path from between this board's start and finish locations, considering the board's obstacles.
	 * In particular this path will be in reverse order, but we don't much care about the order,
	 * only that a path exists and what its connecting nodes are - so this could really return a set.
	 * @return A list of all positions on a path from the start position to the end position in an unspecified order
	 */
	private List<Position> getPath() {
		return this.getPath(new HashSet<>(0));
	}
	
	/**
	 * Find a path from between this board's start and finish locations,
	 * considering the board's obstacles AND a set of additional obstacles.
	 * @param extraObstacles - additional obstacles to consider along with those already on the board
	 * @return A list of all positions on a path from the start position to the end position in an unspecified order
	 */
	private List<Position> getPath(Set<Position> extraObstacles) {
		// all obstacles to consider when looking for a path
		Set<Position> theObstacles = new HashSet<>(this.obstacles);
		theObstacles.addAll(extraObstacles);
		
		// whenever we discover a position that the knight can reach, add it to this set
		// so that we never add the same location to the search queue twice
		Set<Node> discovered = new HashSet<>();
		
		// this is the search queue for implementing BFS
		Deque<Node> searchQueue = new LinkedList<>();
		
		// add the start node to the search queue (it has no parent)
		searchQueue.add(new Node(start_x, start_y, null));
		
		while (!searchQueue.isEmpty()) {
			// grab the first node in the queue
			Node current = searchQueue.remove();
			// if this node is the goal position, then stop searching and create the path list
			if (current.x == end_x && current.y == end_y) {
				return followPath(current);
			}
			// get a list of that node's children, mark them as found and add them to the queue
			List<Node> newPlaces = getNextNodes(current, discovered, theObstacles);
			discovered.addAll(newPlaces);
			searchQueue.addAll(newPlaces);
		}
		
		// if we didn't find the goal position in the search loop, then there is no path from start to finish
		return null;
	}
	
	/* Each stop on the path keeps a pointer to its parent.  This follows those pointers backward
	 * from the destination to create a list of the nodes in the path. */
	private List<Position> followPath(Node finishNode) {
		List<Position> inPath = new LinkedList<>();
		
		// if the start and finish are the same, then we want to return an empty list
		if (finishNode.getParent() != null) {
			// don't add the end point to the path list
			Node cursor = finishNode.getParent();
			// "if there is a node before this one, add this one" so we won't add the start node
			while (cursor.getParent() != null) {
				inPath.add(cursor);
				cursor = cursor.getParent();
			}
		}
		
		return inPath;
	}
	
	/* used to check whether or not to add a node to the queue of places to visit.
	 * A node should be visited if it is in-bounds, not an obstacle, and not added/discovered yet.
	 */
	private boolean isValid(Position p, Set<? extends Position> discovered, Set<? extends Position> obstacles) {
		return isInBounds(p) && (!discovered.contains(p)) && (!obstacles.contains(p));
	}
	
	/* Check if a position is in bounds */
	private boolean isInBounds(Position p) {
		return p.x >= 1 && p.y >= 1 && p.x <= this.max_x && p.y <= this.max_y;
	}
	
	/* Find the list of next moves the knight could make from a particular start position */
	private List<Node> getNextNodes(Node current, Set<Node> discovered, Set<Position> obstacles) {
		List<Node> nexts = new LinkedList<>();
		
		int ex = current.x;
		int wy = current.y;
		
		// knightly moves from the current location
		// there is likely some clever way to create these in a loop...
		Node a = new Node(ex + 1, wy + 2, current);
		Node b = new Node(ex + 2, wy + 1, current);
		Node c = new Node(ex + 2, wy - 1, current);
		Node d = new Node(ex + 1, wy - 2, current);
		Node e = new Node(ex - 1, wy - 2, current);
		Node f = new Node(ex - 2, wy - 1, current);
		Node g = new Node(ex - 2, wy + 1, current);
		Node h = new Node(ex - 1, wy + 2, current);
		
		// only add valid destinations to the list of children
		if (isValid(a, discovered, obstacles)) nexts.add(a);
		if (isValid(b, discovered, obstacles)) nexts.add(b);
		if (isValid(c, discovered, obstacles)) nexts.add(c);
		if (isValid(d, discovered, obstacles)) nexts.add(d);
		if (isValid(e, discovered, obstacles)) nexts.add(e);
		if (isValid(f, discovered, obstacles)) nexts.add(f);
		if (isValid(g, discovered, obstacles)) nexts.add(g);
		if (isValid(h, discovered, obstacles)) nexts.add(h);
		
		return nexts;
	}
	
}

/* Represents a spot on the board using its X and Y coordinates */
class Position {
    final int x, y;
	
	public Position(int _x, int _y) {
		this.x = _x;
		this.y = _y;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Position))
			return false;
		Position other = (Position) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return String.format("(%d, %d)", x, y);
	}
	
	public static Position read(Scanner input) {
		int x = input.nextInt();
		int y = input.nextInt();
		input.nextLine();
		return new Position(x, y);
	}
	
}

/* When searching the board for paths, we need the places we search to keep a pointer back to 
 * the place before (parent node) so that we can follow the pointers back and return a list 
 * of positions in the path. So, these Positions also keep that pointer. */
class Node extends Position {
	
	private Node parent;
	
	public Node(int x, int y, Node parent) {
		super(x, y);
		this.parent = parent;
	}
	
	public Node(Position p, Node parent) {
		this(p.x, p.y, parent);
	}
	
	public Node getParent() {
		return this.parent;
	}
}