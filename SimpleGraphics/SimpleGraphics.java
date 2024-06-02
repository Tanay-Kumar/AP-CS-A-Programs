/**
 *	SimpleGraphics.java
 *
 *	To compile Linux:	javac -cp .:acm.jar SimpleGraphics.java
 *	To execute Linux:	java -cp .:acm.jar SimpleGraphics
 *	To compile MS Powershell:	javac -cp ".;acm.jar" SimpleGraphics.java
 *	To execute MS Powershell:	java -cp ".;acm.jar" SimpleGraphics
 *
 *	@author	Your name
 *	@since	Today's date
 */
 
/*	All package classes should be imported before the class definition.
 *	"java.awt.Color" means package java.awt contains class Color. */
import java.awt.Color;

/*	The following libraries are in the acm.jar file. */
import acm.program.GraphicsProgram;
import acm.graphics.GLabel;
import acm.graphics.GOval;
import acm.graphics.GPoint;
import acm.graphics.GPolygon;
import acm.graphics.GRect;
import acm.graphics.GRectangle;

public class SimpleGraphics extends GraphicsProgram {
	
	/*	All fields and constants should be declared here.
	 *	Only constants (final) are initialized here. */
	private GOval circle;
	private final double RADIUS = 25;
	
	private GRect square;
	private final double SIDE = 40;
	private final double HEIGHT = 20;
	private final double WIDTH = 50;
	private final int INTIAL_X_COORDINATE = 120;
	
	/**	The init() method is executed before the run() method.
	 *	All initialization steps should be performed here.
	 */
	public void init() {

	}
	
	/**	The run() method is executed after init().
	 *	The bulk of the program should be performed here.
	 *	Exercise hint: Use one-dimensional arrays for the GOval's and GRect's.
	 */
	public void run() 
	{
		drawTarget();
		drawBricks();
	}
	
	public void drawTarget()
	{
		int[] coordinates = {250, 350, 275, 375, 300, 400, 325, 425, 350, 450};
		int[] radiusMultiplier = {10, 8, 6, 4, 2};
		int counter = 1;
		int coordinateIndex = 0;
		while (counter <= 5)
		{
			double radiusSize = RADIUS * radiusMultiplier[counter-1];
			circle = new GOval(coordinates[coordinateIndex], 
							coordinates[coordinateIndex + 1], radiusSize, 
							radiusSize);
			circle.setFilled(true);
			if (counter % 2 == 1)
				circle.setFillColor(Color.RED);
			else
				circle.setFillColor(Color.WHITE);
			add(circle);
			coordinateIndex+=2;
			counter++;
		}
	}
	
	public void drawBricks()
	{
		int rowAmount = 10;
		int rowCount = 0;
		int xCoor = -1;
		int yCoor = -1;
		int rowShift = (int)(HEIGHT + 5);
		
		for (int i = 0; i < 10; i++)
		{
			for(int j = 0; j < rowAmount; j++)
			{
				xCoor = (int)(j * WIDTH) + (rowShift * rowCount) 
						+ INTIAL_X_COORDINATE;
				yCoor = i * 20;
				square = new GRect(xCoor, yCoor, WIDTH, HEIGHT);
				add(square);
			}
			rowAmount--;
			rowCount++;
		}
	}
}
