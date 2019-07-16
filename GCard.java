import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

/**
 * Keeps track of a Graphical Card (GCard). Inherits data and methods from Card.
 * Keeps track of a position and an Image for each GCard. Also keep track of
 * some static variables for the background image and the width and height of
 * each Card. Includes methods to construct a new Card, look at and change a
 * Card's position and draw this Card.
 * @author Ridout and Kitty Su
 * @version April 2015
 *
 */
public class GCard extends Card
{
	public final static Image backImage = new ImageIcon("images\\redback.png")
			.getImage();
	public final static int WIDTH = backImage.getWidth(null);
	public final static int HEIGHT = backImage.getHeight(null);

	private Point position;
	private Image image;

	/**
	 * Constructs a graphical Card
	 * @param rank the rank of the Card
	 * @param suit the suit of the Card
	 * @param position the initial position of the Card
	 */
	public GCard(int rank, int suit, Point position)
	{
		super(rank, suit);
		this.position = position;
		// Load up the appropriate image file for this card
		String imageFileName = "" + " cdhs".charAt(suit) + rank + ".png";
		imageFileName = "images\\" + imageFileName;
		image = new ImageIcon(imageFileName).getImage();

	}

	/**
	 * Sets the current position of this GCard
	 * @param position the Card's current position
	 */
	public void setPosition(Point position)
	{
		this.position = position;
	}

	/**
	 * Draws a card in a Graphics context
	 * @param g Graphics to draw the card in
	 */
	public void draw(Graphics g)
	{
		if (isFaceUp())
			g.drawImage(image, position.x, position.y, null);
		else
			g.drawImage(backImage, position.x, position.y, null);
	}

	/**
	 * Checking if the point location of the mouse is located over the GCard
	 * @param point the point location of the mouse
	 * @return true, the point is located within the GCard
	 *         false, the point is not located within the GCard
	 */
	public boolean contains(Point point)
	{		
        if (position.x <= point.x && position.y <= point.y && point.x <= position.x + WIDTH && point.y <= position.y + HEIGHT) 
        	return true;
        
        return false;
	}

	/**
	 * Moving the location of a GCard
	 * @param dx the change in direction on the x-axis
	 * @param dy the change in direction on the y-axis
	 */
	public void translate(int dx, int dy)
	{
		position.x += dx;
		position.y += dy;
	}
}
