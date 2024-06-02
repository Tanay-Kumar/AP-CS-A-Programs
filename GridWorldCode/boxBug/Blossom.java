/* 
 * AP(r) Computer Science GridWorld Case Study:
 * Copyright(c) 2005-2006 Cay S. Horstmann (http://horstmann.com)
 *
 * This code is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * @author Cay Horstmann
 */

import java.awt.Color;
import info.gridworld.actor.Flower;

/**
 * A <code>Blossom</code> is an actor that darkens over time and then removes itself
 * from the grid when its lifetime is up. Some actors drop
 * blossoms as they move. <br />
 * The API of this class is testable on the AP CS A and AB exams.
 * 
 * @author Tanay Kumar
 * @since 18th March, 2024
 */
public class Blossom extends Flower
{
    private int lifetimeSteps = 0;
    private int numSteps = 0;

    // lose 5% of color value in each step

    /**
     * Constructs a blue blossom.
     */
    public Blossom()
    {
        setColor(Color.GREEN);
        lifetimeSteps = 10;
    }

    /**
     * Constructs a blossom
     * @param initialColor the initial color of this flower
     */
    public Blossom(int lifetimeLength)
    {
        setColor(Color.GREEN);
        lifetimeSteps = lifetimeLength;
    }

    /**
     * Causes the color of this flower to darken.
     */
    public void act()
    {		
		if(numSteps < lifetimeSteps)
		{
			int red = (int) (getColor().getRed() * (1 - 0.05));
			int green = (int) (getColor().getGreen() * (1 - 0.05));
			int blue = (int) (getColor().getBlue() * (1 - 0.05));
			setColor(new Color(red, green, blue));
			numSteps++;
		}
		
		else
		{
			removeSelfFromGrid();
		}
    }
}
