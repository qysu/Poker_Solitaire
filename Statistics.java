import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * Constructs the Statistics Object keeping track of its information
 * such as top 5 scores as well as its behavior such as updating the
 * top scores
 * @author Kitty Su
 * @version Thursday, April 16, 2015
 */
public class Statistics implements Serializable 
{
	public ArrayList<Score> topScores;
	      
	/**
	 * Constructs a Statistics Object
	 */
	public Statistics ()
	{
		topScores = new ArrayList<Score>(5);
	}
	
	/**
	 * Writes Statistic objects to a file
	 * @param fileName the file name to write to file
	 */
	public void writeToFile (String fileName)
	{
		try
		{
			ObjectOutputStream fileOut = new ObjectOutputStream (new FileOutputStream(fileName));
			fileOut.writeObject(this);
			fileOut.close();
		}
		catch (IOException exp)
		{
			System.out.println("Error writing to the file");
		}
	}
	
	/**
	 * Checking if a particular score is in the top 5 
	 * @param score the score value to check
	 * @return >= 0, the index on the array of top scores,
	 *               if the score is in the top 5
	 *         -1, if the score is not in the top 5
	 */
	public int isTopScore (int score)
	{
		Score scoreObject = new Score(score);
		
		// First score
		if (topScores.size() == 0)
			return 0;
		// Less than 5 scores in total 
		else if (topScores.size() < 5)
		{
			// Run through each score in the arrayList 
			for (int scoreCount = 0; scoreCount < topScores.size(); scoreCount++)
			{
				if (topScores.get(scoreCount).compareTo(scoreObject) < 0)
				{
					return scoreCount;
				}
//				else if (scoreCount == topScores.size()-1)
//					return topScores.size()-1;
			}
			return topScores.size();
		}
		// ArrayList of scores is full
		else
		{
			// Run through each score in the arrayList
			for (int scoreCount = 0; scoreCount < topScores.size(); scoreCount++)
			{
				if (topScores.get(scoreCount).compareTo(scoreObject) < 0)
				{
					return scoreCount;
				}
			}
		}
		return -1;
		
	}
	
	/**
	 * Adding the score to the top 5 arrayList
	 * @param score the score to add
	 * @param scorePosition the index of this score in the arrayList
	 */
	public void update (Score score, int scorePosition)
	{
		topScores.add(scorePosition, score);
		
		// Remove the last score, if there are more than 5 scores now
		if (topScores.size() > 5)
			topScores.remove(5);
	}
	
	/**
	 * Returning the Score Object as a string
	 * @return the Score Object as a string
	 */
	public String toString()
	{
		StringBuilder displayScore = new StringBuilder();
	
		String title = "  Name         Score                    Date\n";
		displayScore.append(title);
		
		// Run through each score in the arrayList
		for (int index = 0; index < topScores.size(); index++)
		{
			displayScore.append(index+1);
			displayScore.append(". ");
			displayScore.append(topScores.get(index).toString());
			displayScore.append("\n");
		}
		return displayScore.toString();
	}
	
	/**
	 * Reads from a file and creates a Statistic Object
	 * @param fileName the name of the file to read from
	 * @return the Statistic Object
	 */
	public static Statistics readFromFile (String fileName)
	{
		try
		{
			ObjectInputStream fileIn = new ObjectInputStream(new FileInputStream (fileName));
			Statistics stats = (Statistics) fileIn.readObject();
			fileIn.close();
			return stats;
		}
		catch (Exception exp)
		{
			return new Statistics();
		}
	}
	
}