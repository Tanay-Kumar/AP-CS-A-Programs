import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *	SudokuSolver - Solves an incomplete Sudoku puzzle using recursion and backtracking
 *
 *	@author	
 *	@since	
 *
 */
public class SudokuSolver {

	private int[][] puzzle;		// the Sudoku puzzle
	
	private String PUZZLE_FILE = "puzzle1.txt";	// default puzzle file
	
	/* Constructor */
	public SudokuSolver() {
		puzzle = new int[9][9];
		// fill puzzle with zeros
		for (int row = 0; row < puzzle.length; row++)
			for (int col = 0; col < puzzle[0].length; col++)
				puzzle[row][col] = 0;
	}
	
	/**
	 * Main method
	 * 
	 * @param args	User INput on which puzzle they want to run
	 */ 
	public static void main(String[] args) {
		SudokuSolver sm = new SudokuSolver();
		sm.run(args);
	}
	
	/**
	 * This method checks which puzzle the user want to run and loads the 
	 * puzzle. If the user doesn't input something, the program defaults
	 * to puzzle 1. The method prints the unsolved puzzle, calls the solving
	 * method, and then prints the solved puzzle.
	 * 
	 * @param args	User INput on which puzzle they want to run
	 */
	public void run(String[] args) {
		// get the name of the puzzle file
		String puzzleFile = PUZZLE_FILE;
		if (args.length > 0) puzzleFile = args[0];
		
		System.out.println("\nSudoku Puzzle Solver");
		// load the puzzle
		System.out.println("Loading puzzle file " + puzzleFile);
		loadPuzzle(puzzleFile);
		printPuzzle();
		// solve the puzzle starting in (0,0) spot (upper left)
		solvePuzzle(0, 0);
		printPuzzle();
	}
	
	/**	Load the puzzle from a file
	 *	@param filename		name of puzzle file
	 */
	public void loadPuzzle(String filename) {
		Scanner infile = FileUtils.openToRead(filename);
		for (int row = 0; row < 9; row++)
			for (int col = 0; col < 9; col++)
				puzzle[row][col] = infile.nextInt();
		infile.close();
	}
	
	/**
	 * This method recursively navigates through the Sudoku puzzle, first 
	 * checking if the current row index equals 9, indicating completion 
	 * of the puzzle. If so, it returns true. If the cell encountered in 
	 * filled , the method proceeds to the next cell to avoid overwriting 
	 * existing numbers. It iterates through numbers 1 to 9, attempting 
	 * to place each in the current cell while ensuring there are no conflicts 
	 * in rows, columns, or 3x3 subgrids. If a valid number placement is 
	 * found, the method recursively calls itself to progress to the next 
	 * cell. In case no valid placement exists, it backtracks to the 
	 * previous cell, exploring alternative numbers until a solution is 
	 * either found or all possibilities exhausted, returning true or 
	 * false accordingly.
	 * 
	 * @param row 	the current row index being processed
	 * @param col 	the current column index being processed
	 * @return 		True if the puzzle is solved successfully, false otherwise
	 */
	public boolean solvePuzzle(int row, int col) 
	{
		int nextRow = 0;
		int nextCol = 0;
		
		// Solved the puzzle
        if (row == 9) 
        {
			return true;
		}

		if (puzzle[row][col] != 0) 
		{
			// Cell is already filled, move to the next cell
			if (col == 8) 
			{
				nextRow = row + 1;
			} 
			
			// Stay in the current row if not at the end
			else 
			{
				nextRow = row;
			}
				
			nextCol = (col + 1) % 9;
			return solvePuzzle(nextRow, nextCol);
		}

		for (int num = 1; num <= 9; num++) 
		{
			// Check if 'num' is not present in the current row, column, and 3x3 grid
			if (!isInRow(row, num) && !isInColumn(col, num) &&
               !isInBox(row - row % 3, col - col % 3, num)) 
			{
				puzzle[row][col] = num;

				// Move to the next cell
				if (col == 8) 
				{
					nextRow = row + 1;
				} 
				
				// Stay in the current row if not at the end
				else 
				{
					nextRow = row;
				}

				nextCol = (col + 1) % 9;

				if (solvePuzzle(nextRow, nextCol)) 
				{
					return true;
				}

				// Backtrack if placing the number doesn't lead to a solution
				puzzle[row][col] = 0;
			}
		}

		return false;
    }

	/**
     * This method checks if a number is used in the current row. The method 
     * iterates through each column in the given row of the Sudoku puzzle 
     * and checks the value of the cell against the target number. If the 
     * target number is found in any cell of the row, the method returns true; 
     * otherwise, it returns false.
     *
     * @param row    The current row.
     * @param num    The number to be checked.
     * @return		 True if the number is used in the row, false otherwise.
     */
    public boolean isInRow(int row, int num) 
    {
        for (int j = 0; j < 9; j++)
         {
            if (puzzle[row][j] == num) 
            {
                return true;
            }
        }
        
        return false;
    }

	/**
     * This method checks if a number is used in the current column. The 
     * method iterates through each row in the given column of the Sudoku 
     * and checks the value of the cell against the target number. If the 
     * target number is found in any cell of the column, the method returns true; 
     * otherwise, it returns false.
     * 
     * @param col    The current column.
     * @param num    The number to be checked.
     * @return 		 True if the number is used in the column, false otherwise.
     */
    public boolean isInColumn(int col, int num) 
    {
        for (int i = 0; i < 9; i++) 
        {
            if (puzzle[i][col] == num) 
            {
                return true;
            }
        }
        
        return false;
    }

    /**
     * This method checks if a number is used in the 3x3 box starting 
     * from the given position. The method iterates through the 3x3 box 
     * starting from the specified position in the Sudoku puzzle and checks 
     * if the value of the cell against the target number. If the target 
     * number is found in any cell of the box, the method returns true; 
     * otherwise, it returns false.
     *
     * @param boxStartRow  The starting row of the 3x3 box.
     * @param boxStartCol  The starting column of the 3x3 box.
     * @param num          The number to be checked.
     * @return			   True if the number is used in the box, false otherwise.
     */
    public boolean isInBox(int boxStartRow, int boxStartCol, int num) 
    {
        for (int i = 0; i < 3; i++) 
        {
            for (int j = 0; j < 3; j++) 
            {
                if (puzzle[i + boxStartRow][j + boxStartCol] == num) 
                {
                    return true;
                }
            }
        }
        
        return false;
    }

	/**
	 *	printPuzzle - prints the Sudoku puzzle with borders
	 *	If the value is 0, then print an empty space; otherwise, print the number.
	 */
	public void printPuzzle() {
		System.out.print("  +-----------+-----------+-----------+\n");
		String value = "";
		for (int row = 0; row < puzzle.length; row++) {
			for (int col = 0; col < puzzle[0].length; col++) {
				// if number is 0, print a blank
				if (puzzle[row][col] == 0) value = " ";
				else value = "" + puzzle[row][col];
				if (col % 3 == 0)
					System.out.print("  |  " + value);
				else
					System.out.print("  " + value);
			}
			if ((row + 1) % 3 == 0)
				System.out.print("  |\n  +-----------+-----------+-----------+\n");
			else
				System.out.print("  |\n");
		}
	}
}
