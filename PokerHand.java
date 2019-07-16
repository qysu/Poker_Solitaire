/**
 * Keeps track of a Poker Hand.  Hand size could be 1 to 7 cards.
 * Includes a getType() method that finds the type (e.g. two pair,
 * flush, straight) of this hand.  Note: In determining a hand's type
 * you should consider up to the best 5 cards in the hand.
 * @author Kitty Su
 * @version April 2013
 */

public class PokerHand extends Hand
{
	// Poker Hand types/categories
	// Use these constants in your getType method
	// e.g. return FULL_HOUSE;
	public final static int ROYAL_FLUSH = 9;
	public final static int STRAIGHT_FLUSH = 8;
	public final static int FOUR_OF_A_KIND = 7;
	public final static int FULL_HOUSE = 6;
	public final static int FLUSH = 5;
	public final static int STRAIGHT = 4;
	public final static int THREE_OF_A_KIND = 3;
	public final static int TWO_PAIR = 2;
	public final static int PAIR = 1;
	public final static int NOTHING = 0;
	
	// Poker Solitaire scores based on the English version
	private final static int[] SCORES = { 0, 1, 3, 6, 12, 5, 10, 16, 30, 50 };

	public final static String[] TYPES = { "Nothing", "Pair", "Two Pair",
			"Three of a Kind ", "Straight", "Flush", "Full House",
			"Four of a Kind", "Straight Flush", "Royal Flush" };

	private int [] rank;
	private int [] suit;
	
	 /** 
	  * Constructs an empty PokerHand
	  */   
	public PokerHand()
	{
		super();
	}

	public PokerHand(String string)
	{
		super();
	}

	/** Returns the type of this hand e.g. Flush, Straight, Two Pair
	  * @return the poker hand type 0 - NOTHING to 9 - ROYAL_FLUSH
	  */
	public int getType()
	{    
		rank = new int [15];
		suit = new int [5];
		
		// Tally up the number of each rank and suit in the Poker Hand
		// into the two arrays storing the rank and suit
	     for (int index = 0; index < hand.size(); index++)
	     {
	    	 rank[hand.get(index).getRank()]++;
    		 suit[hand.get(index).getSuit()]++;
	     }
	      
	     int index = this.isStraightFlush();
	     
	    // Check for royal flush
	    if (index == 2)
	    	return ROYAL_FLUSH;
	    
	    // Straight flush
	    if (index == 1)
		    return STRAIGHT_FLUSH;

	    // Count the number of two and three pairs
	    int twoCount = 0;
		int threeCount = 0;
		
		// Run through the array of ranks to check for
		// a Full House, Four of a Kind, Three of a Kind,
		// Two, Two Pair
		for (int count = 1; count < rank.length; count++)
		{
			int numberOfRank = rank[count];
			
			// Four of a kind
			if (numberOfRank >= 4)
				return FOUR_OF_A_KIND;
			else if (numberOfRank == 2)
				twoCount++;
			else if (numberOfRank >= 3)
				threeCount++;
			
			// Full house
			if (threeCount >= 1 && twoCount >= 1)
				return FULL_HOUSE;
			else if (threeCount >= 2)
				return FULL_HOUSE;
		}
	    
	    // Flush
   		 if (this.isFlush() > 0)
   			 return FLUSH; 

	    // Straight
	     if (this.isStraight())
	    	 return STRAIGHT;
	     
	     // Three of a kind	    
	     if (threeCount >= 1)
	    	return THREE_OF_A_KIND;
	     
	     // Two Pair
	     if (twoCount >= 2)
	    	 return TWO_PAIR;
	     
	     // Pair
	     if (twoCount == 1)
	    	 return PAIR;    	 
	     
	     // There is no poker hand
	     return NOTHING;
	}
	
	/**
	 * Determining if a Poker Hand is a straight flush
	 * @return 0, if the Poker Hand is not a straight flush
	 * 		   1, if the Poker Hand is a straight flush
	 * 		   2, if the Poker Hand is a royal flush
	 */
	public int isStraightFlush()
	{
		// Create a variable to store the integer value of 
		// the suit that this flush is
		int suitUsed = this.isFlush();
		
		// Exit the method, if there is no flush
		if (suitUsed == 0)
			return 0;
		
		// Create an array to store the number of each rank 
		// of the Cards that are a flush in the Poker Hand
		int [] subRank = new int [14];
		
		// Tally up the number of each rank
		for (int index = 0; index < hand.size(); index++)
		{
			if (hand.get(index).getSuit() == suitUsed)
				  subRank[hand.get(index).getRank()]++;
		}
		
		// Create a variable to keep track of the number of consecutive 
		// ranking Cards
		int count = 0;
		
		// Check through each rank for consecutive ranking Cards
		for (int index = 13; index > 0; index--)
		{
			if (subRank[index] >= 1)
			{
				// Check for royal flush
				if (count >= 3 && subRank[1] >= 1 && index == 10)
					return 2;
				// Check for straight flush
				else if (count == 4)
					return 1;
				count++;
			}
			// Set count back to 0, if the ranks are not consecutive
			else
				count = 0;
		}
		return 0;

 	}
	
	/**
	 * Determing the score of a Poker Hand
	 * @return the score of the Poker Hand
	 */
	 public int getScore()
	{
		return SCORES[getType()];
	}
	
	/**
	 * Determining if a Poker Hand is a straight or not
	 * @return true, if the Poker Hand is a straight
	 * 		   false, if the Poker Hand is not a straight
	 */
	public boolean isStraight()
	{	
		// Create a variable to keep track of the number of consecutive Cards
		int count = 0;
			
		// Run through the ranks of the Cards to check for straights
			for (int index = 1; index <= 13; index++)
			{
				if (rank[index] >= 1)
				{
					// Check for A, 10, J, Q, K
					if (count == 3 && rank[1] >= 1 && index == 13)
						return true;
					// Check for straight
					else if (count == 4)
						return true;
					count++;
				}
				// Reset the count to zero, if there are no Cards of the current rank
				else
					count = 0;
			}
			return false;

	}
	
	/**
	 * Determining if a Poker Hand has a flush
	 * @return true, if the Poker Hand has a flush
	 * 		   false, if the Poker Hand does not
	 */
	public int isFlush ()
	{
		// Run through each suit to check if there is a flush
		for (int index = 1; index < suit.length; index++)
		{
			if (suit[index] >= 5)
				return index;
		}
		return 0;
	}
}
