package regional2014;

import java.util.*;

/*
 * THIS DOES NOT WORK.  We've started but we can't put them all together...
 * 
 * I've got it breaking the word into character chunks correctly, and identifying the
 * chunks as vowels and consonants correctly, though everything is a total mess.
 * 
 * Now just stuck on doing the actual hyphenation - matching the vcv / vccv patterns and
 * inserting the hyphens without things exploding didn't work out so well.
 */
public class ProblemA {

	public static void main(String[] args) {
		Scanner scin = new Scanner(System.in);
		
		while (!scin.hasNext("===")) {
			String in = scin.next();
			// System.out.println("<< " + Word.hyphenate(in));
		}
		
		scin.close();
	}
	
}

/*
 * This parse is just nuts.
 * 
 * Methinks this could be done w/ stream processing?  Maybe?
 */
class Word {
	
	/* constants */
	private static final Set<String> VOWELS = new HashSet<>();
	
	private static final Set<String> PSEUDO_CONSONANTS = new HashSet<>();
	
	static {
		VOWELS.addAll(Arrays.asList( 
				new String[] { "a", "e", "i", "o", "u", "y" } ));
		PSEUDO_CONSONANTS.addAll(Arrays.asList( 
				new String[] { "qu", "tr", "br", "str", "st", "sl", "bl", "cr", "ph", "ch" } ));
	}
	
	/* string tests */
	
	public static boolean splittable(String str) {
		return str.length() > 1 && !PSEUDO_CONSONANTS.contains(str.toLowerCase());
	}
	
	public static boolean isVowel(String str) {
		return VOWELS.contains(str.toLowerCase());
	}
	
	public static boolean isConsonant(String str) {
		String lower= str.toLowerCase();
		if (PSEUDO_CONSONANTS.contains(lower)) {
			return true;
		}
		else if (lower.length() == 1) {
			char c = lower.charAt(0);
			if (Character.isAlphabetic(c)) {
				return !VOWELS.contains(lower);
			}
		}
		return false;
	}
	
	// take a string and break out 3-char consonants
	public static List<String> splitThrees(String str) {
		List<String> res = new LinkedList<String>();
		boolean found = false;
		for (int i = 0; i < str.length() - 3; i += 1) {
			String chunk = str.substring(i, i + 3);
			// found an un-splittable chunk
			if (PSEUDO_CONSONANTS.contains(chunk.toLowerCase())) {
				// the first part of the string as a chunk
				String before = str.substring(0, i);
				if (before.length() > 0) {
					res.add(before);
				}
				// the chunk we found
				res.add(chunk);
				// the rest of the chunks
				res.addAll(splitThrees(str.substring(i + 3)));
				// done
				found = true;
				break;
			}
		}
		// if we never hit a pattern match, then add the whole string to the list
		if (!found) {
			res.add(str);
		}
		
		return res;
	}
	
	// take a string and break out 2-char consonants
	public static List<String> splitTwos(String str) {
		List<String> res = new LinkedList<String>();
		boolean found = false;
		for (int i = 0; i < str.length() - 2; i += 1) {
			String chunk = str.substring(i, i + 2);
			
			if (PSEUDO_CONSONANTS.contains(chunk.toLowerCase())) {
				// add the part of the string before the chunk 
				String before = str.substring(0, i);
				if (before.length() > 0) {
					res.add(before);
				}
				// add the chunk
				res.add(chunk);
				// process the rest of the string and append results to the list
				res.addAll(splitTwos(str.substring(i + 2)));
				
				found = true;
				break;
			}
		}
		// if we never hit the pattern, then we haven't added any substrings to the list
		if (!found) {
			// add the whole string to the list
			res.add(str);
		}
		
		return res;
	}
	
	
	public static List<String> splitOnes(String str) {
		List<String> res = new LinkedList<String>();
		for (int i = 0; i < str.length(); i += 1) {
			res.add(str.substring(i, i + 1));
		}
		return res;
	}
	
	public static List<String> split(String str) {
		/* break out all the 3-char consonants */
		List<String> sansThrees = splitThrees(str);
		
		/* break out all the 2-char consonants */
		List<String> sansTwos = new LinkedList<String>();
		for (String s : sansThrees) {
			if (splittable(s)) {
				sansTwos.addAll(splitTwos(s));
			}
			else {
				sansTwos.add(s);
			}
		}
		
		/* now, whatever is left needs to be broken up into individual chars */
		List<String> res = new LinkedList<String>();
		for (String s : sansTwos) {
			if (splittable(s)) {
				res.addAll(splitOnes(s));
			}
			else {
				res.add(s);
			}
		}
		
		return res;
	}
}

