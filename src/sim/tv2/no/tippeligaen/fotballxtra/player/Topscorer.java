package sim.tv2.no.tippeligaen.fotballxtra.player;

public class Topscorer extends Player {
	
	private int goals;
	private double goalAvg;
	
	public Topscorer(String number, String name, String team, int yellowCards,
			int matches, String average) {
		super(number, name, team, yellowCards, matches, average);
		// TODO Auto-generated constructor stub
	}
	
	public Topscorer(String name, String team, int goals, int matches, double goalAvg) {
		super(null, name, team, 0, matches, null);
		this.setGoals(goals);
		this.setGoalAvg(goalAvg);
	}

	/**
	 * @return the goals
	 */
	public int getGoals() {
		return goals;
	}

	/**
	 * @param goals the goals to set
	 */
	public void setGoals(int goals) {
		this.goals = goals;
	}

	/**
	 * @return the goalAvg
	 */
	public double getGoalAvg() {
		return goalAvg;
	}

	/**
	 * @param goalAvg the goalAvg to set
	 */
	public void setGoalAvg(double goalAvg) {
		this.goalAvg = goalAvg;
	}
	
	

}
