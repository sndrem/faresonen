package sim.tv2.no.tippeligaen.fotballxtra.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.text.html.HTMLDocument;

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
	private JButton tableButton;
	private JComboBox<String> tableDropdown;
	private JPanel getNextMatchesPanel;
	private JComboBox<Integer> roundComboBox;
	private JMenuItem printItem;
	private JCheckBox formatMatchesChkBox;
	private JMenuItem infoItem;
	private JPanel mainPanel;
	private JTabbedPane mainTab;
	private JScrollPane summaryScrollPane;
	private JButton weatherButton;
	private JEditorPane weatherSummaryEditorPane;

	
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
		
		mainTab = new JTabbedPane(JTabbedPane.TOP);
		frame.getContentPane().add(mainTab, BorderLayout.NORTH);
		
		mainPanel = new JPanel();
		JScrollPane mainScrollPane = new JScrollPane(mainPanel);
		mainTab.addTab("Hovedpanel", null, mainScrollPane, null);
		
		mainPanel.setLayout(new BorderLayout(0, 0));
		
			
		JPanel buttonPanel = new JPanel(new GridLayout(2,2));
		mainPanel.add(buttonPanel, BorderLayout.NORTH);
		
		panel = new JPanel();
		panel.setBorder(new TitledBorder("Hent spillere i faresonen"));
		
		getTippeligaButton = new JButton("Tippeligaen");
		panel.add(getTippeligaButton);
		
		getObosligaenButton = new JButton("OBOS-ligaen");
		panel.add(getObosligaenButton);
		
		setLoadAllPlayersButton(new JButton("Last alle spillere"));
		panel.add(getLoadAllPlayersButton());
		
		
		setGetNextMatchesPanel(new JPanel(new GridLayout(2,2)));
		setLeagueUrls(new JComboBox<String>());
		getGetNextMatchesPanel().add(getLeagueUrls());
		setRoundComboBox(new JComboBox<Integer>());
		getGetNextMatchesPanel().add(getRoundComboBox());
		setFormatMatchesChkBox(new JCheckBox("Formater for neste runde"));
		getGetNextMatchesPanel().add(getFormatMatchesChkBox());
		setGetMatchesButton(new JButton("Hent kamper"));
		getGetNextMatchesPanel().add(getGetMatchesButton());
		getGetNextMatchesPanel().setBorder(new TitledBorder("Hent info om neste kamper"));
		
		
		buttonPanel.add(panel);
		buttonPanel.add(getGetNextMatchesPanel());
		
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
		
		JPanel tablePanel = new JPanel();
		tablePanel.setBorder(new TitledBorder("Hent tabell"));
		setTableButton(new JButton("Hent tabell"));
		tablePanel.add(getTableButton());
		setTableDropdown(new JComboBox<String>());
		tablePanel.add(getTableDropdown());
		buttonPanel.add(tablePanel);
		
		dangerZoneEditorPane = new JEditorPane("text/html", "");
		dangerZoneEditorPane.setSize(new Dimension(500, 500));
		
		JScrollPane dangerZoneScrollPane = new JScrollPane(dangerZoneEditorPane);
		dangerZoneScrollPane.setSize(new Dimension(350, 350));
		dangerZoneScrollPane.setPreferredSize(new Dimension(750, 750));
		dangerZoneScrollPane.setViewportBorder(new TitledBorder(null, "Spillere i faresonen", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		summaryEditorPane = new JEditorPane("text/html", "");
		Font font = new Font("Arial", Font.PLAIN, 10);
		String bodyRule = "body {font-family: " + font.getFamily() + "; " +
				"font-size: " + font.getSize() + "pt; }";
		((HTMLDocument) summaryEditorPane.getDocument()).getStyleSheet().addRule(bodyRule);
		((HTMLDocument) dangerZoneEditorPane.getDocument()).getStyleSheet().addRule(bodyRule);
		
		summaryScrollPane = new JScrollPane(summaryEditorPane);
		summaryScrollPane.setViewportBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Oppsummering etter s\u00F8k", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, dangerZoneScrollPane, summaryScrollPane);
		splitPane.setPreferredSize(new Dimension(500, 700));
		mainPanel.add(splitPane, BorderLayout.CENTER);
		splitPane.setAlignmentX(Component.CENTER_ALIGNMENT);
		splitPane.setAlignmentY(Component.CENTER_ALIGNMENT);
		splitPane.setDividerLocation(0.5);
		splitPane.setOneTouchExpandable(true);
		
		JPanel searchPanel = new JPanel(new GridLayout(2,2));
		mainPanel.add(searchPanel, BorderLayout.SOUTH);
		
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

		mainTab.addTab("Værmelding", createWeatherPanel());
		createMenuBar();
		
//		frame.pack();
		frame.setVisible(true);
	
	}
	
	/**
	 * Method to create the menubar
	 */
	private void createMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		
		JMenu menu = new JMenu("File");
		menuBar.add(menu);
		
		JMenu helpMenu = new JMenu("Help");
		menuBar.add(helpMenu);
		
		setPrintItem(new JMenuItem("Print"));
		menu.add(getPrintItem());
		getPrintItem().setEnabled(false);
		
		setInfoItem(new JMenuItem("Info"));
		helpMenu.add(getInfoItem());
		
		frame.setJMenuBar(menuBar);
		
	}
	
	private JPanel createWeatherPanel() {
		JPanel weatherPanel = new JPanel(new BorderLayout());
		JPanel weatherButtonPanel = new JPanel();
		setWeatherButton(new JButton("Hent været for dagens kamper"));
		weatherButtonPanel.add(getWeatherButton());
		
		setWeatherSummaryEditorPane(new JEditorPane("text/html", ""));
		JScrollPane weatherScrollPane = new JScrollPane(getWeatherSummaryEditorPane());
		weatherPanel.add(weatherButtonPanel, BorderLayout.NORTH);
		weatherPanel.add(weatherScrollPane, BorderLayout.CENTER);
		return weatherPanel;
	}
	
	/**
	 * Method to show a popup dialog
	 */
	public void showMessage(String message) {
		JOptionPane.showMessageDialog(frame, message);
	}
	
	/**
	 * Method to show a popup dialig with a title
	 */
	public void showMessage(String message, String title) {
		JOptionPane.showMessageDialog(frame, message, title, JOptionPane.INFORMATION_MESSAGE);
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

	/**
	 * @return the tableButton
	 */
	public JButton getTableButton() {
		return tableButton;
	}

	/**
	 * @param tableButton the tableButton to set
	 */
	public void setTableButton(JButton tableButton) {
		this.tableButton = tableButton;
	}

	/**
	 * @return the tableDropdown
	 */
	public JComboBox<String> getTableDropdown() {
		return tableDropdown;
	}

	/**
	 * @param tableDropdown the tableDropdown to set
	 */
	public void setTableDropdown(JComboBox<String> tableDropdown) {
		this.tableDropdown = tableDropdown;
	}

	/**
	 * @return the getNextMatchesPanel
	 */
	public JPanel getGetNextMatchesPanel() {
		return getNextMatchesPanel;
	}

	/**
	 * @param getNextMatchesPanel the getNextMatchesPanel to set
	 */
	public void setGetNextMatchesPanel(JPanel getNextMatchesPanel) {
		this.getNextMatchesPanel = getNextMatchesPanel;
	}

	/**
	 * @return the roundComboBox
	 */
	public JComboBox<Integer> getRoundComboBox() {
		return roundComboBox;
	}

	/**
	 * @param roundComboBox the roundComboBox to set
	 */
	public void setRoundComboBox(JComboBox<Integer> roundComboBox) {
		this.roundComboBox = roundComboBox;
	}

	/**
	 * @return the printItem
	 */
	public JMenuItem getPrintItem() {
		return printItem;
	}

	/**
	 * @param printItem the printItem to set
	 */
	public void setPrintItem(JMenuItem printItem) {
		this.printItem = printItem;
	}

	/**
	 * @return the formatMatchesChkBox
	 */
	public JCheckBox getFormatMatchesChkBox() {
		return formatMatchesChkBox;
	}

	/**
	 * @param formatMatchesChkBox the formatMatchesChkBox to set
	 */
	public void setFormatMatchesChkBox(JCheckBox formatMatchesChkBox) {
		this.formatMatchesChkBox = formatMatchesChkBox;
	}

	/**
	 * @return the infoItem
	 */
	public JMenuItem getInfoItem() {
		return infoItem;
	}

	/**
	 * @param infoItem the infoItem to set
	 */
	public void setInfoItem(JMenuItem infoItem) {
		this.infoItem = infoItem;
	}

	/**
	 * @return the weatherButton
	 */
	public JButton getWeatherButton() {
		return weatherButton;
	}

	/**
	 * @param weatherButton the weatherButton to set
	 */
	public void setWeatherButton(JButton weatherButton) {
		this.weatherButton = weatherButton;
	}

	/**
	 * @return the weatherSummaryEditorPane
	 */
	public JEditorPane getWeatherSummaryEditorPane() {
		return weatherSummaryEditorPane;
	}

	/**
	 * @param weatherSummaryEditorPane the weatherSummaryEditorPane to set
	 */
	public void setWeatherSummaryEditorPane(JEditorPane weatherSummaryEditorPane) {
		this.weatherSummaryEditorPane = weatherSummaryEditorPane;
	}

}
