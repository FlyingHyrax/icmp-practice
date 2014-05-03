package hscc2012;

import java.util.Scanner;

/**
 * RU Local Competition 2012 Problem 1 - "RoboPainter"
 * @author Matthew Seiler
 * @version 2014-04-27
 */
public class RoboPainter {

	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		double n1 = s.nextDouble();
		double n2 = s.nextDouble();
		double r = (((n1 * 2) + (n2 * 2)) * 8) / 128;
		System.out.printf("%.2f", r);
		s.close();
	}

}
