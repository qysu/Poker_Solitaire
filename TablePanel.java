/** 
 * Keeps track of the area to draw the Cards for a game of Poker Solitaire
 * Includes code to select, drag and drop cards in the panel
 * @author G Ridout and ...
 * @version April 2015
 */
import java.awt.*;
import java.awt.event.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.*;

public class TablePanel extends JPanel implements MouseListener,
		MouseMotionListener
{
	// Constants for the table layout
	static final int WIDTH = 650;
	static final int HEIGHT = 600;
	private static final Color TABLE_COLOUR = new Color(140, 225, 140);
	private static final int TOP_OFFSET = 10;
	private static final int LEFT_OFFSET = 13;
	private static final int ROW_SPACING = GCard.HEIGHT + 15;
	private static final int COL_SPACING = GCard.WIDTH + 15;
	private static final Point DECK_POS = new Point(6 * COL_SPACING
			+ LEFT_OFFSET, 50 + TOP_OFFSET);

	// Variables to keep track of the deck and hands on the table
	private GDeck myDeck;
	private GPokerHand[] rowHands;
	private GPokerHand[] colHands;
	private GCard nextCard;
	private GCard currentCard;
	private Point lastPoint;
	private int originalRow, originalCol;
	private boolean[][] spotsTaken;
	private int score;
	private boolean gameOver;
	public Statistics myStats;

	/**
	 * Constructs a TablePanel to draw Cards in
	 */
	public TablePanel()
	{
		myStats = Statistics.readFromFile("stats.dat");
		
		
		// Sets up the size and colour and font for this Panel
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setFont(new Font("Arial", Font.PLAIN, 18));
		this.setBackground(TABLE_COLOUR);

		// Add listeners to handle mouse events to select, drag and drop Cards
		this.addMouseListener(this);
		this.addMouseMotionListener(this);

		// Set up the deck and hands
		myDeck = new GDeck(DECK_POS);

		// We need 10 hands in total
		rowHands = new GPokerHand[5];
		colHands = new GPokerHand[5];
		for (int hand = 0; hand < 5; hand++)
		{
			rowHands[hand] = new GPokerHand();
			colHands[hand] = new GPokerHand();
		}
		// Set up an array to keep track of the spots taken on the table
		spotsTaken = new boolean[5][5];

		// Start up a new game when first loaded
		newGame();
	}

	/**
	 * Starts a new game by shuffling the deck and re-initialising the hands and
	 * the spots taken on the table
	 */
	public void newGame()
	{
		myDeck.shuffle();

		// Clear the hands (the clear method just resets the size to 0)
		// Also reset the spotsTaken all to false
		for (int hand = 0; hand < 5; hand++)
		{
			rowHands[hand].clear();
			colHands[hand].clear();
			for (int column = 0; column < 5; column++)
				spotsTaken[hand][column] = false;
		}
		score = 0;
		gameOver = false;

		// Deal the first card
		nextCard = myDeck.deal();
		nextCard.flip();
		currentCard = null;

		repaint();
	}

	/**
	 * Updates the score based on the score in all of the hands
	 */
	public void updateScore()
	{
		score = 0;
		for (int hand = 0; hand < 5; hand++)
		{
			score += rowHands[hand].getScore();
			score += colHands[hand].getScore();
		}
	}

	/**
	 * Shows the top scores in a simple dialog window
	 */
	public void showTopScores()
	{		
		JOptionPane
		.showMessageDialog(
				this,
				myStats.toString(),
				"Top Scores",
				JOptionPane.PLAIN_MESSAGE);
	}

	/**
	 * Paints the drawing area
	 * 
	 * @param g the graphics context to paint
	 */
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);

		// Draw the spots for the cards to go
		// You may want to make this nicer
		g.setColor(Color.black);
		for (int row = 0; row < 5; row++)
			for (int column = 0; column < 5; column++)
			{
				int x = column * COL_SPACING;
				int y = row * ROW_SPACING;
				g.drawRoundRect(x + LEFT_OFFSET, y + TOP_OFFSET,
						GCard.WIDTH - 1, GCard.HEIGHT - 1, 7, 7);
			}

		// Draw the hands and their current scores
		// Only the row hands need to be drawn
		g.setColor(Color.blue);
		for (int hand = 0; hand < 5; hand++)
		{
			rowHands[hand].draw(g);
			int score = rowHands[hand].getScore();
			if (score > 0)
				g.drawString(String.valueOf(score), 5 * COL_SPACING
						+ LEFT_OFFSET + 10, hand * ROW_SPACING + GCard.HEIGHT
						/ 2 + TOP_OFFSET + 5);
			score = colHands[hand].getScore();
			if (score > 0)
				g.drawString(String.valueOf(score), hand * COL_SPACING
						+ GCard.WIDTH / 2 - 5 + LEFT_OFFSET, 5 * ROW_SPACING
						+ TOP_OFFSET + 10);
		}

		// Draw the total score
		g.setFont(new Font("Arial", Font.BOLD, 24));
		g.setColor(Color.blue);
		g.drawString("Score: " + score, 500, 310);

		// Draw the next card and deck if not game over
		if (!gameOver)
		{
			myDeck.draw(g);
			nextCard.draw(g);
		}
		else
		{
			g.setColor(Color.blue);
			g.drawString("Game", DECK_POS.x - 7, DECK_POS.y + 25);
			g.drawString("Over", DECK_POS.x, DECK_POS.y + 55);
		}

		// Draw the moving card
		if (currentCard != null)
			currentCard.draw(g);
	}

	/**
	 * Places a card in the given row and column on the board and marks this
	 * spot as taken
	 * 
	 * @param card the Card to place
	 * @param row the row to place the card
	 * @param column the column to place the card 
	 * Precondition row and column are on the board
	 */
	private void placeACard(GCard card, int row, int column)
	{
		spotsTaken[row][column] = true;
		Point newCardPos = new Point(column * COL_SPACING + LEFT_OFFSET, row
				* ROW_SPACING + TOP_OFFSET);
		
			card.setPosition(newCardPos);
	}

	/**
	 * Refresh the drawing area immediately Immediate refresh is needed to show
	 * the animation
	 */
	private void rePaintDrawingAreaImmediately()
	{
		paintImmediately(new Rectangle(0, 0, getWidth(), getHeight()));
	}

	/**
	 * Handles a mousePress when selecting a spot to place a card or picking up
	 * a Card
	 * @param event the event information
	 */
	public void mousePressed(MouseEvent event)
	{
		// If the game is over, we disable any mouse presses
		if (gameOver)
		{
			if (JOptionPane.showConfirmDialog(TablePanel.this,
					"Do you want to Play Again?", "Game Over",
					JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
				newGame();
			return;
		}

		// If we are dragging a Card disregard any mouse pressed
		// May not be needed
		if (currentCard != null)
			return;

		Point clickPoint = event.getPoint();

		// Figure out the selected row and column on the board
		int row = (clickPoint.y - TOP_OFFSET) / ROW_SPACING;
		int column = (clickPoint.x - LEFT_OFFSET) / COL_SPACING;

		// Ignore any clicks off the board area
		if (row < 0 || row > 4 || column < 0 || column > 4)
			return;

		// Pick up card if there is a Card here
		if (spotsTaken[row][column])
		{
			lastPoint = new Point(event.getPoint());

			// Find out which Card was selected
			currentCard = rowHands[row].getCardAt(lastPoint);

			// Ignore clicks between Cards
			if (currentCard == null)
				return;

			// ... and remove it from both hands and the board
			rowHands[row].removeCard(currentCard);
			colHands[column].removeCard(currentCard);
			spotsTaken[row][column] = false;
			updateScore();

			// Keep track of original position if we need to return the
			// card
			originalRow = row;
			originalCol = column;
			// We can quit early
			return;
		}

		// Clicking on an empty spot
		// Place the next card in this spot
		placeACard(nextCard, row, column);

		// ... and add it to the corresponding row and column hand
		rowHands[row].addCard(nextCard);
		colHands[column].addCard(nextCard);
		updateScore();
		nextCard = null;

		// Deal the next card if not done
		if (myDeck.cardsLeft() > 27)
		{
			nextCard = myDeck.deal();
			nextCard.flip();
			rePaintDrawingAreaImmediately();
		}
		// Game is over - check for top scores
		else
		{
			gameOver = true;
			rePaintDrawingAreaImmediately();
			int toUpdate = myStats.isTopScore(score);
			
			if (toUpdate >= 0)
			{
				String player = JOptionPane.showInputDialog("Please enter your name");
				
				Date date = new Date();
				
				Score newScore = new Score (player, score, date);
	
				myStats.update(newScore, toUpdate);
			}
			
			myStats.writeToFile("stats.dat");
			
		

			// Update top scores
			// Need to add this code in to update the Statistics

			if (JOptionPane.showConfirmDialog(TablePanel.this,
					"Do you want to Play Again?", "Game Over",
					JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
				newGame();
		}
	}

	/**
	 * Handles a mouseReleased event when dropping a Card
	 * 
	 * @param event the event information
	 */
	public void mouseReleased(MouseEvent event)
	{
		// Only can release a Card we have
		if (currentCard != null)
		{
			// Figure out the selected row and column on the board
			Point clickPoint = event.getPoint();
			int row = (clickPoint.y - TOP_OFFSET) / ROW_SPACING;
			int column = (clickPoint.x - LEFT_OFFSET) / COL_SPACING;

			// If off the grid or in a taken spot return to original
			// spot
			if (row < 0 || row > 4 || column < 0 || column > 4
					|| spotsTaken[row][column])
			{
				rowHands[originalRow].addCard(currentCard);
				colHands[originalCol].addCard(currentCard);
				placeACard(currentCard, originalRow, originalCol);
			}
			// else add to new spot
			else
			{
				rowHands[row].addCard(currentCard);
				colHands[column].addCard(currentCard);
				placeACard(currentCard, row, column);
			}

			currentCard = null;
			updateScore();
			repaint();
		}
	}

	@Override
	public void mouseClicked(MouseEvent e)
	{
		// Not used but required since TablePanel implements MouseListener
	}

	@Override
	public void mouseEntered(MouseEvent e)
	{
		// Not used but required since TablePanel implements MouseListener
	}

	@Override
	public void mouseExited(MouseEvent e)
	{
		// Not used but required since TablePanel implements MouseListener
	}

	/**
	 * Handles a mouseMove to change the cursor when on a Card
	 * @param event the event information
	 */
	public void mouseMoved(MouseEvent event)
	{
		// Figure out if we are on a card or not
		boolean onACard = false;
		Point clickPoint = event.getPoint();

		// Check all Cards on the table
		for (int row = 0; row < rowHands.length && !onACard; row++)
			if (rowHands[row].getCardAt(clickPoint) != null)
				onACard = true;

		// Show either a hand (on a card) or the default cursor
		if (onACard)
			setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		else
			setCursor(Cursor.getDefaultCursor());
	}

	/**
	 * Handles a mouseDrag when dragging a Card
	 * @param event the event information
	 */
	public void mouseDragged(MouseEvent event)
	{
		Point currentPoint = event.getPoint();

		if (currentCard != null)
		{
			// We use the difference between the lastPoint and the
			// currentPoint to move the card so that the position of
			// the mouse on the card doesn't matter.
			// i.e. we can drag the card from any point on the card
			// image
			currentCard.translate(currentPoint.x - lastPoint.x, currentPoint.y
					- lastPoint.y);
			lastPoint = currentPoint;
			repaint();
		}
	}
}
