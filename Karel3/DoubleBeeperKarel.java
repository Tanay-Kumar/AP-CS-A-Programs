/*
 * File: DoubleBeeperKarel.java
 * -----------------------------
 * The DoubleBeeperKarel class extends the basic Karel class
 * so that Karel doubles the number of beepers on a corner.
 */

import stanford.karel.*;

public class DoubleBeeperKarel extends SuperKarel 
{
	// This method moves Karel, calls the methods doubleBeepers() and 
	// moveBeepersBack() and then moves Karel again.
	public void run() 
	{
		move();
		doubleBeepers();
		moveBeepersBack();
		move();
	}
	
	// This method repeats the action of picking up a beeper moving and 
	// puting down two beepers until there are no beepers left from the 
	// orginal pile.
	private void doubleBeepers() 
	{
		while(beepersPresent()) 
		{
			pickBeeper();
			move();
			putBeeper();
			putBeeper();
			turnAround();
			move();
			turnAround();
		}		
	}
	
	// This method moves the beepers from the new doubled pile created by 
	// the method doubleBeepers() back to the second square. The method 
	// does this by first moving Karel and use a while loop to pick up
	// a beeper and put it back onto the second square until there are no
	// beepers left in the third square.  
	private void moveBeepersBack() 
	{
		move();

		while(beepersPresent()) 
		{
			pickBeeper();
			turnAround();
			move();
			turnAround();
			putBeeper();
			move();
		}
		
		turnAround();
		move();
		turnAround();
	}
}
