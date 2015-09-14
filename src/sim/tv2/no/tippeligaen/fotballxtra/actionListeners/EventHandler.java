package sim.tv2.no.tippeligaen.fotballxtra.actionListeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import sim.tv2.no.tippeligaen.fotballxtra.gui.RealGui;
import sim.tv2.no.tippeligaen.fotballxtra.parser.DangerZoneParser;
import sim.tv2.no.tippeligaen.fotballxtra.player.Player;

public class EventHandler implements ActionListener {
	
	private RealGui gui;
	private DangerZoneParser parser;

	public EventHandler(DangerZoneParser parser) {
		gui = RealGui.getInstance();
		this.parser = parser;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == gui.getGetTippeligaButton()) {
			parser.reset();
			gui.getSearchPlayerButton().setEnabled(true);
			String tippeligaen = "http://www.altomfotball.no/elementsCommonAjax.do?cmd=statistics&subCmd=yellowCards&tournamentId=1&seasonId=&teamId=";
			String info = parser.getDangerZonePlayers(tippeligaen);
			gui.getDangerZoneEditorPane().setText(info);
		} else if (e.getSource() == gui.getGetObosligaenButton()) {
			parser.reset();
			gui.getSearchPlayerButton().setEnabled(true);
			String obosLigaen = "http://www.altomfotball.no/elementsCommonAjax.do?cmd=statistics&subCmd=yellowCards&tournamentId=2&seasonId=&teamId=";
			String info = parser.getDangerZonePlayers(obosLigaen);
			gui.getDangerZoneEditorPane().setText(info);
			
		} else if (e.getSource() == gui.getSearchPlayerButton()) {
			String searchText = gui.getSearchField().getText();
			String info = "";
			List<Player> players = parser.searchPlayer(searchText.trim());
			if(players != null) {
				for(Player player : players) {
					info += player.getName() + " er i faresonen med " + player.getYellowCards() + " gule kort.<br/>";
					info += "<br/>";
				}
				gui.getSummaryEditorPane().setText(info);
			}
			gui.getSearchField().setText("");
			
		}
		
		
	}

}
