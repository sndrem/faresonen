package sim.tv2.no.tippeligaen.fotballxtra.parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import sim.tv2.no.tippeligaen.fotballxtra.player.Player;
import sim.tv2.no.tippeligaen.fotballxtra.scanner.InputScanner;

public class DangerZoneParser {

	private List<Player> players;
	private HashSet<String> teamNames;
	private List<String> teams;
	
	public DangerZoneParser() {
		players = new ArrayList<>();
		teamNames = new HashSet<>();
	}

	
	public void getDangerZonePlayers(String url) {
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
			
			teams = new ArrayList<>();
			for(String t : teamNames) {
				teams.add(t);
			}
			System.out.println(teams.size() + " lag ble hentet...");
			
			Collections.sort(teams);
			
			for(String teamName : teams) {
				System.out.println("\n" + teamName);
				for(Player play : players) {
					if(isEvenNumber(play.getYellowCards()) && play.getTeam().equalsIgnoreCase(teamName)){
						System.out.println(play);
					}
				}
			}
	
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void resetTeamList() {
		players.clear();
	}
	
	public void resetTeamNames() {
		teamNames.clear();
		teams.clear();
	}
	
	public boolean isEvenNumber(int number) {
		if((number % 2) == 0) {
			return true;
		} else return false;
	}
	
	
	
	public static void main(String[] args) {
		DangerZoneParser parser = new DangerZoneParser();
		String tippeligaen = "http://www.altomfotball.no/elementsCommonAjax.do?cmd=statistics&subCmd=yellowCards&tournamentId=1&seasonId=&teamId=";
		String obosligaen = "http://www.altomfotball.no/elementsCommonAjax.do?cmd=statistics&subCmd=yellowCards&tournamentId=2&seasonId=&teamId=";
		
		InputScanner input = new InputScanner();
		String response = input.input();
		boolean isFinished = false;
		
		while(!isFinished) {
			if(response.equalsIgnoreCase("tippeligaen")) {
				parser.getDangerZonePlayers(tippeligaen);
				parser.resetTeamList();
				parser.resetTeamNames();
				isFinished = true;
				
			} else if (response.equalsIgnoreCase("obosligaen")) {
				parser.getDangerZonePlayers(obosligaen);
				parser.resetTeamList();
				parser.resetTeamNames();
				isFinished = true;
			} else if(response.equalsIgnoreCase("avslutt")) {
				isFinished = true;
			}
		}
		
	}

}
