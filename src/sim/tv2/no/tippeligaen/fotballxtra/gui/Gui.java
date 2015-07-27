package sim.tv2.no.tippeligaen.fotballxtra.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class Gui extends JFrame {
	
	private static Gui instance;
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -8541053030310624190L;
	
	private JButton tippeligaButton, obosButton, copyButton;
	private JEditorPane textArea;
	private Clipboard clipBoard;
	private JLabel infoLabel;
	
	
	
	private Gui() {
		setupGui();
		clipBoard = Toolkit.getDefaultToolkit().getSystemClipboard();
	}
	
	public static synchronized Gui getInstance() {
		if(instance == null) {
			instance = new Gui();
		}
		
		return instance;
	}
	
	private void setupGui() {
		tippeligaButton = new JButton("Hent Tippeligaen");
		obosButton = new JButton("Hent Obosligaen");
		copyButton = new JButton("Marker alle spillere");
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(new Dimension(800,800));
		this.setTitle("Faresonen");
		
		this.setLayout(new BorderLayout());
		
		JPanel buttonPanel = new JPanel();
		
		buttonPanel.add(tippeligaButton);
		buttonPanel.add(obosButton);
		
		this.add(buttonPanel, BorderLayout.NORTH);
		
		textArea = new JEditorPane("text/html", "");
		textArea.setEditable(false);
		textArea.setSize(new Dimension(500, 500));
		
		JScrollPane scrollPane = new JScrollPane(textArea);
		
		this.add(scrollPane, BorderLayout.CENTER);
		
		JPanel bottomPanel = new JPanel();
		
		bottomPanel.add(copyButton);
		
		infoLabel = new JLabel("");
		bottomPanel.add(infoLabel);
		
		this.add(bottomPanel, BorderLayout.SOUTH);
		
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		
	}

	/**
	 * @return the tippeligaButton
	 */
	public JButton getTippeligaButton() {
		return tippeligaButton;
	}

	/**
	 * @param tippeligaButton the tippeligaButton to set
	 */
	public void setTippeligaButton(JButton tippeligaButton) {
		this.tippeligaButton = tippeligaButton;
	}

	/**
	 * @return the obosButton
	 */
	public JButton getObosButton() {
		return obosButton;
	}

	/**
	 * @param obosButton the obosButton to set
	 */
	public void setObosButton(JButton obosButton) {
		this.obosButton = obosButton;
	}


	/**
	 * @return the copyButton
	 */
	public JButton getCopyButton() {
		return copyButton;
	}

	/**
	 * @param copyButton the copyButton to set
	 */
	public void setCopyButton(JButton copyButton) {
		this.copyButton = copyButton;
	}

	/**
	 * @return the textArea
	 */
	public JEditorPane getTextArea() {
		return textArea;
	}

	/**
	 * @param textArea the textArea to set
	 */
	public void setTextArea(JEditorPane textArea) {
		this.textArea = textArea;
	}

	/**
	 * @return the clipBoard
	 */
	public Clipboard getClipBoard() {
		return clipBoard;
	}

	/**
	 * @param clipBoard the clipBoard to set
	 */
	public void setClipBoard(Clipboard clipBoard) {
		this.clipBoard = clipBoard;
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
