/**
 *	City data - the city name, state name, location designation,
 *				and population est. 2017
 *
 *	@author	Tanay Kumar
 *	@since	Decemeber 14th, 2023	
 */
public class City implements Comparable<City> 
{
	// fields
	private String name; // Name of the City
	private String state;  // Name of the State
	private String designation; // Location type such as twon
	private int population;  // Population of the City
	private String[] stateInfo;  // Stores original state name and population
	
	// constructor
	public City(String stateIn, String nameIn, String designationIn, int populationIn)
	{
		name = nameIn;
		state = stateIn;
		designation = designationIn;
		population = populationIn;
		stateInfo = new String[]{state, "" + population};
	}
	
	/**	Compare two cities populations
	 *	@param other		the other City to compare
	 *	@return				the following value:
	 *		If populations are different, then returns (this.population - other.population)
	 *		else if states are different, then returns (this.state - other.state)
	 *		else returns (this.name - other.name)
	 */
	public int compareTo(City other)
	{
		if(this.population != other.population)
		{
			return this.population - other.population;
		}
		
		else if(!this.state.equals(other.state))
		{
			return this.state.compareTo(other.state);
		}
		
		return this.name.compareTo(other.name);
	}
	
	/**	Equal city name and state name
	 *	@param other		the other City to compare
	 *	@return				true if city name and state name equal; false otherwise
	 */
	public boolean equals(City other)
	{
		if (this.name.equals(other.name) && this.state.equals(other.state))
		{
			return true;
		}
		
		return false;
	}
	
	/**	Accessor methods */
	
	/**	toString *
	 *	@return		formated string with the state, city name, type, and population
	 */
	public String toString() {
		return String.format("%-22s %-22s %-12s %,12d", state, name, designation,
						population);
	}
	
	/** Gets the state
	 *	@return 	state name;
	 */
	public String getState()
	{
		return state;
	}
	
	/** Gets the city name
	 *	@return 	city name;
	 */
	public String getCity()
	{
		return name;
	}
	
	/** Gets the stateInfo array
	 *	@return 	an array with the state name and population;
	 */
	public String[] getStateInfo()
	{
		return stateInfo;
	}
	
	/** Sets the population size
	 *	@param populationIn		new population size;
	 */
	public void setPopulation(int populationIn)
	{
		population = populationIn;
	}
	
	/** Sets the state name
	 *	@param stateIn		new state name;
	 */
	public void setState(String stateIn)
	{
		state = stateIn;
	}
}
