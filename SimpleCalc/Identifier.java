/**
 * Identifier class that defines and handles identifiers.
 * 
 * @author Tanay Kumar
 * @since March 4th, 2023
*/
public class Identifier
{
	private String name;	// Identifier name
	private double value;	// Identifier value
	
	// Constructor
	public Identifier(String nameIn, double valueIn)
	{
		name = nameIn;
		value = valueIn;
	}
	
	// Gets the identifier name
	public String getName()
	{
		return name;
	}
	
	// Gets the identifier value
	public double getValue()
	{
		return value;
	}
	
	/**
	 * Sets the identifier value passed in to value field variable
	 * 
	 * @param v		Value to set to value field variable
	 */
	public void setValue(double v)
	{
		value = v;
	}
}
