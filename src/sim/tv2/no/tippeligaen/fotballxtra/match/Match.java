package sim.tv2.no.tippeligaen.fotballxtra.match;


public class Match implements Comparable<Match> {

	private String matchDate, homeTeam, awayTeam, tournament, time, channels, referee, arena, matchUrl, round;
	
	
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
		
		String dateOrTime = "";
		String hasBeenPlayedOnTv = "";
		if(this.time.contains("-")) {
			dateOrTime = "<br>Resultat: ";
			hasBeenPlayedOnTv = "Kampen så du på ";
		} else {
			dateOrTime = " Kl. ";
			hasBeenPlayedOnTv = "Kampen ser du på ";
		}
		
		String info = "<b>" + round + "</b> - <em>" + matchDate + "</em>" 
				+ "<br>" + "<span style=\"font-size:15px;\">" + homeTeam + " <span style=\"color:red;\">VS</span> " + awayTeam 
				+ dateOrTime + time 
				+ "<br>" + arena +"</span><br>";
		
		// Hvis kampen går på en kanal, vis kanalen også
		if(this.channels.length() > 2) {
			info += hasBeenPlayedOnTv + channels;
		}
		
		if(this.referee != "") {
			String[] boldText = this.referee.split(":");
			info += "<br><b>" + boldText[0] + ":</b>";
			for (int i = 1; i < boldText.length; i++) {
				info += boldText[i] + " ";
			}
		}
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
	public int compareTo(Match o) {
		Match matchToCompare = o;
		return this.matchDate.compareTo(matchToCompare.getMatchDate());
	}
}
