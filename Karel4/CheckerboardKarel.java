/*
 * File: CheckerboardKarel.java
 * ----------------------------
 * When you finish writing it, the CheckerboardKarel class should draw
 * a checkerboard using beepers, as described in Assignment Karel4.  You
 * should make sure that your program works for all of the sample
 * worlds supplied in the starter folder.
 */

import stanford.karel.*;

public class CheckerboardKarel extends SuperKarel 
{
	// This method puts a beeper and then calls the method checkIfWallPresent().
	// Then using a while loop it calls the method putBeepersEast() and 
	// putBeepersWest() until the front is not clear.
	public void run() 
	{
        putBeeper();
        checkIfWallPresent();
        
        while(frontIsClear()) 
        {
            putBeepersEast();
            putBeepersWest();
        }
    }
    
    // This method will put beepers in specific order determined by the 
    // method putBeeperIfFrontIsClear(). Then using conditonals we check 
    // if there should be a beeper on the square directly above the last 
    // square of the row Karel is currently on. All of this is in a while
    // loop making sure Karel is facing east. 
    private void putBeepersEast() 
    {
        while(facingEast()) 
        {
            move();
            putBeeperIfFrontIsClear();
            
			if(frontIsBlocked())
			{
				turnLeft();
				if(frontIsClear()) 
				{
					if(noBeepersPresent()) 
					{
						move();
						turnLeft();
						putBeeper();
					}
					
					else 
					{
						move();
						turnLeft();
						move();
						putBeeper();
					}
				}
			}
		}
    }
    
    // This method will put beepers in specific order determined by the 
    // method putBeeperIfFrontIsClear(). Then using conditonals we check 
    // if there should be a beeper on the square directly above the last 
    // square of the row Karel is currently on. All of this is in a while
    // loop making sure Karel is facing west.
    private void putBeepersWest() 
    {
        while(facingWest()) 
        {
            move();
            putBeeperIfFrontIsClear();
            
			if(frontIsBlocked()) 
			{
				turnRight();
				
				if(frontIsClear())
				{
					if(noBeepersPresent()) 
					{
						move();
						turnRight();
						putBeeper();
					}
					
					else 
					{
						move();
						turnRight();
						move();
						putBeeper();
					}
				}
			}
		}
    }
	
	// This method checks if the front is clear and if this conditonal is 
	// true, it will move and put beeper.
	private void putBeeperIfFrontIsClear()
	{
		if(frontIsClear()) 
		{
			move();
			putBeeper();
		}
	}
	
	// This method is designed for the case of a 1x8. This method uses a 
	// condtional to check if the world is 1 column and if this is true,
	// Karel turns left and uses a while loop to move and call the method
	// putBeeperIfFrontIsClear().
	private void checkIfWallPresent() 
	{
        if(frontIsBlocked()) 
        {
            turnLeft();
            while(frontIsClear()) 
            {
                move();
                putBeeperIfFrontIsClear();
            }
        }
    }
}
