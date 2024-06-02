// imports go here

import java.util.Scanner;
import java.io.PrintWriter;

/**
 *	MVCipher - Encrypts and Decrypts a cipher based on the keyword input by the user. ALso creates a file for the output if necessary
 *	Requires Prompt and FileUtils classes.
 *	
 *	@author	Tanay Kumar
 *	@since	September 27th, 2023
 */
public class MVCipher 
{
	private final int CAPITAL_A_ASCII = 65; // This is the ascii value of 
											// capital A
	private final int BRACKET_ASCII = 91; // This is the ascii value of 
											// the symbol '['
	private final int CAPITAL_Z_ASCII = 90; // This is the ascii value of 
											// capital Z
	private final int LOWERCASE_A_ASCII = 97; // This is the ascii value  
											// of lowercase a
	private final int LOWERCASE_Z_ASCII = 122; // This is the ascii value  
											// of lowercase z
	private final int ALPHABET_COUNT = 26; // This is the amount of letter
										// in the alphabet
	
	/** Constructor */
	public MVCipher() { }
	
	public static void main(String[] args) 
	{
		MVCipher mvc = new MVCipher();
		mvc.run();
	}
	
	/** This method calls the methods promptUsers()
	 */
	public void run() 
	{
		promptUsers();
	}
	
	/** This method prompts the users using the Prompt class. This method
	 * will keep on prompting the user till the user gives a valid input
	 * of a > two letter word.
	 */
	public void promptUsers() 
	{
		String keyWord = new String("");
		boolean meetsReqs = false; //true if keyword fits requirements
		
		Prompt pr = new Prompt();
		
		System.out.println("\n Welcome to the MV Cipher machine!\n");
		
		/* Prompt for a key and change to uppercase
		   Do not let the key contain anything but alpha
		   Use the Prompt class to get user input */
		keyWord = pr.getString("Please input a word to use as key (letters only)");

		while (!meetsReqs)
		{
			if (keyWord.length() < 3)
			{
				System.out.println("ERROR: Key must be all letters and at least 3 characters long");
				keyWord = pr.getString("Please input a word to use as key (letters only)");
				meetsReqs = false;
			}
			
			else
			{
				for(int i = 0; i < keyWord.length(); i++)
				{
					char keyWordCharacter = keyWord.charAt(i); //character at a specific place in the keyword
					int characterASCIIValue = (int)(keyWordCharacter); //ASCII value of aforementioned character
					if(characterASCIIValue < CAPITAL_A_ASCII 
						|| characterASCIIValue > CAPITAL_Z_ASCII 
							&& characterASCIIValue < LOWERCASE_A_ASCII 
							|| characterASCIIValue > LOWERCASE_Z_ASCII	)
					{
						System.out.println("ERROR: Key must be all letters and at least 3 characters long");
						keyWord = pr.getString("Please input a word to use as key (letters only)");
						meetsReqs = false;
					}
					else
					{
						meetsReqs = true;
					}
				}
			}
		}
		keyWord = keyWord.toUpperCase();
		readAndWriteToFiles(keyWord);
	}
	
	/** This methods reads the input file and calls the method encryptingOrDecrypting()
	 * to get the encrypted or decrypted text. Then this method writes the
	 * information from encryptingOrDecrypting() to the output file
	 * Precondition: keyWord must be all letters
	 * @param keyWord	The key word entered by the user
	 */
	public void readAndWriteToFiles(String keyWord)
	{
		FileUtils fu = new FileUtils();
		Scanner readFile = new Scanner(System.in);
		Prompt pr = new Prompt();
		PrintWriter writeFile;
		String inFileName = ""; // name of input file
		String outFileName = ""; // name of output file
		int encryptOrDecrypt = 0; // 1 if encrypting, 2 if decrypting
		String fileContents = ""; // contents of input file
		String output = "";  // output text by methods
		
		while (encryptOrDecrypt == 0)
		{
			encryptOrDecrypt = pr.getInt("\nEncrypt or decrypt?  (1, 2)");
			if (!(encryptOrDecrypt == 1 || encryptOrDecrypt == 2))
			{
				encryptOrDecrypt = 0;
			}
		}
			
		if (encryptOrDecrypt == 1)
		{
			inFileName = pr.getString("\nName of file to encrypt");
			outFileName = pr.getString("Name of output file");
		}
		
		else
		{
			inFileName = pr.getString("\nName of file to decrypt");
			outFileName = pr.getString("\nName of output file");
		}
		
		readFile = fu.openToRead(inFileName);
		
		while (readFile.hasNext())
		{
			fileContents = fileContents + readFile.nextLine()+"\n";
		}
		
		output = encryptingOrDecrypting(fileContents, keyWord, encryptOrDecrypt);
		writeFile = fu.openToWrite(outFileName);
		writeFile.write(output);
		writeFile.close();
		readFile.close();
		
		if (encryptOrDecrypt == 1)
		{
			System.out.println("\nThe encrypted file " + outFileName + 
							" has been created using the keyword -> " 
							+ keyWord);
		}
		
		else
		{
			System.out.println("\nThe decrypted file " + outFileName + 
							" has been created using the keyword -> " 
							+ keyWord);
		}
	}
	
	/** This method encrypts and decrypts text based on the input text and 
	 * keyword
	 * Precondition: keyWord must be all letters
	 * @param input 	The input text from the input file
	 * @param keyWord	The key word entered by the user
	 * @param encryptOrDecrypt	Whether to encrypt or decrypt
	 * @return	encrypted or decrypted text of input
	 */
	public String encryptingOrDecrypting(String input, String keyWord, int encryptOrDecrypt)
	{
		char [] keyWordLetters = new char[keyWord.length()]; // array of characters in the keyWord
		String finalEncryption = ""; // the encrypted/decrypted text
		int keyWordIndex = 0; // number denotes which character in the keyWord is being used
		int encryptNum = 0; // ASCII value of encrypted text
		int counter = 0;
		
		for(int i = 0; i < keyWord.length(); i++)
		{
			keyWordLetters[i] = keyWord.charAt(i);
		}
		
		while(counter < input.length())
		{
			int letterChar = (int)(input.charAt(counter));
			if(letterChar >= CAPITAL_A_ASCII && letterChar <= CAPITAL_Z_ASCII 
				|| letterChar >= LOWERCASE_A_ASCII 
				&& letterChar <= LOWERCASE_Z_ASCII)
			{	
				if(encryptOrDecrypt == 1)
				{
					encryptNum = letterChar + getLetterPlace(keyWordLetters[keyWordIndex]);
					
					if(letterChar < CAPITAL_Z_ASCII)
					{
						if(encryptNum > CAPITAL_Z_ASCII)
						{
							encryptNum = encryptNum - CAPITAL_Z_ASCII 
											+ CAPITAL_A_ASCII - 1;
						}
					}
					
					else if(letterChar >= LOWERCASE_A_ASCII)
					{
						if(encryptNum > LOWERCASE_Z_ASCII)
						{
							encryptNum = encryptNum - LOWERCASE_Z_ASCII 
											+ LOWERCASE_A_ASCII - 1;
						}
					}
				}
				
				else
				{
					encryptNum = letterChar - getLetterPlace(keyWordLetters[keyWordIndex]);
					
					if(letterChar > CAPITAL_Z_ASCII)
					{
						if(encryptNum < LOWERCASE_A_ASCII)
						{
							encryptNum += ALPHABET_COUNT;
						}
					}
					
					else if(letterChar >= CAPITAL_A_ASCII)
					{
						if(encryptNum <= CAPITAL_A_ASCII)
						{
							encryptNum += ALPHABET_COUNT;
						}
					}
					
					if(encryptNum == BRACKET_ASCII)
					{
						encryptNum = CAPITAL_A_ASCII;
					}
				}
				
				finalEncryption += (char)(encryptNum);
				keyWordIndex++;
				
				if(keyWordIndex > (keyWord.length()-1))
				{
					keyWordIndex = 0;
				}
			}
			
			else
			{
				finalEncryption += input.substring(counter, counter + 1);
			}
			
			counter++;
		}
		
		return finalEncryption;
	}
	
	/** This method gets what place in the 26 letter alpahbet the letter 
	 * is in
	 * @param letterChar	The letter from the key word the user gave
	 * @return	The place the key word letter is in the alphabet
	 */
	public int getLetterPlace(char letterChar)
	{
		int letterAscii = (int)(letterChar); // The ASCII value of letterChar
		
		if(letterAscii >= LOWERCASE_A_ASCII)
		{
			letterAscii -= (LOWERCASE_A_ASCII - 1);
		}
		
		else if(letterAscii >= CAPITAL_A_ASCII)
		{
			letterAscii -= (CAPITAL_A_ASCII - 1);
		}
		
		return letterAscii;
	}
}
