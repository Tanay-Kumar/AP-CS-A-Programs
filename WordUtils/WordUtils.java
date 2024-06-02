import java.util.Scanner;
/**
 *	Provides utilities for word games:
 *	1. finds all words in the dictionary that match a list of letters
 *	2. prints an array of words to the screen in tabular format
 *	3. finds the word from an array of words with the highest score
 *	4. calculates the score of a word according to a table
 *
 *	Uses the FileUtils and Prompt classes.
 *	
 *	@author Tanay Kumar
 *	@since	23rd October, 2023
 */
 
public class WordUtils
{
	private String[] words;		// the dictionary of words
	
	// File containing dictionary of almost 100,000 words.
	private final String WORD_FILE = "wordList.txt";
	
	/* Constructor */
	public WordUtils() { }
	
	/**	Load all of the dictionary from a file into words array. */
	private void loadWords () 
	{
		Scanner input = FileUtils.openToRead(WORD_FILE);
		int arrayLength = 0;
		
		while (input.hasNext())
		{
			input.nextLine();
			arrayLength++;
		} 
		
		words = new String[arrayLength];
		arrayLength = 0;
		input.close(); 
		input = FileUtils.openToRead(WORD_FILE);

		while (input.hasNext())
		{
			words[arrayLength] = input.next();
			arrayLength++;
		}
		
		input.close();
	}
	
	/**	Find all words that can be formed by a list of letters.
	 *  @param letters	string containing list of letters
	 *  @return			array of strings with all words found.
	 */
	public String [] findAllWords (String letters)
	{	
		String userInput = letters;
		int index = 0;
		int countIt = 0;
		int counter = 0;
		boolean wordMatch = true;
		String[] temp = new String[words.length];
		
		while (countIt < words.length)
		{
			String word = words[countIt];
			wordMatch = true;
			counter = 0;
			
			while(counter < word.length())
			{
				char c = word.charAt(counter);
				
				if (letters.indexOf(c) > - 1)
				{
					letters = letters.substring(0, letters.indexOf(c)) + 
								letters.substring(letters.indexOf(c) + 1);
					wordMatch = true;
				}
				
				
				else
				{
					wordMatch = false;
					counter = word.length();
				}
				counter++;
			}
			
			if(wordMatch)
			{
				temp[index] = word;
				index++;
			}
			
			letters = userInput;
			countIt++;
		} 

		String[] output = new String[index];
		
		for(int i = 0; i < index; i++)
		{
			output[i] = temp[i];
		}
		
		return output;
	}
	
	/**	Print the words found to the screen.
	 *  @param words	array containing the words to be printed
	 */
	public void printWords (String [] wordList) 
	{	
		for (int row = 0; row < wordList.length; row++) 
		{
			System.out.printf("%-8s", wordList[row]);
			if((row + 1) % 5 == 0)
			{
				System.out.println();
			}
		}
		
		System.out.println();
	}
	
	/**	Finds the highest scoring word according to a score table.
	 *
	 *  @param word  		An array of words to check
	 *  @param scoreTable	An array of 26 integer scores in letter order
	 *  @return   			The word with the highest score
	 */
	public String bestWord (String [] wordList, int [] scoreTable)
	{
		String word = wordList[0];
		String theBestWord = word;
		int score1 = - 1;
		int score2 = - 1;
		score1 = getScore(word, scoreTable);

		for(int i = 1; i < wordList.length; i++)
		{
			word = wordList[i];
			score2 = getScore(word, scoreTable);
			if(score2 > score1)
			{
				score1 = score2;
				theBestWord = word;
			}
		}
		
		return theBestWord;
	}
	
	/**	Calculates the score of one word according to a score table.
	 *
	 *  @param word			The word to score
	 *  @param scoreTable	An array of 26 integer scores in letter order
	 *  @return				The integer score of the word
	 */
	public int getScore (String word, int [] scoreTable)
	{
		int score = 0;
		
		for(int i = 0; i < word.length(); i++)
		{
			int letterValue = word.charAt(i) - 'a';
			score += scoreTable[letterValue];
		}
		
		return score;
	}
	
	/***************************************************************/
	/************************** Testing ****************************/
	/***************************************************************/
	public static void main (String [] args)
	{
		WordUtils wu = new WordUtils();
		wu.run();
	}
	
	public void run() 
	{
		loadWords();
		String letters = Prompt.getString("Please enter a list of letters, from 3 to 12 letters long, without spaces");
		String [] word = findAllWords(letters);
		System.out.println();
		printWords(word);
		
		// Score table in alphabetic order according to Scrabble
		int [] scoreTable = {1,3,3,2,1,4,2,4,1,8,5,1,3,1,1,3,10,1,1,1,1,4,4,8,4,10};
		String best = bestWord(word,scoreTable);
		System.out.println("\nHighest scoring word: " + best + "\nScore = " 
							+ getScore(best, scoreTable) + "\n");
	}
}
