import java.util.Scanner;

public class CodeJamHard {
	private String lookFor = "welcome to code jam";
	private String lookIn = "";
	private Integer[][] memo;
	private boolean debug = false;;
	
	public CodeJamHard() {
		
	}
	
	public CodeJamHard(String str, boolean dbg) {
		this.lookFor = str;
		this.debug = dbg;
	}
	
	/**
	 * Reads a number of lines and solves each per problem spec
	 * Switchable w/ character arg 'mode'
	 * @param numCases - number of lines to read
	 * @param scan - scanner to scan lines from
	 * @param mode - character A, B, or C for different algorithms
	 */
	public void solveCases(int numCases, Scanner scan, char mode) {
		for (int i = 1; i <= numCases; i++) {
			this.lookIn = scan.nextLine();
			long ans;
			switch (mode) {
			case 'A':
				ans = countA(this.lookIn, this.lookFor);
				break;
			case 'B':
				memo = new Integer[lookIn.length()+1][lookFor.length()+1];
				ans = countB(0, 0);
				break;
			case 'C':
				ans = countC();
				break;
			default:
				ans = -1;
				break;
			}
			ans = ans % 10000;
			System.out.printf("Case #%d: %04d%n", i, ans);
			if (mode == 'B' && debug) {
				printMemoTable();
			}	
		}
	}
	
	/**
	 * bottom-up dp algo searches "lookIn" string for combinations of "lookFor" string.
	 * Built by running countB() w/ printMemoTable() (mode 'B') and analyzing output.
	 * Appears to be correct for small numbers, but Kattis complains?
	 * @return the count
	 */
	public long countC() {
		// initialize
		long[] currentRow = new long[lookFor.length() + 1];
		long[] prevRow = new long[lookFor.length() + 1];
		prevRow[lookFor.length()] = 1;
		// for each letter in search space, reverse order
		for (int r = lookIn.length() - 1; r >= 0; r--) {
			// for each letter in target space, plus the mythic "first column" (seed), reverse order
			for (int c = lookFor.length(); c >= 0; c--) {
				// bubble up
				currentRow[c] = prevRow[c];
				// add if match, don't check the mythic column b/c there won't be a char there
				if (c != lookFor.length() && lookFor.charAt(c) == lookIn.charAt(r)) {
					currentRow[c] += prevRow[c+1];
					// truncate to avoid overflow ?
					currentRow[c] = currentRow[c] % 10000;
				}
				
			}
			// move up; point prevRow at currentRow and make a new currentRow
			prevRow = currentRow;
			currentRow = new long[lookFor.length() + 1];
		}
		// previous row, b/c on last iteration currentRow is still refreshed
		return prevRow[0];
	}
	
	/**
	 * Improved recursive version uses integer indices on 'constant' strings
	 * and memo-izes results to an array.
	 * 
	 * Be sure to set class field "lookIn" to a source string before calling this!
	 * @param srcIndex
	 * @param tarIndex
	 * @return
	 */
	public int countB(int srcIndex, int tarIndex) {
		// check memopad
		if (memo[srcIndex][tarIndex] != null) {
			return memo[srcIndex][tarIndex];
		}
		else {
			int c = 0;
			// we ran through the target string - must've found the whole thing. +1
			if (tarIndex == lookFor.length()) {
				c = 1;
			}
			// we ran through the whole source string - must've not found the target. +0
			else if (srcIndex == lookIn.length()) {
				c = 0;
			}
			// not done yet; recur:
			else {
				// if the current chars match, iterate up one for both strings
				if (lookIn.charAt(srcIndex) == lookFor.charAt(tarIndex)) {
					c += countB(srcIndex + 1, tarIndex + 1);
				}
				// in any case, try to match the current target char on the next char in the source
				c += countB(srcIndex + 1, tarIndex);
			}
			// truncate, memo-ize, and return
			c = c % 10000;
			memo[srcIndex][tarIndex] = c;
			return c;
		}
	}
	
	/**
	 * Prints out a relatively pretty table showing the memopad contents at time of call.
	 * Also possibly huge, so output redirection recommended
	 */
	public void printMemoTable() {
		StringBuilder header = new StringBuilder("   ");
		for (int i = 0; i < lookFor.length(); i++) {
			header.append(lookFor.charAt(i));
			header.append("    ");
		}
		System.out.print(header.toString() + "\n");
		
		StringBuilder body = new StringBuilder();
		for (int i = 0; i < memo.length; i++) {
			if (i < memo.length-1) {
				body.append(lookIn.charAt(i));
				body.append(" ");
			}
			else {
				body.append("  ");
			}
			for (int j = 0; j < memo[i].length; j++) {
				body.append(String.format(" %04d", memo[i][j]));
			}
			body.append("\n");
		}
		System.out.print(body.toString());
	}
	
	/**
	 * Original recursive counter
	 * @param source - string to look in
	 * @param target - string to look for in source
	 * @return - integer count of occurrences
	 */
	public long  countA(String source, String target) {
		if (target.length() == 0) {
			return 1;
		}
		else if (source.length() == 0) {
			return 0;
		}
		else {
			long t = 0;
			if (source.charAt(0) == target.charAt(0)) {
				t += countA(source.substring(1), target.substring(1));
			}
			t += countA(source.substring(1), target);
			return t;
		}
	}
	
	
	public static void main(String[] _) {
		Scanner scin = new Scanner(System.in);
		int numCases = scin.nextInt();	scin.nextLine();
		CodeJamHard meat = new CodeJamHard("welcome to code jam", true);
		meat.solveCases(numCases, scin, 'B');
	}
}