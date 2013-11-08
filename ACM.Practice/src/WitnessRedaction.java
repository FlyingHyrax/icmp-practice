import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WitnessRedaction {

	private static final boolean dbg = false;
	public static void main(String[] args) {
		Scanner scin = new Scanner(System.in);
		boolean done = false;
		while (!done) {
			if (scin.hasNext("EndOfInput")) {
				done = true;
			}
			else {
				// get list of flag words
				ArrayList<String> flags = getKeywords(scin);
				if (dbg) {
					for (String s : flags) {
						System.err.println(s);
					}
				}
				// get message contents
				StringBuffer msg = getMessage(scin);
				if (dbg) {
					System.err.println(msg.toString() + "(" + msg.length() + " chars)");
				}
				// whiteout message using keywords (solve and print)
				whiteout(msg, flags);
			}
		}
	}
	
	private static ArrayList<String> getKeywords(Scanner scin) {
		ArrayList<String> keywords = new ArrayList<String>();
		boolean done = false;
		while (!done) {
			String str = scin.nextLine();
			if (str.equals("EndOfList")) {
				done = true;
			}
			else {
				keywords.add(str.toLowerCase());
			}
		}
		return keywords;
	}
	
	private static StringBuffer getMessage(Scanner scin) {
		boolean done = false;
		StringBuffer buff = new StringBuffer();
		while (!done) {
			String str = scin.nextLine();
			if (str.equals("EndOfMsg")) {
				done = true;
			}
			else {
				buff.append(str);
				buff.append('\n');
			}
		} 
		return buff;
	}
	
	private static void whiteout(StringBuffer message, ArrayList<String> keywords) {
		// get sentence delimiter list
		ArrayList<Integer> sentenceMarks = getSentenceDelimiters(message);
		// test sentences for bad words and replace
		for (int i = 0; i < sentenceMarks.size()-1; i++) {
			int start = sentenceMarks.get(i);
			int end = sentenceMarks.get(i+1);
			String sentence = message.substring(start, end);
			if (dbg) {
				System.err.println("[" + start + ", " + end + "]: \"" + sentence + "\"");
			}
			if (sentenceContainsFlags(sentence, keywords)) {
				message.replace(start, end, getWhiteout(sentence));
			}
		}
		System.out.print(message.toString());
		System.out.println("====");
	}
	
	private static ArrayList<Integer> getSentenceDelimiters(StringBuffer str) {
		ArrayList<Integer> delims = new ArrayList<Integer>();
		delims.add(0);
		for (int i = 0; i < str.length(); i++) {
			char currentChar = str.charAt(i);
			switch (currentChar) {
				case '.':
					delims.add(i+1);
					break;
				case '!':
					delims.add(i+1);
					break;
				case '?':
					delims.add(i+1);
					break;
				case '\n':
					if ((i-1) >= 0 && str.charAt(i-1) == '\n') {
						delims.add(i-1);
						delims.add(i);
					}
					break;
				default:
					break;
			}
		}
		if (str.length() > 0) {	// strings of length 0
			delims.add(str.length()-1);
		}
//		Collections.sort(delims);
		return delims;
	}
	
	private static String getWhiteout(String str) {
		StringBuffer whiteout = new StringBuffer();
		for (int i = 0; i < str.length(); i++) {
			char currentChar = str.charAt(i);
			if (currentChar == '\n') {
				whiteout.append("\n");
			}
			else {
				whiteout.append('@');
			}
		}
		return whiteout.toString();
	}
	
	private static boolean sentenceContainsFlags(String sentence, ArrayList<String> flags) {
		sentence = sentence.toLowerCase();
//		String[] words = sentence.split("\\b");	// this captures all the spaces between words as well, which doubles number of comparisons
		Pattern word  = Pattern.compile("\\w+");
		Matcher matchWord = word.matcher(sentence);
		while (matchWord.find()) {
			if (flags.contains(matchWord.group())) {
				return true;
			}
		}
		return false;
	}

}
