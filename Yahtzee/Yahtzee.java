/**
 *	Plays the game of Yahtzee, a dice game.  The objective is to roll five dice to 
	make various scoring combinations. A game consists of thirteen rounds. After each round a player decides which scoring 
	category is to be used for the round. Once a scoring category is used, it cannot be used again
 *
 *	@author	Tanay Kumar
 *	@since	October 9th, 2023
 */
 
public class Yahtzee 
{
	private String playerName; // This is current player playing
	private final int NUM_OF_CATEGORIES = 13; // The number of categories in the game
	
	public Yahtzee()
	{
		playerName = "";
	}
	public static void main(String [] args)
	{
		Yahtzee yaht = new Yahtzee();
		yaht.printHeader();
		yaht.run();
	}
	
	/**
	 * Prints header
	 */
	public void printHeader() 
	{
		System.out.println("\n");
		System.out.println("+------------------------------------------------------------------------------------+");
		System.out.println("| WELCOME TO MONTA VISTA YAHTZEE!                                                    |");
		System.out.println("|                                                                                    |");
		System.out.println("| There are 13 rounds in a game of Yahtzee. In each turn, a player can roll his/her  |");
		System.out.println("| dice up to 3 times in order to get the desired combination. On the first roll, the |");
		System.out.println("| player rolls all five of the dice at once. On the second and third rolls, the      |");
		System.out.println("| player can roll any number of dice he/she wants to, including none or all of them, |");
		System.out.println("| trying to get a good combination.                                                  |");
		System.out.println("| The player can choose whether he/she wants to roll once, twice or three times in   |");
		System.out.println("| each turn. After the three rolls in a turn, the player must put his/her score down |");
		System.out.println("| on the scorecard, under any one of the thirteen categories. The score that the     |");
		System.out.println("| player finally gets for that turn depends on the category/box that he/she chooses  |");
		System.out.println("| and the combination that he/she got by rolling the dice. But once a box is chosen  |");
		System.out.println("| on the score card, it can't be chosen again.                                       |");
		System.out.println("|                                                                                    |");
		System.out.println("| LET'S PLAY SOME YAHTZEE!                                                           |");
		System.out.println("+------------------------------------------------------------------------------------+");
		System.out.println("\n\n");
	}
	
	/** This method runs the game by getting the users names and setting 
	 * up the game. Then it getTurn() and playRounds()
	 */
	public void run()
	{
		String player1Name = "";
		String player2Name = "";
		String placeholder = ""; // This is a variable for to prompt the 
								// user to hit enter
		
		int player1score = 0; 
		int player2score = 0;
		int player1roll = 0; // Player one's total sum of thier roll
		int player2roll = 0; // Player two's total sum of thier roll
		
		DiceGroup dg = new DiceGroup();
		Prompt pt = new Prompt();
		YahtzeePlayer player1 = new YahtzeePlayer();
		YahtzeePlayer player2 = new YahtzeePlayer();
		
		player1Name = pt.getString("Player 1, please enter your first name : ");
		player2Name = pt.getString("\nPlayer 2, please enter your first name : ");
		player1.setName(player1Name);
		player2.setName(player2Name);
		
		placeholder = pt.getString("Let's see who will go first. " + player1Name 
								+ ", please hit enter to roll the dice: ");
		dg.rollDice();
		dg.printDice();
		player1roll = dg.getTotal();
		
		placeholder = pt.getString(player2Name + ", it's your turn. "
									+ "Please hit enter to roll the dice: ");
		dg.rollDice();
		dg.printDice();
		player2roll = dg.getTotal();
		
		System.out.printf("%s, you rolled a sum of %d, and %s, you rolled"
							+ " a sum of %d", player1Name, player1roll, 
							player2Name, player2roll);
		
		int turn = getTurn(player1roll, player2roll, dg, pt, player1Name, 
						player2Name);			
		playRounds(dg, pt, turn, player1Name, player2Name, player1, player2);
	}
	
	/** This method finds out which user goes first
	 * @param player1roll	Sum of Player 1's roll
	 * @param player2roll	Sum of Player 2's roll
	 * @param dg			Instance of the DiceGroup Class
	 * @param pt			Instance of the Prompt Class
	 * @param player1Name	Player 1's Name
	 * @param player2Name	Player 2's Name
	 * @return				Which player goes first
	 */
	public int getTurn(int player1roll, int player2roll, DiceGroup dg, 
						Prompt pt, String player1Name, String player2Name)
	{
		int turn = 0; // Whether player one or two should play
		boolean rollsAreEqual = true;
		String placeholder = ""; // This is a variable for to prompt the 
								// user to hit enter
		
		while (rollsAreEqual)
		{
			if (player1roll == player2roll)
			{
				System.out.printf("\nWhoops, we have a tie (both rolled"
									+ " %d). Looks like we'll have to try"
									+ " that again...", player1roll);
				placeholder = pt.getString("Let's see who will go first. " 
											+ player1Name 
											+ ", please hit enter to roll"
											+ " the dice :");
				dg.rollDice();
				dg.printDice();
				
				player1roll = dg.getTotal();
				placeholder = pt.getString(player2Name + ", it's your turn."
											+ " Please hit enter to roll the" 
											+ " dice :");
				dg.rollDice();
				dg.printDice();
				player2roll = dg.getTotal();
				
				rollsAreEqual = true;
			}
			
			else if (player1roll > player2roll)
			{
				
				System.out.printf("\n%s, since your sum was higher, you'll"
									+ " roll first.", player1Name);
				rollsAreEqual = false;
				turn = 1;
				playerName = player1Name;
			}
			
			else if (player2roll > player1roll)
			{
				System.out.printf("\n%s, since your sum was higher, you'll"
									+ " roll first.", player2Name);
				rollsAreEqual = false;
				turn = 2;
				playerName = player2Name;
			}
			
		}
		
		return turn;
	}
	
	/** This method plays each round using a for loop. Inside the for loop
	 * The score card is printed, the round number is printed, and the player
	 * is told to hit enter. Then and the player is then prompted on which
	 * dice to keep and then prints the score table. Then asks the user
	 * to choose a category to use. Then the loop changes the turn and 
	 * continues until the round is 13.
	 * @param dg			Instance of the DiceGroup Class
	 * @param pt			Instance of the Prompt Class
	 * @param player1Name	Player 1's Name
	 * @param player2Name	Player 2's Name
	 * @param player1	Player 1's YahtzeePlayer instance
	 * @param player2	Player 2's YahtzeePlayer instance
	 */
	public void playRounds(DiceGroup dg, Prompt pt, int turn, String player1Name,
						String player2Name, YahtzeePlayer player1, YahtzeePlayer player2)
	{
		YahtzeeScoreCard score = null;
		YahtzeeScoreCard score1 = new YahtzeeScoreCard();
		YahtzeeScoreCard score2 = new YahtzeeScoreCard();
		String placeholder = ""; // This is a variable for to prompt the 
								// user to hit enter
		String keptDie = ""; // Which dices the user wants to keep
		int userinput = 0; // What category the user wants to do
		int intIsValid = 0; // Whether the user can use a certain category
		
		for (int i = 0; i < (2 * NUM_OF_CATEGORIES); i++)
		{
			score1.printCardHeader();
			score1.printPlayerScore(player1);
			score2.printPlayerScore(player2);
			System.out.printf("Round %d of 13 rounds\n", i/2 + 1);
			
			if(turn == 1)
			{
				placeholder = pt.getString(player1Name + ", it's your turn"
											+ " to play. Please hit enter"
											+ " to roll the dice :");
				score = score1;
			}
			
			else
			{
				placeholder = pt.getString(player2Name + ", it's your turn"
											+ " to play. Please hit enter"
											+ " to roll the dice :");
				score = score2;
			}
			
			dg.rollDice();
			dg.printDice();
			keptDie = pt.getString("Which di(c)e would you like to keep?  Enter the values you\'d like to \'hold\' without\n spaces. "+
			"For examples, if you'd like to \'hold\' die 1, 2, and 5, enter 125\n(enter -1 if you\'d like to end the turn) :");
			
			if (!keptDie.equals("-1"))
			{
				dg.rollDice(keptDie);
				dg.printDice();
			
				keptDie = pt.getString("Which di(c)e would you like to keep?"
										+ " Enter the values you\'d like to "
										+ " \'hold\' without\n spaces. " 
										+ "For examples, if you'd like to "
										+ "\'hold\' die 1, 2, and 5, enter "
										+ "125\n(enter -1 if you\'d like to "
										+ "end the turn) :");
				
				if (!keptDie.equals("-1"))
				{
					dg.rollDice(keptDie);
					dg.printDice();
				
				}
			}
			
			intIsValid = 0;
			score.printCardHeader();
			score1.printPlayerScore(player1);
			score2.printPlayerScore(player2);
			System.out.println("\t\t  1    2    3    4    5    6    7    "
								+"8    9   10   11   12   13\n");
			
			do
			{
				userinput = pt.getInt(playerName + ", now you need to make"
										+ " a choice. Pick a valid integer "
										+ "from the list above : ");
				intIsValid = score.changeScore(userinput, dg);
			} while (intIsValid == 0);
			
			score1.printCardHeader();
			score1.printPlayerScore(player1);
			score2.printPlayerScore(player2);
			
			if (turn == 1) 
			{
				turn = 2;
				playerName = player2Name;
			}
			else 
			{
				turn = 1;
				playerName = player1Name;
			}
		}
		
		printScores(score1, score2, player1Name, player2Name);
	}
	
	/** This method prints each player's score
	 * @param score1	Player one's YahtzeeScoreCard instance
	 * @param score2	Player two's YahtzeeScoreCard instance
	 */
	public void printScores(YahtzeeScoreCard score1, YahtzeeScoreCard score2, 
							String player1Name, String player2Name)
	{
		int player1Total = 0;
		int player2Total = 0;
		
		for (int i = 1; i <= NUM_OF_CATEGORIES; i++)
		{
			player1Total += score1.getScore(i);
			player2Total += score2.getScore(i);
		}
		
		System.out.printf("\n\n%-14s had a score of %d\n", player1Name, 
						player1Total);
        System.out.printf("%-14s had a score of %d\n\n", player2Name, 
						player2Total);
	}
}
