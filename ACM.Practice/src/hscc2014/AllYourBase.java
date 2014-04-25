package hscc2014;
import java.util.*;

/** Radford University HSCC 2014
 * Problem F - "All Your Base"
 * @author Matthew Seiler
 * @version 2014-04-25
 */
public class AllYourBase {
	
	/**
	 * Driver, reads input and controls execution
	 * @param _ no args
	 */
	public static void main(String[] _) {
		Scanner scan = new Scanner(System.in);
		boolean done = false;
		while (!done) {
			String s1 = scan.nextLine();
			String s2 = scan.nextLine();
			String s3 = scan.nextLine();
			if (s1.equals("0") && s2.equals("0") && s3.equals("0")) {
				done = true;
			}
			else {
				solveOne(s1, s2, s3);
			}
		}
		scan.close();
	}
	
	/**
	 * Solves one case for the problem and prints result
	 * @param s1 first number
	 * @param s2 second number
	 * @param s3 third number
	 */
	static void solveOne(String s1, String s2, String s3) {
		int low = lowestBase(s1, s2, s3);
		
		for (int b = low; b <= 62; ++b) {
			if (checkBase(s1, s2, s3, b)) {
				System.out.printf("%s + %s = %s in base %d%n", s1, s2, s3, b);
				return;
			}
		}
		System.out.printf("%s + %s != %s%n", s1, s2, s3);
	}
	
	/**
	 * Checks if the sum of two numbers is equal to a third number in some base
	 * @param s1 first number
	 * @param s2 second number
	 * @param s3 third number
	 * @param base numeric base of the numbers
	 * @return boolean s1 + s2 == s3
	 * @precondition base >= lowestBase(s1, s2, s3)
	 */
	static boolean checkBase(String s1, String s2, String s3, int base) {
		String s1s2 = addTwoNums(s1, s2, base);
		return s3.equals(s1s2);
	}
	
	/**
	 * Add two numbers in some base 2..62
	 * @param n1 the first number
	 * @param n2 the second number
	 * @param base the base to assume the numbers are in
	 * @return String representing the sum of n1 and n2 in base 'base'
	 * @precondition base >= max(lowestBase(n1), lowestBase(n2))
	 */
	static String addTwoNums(String n1, String n2, int base) {
		StringBuilder result = new StringBuilder();
		Deque<Character> n1Stack = stringStack(n1);
		Deque<Character> n2Stack = stringStack(n2);
		char n1c, n2c, carry = '0';
		String temp = "";
		
		do {
			n1c = (n1Stack.peek() == null ? '0' : n1Stack.pop());
			n2c = (n2Stack.peek() == null ? '0' : n2Stack.pop());
			
			temp = addChars(base, n1c, n2c, carry);
			
			if (temp.length() == 1) {
				result.insert(0, temp.charAt(0));
				carry = '0';
			}
			else if (temp.length() == 2) {
				result.insert(0, temp.charAt(1));
				carry = temp.charAt(0);
			}

		} while (n1Stack.isEmpty() == false || n2Stack.isEmpty() == false || carry != '0');
		
		return result.toString();
	}

	/**
	 * Makes a stack of characters out of a string
	 * @param s the string to make into a stack
	 * @return Deque<Character> from String s
	 */
	static Deque<Character> stringStack(String s) {
		Deque<Character> st = new LinkedList<Character>();
		for (int i = 0; i < s.length(); ++i) {
			st.push(s.charAt(i));
		}
		return st;
	}
	
	/**
	 * Add any (reasonable) number of individual characters in some base by converting them to b10,
	 * keeping a running sum, then converting the sum back to the input base.  e.g., works great for 
	 * adding, say, two digits and a carry from larger numbers
	 * @param base the base of the inputs and outputs
	 * @param cs variable input of characters
	 * @return String representing the sum
	 * @precondition base >= max(lowestBase(c) for c in cs)
	 */
	static String addChars(int base, char... cs) {
		int b10res = 0;
		for (int i = 0; i < cs.length; ++i) {
			b10res += find(cs[i]);
		}
		return tenToBase(b10res, base);
	}
	
	/**
	 * Converts a decimal integer into a another base
	 * @param num input in base 10
	 * @param base the base of the output
	 * @return String of num in base 
	 */
	static String tenToBase(int num, int base) {
		if (num == 0) {
			return "0";
		}
		StringBuilder result = new StringBuilder();
		int decimal = num;
		while (decimal > 0) {
			result.insert(0, chars[decimal % base]);
			decimal /= base;
		}
		return result.toString();
	}
	
	/**
	 * Given three strings which represent numbers in some base,
	 * what is the lowest base those numbers could be in?
	 * @param s1 alphanumeric string
	 * @param s2 alphanumeric string
	 * @param s3 alphanumeric string
	 * @return lowest base
	 */
	static int lowestBase(String s1, String s2, String s3) {
		int low1 = lowestBase(s1);
		int low2 = lowestBase(s2);
		int low3 = lowestBase(s3);
		return Math.max(Math.max(low1, low2), low3);
	}
	
	/**
	 * Given a string and assuming it represents a number in some base,
	 * what is the smallest base the number could be in given the
	 * characters which it contains?  e.g., a string containing 'a'
	 * represent a base less than 10
	 * @param s an alphanumeric string
	 * @return the lowest possible base of the string, as described above
	 */
	static int lowestBase(String s) {
		int max = 2;
		for (int i = 0; i < s.length(); ++i) {
			int n = find(s.charAt(i));
			if (n > max) {
				max = n;
			}
		}
		return max;
	}
	
	
	/** Find character in our array of characters.
	 * Has the pleasant side effect of converting any individual character to base 10
	 * @param c character to find
	 * @return index of c in chars[] or -1 if not found
	 */
	static int find(char c) {
		for (int i = 0; i < chars.length; ++i) {
			if (chars[i] == c) {
				return i;
			}
		}
		return -1;
	}
	/** array of symbols ordered by their value in base 10 */
	static char[] chars = {	'0', '1', '2', '3', '4', '5', '6', 
		'7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 
		'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 
		't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 
		'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 
		'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

}
