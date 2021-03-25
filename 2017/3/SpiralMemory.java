import java.util.Scanner;

public class SpiralMemory 
{
	
	private int[][] positiveRowPositiveColumn = new int[1000][1000]; // Store the values from the positive rows and columns, including the 0 rows and columns
	private int[][] negativeRowPositiveColumn = new int[1000][1000]; // Store the values from the negative rows and positive columns, NOT 0 rows and columns
	private int[][] positiveRowNegativeColumn = new int[1000][1000]; // Store the values from the positive rows and negative column, including 0 rows
	private int[][] negativeRowNegativeColumn = new int[1000][1000]; // Store the values from the negative rows and column, NOT including 0 rows and columns
		
	public static void main(String[] args)
	{
		SpiralMemory sM = new SpiralMemory(); // Create a new instance of the class
		sM.startProgram(); // Call the start program method
	}
	
	public void startProgram() // Start program method 
	{
		partTwo();
		
	}
	
	private void partOne() // Part one of the challenge
	{
		// These should be stored as absolutes. i.e. not negatives
		int maxPositiveRow = 0; // Store the highest row number that has been visited at the top
		int maxPositiveColumn = 0; // Store the highest column number that has been visited on the left
		
		int maxNegativeRow = 0; // Store the highest row number that has been visited on the bottom
		int maxNegativeColumn = 0; // Store the highest column number that has been visited at the right
		
		// These can be negative
		int currentRow = 0; // Store the current row number
		int currentColumn = 0; // Store the current column number
		
		int currentNumber = 1; // Start from one
		
		String currentDirection = "right"; // Store the direction. To allow spiraling
		// Spiral goes right, up, left, down ... 
			
		Scanner inputScanner = new Scanner(System.in); // Declare an input scanner
		
		System.out.println("Please enter the target number: "); // Ask the user for the target number
		int targetNumber = inputScanner.nextInt(); // Get the int that the user has entered
		
		while (currentNumber != targetNumber) // While the program hasn't reached the target number
		{ // We need to spiral
			
			// Spiral code
			System.out.println(currentDirection + " "  + currentRow + "  " + currentColumn);
			if (currentDirection.equals("right")) // If we need to go right
			{
				// As going right changes the column
				currentColumn += 1; // Move right i.e. increase current column
				
				if (Math.abs(currentColumn) > maxPositiveColumn) // If we are now in the furthest column
				{
					maxPositiveColumn = Math.abs(currentColumn); // Set the max columm to the current column 
					currentDirection = "up"; // Change direction
				}
				
			}
			
			else if (currentDirection.equals("up")) // If we need to go up
			{
				// As going up changes the row
				currentRow += 1; // Move up i.e. increase current row
				
				if (Math.abs(currentRow) > maxPositiveRow) // If we are now in the furthest row
				{
					maxPositiveRow = Math.abs(currentRow); // Set the max row to the current row 
					currentDirection = "left"; // Change direction
				}
			}
			
			else if (currentDirection.equals("left")) // If we need to go left
			{
				// As going left changes the column
				currentColumn -= 1; // Move left i.e. decrease current column
				
				if (Math.abs(currentColumn) > maxNegativeColumn) // If we are now in the furthest column
				{
					maxNegativeColumn = Math.abs(currentColumn); // Set the max columm to the current column 
					currentDirection = "down"; // Change direction
				}
			}
			
			else if (currentDirection.equals("down")) // If we need to go down
			{
				// As going down changes the row
				currentRow -= 1; // Move down i.e. decrease current row
				
				if (Math.abs(currentRow) > maxNegativeRow) // If we are now in the furthest row
				{
					maxNegativeRow = Math.abs(currentRow); // Set the max columm to the current column 
					currentDirection = "right"; // Change direction
				}
			}
			
			currentNumber ++; // Increase current number as we now have moved 1 space
		}
		
		System.out.println("That number is in row " + currentRow + " and column " + currentColumn + " This means it should take " + (Math.abs(currentRow) + Math.abs(currentColumn)) + " steps.");
	}
	
	private void partTwo() // Part one of the challenge
	{
		// These should be stored as absolutes. i.e. not negatives
		int maxPositiveRow = 0; // Store the highest row number that has been visited at the top
		int maxPositiveColumn = 0; // Store the highest column number that has been visited on the left
		
		int maxNegativeRow = 0; // Store the highest row number that has been visited on the bottom
		int maxNegativeColumn = 0; // Store the highest column number that has been visited at the right
		
		// These can be negative
		int currentRow = 0; // Store the current row number
		int currentColumn = 0; // Store the current column number
		
		int sum = 0; // Start from one
		positiveRowPositiveColumn[0][0] = 1; // Store starting number
		
		String currentDirection = "right"; // Store the direction. To allow spiraling
		// Spiral goes right, up, left, down ... 
			
		Scanner inputScanner = new Scanner(System.in); // Declare an input scanner
		
		System.out.println("Please enter the target number: "); // Ask the user for the target number
		int targetNumber = inputScanner.nextInt(); // Get the int that the user has entered
		
		while (sum <= targetNumber) // While the program hasn't reached a number greater than the target number
		{ // We need to spiral
			
			// Spiral code
			//System.out.println(currentDirection + " "  + currentRow + "  " + currentColumn);
			if (currentDirection.equals("right")) // If we need to go right
			{
				// As going right changes the column
				currentColumn += 1; // Move right i.e. increase current column
				
				if (Math.abs(currentColumn) > maxPositiveColumn) // If we are now in the furthest column
				{
					maxPositiveColumn = Math.abs(currentColumn); // Set the max columm to the current column 
					currentDirection = "up"; // Change direction
				}
				
			}
			
			else if (currentDirection.equals("up")) // If we need to go up
			{
				// As going up changes the row
				currentRow += 1; // Move up i.e. increase current row
				
				if (Math.abs(currentRow) > maxPositiveRow) // If we are now in the furthest row
				{
					maxPositiveRow = Math.abs(currentRow); // Set the max row to the current row 
					currentDirection = "left"; // Change direction
				}
			}
			
			else if (currentDirection.equals("left")) // If we need to go left
			{
				// As going left changes the column
				currentColumn -= 1; // Move left i.e. decrease current column
				
				if (Math.abs(currentColumn) > maxNegativeColumn) // If we are now in the furthest column
				{
					maxNegativeColumn = Math.abs(currentColumn); // Set the max columm to the current column 
					currentDirection = "down"; // Change direction
				}
			}
			
			else if (currentDirection.equals("down")) // If we need to go down
			{
				// As going down changes the row
				currentRow -= 1; // Move down i.e. decrease current row
				
				if (Math.abs(currentRow) > maxNegativeRow) // If we are now in the furthest row
				{
					maxNegativeRow = Math.abs(currentRow); // Set the max columm to the current column 
					currentDirection = "right"; // Change direction
				}
			}
			
			if (currentRow != 0 || currentColumn != 0) // If we are not in the starting square
			{
				// Sum code
				sum = 0; // Store the sum
				// Keep track of the location which number we should be adding
				int sumRow = currentRow+1; //Start at the top
				int sumColumn = currentColumn-1; // Start from the left
				// There will be 8 numbers to add to it.
				// Lets go by row.
				// x x x 
				// x c x  // c being current number and x being those to add. 
				// x x x 
				// Lets go from top left number and go accross the columns and then down the rows
				System.out.println(currentRow + " " + currentColumn);
				for (int i = 0; i<3; i++) // Do this for the three rows
				{
					
					for (int j = 0; j<3; j++) // Do this for the 3 columns
					{
						if (sumRow != currentRow || sumColumn != currentColumn) // If we aren't trying to add the value that we are trying to find
						{
							sum += retrieveValue(sumRow, sumColumn); // Get the value and add it to the total
							System.out.println("Value from " + sumRow + "  " + sumColumn + " : " + sum);
						}
						sumColumn += 1; // Move along
					}
					
					sumRow -= 1; // Move down the rows
					sumColumn = currentColumn-1; // Reset current column
				}
			}
			
			// We should now have the value that needs to be stored
			
			if (currentRow >= 0) // If we have a positive row
			{
				if (currentColumn >= 0) // If we have a positive column
				{
					System.out.println("Storing PP");
					positiveRowPositiveColumn[currentRow][currentColumn] = sum; // Store the sum
				} 
				else if (currentColumn < 0) // IF we have a negative column
				{
					System.out.println("Storing PN");
					positiveRowNegativeColumn[currentRow][Math.abs(currentColumn)] = sum; // Store the sum
				}
			}
			else if (currentRow < 0) // If we have a negative row
			{
				if (currentColumn >= 0) // If we have a positive column
				{
					System.out.println("Storing NP");
					negativeRowPositiveColumn[Math.abs(currentRow)][currentColumn] = sum; // Store the sum
				}
				else if (currentColumn < 0) // If we have a negative column
				{
					System.out.println("Storing NN");
					negativeRowNegativeColumn[Math.abs(currentRow)][Math.abs(currentColumn)] = sum; // Store the sum
				}
			}
			
			// The value should now be correctly stored
			System.out.println(sum); // Print the sum to the console
			
		}
		
		
	}
	
	private int retrieveValue(int row, int column) // Retrive the value from which of the four arrays that it's stored in
	{
		// This is basically just the storing code but tweaked a bit
		int storedValue = 0;
		if (row >= 0) // If we have a positive row
			{
				if (column >= 0) // If we have a positive column
				{
					storedValue = positiveRowPositiveColumn[row][column]; // Retrieve the value
				} 
				else if (column < 0) // IF we have a negative column
				{
					storedValue = positiveRowNegativeColumn[row][Math.abs(column)]; // Retrieve the value
				}
			}
			else if (row < 0) // If we have a negative row
			{
				if (column >= 0) // If we have a positive column
				{
					storedValue = negativeRowPositiveColumn[Math.abs(row)][column]; // Retrieve the value
				}
				else if (column < 0) // If we have a negative column
				{
					storedValue = negativeRowNegativeColumn[Math.abs(row)][Math.abs(column)]; // Retrieve the value
				}
			}
		
		return storedValue; // Return the retrieved value
	}
}