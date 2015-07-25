package sim.tv2.no.tippeligaen.fotballxtra.player;

public class Player implements Comparable<String> {
	
	private int yellowCards, matches;
	private String name, team, number;
	private String average;
	
	public Player(String number, String name, String team, int yellowCards, int matches, String average) {
		this.number = number;
		this.name = name;
		this.team = team;
		this.yellowCards = yellowCards;
		this.matches = matches;
		this.average = average;
	}
	
	public String toString() {
		return this.name + ", " + this.yellowCards + " gule";
	}

	/**
	 * @return the number
	 */
	public String getNumber() {
		return number;
	}

	/**
	 * @param number the number to set
	 */
	public void setNumber(String number) {
		this.number = number;
	}

	/**
	 * @return the yellowCards
	 */
	public int getYellowCards() {
		return yellowCards;
	}

	/**
	 * @param yellowCards the yellowCards to set
	 */
	public void setYellowCards(int yellowCards) {
		this.yellowCards = yellowCards;
	}

	/**
	 * @return the matches
	 */
	public int getMatches() {
		return matches;
	}

	/**
	 * @param matches the matches to set
	 */
	public void setMatches(int matches) {
		this.matches = matches;
	}

	/**
	 * @return the average
	 */
	public String getAverage() {
		return average;
	}

	/**
	 * @param average the average to set
	 */
	public void setAverage(String average) {
		this.average = average;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the team
	 */
	public String getTeam() {
		return team;
	}

	/**
	 * @param team the team to set
	 */
	public void setTeam(String team) {
		this.team = team;
	}

	@Override
	public int compareTo(String o) {
		return this.team.compareTo(o);
	}
	
	

	

}
