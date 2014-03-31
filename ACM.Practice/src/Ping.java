import java.util.*;
/**
 * MidAtl 2013 Problem E: "Ping!"
 * @author tlein, re-written by mseiler2
 * @version 04-11-2013
 */
public class Ping {
	
	public static void main(String[] _) {
		boolean done = false;
		Scanner scin = new Scanner(System.in);
		String line = "";
		while (!done) {
			line = scin.nextLine();
			if (line.length() < 2) {
				done = true;
			}
			else {
				solveCase(line);
			}
		}
		scin.close();
	}
	
	private static void solveCase(String line) {
		ArrayList<Integer> intervals = new ArrayList<Integer>();
		char expected = '0';
		for (int i = 0; i < line.length(); i++) {
			int numIntervals = 0;
			for (int j = 0; j < intervals.size(); j++) {
				if (i % intervals.get(j) == 0) {
					numIntervals++;
				}
			}
			if (numIntervals == 0 || numIntervals % 2 == 0) {
				expected = '0';
			}
			else {
				expected = '1';
			}
			if (line.charAt(i) != expected && i != 0) {
				intervals.add(i);
			}
		}
		printervals(intervals);
	}
	
	private static void printervals(ArrayList<Integer> intervals) {
		StringBuffer outbuff = new StringBuffer();
		for (int i = 0; i < intervals.size(); i++) {
			outbuff.append(intervals.get(i));
			if (i + 1 != intervals.size()) {
				outbuff.append(' ');
			}
		}
		System.out.println(outbuff.toString());
	}
}
