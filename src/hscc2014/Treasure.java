package hscc2014;

import java.util.Scanner;

/**
 * Radford University HSCC 2014
 * Problem E - "Treasure Everywhere"
 * @author Matthew Seiler
 */
public class Treasure {

	public static void main(String[] args) {
		Scanner scin = new Scanner(System.in);
		
		int count = 1;
		String line = scin.nextLine();
		
		while(!line.equals("END")) {
			solve(line, count);
			
			line = scin.nextLine();
			count++;
		}
		
		scin.close();
	}
	
	static void solve(String input, int caseNumber) {
		String[] steps = getSteps(input);
		
		int x = 0;
		int y = 0;
		
		for (String s : steps) {
			int slen = s.length();
			char dir = s.charAt(slen - 1);
			int dist = Integer.parseInt(s.substring(0, slen - 2));
			
			switch (dir) {
			case 'N': 
				y += dist;
				break;
			case 'S':
				y -= dist;
				break;
			case 'E':
				x += dist;
				break;
			case 'W':
				x -= dist;
				break;
			}
			
			double distance = Math.sqrt( Math.pow(Math.abs(x), 2) + Math.pow(Math.abs(y), 2) );
			
			System.out.println(String.format("Map #%d", caseNumber));
			System.out.println(String.format("The treasure is located at (%d, %d).", x, y));
			System.out.println(String.format("The distance to the treasure is %.3f.", distance));
			
		}
	}
	
	static String[] getSteps(String input) {
		String[] steps = input.split(", ");
		// in case there was only one step
		if (steps.length == 0) {
			steps = new String[1];
			steps[0] = input;
		}
		// strip off the trailing period
		int last = steps.length - 1;
		steps[last] = steps[last].substring(0, steps[last].length() - 1);
		
		return steps;
	}

}
