package hscc2012;

import java.util.HashMap;
import java.util.Scanner;

/**
 * RU Local contest 2012 Problem 5 - "Alpha Bars"
 * @author Matthew Seiler
 * @version
 */
public class AlphaBars {

	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		int numCases = s.nextInt(); s.nextLine();
		for (int i = 0; i < numCases; i += 1) {
			solveOne(s);
		}
		s.close();
	}
	
	private static void solveOne(Scanner s) {
		StringBuilder input = new StringBuilder();
		boolean doneReading = false;
		while (!doneReading) {
			String line = s.nextLine();
			if (line.equals("EOT")) {
				doneReading = true;
			}
			else {
				input.append(line);
			}
		}
		HashMap<Character, Integer> counts = new HashMap<Character, Integer>();
		char[] inputChars = input.toString().toCharArray();
		for (int i = 0; i < inputChars.length; i += 1) {
			char c = inputChars[i];
			if (Character.isLetter(c)) {
				c = Character.toLowerCase(c);
				// increment the count
				if (counts.containsKey(c)) {
					counts.put(c, counts.get(c) + 1);
				}
				else {
					counts.put(c, 1);
				}
			}
		}
		// turn your map into a parallel array
		int[] countArr = new int[26];
		for (int i = 0; i < letters.length; i += 1) {
			char c = letters[i];
			countArr[i] = (counts.containsKey(c) ? counts.get(c) : 0);
		}
		printHistogram(countArr);
	}
	
	private static void printHistogram(int[] counts) {
		StringBuilder hs = new StringBuilder();
		// TODO: No idea how to do this part yet.
		System.out.print(hs.toString());
	}
	
	private static final char[] letters = {
		'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
		'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'
	};
	
	private static <T> void printArr(T[] arr) {
		System.out.printf("Array of %d %s = {%n", arr.length, arr.getClass().toString());
		for (T e : arr) {
			System.out.printf("%s,%n", e.toString());
		}
		System.out.println("}");
	}

}
