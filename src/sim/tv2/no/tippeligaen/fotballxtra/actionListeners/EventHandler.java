package sim.tv2.no.tippeligaen.fotballxtra.actionListeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

import javax.swing.JOptionPane;

import sim.tv2.no.tippeligaen.fotballxtra.gui.RealGui;
import sim.tv2.no.tippeligaen.fotballxtra.parser.DangerZoneParser;
import sim.tv2.no.tippeligaen.fotballxtra.player.Player;

public class EventHandler implements ActionListener {
	
	private RealGui gui;
	private DangerZoneParser parser;
	private String tippeligaen = "http://www.altomfotball.no/elementsCommonAjax.do?cmd=statistics&subCmd=yellowCards&tournamentId=1&seasonId=&teamId=";
	private String obosLigaen = "http://www.altomfotball.no/elementsCommonAjax.do?cmd=statistics&subCmd=yellowCards&tournamentId=2&seasonId=&teamId=";
	private boolean hasLoadedEveryone = false;
	
	public EventHandler(DangerZoneParser parser) {
		gui = RealGui.getInstance();
		this.parser = parser;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == gui.getGetTippeligaButton()) {
			parser.reset();
			gui.getSearchPlayerButton().setEnabled(true);
			String info = parser.getDangerZonePlayers(tippeligaen);
			gui.getDangerZoneEditorPane().setText(info);
			gui.getInfoLabel().setText(parser.getTeams().size() + " lag og " + parser.getPlayers().size() + " spillere ble hentet");
		} else if (e.getSource() == gui.getGetObosligaenButton()) {
			parser.reset();
			gui.getSearchPlayerButton().setEnabled(true);
			String info = parser.getDangerZonePlayers(obosLigaen);
			gui.getDangerZoneEditorPane().setText(info);
			gui.getInfoLabel().setText(parser.getTeams().size() + " lag og " + parser.getPlayers().size() + " spillere ble hentet");
			
		} else if (e.getSource() == gui.getSearchPlayerButton()) {
			String searchText = gui.getSearchField().getText().trim();
			if(searchText.equalsIgnoreCase("")) {
				JOptionPane.showMessageDialog(gui.getFrame(), "Tomt søkefelt");
			} else {
				String info = "";
				Set<Player> players = parser.searchPlayer(searchText.trim());
				if(players != null) {
					for(Player player : players) {
						info += "<span style=\"font-size: 15px; color: red;\">" + player.getName() + " er i faresonen med " + player.getYellowCards() + " gule kort.</span><span style=\"font-size: 15px; text-decoration: underline;\"> Han må stå over neste kamp.</span><br/>";
						info += "<br/>";
					}
					gui.getSummaryEditorPane().setText(info);
				}
				gui.getSearchField().setText("");
			}
			
		} else if (e.getSource() == gui.getClearSearchResultButton()) {
			gui.getSummaryEditorPane().setText("");
			
		} else if (e.getSource() == gui.getLoadAllPlayersButton()) {
			if(!this.hasLoadedEveryone) {
				parser.reset();
				parser.getDangerZonePlayers(tippeligaen);
				parser.getDangerZonePlayers(obosLigaen);
				
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
					parser.getDangerZonePlayers(tippeligaen);
					parser.getDangerZonePlayers(obosLigaen);
					
					gui.getInfoLabel().setText("Tippeligaen og OBOS-ligaen ble lastet på nytt");
					gui.getSearchPlayerButton().setEnabled(true);
					this.hasLoadedEveryone = true;
				}
			}
		}
		
		
	}

}
