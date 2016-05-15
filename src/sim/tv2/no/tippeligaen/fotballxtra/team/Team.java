package sim.tv2.no.tippeligaen.fotballxtra.team;

import java.util.ArrayList;
import java.util.List;

import sim.tv2.no.tippeligaen.fotballxtra.player.Player;

public class Team implements Comparable<Team> {
	
	private String teamName;
	private List<Player> players;
	
	public Team(String teamName) {
		this.teamName = teamName;
		players = new ArrayList<>();
	}
	
	public Team(String teamName, List<Player> players) {
		this.teamName = teamName;
		this.players = players;
	}

	/**
	 * @return the teamName
	 */
	public String getTeamName() {
		return teamName;
	}

	/**
	 * @param teamName the teamName to set
	 */
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	/**
	 * @return the players
	 */
	public List<Player> getPlayers() {
		return players;
	}

	/**
	 * @param players the players to set
	 */
	public void setPlayers(List<Player> players) {
		this.players = players;
	}

	@Override
	public int compareTo(Team team2) {
		return this.getTeamName().compareTo(team2.getTeamName());
	}

	public void addPlayer(Player player) {
		this.players.add(player);		
	}

}
