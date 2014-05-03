package hscc2014;

import java.util.Scanner;

/**
 * Radford University HSCC 2014
 * Problem D - "Hidden in Plain Sight"
 * @author Matthew Seiler
 */
public class Hidden {

	public static void main(String[] args) {
		Scanner scin = new Scanner(System.in);
		
		// initialization for loop-and-a-half
		String lookFor = scin.nextLine();
		String lookIn = "";
		
		while (!lookFor.equals("#")) {
			lookIn = scin.nextLine();	// for current iteration
			
			int result = search(lookFor, lookIn);
			if (result == -1) {
				System.out.println(String.format("[%s] is not found.", lookFor));
			}
			else {
				System.out.println(String.format("[%s] is found with an encoding of %d.", lookFor, result));
			}
			
			lookFor = scin.nextLine();	// for next iteration
		}
		
		scin.close();
	}
	
	static int search(String lookFor, String lookIn) {
		for (int i = 1; i <= lookIn.length(); ++i) {
			if (intervals(lookIn, i).equals(lookFor)) {
				return i;
			}
		}
		return -1;
	}
	
	static String intervals(String str, int n) {
		StringBuilder sb = new StringBuilder("");
		for (int i = n - 1; i < str.length(); i += n) {
			sb.append(str.charAt(i));
		}
		return sb.toString();
	}

}
