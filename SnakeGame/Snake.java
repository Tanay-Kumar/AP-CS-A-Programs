/**
 *	A SinglyLinkedList of Coordinate objects representing
 *	a snake on a two-dimensional grid.
 *
 *	@author	Tanay Kumar
 *	@since	May 12, 2023
 */

public class Snake extends SinglyLinkedList<Coordinate> 
{		
	/**	Constructor for making a Snake that is 5 grids high facing north.
	 *	Places the snake head at Coordinate location and the tail below.
	 *	Precondition: To place the Snake, the board must have at
	 *				least location.getRow() + 4 more rows.
	 */
	public Snake(Coordinate location) 
	{
        for (int i = 0; i < 5; i++)
        {
            Coordinate coord = new Coordinate(location.getRow() + i, location.getCol());
            this.add(coord);
        }
    }
    
    /** 
     * Overloaded Constructor
     */ 
    public Snake(int row, int col)
    {
		this(new Coordinate(row, col));
	}
}
