

import java.util.Scanner;

/**
 * @author Matthew Seiler
 * @version 30-09-2013
 * midatl-2010 Problem A - Palindrometer
 */
public class Palindrometer {

	public static void main(String[] args) {
		Scanner scin = new Scanner(System.in);
		while (true) {
			String odometer = scin.nextLine();
			int digits = odometer.length();
			if (odometer.equals("0")) break;
			int count = 0;
			while (!isPalindrome(odometer)) {
				count++;
				int temp = Integer.parseInt(odometer);
				temp++;
				odometer = String.format("%0" + digits + "d", temp);
			}
			System.out.println(count);
		}
		scin.close();
	}
	
	public static boolean isPalindrome(String str) {
        String reverse = new StringBuilder(str).reverse().toString();
        if (str.equals(reverse)) {
        	return true;
        }
        else {
            return false;
        }
	}

}
