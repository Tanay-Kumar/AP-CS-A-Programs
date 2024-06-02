import java.util.Scanner;
/**
 *	HTMLRender
 *	This program renders HTML code into a JFrame window.
 *	It requires your HTMLUtilities class and
 *	the SimpleHtmlRenderer and HtmlPrinter classes.
 *
 *	The tags supported:
 *		<html>, </html> - start/end of the HTML file
 *		<body>, </body> - start/end of the HTML code
 *		<p>, </p> - Start/end of a paragraph.
 *					Causes a newline before and a blank line after. Lines are restricted
 *					to 80 characters maximum.
 *		<hr>	- Creates a horizontal rule on the following line.
 *		<br>	- newline (break)
 *		<b>, </b> - Start/end of bold font print
 *		<i>, </i> - Start/end of italic font print
 *		<q>, </q> - Start/end of quotations
 *		<hX>, </hX> - Start/end of heading with size X = 1, 2, 3, 4, 5, 6
 *		<pre>, </pre> - Preformatted text
 *
 *	@author Tanay Kumar
 *	@since Novemeber 29th, 2023
 */

public class HTMLRender
{
	private final int TOKENS_SIZE = 100000;	// size of array
	private final int HEADER_1_MAX = 40; // Header 1 max length per line
	private final int HEADER_2_MAX = 50; // Header 2 max length per line
	private final int HEADER_3_MAX = 60; // Header 3 max length per line
	private final int HEADER_4_MAX = 80; // Header 4 max length per line
	private final int HEADER_5_MAX = 100; // Header 5 max length per line
	private final int HEADER_6_MAX = 120; // Header 6 max length per line
	private int lineLength = 0; // This is the current line length
	private boolean isBold; // If text should be bolded
	private boolean isItalic; // If text should be italicized
	private boolean isPreformatted; // If text is preformated
	private boolean isParagraph; // If text is in a paragraph
	private boolean[] isHeader; // Array of Header titles like Header1
	private String [] tokens; // the array holding all the tokens of the HTML file
	
	// SimpleHtmlRenderer fields
	private SimpleHtmlRenderer render;
	private HtmlPrinter browser;
		
	public HTMLRender() 
	{
		tokens = new String[TOKENS_SIZE]; // Initialize token array
		
		// Intializes all booleans to false
		isBold = false;
		isItalic = false;
		isPreformatted = false;
		isParagraph = false;
		isHeader = new boolean[6];
		
		// Initialize Simple Browser
		render = new SimpleHtmlRenderer();
		browser = render.getHtmlPrinter();
		
		//Initialize isHeader
		for(int i = 0; i < 6; i++)
		{
			isHeader[i] = false;
		}
		
	}
	
	public static void main(String[] args) 
	{
		HTMLRender hf = new HTMLRender();
		hf.run(args);
	}
	
	/**
	 * This method reads the input html file and calls the method printHTMLString()
	 * to render the text.
	 * 
	 * @param args		The name of the input file
	 */
	public void run(String[] args)
	{
		String fileName = "";
		
		if (args.length > 0)
		{
			fileName = args[0];
		}

		else 
		{
			System.out.println("Usage: java HTMLTester <htmlFileName>");
			System.exit(0);
		}
		
		Scanner sc = FileUtils.openToRead(fileName);
		HTMLUtilities ht = new HTMLUtilities();
		
		while(sc.hasNextLine())
		{
			tokens = ht.tokenizeHTMLString(sc.nextLine());
			printHTMLString();
		}
	}
	
	/**
	 * This method renders the text using the method printTokens() and 
	 * conditionals to choose which style of text to print the text in.
	 */
	public void printHTMLString()
	{
		int counter = 0; // Index of the tokens array
		String token = ""; // Token in the tokens array
		String tokenLower = ""; // Lowercase version of token
		
		while(counter < tokens.length)
		{
			token = tokens[counter];
			tokenLower = token.toLowerCase();
			
			// Checks if the token is the start or end tag <html> or <body>
			if(tokenLower.equals("<html>") || tokenLower.equals("</html>")
				|| tokenLower.equals("<body>") || tokenLower.equals("</body>"))
			{
				printTokens(" ");
			}
			
			// Checks if the token is a break
			else if(tokenLower.equals("<br>"))
			{
				if(lineLength != 0)
				{
					browser.println();
					lineLength = 0;
				}
				
				browser.printBreak();
				lineLength = 0;
			}	
						
			// Checks if the token is a horizontal rule	
			else if(tokenLower.equals("<hr>"))
			{
				if(lineLength != 0)
				{
					browser.println();
					lineLength = 0;
				}
				
				browser.printHorizontalRule();
				lineLength = 0;
			}
						
			// Checks if the token is a bold and sets isBold to true
			else if(tokenLower.equals("<b>"))
			{
				isBold = true;
				printTokens(" ");
			}
						
			// Checks if the token is a italic and sets isItalic to true
			else if(tokenLower.equals("<i>"))
			{
				isItalic = true;
				printTokens(" ");
				 
			}
						
			// Checks if the token is a paragraph and sets isParagraph to true
			else if(tokenLower.equals("<p>"))
			{
				isParagraph = true;
				
				if(lineLength != 0)
				{
					browser.println();
					lineLength = 0;
				}
				
				browser.println();
				lineLength = 0;
			}
						
			// Checks if the token is header1 and sets isHeader[0] to true
			else if(tokenLower.equals("<h1>"))
			{
				isHeader[0] = true;
				
				if(lineLength != 0)
				{
					browser.println();
					lineLength = 0;
				}
				
				browser.println();
				lineLength = 0;
			}
						
			// Checks if the token is header2 and sets isHeader[1] to true
			else if(tokenLower.equals("<h2>"))
			{
				isHeader[1] = true;
				
				if(lineLength != 0)
				{
					browser.println();
					lineLength = 0;
				}
				
				browser.println();
				lineLength = 0;
			}
						
			// Checks if the token is header3 and sets isHeader[2] to true
			else if (tokenLower.equals("<h3>"))
			{
				isHeader[2] = true;
				
				if (lineLength != 0)
				{
					browser.println();
					lineLength = 0;
				}
				
				browser.println();
				lineLength = 0;
			}
						
			// Checks if the token is header4 and sets isHeader[3] to true
			else if (tokenLower.equals("<h4>"))
			{
				isHeader[3] = true;
				
				if (lineLength != 0)
				{
					browser.println();
					lineLength = 0;
				}
				
				browser.println();
				lineLength = 0;
			}
						
			// Checks if the token is header5 and sets isHeader[4] to true
			else if (tokenLower.equals("<h5>"))
			{
				isHeader[4] = true;
				
				if(lineLength != 0)
				{
					browser.println();
					lineLength = 0;
				}
				
				browser.println();
				lineLength = 0;
			}
						
			// Checks if the token is header6 and sets isHeader[6] to true
			else if (tokenLower.equals("<h6>"))
			{
				isHeader[5] = true;
				
				if(lineLength != 0)
				{
					browser.println();
					lineLength = 0;
				}
				
				browser.println();
				lineLength = 0;
			}
						
			// Checks if the token is a preformat and sets isPreformatted 
			// to true
			else if (tokenLower.equals("<pre>"))
			{
				if (lineLength != 0)
				{
					browser.println();
					lineLength = 0;
				}
				
				browser.println();
				lineLength = 0;
				isPreformatted = true;
			}
			
			// Checks if the token is a quote and prints a "
			else if(tokenLower.equals("<q>"))
			{
				printTokens(" ");
				printTokens("\"");
			}
			
			// Checks if the token is a end bold, sets isBold to false and 
			// lineLength to 0
			else if(tokenLower.equals("</b>"))
			{
				isBold = false;
			}
			
			// Checks if the token is a end italic, sets isItalic to false,
			// and lineLength to 0
			else if(tokenLower.equals("</i>"))
			{
				isItalic = false;
			}
			
			// Checks if the token is a end paragraph, sets isParagraph 
			// to false, and lineLength to 0
			else if(tokenLower.equals("</p>"))
			{
				isParagraph = false;
				browser.println();
				browser.println();
				lineLength = 0;
			}
			
			// Checks if the token is a end header 1, sets isHeader[0] to
			// false, and lineLength to 0
			else if(tokenLower.equals("</h1>"))
			{
				isHeader[0] = false;
				browser.println();
				lineLength = 0;
			}
			
			// Checks if the token is a end header 2, sets isHeader[1] to
			// false, and lineLength to 0
			else if(tokenLower.equals("</h2>"))
			{
				isHeader[1] = false;
				browser.println();
				lineLength = 0;
			}
			
			// Checks if the token is a end header 3, sets isHeader[2] to
			// false, and lineLength to 0
			else if (tokenLower.equals("</h3>"))
			{
				isHeader[2] = false;
				browser.println();
				lineLength = 0;
			}
			
			// Checks if the token is a end header 4, sets isHeader[3] to
			// false, and lineLength to 0
			else if (tokenLower.equals("</h4>"))
			{
				isHeader[3] = false;
				browser.println();
				lineLength = 0;
			}
			
			// Checks if the token is a end header 5, sets isHeader[4] to
			// false, and lineLength to 0
			else if (tokenLower.equals("</h5>"))
			{
				isHeader[4] = false;
				browser.println();
				lineLength = 0;
			}
			
			// Checks if the token is a end header 6, sets isHeader[5] to
			// false, and lineLength to 0
			else if (tokenLower.equals("</h6>"))
			{
				isHeader[5] = false;
				browser.println();
				lineLength = 0;
			}
			
			// Checks if the token is a end preformat, sets isPreformatted 
			// to false, and lineLength to 0
			else if(tokenLower.equals("</pre>"))
			{
				browser.println();
				lineLength = 0;
				isPreformatted = false;
			}
			
			// Checks if the token is a end quote and prints a "
			else if(tokenLower.equals("</q>"))
			{
				printTokens("\"");
			}
			
			else
			{
				// If there is no token -> print a blank line
				if(tokenLower.trim().equals(""))
				{
					browser.println();
				}
				
				// If tokens are preformatted -> print them as it is and
				// print two blank lines.
				else if(isPreformatted)
				{
					browser.printPreformattedText(token);
					browser.println();
					browser.println();
				}
				
				// Handles puncuation after the end of an end tag such as 
				// the end italic in example7.html
				else if(counter > 0 && isEndTag(tokens[counter - 1]))
				{
					if(!isPunctuation(token) && lineLength > 0)
					{
						printTokens(" ");
					}
					
					printTokens(token);
				}
				
				// Checks if this is the start of tag and prints the token 
				// after the beginning tag
				else if(counter > 0 && isBeginTag(tokens[counter - 1]))
				{
						printTokens(token);
				}
				
				// Checks if token is a puncuation and prints it out
				else if(isPunctuation(token))
				{
						printTokens(token);
				}
				
				// Prints out normal text
				else
				{
					if(lineLength > 0)
					{
						printTokens(" ");
					}
						
					printTokens(token);
				}
					
			}
			
			counter++;
		}
	}
	
	/**
	 * This method uses conditionals to print out texts dependent on their
	 * HTML tag. These tags include: <b>, <i>, printing normal tags, and 
	 * headers using isHeaders().
	 * 
	 * @param token		Input token
	 */
	public void printTokens(String token)
	{
		// Checks if the token is a new line -> prints blank lines
		if (token.length() == 1 && ((int)(token.charAt(0))) == 10)
		{
			browser.println();
			browser.println();
			lineLength = 0;
		}
		
		// Checks if isBold is true -> prints tokens in bold
		else if (isBold)
		{
			if (lineLength >= HEADER_4_MAX 
				|| lineLength + token.length() >= HEADER_4_MAX)
			{
				browser.println();
				lineLength = 0;
			}
			
			if (lineLength != 0 || !token.equals(" "))
			{
				browser.printBold(token);
				lineLength += token.length();
			}
		}
		
		// Checks if isItalic is true -> prints tokens in italic
		else if (isItalic)
		{
			if (lineLength >= HEADER_4_MAX 
				|| lineLength + token.length() >= HEADER_4_MAX)
			{
				browser.println();
				lineLength = 0;
			}
			
			if (lineLength != 0 || !token.equals(" "))
			{
				browser.printItalic(token);
				lineLength += token.length();
			}
		}
		
		// Checks if the token is a header -> isHeaders prints tokens in 
		// header format
		else if (isHeaders(token))
		{
			token = "";
		}
		
		// Prints normal text
		else
		{
			if (lineLength >= HEADER_4_MAX 
				|| lineLength + token.length() >= HEADER_4_MAX)
			{
				browser.println();
				lineLength = 0;
			}
			
			if (lineLength!= 0 || !token.equals(" "))
			{
				browser.print(token);
				lineLength += token.length();
			}
			
		}
	}
	
	/**
	 * This method uses conditionals to print out texts if their HTML tag. 
	 * is a header tag. These tags include: <h1>, <h2>, <h3>, <h4>, <h5>, 
	 * and <h6>. 
	 * 
	 * @param token		Input token
	 * @return			If the string is a header or not	
	 */
	public boolean isHeaders (String token)
	{
		// If token is Header 1, then print the token is printed in 
		// Header 1 format
		if (isHeader[0])
		{			
			if (lineLength >= HEADER_1_MAX 
				|| lineLength + token.length() >= HEADER_1_MAX)
			{
				browser.println();
				lineLength = 0;
			}
			
			lineLength += token.length();
			browser.printHeading1(token);
			
			return true;
		}
		
		// If token is Header 2, then print the token is printed in 
		// Header 2 format
		else if (isHeader[1])
		{
			if (lineLength >= HEADER_2_MAX 
				|| lineLength + token.length() >= HEADER_2_MAX)
			{
				browser.println();
				lineLength = 0;
			}
			
			lineLength += token.length();
			browser.printHeading2(token);
			
			return true;
		}
		
		// If token is Header 3, then print the token is printed in 
		// Header 3 format
		else if (isHeader[2])
		{
			if (lineLength >= HEADER_3_MAX 
				|| lineLength + token.length() >= HEADER_3_MAX)
			{
				browser.println();
				lineLength = 0;
			}
			
			lineLength += token.length();
			browser.printHeading3(token);
			
			return true;
		}
		
		// If token is Header 4, then print the token is printed in 
		// Header 4 format
		else if (isHeader[3])
		{
			if (lineLength >= HEADER_4_MAX 
				|| lineLength + token.length() >= HEADER_4_MAX)
			{
				browser.println();
				lineLength = 0;
			}
			
			lineLength += token.length();
			browser.printHeading4(token);
			
			return true;
		}
		
		// If token is Header 5, then print the token is printed in 
		// Header 5 format
		else if (isHeader[4])
		{
			if (lineLength >= HEADER_5_MAX 
				|| lineLength + token.length() >= HEADER_5_MAX)
			{
				browser.println();
				lineLength = 0;
			}
			
			lineLength += token.length();
			browser.printHeading5(token);
			
			return true;
		}
		
		// If token is Header61, then print the token is printed in 
		// Header 6 format
		else if (isHeader[5])
		{
			if (lineLength >= HEADER_6_MAX 
				|| lineLength + token.length() >= HEADER_6_MAX)
			{
				browser.println();
				lineLength = 0;
			}
			
			lineLength += token.length();
			browser.printHeading6(token);
			
			return true;
		}
		
		// If token is not a header 
		return false;
	}
	
	/**
	 * Checks if a given string is punctuation
	 * 
	 * @param str		Input string
	 * @return			If the string is a piece of punctuation or not
	 */
	public boolean isPunctuation(String str)
	{
		switch (str) 
		{
			case ".":
			case ",":
			case ";":
			case "(":
			case ")":
			case "?":
			case "!":
			case "=":
			case "&":
			case "~":
			case "+":
			case "-":
			case ":":
				return true;
			default:
				return false;
		}
	}
	
	/**
	 * Checks if a string is a begin tag
	 * 
	 * @param str		Input string
	 * @return			If the string is a begin tag or not
	 */
	public boolean isBeginTag(String str)
	{
		switch (str) 
		{
			case "<p>":
			case "<b>":
			case "<i>":
			case "<br>":
			case "<hr>":
			case "<q>":
			case "<h1>":
			case "<h2>":
			case "<h3>":
			case "<h4>":
			case "<h5>":
			case "<h6>":
			case "<pre>":
			case "<html>":
			case "<body>":
				return true;
			default:
				return false;
		}
	}
	
	/**
	 * Checks if a string is an end tag
	 * 
	 * @param str		Input string
	 * @return			If the string is an end tag or not
	 */
	public boolean isEndTag(String str)
	{
		switch (str) 
		{
			case "</p>":
			case "</b>":
			case "</i>":
			case "</h1>":
			case "</h2>":
			case "</h3>":
			case "</h4>":
			case "</h5>":
			case "</h6>":
			case "</pre>":
			case "</html>":
			case "</body>":
				return true;
			default:
				return false;
		}
	}
}
