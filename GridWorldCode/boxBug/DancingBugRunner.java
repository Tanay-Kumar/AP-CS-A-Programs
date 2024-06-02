import info.gridworld.actor.ActorWorld;
import info.gridworld.grid.Location;
import java.awt.Color;

public class DancingBugRunner
{
	public static void main(String[] args)
	{
		ActorWorld world = new ActorWorld();
		int[] turns = { 1, 0, 0, 0, 1, 0, 0, 3, 4, 4, 0, 0, 1, 0, 3, 2, 
						0, 7, 0, 0, 0, 3, 2, 1 };
		DancingBug ballerina = new DancingBug(turns);
		ballerina.setColor(Color.ORANGE);
		world.add(new Location(9, 9), ballerina);
		world.show();
	}
}
