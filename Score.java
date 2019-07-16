import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Keeps track of the Score object including its information
 * such as player name, score, date
 * as well as its behavior such as compareTo and toString 
 * @author Kitty Su
 * @version Thursday, April 16, 2015
 */
public class Score implements Serializable, Comparable<Score>
{
	private String name;
	private int score;
	private Date date; 
	
	/**
	 * Constructs the Score Object
	 * @param name the name of the player
	 * @param score the player's final score
	 * @param date the date the player played the game
	 */
	public Score (String name, int score, Date date)
	{
		this.name = name;
		this.score = score;
		this.date = date;
	}
	
	/**
	 * Constructs the Score Object with only a score
	 * @param score the final score of the game
	 */
	public Score (int score)
	{
		this.score = score;
	}
	
	/**
	 * Comparing a Score Object to another by their scores
	 * @return 0, if the scores are equal
	 *         < 0, if the current score is smaller than the other
	 *         > 0, if the current score is greater than the other
	 */
	public int compareTo(Score other)
	{		
		return this.score - other.score;
	}

	/**
	 * Returns the Score Object as a string
	 * @return the Score Object as a string
	 */
	public String toString()
	{
		DateFormat dateFormat = new SimpleDateFormat("MMMM d, yyyy");
		
		return String.format("%-20s  %2d  %30s", name, score, dateFormat.format(date));
	}
}
