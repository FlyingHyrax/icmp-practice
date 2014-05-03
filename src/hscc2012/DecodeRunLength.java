package hscc2012;

import java.util.Scanner;

/** 
 * RU Local contest problem 4 - "Decode Run Length"
 * @author Matthew Seiler
 * @version 2014-04-27
 */
public class DecodeRunLength {

	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		boolean done = false;
		while (!done) {
			String line = s.nextLine();
			if (line.equals("000")) {
				done = true;
			}
			else {
				printResult(decode(line));
			}
		}
		s.close();
	}
	
	private static void printResult(String s) {
		if (s.length() > 40) {
			System.out.println(s.substring(0, 40) + "-");
			printResult(s.substring(40));
		}
		else {
			System.out.println(s);
		}
	}
	
	private static String decode(String l) {
		StringBuilder res = new StringBuilder();
		char[] arr = l.toCharArray();
		int acc = 0;
		boolean prevWasDigit = false;
		for (int i = 0; i < arr.length; i += 1) {
			// current is a digit
			if (Character.isDigit(arr[i])) {
				int v = arr[i] - 48;
				// previous was a digit
				if (prevWasDigit) {
					acc = acc * 10 + v;
				}
				// previous was not a digit
				else {
					acc = v;
				}
				// set for the next index
				prevWasDigit = true;
			}
			// current is a letter
			else {
				// previous was a digit
				if (prevWasDigit) {
					res.append(charTimes(arr[i], acc));
				}
				// previous was not a digit
				else {
					res.append(arr[i]);
				}
				// set for the next index
				prevWasDigit = false;
				acc = 0;
			}
		}
		return res.toString();
	}
	
	private static String charTimes(char c, int x) {
		StringBuilder b = new StringBuilder();
		for (int i = 0; i < x; i += 1) {
			b.append(c);
		}
		return b.toString();
	}

}
