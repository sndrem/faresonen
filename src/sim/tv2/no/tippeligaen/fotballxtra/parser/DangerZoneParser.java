package sim.tv2.no.tippeligaen.fotballxtra.parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import sim.tv2.no.tippeligaen.fotballxtra.match.Match;
import sim.tv2.no.tippeligaen.fotballxtra.player.Player;

public class DangerZoneParser {

	private List<Player> players;
	private HashSet<String> teamNames;
	private List<String> teams;
	private List<Match> matchList;
	
	public static void main(String[] args) {
		new DangerZoneParser();

	}
	
	
	public DangerZoneParser() {
//		dbConn = DatabaseConnection.getInstance();
		setPlayers(new ArrayList<Player>());
		setTeamNames(new HashSet<String>());
		setMatchList(new ArrayList<Match>());
		setTeams(new ArrayList<String>());
				
		
	}
	
	
	public List<Match> getNextMatches(String url) {
		try {
			Document doc = Jsoup.connect(url).get();
			Elements nextMatches = doc.getElementsByAttributeValue("id", "sd_fixtures_table_next");
			
			Elements matches = nextMatches.get(0).getElementsByTag("tr");
		
			for(Element match : matches) {

				String round = match.getElementsByClass("sd_fixtures_round").text();
				String tournament = match.getElementsByClass("sd_fixtures_tournament").text();
				String homeTeam = match.getElementsByClass("sd_fixtures_home").text();
				String awayTeam = match.getElementsByClass("sd_fixtures_away").text();
				String time = match.getElementsByClass("sd_fixtures_score").text();
				String channels = match.getElementsByClass("sd_fixtures_channels").text();
				
				String matchUrl = "http://altomfotball.no/" + match.getElementsByClass("sd_fixtures_score").tagName("a").attr("href");
				Document matchPage = Jsoup.connect(matchUrl).get();
				
				// TODO Hent bare ut dommeren
				Elements arenas = matchPage.select(".sd_game_small").select(".sd_game_home");
				Elements roundAndDate = matchPage.select(".sd_game_small").select(".sd_game_away");
				// Get the date for the game
				String date = roundAndDate.text().split(" ")[4];
				Element matchDetails = matchPage.getElementById("sd_match_details");
				Elements refs = matchDetails.select("#sd_match_details > table > tbody > tr > td:nth-child(2) > p");
				
				Pattern regexPattern = Pattern.compile("(.{1}).*?Assistentdommere:");
				Matcher regexMatcher = regexPattern.matcher(refs.text());
				
				String referee = "";
				while(regexMatcher.find()) {
					if(regexMatcher.group().length() != 0) {
						referee = regexMatcher.group().replaceAll(" Assistentdommere:", "");
					}
				}
								
				String[] arenaText = null;
				for(Element arena : arenas) {
					arenaText = arena.text().split(" ");
				}

				Match matchToList = new Match(date, homeTeam, awayTeam, tournament, time.split(" ")[0], channels, round);
				String arena = "";
				for(String s : arenaText) {
					if(!s.equals("Kampen")) {
						arena += s + " ";
					} else break;
				}
				matchToList.setArena(arena);
				matchToList.setReferee(referee);
				getMatchList().add(matchToList);
				
//				Uncomment to add match to database
//				dbConn.addMatch(matchToList);
			}
			
						
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return matchList;
	}
	
	
	public String getDangerZonePlayers(String url) {
		String information = "";
		try {
			Document doc = Jsoup.connect(url).get();
			Elements playerTable = doc.getElementsByTag("tbody");
			Elements playerRows = playerTable.get(0).getElementsByTag("tr");
			
			for(Element e : playerRows) {
				String[] playerData = e.text().split(" ");
				String number = playerData[0];
				String name = playerData[1];
				String team = playerData[2];
				if(team.contains("Kristiansund")) {
					team = "Kristiansund BK";
				} else if (team.contains("Sandnes")) {
					team = "Sandnes Ulf";
				} else if (team.contains("Sarpsborg")) {
					team = "Sarpsborg 08";
				}
				
				int yellowCards = Integer.parseInt(playerData[3]);
				int matches = Integer.parseInt(playerData[4]);
				String average = playerData[5];
				getTeamNames().add(team);
				Player player = new Player(number, name, team, yellowCards, matches, average);
				if(isEvenNumber(player.getYellowCards())) {
					getPlayers().add(player);
				}
			}
			
			Collections.sort(getPlayers(), new Comparator<Player>() {
		        @Override
		        public int compare(Player player1, Player player2){

		            return  player1.getTeam().compareTo(player2.getTeam());
		        }
		    });
			
			setTeams(new ArrayList<String>());
			for(String t : getTeamNames()) {
				getTeams().add(t);
			}

			
			Collections.sort(getTeams());
			
			for(String teamName : getTeams()) {
				information += "<br/><b>" + teamName + "</b>";
				for(Player play : getPlayers()) {
					if(play.getTeam().equalsIgnoreCase(teamName)){
						information += "<br/>" + play;
					}
				}
				information += "<br/>";
			}
	
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return information;
	}
	
	
	public void reset() {
		getTeamNames().clear();
		getTeams().clear();
		getPlayers().clear();
	}
	
	public boolean isEvenNumber(int number) {
		if((number % 2) == 0) {
			return true;
		} else return false;
	}


	/**
	 * @return the teams
	 */
	public List<String> getTeams() {
		return teams;
	}


	/**
	 * @param teams the teams to set
	 */
	public void setTeams(List<String> teams) {
		this.teams = teams;
	}


	/**
	 * @return the teamNames
	 */
	public HashSet<String> getTeamNames() {
		return teamNames;
	}


	/**
	 * @param teamNames the teamNames to set
	 */
	public void setTeamNames(HashSet<String> teamNames) {
		this.teamNames = teamNames;
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


	/**
	 * @return the matchList
	 */
	public List<Match> getMatchList() {
		return matchList;
	}


	/**
	 * @param matchList the matchList to set
	 */
	public void setMatchList(List<Match> matchList) {
		this.matchList = matchList;
	}
}
