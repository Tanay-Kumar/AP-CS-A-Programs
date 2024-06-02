import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;
import info.gridworld.actor.Critter;
import info.gridworld.actor.Actor;
import java.awt.Color;
import java.util.ArrayList;

/** 
 * The Chicken class represents a Chicken actor in the GridWorld Chicken 
 * Coop simulation. It extends the Critter class and simulates the life
 * cycle of a chicken, including aging, movement, egg-laying, and death.
 * 	
 * 	@author Tanay Kumar
 * 	@since 4th April, 2024
 */
public class Chicken extends Critter 
{
	private int steps; // Number of steps

	/** Constructor */
	public Chicken() 
	{
		steps = 0;
		setColor(Color.WHITE);
	}

	/** Overriden method */
	public void makeMove(Location loc) 
	{
		// When a Chicken reaches 300 steps, it dies and is replaced with 
		// a Tombstone
		if (steps >= 300) 
		{
			Tombstone ts = new Tombstone();
			ts.putSelfInGrid(getGrid(), getLocation());
		} 
	
		// Determines movement and action based on the Chicken's age
		else if ((steps >= 200 && steps % 2 == 0) || (steps >= 280 && steps % 4 == 0) 
				|| steps < 200) 
		{
			// Decides between moving or changing direction
			if ((int)(Math.random() * 2) == 0 && isLocValid()) 
			{
				moveTo(getLocation().getAdjacentLocation(getDirection()));

				// Young Chickens have 0.05% chance to lay eggs 
				if (steps <= 200) 
				{ 
					if (Math.random() < 0.05) 
					{
						Egg egg = new Egg();
						egg.putSelfInGrid(getGrid(), loc);
					}
				}
			}
			
			// Changes direction randomly
			else 
			{
				int dir = getDirection() + (int)(Math.random() * 8) * 45; 
				setDirection(dir);
			}

			// Gradually darkens the color of elderly Chickens
			if (steps >= 280 && steps % 4 == 0) 
			{
				setColor(getColor().darker());
			}
		}
		
		steps++;
	}

	/** 
	 * 	Checks if the location in front of the Chicken is valid for movement.
	 * 
	 *  @return 		true if location in front is valid, false otherwise
	 */
	public boolean isLocValid() 
	{
		Location loc = getLocation().getAdjacentLocation(getDirection());
		
		if (!getGrid().isValid(loc) || getGrid().get(loc) != null)
			return false;
			
		return true;
	}
	
	/** 
	 * This method is overridden to fulfill the Critter class requirement
	 * and intentionally left empty as it is not used in the Chicken simulation
	 * but is called in the act method.
	 * 
	 * @param actors 	The list of actors to process. 
	 */
	public void processActors(ArrayList<Actor> actors) {}
}
