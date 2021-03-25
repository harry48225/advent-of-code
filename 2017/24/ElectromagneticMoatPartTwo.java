import java.io.*;
import java.util.*;

public class ElectromagneticMoatPartTwo
{
	private final String FILE_NAME = "puzzleInput.txt";
	
	public static void main(String[] args)
	{
		ElectromagneticMoatPartTwo eM = new ElectromagneticMoatPartTwo(); // Create a new instance of the class
		eM.partTwo(); // Call the part two method
	}
	
	private void partTwo() // For part two of the challenge
	{
		ArrayList<String[]> components = loadComponents(); // Load the components from the file
		
		/* To print the loaded components
		for (String[] component : components)
		{
			System.out.println(Arrays.toString(component));
		}
		*/
		
		System.out.println(Arrays.toString(buildBridge("0", components, (long) 0, (long) 0))); // Built a bridge
		
	}
	
	private long[] buildBridge(String requiredPort, ArrayList<String[]> components, long startingScore, long startingLength) // To build the bridge - recursive function
	{
		// Should try all possible permutations of the components and return the score of the highest scoring one
		
		long highestScore = 0; // Store the highest score
		
		long runningScore = startingScore; // Store the running total
		
		long runningLength = startingLength; // Store the length
		
		long highestLength = 0; // Store the length
		
		for (String[] component : components) // For each component
		{
			String nextRequiredPort = null; // Initalise a variable to store the next required port
			
			if (component[0].equals(requiredPort)) // If the component has the right port
			{
				nextRequiredPort = component[1]; // The port on the other end is the next required port
			}
			else if (component[1].equals(requiredPort)) // If the other end of the component has the required port
			{
				nextRequiredPort = component[0]; // The port on the other end is the next required port
			}
			else // The component doesn't have the required port on either end
			{
				continue; // Go to the top of the loop
			}
			
			// This section will only be reached if we have a valid component
			ArrayList<String[]> newComponents = new ArrayList<String[]>(components); // Create a shallow copy of components
			newComponents.remove(component); // Remove the matched component
			
			long[] scores = buildBridge(nextRequiredPort, newComponents, (Long.parseLong(component[0]) + Long.parseLong(component[1])), runningLength+1); // Build a new bridge and get the score
			
			if (scores[0] > highestLength) // If we have a new longest
			{
				highestLength = scores[0]; // Set the length as the new highest
				highestScore = scores[1]; // Set the score as the new highest
			}
			else if (scores[0] == highestLength) // If the length is the same
			{
				if (scores[1] > highestScore) // If we have a new highest score
				{
					highestScore = scores[1]; // Set score as the new highest
				}
			}
		}
		
		long[] returns = new long[2];
		returns[0] = runningLength + highestLength; // The first item should be the length
		returns[1] = runningScore + highestScore;
		return returns; // Return the highest score
	}
	
	private ArrayList<String[]> loadComponents() // To load the puzzle input
	{
		BufferedReader puzzleInput = null; // Initalise a variable to store the puzzle input
		
		try
		{
			puzzleInput = new BufferedReader(new FileReader(FILE_NAME)); // Open the file
		}
		catch (Exception e) // Something went wrong
		{
			e.printStackTrace(); // Print what went wrong
		}
		
		ArrayList<String[]> components = new ArrayList<String[]>(); // Initalise a new ArrayList to store the loaded components
		
		String line = ""; // Initalise the current line variable
		
		while (line != null) // While the line isn't empty
		{
			try
			{
				line = puzzleInput.readLine(); // Read the next line from the file
			}
			catch (Exception e)
			{
				e.printStackTrace(); // Print what went wrong
			}
			
			if (line != null) // If the line isn't empty
			{
				components.add(line.split("/")); // Store the split line in the ArrayList
			}
		}
		
		return components; // Return the loaded components
	}
}