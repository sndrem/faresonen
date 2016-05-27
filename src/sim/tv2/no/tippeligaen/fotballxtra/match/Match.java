package sim.tv2.no.tippeligaen.fotballxtra.match;

import java.util.ArrayList;
import java.util.List;

import sim.tv2.no.tippeligaen.fotballxtra.main.Main;
import sim.tv2.no.tippeligaen.fotballxtra.player.Player;


public class Match implements Comparable<Match> {

	private String matchDate, homeTeam, awayTeam, tournament, time, channels, referee, arena, matchUrl, round;
	private boolean isPlayed = false;
	private List<Player> homeScorers, awayScorers;
	
	
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
		
		if(this.time.contains("-")) {
			this.setPlayed(true);
		}
		this.setHomeScorers(new ArrayList<Player>());
		this.setAwayScorers(new ArrayList<Player>());
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
	
	/**
	 * Returns a formatted text string if the match has already been played. If the match has been played, we do not care about which channel etc...
	 * @return a formatted text string
	 */
	public String getMatchIsPlayedInfo() {
		String info = "<p style=\"font-weight: bold; text-align:center;\">" + homeTeam + " " + time + " " + awayTeam + "</p>"
				+ "<p style=\"text-align:left;\"><span style=\"font-weight:bold;\">" + Main.getAbbreviation(homeTeam) + ": </span>"
				+ "<span style=\"font-weight: normal;\">";
				
				for(Player player : getHomeScorers()) {
					info += getCorrectEndGoalInfoString(getHomeScorers().size(), player);
				}
			info += "</span></p>"
				+ "<p style=\"text-align:left; border-bottom: 1px solid black;\"><span style=\"font-weight:bold;\">" + Main.getAbbreviation(awayTeam) + ": </span>"
				+ "<span style=\"font-weight: normal;\">";
				for(Player player : getAwayScorers()) {
					info += getCorrectEndGoalInfoString(getAwayScorers().size(), player);
				}
				
			info += "</span></p>";
		
		return info;
	}
	
	// TODO Finn på et bedre navn for denne metoden
	public String getCorrectEndGoalInfoString(int size, Player player) {
		if(size > 1) {
			return player.getGoalString() + ", ";
		} else return player.getGoalString() +  " ";
	}
	
	/**
	 * Returns match info if the match has not been played before
	 * @return a formatted text string of the match has not been played
	 */
	public String getMatchInfo() {
		String hasBeenPlayedOnTv = "";
		String info = "";
		
		// Hvis kampen går på en kanal, vis kanalen også
		if(this.channels.length() > 2) {
			hasBeenPlayedOnTv += "<span style=\"text-align:center;\">" + this.channels + "</span>";
		}
		
//			if(this.referee != "") {
//				String[] boldText = this.referee.split(":");
//				info += "<br><b>" + boldText[0] + ":</b>";
//				for (int i = 1; i < boldText.length; i++) {
//					info += boldText[i] + " ";
//				}
//			}
		
		info = 	"Avspark kl. " + this.time + " " + hasBeenPlayedOnTv + "<br>"
				+ "<b style=\"font-size:11px;\">" + homeTeam + " - " + awayTeam + ", " + this.arena + "</b><br>"
				+ "<p style=\"text-align:right; border-bottom: 1px solid black;\"<b>Reporter: </b><br>"
				+ "<b>Kommentator(er): </b><br>"
				+ "<b>Arenaekspert: </b><br>"
				+ "<b>Studioekspert: </b><br>"
				+ "<b>Dommer: <span style=\"font-weight:normal;\">" + this.referee + "</span></p>";			
		return info;
	}
	
	/**
	 * Returns a formatted text string if the match is for the next round
	 * @return a formatted text string if the match is for the next round
	 */
	public String getNextRoundMatchInfo() {
		return  "<td>" + this.matchDate + "</td>"
				+ "<td>" + this.round + "<td>"
				+ "<td></td>"
				+ "<td>" + toString() + "</td>"
				+ "<td>" + this.channels + "</td>";
	}
		
	public String toString() {
		return this.homeTeam + " " + this.time + " " + this.awayTeam;
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


	/**
	 * @return the isPlayed
	 */
	public boolean isPlayed() {
		return isPlayed;
	}


	/**
	 * @param isPlayed the isPlayed to set
	 */
	public void setPlayed(boolean isPlayed) {
		this.isPlayed = isPlayed;
	}


	/**
	 * @return the homeScorers
	 */
	public List<Player> getHomeScorers() {
		return homeScorers;
	}


	/**
	 * @param homeScorers the homeScorers to set
	 */
	public void setHomeScorers(List<Player> homeScorers) {
		this.homeScorers = homeScorers;
	}


	/**
	 * @return the awayScorers
	 */
	public List<Player> getAwayScorers() {
		return awayScorers;
	}


	/**
	 * @param awayScorers the awayScorers to set
	 */
	public void setAwayScorers(List<Player> awayScorers) {
		this.awayScorers = awayScorers;
	}

}
