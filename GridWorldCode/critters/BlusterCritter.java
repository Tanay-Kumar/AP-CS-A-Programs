import info.gridworld.actor.Actor;
import info.gridworld.actor.Rock;
import info.gridworld.actor.Critter;
import info.gridworld.grid.Location;
import java.util.ArrayList;
import java.awt.Color;

public class BlusterCritter extends Critter
{
	private int courage;

	public BlusterCritter(int c)
	{
		super();
		courage = c;
	}
	
	/**
	 * Gets the actors for processing.
	 * 
	 * @return 		a list of neighbor actors
	 */
	public ArrayList<Actor> getActors()
	{
		ArrayList<Actor> actors = new ArrayList<Actor>();
		Location loc = getLocation();
		
		for(int row = loc.getRow() - 2; row <= loc.getRow() + 2; row++)
		{
			for(int col = loc.getCol() - 2; col <= loc.getCol() + 2; col++)
			{				
				if(getGrid().isValid(new Location(row, col)))
				{
					Actor actor = getGrid().get(new Location(row, col));
					
					if(actor != null && actor != this)
					{
						actors.add(actor);
					}
				}
			}
		}
			
		return actors;
	}
	
	/**
	 * Processes the actors. 
	 * 
	 * @param actors 	the actors to be processed
	 */
	public void processActors(ArrayList<Actor> actors)
	{
		int count = 0;
		
		for (Actor a : actors)
		{
			if(a instanceof Critter)
			{
				count++;
			}
		}
		
		if(count < courage)
		{
			int r = (int) (getColor().getRed() * 0.95);
			int g = (int) (getColor().getGreen() * 0.95);
			int b = (int) (getColor().getBlue() * 0.95);
			setColor(new Color(r, g, b));
		}
		
		else
		{
			int r = (int) (getColor().getRed() * 0.95);
			int g = (int) (getColor().getGreen() * 0.95);
			int b = (int) (getColor().getBlue() * 0.95);
			setColor(new Color(r, g, b));
		}
	} 
}
