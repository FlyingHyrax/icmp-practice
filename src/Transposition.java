import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * MidAtl Problem A - "First Composed, Then Transposed"
 * @author Matthew Seiler
 * @version 2013-10-06
 */
public class Transposition {
	public static void main(String[] args) {
		Scanner scin = new Scanner(System.in);
		boolean done = false;
		
		while (!done) {
			String line = scin.nextLine();
			if (line.equals("***")) {
				done = true;
			}
			else {
				String[] noteTokens = line.split("\\s+");
				int shift = scin.nextInt();
				scin.nextLine();	// eat trailing newline
				transpose(noteTokens, shift);
			}
		}
		
		scin.close();
	}
	
	private static void transpose(String[] noteInput, int shift) {
		// handle special case: empty string from blank line
		if (noteInput.length == 1 && noteInput[0].equals("")) {
			System.out.println();
			return;
		}
		// turn array of note tokens into array of number 0-11;
		int[] numericNotes = stringsToInts(noteInput);
		// add the shift value to the numeric notes, special cases for Ab and G##
		numericNotes = shiftInts(numericNotes, shift);
		// print strings correspnding to the shifted numeric notes
		printIntsAsStrings(numericNotes);
	}
	
	private static int[] stringsToInts(String[] str) {
		int[] numerics = new int[str.length];
		for (int i = 0; i < str.length; i++) {
			numerics[i] = makeNumericNote(str[i]);
		}
		return numerics;
	}
	private static int[] shiftInts(int[] ints, int shift) {
		boolean negative = (shift < 0);
		shift = Math.abs(shift) % 12;
		if (negative) {
			shift = shift * -1;
		}
		for (int i = 0; i < ints.length; i++) {
			int shiftedValue = ints[i] + shift;
			if (shiftedValue < 0) {
				shiftedValue = 12 + shiftedValue;
			}
			else if (shiftedValue > 11) {
				shiftedValue = shiftedValue - 12;
			}
			ints[i] = shiftedValue;
		}
		return ints;
	}
	
	private static void printIntsAsStrings(int[] notes) {
		String[] noteStrings = {"A", "A#", "B", "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#"};
		for (int i = 0; i < notes.length; i++) {
			System.out.print(noteStrings[notes[i]]);
			if (i != notes.length - 1) {
				System.out.print(" ");
			}
		}
		System.out.println();
	}
	
	private static int makeNumericNote(String note) {
		Pattern notePattern = Pattern.compile("([A-G]?)([b|#]?)");
		Matcher noteMatch = notePattern.matcher(note);
		noteMatch.matches();
		int numericNote = findIndexOfNote(noteMatch.group(1));
		if (noteMatch.group(2).equals("#")) {
			numericNote++;
		}
		else if (noteMatch.group(2).equals("b")) {
			numericNote--;
		}
		return numericNote;
	}
	
	private static int findIndexOfNote(String note) {
		String[] noteStrings = {"A", "A#", "B", "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#"};
		for (int i = 0; i < noteStrings.length; i++) {
			if (noteStrings[i].equals(note)) {
				return i;
			}
		}
		return -1;
	}
	
}
