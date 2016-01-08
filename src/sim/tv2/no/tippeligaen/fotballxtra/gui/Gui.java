package sim.tv2.no.tippeligaen.fotballxtra.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class Gui {

	private JFrame frame;
	private JTextField searchField;
	private JButton getTippeligaButton;
	private JButton getObosligaenButton;
	private JButton searchPlayerButton;
	private JEditorPane summaryEditorPane;
	private JEditorPane dangerZoneEditorPane;
	
	private static Gui instance = null;
	private JLabel infoLabel;
	private JPanel panel;
	private JPanel infoPanel;
	private JButton loadAllPlayersButton;
	private JButton clearSearchResultButton;
	private JTabbedPane tabbedPane;
	private JButton getMatchesButton;
	private JTextField urlArea;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					RealGui window = new RealGui();
//					window.frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}
	
	public Gui() {
		initialize();
	}

	/**
	 * Create the application.
	 */
	public static Gui getInstance() {
		if(instance == null) {
			instance = new Gui();
		}
		return instance;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setSize(new Dimension(1500, 1000));
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		
		
		JPanel buttonPanel = new JPanel();
		frame.getContentPane().add(buttonPanel, BorderLayout.NORTH);
		buttonPanel.setLayout(new BorderLayout(0, 0));
		
		panel = new JPanel();
		buttonPanel.add(panel, BorderLayout.NORTH);
		
		getTippeligaButton = new JButton("Tippeligaen");
		panel.add(getTippeligaButton);
		
		getObosligaenButton = new JButton("OBOS-ligaen");
		panel.add(getObosligaenButton);
		
		setLoadAllPlayersButton(new JButton("Last alle spillere"));
		panel.add(getLoadAllPlayersButton());
		
		setGetMatchesButton(new JButton("Hent kamper"));
		panel.add(getGetMatchesButton());
		
		setUrlArea(new JTextField(50));
		getUrlArea().setToolTipText("Lim inn url fra AltOmFotball");
		getUrlArea().setBorder(new TitledBorder("Url fra Alt om Fotball"));
		panel.add(getUrlArea());
		
		infoPanel = new JPanel();
		buttonPanel.add(infoPanel, BorderLayout.SOUTH);
		
		setInfoLabel(new JLabel(""));
		infoPanel.add(getInfoLabel());
		
		
		dangerZoneEditorPane = new JEditorPane("text/html", "");
		dangerZoneEditorPane.setSize(new Dimension(500, 500));
		
		JScrollPane dangerZoneScrollPane = new JScrollPane(dangerZoneEditorPane);
		dangerZoneScrollPane.setSize(new Dimension(350, 350));
		dangerZoneScrollPane.setPreferredSize(new Dimension(750, 750));
		dangerZoneScrollPane.setViewportBorder(new TitledBorder(null, "Spillere i faresonen", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		summaryEditorPane = new JEditorPane("text/html", "");
		
		JScrollPane summaryScrollPane = new JScrollPane(summaryEditorPane);
		summaryScrollPane.setViewportBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Oppsummering etter s\u00F8k", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));

		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, dangerZoneScrollPane, summaryScrollPane);
		splitPane.setAlignmentX(Component.CENTER_ALIGNMENT);
		splitPane.setAlignmentY(Component.CENTER_ALIGNMENT);
		frame.getContentPane().add(splitPane, BorderLayout.CENTER);
		splitPane.setDividerLocation(0.5);
		splitPane.setOneTouchExpandable(true);
		
		JPanel searchPanel = new JPanel();
		frame.getContentPane().add(searchPanel, BorderLayout.SOUTH);
		
		searchField = new JTextField();
		searchPanel.add(searchField);
		searchField.setColumns(40);
		
		searchPlayerButton = new JButton("Søk etter spiller(e)");
		searchPlayerButton.setEnabled(false);
		searchPanel.add(searchPlayerButton);
		
		setClearSearchResultButton(new JButton("Fjern søkeresultater"));
		searchPanel.add(getClearSearchResultButton());
		
		
		frame.getRootPane().setDefaultButton(searchPlayerButton);
		
		frame.setVisible(true);
	
	}

	/**
	 * @return the frame
	 */
	public JFrame getFrame() {
		return frame;
	}

	/**
	 * @param frame the frame to set
	 */
	public void setFrame(JFrame frame) {
		this.frame = frame;
	}

	/**
	 * @return the searchField
	 */
	public JTextField getSearchField() {
		return searchField;
	}

	/**
	 * @param searchField the searchField to set
	 */
	public void setSearchField(JTextField searchField) {
		this.searchField = searchField;
	}

	/**
	 * @return the getTippeligaButton
	 */
	public JButton getGetTippeligaButton() {
		return getTippeligaButton;
	}

	/**
	 * @param getTippeligaButton the getTippeligaButton to set
	 */
	public void setGetTippeligaButton(JButton getTippeligaButton) {
		this.getTippeligaButton = getTippeligaButton;
	}

	/**
	 * @return the getObosligaenButton
	 */
	public JButton getGetObosligaenButton() {
		return getObosligaenButton;
	}

	/**
	 * @param getObosligaenButton the getObosligaenButton to set
	 */
	public void setGetObosligaenButton(JButton getObosligaenButton) {
		this.getObosligaenButton = getObosligaenButton;
	}

	/**
	 * @return the searchPlayerButton
	 */
	public JButton getSearchPlayerButton() {
		return searchPlayerButton;
	}

	/**
	 * @param searchPlayerButton the searchPlayerButton to set
	 */
	public void setSearchPlayerButton(JButton searchPlayerButton) {
		this.searchPlayerButton = searchPlayerButton;
	}

	/**
	 * @return the summaryEditorPane
	 */
	public JEditorPane getSummaryEditorPane() {
		return summaryEditorPane;
	}

	/**
	 * @param summaryEditorPane the summaryEditorPane to set
	 */
	public void setSummaryEditorPane(JEditorPane summaryEditorPane) {
		this.summaryEditorPane = summaryEditorPane;
	}

	/**
	 * @return the dangerSoneEditorPane
	 */
	public JEditorPane getDangerZoneEditorPane() {
		return dangerZoneEditorPane;
	}

	/**
	 * @param dangerSoneEditorPane the dangerSoneEditorPane to set
	 */
	public void setDangerZoneEditorPane(JEditorPane dangerSoneEditorPane) {
		this.dangerZoneEditorPane = dangerSoneEditorPane;
	}

	/**
	 * @return the infoLabel
	 */
	public JLabel getInfoLabel() {
		return infoLabel;
	}

	/**
	 * @param infoLabel the infoLabel to set
	 */
	public void setInfoLabel(JLabel infoLabel) {
		this.infoLabel = infoLabel;
	}

	/**
	 * @return the loadAllPlayersButton
	 */
	public JButton getLoadAllPlayersButton() {
		return loadAllPlayersButton;
	}

	/**
	 * @param loadAllPlayersButton the loadAllPlayersButton to set
	 */
	public void setLoadAllPlayersButton(JButton loadAllPlayersButton) {
		this.loadAllPlayersButton = loadAllPlayersButton;
	}

	/**
	 * @return the clearSearchResultButton
	 */
	public JButton getClearSearchResultButton() {
		return clearSearchResultButton;
	}

	/**
	 * @param clearSearchResultButton the clearSearchResultButton to set
	 */
	public void setClearSearchResultButton(JButton clearSearchResultButton) {
		this.clearSearchResultButton = clearSearchResultButton;
	}

	/**
	 * @return the tabbedPane
	 */
	public JTabbedPane getTabbedPane() {
		return tabbedPane;
	}

	/**
	 * @param tabbedPane the tabbedPane to set
	 */
	public void setTabbedPane(JTabbedPane tabbedPane) {
		this.tabbedPane = tabbedPane;
	}

	/**
	 * @return the getMatchesButton
	 */
	public JButton getGetMatchesButton() {
		return getMatchesButton;
	}

	/**
	 * @param getMatchesButton the getMatchesButton to set
	 */
	public void setGetMatchesButton(JButton getMatchesButton) {
		this.getMatchesButton = getMatchesButton;
	}

	/**
	 * @return the urlArea
	 */
	public JTextField getUrlArea() {
		return urlArea;
	}

	/**
	 * @param urlArea the urlArea to set
	 */
	public void setUrlArea(JTextField urlArea) {
		this.urlArea = urlArea;
	}

}
