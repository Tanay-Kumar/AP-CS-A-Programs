/*
 * File: MidpointFindingKarel.java
 * -------------------------------
 * When you finish writing it, the MidpointFindingKarel class should
 * leave a beeper on the corner closest to the center of 1st Street
 * (or either of the two central corners if 1st Street has an even
 * number of corners).  Karel can put down additional beepers as it
 * looks for the midpoint, but must pick them up again before it
 * stops.  The world may be of any size, but you are allowed to
 * assume that it is at least as tall as it is wide.
 */

import stanford.karel.*;

public class MidpointFindingKarel extends SuperKarel 
{
	
	// This method calls the method putFirstAndLastBeepers() and then uses
	// a while loop and calls the method moveBeepersToTheMiddle(). Then
	// this method picks up the last beeper and returns to the middle.
	// Finally it calls the method makeKarelFaceEast()
	public void run()
	{
		putFirstAndLastBeepers();
		
		while(noBeepersPresent())
		{
			moveBeepersToTheMiddle();
		}

		pickBeeper();
		turnAround();
		move();
		makeKarelFaceEast();
	}
	
	// This method first puts a beeper at the first square and then using
	// a while loop puts a beeper at the end of the first row and turns 
	// around.  
	private void putFirstAndLastBeepers()
	{
		putBeeper();
		
		while(frontIsClear())
		{
			move();
		}
		
		putBeeper();
		turnAround();
		move();
	}
	
	// This method finds the middle by picking up beepers and moving until 
	// it gets to the middle.
	private void moveBeepersToTheMiddle()
	{ 
		while(noBeepersPresent())
		{
			move();
		}
		
		pickBeeper();
		turnAround();
		move();
		putBeeper();
		move();
	}
	
	// This method uses a while loop to make Karel turn left until she 
	// faces east.
	private void makeKarelFaceEast()
	{
		while(notFacingEast())
		{
			turnLeft();
		}
	}
}
