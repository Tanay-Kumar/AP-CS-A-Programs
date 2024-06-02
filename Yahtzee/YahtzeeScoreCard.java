/**
 * This makes a score card for Yahtzee where there are 13 columns and 
 * various different categroies for each column including 3 of a kind,
 * 4 of a kind, full house, small straight, large straight, chance, and 
 * Yahtzee. This class prints the score card as well as figures out which
 * number to put in each category.
 *
 *	@author	Tanay Kumar
 *	@since	October 9th, 2023
 */
public class YahtzeeScoreCard 
{
	private int [] categories = new int[13]; // Score in each category
	private int [] categoriesTaken = new int[13]; // Tells wheter the category has been used
	private final int NUM_OF_CATEGORIES = 13; // The number of categories in the game
	private final int NUM_OF_DICES = 5; // The number of dices in the game
	
	public YahtzeeScoreCard ()
	{
		for(int i = 0; i < NUM_OF_CATEGORIES; i++)
		{
			categories[i] = -1;
		}
	}
	
	/**
	 *	Get a category score on the score card.
	 *	@param category		the category number (1 to 13)
	 *	@return				the score of that category
	 */
	public int getScore(int category) 
	{
		return categories[category - 1];
	}
	
	/**
	 *  Print the scorecard header
	 */
	public void printCardHeader() 
	{
		System.out.println("\n");
		System.out.printf("\t\t\t\t\t       3of  4of  Fll Smll Lrg\n");
		System.out.printf("  NAME\t\t  1    2    3    4    5    6   Knd  Knd  Hse " +
						"Strt Strt Chnc Ytz!\n");
		System.out.printf("+----------------------------------------------------" +
						"---------------------------+\n");
	}
	
	/**
	 *  Prints the player's score
	 */
	public void printPlayerScore(YahtzeePlayer player) 
	{
		String [] space = new String[NUM_OF_CATEGORIES];
		int counter = 1;
		
		for(int i = 0; i < NUM_OF_CATEGORIES; i++)
		{
			space[i] = " ";
		}
		
		System.out.printf("| %-12s |", player.getName());
		
		while (counter <= NUM_OF_CATEGORIES)
		{
			if (getScore(counter) >= -1)
			{
				if (getScore(counter) == -1)
				{
					System.out.printf(" %2s |", space[counter - 1]);
				}
				
				else
				{
					System.out.printf(" %2d |", getScore(counter));
				}
			}
			
			else 
			{
				System.out.printf("    |");
			}
			
			counter++;
		}
		
		System.out.println();
		System.out.printf("+----------------------------------------------------" +
						"---------------------------+\n");
	}


	/**
	 *  Change the scorecard based on the category choice 1-13.
	 *
	 *  @param choice The choice of the player 1 to 13
	 *  @param dg  The DiceGroup to score
	 *  @return  true if change succeeded. Returns false if choice already taken.
	 */
	public int changeScore(int choice, DiceGroup dg) 
	{
		if (choice > NUM_OF_CATEGORIES || choice < 1)
		{
			System.out.println("ERROR");
			return 0;
		}
		
		else if (categoriesTaken[choice - 1] == 1)
		{
			return 0;
		}
		
		else
		{
			if (choice >= 1 && choice <= 6)
			{
				numberScore(choice, dg);
			}
			
			else if (choice == 6)
			{
				numberScore(6, dg);
			}
			
			else if (choice == 7)
			{
				threeOfAKind(dg);
			}
			
			else if (choice == 8)
			{
				fourOfAKind(dg);
			}
			
			else if (choice == 9)
			{
				fullHouse(dg);
			}
			
			else if (choice == 10)
			{
				smallStraight(dg);
			}
			
			else if (choice == 11)
			{
				largeStraight(dg);
			}
			
			else if (choice == 12)
			{
				chance(dg);
			}
			
			else
			{
				yahtzeeScore(dg);
			}
			
			return categoriesTaken[choice - 1];
		}
	}
	
	/**
	 *  Change the scorecard for a number score 1 to 6
	 *
	 *  @param choice The choice of the player 1 to 6
	 *  @param dg  The DiceGroup to score
	 */
	public void numberScore(int choice, DiceGroup dg) 
	{
		int sum = 0;
		int counter = 0;
		
		while (counter < NUM_OF_DICES)
		{
			if (dg.getDie(counter).getLastRollValue() == choice) 
			{
				sum += choice;
			}
			
			counter++;
		}

		categories[choice - 1] = sum;
		categoriesTaken[choice - 1] = 1;
		
	}
	
	/**
	 *	Updates the scorecard for Three Of A Kind choice.
	 *
	 *	@param dg	The DiceGroup to score
	 */	
	public void threeOfAKind(DiceGroup dg) 
	{
		int flags[] = new int[NUM_OF_DICES + 1];
		int sum = 0;
		boolean found = false;
		int counter = 0;
		
		while (counter < NUM_OF_DICES) 
		{
			flags[dg.getDie(counter).getLastRollValue() - 1] += 1;
			counter++;
		}
		
		counter = 0;
		while (counter <= NUM_OF_DICES) 
		{
			if(flags[counter] >= 3) 
			{
				found = true;
				sum = flags[counter] * (counter + 1);
			}
			counter++;
		}
		
		categoriesTaken[6] = 1;
		
		if (found) 
		{
			categories[6] = sum;
		}
		
		else
		{
			categories[6] = 0;
		}
		
	}
	
	/**
	 *	Updates the scorecard for Four Of A Kind choice.
	 *
	 *	@param dg	The DiceGroup to score
	 */	
	public void fourOfAKind(DiceGroup dg) 
	{
		int flags[] = new int[NUM_OF_DICES + 1];
		int sum = 0;
		boolean found = false;
		int counter = 0;
		
		while (counter < NUM_OF_DICES) 
		{
			flags[dg.getDie(counter).getLastRollValue() - 1] += 1;
			counter++;
		}
		
		counter = 0;
		while (counter <= NUM_OF_DICES) 
		{
			if(flags[counter] >= 4) 
			{
				found = true;
				sum = flags[counter] * (counter + 1);
			}
			counter++;
		}
		
		categoriesTaken[7] = 1;
		
		if (found) 
		{
			categories[7] = sum;
		}
		
		else
		{
			categories[7] = 0;
		}
	
	}
	
	/**
	 *	Updates the scorecard for Full House choice.
	 *
	 *	@param dg	The DiceGroup to score
	 */	
	public void fullHouse(DiceGroup dg) 
	{
		int flags[] = new int[NUM_OF_DICES + 1];
		int sum = 0;
		boolean threeFound = false;
		boolean twoFound = false;
		int counter = 0;
		
		while (counter < NUM_OF_DICES) 
		{
			flags[dg.getDie(counter).getLastRollValue() - 1] += 1;
			counter++;
		}
		
		counter = 0;
		while (counter <= NUM_OF_DICES) 
		{
			if (flags[counter] == 3) 
			{
				threeFound = true;
			}
			else if (flags[counter] == 2) 
			{
				twoFound = true;
			}
			
			counter++;
		}
		
		categoriesTaken[8] = 1;
		
		if (twoFound && threeFound)
		{
			categories[8] = 25;
		}
		
		else
		{
			categories[8] = 0;
		}

	}
	
	/**
	 *	Updates the scorecard for Small Straight choice.
	 *
	 *	@param dg	The DiceGroup to score
	 */	
	public void smallStraight(DiceGroup dg) 
	{
		int flags[] = new int[NUM_OF_DICES + 1];
		int seq = 0;
		int max = 0;
		int counter = 0;
		
		while (counter < NUM_OF_DICES) 
		{
			flags[dg.getDie(counter).getLastRollValue() - 1] += 1;
			counter++;
		}
		 
        if ((flags[0] > 0 && flags[1] > 0 && flags[2] > 0 && flags[3] > 0) 
			|| (flags[1] > 0 && flags[2] > 0 && flags[3] > 0 && flags[4] > 0) 
			|| (flags[2] > 0 && flags[3] > 0 && flags[4] > 0 && flags[5] > 0)) 
		{
            categories[9] = 30;
        }
        
        else 
		{
            categories[9] = 0;
        }
		
		categoriesTaken[9] = 1;
	}
	
	/**
	 *	Updates the scorecard for Large Straight choice.
	 *
	 *	@param dg	The DiceGroup to score
	 */	
	public void largeStraight(DiceGroup dg) 
	{
		int flags[] = new int[NUM_OF_DICES + 1];
        int counter = 0;
        
        while (counter < NUM_OF_DICES) 
		{
			flags[dg.getDie(counter).getLastRollValue() - 1] += 1;
			counter++;
		}
        
        if ((flags[0] == 1 && flags[1] == 1 && flags[2] == 1 && flags[3] == 1 
			&& flags[4] == 1) || (flags[1] == 1 && flags[2] == 1 
			&& flags[3] == 1 && flags[4] == 1 && flags[5] == 1)) 
		{
            categories[10] = 40;
        }
        
        else 
        {
            categories[10] = 0;
        }	
        
        categoriesTaken[10] = 1;
	}
	
	/**
	 *	Updates the scorecard for Change choice.
	 *
	 *	@param dg	The DiceGroup to score
	 */	
	public void chance(DiceGroup dg) 
	{
		int [] dieValues = new int[NUM_OF_DICES];
		
		for(int i = 0; i < NUM_OF_DICES; i++)
		{
			dieValues[i] = dg.getDie(i).getLastRollValue();
		}
		
		categoriesTaken[11] = 1;
		categories[11] = dieValues[0] + dieValues[1] + dieValues[2] 
						+ dieValues[3] + dieValues[4];
	}
	
	/**
	 *	Updates the scorecard for Yahtzee choice.
	 *
	 *	@param dg	The DiceGroup to score
	 */	
	public void yahtzeeScore(DiceGroup dg) 
	{
		int [] dieValues = new int[NUM_OF_DICES];
		
		for(int i = 0; i < NUM_OF_DICES; i++)
		{
			dieValues[i] = dg.getDie(i).getLastRollValue();
		}
		
		if(dieValues[0] == dieValues[1] && dieValues[1] == dieValues[2]
			&& dieValues[2] == dieValues[3] && dieValues[3] == dieValues[4])
		{
			categories[12] = 50;
		}
		
		else
		{
			categories[12] = 0;
		}
		
		categoriesTaken[12] = 1;
	}

}
