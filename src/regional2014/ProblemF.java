package regional2014;

import java.util.*;

/*
 * ICPC Regionals 2014 Problem F: "Tight Knight"
 * I think... I think I got it.  Maybe.
 */
public class ProblemF {

	public static final boolean LOUD = false;
	
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
			
			scin.nextLine();// eat trailing newline
			
			if (n != 0) {
				Set<Position> obstacles = Board.readObstacles(scin, c);
				Board b = new Board(n, m, i, j, k, l, obstacles);
				
				if (LOUD) {
					System.out.println(b.toString());
				}
				
				long start = System.currentTimeMillis();
				
				String result;
				if (b.startIsFinish()) {
					// Special case: if the board is 1x1, then it cannot be blocked
					result = "No";
				}
				else {
					// otherwise, do the search
					result = b.countPaths() > 1 ? "No" : "Yes";
				}
				System.out.println(result);
				
				System.err.printf("(%d ms)%n", System.currentTimeMillis() - start);
			}
			else {
				done = true;
			}
		}
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

/*
 * Represent one board configuration
 */
class Board {
	private int width, height;
	
	private Position origin, destination;
	
	private Set<Position> obstacles;
	
	private Set<Position> visited = new HashSet<Position>();
	
	// can construct from a scanner and obstacle count instead of a set of obstacle positions
	public Board(int width, int height, int originX, int originY, int goalX, int goalY, int obsCount, Scanner s) {
		this(width, height, originX, originY, goalX, goalY, Board.readObstacles(s, obsCount));
	}
	
	// construct the board w/ dimensions, start/end, and set of obstacle positions
	public Board(int width, int height, int originX, int originY, int goalX, int goalY, Set<Position> obstacles) {
		this.width = width;
		this.height = height;
		
		this.origin = new Position(originX, originY);
		this.destination = new Position(goalX, goalY);
		
		this.obstacles = obstacles;
	}
	
	/*
	 * Only keep two collections - the current possible positions of the Knight, and all the possible positions after that.
	 * When we try to generate the next list of possible positions and fail (empty list), then we have exhausted every possible
	 * path on the board.  For every iteration, we check how many paths from the previous iteration have now reached the 
	 * destination/goal, and increment the counter for each one.
	 * 
	 * Besides eliminating recursion to prevent stack overflows, the other major optimization is to remove duplicates from
	 * the current/seed collection by using a Set instead of a List.  This works because we aren't really counting unique paths -
	 * any given space can be reached from up to eight prior positions/paths, but if we were to place an obstacle at the space
	 * then all eight paths would be blocked.  So if N paths reach the destination but all go through the same space _at any point_,
	 * then they still only contribute 1.
	 */
	public int countPaths() {
		// how many times we have reached the goal from a unique position
		int count = 0;
		
		// seed generation - all valid, current positions of the knight
		Set<Position> current = new HashSet<>();
		current.add(this.origin);
		
		// possible next positions
		List<Position> next = getNextPositions(current);
		
		// if there are no more possible moves, then we're done
		// small optimization: if we reach count = 2, then there are at least 2 routes to the dest and we can stop looking
		while (!next.isEmpty() && count < 2) {
			// dbg
			if (ProblemF.LOUD) {
				ProblemF.printCollection(next);
			}
			
			// major optimization: remove duplicates as we are selecting seed positions for the next generation
			current = new HashSet<>();
			
			// for each possible move
			for (Position p : next) {
				if (p.equals(destination)) {
					// we reached the goal from a unique position
					count += 1;
				}
				else {
					// "visit" the point
					visited.add(p);
					// add the point to the seed set we'll use to generate the next list of moves
					current.add(p);
				}
			}
			
			// now that we have selected unique seed positions, generate the next list of possible moves
			next = getNextPositions(current);
		}
		
		return count;
	}
	
	/* Check for a special case: problem does not exclude, i == k && j == l,
	 * but does exclude an obstacle being placed at (i,j) or (k,l),
	 * so if origin == destination then the Kinght is unblockable
	 */
	public boolean startIsFinish() {
		return origin.equals(destination);
	}
	
	// given set of positions, returns all valid next positions
	private List<Position> getNextPositions(Set<Position> posl) {
		List<Position> next = new LinkedList<>();
		for (Position p : posl) {
			next.addAll(getNextPositions(p));
		}
		return next;
	}
	
	// given a position, returns all valid next positions
	private List<Position> getNextPositions(Position p) {
		List<Position> candidates = new LinkedList<>();
		
		candidates.add(new Position(p.x + 2, p.y + 1));
		candidates.add(new Position(p.x + 2, p.y - 1));
		candidates.add(new Position(p.x - 2, p.y + 1));
		candidates.add(new Position(p.x - 2, p.y - 1));
		candidates.add(new Position(p.x + 1, p.y + 2));
		candidates.add(new Position(p.x + 1, p.y - 2));
		candidates.add(new Position(p.x - 1, p.y + 2));
		candidates.add(new Position(p.x - 1, p.y - 2));
		
		return filterPositions(candidates);
	}
	
	// filters a list of possible positions to return a list of valid positions
	private List<Position> filterPositions(List<Position> posl) {
		List<Position> valid = new LinkedList<>();
		for (Position pos : posl) {
			if (isValid(pos)) {
				valid.add(pos);
			}
		}
		return valid;
	}
	
	// check if a position is in bounds, visited already, or an obstacle
	private boolean isValid(Position p) {
		return inBounds(p) && !obstacles.contains(p) && !visited.contains(p);
	}
	
	// check if a position is inside the bounds of the board
	private boolean inBounds(Position p) {
		return (p.x > 0 && p.y > 0 && p.x <= this.width && p.y <= this.height);
	}
	
	// given a scanner and number of obstacles to read, reads pairs of obstacles into a Set
	public static Set<Position> readObstacles(Scanner scin, int count) {
		Set<Position> obstacles = new HashSet<Position>();
		for (int i = 0; i < count; i += 1) {
			obstacles.add(new Position(scin.nextInt(), scin.nextInt()));
			scin.nextLine();
		}
		return obstacles;
	}
	
	// for debuggin'
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < width; i += 1) {
			for (int j = 0; j < height; j += 1) {
				Position currentPos = new Position(i + 1, j + 1);
				if (currentPos.equals(this.origin)) {
					sb.append('S');
				}
				else if (currentPos.equals(this.destination)) {
					sb.append('F');
				}
				else if (obstacles.contains(currentPos)) {
					sb.append('X');
				}
				else {
					sb.append('_');
				}
			}
			sb.append('\n');
		}
		return sb.toString();
	}
}

// It's like a pair... but it isn't.
class Position {
	int x, y;
	
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
		return "(x=" + x + ",y=" + y + ")";
	}
	
}