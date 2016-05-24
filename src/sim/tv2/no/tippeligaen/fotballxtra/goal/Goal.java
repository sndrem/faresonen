package sim.tv2.no.tippeligaen.fotballxtra.goal;

import sim.tv2.no.tippeligaen.fotballxtra.player.Player;

public class Goal {
	
	private Player goalScorer;
	private int time;
	
	public Goal(Player goalScorer, int time) {
		this.goalScorer = goalScorer;
		this.time = time;
	}

	/**
	 * @return the goalScorer
	 */
	public Player getGoalScorer() {
		return goalScorer;
	}

	/**
	 * @param goalScorer the goalScorer to set
	 */
	public void setGoalScorer(Player goalScorer) {
		this.goalScorer = goalScorer;
	}

	/**
	 * @return the time
	 */
	public int getTime() {
		return time;
	}

	/**
	 * @param time the time to set
	 */
	public void setTime(int time) {
		this.time = time;
	}
	
	

}
