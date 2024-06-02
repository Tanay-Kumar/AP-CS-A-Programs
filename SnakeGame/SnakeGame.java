/**
 * This class recreates the classic Snake game using a two-dimensional 
 * array and a SinglyLinkedList. In this game, players control a snake on 
 * a grid, aiming to consume targets and expand its tail. The game poses 
 * challenges such as avoiding collisions with the border or itself and 
 * navigating strategically to prevent getting trapped. This class uses 
 * classes like Coordinate, Snake, and SnakeBoard to manage game logic 
 * efficiently. The SnakeGame class orchestrates gameplay, handles
 * user commands, and offers features to save and restore game progress.
 * 
 *	@author	Tanay Kumar
 *	@since	May 14, 2023
 */

import java.util.Scanner;
import java.io.PrintWriter;

public class SnakeGame
{
	private Snake snake;		// the snake in the game
	private SnakeBoard board;	// the game board
	private Coordinate target;	// the target for the snake
	private int score;			// the score of the game
	
	/*	Constructor	*/
	public SnakeGame()
	{
		score = 0;
		snake = new Snake(3, 3);
		board = new SnakeBoard(20, 25);
		board.placeSnake(snake);
		target = createTarget();
	}
	
	/*	Main method	*/
	public static void main(String[] args)
	{
		SnakeGame snakeGame = new SnakeGame();
		snakeGame.run();
	}
	
	/**
	 * This method orchestrates the flow of the game. It begins by printing 
	 * the game introduction, then enters a loop where it prints the game 
	 * board, checks for player moves, and determines if the game is over. 
	 * The loop continues until the game ends. Finally, it prints a closing 
	 * message to thank the player for playing.
	 */
	public void run()
	{
		boolean isRunning = true; // Set a flag to indicate if the game is running
		printIntroduction();
		
		while (isRunning)
		{
			board.printBoard(snake, target); // Print the game board
			isRunning = isMoved(); // Determine if the player has played a move
			
			// Check if the game is over
			if (hasGameEnded())
			{
				isRunning = false; // Set isRunning to false
				board.printBoard(snake, target); // Print the final game board
			}
		}
		
		// Print a thank you message
		System.out.println("Thanks for playing SnakeGame!!!");
	}
	
	/**
	 * This method prompts the user for input and interprets their selection.
	 * It then executes the corresponding action based on the input.
	 * 
	 * @return 		True if the player has made a move, false otherwise
	 */
	public char getSelection()
	{
		char selection = ' '; // Initialize the selection variable
		boolean hasSelected = false; // Indicates if a valid selection has been made
		
		// Continue looping until a valid selection is made
		while(!hasSelected) 
		{
			// Prompt the user for input and display the available commands
			selection = Prompt.getChar("Score: " + score + 
				" (w - North, s - South, d - East, a - West, h - Help)");
			
			// Check if the selection is valid
			if (selection == 'w' || selection == 'd' || selection == 's' 
				|| selection == 'a' || selection == 'q' || selection == 'f' || selection == 'r' 
				|| selection == 'h') 
			{
				hasSelected = true; // Set hasSelected to true if the selection is valid
			}
		}
		
		return selection; // Return the user's selection
	}
	
	/**
	 * This method creates a random valid target on the snake game board.  
	 * A target coordinate is considered valid if it exists on the board 
	 * and does not overlap with the coordinates occupied by the snake.
	 * 
	 * @return 		the randomly generated valid target coordinate
	 */
	public Coordinate createTarget()
	{
		// List of Valid Coordinates
		SinglyLinkedList<Coordinate> coords = new SinglyLinkedList<Coordinate>();
		
		// Iterate over the board to find empty spaces
		for (int i = 0; i < board.getHeight(); i++)
		{
			for (int j = 0; j < board.getWidth(); j++)
			{
				// Check if the space is empty
				if (board.getChar(new Coordinate(i, j)) == ' ')
				{
					coords.add(new Coordinate(i, j)); // Add the coordinate to the list
				}
			}
		}
		
		// Choose a random coordinate from the list of valid coordinates
		return coords.get((int)(Math.random() * coords.size())).getValue();
	}
	
	/**
	 * This method handles the player's move selection by interpreting the 
	 * input character and executing corresponding actions such as moving 
	 * the snake, saving or restoring the game, or quitting the game. The 
	 * method returns a boolean value indicating whether the player has 
	 * played a move that allows the game to continue.
	 * 
	 * @return		true if the player has played a move, false if the game should end
	 */
	public boolean isMoved()
	{
		char selection = getSelection(); // Get the user's selection
		boolean moved = true; // Assume the player has played a move
		
		// Determine the action based on the user's selection
		if (selection == 'w') 
		{
			// Move the snake north
			moved = move(new Coordinate(snake.get(0).getValue().getRow() - 1, 
						snake.get(0).getValue().getCol()));
		} 
		
		else if (selection == 'd') 
		{
			// Move the snake east
			moved = move(new Coordinate(snake.get(0).getValue().getRow(), 
						snake.get(0).getValue().getCol() + 1));
		}
		 
		else if (selection == 's') 
		{
			// Move the snake south
			moved = move(new Coordinate(snake.get(0).getValue().getRow() + 1, 
						snake.get(0).getValue().getCol()));
		} 
			
		else if (selection == 'a') 
		{
			// Move the snake west
			moved = move(new Coordinate(snake.get(0).getValue().getRow(), 
						snake.get(0).getValue().getCol() - 1));
		}
		
		else if (selection == 'q') 
		{
			// Ask for confirmation to quit the game
			char input = Prompt.getChar("\nDo you really want to quit? "
								+  "(yes (y) or no (n)");
			moved = (input != 'y'); // If input is 'y', the player wants to stop
		} 
		
		// Save the game
		else if (selection == 'f') 
		{
			moved = saveGame();
		} 
		
		// Restore the game from a saved file
		else if (selection == 'r') 
		{
			restoreGame();
		} 
		
		// Display the help menu
		else if (selection == 'h') 
		{
			helpMenu();
		}
		
		// Return whether the player has played a move
		return moved;
	}
	
	/**
	 * This method checks if the given coordinate is valid and if the move
	 * will result in a collision with the snake itself. If the move is valid,
	 * the snake's head is updated to the new coordinate. If the snake eats
	 * the target, the score is incremented, and a new target is generated.
	 * If the target generation fails, the method returns false. Otherwise,
	 * the tail of the snake is adjusted accordingly.
	 *
	 * @param coord		The coordinate to which the snake should be moved
	 * @return 			true if the move is successful, false otherwise
	 */
	public boolean move(Coordinate coord)
	{
		// Check if the coordinate is valid or if the snake has collided with itself
		if (!board.isValidLoc(coord) || snake.contains(coord))
		{
			return false; 
		}
		
		// Add the new coordinate to the snake's head
		snake.add(0, coord);
		
		// Check if the snake has eaten the target
		if (target.equals(coord))
		{
			score++;
			target = createTarget();
			
			// Return false if the target generation failed
			if (target == null)
			{
				return false;
			}
		}
		
		// Remove the last segment of the snake
		else
		{
			snake.remove(snake.size() - 1);
		}
		
		// Return true if the move is successful
		return true;
	}
	
	/**
	 * This method checks if the game is over by evaluating two conditions:
	 * 		1. If the number of empty spaces on the board is less than 5
	 * 		2. If none of the adjacent locations around the head of the snake
	 * 	   		are valid or available for movement.
	 * 
	 * Each condition is evaluated separately to determine if the game has ended.
	 * 
	 * @return 		true if the game is over, false otherwise
	 */
	public boolean hasGameEnded()
	{
		// Count the number of empty spaces on the board
		int emptySqs = 0;
		
		for (int i = 1; i < board.getHeight(); i++)
		{
			for (int j = 1; j < board.getWidth(); j++)
			{
				if (board.getChar(new Coordinate(i, j)) == ' ')
				{
				  emptySqs++; 
				}
			} 
		} 
		
		// Check if the number of empty spaces is less than 5
		if (emptySqs < 5)
		{
			return true; // Return true if the game is over
		}
		
		// Check if there are no valid moves for the snake
		Coordinate snakeCoord = snake.get(0).getValue();
		Coordinate adj1 = new Coordinate(snakeCoord.getRow() - 1, 
											snakeCoord.getCol());
		Coordinate adj2 = new Coordinate(snakeCoord.getRow() + 1, 
										snakeCoord.getCol());
		Coordinate adj3 = new Coordinate(snakeCoord.getRow(), snakeCoord.getCol() - 1);
		Coordinate adj4 = new Coordinate(snakeCoord.getRow(), snakeCoord.getCol() + 1);
		
		boolean checkAdj1 = (!board.isValidLoc(adj1) || (board.getChar(adj1) != ' ' 
							&& board.getChar(adj1) != '+'));
		boolean checkAdj2 = (!board.isValidLoc(adj2) || (board.getChar(adj2) != ' ' 
							&& board.getChar(adj2) != '+'));
		boolean checkAdj3 = (!board.isValidLoc(adj3) || (board.getChar(adj3) != ' ' 
							&& board.getChar(adj3) != '+'));
		boolean checkAdj4 = (!board.isValidLoc(adj4) || (board.getChar(adj4) != ' ' 
							&& board.getChar(adj4) != '+'));
		
		// Return true if the game is over
		return checkAdj1 && checkAdj2 && checkAdj3 && checkAdj4;
	}
	
	/**
	 * This method opens a PrintWriter to write to a file named 
	 * "snakeGameSave.txt". It then writes the current game score, the 
	 * target coordinates, and the snake coordinates to the file. Finally, 
	 * it closes the PrintWriter.
	 * 
	 * @return 		true if the game is successfully saved, false otherwise
	 */
	public boolean saveGame()
	{		
		// Open a PrintWriter to write to a file
		PrintWriter writer = FileUtils.openToWrite("snakeGameSave.txt");
		
		// Write game data to the file
		writer.println("Score " + score);
		writer.println("Target");
		writer.println(target.getRow() + " " + target.getCol());
		writer.println("Snake " + snake.size());
		
		for (int i = 0; i < snake.size(); i++)
		{
		  writer.printf("%d %d\n", snake.get(i).getValue().getRow(), 
						snake.get(i).getValue().getCol()); 
		}
		
		// Close the PrintWriter
		writer.close();
		System.out.println("\nGame saved to snakeGameSave.txt\n");
		return true; // Return true indicating successful save
	}

	/**	
	 * This method reads the game state from a saved game file and restores 
	 * it. It opens a Scanner to read from the saved game file, then reads 
	 * the score, target coordinates, and snake coordinates from the file. 
	 * After reading the data, it updates the game state accordingly.
	 */
	public void restoreGame()
	{
		int row = 0;
		int col = 0;
		int numCoords = 0;
		
		// Open a Scanner to read from the saved game file
		Scanner scan = FileUtils.openToRead("snakeGameSave.txt");
		
		scan.next(); // Skip "Score" label
		score = scan.nextInt(); // Read the score
		scan.next(); // Skip "Target" label
		
		// Read target coordinates
		row = scan.nextInt();
		col = scan.nextInt();
		target = new Coordinate(row, col);
		
		scan.next(); // Skip "Snake" label
		numCoords = scan.nextInt(); // Read the number of snake coordinates
		snake.clear(); // Clear the snake
		
		// Read snake coordinates and add them to the snake list
		for (int i = 0; i < numCoords; i++)
		{
			row = scan.nextInt();
			col = scan.nextInt();
			snake.add(new Coordinate(row, col));
		}
		
		scan.close(); // Close the Scanner
	}
	
	/**	Print the game introduction	*/
	public void printIntroduction() {
		System.out.println("  _________              __            ________");
		System.out.println(" /   _____/ ____ _____  |  | __ ____  /  _____/_____    _____   ____");
		System.out.println(" \\_____  \\ /    \\\\__  \\ |  |/ // __ \\/   \\  ___\\__  \\  /     \\_/ __ \\");
		System.out.println(" /        \\   |  \\/ __ \\|    <\\  ___/\\    \\_\\  \\/ __ \\|  Y Y  \\  ___/");
		System.out.println("/_______  /___|  (____  /__|_ \\\\___  >\\______  (____  /__|_|  /\\___  >");
		System.out.println("        \\/     \\/     \\/     \\/    \\/        \\/     \\/      \\/     \\/");
		System.out.println("\nWelcome to SnakeGame!");
		System.out.println("\nA snake @****** moves around a board " +
							"eating targets \"+\".");
		System.out.println("Each time the snake eats the target it grows " +
							"another * longer.");
		System.out.println("The objective is to grow the longest it can " +
							"without moving into");
		System.out.println("itself or the wall.");
		System.out.println("\n");
	}
	
	/**	Print help menu	*/
	public void helpMenu() {
		System.out.println("\nCommands:\n" +
							"  w - move north\n" +
							"  s - move south\n" +
							"  d - move east\n" +
							"  a - move west\n" +
							"  h - help\n" +
							"  f - save game to file\n" +
							"  r - restore game from file\n" +
							"  q - quit");
		Prompt.getString("Press enter to continue");
	}
}
