package hscc2012;

import java.util.Scanner;

/**
 * RU Local contest 2012 Problem 6 - "Temperature Swing"
 * @author Matthew Seiler
 * @version 2014-04-28
 */
public class TempSwing {

	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		int numCases = s.nextInt(); s.nextLine();
		
		for (int i = 0; i < numCases; i += 1) {
			s.nextLine();	// consume blank line at start of case
			int numTemps = s.nextInt(); s.nextLine();
			System.out.println(solveOne(numTemps, s));
		}
		s.close();
	}
	
	private static int solveOne(int numTemps, Scanner s) {
		int maxAbsDiff = 0,
			maxActDiff = 0,
			prev = s.nextInt(); s.nextLine();
		for (int i = 1; i < numTemps; i += 1) {
			int current = s.nextInt(); s.nextLine();
			int actDiff = current - prev;
			int absDiff = Math.abs(actDiff);
			if (absDiff > maxAbsDiff) {
				maxAbsDiff = absDiff;
				maxActDiff = actDiff;
			}
			prev = current;
		}
		return maxActDiff;
	}

}
