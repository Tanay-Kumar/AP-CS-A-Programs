import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
/**
 *	Population
 * 	Sorting a database from the list of USA cities using Merge Sort
 * 	Selection Sort, and Insertion Sort.
 *
 *	Requires FileUtils and Prompt classes.
 *
 *	@author	Tanay Kumar
 *	@since	Decemeber 14th, 2023	
 */
public class Population {
	
	// List of cities
	private List<City> cities;
	
	// US data file
	private final String DATA_FILE = "usPopData2017.txt";
	
	public static void main(String[] args)
	{
		Population p = new Population();
		p.run();
	}
	
	public void run()
	{
		printIntroduction();
		
		int userNum = 1;
		while (userNum != 9)
		{
			printMenu();
			userNum = Prompt.getInt("Enter selection");
			processRequest(getScanner(), userNum);
			
			if(userNum > 6 && userNum != 9)
			{
				System.out.println("\n");
			}
		}
		System.out.println("\nThanks for using Population!");
	}
	
	/**	Prints the introduction to Population */
	public void printIntroduction() {
		System.out.println("   ___                  _       _   _");
		System.out.println("  / _ \\___  _ __  _   _| | __ _| |_(_) ___  _ __ ");
		System.out.println(" / /_)/ _ \\| '_ \\| | | | |/ _` | __| |/ _ \\| '_ \\ ");
		System.out.println("/ ___/ (_) | |_) | |_| | | (_| | |_| | (_) | | | |");
		System.out.println("\\/    \\___/| .__/ \\__,_|_|\\__,_|\\__|_|\\___/|_| |_|");
		System.out.println("           |_|");
		System.out.println();
	}
	
	/**	Print out the choices for population sorting */
	public void printMenu() {
		System.out.println("1. Fifty least populous cities in USA (Selection Sort)");
		System.out.println("2. Fifty most populous cities in USA (Merge Sort)");
		System.out.println("3. First fifty cities sorted by name (Insertion Sort)");
		System.out.println("4. Last fifty cities sorted by name descending (Merge Sort)");
		System.out.println("5. Fifty most populous cities in named state");
		System.out.println("6. All cities matching a name sorted by population");
		System.out.println("9. Quit");
	}
	
	/**
	 * Creates a scanner with a delemiter
	 * 
	 * @return		a scanner with a delemiter
	 */
	public Scanner getScanner()
	{
		Scanner reader = FileUtils.openToRead(DATA_FILE);
		reader.useDelimiter("[\t\n]");
		return reader;
	}
	
	/**
	 * Reads through the DATA_FILE using the scanner and creates City objects
	 * which are put into the cities array. Then conditionals are used to 
	 * determine which output should be printed.
	 * 
	 * @param Scanner reader	a scanner with a delemiter
	 * @param int userNum		user's output choice
	 */
	public void processRequest(Scanner reader, int userNum)
	{
		SortMethods sm = new SortMethods();
		cities = new ArrayList<City>();
		String state = "";
		String cityName = "";
		String designation = "";
		int population = 0;
		
		while (reader.hasNext())
		{
			state = reader.next();
			cityName = reader.next();
			designation = reader.next();
			population = reader.nextInt();
			City cityObject = new City(state, cityName, designation, population);
			cities.add(cityObject);
		}
		
		if (userNum == 1)
		{
			leastPopulated(sm);
		}
		
		else if (userNum == 2)
		{
			mostPopulated(sm);
		}
		
		else if (userNum == 3)
		{
			firstNameSort(sm);
		}
		
		else if (userNum == 4)
		{
			lastNameSort(sm);
		}
		
		else if (userNum == 5)
		{
			namedState(sm);
		}
		
		else if (userNum == 6)
		{
			namedCity(sm);
		}
	}
	
	/**
	 * Uses the selectionSort method from SortMethods to sort the cities
	 * ArrayList and prints it out.
	 * 
	 * @param SortMethods sm		a SortMethods object
	 */
	public void leastPopulated(SortMethods sm)
	{
		System.out.println("\nFifty least populous cities");
		System.out.printf("%5s%-22s %-22s %-12s %12s\n", "", "State", 
						"City", "Type", "Population");
		long startMillisec = System.currentTimeMillis();
		sm.selectionSort(cities);
		long endMillisec = System.currentTimeMillis();
		long millisecElapsed = endMillisec - startMillisec;
		
		for(int i = 0; i < 50; i++)
		{
			System.out.printf("%3d: %s\n", i + 1, cities.get(i));
		}
		
		System.out.printf("\nElapsed Time %d milliseconds\n\n", millisecElapsed);
	}
	
	/**
	 * Uses the mergeSort method from SortMethods to sort the cities
	 * ArrayList and prints it out.
	 * 
	 * @param SortMethods sm		a SortMethods object
	 */
	public void mostPopulated(SortMethods sm)
	{
		int counter = 1;
		long startMillisec = System.currentTimeMillis();
		sm.mergeSort(cities);
		long endMillisec = System.currentTimeMillis();
		long millisecElapsed = endMillisec - startMillisec;
		
		System.out.println("\nFifty most populous cities");
		System.out.printf("%5s%-22s %-22s %-12s %12s\n", "", "State", 
						"City", "Type", "Population");
		
		for(int i = cities.size() - 1; i >= cities.size() - 50; i--)
		{
			System.out.printf("%3d: %s\n", counter, cities.get(i));
			counter++;
		}
		
		System.out.printf("\nElapsed Time %d milliseconds\n\n", millisecElapsed);
	}
	
	/**
	 * Changes population and state to the same value for a city objects
	 * in the cities array. Then the method uses the insertionSort method
	 * from SortMethods. Then the method puts the population and state back
	 * into the city objects and prints them.
	 * 
	 * @param SortMethods sm		a SortMethods object
	 */
	public void firstNameSort(SortMethods sm)
	{
		int population = -1;
		
		for (int i = 0; i < cities.size(); i++)
		{
			cities.get(i).setPopulation(0);
			cities.get(i).setState("Homeless");
		}
		
		long startMillisec = System.currentTimeMillis();
		sm.insertionSort(cities);
		long endMillisec = System.currentTimeMillis();
		long millisecElapsed = endMillisec - startMillisec;
		System.out.println("\nFifty cities sorted by name");
		System.out.printf("%5s%-22s %-22s %-12s %12s\n", "", "State", 
						"City", "Type", "Population");
						
		for(int i = 0; i < 50; i++)
		{
			population = Integer.parseInt(cities.get(i).getStateInfo()[1]);
			cities.get(i).setPopulation(population);
			cities.get(i).setState(cities.get(i).getStateInfo()[0]);
			System.out.printf("%3d: %s\n", i + 1, cities.get(i));
		}
		
		System.out.printf("\nElapsed Time %d milliseconds\n\n", millisecElapsed);
	}
	
	/**
	 * Changes population and state to the same value for a city objects
	 * in the cities array. Then the method uses the mergeSort method
	 * from SortMethods. Then the method puts the population and state back
	 * into the city objects and prints them.
	 * 
	 * @param SortMethods sm		a SortMethods object
	 */
	public void lastNameSort(SortMethods sm)
	{
		int counter = 1;
		int population = -1;
		
		for (int i = 0; i < cities.size(); i++)
		{
			cities.get(i).setPopulation(0);
			cities.get(i).setState("Homeless");
		}
		
		long startMillisec = System.currentTimeMillis();
		sm.mergeSort(cities);
		long endMillisec = System.currentTimeMillis();
		long millisecElapsed = endMillisec - startMillisec;
		System.out.println("\nFifty cities sorted by name descending");
		System.out.printf("%5s%-22s %-22s %-12s %12s\n", "", "State", 
						"City", "Type", "Population");
						
		for(int i = cities.size() - 1; i >= cities.size() - 50; i--)
		{
			population = Integer.parseInt(cities.get(i).getStateInfo()[1]);
			cities.get(i).setPopulation(population);
			cities.get(i).setState(cities.get(i).getStateInfo()[0]);
			System.out.printf("%3d: %s\n", counter, cities.get(i));
			counter++;
		}
		
		System.out.printf("\nElapsed Time %d milliseconds\n\n", millisecElapsed);
	}
	
	/**
	 * Creates an ArrayList of valid states and then prompts the user until
	 * the user gives a valid state. Then the mergeSort method is used
	 * from SortMethods and the top 50 are printed.
	 * 
	 * @param SortMethods sm		a SortMethods object
	 */
	public void namedState(SortMethods sm)
	{
		int counter = 1;
		List<String> states = new ArrayList<String>();
		
		for (int i = 0; i < cities.size(); i++)
		{
			if (states.indexOf(cities.get(i).getState()) == -1)
			{
				states.add(cities.get(i).getState());
			}
		}
		
		String userState = "";
		System.out.println();
		
		while (states.indexOf(userState) == -1)
		{
			userState = Prompt.getString("Enter state name (ie. Alabama)");
			
			if (states.indexOf(userState) == -1)
			{
				System.out.printf("ERROR: %s is not valid\n", userState);
			}
		}
		
		sm.mergeSort(cities);
		System.out.println("\nFifty most populous cities in " + userState);
		System.out.printf("%5s%-22s %-22s %-12s %12s\n", "", "State", 
						"City", "Type", "Population");
						
		for (int i = cities.size() -1; i >= 0; i--)
		{
			if (cities.get(i).getState().equals(userState) && 
				counter <= 50)
			{
				System.out.printf("%3d: %s\n", counter, cities.get(i));
				counter++;
			}
		}
		
		System.out.println();
	}
	
	/**
	 * Creates an ArrayList of valid cities and then prompts the user until
	 * the user gives a valid city. Then the mergeSort method is used
	 * from SortMethods and all the cities are printed.
	 * 
	 * @param SortMethods sm		a SortMethods object
	 */
	public void namedCity(SortMethods sm)
	{
		int counter = 1;
		List<String> cityNames = new ArrayList<String>();
		
		for (int i = 0; i < cities.size(); i++)
		{
			if (cityNames.indexOf(cities.get(i).getCity()) == -1)
			{
				cityNames.add(cities.get(i).getCity());
			}
		}
		
		String userCity = "";
		System.out.println();
		
		while (cityNames.indexOf(userCity) == -1)
		{
			userCity = Prompt.getString("Enter city name");
			
			if (cityNames.indexOf(userCity) == -1)
			{
				System.out.printf("ERROR: %s is not valid\n", userCity);
			}
		}
		
		sm.mergeSort(cities);
		System.out.println("\nCity " + userCity + " by population");
		System.out.printf("%5s%-22s %-22s %-12s %12s\n", "", "State", 
						"City", "Type", "Population");
						
		for (int i = cities.size() -1; i >= 0; i--)
		{
			if (cities.get(i).getCity().equals(userCity) && 
				counter <= 50)
			{
				System.out.printf("%3d: %s\n", counter, cities.get(i));
				counter++;
			}
		}
		
		System.out.println();
	}
}
