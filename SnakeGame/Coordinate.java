/**
 *	A coordinate on a grid. Integer XY values.
 *
 *	@author Mr Greenstein and Tanay Kumar
 *  @since May 13th, 2024
 */
public class Coordinate implements Comparable<Coordinate>
{
	private int row; 	// Row value of coordinate
	private int col; 	// Column value of coordinate

	public Coordinate(int rowIn, int colIn)
	{
		row = rowIn;
		col = colIn;
	}
	
	/* Accessor methods */
	/**
	 * Returns the row value of the coordinate object.
	 *
	 * @return 		The row value of the coordinate object
	 */
	public int getRow() 
	{
		return row;
	}

	/**
	 * Returns the column value of the coordinate object.
	 *
	 * @return 		The column value of the coordinate object
	 */
	public int getCol() 
	{
		return col;
	}
	
	@Override
	/**
	 * Checks whether this Coordinate object is equal to another object.
	 * 
	 * @param other 	the object to compare with this Coordinate
	 * @return 			true if the object is a Coordinate with the same 
	 * 					row and column values as this Coordinate, false otherwise
	 */
	public boolean equals(Object other)
	{
		if(other != null && other instanceof Coordinate &&
		  (row == ((Coordinate)other).row) &&
		  (col == ((Coordinate)other).col))
			return true;
		
		return false;
	}
	
	@Override
	/**
	 *	Coordinate is greater when:
	 *	1. x is greater or
	 *	2. x is equal and y is greater
	 *	3. otherwise Coordinates are equal
	 *	@return		negative if less than, 0 if equal, positive if greater than
	 */
	public int compareTo(Coordinate other) {
		if (! (other instanceof Coordinate))
			throw new IllegalArgumentException("compareTo not Coordinate object");
		if (row > ((Coordinate)other).row || row < ((Coordinate)other).row)
			return row - ((Coordinate)other).row;
		if (col > ((Coordinate)other).col || col < ((Coordinate)other).col)
			return col - ((Coordinate)other).col;
		return 0;
	}
	
	public String toString()
	{	return "[ " + row + ", " + col + "]";  }
}
