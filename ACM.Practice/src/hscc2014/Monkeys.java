package hscc2014;

import java.util.Scanner;

/**
 * Radford University HSCC 2014
 * Problem C - "Monkeys and Coconuts"
 * @author Matthew Seiler
 */
public class Monkeys {

	public static void main(String[] args) {
		Scanner scin = new Scanner(System.in);
		
		int explorers = scin.nextInt();
		int coconuts = scin.nextInt();
		
		while ((explorers != 0) && (coconuts != 0)) {
			System.out.println(solve(explorers, coconuts));
			explorers = scin.nextInt();
			coconuts = scin.nextInt();
			scin.nextLine();
		}
		
		scin.close();
	}
	
	static int solve(int e, int c) {
		for (int i = 1; i <= e; ++i) {
			int share = c / e;	// recall that integer division floors remainders for us
			c = ( c - share - 1);
		}
		return c;
	}

}
