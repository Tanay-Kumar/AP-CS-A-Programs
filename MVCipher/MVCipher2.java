// imports go here

import java.util.Scanner;
import java.io.PrintWriter;

/**
 *	MVCipher - Encrypts and Decrypts a cipher based on the keyword input by the user. ALso creates a file for the output if necessary
 *	Requires Prompt and FileUtils classes.
 *	
 *	@author	Nathan Bao
 *	@since	9/23/2022
 */
public class MVCipher2 {
	
	// fields go here
		
	/** Constructor */
	public MVCipher2() { }
	
	public static void main(String[] args) {
		MVCipher2 mvc = new MVCipher2();
		mvc.run();
	}
	
	/**
	 *	This method prompts the user for the keyword, whether they are decrypting/encrypting, and calls the other methods
	 * 
	 */
	public void run() {
		String keyWord = new String("");
		boolean meetsReqs = false; //true if keyword fits requirements
		boolean hasError = false;  //false if keyword has no problems, true otherwise
		String inFileName = ""; //name of input file
		String outFileName = ""; //name of output file
		int encryptOrDecrypt = 0; //1 if encrypting, 2 if decrypting
		String fileContents = ""; //contents of input file
		String output = "";  //output text by methods
		Scanner readFile = new Scanner(System.in);
		PrintWriter writeFile;
		Prompt pr = new Prompt();
		FileUtils FU = new FileUtils();
		System.out.println("\n Welcome to the MV Cipher machine!\n");
		
		/* Prompt for a key and change to uppercase
		   Do not let the key contain anything but alpha
		   Use the Prompt class to get user input */
		keyWord = pr.getString("Please input a word to use as key (letters only)");
		while(meetsReqs == false){
			if(keyWord.length()<3){
				System.out.println("ERROR: Key must be all letters and at least 3 characters long");
				keyWord = pr.getString("Please input a word to use as key (letters only)");
				hasError = true;
			}
			else{
				for(int i = 0;i<keyWord.length();i++){
					char keyWordCharacter = keyWord.charAt(i); //character at a specific place in the keyword
					int characterASCIIValue = (int)(keyWordCharacter); //ASCII value of aforementioned character
					if(characterASCIIValue<65||90<characterASCIIValue&&characterASCIIValue<97||characterASCIIValue>122){
						System.out.println("ERROR: Key must be all letters and at least 3 characters long");
						keyWord = pr.getString("Please input a word to use as key (letters only)");
						hasError = true;
					}
					else{
						hasError = false;
					}
				}
			}
			if(hasError == false){
				meetsReqs = true;
			}
		}
		System.out.println("");
		/* Prompt for encrypt or decrypt */
		encryptOrDecrypt = pr.getInt("Encrypt or decrypt?  (1, 2)");
			
		/* Prompt for an input file name */
		if(encryptOrDecrypt==1){
			inFileName = pr.getString("\nName of file to encrypt");
			outFileName = pr.getString("Name of output file");
		}
		else{
			inFileName = pr.getString("\nName of file to decrypt");
			outFileName = pr.getString("\nName of output file");
		}
		
		/* Read input file, encrypt or decrypt, and print to output file */
		readFile = FU.openToRead(inFileName);
		while(readFile.hasNext()){
			fileContents = fileContents + readFile.nextLine()+"\n";
		}
		if(encryptOrDecrypt==1){
			output = ReturnEncrypt(fileContents, keyWord);
		}
		else{
			output = Decrypt(fileContents, keyWord);
		}
		writeFile = FU.openToWrite(outFileName);
		writeFile.write(output);
		writeFile.close();
		readFile.close();
		if(encryptOrDecrypt==1)
			System.out.println("\nThe encrypted file " + outFileName + " has been created using the keyword -> " + keyWord);
		else
			System.out.println("\nThe decrypted file " + outFileName + " has been created using the keyword -> " + keyWord);
	}
	/**
	 * This method encrypts text based on the input text and keyword
	 * Precondition: keyWord must be all letters
	 * @param input String input, keyWord String keyWord
	 * @return encrypted text of input
	 */
	public String ReturnEncrypt(String input, String keyWord){
		char [] keyWordLetters = new char[keyWord.length()];//array of characters in the keyWord
		String FinalEncryption = ""; //output, returned encrypted text
		int LetterDifference = 0; //ASCII value of a character of the keyWord
		int OriginalLetter = 0; //ASCII Value of input character 
		int letterNumber = 0; //number denotes which character in the keyWord is being used
		int newCharNumber = 0; //ASCII value of encrypted text
		for(int i = 0;i<keyWord.length();i++){
			keyWordLetters[i] = keyWord.charAt(i);
		}
		for(int o = 0;o<input.length();o++){
			int LetterChar = (int)(input.charAt(o));
			if(LetterChar>64&&LetterChar<91|LetterChar>96&&LetterChar<123){
				LetterDifference = (int)(keyWordLetters[letterNumber]);
				OriginalLetter = LetterDifference;
				if(LetterDifference>96){
					LetterDifference = LetterDifference - 96;
				}
				else if(LetterDifference>64){
					LetterDifference = LetterDifference - 64;
				}
				else{
					
				}
				//System.out.println(LetterDifference);
				newCharNumber = LetterChar+LetterDifference;
				//System.out.println(newCharNumber);
				if(LetterChar<90){
					if(newCharNumber>90){
						newCharNumber = newCharNumber-90+64;
					}
				}
				else if(LetterChar>96){
					if(newCharNumber>122){
						newCharNumber = newCharNumber-122+96;
					}
				}
				FinalEncryption = FinalEncryption + "" + (char)(newCharNumber) + "";
				//System.out.println(FinalEncryption);
				letterNumber++;
				if(letterNumber>keyWord.length()-1){
					letterNumber = 0;
				}
			}
			else{
				FinalEncryption += input.substring(o, o+1);
			}
		}
		return FinalEncryption;
	}
	/**
	 * This method decrypts text based on the input text and keyword
	 * Precondition: keyWord must be all letters
	 * @param input 	The input text from the input file
	 * @param keyWord    The key word entered by the user
	 * @return decrypted text of input
	 */
	public String Decrypt(String input, String keyWord){
		char [] keyWordLetters = new char[keyWord.length()];//array of characters in the keyWord
		String FinalDecryption = ""; //output, returned encrypted text
		int LetterDifference = 0; //ASCII value of a character of the keyWord
		int OriginalLetter = 0; //ASCII Value of input character 
		int letterNumber = 0; //number denotes which character in the keyWord is being used
		int newCharNumber = 0; //ASCII value of encrypted text
		for(int i = 0;i<keyWord.length();i++){
			keyWordLetters[i] = keyWord.charAt(i);
		}
		for(int a = 0;a<input.length();a++){
			int LetterChar = (int)(input.charAt(a));
			if(LetterChar>64&&LetterChar<91|LetterChar>96&&LetterChar<123){
				LetterDifference = (int)(keyWordLetters[letterNumber]);
				OriginalLetter = LetterDifference;
				if(LetterDifference>96){
					LetterDifference = LetterDifference - 96;
				}
				else if(LetterDifference>64){
					LetterDifference = LetterDifference - 64;
				}
				else{
					
				}
				//System.out.println(LetterDifference);
				newCharNumber = LetterChar-LetterDifference;
				//System.out.println(newCharNumber);
				if(LetterChar>90){
					if(newCharNumber<97){
						newCharNumber = newCharNumber+26;
					}
				}
				else if(LetterChar>64){
					if(newCharNumber<64){
						newCharNumber = newCharNumber+26;
					}
				}
				FinalDecryption = FinalDecryption + "" + (char)(newCharNumber) + "";
				//System.out.println(FinalEncryption);
				letterNumber++;
				if(letterNumber>keyWord.length()-1){
					letterNumber = 0;
				}
			}
			else{
				FinalDecryption += input.substring(a, a+1);
			}
		}
		return FinalDecryption;
	}
}
