import info.gridworld.actor.Bug;
import info.gridworld.grid.Location;

/**
 * A <code>ZBug</code> traces out a "Z" of a given size. <br />
 * The implementation of this class is testable on the AP CS A and AB exams.
 * 
 * @author Tanay Kumar
 * @since 18th March, 2024
 */
public class ZBug extends Bug
{
	private int segmentLength; // the number of flowers in each segment
	private int steps; // the number of steps in the current side
	private int segment; // which segment of the Z the ZBug is on 
	
	/**
     * Constructs a z bug that traces a square of a given side length
     * @param length the side length
     */
	public ZBug(int length)
	{
		setDirection(Location.EAST);
		steps = 0;
		segment = 1;
		segmentLength = length;
	}
	
	/**
     * Moves to the next location of the square.
     */
	public void act()
	{
		if (segment <= 3 && steps < segmentLength)
		{
			if (canMove())
			{
				move();
				steps++;
			}
		}
		
		else if (segment == 1)
		{
			setDirection(Location.SOUTHWEST);
			steps = 0;
			segment++;
		}
		
		else if (segment == 2)
		{
			setDirection(Location.EAST);
			steps = 0;
			segment++;
		}
	}
} 
