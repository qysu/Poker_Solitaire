import java.util.Comparator;

/**
 * Keeps track of the Card Object including
 * its information such as rank, suit and whether its facing up or down
 * its behavior such as checking if it is an ace or getting its game value
 * @author Kitty Su
 * @version Wednesday, April 15, 2015
 */
public class Card implements Comparable<Card>
{
	
	protected int rank;
	protected int suit;
	protected boolean isFaceUp;
	
	public static final Comparator<Card>
		SUIT_ORDER = new suitOrder();
	
	private static final String SUITS = " CDHS";
	private static final String RANKS = " A23456789TJQK";
	
	/**
	 * Constructs a Card Object storing the specified suit and rank
	 * The card is face down by default
	 * @param rank the ordered values of each card
	 * 		  Ace, 2-10, Jack, Queen, King
	 * @param suit the symbol of each card
	 * 		  Clubs, Diamonds, Hearts, Spades
	 */
	public Card(int rank, int suit)
	{
		this.rank = rank;
		this.suit = suit;
		this.isFaceUp = false;
	}
	
	/**
	 * Creating Card Object given through a string
	 * Calling the prior Card Constructor
	 * @param cardStr
	 */
	public Card (String cardStr)
	{
		this(RANKS.indexOf(cardStr.charAt(0)), SUITS.indexOf(cardStr.charAt(1)));
	}
	
	/**
	 * Returning the rank of a card
	 * @return the integer value of a card
	 */
	public int getRank()
	{
		return rank;
	}
	
	/**
	 * Returning the suit of a card 
	 * @return the integer value of the suit
	 */
	public int getSuit ()
	{
		return suit;
	}
	
	/**
	 * Flipping the card so that
	 * If it was facing up, it now faces down
	 * If it was facing down, it now faces up
	 */
	public void flip()
	{
		if (isFaceUp == true)
			isFaceUp = false;
		else
			isFaceUp = true;
	}
	
	/**
	 * Checks if the card is an ace
	 * @return true, if the card is an ace
	 * 		   false, if the card is not an ace
	 */
	public boolean isAce()
	{
		return this.rank == 1;
	}
	
	/**
	 * Gets the blackjack value of each card
	 * @return the blackjack value of each card
	 */
	public int getValue()
	{
		// When the card ranges from 2-10, they are equal to their rank
		if (rank >= 2 && rank <= 10)
			return rank;
		// When the card is an ace, it is 11 by default
		else if (rank == 1)
			return 11;
		// J, Q, K are equal to 10
		else 
			return 10;
	}
	
	/**
	 * Checks whether if the card is facing up or down
	 * @return true, if the card is facing up
	 * 		   false, if the card is facing down
	 */
	public boolean isFaceUp ()
	{
		return isFaceUp;
	}
	
	/**
	 * Comparing one Card Object to another by comparing their rank 
	 * If they have the same rank, then compare by suit
	 * @param otherCard the other Card Object to compare to
	 * @return > 0, if the current Card is bigger than the otherCard
	 * 		   < 0, if the current Card is smaller than the otherCard
	 * 		   0, if they are equal
	 */
	public int compareTo (Card otherCard)
	{
		// Compare by rank if they are different
		if (this.rank != otherCard.rank)
			return this.rank - otherCard.rank;
		
		return this.suit - otherCard.suit;
	}
	
	/**
	 * An inner Comparator class that compares two Cards by their suit then rank
	 */
	private static class suitOrder implements Comparator<Card>
	{
		/**
		 * Comparing one Card Object to another by comparing their suit
		 * If they have the same suit, then compare by rank
		 * @param first the first Card Object to compare
		 * @param second the Card Object to compare with the first
		 * @return > 0, if the current Card is bigger than the otherCard
		 *	       < 0, if the current Card is smaller than the otherCard
		 *	       0, if they are equal
		 */
		public int compare (Card first, Card second)
		{
			// Compare by suit if they are different
			if (first.suit != second.suit)
				return first.suit - second.suit;
			
			return first.rank - second.rank;
		}
	}
	
	/**
	 * Returns the Card Object as a String
	 * @return the Card Object as a String 
	 */
	public String toString()
	{
		return String.format("%c%c", RANKS.charAt(rank), SUITS.charAt(suit));
	}
	

}
