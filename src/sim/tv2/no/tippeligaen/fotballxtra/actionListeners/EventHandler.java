package sim.tv2.no.tippeligaen.fotballxtra.actionListeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import sim.tv2.no.tippeligaen.fotballxtra.gui.Gui;
import sim.tv2.no.tippeligaen.fotballxtra.parser.DangerZoneParser;

public class EventHandler implements ActionListener {
	
	private Gui gui;
	private DangerZoneParser parser;

	public EventHandler(DangerZoneParser parser) {
		gui = Gui.getInstance();
		this.parser = parser;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == gui.getTippeligaButton()) {
			
			String tippeligaen = "http://www.altomfotball.no/elementsCommonAjax.do?cmd=statistics&subCmd=yellowCards&tournamentId=1&seasonId=&teamId=";
			String info = parser.getDangerZonePlayers(tippeligaen);
			gui.getTextArea().setText(info);
			parser.reset();
		} else if (e.getSource() == gui.getObosButton()) {
			String obosLigaen = "http://www.altomfotball.no/elementsCommonAjax.do?cmd=statistics&subCmd=yellowCards&tournamentId=2&seasonId=&teamId=";
			String info = parser.getDangerZonePlayers(obosLigaen);
			gui.getTextArea().setText(info);
			parser.reset();
		}  else if (e.getSource() == gui.getCopyButton()) {
			
			gui.getTextArea().selectAll();
			gui.getTextArea().requestFocusInWindow();
			
		}
		
		
	}

}
