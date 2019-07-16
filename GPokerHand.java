import java.awt.Graphics;
import java.awt.Point;

/**
 * Keeps track of a PokerHand of GCards. Inherits data and methods from
 * PokerHand. Assumes all Cards in this Hand are GCards. Includes a constructor
 * and methods to get the GCard at a given point and to draw all of the GCards
 * in this Hand
 * @author Ridout
 * @version April 2015
 *
 */
public class GPokerHand extends PokerHand
{
	// Assumes all Cards in GPokerHand are GCards

	/**
	 * Constructs a new empty GPokerHand.
	 */
	public GPokerHand()
	{
		super();
	}

	/**
	 * Gets the GCard in this GPokerHand at the given point
	 * @param point the point to check
	 * @return the GCard in this GPokerHand at the given point
	 */
	public GCard getCardAt(Point point)
	{
		for (Card next : hand)
			if (((GCard) next).contains(point))
				return (GCard) next;
		return null;
	}

	/**
	 * Displays the GCards in this Hand
	 * @param g Graphics context to display this Hand
	 */
	public void draw(Graphics g)
	{
		for (Card next : hand)
		{
			((GCard) next).draw(g);
		}
	}
}
