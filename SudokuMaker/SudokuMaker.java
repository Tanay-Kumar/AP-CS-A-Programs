/**
 *	SudokuMaker - Creates a Sudoku puzzle using recursion and backtracking
 *
 *	@author Tanay Kumar
 *	@version January 25th, 2023
 *
 */
import java.io.PrintWriter;

public class SudokuMaker
{
	private int[][] puzzle; // This is a 2D array of each value of the sudoku
	
	public SudokuMaker ()
	{
		puzzle = new int[9][9];
	}
	
    public static void main(String[] args) 
    {
		SudokuMaker sm = new SudokuMaker();
        sm.solveSudoku(0,0);
        sm.printPuzzle();
    }
	
	/**
     * This method fills in the Sudoku puzzle using a brute force method
     * and recursion. The method checks if the current row is equal to 9,
     * which indicates that the entire puzzle has been filled (base case). 
     * If so, the method returns true, telling that the puzzle has been 
     * solved successfully. If the current cell is not empty, then the 
     * row is moved. If the current column is at the end of the row, 
     * it moves to the next row; otherwise, it stays on the current row 
     * and moves to the next column.If the current cell is empty, the method 
     * iterates over numbers 1 to 9 and attempts to place each number in 
     * the cell, one at a time. For each number, the method checks if placing 
     * that number in the current cell would violate Sudoku rules using 
     * isInRow(), isInColumn(), and isInBox(). If the number is valid 
     * for the current cell, it is placed in the cell, and the method 
     * recursively calls itself to move to the next cell. If placing the 
     * number does not lead to a solution, the method backtracks by 
     * resetting the cell to. If none of the numbers from 1 to 9 work for 
     * the current cell, the method returns false, indicating that the 
     * current path does not lead to a solution.
     *
     * @param row    The current row.
     * @param col    The current column.
     * @return True  if the puzzle is solved, false otherwise.
     */
    public boolean solveSudoku(int row, int col) 
    {
		int nextRow = 0;
		int nextCol = 0;
		
		// Solved the puzzle
        if (row == 9) 
        {
            return true;
        }
        
        // Cell is already filled, move to the next cell
        if (puzzle[row][col] != 0) 
        {
            if (col == 8) 
            {
				nextRow = row + 1;
			} 
			
			else 
			{
				nextRow = row;
			}
			
			nextCol = (col + 1) % 9;
			return solveSudoku(nextRow, nextCol);
        }
        
        // Iterate over numbers 1 to 9 and try placing each number in 
        // the current cell
        for (int i = 1; i <= puzzle[row].length; i++) 
        {
			int num = (int)(Math.random() * 9) + 1;
			
            if (!isInRow(row, num) && !isInColumn(col, num) &&
               !isInBox(row - row % 3, col - col % 3, num)) 
            {
                puzzle[row][col] = num;
                
                if (col == 8) 
				{
					nextRow = row + 1;
				} 
				
				else 
				{
					nextRow = row;
				}
				
				nextCol = (col + 1) % 9;

                if (solveSudoku(nextRow, nextCol)) 
                {
                    return true;
                }

                // Backtrack if placing the number doesn't lead to a solution
                puzzle[row][col] = 0;
            }
        }
        
        // None of the numbers 1 to 9 worked in the current cell, 
        // 	backtrack to a previous cell
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
    
    public void insertRandomZeros() {
        double threshold = 0.3; // Adjust the threshold as needed

        for (int i = 0; i < puzzle.length; i++) {
            for (int j = 0; j < puzzle[i].length; j++) {
                if (Math.random() < threshold) {
                    puzzle[i][j] = 0;
                }
            }
        }
    }
    
    /**
     * This method prints the Sudoku puzzle. The method first, prints the 
     * top border of the Sudoku puzzle, dividing it into 3x3 boxes. Then 
     * it iterates through each row and column of the Sudoku puzzle checks 
     * the value and prints it. If the value is 0, it prints a blank space.
     * Horizontal dividers separting boxes are printed every 3 rows and a 
     * space is printed after each row.
     */
    public void printPuzzle() 
    {
		PrintWriter writer = FileUtils.openToWrite("puzzle3.txt");
		insertRandomZeros();
		//~ System.out.print("  +-----------+-----------+-----------+\n");
		//~ String value = "";
		//~ for (int row = 0; row < puzzle.length; row++) {
			//~ for (int col = 0; col < puzzle[0].length; col++) {
				//~ // if number is 0, print a blank
				//~ if (puzzle[row][col] == 0) value = " ";
				//~ else value = "" + puzzle[row][col];
				//~ if (col % 3 == 0)
					//~ System.out.print("  |  " + value);
				//~ else
					//~ System.out.print("  " + value);
			//~ }
			//~ if ((row + 1) % 3 == 0)
				//~ System.out.print("  |\n  +-----------+-----------+-----------+\n");
			//~ else
				//~ System.out.print("  |\n");
		//~ }
		
		for (int i = 0; i < puzzle.length; i++) {
            for (int j = 0; j < puzzle[i].length; j++) {
                writer.print(puzzle[i][j] + " ");
            }
            writer.println();
        }
        
        writer.close();
	}
}
