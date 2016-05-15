package sim.tv2.no.tippeligaen.fotballxtra.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
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
	private JLabel statusLabel;
	private JComboBox<String> leagueUrls;
	private JButton topscorerButton;
	private JComboBox<String> topscorerDropdown;

	
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
		
		panel = new JPanel();
		panel.setBorder(new TitledBorder("Hent spillere i faresonen"));
		
		getTippeligaButton = new JButton("Tippeligaen");
		panel.add(getTippeligaButton);
		
		getObosligaenButton = new JButton("OBOS-ligaen");
		panel.add(getObosligaenButton);
		
		setLoadAllPlayersButton(new JButton("Last alle spillere"));
		panel.add(getLoadAllPlayersButton());
		
		
		JPanel getNextMatchesPanel = new JPanel();
		setGetMatchesButton(new JButton("Hent kamper"));
		getNextMatchesPanel.add(getGetMatchesButton());
		setLeagueUrls(new JComboBox<String>());
		getNextMatchesPanel.add(getLeagueUrls());
		getNextMatchesPanel.setBorder(new TitledBorder("Hent info om neste kamper"));
		
		
		buttonPanel.add(panel);
		buttonPanel.add(getNextMatchesPanel);
		
		infoPanel = new JPanel();
		buttonPanel.add(infoPanel, BorderLayout.SOUTH);
		
		setInfoLabel(new JLabel(""));
		infoPanel.add(getInfoLabel());
		
		JPanel topscorerPanel = new JPanel();
		setTopscorerButton(new JButton("Hent toppscorere"));
		getTopscorerButton().setToolTipText("Henter topp 10 toppscorere");
		topscorerPanel.add(getTopscorerButton());
		setTopscorerDropdown(new JComboBox<String>());
		topscorerPanel.add(getTopscorerDropdown());
		topscorerPanel.setBorder(new TitledBorder("Hent toppscorerliste"));
		
		buttonPanel.add(topscorerPanel, BorderLayout.SOUTH);
		
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
		
		JPanel searchPanel = new JPanel(new GridLayout(2,2));
		frame.getContentPane().add(searchPanel, BorderLayout.SOUTH);
		
		searchField = new JTextField();
		searchField.setBorder(new TitledBorder("Søk etter spillere"));
		searchPanel.add(searchField);
		searchField.setColumns(40);
		
		searchPlayerButton = new JButton("Søk etter spiller(e)");
		searchPlayerButton.setEnabled(false);
		searchPanel.add(searchPlayerButton);
		
		
		setClearSearchResultButton(new JButton("Fjern søkeresultater"));
		searchPanel.add(getClearSearchResultButton());
		
		setStatusLabel(new JLabel(""));
		searchPanel.add(getStatusLabel());
		
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
	 * @return the statusLabel
	 */
	public JLabel getStatusLabel() {
		return statusLabel;
	}

	/**
	 * @param statusLabel the statusLabel to set
	 */
	public void setStatusLabel(JLabel statusLabel) {
		this.statusLabel = statusLabel;
	}
	
	public void updateStatusLabel(String status) {
		this.statusLabel.setText(status);
	}

	/**
	 * @return the leagueUrls
	 */
	public JComboBox<String> getLeagueUrls() {
		return leagueUrls;
	}

	/**
	 * @param leagueUrls the leagueUrls to set
	 */
	public void setLeagueUrls(JComboBox<String> leagueUrls) {
		this.leagueUrls = leagueUrls;
	}

	/**
	 * @return the topscorerButton
	 */
	public JButton getTopscorerButton() {
		return topscorerButton;
	}

	/**
	 * @param topscorerButton the topscorerButton to set
	 */
	public void setTopscorerButton(JButton topscorerButton) {
		this.topscorerButton = topscorerButton;
	}

	/**
	 * @return the topscorerDropdown
	 */
	public JComboBox<String> getTopscorerDropdown() {
		return topscorerDropdown;
	}

	/**
	 * @param topscorerDropdown the topscorerDropdown to set
	 */
	public void setTopscorerDropdown(JComboBox<String> topscorerDropdown) {
		this.topscorerDropdown = topscorerDropdown;
	}

}
