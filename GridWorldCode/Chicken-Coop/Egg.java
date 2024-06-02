import info.gridworld.actor.Actor;
import java.awt.Color;

/** 
 * Eggs are laid by young Chickens and undergo a process of incubation 
 * that is visually represented by a change in color from white to 
 * increasingly darker shades. After a certain period, the egg turns red.
 * Upon reaching the hatch point, the egg is replaced by a new Chicken, 
 * continuing the cycle of life in GridWorld. 
 * 
 * 	@author Tanay Kumar
 * 	@since 4th April, 2024
 */
public class Egg extends Actor 
{
	private int steps; // Number of steps
	
	/** Constructor */
	public Egg() 
	{
		steps = 0;
		setColor(Color.WHITE);
	}
	
	/** 
	 * This method manages the egg's lifecycle, 
	 * including its darkening as it ages, changing to red at 45 steps,
	 * and hatching into a chicken at 50 steps.
     */
	public void act() 
	{
		steps++;
		
		// Darkens the egg's color gradually to indicate aging, until it 
		// reaches 45 steps
		if (steps < 45) 
		{
	        setColor(getColor().darker());
       	}
       	
       	// Changes the egg's color to red to indicate it's about to hatch
		else if (steps == 45)
		{
			setColor(Color.RED);
		}
		
		// Egg hatches into a new Chicken (The egg is automatically 
		// removed from the grid when the chicken is placed)
		else if (steps >= 50) 
		{
			Chicken chick = new Chicken();
			chick.putSelfInGrid(getGrid(), getLocation());
		}	
	}
}
