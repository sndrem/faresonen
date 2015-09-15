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
						info += "<span style=\"font-size: 20px; color: red;\">" + player.getName() + " er i faresonen med " + player.getYellowCards() + " gule kort.</span><br/>";
						info += "<br/>";
					}
					gui.getSummaryEditorPane().setText(info);
				}
				gui.getSearchField().setText("");
			}
			
		} else if (e.getSource() == gui.getLoadAllPlayersButton()) {
			parser.reset();
			parser.getDangerZonePlayers(tippeligaen);
			parser.getDangerZonePlayers(obosLigaen);
			
			gui.getInfoLabel().setText("Tippeligaen og OBOS-ligaen er lastet");
			gui.getSearchPlayerButton().setEnabled(true);
		}
		
		
	}

}
