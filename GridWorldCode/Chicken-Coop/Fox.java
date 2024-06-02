import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;
import info.gridworld.actor.Actor;
import info.gridworld.actor.Critter;
import java.util.ArrayList;

/** 
 * Foxes actively pursue and eat the nearest Chicken, 
 * utilizing the distance formula to determine proximity. They prefer 
 * open neighboring locations for movement but will choose a random direction 
 * if direct paths to Chickens are blocked or no Chickens are present. Upon 
 * eating a Chicken, the Fox replaces it with a Tombstone. The Fox then 
 * enters a nap state for 10 steps, during which it does not move or pursue 
 * Chickens. If a Fox fails to eat within 20 steps post-nap, it starves and 
 * dies. A Tombstone replaces it, marking its death.
 * 
 * 	@author Tanay Kumar
 * 	@since 4th April, 2024
 */
public class Fox extends Critter 
{
	private int steps; // Number of steps taken while hungry
	private boolean hasEaten; // if Fox is resting

	/** Constructor */
	public Fox() 
	{
		steps = 0;
		hasEaten = false;
		setColor(null);
	}

	/**  
	 * Executes the fox's movement behavior each step. If the fox has been 
	 * hungry for 20 steps, it dies of starvation and is replaced by a Tombstone.
	 * If the fox has recently eaten and is napping, it will wake up after 
	 * 10 steps, ready to hunt again. Otherwise, the fox moves to the 
	 * specified location, potentially chasing a chicken.
	 * 
	 * @param loc 		The location to move to
	 */
	public void makeMove(Location loc) 
	{
		// Checks if the fox has starved to death
		if (steps >= 20) 
		{
			Tombstone ts = new Tombstone();
			ts.putSelfInGrid(getGrid(), getLocation());
		} 
		
		// Checks if the fox's nap is over
		else if (hasEaten && steps >= 10) 
		{
			hasEaten = false;
			steps = 0;
		}
		
		// Moves the fox to a new location
		else
		{
			moveTo(loc);
		}
	}

	/** 
	 * Determines potential next move locations for the fox. If there are 
	 * chickens on the grid, the fox will prioritize moving towards the 
	 * closest chicken. If no chickens are present, the fox moves randomly. 
	 * 
	 * @return 		A list of potential locations the fox can move to next
	 */
	public ArrayList<Location> getMoveLocations() 
	{
		ArrayList<Location> result = new ArrayList<Location>();
		// List to hold all chickens on the grid
		ArrayList<Actor> chicks = new ArrayList<Actor>(); 
		// Initialize with max value to ensure any chicken found is closer.
		double dist = 500;
		
		// Gather all chickens currently on the grid to determine the closest one
		for (Location loc : getGrid().getOccupiedLocations())
		{
			Actor actor = getGrid().get(loc);
						
			if (actor instanceof Chicken)
			{
				chicks.add(actor);
			}
		}
		
		// If no Chickens are present, the Fox moves randomly
		if (chicks.size() == 0) 
		{ 
			steps++;
			
			return getGrid().getEmptyAdjacentLocations(getLocation());
		}
		
		 // Identify the closest chicken and set the move towards it
		for (Actor chick : chicks)
		{ 
			// Filter through all Chickens to find closest using Distance Formula
			int dist1 = chick.getLocation().getRow() - getLocation().getRow();
			int dist2 = chick.getLocation().getCol() - getLocation().getCol();
			int chickDist = (int) (Math.sqrt(Math.pow(dist1, 2) + Math.pow(dist2, 2)));

			if (chickDist <= dist) 
			{ 
				// Gets Closet Chicken
				dist = chickDist;
				Location currChickLoc = chick.getLocation();

				// If Fox is not resting then Chicken's are chased
				if (!hasEaten && getGrid().isValid(currChickLoc)) 
				{
					int dir = getLocation().getDirectionToward(currChickLoc);
					result.add(getLocation().getAdjacentLocation(dir));
				}
			}
		}
		
		steps++;
		
		return result;
	}

	/** 
	 * This method allows the Fox to eat nearby chickens, 
	 * initiating a rest period (nap) for the fox. Each eaten chicken is 
	 * replaced with a Tombstone, simulating the fox's hunting behavior.
	 * 
	 * @param actors 		The list of actors in neighboring locations
	 */
	public void processActors(ArrayList<Actor> actors) 
	{
		for (Actor actor : actors) 
		{			
			// If not resting and a Chicken is found
			if(!hasEaten && actor instanceof Chicken) 
			{
				 // The Fox eats the chicken and replaces it with a Tombstone
				Tombstone ts = new Tombstone();
				ts.putSelfInGrid(getGrid(), actor.getLocation());
				
				// Initiates the Fox's nap time after eating
				hasEaten = true;
				steps = 0;
			}
		}
	}
}
