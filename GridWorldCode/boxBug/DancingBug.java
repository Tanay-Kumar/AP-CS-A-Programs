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

/**
 * A <code>DancingBug</code> dances on the grid. <br />
 * The implementation of this class is testable on the AP CS A and AB exams.
 */
public class DancingBug extends Bug
{
	private int[] turnList;
	private int steps;

	/**
     * Constructs a dancing bug that traces a square of a given side length
     * @param length the side length
     */
	public DancingBug(int[] turns)
	{
		turnList = turns;
		steps = 0;
	} 

	/**
     * Moves to the next location of the dance.
     */
	public void act()
	{
		if(steps == turnList.length)
		{
			steps = 0;
		}
		
		for(int j = 1; j <= turnList[steps]; j++)
		{
			turn();
		}
		
		steps++;
		super.act();
	}
} 
