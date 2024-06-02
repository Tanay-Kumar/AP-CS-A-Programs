/**
 *	The object to store US state information.
 *
 *	@author	Tanay Kumar
 *	@since	May 30th, 2024
 */
public class State implements Comparable<State>
{
    private String name; 		  // State's full name
    private String abbreviation; // State's abbreviation (e.g., CA for California)
    private int population;     // State's population count
    private int area;    	   // State's total area in square miles
    private int reps;	   	  // Number of representatives in the state
    private String capital;  // Capital city of the state
    private int month;    	// Admission month into the Union
    private int day;   	   // Admission day into the Union
    private int year;     // Admission year into the Union
    
    /**
     * Constructor to initialize the State object with all attributes.
     *
     * @param n 		Name of the state
     * @param a 		Abbreviation of the state
     * @param p 		Population of the state
     * @param ar 		Area of the state in square miles
     * @param r 		Number of representatives of the state
     * @param c 		Capital city of the state
     * @param m 		Month of admission to the Union
     * @param d 		Day of admission to the Union
     * @param y 		Year of admission to the Union
     */
    public State(String n, String a, int p, int ar, int r, String c, int m, int d, int y) 
    {
        name = n;
        abbreviation = a;
        population = p;
        area = ar;
        reps = r;
        capital = c;
        month = m;
        day = d;
        year = y;
    }
    
    /**
     * Compares this State to another State based on the state's name alphabetically.
     * 
     * @param other 		The other State to compare to.
     * @return			 	a negative integer, zero, or a positive integer 
     * 						as this name is less than, equal to, or greater 
     * 						than the specified object's name.
     */
    public int compareTo(State other) 
    {
        return name.compareTo(other.name);
    }
    
    /**
     * Gets the name of the state.
     *
     * @return 			The name of the state.
     */
    public String getName()
    {
        return name;
    }
    
    /**
     * Returns a string representation of the State object
     * 
     * @return 			Formatted string including all state attributes.
     */
    public String toString()
    {
        return String.format("%-25s%-5s%-15s%-10s%-5s%-20s%-5s%-5s%-5s", 
                             name, abbreviation, population, area, reps, capital, 
                             month, day, year);
    }
}
