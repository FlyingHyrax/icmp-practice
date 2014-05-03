package hscc2012;

import java.util.Scanner;

/**
 * RU Local competition 2012 Problem 2 - "So Square"
 * @author Matthew Seiler
 * @version 2014-04-27
 */
public class SoSquare {

	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		boolean done = false;
		while (!done) {
			int n = s.nextInt();	s.nextLine();
			if (n == 0) {
				done = true;
			}
			else {
				printSquare(n);
			}
		}
		s.close();
	}
	
	private static void printSquare(int size) {
		StringBuilder b = new StringBuilder();
		for (int i = 1; i <= size; i += 1) {
			if (i == 1 || i == size) {
				b.append(makeTopRow(size));
			}
			else {
				b.append(makeInnerRow(size));
			}
		}
		b.append(stringTimes("-", size * 2 -1) + "\n");
		System.out.print(b.toString());
	}
	
	private static String makeTopRow(int size) {
		String r = "";
		for (int i = 0; i < size; i += 1) {
			r += ((i == size - 1) ? "*" : "* ");
		}
		return r + "\n";
	}
	
	private static String makeInnerRow(int size) {
		int spaces = ((size - 2) * 2 + 1);
		return ("*" + stringTimes(" ", spaces) + "*\n");
	}
	
	private static String stringTimes(String s, int x) {
		StringBuilder b = new StringBuilder();
		for (int i = 0; i < x; i += 1) {
			b.append(s);
		}
		return b.toString();
	}

}
