/**
 * The SnakeBoard class handles the construction of the game board, placement 
 * of the snake and targets, and rendering the board on the screen. It utilizes 
 * a two-dimensional array of characters to represent the game board. The 
 * SnakeGame class relies on the SnakeBoard for displaying the game board 
 * during gameplay.
 *
 *	@author	Tanay Kumar
 *	@since	May 13, 2023
 */
public class SnakeBoard
{
	/*	fields	*/
	private char[][] board;			// The 2D array to hold the board
	
	/*	Constructor	*/
	public SnakeBoard(int height, int width)
	{
		board = new char[height + 2][width + 2];
		createBoard();
	}
	
	/**
	 *	Print the board to the screen.
	 * 
	 * 	@param snake 		the snake to be displayed on the board
     * 	@param target 		the target to be displayed on the board
	 */
	public void printBoard(Snake snake, Coordinate target)
	{
		createBoard(); 
        placeSnake(snake);  // Place the snake on the board
        board[target.getRow()][target.getCol()] = '+';  // Place the target on the board
        
        // Print each cell of the board
		for(int i = 0; i < board.length; i++)
		{
			for (int j = 0; j < (board[0]).length; j++)
			{
				System.out.print(board[i][j] + " "); 
			}
			
			System.out.println();
		}
	}
	
	/**
	 * This method initializes all cells of the board to empty space,
	 * draws borders on the top and bottom rows, draws borders on the left
	 * and right columns, and draws corners.
	 */
	public void createBoard()
	{
		// Initialize all cells of the board to empty space
		for(int i = 0; i < board.length; i++)
		{
			for (int j = 0; j < (board[0]).length; j++)
			{
				board[i][j] = ' '; 
			}
		}
		
		// Draw borders on top and bottom rows
		for(int i = 0; i < board[0].length; i++)
		{
			board[board.length - 1][i] = '-';
			board[0][i] = '-';
		} 
		
		// Draw borders on left and right columns
		for(int i = 0; i < board.length; i++)
		{
			board[i][(board[0]).length - 1] = '|';
			board[i][0] = '|';
		} 
		
		// Draw corners
		board[board.length - 1][(board[0]).length - 1] = '+';
		board[board.length - 1][0] = '+';
		board[0][(board[0]).length - 1] = '+';
		board[0][0] = '+';
	}
	
	/* Helper methods go here	*/
	
	/**
	 * This method checks if the coordinate passed in is a valid location 
	 * for moving the snake on the board relative to the snake's current 
	 * position. It checks if the coordinate is within the boundaries of 
	 * the board.
	 * 
	 * @param coord		the coordinate to check for validity
	 * @return			true if the coord passed in is a valid location 
	 * 					for moving, false otherwise
	 */
	public boolean isValidLoc(Coordinate coord)
	{
		return (coord.getCol() > 0 && coord.getCol() <= getWidth() &&
				coord.getRow() > 0 && coord.getRow() <= getHeight());
	}
	
	/**
	 * This method draws the snake on the snake game board. It iterates 
	 * over the snake's body segments and places them on the board. The 
	 * head of the snake is represented by '@' and the body segments by '*'.
	 * 
	 * @param snake		the snake to place on the board
	 */
	public void placeSnake(Snake snake)
	{
		if (snake.size() >= 1)
		{		
			// Place snake's head at its current position  
			board[snake.get(0).getValue().getRow()][snake.get(0).getValue().getCol()] = '@';

			// Place snake's body segments at their respective positions
			for (int i = 1; i < snake.size(); i++)
			{
				board[snake.get(i).getValue().getRow()][snake.get(i).getValue().getCol()] = '*'; 
			}
		}
	}
	
	/*	Accessor methods	*/
	
	// Returns the height of the snake board
	public int getHeight()
	{
		return board.length - 2;
	}
	
	// Returns the width of the snake board
	public int getWidth()
	{
		return board[0].length - 2;
	}
	
	// Gets and returns the character at the coordinate passed in
	public char getChar(Coordinate coord)
	{
		return board[coord.getRow()][coord.getCol()];
	}
	
	/********************************************************/
	/********************* For Testing **********************/
	/********************************************************/
	
	public static void main(String[] args)
	{
		// Create the board
		int height = 10, width = 15;
		SnakeBoard sb = new SnakeBoard(height, width);
		// Place the snake
		Snake snake = new Snake(3, 3);
		// Place the target
		Coordinate target = new Coordinate(1, 7);
		// Print the board
		sb.printBoard(snake, target);
	}
}
