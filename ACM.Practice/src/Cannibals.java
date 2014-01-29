/* Missionaries and Cannibals
 * (sans-accumulator, need to add that to show solution steps)
 * htdp.org/2003-09-26/Book/curriculum-Z-H-40.html#node_sec_32.2
 */

import java.util.HashSet;
import java.util.LinkedList;

public class Cannibals {
	
	public static void main(String[] _) {
		Problem p = new Problem(2, 2, 3);
		p.solve();
		printStates(p.getSolutions());
	}
	
	/**
	 * Helper method using State.toString() to print a list of states to 
	 * standard out, one state per line.
	 * @param states - a LinkedList of game/problem States
	 */
	private static void printStates(LinkedList<State> states) {
		for (State s : states) {
			System.out.println(s.toString());
		}
	}
	
}

class Problem {
	
	private LinkedList<State> solutions;
	
	/**
	 * @param initM
	 * @param initC
	 * @param boatCap
	 */
	public Problem(int initM, int initC, int boatCap) {
		solutions = new LinkedList<State>();
		
		State.missTotal = initM;
		State.cannTotal = initC;
		Boatload.capacity = boatCap;
		
		Boatload.possibleLoads = getPossibleLoads();
	}
	
	public LinkedList<State> getSolutions() {
		return solutions;
	}
	
	/**
	 * Starter method for the recursive solver()
	 */
	public void solve() {
		// no-arg State constructor uses static 'total' fields
		LinkedList<State> states = new LinkedList<>();
		states.add(new State());	// list w/ one initial element
		
		solvable(states);
	}
	
	/**
	 * Recursively generates lists of states using the previous list of states
	 * until it is given a list with a final/solved state.
	 * @param initStates List of initial states (usually list of only one element)
	 */
	private void solvable(LinkedList<State> initStates) {
		solutions = filterFinalStates(initStates);
		if (solutions.size() > 0) {
			System.out.println("Solved");
			return;
		}
		else {
			solvable(filterLegalStates(mapNewStates(initStates)));
		}
	}
	
	/**
	 * Creates a set of all possible boatloads.
	 * INEFFICIENT: Could use, like, actual combinatorics.
	 * (Though, we only do it once...)
	 * @return HashSet of all possible BoatLoads for Boatload.capacity
	 */
	private HashSet<Boatload> getPossibleLoads() {
		HashSet<Boatload> possibleLoads = new HashSet<Boatload>();
		for (int i = 1; i <= Boatload.capacity; i++) {
			possibleLoads.add(new Boatload(i, 0));
			possibleLoads.add(new Boatload(0, i));
			possibleLoads.add(new Boatload(i, (Boatload.capacity - i)));
		}
		return possibleLoads;
		
	}
	
	/**
	 * Creates a list of all possible future states from the list of all possible current states
	 * @param prevStates - LinkedList of game states
	 * @return LinkedList of all possible states reachable from all prevStates
	 */
	private LinkedList<State> mapNewStates(LinkedList<State> prevStates) {
		LinkedList<State> nextStates = new LinkedList<State>();
		for (State state : prevStates) {
			nextStates.addAll(getNextStates(state));
		}
		return nextStates;
	}
	
	/**
	 * Creates a list of all possible future states from a given single state
	 * using the set of all possible boatloads.
	 * INEFFECIENT: not all boatloads are valid for all possible current states - eats memory.
	 * @param s - a single game/problem State
	 * @return LinkedList of all possible states that can be reached from state s,
	 * determined using the set of all possible boatloads.
	 */
	private LinkedList<State> getNextStates(State s) {
		LinkedList<State> nextStates = new LinkedList<State>();
		for (Boatload bl : Boatload.possibleLoads) {
			nextStates.add(s.next(bl));
		}
		return nextStates;
	}
	
	/**
	 * Filters a list of states to eliminate illegal states / "catch" legal states
	 * @param states - LinkedList of States
	 * @return LinkedList of States that satisfy isLegal
	 */
	private LinkedList<State> filterLegalStates(LinkedList<State> states) {
		LinkedList<State> cleanedStates = new LinkedList<State>();
		for (State s : states) {
			if (s.isLegal()) {
				cleanedStates.add(s);
			}
		}
		return cleanedStates;
	}
	
	/**
	 * Filter to catch any states which are a valid final state in a list of states.
	 * It's a good idea to use filterLegalStates() first.
	 * @param states - LinkedList of States
	 * @return LinkedList of States that satisfy isFinal()
	 */
	private LinkedList<State> filterFinalStates(LinkedList<State> states) {
		LinkedList<State> finalStates = new LinkedList<State>();
		for (State s : states) {
			if (s.isFinal()) {
				finalStates.add(s);
			}
		}
		return finalStates;
	}

}

class Boatload {
	
	private int miss, cann;
	
	static int capacity;
	static HashSet<Boatload> possibleLoads;

	/**
	 * @param miss - number of missionaries in the boatload
	 * @param cann - number of cannibals in this boatload
	 */
	public Boatload(int miss, int cann) {
		this.miss = miss;
		this.cann = cann;
	}
	
	/**
	 * Getter for the 'miss' field
	 * @return number of missionaries in the boat
	 */
	public int getMiss() {
		return this.miss;
	}
	
	/**
	 * Getter for the 'cann' field
	 * @return number of cannibals in the boat
	 */
	public int getCann() {
		return this.cann;
	}
	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return String.format("Boatload: %d m , %d c", miss, cann);
	}

	// Thanks Eclipse!
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + cann;
		result = prime * result + miss;
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
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
		Boatload other = (Boatload) obj;
		if (cann != other.cann) {
			return false;
		}
		if (miss != other.miss) {
			return false;
		}
		return true;
	}

}

class State {
	
	private int missLeft, cannLeft;
	private boolean boatOnLeft;
	
	static int missTotal, cannTotal;
	
	public State() {
		this(State.missTotal, State.cannTotal, true);
	}
	
	/**
	 * @param missLeft number of missionaries on the left shore
	 * @param cannLeft number of cannibals on the left shore
	 * @param boatOnLeft true if the boat is on the left shore
	 */
	public State(int missLeft, int cannLeft, boolean boatOnLeft) {
		this.missLeft = missLeft;
		this.cannLeft = cannLeft;
		this.boatOnLeft = boatOnLeft;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return String.format("Left: m %d c %d, Right: m %d c %d, boat: %s", 
				missLeft, cannLeft, (missTotal - missLeft), (cannTotal - cannLeft), 
				(boatOnLeft ? "left" : "right"));
	}
	
	/**
	 * Given a boatload, return the state reached from this state after 
	 * 'executing' the boat trip.
	 * @param bl - boatload object
	 * @return the resulting state
	 */
	public State next(Boatload bl) {
		if (boatOnLeft) {
			return new State(missLeft - bl.getMiss(), cannLeft - bl.getCann(), false);
		}
		else {
			return new State(missLeft + bl.getMiss(), cannLeft + bl.getCann(), true);
		}
	}

	/**
	 * Determines if the current state follows the rules of the game
	 * Note to self:
	 *  1) If the number of a group on one side of a river is greater than the total, than it will be negative on the other side.
	 *  2) If the number of a group on one side of the river is negative, then the other side will be greater than the total.
	 *  3) You can therefore cover both cases by testing if either side is greater than the total for both categories
	 * @return true if a valid state
	 */
	public boolean isLegal() {
		// temps
		int missRight = missTotal - missLeft;
		int cannRight = cannTotal - cannLeft;
		boolean flag = true;
		
		// more than total (which implies checks against negative values since we calculate right = total - left)
		if (missLeft > missTotal || cannLeft > cannTotal || missRight > missTotal || cannRight > cannTotal) {
			flag = false;
		}
		// number of cannibals on a side compared to number of missionaries...
		else if ((missLeft > 0 && cannLeft > missLeft) || (missRight > 0 && cannRight > missRight)) {
			flag = false;
		}

		return flag;
	}
	
	/**
	 * Determines if this state is the final/winning state for the game
	 * by checking if there are no missionaries or cannibals left on this side of the river
	 * @return true if this state is the final state
	 */
	public boolean isFinal() {
		return (missLeft == 0 && cannLeft == 0);
	}

}
