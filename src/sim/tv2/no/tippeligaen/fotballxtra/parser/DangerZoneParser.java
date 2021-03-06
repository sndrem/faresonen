package sim.tv2.no.tippeligaen.fotballxtra.parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import sim.tv2.no.tippeligaen.fotballxtra.goal.Event;
import sim.tv2.no.tippeligaen.fotballxtra.main.Main;
import sim.tv2.no.tippeligaen.fotballxtra.match.Match;
import sim.tv2.no.tippeligaen.fotballxtra.player.Player;
import sim.tv2.no.tippeligaen.fotballxtra.player.Topscorer;
import sim.tv2.no.tippeligaen.fotballxtra.team.Team;

public class DangerZoneParser {

	private List<Player> players;
	private HashSet<String> teamNames;
	private HashMap<String, Team> teams;
	private List<Match> matchList;


	public DangerZoneParser() {
		setPlayers(new ArrayList<Player>());
		setTeamNames(new HashSet<String>());
		setMatchList(new ArrayList<Match>());
		setTeams(new HashMap<String, Team>());
	}

	/**
	 * Method to retrieve the next matches for a league based on an url
	 * @param url the url for the league from altomfotball.no
	 * @return a list with the next matches
	 * @throws IndexOutOfBoundsException
	 */
	public List<Match> getNextMatches(String url) throws IndexOutOfBoundsException , IOException{
		List<Player> homeScorers = null;
		List<Player> awayScorers = null;
		try {
			Document doc = Jsoup.connect(url).get();
			Elements matches = doc.getElementsByTag("tr");

			for(Element match : matches) {
				String round = match.getElementsByClass("sd_fixtures_round").text();
				String tournament = match.getElementsByClass("sd_fixtures_tournament").text();
				String homeTeam = match.getElementsByClass("sd_fixtures_home").text().replace(Main.NBSP, " ");
				String awayTeam = match.getElementsByClass("sd_fixtures_away").text().replace(Main.NBSP, " ");
				String time = match.getElementsByClass("sd_fixtures_score").text();
				String channels = match.getElementsByClass("sd_fixtures_channels").text();

				String matchUrl = "http://altomfotball.no/" + match.getElementsByClass("sd_fixtures_score").tagName("a").attr("href");
				Document matchPage = Jsoup.connect(matchUrl).get();


				// TODO Finn målscorere
				Elements typeOfTable = matchPage.select("#sd_match_stats table");
				String summary = typeOfTable.attr("summary");
				if(summary.equalsIgnoreCase("Kampfakta")) {
					Elements events = matchPage.select("#sd_match_stats tr");
					homeScorers = new ArrayList<Player>();
					awayScorers = new ArrayList<Player>();
					for(Element event : events) {
						Element homeTeamEvent = event.children().first();
						Element timeCode = null;
						try {
							timeCode = event.children().get(1);
						} catch(IndexOutOfBoundsException e) {
							System.out.println(e.getMessage());
						}
						Element awayTeamEvent = event.children().last();
						extractGoalScorers(homeScorers, homeTeamEvent, timeCode);
						extractGoalScorers(awayScorers, awayTeamEvent, timeCode);
					}
				}

				String arena = extractArena(matchPage);
				String date = extractMatchDate(matchPage);
				String referee = extractReferee(matchPage);


				Match matchToList = new Match(date, homeTeam, awayTeam, tournament, time.split(" ")[0], channels, round);
				matchToList.setHomeScorers(homeScorers);
				matchToList.setAwayScorers(awayScorers);

				matchToList.setArena(arena);
				matchToList.setReferee(referee);
				getMatchList().add(matchToList);

			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IOException("Kunne ikke hente neste kamper for" + url + ". Er du koblet til internett?");
		}
		return matchList;
	}

	private String extractReferee(Document matchPage) {
		Element matchDetails = matchPage.getElementById("sd_match_details");
		Elements refs = matchDetails.select("#sd_match_details > table > tbody > tr > td:nth-child(2) > p");

		Pattern regexPattern = Pattern.compile("(.{1}).*?Assistentdommere:");
		Matcher regexMatcher = regexPattern.matcher(refs.text());

		// Hent ut dommeren
		String referee = "";
		while(regexMatcher.find()) {
			if(regexMatcher.group().length() != 0) {
				referee = regexMatcher.group().replaceAll(" Assistentdommere:", "");
			}
		}
		return referee;
	}

	private String extractMatchDate(Document matchPage) {
		Elements roundAndDate = matchPage.select(".sd_game_small").select(".sd_game_away");
		// Get the date for the game
		String[] dateArray = roundAndDate.text().split(" ");	
		String date = dateArray[dateArray.length - 3];
		return date;
	}

	private String extractArena(Document matchPage) {
		Elements elements = matchPage.select(".sd_game_small").select(".sd_game_home");
		String[] arenaText = null;
		for(Element arena : elements) {
			arenaText = arena.text().split(" ");
		}

		String arena = "";
		for(String s : arenaText) {
			if(!s.equals("Kampen")) {
				arena += s + " ";
			} else break;
		}
		return arena;
	}

	/**
	 * Method to get the goal scorers for a given game
	 * @param homeScorers
	 * @param homeTeamEvent
	 * @param timeCode
	 */
	private void extractGoalScorers(List<Player> scorers, Element event,
			Element timeCode) {
		boolean status = false;
		if(!event.text().equalsIgnoreCase(Main.NBSP)) {
			if(event.childNodeSize() > 0) {
				try {
					status = isGoalScorerElement(event.child(0).attr("style"));
				} catch (IndexOutOfBoundsException exe) {

				}
				if(status) {
					String name = event.text().replace(Main.NBSP, " ");
					Player player = new Player("", name.trim(), "", 0, 0, "");
					int eventTime = 0;
					try {
						eventTime = Integer.parseInt(timeCode.text());
					} catch(NumberFormatException e) {

					}
					player.getEventList().add(new Event(player.getName(), eventTime));
					scorers.add(player);
				}
			}
		}
	}

	/**
	 * Method to check if a text string contains symbols related to goals scorers from alt om fotball
	 * @return true if the string marks a goal scorer, false otherwise
	 */
	private boolean isGoalScorerElement(String symbolPath) {
		if(symbolPath.contains("graphics/eventIcon3/3")) {
			return true;
		} else if(symbolPath.contains("graphics/eventIcon3/6")) {
			return true;
		} else if(symbolPath.contains("graphics/eventIcon3/7")) {
			return true;
		} else {
			return false;
		}
	}


	/**
	 * Method to get players in the danger zone
	 * @param url the url for the league
	 * @return a string with information about the 
	 */
	public String getDangerZonePlayers(String url) throws IOException {
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

			setTeams(new HashMap<String, Team>());
			for(String t : getTeamNames()) {
				getTeams().put(t, new Team(t));
				for(int i = 0; i < getPlayers().size(); i++) {
					Player player = getPlayers().get(i);
					if(player.getTeam().equalsIgnoreCase(t)) {
						getTeams().get(t).addPlayer(player);
					}
				}
			}

			ArrayList<Team> teamList = new ArrayList<Team>(getTeams().values());
			Collections.sort(teamList);

			for(Team team : teamList) {
				if(team.getPlayers().size() > 0) {
					information += "<br/><b>" + team.getTeamName() + "</b>";
					for(Player play : getPlayers()) {
						if(play.getTeam().equalsIgnoreCase(team.getTeamName())){
							information += "<br/>" + play;
						}
					}
					information += "<br/>";
				}
			}



		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IOException("Kunne ikke hente spillere i faresonen. Er du koble til internett?");
		}

		return information;
	}

	/**
	 * Method to get top 10 topscorers for a given league based on the url
	 * @param url - the url for the league
	 * @throws IOException
	 */
	public List<Topscorer> getTopscorers(String url) throws IOException {
		List<Topscorer> topscorers = new ArrayList<Topscorer>();
		try {
			Document doc = Jsoup.connect(url).get();
			Elements playerRow = doc.select("tbody tr");
			for (int i = 0; i < 10; i++) {
				String playerName = playerRow.get(i).child(1).text();
				String team = playerRow.get(i).child(2).text();
				int goals = Integer.parseInt(playerRow.get(i).child(3).text());
				int numMatches = Integer.parseInt(playerRow.get(i).child(4).text());
				String avg = playerRow.get(i).child(5).text().replace(",", ".");;
				double goalAvg = Double.parseDouble(avg);

				topscorers.add(new Topscorer(playerName, team, goals, numMatches, goalAvg));
			}
		} catch(IOException e) {
			e.printStackTrace();
			throw new IOException("Kunne ikke hente toppscorere. Er du koblet til internett?");
		}

		return topscorers;
	}

	/**
	 * Method to get the table for a given league based on the url
	 * @param url - the url for the league
	 * @throws IOEXception
	 */
	public Element getTable(String url) {
		Element table = null;
		try {
			Document doc = Jsoup.connect(url).get();
			table = doc.getElementById("sd_table_wrap").child(0);
			for(Element previousMatches : table.getElementsByClass("sd_left")) {
				try {
					previousMatches.remove();
				} catch(NullPointerException e) {
					e.printStackTrace();
				}
			}


		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return table;
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
	public HashMap<String, Team> getTeams() {
		return teams;
	}


	/**
	 * @param teams the teams to set
	 */
	public void setTeams(HashMap<String, Team> teams) {
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
