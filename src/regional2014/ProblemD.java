package regional2014;

import java.util.*;

/*
 * ICPC MidAtl Regional 2014
 * Problem D - "Everything in Excess!"
 * 
 * Definitely mixed up and backwards about where to use sqrt(n).
 * There are primes sqrt(10,000,000)..10,000,000.
 * But in trial division we only want to use divisors up to sqrt(n)
 * So do we ever need to generate a prime > sqrt(10,000,000) ?
 * 
 * Not the fastest - does 2..10,000,000 in ~19 seconds
 */
public class ProblemD {

	private static final int BIGGEST_N = 10000000;
	private static final List<Integer> PRIMES = getPrimes(BIGGEST_N);
	
	public static void main(String[] args) {
		/*
		System.out.println(PRIMES.size());
		System.out.println(collectionAsString(PRIMES));
		//*/
		
		Scanner scin = new Scanner(System.in);
		
		// get initial input
		int bottom = scin.nextInt();
		int top = scin.nextInt();
		scin.nextLine();
		
		while (top != 0 && bottom != 0) {
			
			// solve current input set
			long start = System.currentTimeMillis();
			System.out.println(largestExcessInRange(bottom, top));
			System.err.printf("(%d)%n", System.currentTimeMillis() - start);
			
			// get next input
			bottom = scin.nextInt();
			top = scin.nextInt();
			scin.nextLine();
		}
		
		scin.close();
	}
	
	/* returns the number in range [bottom, top] with the largest excess */
	private static int largestExcessInRange(int bottom, int top) {
		int most_excessive = bottom;
		int max_excess = getExcess(bottom);
		
		for (int val = bottom + 1; val <= top; val += 1) {
			int excess = getExcess(val);
			if (excess > max_excess) {
				max_excess = excess;
				most_excessive = val;
			}
		}
		
		return most_excessive;
	}
	
	/* finds the excess of a number by subtracting it's number of unique prime factors
	 * from the number of values in it's factorization.
	 */
	private static int getExcess(int number) {
		Set<Integer> unique_factors = new HashSet<>();
		List<Integer> all_factors = getFactorization(number);
		
		unique_factors.addAll(all_factors);
		
		return all_factors.size() - unique_factors.size();
	}
	
	/* returns the full prime factorization of an integer */
	private static List<Integer> getFactorization(int number) {
		List<Integer> factors = new ArrayList<>();
		
		int prime_idx = 0;
		while (number > 1 && prime_idx < PRIMES.size()) {
			int prime = PRIMES.get(prime_idx);
			
			while (number % prime == 0) {
				number = number / prime;
				factors.add(prime);
			}
			
			prime_idx += 1;
		}
		
		return factors;
	}
	
	/**
	 * Finds all prime numbers below a maximum using the Sieve of Eratosthenes
	 * @param n ceiling for primes - must be <= INT_MAXVALUE
	 * @return a List of prime integers less than n
	 */
	private static List<Integer> getPrimes(int n) {
		List<Integer> primes = new ArrayList<Integer>();
		boolean[] sieve = new boolean[n+1];
		
		// initially assume all numbers are prime
		for (int i = 2; i < sieve.length; i += 1) sieve[i] = true;

		// only need to consider values up to sqrt(n) ?
		int top = (int) Math.ceil(Math.sqrt(n));
		
		for (int p = 2; p <= top; p += 1) {
			// when we reach a prime...
			if (sieve[p]) {
				// ...add it to the result list
				primes.add(p);
				// ...and mark all of it's multiples as composite
				for (int i = p; i <= n / p; i += 1) {
					sieve[i * p] = false;
				}
			}
		}
		
		return primes;
	}
	
	/* for debuggin' */
	private static <T> String collectionAsString(Collection<T> things) {
		StringBuilder sb = new StringBuilder();
		sb.append("[ ");
		for (T thing : things) {
			sb.append(thing.toString() + ", ");
		}
		sb.append("]");
		return sb.toString();
	}

}
