/**
 *	Utilities for handling HTML
 *
 *	@author	Tanay Kumar
 *	@since	November 17th, 2023
 */
public class HTMLUtilities 
{
	private int counter; // This variable is the index of the the result 
						// array in the method tokenizeHTMLString()
	private enum TokenState { NONE, COMMENT, PREFORMAT }; // NONE = not 
														// nested in a block, 
														// COMMENT = inside 
														// a comment block
														// PREFORMAT = inside 
														// a pre-format block
	private TokenState state; // the current tokenizer state
	/**
	 *	Break the HTML string into tokens. The array returned is
	 *	exactly the size of the number of tokens in the HTML string.
	 *	Example:	HTML string = "Goodnight moon goodnight stars"
	 *				returns { "Goodnight", "moon", "goodnight", "stars" }
	 *	@param str			the HTML string
	 *	@return				the String array of tokens
	 */
	public String[] tokenizeHTMLString(String str) 
	{
		// make the size of the array large to start
		String[] result = new String[1000];
		String output = ""; // A line that is going to be put in the result array
		counter = 0;
		int index = 0; // The final length of the new array
		
		while (counter < str.length())
		{
			// This block tokenizes HTML tags using condtionals and the 
			// isComment() and isPreFormat() method.
			if(str.charAt(counter) == '<')
			{	
				// This checks to see if a comment block has started and 
				// changes the state accordingly 
				if (isComment(str, "start"))
				{
					output = str.substring(counter, str.length());

					while (output.indexOf("-->") != -1 || counter < str.length())
					{
						output = str.substring(counter, str.length());
						counter++;
					}

					output = "";
					counter +=2;
					
					if(str.indexOf("-->") == -1)
					{
						state = TokenState.COMMENT;
					}
				}
				
				// This checks to see if <pre> block has started and changes
				// the state accordingly 
				else if (isPreFormat(str, "start"))
				{
					output = str.substring(0, str.length());
					result[index] = output;
					index++;
					output = "";
					counter +=5;
					state = TokenState.PREFORMAT;
				}
				
				// This checks to see if <pre> block has ended and changes
				// the state accordingly by looking for </pre>
				else if (isPreFormat(str, "end"))
				{
					output = str.substring(0, str.length());
					result[index] = output;
					index++;
					output = "";
					counter +=6;
					state = TokenState.NONE;
				}
				
				// This tokenizes a normal HTML tag 				
				else
				{
					if (output.indexOf('<') == -1 && output.indexOf('>') == -1 
						&& !output.equals(""))
					{
						result[index] = output;
						index++;
						output = "";
					}
					
					while (!(str.charAt(counter) == '>'))
					{
						output += str.charAt(counter);
						counter++;
					}
										
					output += str.charAt(counter);
					result[index] = output;
					index++;
					output = "";
				}
			}
			
			// This block checks to see if their a multiline comment and 
			// ignores the text if true using conditionals and the isComment 
			// method. 
			else if (state == TokenState.COMMENT)
			{
				if (isComment(str, "end"))
				{
					state = TokenState.NONE;
				}
				
				else
				{
					counter = str.length();
				}
			}
			
			// This block adds the text as a token in the fromat given if
			// it's in a <pre> block.
			else if (state == TokenState.PREFORMAT)
			{
				counter = str.length();
				result[index] = str;
				index++;
			}
						
			// This block tokenizes strings or numbers before spaces
			else if ((int)(str.charAt(counter)) <= ' ')
			{
				if(!output.trim().equals(""))
				{
					result[index] = output.trim();
					output = "";
					index++;	
				}
			}
			
			// This block tokenizes numbers
			else if (counter + 1 != str.length() && (str.charAt(counter) == '-' 
					|| str.charAt(counter) == '.') && isNumber("" + str.charAt(counter + 1)))
			{
				output = getNumbers(str, output);
				counter--;
				result[index] = output.trim();
				output = "";
				index++;
			}
			
			// This block tokenizes puncuation
			else if(isPuncuation(str, counter))
			{
				if (!output.equals(""))
				{
					result[index] = output.substring(0, output.length());
					index++;
				}
				
				result[index] = "" + str.charAt(counter);
				index++;
				
				output = "";
				
				if (counter + 1 != str.length() && str.charAt(counter + 1) == ' ')
				{
					counter++;
				}
			}
			
			// This block tokenizes a string at the end of the given line
			else if (counter + 1 == str.length())
			{
				result[index] = output + str.charAt(counter);
				//~ System.out.println("changing index:" + str.charAt(counter));
				output = "";
				index++;
			}
			
			// This block adds a chracter to the output line
			else
			{
				output += str.charAt(counter);
			}
			
			counter++;
		}

		String[] outputArray = new String[index];
		
		// return the correctly sized array
		for(int i = 0; i < index; i++)
		{
			outputArray[i] = result[i];
		}

		return outputArray;
	}
	
	/** 
	 * This method goes through the the HTML string and adds numbers,
	 * dashes, and decimal points to the output string using conditionals
	 * and while loops.
	 * 
	 * @param str			the HTML string
	 * @param output		the current substring of the HTML string
	 * @return				a number from the the HTML string
	 */ 
	public String getNumbers(String str, String output)
	{
		int countIt = counter;

		while (countIt < str.length())
		{
			// Handles negative signs decimal places and e using conditionals
			// and loops
			if (str.charAt(counter) == '-' || str.charAt(counter) == '.' 
				|| isNumber("" + str.charAt(counter)))
			{
				if (str.charAt(counter) == '-' || str.charAt(counter) == '.')
				{
					output += str.charAt(counter);
					counter++;
				}
				
				while(counter < str.length() && isNumber("" + str.charAt(counter)))
				{
					output += str.charAt(counter);
					counter++;

					if(counter + 1 < str.length() 
						&& (isNumber("" + str.charAt(counter + 1)) 
						|| str.charAt(counter + 1) == '-'))
					{
						output += str.charAt(counter);
						counter++;

						if (str.charAt(counter) == '.'
							&& isNumber("" + str.charAt(counter + 1)))
						{
							output += str.charAt(counter);
						}
						
						else if (str.charAt(counter - 1) == 'e')
						{
							if (isNumber("" + str.charAt(counter))
								|| str.charAt(counter) == '-')
							{
								output += str.charAt(counter);
								counter++;
							}
						}
					} 
				}
				
				countIt = str.length();
			}

			countIt++;
		}
		
		return output;
	}
	
	/** 
	 * This method checks if the letter at the index given is a number. 
	 * @param str			the HTML string
	 * @return				a boolean indicating if the index was for a number
	 */ 
	public boolean isNumber(String str)
	{
		boolean numChecker = false;
		int num = -1; 
		
		try
		{
			num = Integer.parseInt(str);
			numChecker = true;
		}
		
		catch (NumberFormatException e)
		{
			numChecker = false;
		}
		
		return numChecker;
	}
	
	/** 
	 * This method checks if the letter at the index given is a puncuation,
	 * not including dashes. 
	 * @param str			the HTML string
	 * @param index			the index that is being checked
	 * @return				a boolean indicating if the index was for a puncuation
	 */ 
	public boolean isPuncuation(String str, int index)
	{
		char letter = str.charAt(index);

		if (letter == ',' || letter == '.' || letter == ';' || letter == ':' 
			|| letter == '(' || letter == ')' || letter == '?' || letter == '!' 
			|| letter == '=' || letter == '&'|| letter == '~' || letter == '+') 
		{
			return true;
		}
		
		return false;
	}
	
	/**
	 * This method checks to see whether the start of the string has "<!--" 
	 * or the end has "-->" using loops and conditionals.
	 * @param str			the HTML string
	 * @param decision		tells whether to look for "<!--" or "-->"
	 * @return				a boolean indicating if the line has a comment tag
	 */
	public boolean isComment(String str, String decision)
	{
		boolean commentBool = false;
		
		// True if string has <!--
		if(decision.equals("start"))
		{
			if(counter < str.length() - 3 
				&& str.substring(counter, counter + 4).equals("<!--"))
			{
				commentBool = true;
				counter+=3;
			}
		}
		
		// True if string has -->
		else
		{
			int countIt = counter;
			while(countIt < str.length() - 2)
			{
				if (str.substring(countIt, countIt + 3).equals("-->"))
				{
					counter = countIt + 2;
					commentBool = true;
				}

				countIt++;
			}
		}
		return commentBool;
	}
	
	/**
	 * This method checks to see whether the start of the string has <pre>
	 * or the end has </pre> using loops and conditionals.
	 * @param str			the HTML string
	 * @param decision		tells whether to look for <pre> or </pre>
	 * @return				a boolean indicating if the line has a preformat tag
	 */
	public boolean isPreFormat(String str, String decision)
	{
		boolean preFormBool = false;
		
		// True if string is <pre>
		if(decision.equals("start"))
		{
			if(counter < str.length() - 4 
				&& str.substring(counter, counter + 5).equals("<pre>"))
			{
				preFormBool = true;
				counter += 4;
			}
		}
		
		// True if string is </pre>
		else
		{
			int countIt = counter;
			while(countIt < str.length() - 5)
			{
				if (str.substring(countIt, countIt + 6).equals("</pre>"))
				{
					counter = countIt + 5;
					preFormBool = true;
				}
				
				countIt++;
			}
		}
		return preFormBool;
	}
	
	/**
	 *	Print the tokens in the array to the screen
	 *	Precondition: All elements in the array are valid String objects.
	 *				(no nulls)
	 *	@param tokens		an array of String tokens
	 */
	public void printTokens(String[] tokens) 
	{
		if (tokens == null) return;
		for (int a = 0; a < tokens.length; a++) {
			if (a % 5 == 0) System.out.print("\n  ");
			System.out.print("[token " + a + "]: " + tokens[a] + " ");
		}
		System.out.println();
	}

}
