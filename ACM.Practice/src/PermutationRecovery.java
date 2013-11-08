import java.util.*;

/**
 * MidAtl 2006 Prob A - "Permutation Recovery"
 * BROKEN - time complexity too great (Bubbling not a great approach)
 * @author Matthew Seiler
 * @version
 */
public class PermutationRecovery {
	/**
	 * Driver checks "done-ness" and acts accordingly
	 * @param args
	 */
	public static void main(String[] args) {
		boolean done = false;
		Scanner scin = new Scanner(System.in);
		while (!done) {
			int n = scin.nextInt();
			if (n == 0) {
				done = true;
			}
			else {
				PermOld p = new PermOld(n, scin);
				p.sortSelf();
				printResult(p.getPermutation());
			}
		}
	}
	/**
	 * Given an integer list, prints the values on one line separated by commas
	 * @param perm = list of integers
	 */
	private static void printResult(List<Integer> perm) {
		for (int i = 0; i < perm.size(); i++) {
			System.out.print(perm.get(i));
			if (i != perm.size()-1) {
				System.out.print(",");
			}
		}
		System.out.println("");
	}

}

class PermOld {

	private int[] a_sub_i;
	private List<Integer> perm;
	
	/**
	 * Constructor for one permutation
	 * @param n - number of elements in permutation
	 * @param scin - scanner to get a_i elements from standard in
	 */
	public PermOld(int n, Scanner scin) {
		a_sub_i = new int[n];
		perm = new LinkedList<Integer>();
		for (int i = 0; i < n; i++) {
			a_sub_i[i] = scin.nextInt();
			perm.add(i+1);
		}
	}
	
	/**
	 * Rather like BubbleSort, only the method of determining 
	 * if an element is in the correct position is"greaterBefore(index)"
	 */
	public void sortSelf() {
		boolean ordered = false;
		while (!ordered) {
			ordered = true;
			for (int index = 0; index < perm.size(); index++) {
				int current_a = a_sub_i[(perm.get(index)-1)];
				if (greaterBefore(index) < current_a) {
					swapUp(index);
					ordered = false;
				}
			}
		}
	}
	
	/**
	 * Counts the number of elements before a given index 
	 * which have a value greater than the value in the index.
	 * @param index
	 * @return int - a number of elements
	 */
	private int greaterBefore(int index) {
		int current = perm.get(index);
		int numGreater = 0;
		for (int i = 0; i < index; i++) {
			if (perm.get(i) > current) {
				numGreater++;
			}
		}
		return numGreater;
	}
	
	/**
	 * Swaps a given element with the element ahead of it in the list.
	 * @param index
	 */
	private void swapUp(int index) {
		int temp = perm.get(index+1);
		perm.set(index+1, perm.get(index));
		perm.set(index, temp);
	}
	
	/**
	 * Returns reference to the List containing the actual permutation.
	 * @return the LinkedList for the permutation
	 */
	public List<Integer> getPermutation() {
		return perm;
	}
	
}