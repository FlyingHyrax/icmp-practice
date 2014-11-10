package regional2014;

import java.util.*;

/*
 * ICPC MidAtl Regional 2014
 * Problem D - "Everything in Excess!"
 * 
 * Finally figured out why nothing seemed to work in the sieve - I tried to stick the .add()
 * (to add prime indices to the list of primes) inside the for-loop that marks the composite indices,
 * but that outer loop never iterates over the entire marker array!  So the primes were marked correctly,
 * but I wasn't adding all of them... 
 * 
 * 2..10000000 in ~18 seconds
 */
public class ProblemD {

	/* this is the highest input we can possibly receive */
	private static final int BIGGEST_N = 10000000;
	
	/* The last possible prime factor of N is P_i, where (P_i+1)^2 > N That is, no prime > sqrt(N) 
	 * is a factor of N, because the largest possible prime factor would be P = sqrt(N) or P^2 = N 
	 * So when we pre-compute primes, don't compute a prime larger than the square root of our maximum */
	private static final int PRIME_CEILING = (int) Math.ceil(Math.sqrt(BIGGEST_N));
	
	/* getPrimes will return every prime <= it's argument */
	private static final List<Integer> PRIMES = getPrimes(PRIME_CEILING);
	
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
		int prime = 0; // cheat on initialization
		int stop = (int) Math.ceil(Math.sqrt(number));
		
		/* The only one of these rules I am sure about is #1)
		 * 1) Main condition: If 'number' == 1, then we have divided out all of it's factors
		 * 2) Short-circuit: If the current prime (the prime at prime_idx) is > sqrt(number),
		 *   then no larger prime can possibly be a factor of 'number', so 'number' is primes
		 * 3) Safety-check: We have completely run out of factors to check; 'number' is prime
		 */
		while (number > 1 && prime < stop && prime_idx < PRIMES.size()) {
			prime = PRIMES.get(prime_idx);
			
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
	 * ftp://ftp.cs.wisc.edu/pub/techreports/1990/TR909.pdf
	 * @param n ceiling for primes - must be <= INT_MAXVALUE
	 * @return a List of prime integers less than n
	 */
	private static List<Integer> getPrimes(int n) {
		// list to hold the prime integers we find
		List<Integer> primes = new ArrayList<Integer>();
		// markers - if (sieve[i]) then i is prime
		boolean[] sieve = new boolean[n+1];
		
		// initially assume all numbers are prime except 0 and 1
		for (int i = 2; i < sieve.length; i += 1) sieve[i] = true;

		/* iteration max - NOT the largest possible prime, but when marking composites 
		 * formed by multiplying primes less than this, we will naturally eliminate the 
		 * non-primes between sqrt(n) and n; the first composite after p that hasn't 
		 * already been marked previously is p^2 */
		int stop = (int) Math.ceil(Math.sqrt(n));
		for (int p = 2; p <= stop; p += 1) {
			// when we reach a prime...
			if (sieve[p]) {
				// mark all of it's multiples as composite
				for (int i = p; i <= n / p; i += 1) {
					sieve[i * p] = false;
				}
			}
		}
		
		// now go over the marker array and add all the prime indices to a list
		// (we need a separate for loop b/c the main sieve loop never hits the entire marker array) 
		for (int i = 2; i < sieve.length; i += 1) {
			if (sieve[i]) {
				primes.add(i);
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
