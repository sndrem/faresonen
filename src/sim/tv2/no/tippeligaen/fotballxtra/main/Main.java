package sim.tv2.no.tippeligaen.fotballxtra.main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.print.PrinterException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import org.jsoup.nodes.Element;

import sim.tv2.no.tippeligaen.fotballxtra.gui.Gui;
import sim.tv2.no.tippeligaen.fotballxtra.match.Match;
import sim.tv2.no.tippeligaen.fotballxtra.parser.DangerZoneParser;
import sim.tv2.no.tippeligaen.fotballxtra.parser.XMLParser;
import sim.tv2.no.tippeligaen.fotballxtra.player.Player;
import sim.tv2.no.tippeligaen.fotballxtra.player.Topscorer;
import sim.tv2.no.tippeligaen.fotballxtra.weather.Weather;

/**
 * Dette er hovedklassen for faresone-programmet.
 * Programmet henter spillere i faresonen for Tippeligaen og OBOSligaen og presenterer det grafisk slik at det kan bli
 * kopiert inn i Word eller et annet program.
 * Programmet kan også søke etter spillere som er i faresonen.
 */

public class Main {
	private Gui gui;
	private DangerZoneParser parser;
	private XMLParser weatherParser;
	private Map<String, String> leagueUrls;
	private Map<String, String> topscorerUrls;
	private Map<String, String> weatherUrls;
	private static Map<String, String> teamNames;
	private final String TIPPELIGAEN = "http://www.altomfotball.no/elementsCommonAjax.do?cmd=statistics&subCmd=yellowCards&tournamentId=1&seasonId=&teamId=";
	private final String OBOSLIGAEN = "http://www.altomfotball.no/elementsCommonAjax.do?cmd=statistics&subCmd=yellowCards&tournamentId=2&seasonId=&teamId=";
	public static final String NBSP = "\u00A0";
	private static final String VERSION = "1.0";
	private static final String AUTHOR = "Sindre Moldeklev";
	private static final String EMAIL = "sndrem@gmail.com";
	
	public static void main(String[] args) {
		Runnable r = new Runnable() {

			@Override
			public void run() {
				new Main();				
			}
		};
		SwingUtilities.invokeLater(r);
		
//		XMLParser parser = new XMLParser();
//		String url = "http://www.yr.no/sted/Norge/Oslo/Oslo/Ullevål_stadion/varsel.xml";
//		Weather weather = parser.parseUrl(url);
//		System.out.println(weather);
//		System.out.println();
	}

	
	/**
	 * Konstruktør for main-klassen
	 */
	public Main() {	
		parser = new DangerZoneParser();
		weatherParser = new XMLParser();
		gui = Gui.getInstance();
		leagueUrls = new HashMap<String, String>();
		topscorerUrls = new HashMap<String, String>();
		weatherUrls = new HashMap<String, String>();
		setTeamNames(new HashMap<String, String>());
		try {
			readTextFile(new File("lagnavn.txt"), getTeamNames(), "-");
			readTextFile(new File("stedsnavn.txt"), weatherUrls, "\t");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setupLeagueUrls();
		updateRoundSelector((String) gui.getLeagueUrls().getSelectedItem());
		setupActionListeners();
		
	}
	
	/**
	 * Metode for å sette opp combobox med league-urls
	 */
	private void setupLeagueUrls() {
		// For å hente neste kamper
		this.leagueUrls.put("Tippeligaen", "http://www.altomfotball.no/element.do?cmd=tournament&tournamentId=1&useFullUrl=false");
		this.leagueUrls.put("OBOS-ligaen", "http://www.altomfotball.no/element.do?cmd=tournament&tournamentId=2&useFullUrl=false");
		this.leagueUrls.put("Premier League", "http://www.altomfotball.no/element.do?cmd=tournament&tournamentId=230&useFullUrl=false");
		this.leagueUrls.put("Championship", "http://www.altomfotball.no/element.do?cmd=tournament&tournamentId=231&useFullUrl=false");
		
		for(String league : this.leagueUrls.keySet()) {
			gui.getLeagueUrls().addItem(league);
			gui.getTableDropdown().addItem(league);
		}

		// Url for toppscorerlister
		this.topscorerUrls.put("Tippeligaen", "http://www.altomfotball.no/elementsCommonAjax.do?cmd=statistics&subCmd=goals&tournamentId=1&seasonId=&teamId=&useFullUrl=false");
		this.topscorerUrls.put("Premier League", "http://www.altomfotball.no/elementsCommonAjax.do?cmd=statistics&subCmd=goals&tournamentId=230&seasonId=&teamId=&useFullUrl=false");
		this.topscorerUrls.put("OBOS-ligaen", "http://www.altomfotball.no/elementsCommonAjax.do?cmd=statistics&subCmd=goals&tournamentId=2&seasonId=&teamId=&useFullUrl=false");
		this.topscorerUrls.put("Championship", "http://www.altomfotball.no/elementsCommonAjax.do?cmd=statistics&subCmd=goals&tournamentId=231&seasonId=&teamId=&useFullUrl=false");
		
		for(String league : this.topscorerUrls.keySet()) {
			gui.getTopscorerDropdown().addItem(league);
		}
	}
	
	/**
	 * Metode for å legge til action-listener for knapper
	 */
	private void setupActionListeners() {
		EventHandler e = new EventHandler();
		gui.getGetObosligaenButton().addActionListener(e);
		gui.getGetTippeligaButton().addActionListener(e);
		gui.getSearchPlayerButton().addActionListener(e);
		gui.getLoadAllPlayersButton().addActionListener(e);
		gui.getClearSearchResultButton().addActionListener(e);
		gui.getGetMatchesButton().addActionListener(e);
		gui.getTopscorerButton().addActionListener(e);
		gui.getTableButton().addActionListener(e);
		gui.getPrintItem().addActionListener(e);
		gui.getInfoItem().addActionListener(e);
		gui.getWeatherButton().addActionListener(e);
		
		gui.getLeagueUrls().addItemListener(new ComboBoxEvent());
		gui.getRoundComboBox().addItemListener(new ComboBoxEvent());
	}
	
	/**
	 * Method for reading a text file given a specified delimited
	 * @param File file to read
	 * @param map the map you want to add to
	 * @param delimiter the delimiter for splitting a string
	 */
	private void readTextFile(File file, Map<String, String> map, String delimiter) throws IOException {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			String line = "";
			while((line = reader.readLine()) != null) {
				String[] items = line.split(delimiter);
				try {
					
					map.put(items[1].trim(), items[0].trim());
 				} catch(ArrayIndexOutOfBoundsException e) {
 					System.out.println(line + " kunne ikke bli splittet....");
 				}
			}

		} catch(IOException e) {
			throw e;
		} finally {
			try {
				reader.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Method to get the abbreviation of a teams name
	 * @param String teamName name of the team
	 * @return String the abbreviation if one is found, otherwise just return the teamname
	 */
	public static String getAbbreviation(String teamName) {
		String abbreviation = Main.teamNames.get(teamName);
		if(abbreviation != null) {
			return abbreviation;
		} else return teamName;
	}

	/**
	 * Metode for å vise kampene som brukeren ber om
	 * @param matches
	 */
	private void showMatches(List<Match> matches, boolean nextRound) {
		String summary = "";
		if(nextRound) {
			summary = "<table><thead>"
					+ "<tr><th align=\"left\" colspan=\"3\">Neste runde</th></tr>"
					+ "<tr>"
					+ "<th align=\"left\">Dato</th>"
					+ "<th align=\"left\">Kl.</th>"
					+ "<th align=\"left\">Runde</th>"
					+ "<th></th>"
					+ "<th></th>"
					+ "<th align=\"left\">Kamp</th>"
					+ "<th align=\"left\">Kanal</th>"
					+ "</tr>"
					+ "</thead></tbody>";
		}
		gui.getInfoLabel().setText("Hentet " + matches.size() + " kamper" );
		for(int i = 0; i < matches.size(); i++) {
			Match match = matches.get(i);
			
			if(!nextRound) {
				if(match.isPlayed()) {
					summary += match.getMatchIsPlayedInfo();
				} else {
					summary += match.getMatchInfo();
				} 
			} else if(nextRound) {
				summary += "<tr\">";
				summary += match.getNextRoundMatchInfo();
				summary += "</tr>";
			}
		}
		if(nextRound) {
			summary += "</tbody></table>";
		}
		gui.getSummaryEditorPane().setText(summary);
		gui.getSummaryEditorPane().setCaretPosition(0);
	}
	
	/**
	 * Metode for å søke etter en eller flere spillere
	 * @param searchText
	 * @return a set of players 
	 */
	public Set<Player> searchPlayer(String searchText) {
		Set<Player> playList = new HashSet<Player>();
		
		if(parser.getPlayers().size() < 1) {
			JOptionPane.showMessageDialog(gui.getFrame(), "Last ned spillere før du kan søke");
		} else {
			for(Player play : parser.getPlayers()) {
				if(play.getName().toLowerCase().contains(searchText)) {
					playList.add(play);
				} 
			}
		}
		return playList;
	}
	
	/**
	 * Method to show the players that a user has searched for
	 * @param players
	 * @param searchText
	 */
	public void showSearchPlayers(Set<Player> players, String searchText) {
		String info = "";
		if(players != null && players.size() > 0) {
			for(Player player : players) {
				info += "<span style=\"font-size: 15px; color: red;\">" + player.getName() + " er i faresonen med " + player.getYellowCards() + " gule kort.</span><span style=\"font-size: 15px; text-decoration: underline;\"> Han må stå over neste kamp.</span><br/>";
				info += "<br/>";
			}
			gui.getSummaryEditorPane().setText(info);
			gui.getSummaryEditorPane().setCaretPosition(0);
		} else {
			JOptionPane.showMessageDialog(gui.getFrame(), searchText + " er ikke i faresonen");
		}
	}
	
	/**
	 * method to show information to the gui
	 * @param info
	 */
	public void showInfo(String info) {
		gui.getSearchPlayerButton().setEnabled(true);
		gui.getDangerZoneEditorPane().setText(info);
		gui.getInfoLabel().setText(parser.getTeams().size() + " lag og " + parser.getPlayers().size() + " spillere ble hentet");
	}
	
	/**
	 * Method to show topscorers
	 */
	public void showTopscorers(List<Topscorer> players) {
		String html 	= "<table>"
						+ "<thead>"
						+ "<tr>"
						+ "<th></th>"
						+ "<th align=\"left\">Navn</th>"
						+ "<th align=\"left\">Lag</th>"
						+ "<th align=\"left\">Kamper</th>"
						+ "<th align=\"left\">Mål</th>"
						+ "<th align=\"left\">Målsnitt</th>"
						+ "</thead>"
						+ "<tbody>"
						+ "</tr>";
		int index = 1;
						for(Topscorer player : players) {
							html += "<tr>"
									+ "<td>" + index + "</td>"
									+ "<td>" + player.getName() + "</td>"
									+ "<td>" + player.getTeam() + "</td>"
									+ "<td>" + player.getMatches() + "</td>"
									+ "<td>" + player.getGoals() + "</td>"
									+ "<td>" + player.getGoalAvg() + "</td>"
									+ "</tr>";
						index++;
						}
				html 	+= "</tbody>"
						+ "</table>";
		gui.getSummaryEditorPane().setText(html);
		gui.getSummaryEditorPane().setCaretPosition(0);
	}
	
	/**
	 * Method to show the Table
	 * @parar Element JSoup element 
	 */
	public void showTable(Element tableElements) {
		gui.getSummaryEditorPane().setText(tableElements.toString());
		gui.getSummaryEditorPane().setCaretPosition(0);
	}
	
	/**
	 * Method to update the round combobox based upon wheter the rounds are for Tippeligaen, OBOS-ligaen, Premier League etc..
	 * @param String the type of league
	 */
	private void updateRoundSelector(String leagueType) {
		// Reset round selector first
		gui.getRoundComboBox().removeAllItems();
		int numOfRounds = 1;
		if(leagueType.equalsIgnoreCase("Tippeligaen")) {
			numOfRounds = 30;
		} else if(leagueType.equalsIgnoreCase("OBOS-ligaen")) {
			numOfRounds = 30;
		} else if (leagueType.equalsIgnoreCase("Premier League")) {
			numOfRounds = 38;
		} else if (leagueType.equalsIgnoreCase("Championship")) {
			numOfRounds = 46;
		}
		
		for(int i = 1; i <= numOfRounds; i++) {
			gui.getRoundComboBox().addItem(i);
		}
	}
	
	/**
	 * Method to return the correct url based on the league name and the round
	 */
	private String convertLeagueNameToUrl(String leagueName, Integer round) {
		if(leagueName.equalsIgnoreCase("Tippeligaen")) {
			return "http://www.altomfotball.no/elementsCommonAjax.do?cmd=fixturesContent&tournamentId=1&roundNo=" + round  + "&useFullUrl=false";
		} else if (leagueName.equalsIgnoreCase("OBOS-ligaen")) {
			return "http://www.altomfotball.no/elementsCommonAjax.do?cmd=fixturesContent&tournamentId=2&roundNo=" + round + "&useFullUrl=false";
		} else if (leagueName.equalsIgnoreCase("Premier League")) {
			return "http://www.altomfotball.no/elementsCommonAjax.do?cmd=fixturesContent&tournamentId=230&roundNo=" + round + "&useFullUrl=false";
		} else if (leagueName.equalsIgnoreCase("Championship")) {
			return "http://www.altomfotball.no/elementsCommonAjax.do?cmd=fixturesContent&tournamentId=231&roundNo=" + round + "&useFullUrl=false";
		} else return null;
	}
	
	
	

	/**
	 * @return the teamNames
	 */
	public Map<String, String> getTeamNames() {
		return teamNames;
	}


	/**
	 * @param teamNames the teamNames to set
	 */
	public void setTeamNames(Map<String, String> teamNames) {
		this.teamNames = teamNames;
	}




	/**
	 * Private class to deal with the event handling
	 * @author sindremoldeklev
	 *
	 */
private class EventHandler implements ActionListener {
		
		private boolean hasLoadedEveryone = false;
		
		public EventHandler() {

		}

		@Override
		public void actionPerformed(ActionEvent e) {
			
			// Hent faresonen for Tippeligaen
			if(e.getSource() == gui.getGetTippeligaButton()) {
				parser.reset();
				String info = null;
				try {
					info = parser.getDangerZonePlayers(TIPPELIGAEN);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					gui.showMessage(e1.getMessage());
				}
				showInfo(info);
			
			// Hent faresonen for OBOSligaen
			} else if (e.getSource() == gui.getGetObosligaenButton()) {
				parser.reset();
				String info = null;
				try {
					info = parser.getDangerZonePlayers(OBOSLIGAEN);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					gui.showMessage(e1.getMessage());
				}
				showInfo(info);
			
			// Søk etter en spiller
			} else if (e.getSource() == gui.getSearchPlayerButton()) {
				String searchText = gui.getSearchField().getText().trim();
				if(searchText.equalsIgnoreCase("")) {
					JOptionPane.showMessageDialog(gui.getFrame(), "Tomt søkefelt");
				} else {
					Set<Player> players = searchPlayer(searchText.trim());
					showSearchPlayers(players, searchText);
					gui.getSearchField().setText("");
				}
			
			// Fjern søkeresultater
			} else if (e.getSource() == gui.getClearSearchResultButton()) {
				gui.getSummaryEditorPane().setText("");
				parser.getMatchList().clear();
			} else if (e.getSource() == gui.getLoadAllPlayersButton()) {
				if(!this.hasLoadedEveryone) {
					parser.reset();
					try {
						parser.getDangerZonePlayers(TIPPELIGAEN);
						parser.getDangerZonePlayers(OBOSLIGAEN);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						gui.showMessage(e1.getMessage());
					}
					
					gui.getInfoLabel().setText("Tippeligaen og OBOS-ligaen er lastet");
					gui.getSearchPlayerButton().setEnabled(true);
					this.hasLoadedEveryone = true;
				} else {
					Object[] options = {"Ja", "Nei"};
					int choice = JOptionPane.showOptionDialog(gui.getFrame(), "Du har allerede lastet alle spillerne. Vil du laste på nytt?", "Laste spillere på nytt?", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
					if(choice == 1) {
						return;
					} else if (choice == 0) {
						parser.reset();
						try {
							parser.getDangerZonePlayers(TIPPELIGAEN);
							parser.getDangerZonePlayers(OBOSLIGAEN);
						} catch(IOException e2) {
							gui.showMessage(e2.getMessage());
						}
						
						gui.getInfoLabel().setText("Tippeligaen og OBOS-ligaen ble lastet på nytt");
						gui.getSearchPlayerButton().setEnabled(true);
						this.hasLoadedEveryone = true;
					}
				}
			
			// Hent kamper fra AltOmFotball
			} else if (e.getSource() == gui.getGetMatchesButton()) {
				// hent kampene
				parser.getMatchList().clear();
				List<Match> nextMatches = null;
				gui.getSummaryEditorPane().setText("");
								
				String leagueName = (String) gui.getLeagueUrls().getSelectedItem();
				Integer round = (Integer) gui.getRoundComboBox().getSelectedItem();
				
				String leagueUrl = convertLeagueNameToUrl(leagueName, round);
			
				
				try {
					nextMatches = parser.getNextMatches(leagueUrl.trim());
					showMatches(nextMatches, gui.getFormatMatchesChkBox().isSelected());
				} catch(IndexOutOfBoundsException exe) {
					gui.getInfoLabel().setText("Kunne ikke hente kamper for " + gui.getLeagueUrls().getSelectedItem());
				} catch(IOException exe) {
					gui.showMessage(exe.getMessage());
					gui.getStatusLabel().setText("Hentet kamper: 0");
				}
			// Hent toppscorere
			} else if(e.getSource() == gui.getTopscorerButton()) {
				String url = topscorerUrls.get(gui.getTopscorerDropdown().getSelectedItem());
				try {
					showTopscorers(parser.getTopscorers(url));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					gui.showMessage(e1.getMessage());
				}
			// Hent tabell
			} else if(e.getSource() == gui.getTableButton()) {
				showTable(parser.getTable(leagueUrls.get(gui.getTableDropdown().getSelectedItem())));
			} else if(e.getSource() == gui.getPrintItem()) {
				boolean complete = false;
				try {
					complete = gui.getSummaryEditorPane().print();
				} catch (PrinterException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if(complete) {
					gui.showMessage("Printing is done");
				} else {
					gui.showMessage("Printing...");
				}
			} else if(e.getSource() == gui.getInfoItem()) {
				String message = "Version: " + Main.VERSION + "\nUtviklet av: " + Main.AUTHOR + "\nSpørsmål? " + Main.EMAIL;
				gui.showMessage(message, "Info om programmet");
			} else if(e.getSource() == gui.getWeatherButton()) {
				if(parser.getMatchList().size() > 0) {
					String html = "";
					for(Match match : parser.getMatchList()) {
						String url = weatherUrls.get(match.getHomeTeam());
						if(url != null) {
							html += weatherParser.parseUrl(weatherUrls.get(match.getHomeTeam()));
						} else {
							System.out.println(match.getHomeTeam() + " finnes ikke i hashmap");
						}
					}
					gui.getWeatherSummaryEditorPane().setText(html);
					
				}
			}
		}

		
	}

	private class ComboBoxEvent implements ItemListener {

		@Override
		public void itemStateChanged(ItemEvent e) {
			// TODO Auto-generated method stub
			if(e.getStateChange() == ItemEvent.SELECTED && e.getSource() == gui.getLeagueUrls()) {
				updateRoundSelector((String) e.getItem());
			} else if (e.getStateChange() == ItemEvent.SELECTED && e.getSource() == gui.getRoundComboBox()) {
				// TODO Implementere noe her?
			}
			
		}
		
	}

}
