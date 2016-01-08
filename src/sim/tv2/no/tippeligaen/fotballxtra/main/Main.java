package sim.tv2.no.tippeligaen.fotballxtra.main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JOptionPane;

import sim.tv2.no.tippeligaen.fotballxtra.gui.Gui;
import sim.tv2.no.tippeligaen.fotballxtra.match.Match;
import sim.tv2.no.tippeligaen.fotballxtra.parser.DangerZoneParser;
import sim.tv2.no.tippeligaen.fotballxtra.player.Player;

public class Main {
	private Gui gui;
	private DangerZoneParser parser;
	
	public static void main(String[] args) {
		new Main();

	}


	public Main() {	
		parser = new DangerZoneParser();
		gui = Gui.getInstance();
		setupActionListeners();
		
	}
	
	private void setupActionListeners() {
		EventHandler e = new EventHandler();
		gui.getGetObosligaenButton().addActionListener(e);
		gui.getGetTippeligaButton().addActionListener(e);
		gui.getSearchPlayerButton().addActionListener(e);
		gui.getLoadAllPlayersButton().addActionListener(e);
		gui.getClearSearchResultButton().addActionListener(e);
		gui.getGetMatchesButton().addActionListener(e);
}

	private void showMatches(List<Match> matches) {
		String summary = "";
		for(Match match : matches) {
			summary += "<br><br>" + match.toString() + "<br><br>";
			gui.getSummaryEditorPane().setText("<p>" + summary + "</p>");	
		}	
	}
	
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
	
	

private class EventHandler implements ActionListener {
		
		
		private final String TIPPELIGAEN = "http://www.altomfotball.no/elementsCommonAjax.do?cmd=statistics&subCmd=yellowCards&tournamentId=1&seasonId=&teamId=";
		private final String OBOSLIGAEN = "http://www.altomfotball.no/elementsCommonAjax.do?cmd=statistics&subCmd=yellowCards&tournamentId=2&seasonId=&teamId=";
		private boolean hasLoadedEveryone = false;
		
		public EventHandler() {

		}

		@Override
		public void actionPerformed(ActionEvent e) {
			
			if(e.getSource() == gui.getGetTippeligaButton()) {
				parser.reset();
				gui.getSearchPlayerButton().setEnabled(true);
				String info = parser.getDangerZonePlayers(TIPPELIGAEN);
				gui.getDangerZoneEditorPane().setText(info);
				gui.getInfoLabel().setText(parser.getTeams().size() + " lag og " + parser.getPlayers().size() + " spillere ble hentet");
			} else if (e.getSource() == gui.getGetObosligaenButton()) {
				parser.reset();
				gui.getSearchPlayerButton().setEnabled(true);
				String info = parser.getDangerZonePlayers(OBOSLIGAEN);
				gui.getDangerZoneEditorPane().setText(info);
				gui.getInfoLabel().setText(parser.getTeams().size() + " lag og " + parser.getPlayers().size() + " spillere ble hentet");
				
			} else if (e.getSource() == gui.getSearchPlayerButton()) {
				String searchText = gui.getSearchField().getText().trim();
				if(searchText.equalsIgnoreCase("")) {
					JOptionPane.showMessageDialog(gui.getFrame(), "Tomt søkefelt");
				} else {
					String info = "";
					Set<Player> players = searchPlayer(searchText.trim());
					if(players != null && players.size() > 0) {
						for(Player player : players) {
							info += "<span style=\"font-size: 15px; color: red;\">" + player.getName() + " er i faresonen med " + player.getYellowCards() + " gule kort.</span><span style=\"font-size: 15px; text-decoration: underline;\"> Han må stå over neste kamp.</span><br/>";
							info += "<br/>";
						}
						gui.getSummaryEditorPane().setText(info);
					} else {
						JOptionPane.showMessageDialog(gui.getFrame(), searchText + " er ikke i faresonen");
					}
					gui.getSearchField().setText("");
				}
				
			} else if (e.getSource() == gui.getClearSearchResultButton()) {
				gui.getSummaryEditorPane().setText("");
				parser.getMatchList().clear();
			} else if (e.getSource() == gui.getLoadAllPlayersButton()) {
				if(!this.hasLoadedEveryone) {
					parser.reset();
					parser.getDangerZonePlayers(TIPPELIGAEN);
					parser.getDangerZonePlayers(OBOSLIGAEN);
					
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
						parser.getDangerZonePlayers(TIPPELIGAEN);
						parser.getDangerZonePlayers(OBOSLIGAEN);
						
						gui.getInfoLabel().setText("Tippeligaen og OBOS-ligaen ble lastet på nytt");
						gui.getSearchPlayerButton().setEnabled(true);
						this.hasLoadedEveryone = true;
					}
				}
			} else if (e.getSource() == gui.getGetMatchesButton()) {
				// hent kampene
				parser.getMatchList().clear();
				List<Match> nextMatches = null;
				gui.getSummaryEditorPane().setText("");
				if(gui.getUrlArea().getText().equals("")) {
					nextMatches = parser.getNextMatches("http://www.altomfotball.no/element.do?cmd=tournament&tournamentId=230&useFullUrl=false");
				} else {
					nextMatches = parser.getNextMatches(gui.getUrlArea().getText().trim());
				}
				showMatches(nextMatches);
			}
			
			
		}

	}


}
