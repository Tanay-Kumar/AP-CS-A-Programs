import info.gridworld.actor.Actor;
import info.gridworld.actor.Bug;
import info.gridworld.actor.Flower;
import info.gridworld.actor.Rock;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;
import java.awt.Color;
/**
 * A <code>Jumper</code> is an actor that will jump over Rocks and Flowers
 * and turn.
 */
public class Jumper extends Bug
{
	private int totalMoves;
	private int moves;
	
	/**
	* Constructs a blue Jumper.
	*/
	public Jumper()
	{
		setColor(Color.BLUE);
		totalMoves = -1;
		moves = 0;
	}
	
	/**
	* Constructs a blue Jumper.
	*/
	public Jumper(int totalMovesIn)
	{
		setColor(Color.BLUE);
		totalMoves = totalMovesIn;
		moves = 0;
	}
	
	public void act()
	{
		if (moves >= totalMoves && totalMoves != -1)
		{
			setDirection(getDirection() + Location.HALF_RIGHT);
			moves = 0;
		}
		
		else if (canJump())
		{
			jump();
			moves += 2;
		}
		
		else
		{
			setDirection(getDirection() + Location.HALF_RIGHT);
		}
	}
	
	/**
	* Moves the Jumper forward two locations.
	* The location two in front must be valid or the Jumper will remove
	* itself from the grid.
	*/
	public void jump()
	{	
		if (getGrid() != null)
		{			
			Location loc = getLocation();
			Location next = loc.getAdjacentLocation(getDirection());
			Location twoAway = next.getAdjacentLocation(getDirection());
			
			if (getGrid().isValid(twoAway))
			{
				moveTo(twoAway);
				Blossom bl = new Blossom((int)(Math.random() * 20) + 1);
				bl.putSelfInGrid(getGrid(), loc);
			}
			
			else
			{
				removeSelfFromGrid();
			}
		}
	}
	
	/**
	* Tests whether this Jumper can move forward into a location two in
	* front that is empty or contains a flower.
	* The location one in front must be empty or contain a Rock or a
	* Blossom.
	* @return true if this Jumper can move.
	*/
	public boolean canJump()
	{		
		if (getGrid() == null)
		{
			return false;
		}

		Location loc = getLocation();
		Location next = loc.getAdjacentLocation(getDirection());
		Location twoAway = next.getAdjacentLocation(getDirection());
		
		if (!getGrid().isValid(next) || !getGrid().isValid(twoAway))
		{
			return false;
		}
			
		Actor actor = getGrid().get(twoAway);
		
		if (actor instanceof Blossom || actor instanceof Rock)
		{
			return false;
		}
		
		return (actor == null) || (actor instanceof Flower);
	}
} 
