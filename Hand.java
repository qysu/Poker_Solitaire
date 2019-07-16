import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 * Keeps track of the Hand Object including an array of cards it holds
 * and its behavior such as clearing or sorting or getting its value
 * @author Kitty Su
 * @version Thursday, April 16, 2015
 */
public class Hand 
{
	protected ArrayList<Card> hand;
	
	/**
	 * Constructs an empty Hand Object
	 */
	public Hand()
	{
		hand = new ArrayList<Card>();
	}
	
	/**
	 * Constructs a Hand Object
	 * @param str
	 */
	public Hand(String str)
	{
		Scanner handStr = new Scanner(str);
		hand = new ArrayList<Card>(str.length()/3 + 1);
		
		while (handStr.hasNext())
			hand.add(new Card(handStr.next()));
		
		handStr.close();
	}
	
	/**
	 * Adding an additional card from to the hand
	 * @param card the new card to add to the hand
	 */
	public void addCard(Card card)
	{
		hand.add(card);
	}
	
	/**
	 * Removing a Card from a Hand
	 * @param card the card to remove
	 */
	public void removeCard(Card card)
	{
		hand.remove(hand.indexOf(card));
	}
	
	/**
	 * Sorting the hand by suit first, then by rank
	 */
	public void sortBySuit()
	{
		Collections.sort(hand, Card.SUIT_ORDER);
	}
	
	/**
	 * Sorting the hand by rank first, then by suit
	 */
	public void sortByRank()
	{
		Collections.sort(hand);
	}
	
	/**
	 * Clearing the hand
	 */
	public void clear()
	{
		hand.clear();
	}
	
	/**
	 * Checking if the hand is a Blackjack, meaning that 
	 * it has a total of two cards and they sum up to 21
	 * @return true, if the hand is a Blackjack
	 * 		   false, if the hand is not a Blackjack
	 */
	public boolean isBlackJack()
	{
		if (getValue() == 21 && hand.size() == 2)
			return true;
		
		return false;
	}
	
	/**
	 * Calculating the Blackjack value of a hand
	 * @return the Blackjack value of the hand
	 */
	public int getValue()
	{
		// Create a variable to keep track of the number of aces
		int aceCount = 0;
		// Create a variable to keep track of the total
		int total = 0;
		
		// Run through every single card in the hand
		// Check if it is an ace
		// Add its value to the total
		for (int index = 0; index < hand.size(); index++)
		{
			if (hand.get(index).isAce() == true)
				aceCount++;
			
			total += hand.get(index).getValue();
		}
		
		
		// Change the ace values from eleven to one
		// if the total is bigger than 21
		while (total > 21 && 0 < aceCount)
		{
			total -= 10;
			aceCount --;
		}
		
		return total;
	}
	
	/**
	 * Returns the Hand Object as a string
	 * @return hand as a string
	 */
	public String toString()
	{
		// Create a Stringbuilder to store all the Cards in the Hand 
		StringBuilder handString = new StringBuilder();
		
		for (int index = 0; index < hand.size(); index++)
		{
			handString.append(hand.get(index).toString() + " ");
		}

		return handString.toString();
}
}
