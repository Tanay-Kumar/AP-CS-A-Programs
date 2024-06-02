/**
 *	SimpleGraphics.java
 *
 *	To compile:	javac -cp .:mvAcm.jar SimpleGraphics.java
 *	To execute:	java -cp .:mvAcm.jar SimpleGraphics
 *
 *	@author	Nathan Bao
 *	@since	9/20/2022
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

public class Bricks extends GraphicsProgram {
	
	/*	All fields and constants should be declared here.
	 *	Only constants (final) are initialized here. */
	private GRect[] rectangles = new GRect[55];
	
	/**	The init() method is executed before the run() method.
	 *	All initialization steps should be performed here.
	 */
	public void init() {

	}
	
	/**	The run() method is executed after init().
	 *	The bulk of the program should be performed here.
	 *	Exercise hint: Use one-dimensional arrays for the GOval's and GRect's.
	 */
	public void run() {
		int rectanglesinrow = 10;
		int counter = 0;
		int rowspassed = 0;
		for(int i = 0;i<10;i++){
			for(int a = 0;a<rectanglesinrow;a++){
				rectangles[counter] = new GRect(125+a*50+25*rowspassed, i*20, 50, 20);
				add(rectangles[counter]);
				counter++;
			}
			rectanglesinrow--;
			rowspassed++;
		}
	}
}
