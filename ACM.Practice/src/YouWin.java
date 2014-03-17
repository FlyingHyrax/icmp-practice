import java.util.*;

public class YouWin {
	
	public static void main(String[] _) {
		YouWin one = new YouWin("MATT");
		System.out.println(one.solve());
		System.out.println(one.audit);
	}
	
	String target;
	int audit = 0;
	
	public YouWin(String target) {
		this.target = target;
	}
	
	public int solve() {
		Set<NameState> states = new HashSet<NameState>();
		states.add(new NameState());
		int solution = getSolution(states);
		audit = 0;
		
		while (solution == -1) {
			// map
			states = nextStates(states);
			// filter
			states = filterLegal(states);
			// check for a solution
			solution = getSolution(states);
			
			audit++;
		}
		
		return solution;
	}
	
	private Set<NameState> nextStates(Set<NameState> init) {
		Set<NameState> nexts = new HashSet<NameState>();
		for (NameState s : init) {
			nexts.addAll(s.allNext());
		}
		return nexts;
	}
	
	private Set<NameState> filterLegal(Set<NameState> init) {
		Set<NameState> filtered = new HashSet<NameState>();
		for (NameState s : init) {
			if (s.isLegal(target)) {
				filtered.add(s);
				NameState.seen.add(s);
			}
		}
		return filtered;
	}
	
	private int getSolution(Set<NameState> states) {
		for (NameState s : states) {
			if (target.equals(s.name)) {
				System.out.println(s.toString());
				return s.counter;
			}
		}
		return -1;
	}
	
}

class NameState {
	
	int cursorIndex;
	int letterIndex;
	String name;
	
	int counter;
	
	public NameState() {
		this(0, 0, "", 0);
	}
	
	public NameState(int c, int l, String n, int count) {
		cursorIndex = c;
		letterIndex = l;
		name = n;
		counter = c;
//		this is a terrible idea, then you can't use isLegal to check for duplicates...
//		NameState.seen.add(this);
	}
	
	public NameState(NameState parent, Button action) {
		// clone
		this.name = parent.name;
		this.cursorIndex = parent.cursorIndex;
		this.letterIndex = parent.letterIndex;
		this.counter = parent.counter + 1;
		// mutate
		switch (action) {
		case LEFT:	// move cursor left
			this.cursorIndex = (parent.cursorIndex == 0 ? 0 : parent.cursorIndex - 1);
			break;
		case RIGHT:	// move cursor right
			this.cursorIndex = (parent.cursorIndex == parent.name.length() ? 
					parent.name.length() : parent.cursorIndex - 1);
			break;
		case UP:	// move selected letter from A toward Z, wrap Z->A
			this.letterIndex = (parent.letterIndex + 1) % NameState.LETTERS.length;
			break;
		case DOWN:	// move letter from Z toward A, wrap A->Z
			this.letterIndex = (parent.letterIndex == 0 ? 
					NameState.LETTERS.length - 1 : parent.letterIndex - 1);
			break;
		case FIRE:	// add current letter
			StringBuilder newStr = new StringBuilder(parent.name);
			newStr.insert(this.cursorIndex, NameState.LETTERS[this.letterIndex]);
			this.name = newStr.toString();
			this.cursorIndex++;
			break;
		}
	}
	
	/*
	public NameState next(Button action) {
		int tempC = this.cursorIndex;
		int tempL = this.letterIndex;
		String tempN = this.name;
		
		switch (action) {
		case LEFT:	// move cursor left
			tempC = (tempC == 0 ? 0 : tempC - 1);
			break;
		case RIGHT:	// move cursor right
			tempC = ((tempC == tempN.length() - 1) ? (tempN.length() - 1) : (tempC + 1));
			break;
		case UP:	// move selected letter from A toward Z, wrap Z->A
			tempL = (tempL + 1) % NameState.LETTERS.length;
			break;
		case DOWN:	// move letter from Z toward A, wrap A->Z
			tempL = (tempL == 0 ? NameState.LETTERS.length - 1 : tempL - 1);
			break;
		case FIRE:	// add current letter
			StringBuilder newStr = new StringBuilder(tempN);
			newStr.insert(tempC, NameState.LETTERS[tempL]);
			tempN = newStr.toString();
			tempC = tempC + 1;
			break;
		}
		
		return new NameState(tempC, tempL, tempN, (this.counter + 1));
	}
	*/
	
	public Set<NameState> allNext() {
		Set<NameState> nexts = new HashSet<NameState>();
		for (Button b : Button.values()) {
			nexts.add(new NameState(this, b));
		}
		return nexts;
	}
	
	public boolean isLegal(String target) {
		// cursorIndex in bounds
		if (0 > cursorIndex || cursorIndex > name.length()) {
			return false;
		}
		// length of name greater than length of target
		if (name.length() > target.length()) {
			return false;
		}
		// current state is a repeat (in the set of states we've already seen)
		if (seen.contains(this)) {
			return false;
		}
		// name contains letters that are not in the target
		for (int i = 0; i < name.length(); i++) {
			if (!target.contains(name.subSequence(i, i))) {
				return false;
			}
		}
		// TODO: add case - letters must also be in order!
		return true;
	}

	public boolean isTarget(String target) {
		return name.equals(target);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
//		result = prime * result + counter;
		result = prime * result + cursorIndex;
		result = prime * result + letterIndex;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		NameState other = (NameState) obj;
//		if (counter != other.counter) {
//			return false;
//		}
		if (cursorIndex != other.cursorIndex) {
			return false;
		}
		if (letterIndex != other.letterIndex) {
			return false;
		}
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		return true;
	}
	
	public String toString() {
		return String.format("Current %s (c %d, l %c) %d steps", name, cursorIndex, NameState.LETTERS[letterIndex], counter);
	}

	static final char[] LETTERS = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
									'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };
	static HashSet<NameState> seen = new HashSet<NameState>();
}

enum Button { UP, DOWN, LEFT, RIGHT, FIRE }