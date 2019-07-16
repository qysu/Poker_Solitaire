/**
 * Keeps track of Deck Object including
 * its information such as the Card objects and the top card value
 * its behavior such as dealing cards, shuffling and calculating the cards left
 * @author Kitty Su
 * @version Sunday, April 19, 2015
 */
public class Deck {

	protected Card [] deck;
	protected int topCard;
	
	/**
	 * Constructs the Deck Object keeping track of the top card
	 * and an array of all its cards
	 * @param noOfDecks the number of decks the Deck Object has
	 */
	public Deck (int noOfDecks)
	{
		// Construct an array to store all the cards
		deck = new Card [noOfDecks * 52];
		topCard = 0;
		
		// Create the cards with the indicated number of decks
		for (int deckNo = 1; deckNo <= noOfDecks; deckNo++)
			for (int suit = 1; suit <= 4; suit++)
				for (int rank = 1; rank <= 13; rank ++)
				{
					// Create a new Card Object and add it to the array
					// Increase the top card index by 1
					deck[topCard] = new Card(rank, suit);
					topCard ++;
				}			
	}
	
	/**
	 * Constructs a Deck Object for one number of decks
	 * Calls on the prior constructor 
	 */
	public Deck()
	{
		this(1);
	}
	
	/**
	 * Deals a Card from the Deck
	 * @return the Card that is dealt
	 */
	public Card deal ()
	{
		// Check if there are no more cards in the Deck
		if (topCard == 0)
			return null;
		else
		{
			// Return the card at the top of the deck
			// Decrease the top card index by one
			topCard --;
			return deck[topCard];
		}
	}
	
	/**
	 * Finds the number of cards left in the deck
	 * @return the number of remaining cards
	 */
	public int cardsLeft()
	{
		return topCard;
	}
	
	/**
	 * Randomly shuffling the Deck of Cards
	 * using the Fisher-Yates Shuffling Algorithm 
	 */
	public void shuffle()
	{
		// Run through each of the card except the first since it can only swap with itself
		for (int position = deck.length-1; position > 0; position --)
		{
			// Randomly generate a new position for this Card
			int newPos = (int)(Math.random()*(position+1));
			
			// Swap the two Cards by creating a temporary Card Object
			Card temp = deck [newPos];
			deck[newPos] = deck[position];
			deck[position]=temp;
			
			// Make all Cards face down
			if (deck[position].isFaceUp())
				deck[position].flip();
		}
		
		// Update topCard value
		topCard = deck.length;
		
	}
	
	/**
	 * Returns the Deck Object as a String
	 * @return the deck as a String
	 */
	public String toString()
	{
		// Create a StringBuilder and add each Card to it after its toString method is called
		StringBuilder deckString = new StringBuilder();
		
		for (int index = 0; index < topCard; index++)
		{
			deckString.append(deck[index].toString() + " ");
		}

		return deckString.toString();
	}
	
}
