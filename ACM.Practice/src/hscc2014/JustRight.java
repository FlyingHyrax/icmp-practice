package hscc2014;

import java.util.Scanner;

/**
 * Radford University HSCC contest 2014
 * Problem A - "Just Right?"
 * @author Matthew Seiler
 */
public class JustRight {

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int numSets = scan.nextInt();	scan.nextLine();
		
		for (int i = 0; i < numSets; ++i) {
			solveOne(scan);
		}
		
		scan.close();
	}
	
	static void solveOne(Scanner s) {
		int target = s.nextInt();	s.nextLine();
		int setSize = s.nextInt();	s.nextLine();
		int[] nums = getSet(s, setSize);
		String answer = getSolution(target, nums);
		System.out.println(answer);
	}
	
	static int[] getSet(Scanner s, int setSize) {
		int[] nums = new int[setSize];
		for (int i = 0; i < setSize; ++i) {
			nums[i] = s.nextInt();
		}
		s.nextLine();
		return nums;
	}
	
	static String getSolution(int t, int[] nums) {
		int size = nums.length;
		int smaller = 0;
		int equal = 0;
		int larger = 0;
		
		for (int i = 0; i < size; i++) {
			int current = nums[i];
			if (current < t) {
				smaller++;
			}
			else if (current > t) {
				larger++;
			}
			else {
				equal++;
			}
		}
		
		return String.format("%d %d %d %d %d", t, size, smaller, equal, larger);
	}

}
