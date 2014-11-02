package icpc2014;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * MidAtl 2013 Problem A: "Text Roll"
 * @author hyrax
 * @version 04-11-2013
 */
public class TextRoll {

	public static void main(String[] _) {
		Scanner scin = new Scanner(System.in);
		int numLines = scin.nextInt(); 
		while (numLines != 0) {
			scin.nextLine();	// eat trailing newline from nextInt()
			solveCase(numLines, scin);
			numLines = scin.nextInt();
		}
	}
	
	private static void solveCase(int numLines, Scanner scan) {
		ArrayList<String> lines = new ArrayList<String>();
		for (int i = 0; i < numLines; i++) {
			lines.add(scan.nextLine());
		}
//		System.err.println("lines: " + lines.size());
		int dropCol = 0;
		for (String line : lines) {
			if (line.length() > dropCol) {
				int findSpace = line.indexOf(' ', dropCol);
				if (findSpace == -1) {
					dropCol = line.length();
				}
				else if (findSpace > dropCol) {
					dropCol = findSpace;
				}
			}
		}
		System.out.println(dropCol + 1);
	} 

}
