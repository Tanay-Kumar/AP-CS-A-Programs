import java.util.Scanner;
/**
 *	Counts the frequency of letters in a file and produces a histogram.
 *
 *	@author Tanay Kumar	
 *	@since September 18th, 2023
 */
public class LetterCount {
	
	// Fields go here, all must be private
	private int[] count; // This is an array of the count of each letter
	private Scanner reader; // This is the scanner used for reading the file
	
	/* Constructor */							
	public LetterCount() 
	{
		count = new int[26];
		reader = null;
	}
	
	/* Main routine */
	public static void main(String[] args) {
		LetterCount lc = new LetterCount();
		if (args.length != 1)
			System.out.println("Usage: java LetterCount <inputFile>");
		else
			lc.run(args);
	}
	
	/**	The core program of the class, it
	 *	- opens the input file
	 *	- reads the file and counts the letters
	 *	- closes the input file
	 *	- prints the histogram of the letter count
	 */
	public void run(String[] args) {
		String fileName = args[0];
		getLetterCount(fileName);
		printHistogram(getFrequencyPluses(getMax()));
	}
	
	/**
	 * This method opens the file given and get's the count of each letter
	 * 
	 * @param fileName	Name of the File
	 */ 
	public void getLetterCount(String fileName)
	{
		FileUtils fu = new FileUtils();
		Scanner reader = fu.openToRead(fileName);
		String str = "";
		char str2 = '0';
		int str2Value = 0;
		
		while(reader.hasNext())
		{
			str = reader.nextLine();
			str = str.toLowerCase();
			int counter = 0;
			int letterIndex = 0;
			while(counter < str.length())
			{
				str2 = str.charAt(counter);
				str2Value = (int)(str2);
				if (str2Value >= 97 && str2Value <= 122)
				{
					count[str2Value - 97] += 1;
				}
				counter++;
			}
		}
	}
	
	/**
	 * This method get's the biggest count from the array count
	 * 
	 * @return	The biggest count
	 */ 
	public int getMax()
	{
		int max = count[0];
		int counter = 1;
		while (counter < count.length)
		{
			if(count[counter] > max)
			{
				max = count[counter];
			}
			counter++;
		}
		
		return max;
	}
	
	/**
	 * This method makes an array with the amount of pluses needed for each
	 * letter
	 * 
	 * @param max	The biggest number in the array count
	 * @return	An array of pluses
	 */ 
	public String[] getFrequencyPluses(int max)
	{
		String[] frequency = new String[26];
		
		for (int i = 0; i < count.length; i++)
		{
			String pluses = "";
			for (int j = 0; j < (count[i]*60)/max; j++)
				pluses += "+";
			frequency[i] = pluses;
		}
		
		return frequency;
	}
	
	/**
	 * This method prints the histogram in the terminal window using a
	 * while loop to read the pluses and print them.
	 * 
	 * @param frequencies	the amount of ppluses for each letter
	 */ 
	public void printHistogram(String[] frequencies)
	{
		char character = '0';
		int counter = 0;
		
		while (counter < count.length)
		{
			character = (char)(97 + counter);
			System.out.printf("%s:%10d %s\n", character, count[counter], frequencies[counter]);
			counter++;
		}
	}
}
