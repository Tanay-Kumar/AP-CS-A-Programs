import java.util.List;       // used by expression evaluator
import java.util.ArrayList;

/**
 * SimpleCalc is a basic calculator application. It supports operations 
 * according to the PEMDAS rule. Users can perform calculations, assign 
 * values to variables, and retrieve these values to use in further calculations.
 * The application utilizes stacks for operation order and supports 
 * adding new variables dynamically. Commands are provided for help, listing 
 * variables, and quitting. SimpleCalc initiates with constants 'e' 
 * and 'pi' for convenience.
 *
 *	@author	Tanay Kumar
 *	@since	12th March, 2024
 */
public class SimpleCalc {
    
    private ExprUtils utils;   // expression utilities
    
    private ArrayStack<Double> valueStack;         // value stack
    private ArrayStack<String> operatorStack;      // operator stack
    private List<Identifier> identifiers;          // Store variables and their values

    // constructor 
    public SimpleCalc() 
    {
        utils = new ExprUtils();
        valueStack = new ArrayStack<Double>();
        operatorStack = new ArrayStack<String>();
        identifiers = new ArrayList<Identifier>();
    }
    
    public static void main(String[] args) {
        SimpleCalc sc = new SimpleCalc();
        sc.run();
    }
    
    /**
	 * Initiates the calculator application. Displays a welcome message, 
	 * runs the calculator loop, and shows a goodbye message upon exit.
	 */
    public void run() {
        System.out.println("\nWelcome to SimpleCalc!!!");
        runCalc();
        System.out.println("\nThanks for using SimpleCalc! Goodbye.\n");
    }
    
    /**
	 * Processes user input in the calculator loop, supporting mathematical 
	 * expressions, variable assignments, and special commands. Initializes 
	 * with constants 'e' and 'pi' (Pi). Accepts expressions to be calculated, 
	 * variable assignments using "variable = expression", and commands 
	 * for help ('h') and listing variables ('l'). Continues prompting and 
	 * processing input until 'q' is entered to quit.
	 */
    public void runCalc() 
    {
		identifiers.add(new Identifier("e", Math.E));
		identifiers.add(new Identifier("pi", Math.PI));
		String input = Prompt.getString("");
		
		// Main loop: process input until 'q' is received
		while (!input.equals("q")) 
		{
			// Display help information
			if (input.equals("h")) 
			{
				printHelp();
			} 
			
			// List all defined variables and their values
			else if (input.equals("l"))
			{
				printVariables();
			}
			
			// Handle variable assignment
			else if (input.indexOf("=") != -1) 
			{ 
				handleAssignment(input);
			} 
			
			// Handle expressions or variable retrieval
			else 
			{ 
				// Assume input is a variable name initially
				boolean isVariableName = true;
				
				// Check if input contains non-letter characters
				for (int i = 0; i < input.length(); i++) 
				{
					if (!Character.isLetter(input.charAt(i))) 
					{
						isVariableName = false;
						i = input.length();
					}
				}
				
				if (isVariableName) 
				{
					// Print value for single variable name inputs
					for (Identifier identifier : identifiers) 
					{
						if (identifier.getName().equals(input)) 
						{
							System.out.println(input + " = " + identifier.getValue());
						}
					}
				} 
				
				// Evaluate and print the result of a mathematical expression
				else 
				{
					double val = evaluateExpression(utils.tokenizeExpression(input));
					System.out.println(val);
				}
			}
			
			input = Prompt.getString("");
		}
	}
	
	/**
	 * Handles variable assignment from a string input. Parses the input 
	 * to separate the variable name and its associated value or expression.
	 * If the expression is numeric, it's directly assigned; otherwise, 
	 * the expression is evaluated. Updates existing variables or adds 
	 * new ones with the determined value.
	 *
	 * @param input 	A variable
	 */
    public void handleAssignment(String input) 
    {
		// Extract variable name from the input string before the '='
		String varName = input.substring(0, input.indexOf('=')).trim();
		
		// Extract the expression part from the input string after the '='
		String expression = input.substring(input.indexOf('=') + 1).trim();
		double varValue = 0.0;
		boolean found = false; // Flag to check if variable already exists
		
		// Evaluate the expression or parse the direct number
		if (isNumber(expression)) 
		{
			varValue = Double.parseDouble(expression);
		} 
		
		else 
		{
			varValue = evaluateExpression(utils.tokenizeExpression(expression));
		}
		
		// Iterate over existing identifiers to find a match and update its value
		for (int i = 0; i < identifiers.size(); i++)
		{
			if (identifiers.get(i).getName().equals(varName)) 
			{
				identifiers.get(i).setValue(varValue);
				found = true;
				i = identifiers.size();
			}
		}
		
		// If the variable was not found, add a new identifier
		if (!found) 
		{
			identifiers.add(new Identifier(varName, varValue));
		}

		// Print the result of the assignment
		System.out.printf("%4s = %s\n", varName, varValue);
	}
	
	/**	Print help */
	public void printHelp() {
		System.out.println("Help:");
		System.out.println("  h - this message\n  q - quit\n");
		System.out.println("Expressions can contain:");
		System.out.println("  integers or decimal numbers");
		System.out.println("  arithmetic operators +, -, *, /, %, ^");
		System.out.println("  parentheses '(' and ')'");
	}
	
	/**	Print Variables */
	public void printVariables() 
	{
		System.out.println("Variables:");
		
		for (Identifier identifier : identifiers)
		{
			System.out.printf("%-7s= %5f\n", identifier.getName(), 
											identifier.getValue());
		}
	}
	
	/**
	 * 	Evaluates a mathematical expression given as a list of string tokens. 
	 * 	The method follows the PEMDAS rule to ensure the correct order of 
	 * 	operations. The evaluation is performed using two stacks: one for 
	 * 	values (numbers) and one for operators (including parentheses). 
	 * 	The method handles parentheses by performing operations within them
	 * 	first and supports basic arithmetic operations between two operands.
	 *
	 *	@param tokens	a List of String tokens making up an arithmetic expression
	 *	@return			a double value of the evaluated expression
	 */
	public double evaluateExpression(List<String> tokens) 
	{
		// Loop through each token in the input list
		for (String token : tokens)
		 {
			// If the current token is an opening parenthesis, push it onto the operator stack
            if (token.equals("(")) 
            {
                operatorStack.push(token);
            } 
            
            // If the current token is a closing parenthesis
            else if (token.equals(")")) 
            {
                String topOperator = operatorStack.pop();
                
                // Get operators from the operator stack until an opening parenthesis is found
                while (!topOperator.equals("("))
                {
                    // Get the top two operands from the value stack for the operation
					double secondOperand = valueStack.pop();
					double firstOperand = valueStack.pop();
					
					// Evaluate the operation and put the result back onto the value stack
					double result = evaluate(topOperator, firstOperand, secondOperand);
					valueStack.push(result);
					
					// Get the next operator from the operator stack
					topOperator = operatorStack.pop();
                }
                
            } 
            
            // Modify the evaluateExpression method to support variable lookup
			// Replace the part where you push numbers onto the stack with:
			else if (isNumber(token) || Character.isLetter(token.charAt(0))) 
			{
				// Check if token is a variable
				boolean foundVariable = false;
				double variableValue = 0.0;
				
				// Loop through identifiers to find a match
				for (int i = 0; i < identifiers.size(); i++) 
				{
					Identifier identifier = identifiers.get(i);
					if (identifier.getName().equals(token)) 
					{
						variableValue = identifier.getValue();
						valueStack.push(variableValue);
						foundVariable = true;
						i = identifiers.size();
					}
				}
				
				// Push number onto the stack
				if (!foundVariable) 
				{
					valueStack.push(Double.parseDouble(token)); 
				}
			}

            // If the current token is an operator
            else if (utils.isOperator(token.charAt(0))) 
            {
				// While there is an operator on the top of the operator stack with higher or equal precedence
                while (!operatorStack.isEmpty() && hasPrecedence(token, operatorStack.peek())) 
                {
                    // Get the top two operands from the value stack for the operation
                    String topOperator = operatorStack.pop();
					double secondOperand = valueStack.pop();
					double firstOperand = valueStack.pop();
					
					// Evaluate the operation and put the result back onto the value stack
					double result = evaluate(topOperator, firstOperand, secondOperand);
					valueStack.push(result);
                }
                
                // Put the current operator onto the operator stack
                operatorStack.push(token);
            }
        }
        
        // After going through all tokens, apply remaining operations to the remaining values
        while (!operatorStack.isEmpty()) 
        {
            // Pop the operator from the operator stack and the top two operands from the value stack
			String topOperator = operatorStack.pop();
			double secondOperand = valueStack.pop();
			double firstOperand = valueStack.pop();
			
			// Evaluate the operation and push the result back onto the value stack
			double result = evaluate(topOperator, firstOperand, secondOperand);
			valueStack.push(result);
        }

		// The top of the value stack now contains the result of the expression
        return valueStack.peek();
	}
	
	/**
	 * 	This method evaluates a mathematical operation between two operands 
	 *  based on the operator provided.
	 *
	 * 	@param operator 	A string representing the mathematical operator 
	 * 	@param num1 		The first operand in the operation 
	 * 	@param num2 		The second operand in the operation
	 * 	@return 			The result of applying the specified operation
	 */
	public double evaluate(String operator, double num1, double num2)
	{	
		// Handles exponentiation
		if (operator.equals("^")) 
		{
			return Math.pow(num1, num2);
		} 
		
		// Handles multiplication
		else if (operator.equals("*")) 
		{
			return num1 * num2;
		} 
		
		// Handles division
		else if (operator.equals("/")) 
		{
			return num1 / num2;
		} 
		
		// Handles modulus
		else if (operator.equals("%")) 
		{
			return num1 % num2;
		}
		
		// Handles addition
		else if (operator.equals("+")) 
		{
			return num1 + num2;
		} 
		
		// Handles subtraction
		else if (operator.equals("-")) 
		{
			return num1 - num2;
		}

		// Default return value if an unrecognized operator is provided.
		return 0;
	}

	/**
	 * Determines if a given string is a valid representation of a number
	 * 
	 * @param str the string to evaluate
	 * @return true if the string represents a number; false otherwise
	 */
	public boolean isNumber(String str) 
	{        
        try 
        {
            double num = Double.parseDouble(str);
            return true;
        } 
        
        catch (NumberFormatException e) 
        {
            return false;
        }
    }
    
	/**
	 *	Precedence of operators
	 *	@param op1	operator 1
	 *	@param op2	operator 2
	 *	@return		true if op2 has higher or same precedence as op1; false otherwise
	 *	Algorithm:
	 *		if op1 is exponent, then false
	 *		if op2 is either left or right parenthesis, then false
	 *		if op1 is multiplication or division or modulus and 
	 *				op2 is addition or subtraction, then false
	 *		otherwise true
	 */
	private boolean hasPrecedence(String op1, String op2) {
		if (op1.equals("^")) return false;
		if (op2.equals("(") || op2.equals(")")) return false;
		if ((op1.equals("*") || op1.equals("/") || op1.equals("%")) 
				&& (op2.equals("+") || op2.equals("-")))
			return false;
		return true;
	}
	 
}
