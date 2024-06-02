import java.util.Scanner;

public class USMap
{
	private Scanner reader1;
	
	// This method makes an instance of USMap and uses it to call the method
	// runIt().
	public static void main(String[] args)
	{
		USMap usm = new USMap();
		usm.runIt();
	}
	
	// This method calls the method setupCanvas(), then it reads and stores 
	// the info from cities.txt and bigCities.txt to their own array. Then
	// this method gets the coordinates from city.txt and the city names
	// from cities.txt and bigCities.txt. Then this method calls the methods
	// drawGrayPoints() and drawBigCities().
	public void runIt()
	{
		setupCanvas();
		String[] cities = readFiles("cities.txt", 1112);
		String[] bigCities = readFiles("bigCities.txt", 275);
		double[] cityCoordinates = getCityCoordinates(cities, 1112);
		String[] bigCityNames = getCitiesName(bigCities, false);
		String[] cityNames = getCitiesName(cities, true);
		drawGrayPoints(cityCoordinates);
		drawBigCities(bigCityNames, cityNames, getPopulations(bigCities), cityCoordinates);
	}
	
	// This method sets up the canvas for the map to be drawn on.
	public void setupCanvas() 
	{
		StdDraw.setTitle("USMap");
		StdDraw.setCanvasSize(900, 512);
		StdDraw.setXscale(128.0, 65.0);
		StdDraw.setYscale(22.0, 52.0);
	}
	
	// This method uses the FilesUtils class to open the file and this
	// method reads each line and puts each line into an array then gets
	// returned. 
	public String[] readFiles(String fileName, int arrayLength)
	{
		reader1 = FileUtils.openToRead(fileName);
		String[] array = new String[arrayLength];
		int count = 0;
		while(reader1.hasNext())
		{
			array[count] = reader1.nextLine();
			count++;
		}
		return array;
	}
	
	// This method gets the coordinates of each city by reading the city
	// array and then splicing each line to get the longitude and latitude
	// and putting that into a new array which is returned. 
	public double[] getCityCoordinates(String[] array1, int arrayLength)
	{
		double[] array2 = new double[arrayLength*2];
		int count1 = 0;
		int count2 = 0;
		String arraySentence = "";
		String str1 = "";
		String str2 = "";
		while(count1 < arrayLength)
		{
			str1 = array1[count1].substring(0, array1[count1].indexOf(' '));
			arraySentence = array1[count1].substring(str1.length()+1);			
			str2 = arraySentence.substring(0, arraySentence.indexOf(' '));
			array2[count2] = Double.parseDouble(str2);
			count2++;
			array2[count2] = Double.parseDouble(str1);
			count1++;
			count2++;
		}

		return array2;
	}
	
	// This method draws each point from the coordinates array using the
	// StdDraw method point.
	public void drawGrayPoints(double[] coordinateArray)
	{
		int counter = 0;

		while (counter < (coordinateArray.length-1))
		{
			StdDraw.setPenColor(StdDraw.GRAY);
			StdDraw.setPenRadius(0.006);	
			StdDraw.point(coordinateArray[counter], coordinateArray[counter + 1]);
			counter++;
		}
	}
	
	// This method draws the red dots for the top ten biggest cities and
	// then draws the blue dots for any of the other big cities 
	public void drawBigCities(String[] bigCityNamesArray, String[] cityNamesArray,
								int[] populations, double[] coordinateArray)
	{
		int counter1 = 10; 
		int counter2 = 0; 
		int index = 0;
		
		while (counter1 < bigCityNamesArray.length)
		{
			counter2 = 0;
			while (counter2 < cityNamesArray.length)
			{
				if(bigCityNamesArray[counter1].equals(cityNamesArray[counter2]))
				{
					StdDraw.setPenColor(StdDraw.BLUE);
					StdDraw.setPenRadius(0.6 * (Math.sqrt(populations[counter1])/18500));
					index = counter2*2;
					StdDraw.point(coordinateArray[index], coordinateArray[index + 1]);
					// counter2 = cityNamesArray.length; // Check if duplicates are allowed
				}
				counter2++;
			}
			counter1++;
		}
		
		counter1 = 0;
		
		while(counter1 < 10)
		{
			StdDraw.setPenColor(StdDraw.RED);
			StdDraw.setPenRadius(0.6 * (Math.sqrt(populations[counter1])/18500));
			index = getBigCityIndexCoordinate(bigCityNamesArray[counter1], cityNamesArray) * 2;
			StdDraw.point(coordinateArray[index], coordinateArray[index + 1]);
			counter1++;
		}
	}
	
	// This method finds what index corresponds from the cities array to 
	// the big city array. 
	public int getBigCityIndexCoordinate(String bigCityName, String[] cityNamesArray)
	{
		int count = 0;
		int index = 0;
		
		while(count < cityNamesArray.length)
		{
			if(bigCityName.equals(cityNamesArray[count]))
			{
				index = count;
				count = cityNamesArray.length;
			}
			
			else
			{
				count++;
			}
		}
		
		return index;
	}
	
	// This method takes in an array and splices each line to get the city 
	// name. The boolean isCities is used to dtermines which array has been
	// inputed because it is a diffrent process to splice the cities vs.
	// bigCities text files. This method get each city name and returns
	// the array.
	public String[] getCitiesName(String[] array1, boolean isCities)
	{
		int counter = 0;
		String city = "";
		String[] cityNames = new String[array1.length];
		while(counter < array1.length)
		{
			city = array1[counter].substring(0, array1[counter].indexOf(',') - 1);
			city = city.substring(city.indexOf(' ')+1);
			if(isCities)
			{
				city = city.substring(city.indexOf(' ')+1);
			}
			cityNames[counter] = city;
			counter++;
		}
		
		return cityNames;
	}
	
	// This method gets the big cities array and splices each line to get 
	// the population. The method puts the population into an array which
	// is returned.
	public int[] getPopulations(String[] array1)
	{
		int theCount = 0;
		int[] populations = new int[array1.length];
		String thePopulation = " ";
		while(theCount < array1.length)
		{
			thePopulation = array1[theCount].substring(array1[theCount].lastIndexOf(' ') + 1);
			populations[theCount] = Integer.parseInt(thePopulation);
			theCount++;
		}
		
		return populations;
	}
}
