package sim.tv2.no.tippeligaen.fotballxtra.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Dimension;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JSplitPane;
import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;
import java.awt.Color;
import javax.swing.JTextField;
import java.awt.Component;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;

public class RealGui {

	private JFrame frame;
	private JTextField searchField;
	private JButton getTippeligaButton;
	private JButton getObosligaenButton;
	private JButton searchPlayerButton;
	private JEditorPane summaryEditorPane;
	private JEditorPane dangerZoneEditorPane;
	
	private static RealGui instance = null;
	private JLabel infoLabel;
	private JPanel panel;
	private JPanel infoPanel;

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
	
	public RealGui() {
		initialize();
	}

	/**
	 * Create the application.
	 */
	public static RealGui getInstance() {
		if(instance == null) {
			instance = new RealGui();
		}
		return instance;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setSize(new Dimension(1000, 1000));
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
		
		infoPanel = new JPanel();
		buttonPanel.add(infoPanel, BorderLayout.SOUTH);
		
		setInfoLabel(new JLabel(""));
		infoPanel.add(getInfoLabel());
		
		
		dangerZoneEditorPane = new JEditorPane("text/html", "");
		dangerZoneEditorPane.setSize(new Dimension(500, 500));
		
		JScrollPane dangerZoneScrollPane = new JScrollPane(dangerZoneEditorPane);
		dangerZoneScrollPane.setSize(new Dimension(150, 150));
		dangerZoneScrollPane.setPreferredSize(new Dimension(17, 17));
		dangerZoneScrollPane.setViewportBorder(new TitledBorder(null, "Spillere i faresonen", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		
		summaryEditorPane = new JEditorPane("text/html", "");
		
		JScrollPane summaryScrollPane = new JScrollPane(summaryEditorPane);
		summaryScrollPane.setViewportBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Oppsummering etter s\u00F8k", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));

		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, dangerZoneScrollPane, summaryScrollPane);
		splitPane.setAlignmentX(Component.CENTER_ALIGNMENT);
		splitPane.setAlignmentY(Component.CENTER_ALIGNMENT);
		frame.getContentPane().add(splitPane, BorderLayout.CENTER);
		splitPane.setDividerLocation(0.5);
		
		JPanel searchPanel = new JPanel();
		frame.getContentPane().add(searchPanel, BorderLayout.SOUTH);
		
		searchField = new JTextField();
		searchPanel.add(searchField);
		searchField.setColumns(40);
		
		searchPlayerButton = new JButton("Søk etter spiller(e)");
		searchPlayerButton.setEnabled(false);
		searchPanel.add(searchPlayerButton);
		
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

}
