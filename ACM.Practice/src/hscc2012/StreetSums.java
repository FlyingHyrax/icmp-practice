package hscc2012;


/**
 * RU Local contest 2012 Problem 3 - "Street Sums"
 * @author Matthew Seiler
 * @version 2014-04-27
 */
public class StreetSums {
	
	public static void main(String[] args) {
		int streetLength = 8;
		int numFound = 0;
		while (numFound < 6) {
			for (int i = 2; i < streetLength; i += 1) {
				int sl = sumRange(1, i-1);
				int sg = sumRange(i+1, streetLength);
				if (sl == sg) {
					System.err.println(sl + ", " + sg);
					System.out.printf("(%d, %d)%n", i, streetLength);
					numFound += 1;
					break;
				}
			}
			streetLength += 1;
		}
	}

	// TODO: memoize this... tried a few different hash ideas but nothing worked
	private static int sumRange(int min, int max) {
		return (min == max ? min : max + sumRange(min, max - 1));
	}

}
