import java.io.*;
import java.util.*;

public class ASeriesOfTubes
{
	
	private final String FILE_NAME = "puzzleInput.txt";
	
	public static void main(String[] args)
	{
		ASeriesOfTubes aSOT = new ASeriesOfTubes(); // Create a new instance of the class
		aSOT.partOne(); // Call the part one method
	}
	
	private void partOne() // For part one of the challenge
	{
		char[][] tubes = loadTubes(); // Load the tubes from the text file
		
		// Store the current location in the array
		int x = 0;
		int y = 0;
		
		String direction = "down"; // Store the current direction
		
		// We need to find the start tube
		for (int i = 0; i<tubes[0].length; i++) // For each character in the first row
		{
			char tube = tubes[0][i]; // Get the character
			if (tube == '|') // If we've found the start tube
			{
				x = i; // Set the current x to the tube
				break; // Break as we have found the start tube
			}
		}
		
		// Fun time
		
		boolean endReached = false; // Store whether we have reached the end of the tubes
		String charactersVisited = ""; // Store the characters visited
		int numberOfSteps = 0; // Store the number of steps taken //PART TWO
		
		while (!endReached) // While we've not reached the end
		{
			char currentLocation = tubes[y][x]; // Get the character that we are on
			
			if (currentLocation == ' ') // If we're on a space
			{
				endReached = true; // We've reached the end
			}
			else
			{
				if (currentLocation == '+') // If we've reached a tube crossing
				{
					//System.out.println("At a crossing");
					// We need to determine the new direction
					if (direction.equals("up") || direction.equals("down")) // If we are going up or down
					{
						// We need to see whether we are going left or right
						if (tubes[y][x+1] != ' ') // If there is something other than a space to the right
						{
							direction = "right"; // Go right
						}
						else if (tubes[y][x-1] != ' ') // If there is something other than a space to the left
						{
							direction = "left"; // Go left
						}
					}
					else if (direction.equals("left") || direction.equals("right")) // If we are going left or right
					{
						// We need to see whether we are going left or right
						if (tubes[y-1][x] != ' ') // If there is something other than a space above
						{
							direction = "up"; // Go up
						}
						else if (tubes[y+1][x] != ' ') // If there is something other than a space below
						{
							direction = "down"; // Go down
						}
					}
					
				}
			
				else if  (currentLocation != '|' && currentLocation != '-') // It must be a letter
				{
					charactersVisited += currentLocation; // Add the letter to the characters visited string
				}
				
				// We need to move
				if (direction.equals("up")) // If we need to go up
				{
					y-=1; // Go up
				}
				else if (direction.equals("down")) // If we need to go down
				{
					y+=1; // Go down
				}
				else if (direction.equals("left")) // If we need to go left
				{
					x-=1; // Go left
				}
				else if (direction.equals("right")) // If we need to go right
				{
					x+=1; // Go right
				}
				
				//System.out.println(direction);
				numberOfSteps++; // Increment the number of steps taken // PART TWO
			}
		}
		
		System.out.println(charactersVisited);
		System.out.println(numberOfSteps); // PART TWO
	}
	
	private char[][] loadTubes() // Method to load the tubes from the file
	{
		BufferedReader puzzleInput = null; // Initalise a variable to store the puzzle input reader
		
		try
		{
			puzzleInput = new BufferedReader(new FileReader(FILE_NAME)); // Open the file
		}
		catch (Exception e) // Something went wrong
		{
			e.printStackTrace(); // Print what went wrong
		}
		
		char[][] tubes = new char[999][]; // Create a new 2d array to store the tubes
		int currentIndex = 0; // Store the index in the char array
		
		String line = ""; // Initalise the line variable
		
		boolean linesToRead = true; // Store whether there are lines to read in the file
		
		while (linesToRead) // While there are lines to read
		{
			try
			{
				line = puzzleInput.readLine(); // Read the next line from the text file
			}
			catch (Exception e)
			{
				e.printStackTrace(); // Print what went wrong
			}
			
			if (line == null) // If the line is empty
			{
				linesToRead = false; // There are no more lines to read
			}
			else
			{
				tubes[currentIndex] = line.toCharArray(); // Convert the line to a char array and store it in tubes
				//System.out.println(Arrays.toString(tubes[currentIndex]));
				currentIndex++; // Increment the current index
			}
		}
		
		return Arrays.copyOf(tubes, currentIndex); // Return a copy of the array with all the null elements chopped off
		
	}
}