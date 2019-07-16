/** 
 * Plays a simple game of Poker Solitaire
 * @author G Ridout
 * @version April 2015
 */
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class PokerMain extends JFrame implements ActionListener
{
	private TablePanel tableArea;
	private JMenuItem newMenuItem, topScoresOption, quitMenuItem,
			aboutMenuItem;

	/**
	 * Creates a Simple Poker Solitaire Frame Application
	 */
	public PokerMain()
	{
		super("Poker Solitaire");
		setIconImage(Toolkit.getDefaultToolkit().getImage("images\\s1.png"));

		// Add in a Menu
		JMenuBar menuBar = new JMenuBar();
		JMenu gameMenu = new JMenu("Game");
		gameMenu.setMnemonic('G');
		newMenuItem = new JMenuItem("New Game");
		newMenuItem.addActionListener(this);

		topScoresOption = new JMenuItem("Top Scores");
		topScoresOption.addActionListener(this);

		quitMenuItem = new JMenuItem("Exit");
		quitMenuItem.addActionListener(this);
		gameMenu.add(newMenuItem);
		gameMenu.add(topScoresOption);
		gameMenu.addSeparator();
		gameMenu.add(quitMenuItem);
		menuBar.add(gameMenu);

		JMenu helpMenu = new JMenu("Help");
		helpMenu.setMnemonic('H');
		aboutMenuItem = new JMenuItem("About...");
		aboutMenuItem.addActionListener(this);
		helpMenu.add(aboutMenuItem);
		menuBar.add(helpMenu);
		setJMenuBar(menuBar);

		// Set up the layout and add in a CardPanel for the card area
		// Centre the frame in the middle (almost) of the screen
		setLayout(new BorderLayout());
		tableArea = new TablePanel();
		add(tableArea, BorderLayout.CENTER);
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((screen.width - TablePanel.WIDTH) / 2,
				(screen.height - TablePanel.HEIGHT) / 2 - 52);

	}

	/**
	 * Method that deals with the menu options
	 * @param event the event that triggered this method
	 */
	public void actionPerformed(ActionEvent event)
	{
		if (event.getSource() == newMenuItem)
		{
			tableArea.newGame();
		}
		else if (event.getSource() == topScoresOption)
		{
			tableArea.showTopScores();
			
		}
		else if (event.getSource() == quitMenuItem)
		{
			System.exit(0);
		}
		else if (event.getSource() == aboutMenuItem)
		{
			JOptionPane
					.showMessageDialog(
							tableArea,
							"Poker Solitaire by Ridout\nand Kitty Su\n\u00a9 2015",
							"About Poker Solitaire",
							JOptionPane.INFORMATION_MESSAGE);
		}
	}

	public static void main(String[] args)
	{
		PokerMain game = new PokerMain();
		game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.pack();
		game.setVisible(true);
	}
}
