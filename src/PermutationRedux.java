import java.util.Scanner;

/**
 * MidAtl 2006 Prob A - "Permutation Recovery"
 * Version two doesn't even remotely look like bubble sort,
 * and in consequence runs somewhat more quickly...
 * @author Matthew Seiler
 * @version 2013-10-6
 */
public class PermutationRedux {

	public static void main(String[] args) {
		boolean done = false;
		Scanner scin = new Scanner(System.in);
		while (!done) {
			int n = scin.nextInt();
			if (n == 0) {
				done = true;
			}
			else {
				Permutation p = new Permutation(n, scin);
				p.sortSelf();
				printResult(p.getPermutation());
			}
		}
		scin.close();
	}
	
	/**
	 * Prints an array of integers separated by commas, one array per line
	 * @param perm - array of ints
	 */
	private static void printResult(int[] perm) {
		for (int i = 0; i < perm.length; i++) {
			System.out.print(perm[i]);
			if (i != perm.length-1) {
				System.out.print(",");
			}
		}
		System.out.println();
	}

}

class Permutation {
	private int[] a_i;
	private int[] perm;	// should also try a version with LinkedList
	
	/**
	 * Constructor reads values for a_1 - a_i, 
	 * and constructs int array for initial state of permutation
	 * @param n - number of elements for a_i (length of permutation, largest value)
	 * @param scin - scanner to use to read a_i values
	 */
	public Permutation(int n, Scanner scin) {
		a_i = new int[n];
		perm = new int[n];
		for (int i = 0; i < n; i++) {
			a_i[i] = scin.nextInt();
			perm[i] = (n-i);
		}
	}
	/**
	 * Uses the information in a_i array to order the permutation.
	 * 
	 * Initial state of permutation is n, n-1, n-2, ... n-(n-1)
	 * For each element in the permutation:
	 * 1) Check the initial number of elements in the permutation before 
	 * the current element which are larger than the current element (by counting)
	 * 2) Check the many elements before the current element which are 
	 * *supposed* to be larger than the current element (from a_i array)
	 * 3)a) While the 1st value is greater than the second value, 
	 * swap the current element with the one before it in the permutation
	 * (moving the current element closer to the beginning of the list, which 
	 * reduces the number of elements before it which can be larger than it).
	 * 3)b) For each swap towards the front of the list, if the value 
	 * which was swapped is larger than the current element, reduce the 
	 * count of elements larger than the current element which appear before 
	 * it in the permutation by one (allowing termination of 3)a))
	 * 
	 * Uses secondary index variable 'j' to follow the current element from 
	 * the outer loop through the permutation as it is moved
	 */
	public void sortSelf() {
		// i is the starting index of a given element n, j follows that element as it is swapped backward (if necessary)
		for (int i = 0, j; i < perm.length; i++) {
			j = i;
			int numBefore = greaterBefore(j);
			int targetNumBefore = a_i[perm[j]-1];
			while (numBefore > targetNumBefore) {
				if (perm[j-1] > perm[j]) {
					numBefore--;	// if the number before i in the list is larger than i, we decrement the count of numbers as we swap
				}
				swapBack(j);	// move element backward (toward the beginning of the list
				j--;	// follow it as it goes
			}
		}
	}
	
	/**
	 * Swaps the element at 'index' in the permutation with the element in the permutation at index-1
	 * @param index - element to move
	 */
	private void swapBack(int index) {
		int temp = perm[index-1];
		perm[index-1] = perm[index];
		perm[index] = temp;
	}
	/**
	 * Counts the number of elements in the permutation before index which are larger than the value of index
	 * @param index - element to check
	 * @return int - count of larger elements
	 */
	private int greaterBefore(int index) {
		int count = 0;
		for (int i = 0; i < index; i++) {
			if (perm[i] > perm[index]) {
				count++;
			}
		}
		return count;
	}
	/**
	 * Returns ref to the permutation array
	 * @return integer array of the permutation
	 */
	public int[] getPermutation() {
		return perm;
	}
	
}
