package sim.tv2.no.tippeligaen.fotballxtra.parser;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import sim.tv2.no.tippeligaen.fotballxtra.HTMLParser.HtmlConverter;
import sim.tv2.no.tippeligaen.fotballxtra.actionListeners.EventHandler;
import sim.tv2.no.tippeligaen.fotballxtra.gui.Gui;
import sim.tv2.no.tippeligaen.fotballxtra.player.Player;

public class DangerZoneParser {

	private Gui gui;
	private List<Player> players;
	private HashSet<String> teamNames;
	private List<String> teams;
	private HtmlConverter parser;
	
	public static void main(String[] args) {
		DangerZoneParser parser = new DangerZoneParser();
		parser.getNextMatches("http://www.altomfotball.no/element.do?cmd=tournament&tournamentId=1");
		
		
	}
	
	
	public DangerZoneParser() {
		players = new ArrayList<Player>();
		teamNames = new HashSet<String>();
//		gui = Gui.getInstance();
//		setupActionListeners();
		parser = new HtmlConverter();
	}
	
	private void setupActionListeners() {
		EventHandler e = new EventHandler(this);
		gui.getObosButton().addActionListener(e);
		gui.getTippeligaButton().addActionListener(e);
		gui.getCopyButton().addActionListener(e);
	}
	
	
	public void getNextMatches(String url) {
		try {
			Document doc = Jsoup.connect(url).get();
			Elements nextMatches = doc.getElementsByAttributeValue("id", "sd_fixtures_table_next");
//			System.out.println(nextMatches);
			
			Elements matches = nextMatches.get(0).getElementsByTag("tr");
//			Elements matchRound = nextMatches.get(0).getElementsByClass("sd_fixtures_round");
//			Elements matchTournament = nextMatches.get(0).getElementsByClass("sd_fixtures_tournament");
//			Elements matchHomeTeam = nextMatches.get(0).getElementsByClass("sd_fixtures_home");
//			Elements matchTime = nextMatches.get(0).getElementsByClass("sd_fixtures_score");
//			Elements matchAwayTeam = nextMatches.get(0).getElementsByClass("sd_fixtures_away");
//			Elements matchChannel = nextMatches.get(0).getElementsByClass("sd_fixtures_channel");
			
			System.out.println(matches);
						
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
