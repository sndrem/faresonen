package sim.tv2.no.tippeligaen.fotballxtra.parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import sim.tv2.no.tippeligaen.fotballxtra.actionListeners.EventHandler;
import sim.tv2.no.tippeligaen.fotballxtra.gui.Gui;
import sim.tv2.no.tippeligaen.fotballxtra.match.Match;
import sim.tv2.no.tippeligaen.fotballxtra.player.Player;

public class DangerZoneParser {

	private Gui gui;
	private List<Player> players;
	private HashSet<String> teamNames;
	private List<String> teams;
	private List<Match> matchList;
	
	public static void main(String[] args) {
		DangerZoneParser parser = new DangerZoneParser();
//		parser.getNextMatches("http://www.altomfotball.no/element.do?cmd=tournament&tournamentId=1");
		
	}
	
	
	public DangerZoneParser() {
		players = new ArrayList<Player>();
		teamNames = new HashSet<String>();
		matchList = new ArrayList<Match>();
		teams = new ArrayList<String>();
//		getNextMatches("http://www.altomfotball.no/element.do?cmd=tournament&tournamentId=1");
				
		for(Match m : matchList) {
			System.out.println(m);
		}
		
		gui = Gui.getInstance();
		setupActionListeners();
		
	}
	
	private void setupActionListeners() {
		EventHandler e = new EventHandler(this);
		gui.getObosButton().addActionListener(e);
		gui.getTippeligaButton().addActionListener(e);
		gui.getCopyButton().addActionListener(e);
		gui.getSearchButton().addActionListener(e);
	}
	
	
	public void getNextMatches(String url) {
		try {
			Document doc = Jsoup.connect(url).get();
			Elements nextMatches = doc.getElementsByAttributeValue("id", "sd_fixtures_table_next");
			
			Elements matches = nextMatches.get(0).getElementsByTag("tr");
		
			for(Element match : matches) {
				String date = match.getElementsByClass("sd_fixtures_date").text();
//				String firstDate = matches.get(2).getElementsByClass("sd_fixtures_date").text();
				String round = match.getElementsByClass("sd_fixtures_round").text();
				String tournament = match.getElementsByClass("sd_fixtures_tournament").text();
				String homeTeam = match.getElementsByClass("sd_fixtures_home").text();
				String awayTeam = match.getElementsByClass("sd_fixtures_away").text();
				String time = match.getElementsByClass("sd_fixtures_score").text();
				String channels = match.getElementsByClass("sd_fixtures_channels").text();
				
				String matchUrl = "http://altomfotball.no/" + match.getElementsByClass("sd_fixtures_score").tagName("a").attr("href");
				Document matchPage = Jsoup.connect(matchUrl).get();
				
				// TODO Hent ut bare dommeren
				Elements arenas = matchPage.select(".sd_game_small").select(".sd_game_home");
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
						
				// Has to check for non breaking space if several matches are played on the same date
				if(date.equalsIgnoreCase("\u00a0")) {
					Match matchToList = new Match(date, homeTeam, awayTeam, tournament, time.split(" ")[0], channels, round);
					matchToList.setArena(arenaText[0] + " " + arenaText[1]);
					matchToList.setReferee(referee);
					matchList.add(matchToList);
				} else {
					Match matchToList = new Match(date, homeTeam, awayTeam, tournament, time.split(" ")[0], channels, round);
					matchList.add(matchToList);
					matchToList.setArena(arenaText[0] + " " + arenaText[1]);
					matchToList.setReferee(referee);
				}
			}
			
						
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void searchPlayer(String searchText) {
		if(players.size() < 1) {
			JOptionPane.showMessageDialog(gui, "Last ned spillere før du kan søke");
		} else {
			for(Player play : players) {
				if(play.getName().toLowerCase().contains(searchText)) {
					System.out.println(play.getName() + " er i faresonen med " + play.getYellowCards() + " gule kort.");
				} else {
					
				}
			}
		}
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
				teamNames.add(team);
				Player player = new Player(number, name, team, yellowCards, matches, average);
				players.add(player);
			}
			
			Collections.sort(players, new Comparator<Player>() {
		        @Override
		        public int compare(Player player1, Player player2){

		            return  player1.getTeam().compareTo(player2.getTeam());
		        }
		    });
			
			teams = new ArrayList<String>();
			for(String t : teamNames) {
				teams.add(t);
			}

			
			Collections.sort(teams);
			
			for(String teamName : teams) {
				information += "<br/><b>" + teamName + "</b>";
				for(Player play : players) {
					if(isEvenNumber(play.getYellowCards()) && play.getTeam().equalsIgnoreCase(teamName)){
						information += "<br/>" + play;
					}
				}
				information += "<br/>";
			}
	
			gui.getInfoLabel().setText(teams.size() + " lag og " + players.size() + " spillere ble hentet");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return information;
	}
	
	
	public void reset() {
		teamNames.clear();
		teams.clear();
		players.clear();
	}
	
	public boolean isEvenNumber(int number) {
		if((number % 2) == 0) {
			return true;
		} else return false;
	}
	
	
	

}
