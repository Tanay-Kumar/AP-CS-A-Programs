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
 * @author Chris Nevison
 * @author Barbara Cloud Wells
 */
 
import info.gridworld.actor.Bug;
import info.gridworld.grid.Location;

/**
 * A <code>CircleBug</code> traces out a circle of a given size. <br />
 * The implementation of this class is testable on the AP CS A and AB exams.
 * 
 * @author Tanay Kumar
 * @since 18th March, 2024
 */
public class CircleBug extends Bug
{
	private int steps;
	private int sideLength;
	
	/**
     * Constructs a circle bug that traces a square of a given side length
     * @param length the side length
     */
	public CircleBug(int length)
	{
		steps = 0;
		sideLength = length;
		Location loc = new Location(5, 3);
		this.setDirection(Location.WEST);
		System.out.println((loc.getAdjacentLocation(this.getDirection() + Location.HALF_LEFT)));
		
	}
	
	/**
     * Moves to the next location of the square.
     */
	public void act()
	{
		if (steps < sideLength && canMove())
		{
			move();
			steps++;
		}
		else
		{
			turn();
			steps = 0;
		}
	}
}
