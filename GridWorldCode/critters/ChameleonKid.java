import info.gridworld.actor.Actor;
import info.gridworld.actor.Critter;
import info.gridworld.actor.Flower;
import info.gridworld.grid.Location;
import info.gridworld.grid.Grid;
import java.util.ArrayList;

/**
 * A ChameleonKid takes the color of neighboring actors
 * that are in front or behind as it moves through the grid.
 */
public class ChameleonKid extends ChameleonCritter
{
	/**
	 * Gets the actors for processing. 
	 * 
	 * @return 		a list of neighbor actors 
	 */
	public ArrayList<Actor> getActors()
	{
		ArrayList<Actor> actors = new ArrayList<Actor>();
		int[] dirs = { Location.AHEAD, Location.HALF_CIRCLE };
		
		for (Location loc : getLocationsInDirections(dirs))
		{
			Actor actor = getGrid().get(loc);
			
			if (actor != null)
			{
				actors.add(actor);
			}
		}
		
		return actors;
	}

	/**
	 * Finds the valid adjacent locations of this critter in different
	 * directions.
	 * 
	 * @param directions 	an array of directions 
	 * @return 				a set of valid locations
	 */
	public ArrayList<Location> getLocationsInDirections(int[] directions)
	{
		ArrayList<Location> locs = new ArrayList<Location>();

		for (int direction : directions)
		{
			Location nextLoc = getLocation().getAdjacentLocation
												(getDirection() + direction);
			
			if (getGrid().isValid(nextLoc))
			{
				locs.add(nextLoc);
			}
		}
		
		return locs;
	}
} 
