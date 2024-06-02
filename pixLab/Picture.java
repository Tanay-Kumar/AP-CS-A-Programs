import java.awt.*;
import java.awt.font.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.text.*;
import java.util.*;
import java.util.List; // resolves problem with java.awt.List and java.util.List

/**
 * A class that represents a picture.  This class inherits from 
 * SimplePicture and allows the student to add functionality to
 * the Picture class.  
 * 
 * @author Barbara Ericson ericson@cc.gatech.edu and Tanay Kumar
 * @since Feburary 15th, 2024
 */
public class Picture extends SimplePicture 
{
  ///////////////////// constructors //////////////////////////////////
  
  /**
   * Constructor that takes no arguments 
   */
  public Picture ()
  {
    /* not needed but use it to show students the implicit call to super()
     * child constructors always call a parent constructor 
     */
    super();  
  }
  
  /**
   * Constructor that takes a file name and creates the picture 
   * @param fileName the name of the file to create the picture from
   */
  public Picture(String fileName)
  {
    // let the parent class handle this fileName
    super(fileName);
  }
  
  /**
   * Constructor that takes the width and height
   * @param height the height of the desired picture
   * @param width the width of the desired picture
   */
  public Picture(int height, int width)
  {
    // let the parent class handle this width and height
    super(width,height);
  }
  
  /**
   * Constructor that takes a picture and creates a 
   * copy of that picture
   * @param copyPicture the picture to copy
   */
  public Picture(Picture copyPicture)
  {
    // let the parent class do the copy
    super(copyPicture);
  }
  
  /**
   * Constructor that takes a buffered image
   * @param image the buffered image to use
   */
  public Picture(BufferedImage image)
  {
    super(image);
  }
  
  ////////////////////// methods ///////////////////////////////////////
  
  /**
   * Method to return a string with information about this picture.
   * @return a string with information about the picture such as fileName,
   * height and width.
   */
  public String toString()
  {
    String output = "Picture, filename " + getFileName() + 
      " height " + getHeight() 
      + " width " + getWidth();
    return output;
    
  }
  
  /** Method to set the blue to 0 */
  public void zeroBlue()
  {
    Pixel[][] pixels = this.getPixels2D();
    for (Pixel[] rowArray : pixels)
    {
      for (Pixel pixelObj : rowArray)
      {
        pixelObj.setBlue(0);
      }
    }
  }
 
  /** Method that keeps only blue values */
  public void keepOnlyBlue()
  {
    Pixel [][] pixels = this.getPixels2D();
    for(Pixel[] rowArray : pixels)
    {
      for(Pixel pixelObj : rowArray)
      {
        int blue = pixelObj.getBlue();
        pixelObj.setColor(new Color(0, 0, blue));
      }
    }
  }

  /** Method that mirrors the picture around a 
    * vertical mirror in the center of the picture
    * from left to right */
  public void mirrorVertical()
  {
    Pixel[][] pixels = this.getPixels2D();
    Pixel leftPixel = null;
    Pixel rightPixel = null;
    int width = pixels[0].length;
    for (int row = 0; row < pixels.length; row++)
    {
      for (int col = 0; col < width / 2; col++)
      {
        leftPixel = pixels[row][col];
        rightPixel = pixels[row][width - 1 - col];
        rightPixel.setColor(leftPixel.getColor());
      }
    } 
  }
  
	/** Mirror just part of a picture of a temple */
	public void mirrorTemple()
	{
		int mirrorPoint = 276;
		Pixel leftPixel = null;
		Pixel rightPixel = null;
		int count = 0;
		Pixel[][] pixels = this.getPixels2D();

		// loop through the rows
		for (int row = 27; row < 97; row++)
		{
			// loop from 13 to just before the mirror point
			for (int col = 13; col < mirrorPoint; col++)
			{
				leftPixel = pixels[row][col];      
				rightPixel = pixels[row][mirrorPoint - col + mirrorPoint];
				rightPixel.setColor(leftPixel.getColor());
			}
		}
	}
  
	/** copy from the passed fromPic to the
	* specified startRow and startCol in the
	* current picture
	* @param fromPic the picture to copy from
	* @param startRow the start row to copy to
	* @param startCol the start col to copy to
	*/
	public void copy(Picture fromPic, 
		int startRow, int startCol)
		{
		Pixel fromPixel = null;
		Pixel toPixel = null;
		Pixel[][] toPixels = this.getPixels2D();
		Pixel[][] fromPixels = fromPic.getPixels2D();
		for (int fromRow = 0, toRow = startRow; fromRow < fromPixels.length &&
			toRow < toPixels.length; fromRow++, toRow++)
		{
			for (int fromCol = 0, toCol = startCol; fromCol < fromPixels[0].length &&
				toCol < toPixels[0].length;  fromCol++, toCol++)
			{
				fromPixel = fromPixels[fromRow][fromCol];
				toPixel = toPixels[toRow][toCol];
				toPixel.setColor(fromPixel.getColor());
			}
		}   
	}

	/** Method to create a collage of several pictures */
	public void createCollage()
	{
		Picture flower1 = new Picture("flower1.jpg");
		Picture flower2 = new Picture("flower2.jpg");
		this.copy(flower1,0,0);
		this.copy(flower2,100,0);
		this.copy(flower1,200,0);
		Picture flowerNoBlue = new Picture(flower2);
		flowerNoBlue.zeroBlue();
		this.copy(flowerNoBlue,300,0);
		this.copy(flower1,400,0);
		this.copy(flower2,500,0);
		this.mirrorVertical();
		this.write("collage.jpg");
	}
  
  
	/** Method to show large changes in color 
	* @param edgeDist the distance for finding edges
	*/
	public void edgeDetection(int edgeDist)
	{
		Pixel leftPixel = null;
		Pixel rightPixel = null;
		Pixel[][] pixels = this.getPixels2D();
		Color rightColor = null;
		for (int row = 0; row < pixels.length; row++)
		{
			for (int col = 0; 
				col < pixels[0].length-1; col++)
			{
				leftPixel = pixels[row][col];
				rightPixel = pixels[row][col+1];
				rightColor = rightPixel.getColor();
				if (leftPixel.colorDistance(rightColor) > 
				edgeDist)
					leftPixel.setColor(Color.BLACK);
				else
					leftPixel.setColor(Color.WHITE);
			}
		}
	}
  
	/** Makes the negative of the picture **/
	public void negate()
	{
		Pixel[][] pixels = this.getPixels2D();
		int red = 0;
		int blue = 0;
		int green = 0;
		
		for (Pixel[] rowArray : pixels)
		{
			for (Pixel pixelObj : rowArray)
			{
				red = 255 - pixelObj.getRed();
				green = 255 - pixelObj.getGreen();
				blue = 255 - pixelObj.getBlue();
				pixelObj.setColor(new Color(red, green, blue));
			}
		}
	}

	/** Method that makes picture gray scale */
	public void grayscale()
	{
		Pixel[][] pixels = this.getPixels2D();
		int avg = 0;
		
		for(Pixel[] rowArray : pixels)
		{
			for(Pixel pixelObj : rowArray)
			{
				avg = pixelObj.getRed() + pixelObj.getBlue()
						+ pixelObj.getGreen();
				avg /= 3;
				pixelObj.setColor(new Color(avg, avg, avg));
			}
		}
	}
	
	/**
	 * This method pixelates a picture
	 * @param size 		Side length of square area to pixelate.
	 */
	public void pixelate(int size)
	{
		int height = this.getHeight();
		int width = this.getWidth();
		Pixel[][] pixels = this.getPixels2D();

		// Iterate over the pixels
		for(int row = 0; row < height; row += size)
		{
			for(int col = 0; col < width; col += size)
			{
				int redAvg = 0;
				int greenAvg = 0;
				int blueAvg = 0;
				int count = 0;

				// Get the average red, green, and blue values of the square
				for(int i = row; i < row + size && i < height; i++)
				{
					for(int j = col; j < col + size && j < width; j++)
					{
						Pixel pixel = pixels[i][j];
						redAvg += pixel.getRed();
						greenAvg += pixel.getGreen();
						blueAvg += pixel.getBlue();
						count++;
					}
				}

				// Set the square to the average color
				redAvg = redAvg / count;
				greenAvg = greenAvg / count;
				blueAvg = blueAvg / count;
				Color averageColor = new Color(redAvg, greenAvg, blueAvg);
				for(int i = row; i < row + size && i < height; i++)
				{
					for(int j = col; j < col + size && j < width; j++)
					{
						Pixel pixel = pixels[i][j];
						pixel.setColor(averageColor);
					}
				}
			}
		}
	}
	
	/**
	 * This method blurs a picture
	 * 
	 * @param size 		Blur size, greater is more blur
	 * @return 			Blurred picture
	 */
	public Picture blur(int size)
	{
		Pixel[][] pixels = this.getPixels2D();
		Picture result = new Picture(pixels.length, pixels[0].length);
		Pixel[][] resultPixels = result.getPixels2D();

		// loop through all the pixels in the original picture
		for(int row = 0; row < pixels.length; row++)
		{
			for(int col = 0; col < pixels[0].length; col++)
			{
			  // calculate the average RGB values of the surrounding pixels
			  // based on the blur size
			  int redAvg = 0;
			  int greenAvg = 0;
			  int blueAvg = 0;
			  int count = 0;
			  
			  for(int i = row - size; i <= row + size; i++)
			  {
				for(int j = col - size; j <= col + size; j++)
				{
				  if(i >= 0 && i < pixels.length && j >= 0 && j < pixels[0].length)
				  {
					redAvg += pixels[i][j].getRed();
					greenAvg += pixels[i][j].getGreen();
					blueAvg += pixels[i][j].getBlue();
					count++;
				  }
				}
			  }
			  // set the average RGB values for the current pixel in the blurred picture
			  resultPixels[row][col].setRed(redAvg / count);
			  resultPixels[row][col].setGreen(greenAvg / count);
			  resultPixels[row][col].setBlue(blueAvg / count);
			}
		}
		
		return result;
	}
	
	/**
	 * Enhances a picture by getting average Color around a pixel then applies the following formula:
	 * pixelColor <- 2 * currentValue - averageValue
	 *
	 * size is the area to sample for blur.
	 *
	 * @param size Larger means more area to average around pixel and longer compute time.
	 * @return enhanced picture
	 */
	public Picture enhance(int size)
	{
		Pixel[][] pixels = this.getPixels2D();
		Picture result = new Picture(pixels.length, pixels[0].length);
		Pixel[][] resultPixels = result.getPixels2D();

		for(int row = 0; row < pixels.length; row++)
		{
			for(int col = 0; col < pixels[0].length; col++)
			{
				int averageRed = 0;
				int averageGreen = 0;
				int averageBlue = 0;
				int count = 0;

				// get average RGB values of the surrounding pixels
				for(int i = -size/2; i <= size/2; i++)
				{
					for(int j = -size/2; j <= size/2; j++)
					{
						if(row + i >= 0 && row + i < pixels.length &&
							col + j >= 0 && col + j < pixels[0].length)
						{
							 averageRed += pixels[row + i][col + j].getRed();
							 averageGreen += pixels[row + i][col + j].getGreen();
							 averageBlue += pixels[row + i][col + j].getBlue();
							 count++;
						}
					}
				}

				averageRed /= count;
				averageGreen /= count;
				averageBlue /= count;

				// apply the formula to enhance the pixel
				int enhancedRed = 2 * pixels[row][col].getRed() - averageRed;
				int enhancedGreen = 2 * pixels[row][col].getGreen() - averageGreen;
				int enhancedBlue = 2 * pixels[row][col].getBlue() - averageBlue;

				// set the enhanced values to the result picture
				resultPixels[row][col].setRed(Math.min(Math.max(enhancedRed, 0), 255));
				resultPixels[row][col].setGreen(Math.min(Math.max(enhancedGreen, 0), 255));
				resultPixels[row][col].setBlue(Math.min(Math.max(enhancedBlue, 0), 255));
			}
		}

		return result;
	}
	
	/**
	 * This method moves the image right by a percentage given
	 * 
	 * @param percentage 	How much the image should move
	 * @return 				Modfified image
	 */
	public Picture shiftRight(int percentage)
	{
		Pixel[][] pixels = this.getPixels2D();
		Picture result = new Picture(this);
		Pixel[][] resultPixels = result.getPixels2D();
				
		int numRows = pixels.length;
		int numCols = pixels[0].length;

		// Calculate the number of columns to shift by
		int shift = (int)Math.round(percentage * numCols / 100.0);

		// Shift the elements in the array
		for (int i = 0; i < numRows; i++) 
		{
			for (int j = 0; j < numCols; j++) 
			{
				int newJ = (j + shift) % numCols;
				pixels[i][newJ].setColor(resultPixels[i][j].getColor());
			}
		}

		return this;
	}
	
	/**
	 * Creates stair steps for a picture. Pixels wrap around.
	 * 
	 * @return		Modified Image
	 */
	public Picture stairStep(int shiftCount, int steps)
	{
      int height = this.getHeight();
      int width = this.getWidth();
      
      Pixel[][] pixels = this.getPixels2D();
      Picture result = new Picture(this);
      Pixel[][] resultPixels = result.getPixels2D();
      
      int step = 1;
      int count = 0;
      
      for(int row = 0;  row < height; row++)
      {
          for(int col = 0; col < width; col++)
          {
              if(height / steps == count)
              {
                  step++;
                  count = 0;
              }
              
              Pixel temp = pixels[row][col];
              int newCol = col + shiftCount * step;
              
              while(newCol >= width)
              {
                  newCol -= width;
			  }
              
              resultPixels[row][newCol].setColor(new Color(temp.getRed(),
										temp.getGreen(), temp.getBlue()));
          }
          
          count++;
      }
      
      return result;
	}
	
	/**
	 * This method turns the image turned 90 degrees
	 * 
	 * @return		Modified Image
	 */
	public Picture turn90() 
	{
		Pixel[][] pixels = this.getPixels2D();
		Picture result = new Picture(pixels[0].length, pixels.length);
		Pixel[][] resultPixels = result.getPixels2D();
		
		for (int i = 0; i < pixels.length; i++) 
		{
			for (int j = 0; j < pixels[0].length; j++) 
			{
				// Place each element in the corresponding position of the result array
				resultPixels[j][pixels.length - i - 1].setColor(pixels[i][j].getColor());
			}
		}

		return result;
	}
	
	/**
	 * This method zooms into the top lest corner, only processing 25% of 
	 * the picture area.
	 * 
	 * @return 		the Image zoomed in.
	 */
	public Picture zoomUpperLeft()
	{
		Pixel[][] pixels = this.getPixels2D();	
		Picture result = new Picture(pixels.length, pixels[0].length);
		Pixel[][] resultPixels = result.getPixels2D();
		
		int row = 0;
		int col = 0;
		
		for (int r = 0; r < pixels.length / 2; r++)
		{
			for (int c = 0; c < pixels[0].length / 2; c++)
			{
				if (col < pixels[0].length && row < pixels.length)
				{
					int temp = row + 1;
					
					resultPixels[row][col].setColor(new Color (pixels[r][c].getRed(),
													pixels[r][c].getGreen(), 
													pixels[r][c].getBlue()));
					
					resultPixels[temp][col].setColor(new Color (pixels[r][c].getRed(),
													pixels[r][c].getGreen(), 
													pixels[r][c].getBlue()));
					col++;
					
					resultPixels[row][col].setColor(new Color (pixels[r][c].getRed(),
													pixels[r][c].getGreen(), 
													pixels[r][c].getBlue()));
					
					resultPixels[temp][col].setColor(new Color (pixels[r][c].getRed(),
													pixels[r][c].getGreen(), 
													pixels[r][c].getBlue()));
					col++;		
				}
			}
			
			col = 0;
			row += 2;
			
		}
		
		return result;
	}
	
	/**
	 * This method reduces the image by 25% and mirrors it across.
	 * 
	 * @return		Modified Image
	 */
	public Picture tileMirror()
	{
		Pixel[][] pixels = this.getPixels2D();	
		Picture result = new Picture(pixels.length, pixels[0].length);
		Pixel[][] resultPixels = result.getPixels2D();
		int avgRed = 0;
		int avgGreen = 0;
		int avgBlue = 0;
		
		for(int row = 1; row < pixels.length; row += 2)
		{
			for(int col = 1; col < pixels[0].length; col += 2)
			{
				avgRed = 0;
				avgGreen = 0;
				avgBlue = 0;
				
				if(row < pixels.length && col < pixels[0].length)
				{
					avgRed = pixels[row - 1][col - 1].getRed()+
						pixels[row][col].getRed()+	
						pixels[row][col - 1].getRed()+
						pixels[row - 1][col].getRed();
				
				
					avgGreen = pixels[row - 1][col - 1].getGreen()+
							pixels[row][col].getGreen()+	
							pixels[row][col - 1].getGreen()+
							pixels[row - 1][col].getGreen();
							
					avgBlue = pixels[row - 1][col - 1].getBlue()+
							pixels[row][col].getBlue()+	
							pixels[row][col - 1].getBlue()+
							pixels[row - 1][col].getBlue();
					
					resultPixels[row / 2][col / 2].setColor(new Color(avgRed/4, 
													avgGreen/4, avgBlue/4));
				}
				
			}
			
		}
		
		for(int row = 0; row < pixels.length / 2; row++)
		{
			for(int col = 0; col < pixels[0].length / 2; col++)
			{
				resultPixels[row][pixels[0].length - 1 - col - 3 
							* pixels[0].length % 2].setColor(
							new Color(resultPixels[row][col].getRed(),
							resultPixels[row][col].getGreen(),
							resultPixels[row][col].getBlue()));
			}
		}
		
		for(int row = 0; row < pixels.length / 2; row++)
		{
			for(int col = 0; col < pixels[0].length; col++)
			{
				resultPixels[pixels.length - 1 - row - 3 * pixels.length % 2]
							[col].setColor(new Color(resultPixels[row][col].getRed(),
							resultPixels[row][col].getGreen(),
							resultPixels[row][col].getBlue()));

			}
		}

		return result;
	}
	
	/**
	 * Applies edge detection to an image
	 * 
	 * @param threshold 	Threshold for RGB color distance
	 * @return Picture 		Edge detected picture
	 */
	public Picture edgeDetectionBelow(int threshold)
	{
		Pixel[][] pixels = this.getPixels2D();
		Picture result = new Picture(this);
		Pixel[][] resultPixels = result.getPixels2D();

		for(int row = 0; row < pixels.length - 1; row++)
		{
			for(int col = 0; col < pixels[0].length; col++)
			{				
				if (pixels[row][col].colorDistance(pixels[row + 1][col].getColor()) 
															> threshold)
				{
					resultPixels[row][col].setColor(Color.BLACK);
				}
				
				else
				{
					resultPixels[row][col].setColor(Color.WHITE);
				}
			}
		}
		
		return result;
	}
	
	/**
	 * Greenscreens images by calling superImpose method
	 * 
	 * @param amplitude 	Amplitude of wavy distortion of the sinusoidal function
	 * @return Picture 		Setting with green screened images
	 */
	public Picture greenScreen()
	{
		Picture puppy = new Picture("images/puppy1GreenScreen.jpg");
		Picture kitten = new Picture("images/kitten2greenScreen.jpg");
		
		superImpose(puppy, 350, 50, 100, 0.8);
		superImpose(kitten, 450, 300, 100, 0.25);

		return this;
	}
	
	/**
	 * Superimposes images on a background. Removes the green background 
	 * pixels (greenscreen)
	 * 
	 * @param image			Image to be superimposed
	 * @param x				X coordinate to place the image on background image
	 * @param y				Y coordinate to place the image on background image
	 * @param factor		Factor to scale down image (between 0 - 1)
	 * @param threshold		Threshold for RGB color distance
	 */
	public void superImpose(Picture image, int x, int y, int threshold, double factor)
	{	
		Pixel[][] imagePixels = image.getPixels2D();
		Pixel[][] backPixels = this.getPixels2D();
		
		Color greenScreen = new Color(imagePixels[0][0].getRed(), 
									imagePixels[0][0].getGreen(), 
									imagePixels[0][0].getBlue());
		int row = 0;
		
		// Does not try to superimpose if the image is not loaded
		if(x > this.getHeight() || y > this.getWidth())
		{
			row = (int)(image.getHeight() * factor) + 1;
		}
		
		while (row < image.getHeight() * factor)
		{
			for(int col = 0; col < image.getWidth() * factor; col++)
			{
				Pixel imagePix = imagePixels[(int)(row / factor)]
											[(int)(col / factor)];
				
				if(imagePix.colorDistance(greenScreen) > threshold)
				{
					backPixels[row + x][col + y].setColor(imagePix.getColor());
				}
			}
			
			row++;
		}
	}
	
	/* Main method for testing - each class in Java can have a main 
	* method 
	*/
	public static void main(String[] args) 
	{
		//Picture beach = new Picture("images\beach.jpg");
		//beach.explore();
		//beach.zeroBlue();
		//beach.explore();
	}
  
} // this } is the end of class Picture, put all new methods before this
