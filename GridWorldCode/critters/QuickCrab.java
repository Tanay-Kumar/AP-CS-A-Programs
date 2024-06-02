import info.gridworld.actor.Actor;
import info.gridworld.actor.Critter;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;
import java.awt.Color;
import java.util.ArrayList;
/**
 * QuickCrab ats a small set of neighbors when it moves.
 */
public class QuickCrab extends CrabCritter
{
	public QuickCrab()
	{
		setColor(Color.RED);
	}
 
	/**
	 * Gets the move locations
	 * 
	 * @return 		list of empty locations
	 */
	public ArrayList<Location> getMoveLocations()
	{
		ArrayList<Location> locs = new ArrayList<Location>();

		addTwoAway(locs, getDirection() + Location.LEFT);
		addTwoAway(locs, getDirection() + Location.RIGHT);

		if (locs.size() == 0)
		{
			return super.getMoveLocations();
		}

		return locs;
	}

	/**
	 * Adds a valid and empty two away location
	 * 
	 * @param locs 		list of locations
	 * @param dir		direction of location
	 */
	public void addTwoAway(ArrayList<Location> locs, int dir)
	{
		Location temp = getLocation().getAdjacentLocation(dir);

		if(getGrid().isValid(temp) && getGrid().get(temp) == null)
		{
			Location loc2 = temp.getAdjacentLocation(dir);
			
			if(getGrid().isValid(loc2) && getGrid().get(loc2) == null)
			{
				locs.add(loc2);
			}
		}
	}
}
