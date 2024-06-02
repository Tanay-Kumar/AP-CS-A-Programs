import java.util.List;
import java.util.ArrayList;
/**
 *	SortMethods - Sorts an array of Integers in ascending order.
 *
 *	@author Mr. Greenstein and Tanay Kumar
 *	@since	30 November, 2023
 */

public class SortMethods 
{
	
	/**
	 *	Bubble Sort algorithm:in ascending order
	 *	@param arr		array of Integer objects to sort
	 */
	public void bubbleSort(List<City> arr) 
	{
		for(int outer = arr.size() - 1; outer > 0; outer--)
		{
			for(int inner = 0; inner < outer; inner++)
			{
				if(arr.get(inner).compareTo(arr.get(inner + 1)) > 0)
				{
					swap(arr, inner, inner+1);
				}
			}
		}
	}
	
	/**
	 *	Swaps two Integer objects in array arr
	 *	@param arr		array list of City objects
	 *	@param x		index of first object to swap
	 *	@param y		index of second object to swap
	 */
	private void swap(List<City> arr, int x, int y) 
	{
		City temp = arr.get(x); 
		arr.set(x, arr.get(y));
		arr.set(y, temp);
	}
	
	/**
	 *	Swaps two Integer objects in array arr
	 *	@param arr		array of City objects
	 *	@param x		index of first object to swap
	 *	@param y		index of second object to swap
	 */
	private void swap(City[] arr, int x, int y) 
	{
		City temp = arr[x];
		arr[x] = arr[y];
		arr[y] = temp;
	}
	
	/**
	 *	Selection Sort algorithm: in ascending order
	 *	@param arr		array of Integer objects to sort
	 */
	public void selectionSort(List<City> arr) 
	{
		for(int outer = arr.size() - 1; outer > 0; outer--)
		{
			for(int inner = 0; inner < outer; inner++)
			{
				if(arr.get(inner).compareTo(arr.get(outer)) > 0)
				{
					swap(arr, inner, outer);
				}
			}
		}
	}
	
	/**
	 * Insertion Sort algorithm: in ascending order (you implement)
	 * @param arr		array of Integer objects to sort
	 */
	public void insertionSort(List<City> arr) 
	{		
		for (int i = 1; i < arr.size(); i++)
		{
			City temp = arr.get(i);
			int j = i - 1;
			
			while (j >= 0 && arr.get(j).compareTo(temp) > 0)
			{
                arr.set(j + 1, arr.get(j));
                j = j - 1;
            }
            arr.set(j + 1, temp);
		}
	}
	
	/**
	 *	Merge Sort algorithm: in ascending order
	 *	@param arr		array of Integer objects to sort
	 */
	public void mergeSort(List<City> arr) 
	{
		City[] newArr = new City[arr.size()];
		City[] arrDuplicate = new City[arr.size()];
		
		for(int i = 0; i < arr.size(); i++)
		{
			arrDuplicate[i] = arr.get(i);
		}
		
		newArr = returnMergeSort(arrDuplicate);

		for(int i = 0; i < newArr.length; i++)
		{
			arr.set(i, newArr[i]);
		}
	}

	/**
	 * Merge Sort algorithm: splits arrays until
	 * the arrays are of length 1 or 2, then merges them using recursion.
	 * @param  arr		array of Integer objects to sort
	 * @return			array that is sorted
	 */
	public City[] returnMergeSort(City[] arr)
	{
		City [] left;
		City [] right;
		
		if(arr.length == 2 || arr.length == 1)
		{
			if(arr.length == 2 && (arr[0].compareTo(arr[1]) > 0))
			{
				swap(arr, 0, 1);
			}

			return arr;
		}

		else
		{
			left = split(arr, 0, arr.length/2);
			right = split(arr, arr.length/2 + 1, arr.length - 1);
			left = returnMergeSort(left);
			right = returnMergeSort(right);

			return merge(left, right);
		}
	}

	/**
	 * Copies the elements from the array passed in from 
	 * a specified start index to a specified end index.
	 * 
	 * @param  arr			array to be split
	 * @param  startIndex	starting index of arrays
	 * @param  endIndex		ending index of array
	 * @return returnArr	returns part of the original array
	 */
	public City[] split(City [] arr, int startIndex, int endIndex)
	{
		int index = 0;
		City [] returnArr = new City[endIndex - startIndex + 1];
		
		for(int i = startIndex; i <= endIndex; i++)
		{
			returnArr[index] = arr[i];
			index++;
		}
		
		return returnArr;
	}

	/**
	 * Merges the left and right arrays
	 * 
	 * @param  left			Left array
	 * @param  right		Right array 
	 * @return returnArr	Array that has merged left and right
	 */
	public City[] merge(City[] left, City[] right)
	{
		int leftIndex = 0; 
		int rightIndex = 0; 
		int returnArrIndex = 0; 
		City[] returnArr = new City[left.length + right.length];

		while(leftIndex < left.length && rightIndex < right.length)
		{
			if(left[leftIndex].compareTo(right[rightIndex]) < 0)
			{
				returnArr[returnArrIndex] = left[leftIndex];
				leftIndex++;
			}

			else if(right[rightIndex].compareTo(left[leftIndex]) < 0)
			{
				returnArr[returnArrIndex] = right[rightIndex];
				rightIndex++;
			}

			else 
			{
				returnArr[returnArrIndex] = left[leftIndex];
				leftIndex++;
			}
			returnArrIndex++;
		}


		while(rightIndex >= right.length && leftIndex < left.length)
		{
			returnArr[returnArrIndex] = left[leftIndex];
			leftIndex++;
			returnArrIndex++;
		}

		while(leftIndex >= left.length && rightIndex < right.length)
		{
			returnArr[returnArrIndex] = right[rightIndex];
			rightIndex++;
			returnArrIndex++;
		}

		return returnArr;
	}

	/*****************************************************************/
	/************************* For Testing ***************************/
	/*****************************************************************/
	
	//~ /**
	 //~ *	Print an array of Integers to the screen
	 //~ *	@param arr		the array of Integers
	 //~ */
	//~ public void printArray(List<Integer> arr) {
		//~ if (arr.size() == 0) System.out.print("(");
		//~ else System.out.printf("( %4d", arr.get(0));
		//~ for (int a = 1; a < arr.size(); a++) {
			//~ if (a % 10 == 0) System.out.printf(",\n  %4d", arr.get(a));
			//~ else System.out.printf(", %4d", arr.get(a));
		//~ }
		//~ System.out.println(" )");
	//~ }

	//~ public static void main(String[] args) {
		//~ SortMethods se = new SortMethods();
		//~ se.run();
	//~ }
	
	//~ public void run() {
		//~ ArrayList<Integer> arr = new ArrayList<Integer>(10);
		//~ // Fill arr with random numbers
		//~ for (int a = 0; a < 10; a++)
			//~ arr.add((int)(Math.random() * 100) + 1);
		//~ System.out.println("\nBubble Sort");
		//~ System.out.println("Array before sort:");
		//~ printArray(arr);
		//~ System.out.println();
		//~ bubbleSort(arr);
		//~ System.out.println("Array after sort:");
		//~ printArray(arr);
		//~ System.out.println();
		
		//~ // Fill arr with random numbers
		//~ for (int a = 0; a < 10; a++)
			//~ arr.set(a, (int)(Math.random() * 100) + 1);
		//~ System.out.println("\nSelection Sort");
		//~ System.out.println("Array before sort:");
		//~ printArray(arr);
		//~ System.out.println();
		//~ selectionSort(arr);
		//~ System.out.println("Array after sort:");
		//~ printArray(arr);
		//~ System.out.println();

		
		//~ // Fill arr with random numbers
		//~ for (int a = 0; a < 10; a++)
			//~ arr.set(a, (int)(Math.random() * 100) + 1);
		//~ System.out.println("\nInsertion Sort");
		//~ System.out.println("Array before sort:");
		//~ printArray(arr);
		//~ System.out.println();
		//~ insertionSort(arr);
		//~ System.out.println("Array after sort:");
		//~ printArray(arr);
		//~ System.out.println();

		//~ for (int a = 0; a < 10; a++)
			//~ arr.set(a, (int)(Math.random() * 100) + 1);

		//~ System.out.println("\nMerge Sort");
		//~ System.out.println("Array before sort:");
		//~ printArray(arr);
		//~ System.out.println();
		//~ mergeSort(arr);
		//~ System.out.println("Array after sort:");
		//~ printArray(arr);
		//~ System.out.println();

	//~ }
}
