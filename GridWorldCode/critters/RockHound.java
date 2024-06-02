import info.gridworld.actor.Actor;
import info.gridworld.actor.Rock;
import info.gridworld.actor.Critter;
import info.gridworld.grid.Location;
import java.util.ArrayList;

public class RockHound extends Critter
{
	/**
	 * Processes the actors. Removes all rocks
	 * 
	 * @param actors 	the actors to be processed
	 */
	public void processActors(ArrayList<Actor> actors)
	{
		for (Actor a : actors)
		{
			if (a instanceof Rock)
			{
				a.removeSelfFromGrid();
			}
		}
	}
} 
