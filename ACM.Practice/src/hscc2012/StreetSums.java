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
			int sumAll = sumToN(streetLength);
			for (int i = 2; i < streetLength; i += 1) {
				int sl = sumToN(i-1);
				int sg = sumAll - i - sl;
				if (sl == sg) {
//					System.err.println(sl + ", " + sg);
					System.out.printf("(%d, %d)%n", i, streetLength);
					numFound += 1;
					break;
				}
			}
			streetLength += 1;
		}
	}
	
	/**
	 * One of Dr. B's "two arithmetic sums every CS major should memorize"
	 * @param n max
	 * @return sum of integers from 1 to n
	 */
	private static int sumToN(int n) {
		return (int)((n / 2.0)*(1 + n));
	}

}
