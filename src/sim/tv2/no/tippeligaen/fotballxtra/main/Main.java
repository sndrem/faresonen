package sim.tv2.no.tippeligaen.fotballxtra.main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
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
import sim.tv2.no.tippeligaen.fotballxtra.player.Player;
import sim.tv2.no.tippeligaen.fotballxtra.player.Topscorer;

/**
 * Dette er hovedklassen for faresone-programmet.
 * Programmet henter spillere i faresonen for Tippeligaen og OBOSligaen og presenterer det grafisk slik at det kan bli
 * kopiert inn i Word eller et annet program.
 * Programmet kan også søke etter spillere som er i faresonen.
 */

public class Main {
	private Gui gui;
	private DangerZoneParser parser;
	private Map<String, String> leagueUrls;
	private Map<String, String> topscorerUrls;
	private final String TIPPELIGAEN = "http://www.altomfotball.no/elementsCommonAjax.do?cmd=statistics&subCmd=yellowCards&tournamentId=1&seasonId=&teamId=";
	private final String OBOSLIGAEN = "http://www.altomfotball.no/elementsCommonAjax.do?cmd=statistics&subCmd=yellowCards&tournamentId=2&seasonId=&teamId=";
	
	public static void main(String[] args) {
		Runnable r = new Runnable() {

			@Override
			public void run() {
				new Main();				
			}
		};

		SwingUtilities.invokeLater(r);
	}

	
	/**
	 * Konstruktør for main-klassen
	 */
	public Main() {	
		parser = new DangerZoneParser();
		gui = Gui.getInstance();
		leagueUrls = new HashMap<String, String>();
		topscorerUrls = new HashMap<String, String>();
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
		
		gui.getLeagueUrls().addItemListener(new ComboBoxEvent());
		gui.getRoundComboBox().addItemListener(new ComboBoxEvent());
}

	/**
	 * Metode for å vise kampene som brukeren ber om
	 * @param matches
	 */
	private void showMatches(List<Match> matches) {
		String summary = "";
		gui.getInfoLabel().setText("Hentet " + matches.size() + " kamper" );
		for(Match match : matches) {
			summary += "<br><br>" + match.toString() + "<br><br>";
			gui.getSummaryEditorPane().setText("<p>" + summary + "</p>");
		}
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
				} else {
					
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
						+ "<th>Navn</th>"
						+ "<th>Lag</th>"
						+ "<th>Kamper</th>"
						+ "<th>Mål</th>"
						+ "<th>Målsnitt</th>"
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
	 * Method to get the top scorer
	 */
	public void getTopscorers(String url) {
		try {
			parser.getTopscorers(url);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			gui.showMessage(e.getMessage());
		}
	}
	
	/**
	 * Method to show the table 
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
				
				String leagueUrl = leagueUrls.get(gui.getLeagueUrls().getSelectedItem());
				
				String leagueName = (String) gui.getLeagueUrls().getSelectedItem();
				Integer round = (Integer) gui.getRoundComboBox().getSelectedItem();
				
				leagueUrl = convertLeagueNameToUrl(leagueName, round);
			
				
				try {
					nextMatches = parser.getNextMatches(leagueUrl.trim());
					showMatches(nextMatches);
				} catch(IndexOutOfBoundsException exe) {
					gui.getInfoLabel().setText("Kunne ikke hente kamper for " + gui.getLeagueUrls().getSelectedItem());
				} catch(IOException exe) {
					gui.showMessage(exe.getMessage());
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
