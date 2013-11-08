import java.util.Arrays;
import java.util.Scanner;


public class CodeJamFinal {
	
	private String lookFor = "welcome to code jam";
	private String lookIn;
	private boolean dbg = false;
	
	public CodeJamFinal() {
		// no arg leaves defaults
	}
	
	public CodeJamFinal(String target, boolean debug) {
		this.lookFor = target;
		this.dbg = debug;
	}
	
	public void solve(int numCases, Scanner scan) {
		for (int i = 1; i <= numCases; i++) {
			this.lookIn = scan.nextLine();
			System.out.printf("Case #%d: %04d%n", i, count());
		}
	}
	
	public int count() {
		// initialize
		int[] currentRow = new int[lookFor.length() + 1];
		int[] prevRow = new int[lookFor.length() + 1];
		int[] t;
		prevRow[lookFor.length()] = 1;
		// for each letter in search space, reverse order
		for (int r = lookIn.length() - 1; r >= 0; r--) {
			// swap and initialize (moving up a row)
			t = currentRow;
			currentRow = prevRow;
			prevRow = t;
			prevRow[lookFor.length()] = 1;	// base case
			// for each letter in target space, reverse order
			for (int c = lookFor.length() - 1; c >= 0; c--) {
				// bubble up
				currentRow[c] = prevRow[c];
				// add if match
				if (lookFor.charAt(c) == lookIn.charAt(r)) {
					currentRow[c] += prevRow[c+1];
					// truncate to avoid overflow ?
					currentRow[c] = currentRow[c] % 10000;
				}
			}
			if (dbg) {
				System.err.println("Row#" + r + ":" + Arrays.toString(currentRow));
			}
		}
		return currentRow[0];
	}


	public static void main(String[] _) {
		Scanner scin = new Scanner(System.in);
		CodeJamFinal solver = new CodeJamFinal();
//		CodeJamFinal solver = new CodeJamFinal("welcome to code jam", true);
		int numCases = scin.nextInt();	scin.nextLine();
		solver.solve(numCases, scin);
	}

}
