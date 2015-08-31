package sim.tv2.no.tippeligaen.fotballxtra.match;

public class Match implements Comparable {

	private String matchDate, homeTeam, awayTeam, tournament, time, channels, round, referee, arena, matchUrl; 
	
	
	// TODO Lagre url for kampene slik at vi kan direkte til de



	/**
	 * @return the matchDate
	 */
	public String getMatchDate() {
		return matchDate;
	}


	public Match(String matchDate, String homeTeam, String awayTeam,
			String tournament, String time, String channels, String round) {
		this.matchDate = matchDate;
		this.homeTeam = homeTeam;
		this.awayTeam = awayTeam;
		this.tournament = tournament;
		this.time = time;
		this.channels = channels;
		this.round = round;
	}


	/**
	 * @param matchDate the matchDate to set
	 */
	public void setMatchDate(String matchDate) {
		this.matchDate = matchDate;
	}

	/**
	 * @return the homeTeam
	 */
	public String getHomeTeam() {
		return homeTeam;
	}

	/**
	 * @param homeTeam the homeTeam to set
	 */
	public void setHomeTeam(String homeTeam) {
		this.homeTeam = homeTeam;
	}

	/**
	 * @return the awayTeam
	 */
	public String getAwayTeam() {
		return awayTeam;
	}

	/**
	 * @param awayTeam the awayTeam to set
	 */
	public void setAwayTeam(String awayTeam) {
		this.awayTeam = awayTeam;
	}

	/**
	 * @return the tournament
	 */
	public String getTournament() {
		return tournament;
	}

	/**
	 * @param tournament the tournament to set
	 */
	public void setTournament(String tournament) {
		this.tournament = tournament;
	}

	/**
	 * @return the time
	 */
	public String getTime() {
		return time;
	}

	/**
	 * @param time the time to set
	 */
	public void setTime(String time) {
		this.time = time;
	}

	/**
	 * @return the channels
	 */
	public String getChannels() {
		return channels;
	}

	/**
	 * @param channels the channels to set
	 */
	public void setChannels(String channels) {
		this.channels = channels;
	}

	/**
	 * @return the referee
	 */
	public String getReferee() {
		return referee;
	}

	/**
	 * @param referee the referee to set
	 */
	public void setReferee(String referee) {
		this.referee = referee;
	}

	/**
	 * @return the arena
	 */
	public String getArena() {
		return arena;
	}

	/**
	 * @param arena the arena to set
	 */
	public void setArena(String arena) {
		this.arena = arena;
	}
	
	public String toString() {
		String info = "Den " + matchDate + " på " + arena + " skal " + homeTeam + " spille " + round + " mot " + awayTeam + " kl. " + time + ". Kampen ser du på " + channels;
		return info;
	}

	/**
	 * @return the round
	 */
	public String getRound() {
		return round;
	}

	/**
	 * @param round the round to set
	 */
	public void setRound(String round) {
		this.round = round;
	}

	/**
	 * @return the matchUrl
	 */
	public String getMatchUrl() {
		return matchUrl;
	}

	/**
	 * @param matchUrl the matchUrl to set
	 */
	public void setMatchUrl(String matchUrl) {
		this.matchUrl = matchUrl;
	}


	@Override
	public int compareTo(Object o) {
		Match matchToCompare = (Match) o;
		return this.matchDate.compareTo(matchToCompare.getMatchDate());
	}
}
