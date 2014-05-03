
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
 
public class ProbE {

	public static void main(String[] args) {
		Scanner scin = new Scanner(System.in);
		boolean done = false;
		// outermost input loop
		while (!done) {
			int numTeams = scin.nextInt();
			done = (numTeams == 0);
			// ... for a given set of teams
			if (!done) {
				Map<String, Team> teams = new TreeMap<String, Team>();	// hold all team data; name is key b.c guaranteed unique
				for (int i = 0; i < (numTeams*2); i++) {
					scin.next();	// skip the name; we don't care.
					String teamName = scin.next();
					double memInitWeight = scin.nextDouble();
					double memFinWeight = scin.nextDouble();
					// adding new members vs. adding new teams
					if (teams.containsKey(teamName)) {
						teams.get(teamName).addMember(memInitWeight, memFinWeight);
					}
					else {
						teams.put(teamName, new Team(memInitWeight, memFinWeight));
					}
				}
				// after collection, find max percent lost (and name of team)
				Set<Map.Entry<String, Team>> teamSet = teams.entrySet();	// set of k|v, so iterable
				String winningTeam = "";
				double winningPercent = 0.0;
				boolean firstTeam = true;	// differentiate first team, so winningVars always get set
				for (Map.Entry<String, Team> ateam : teamSet) {
					double per = ateam.getValue().getPercentLost();
					if (firstTeam) {
						winningTeam = ateam.getKey();
						winningPercent = per;
						firstTeam = false;
					}
					else if (per > winningPercent) {
						winningTeam = ateam.getKey();
						winningPercent = per;
					}
				}
				System.out.printf("%s %2.1f%%%n", winningTeam, winningPercent);
			}
		}
		scin.close();
	}
}

class Team {
	
	private double initWeight = 0.0;
	private double finalWeight = 0.0;
	private double percentLost = 0.0;
	
	public Team() {
		addMember(0.0, 0.0);
	}
	
	public Team(double init, double fin) {
		addMember(init, fin);
	}
	
	public void addMember(double init, double fin) {
		this.initWeight += init;
		this.finalWeight += fin;
		updatePercentLost();
	}
	
	public double getInitWeight() {
		return initWeight;
	}

	public double getFinalWeight() {
		return finalWeight;
	}

	public double getPercentLost() {
		return percentLost;
	}

	private void updatePercentLost() {
		this.percentLost = (100*((initWeight-finalWeight)/initWeight));
	}
}
