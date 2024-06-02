import info.gridworld.actor.Actor;

/** 
 * 	When a Chicken dies, a Tombstone replaces the Chicken. Tombstone stays 
 * 	there for 20 steps and then disappears.
 * 
 * 	@author Tanay Kumar
 * 	@since 4th April, 2024
 */
public class Tombstone extends Actor 
{
	private int lifetime; // Tracks the remaining "lifetime" of the Tombstone in the grid

    /** Constructor */
    public Tombstone() 
    {
		// Initialize the lifetime to default steps
        lifetime = 20; 
    }   
    
    /** 
     * This overridden method is called at each "step" to decrement 
     * the tombstone's lifetime and remove itself from the grid once its 
     * lifetime reaches zero.
	 */
    public void act() 
    {
        lifetime--;
        
        // Check if the Tombstone's lifetime has expired
        if(lifetime <= 0)
        {
            removeSelfFromGrid();
        }
    }   
}
