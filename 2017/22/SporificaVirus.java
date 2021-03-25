import java.io.*;
import java.util.*;

public class SporificaVirus
{
	private final String FILE_NAME = "puzzleInput.txt";
	
	private ArrayList<int[]> infectedNodes = null; // An array list to store the nodes that are infected
	
	public static void main(String[] args)
	{
		SporificaVirus sV = new SporificaVirus(); // Create a new instance of the class
		sV.partOne(); // Call the part one method
	}
	
	private void partOne() // For part one of the challenge
	{
		infectedNodes = loadStartingMap(); // Get the already infected nodes from the puzzle input
		
		//printInfectedNodes();
		
		// The coordinates of the virus
		int x = 0;
		int y = 0; 
		
		// 0 is up, 1 is right, 2 is down, 3 is left
		int direction = 0; // Store the current direction
		
		int numberOfInfectionsCaused = 0; // Store the number of infections causes
		
		for (int i = 0; i < 10000; i++) // Do this 10 000 times
		{
			//System.out.println("x: " + x + " y: " + y);
			int[] currentNode = {x, y}; // Declare an int array that stores the current coordinates
			
			// We need to turn based on whether the node is infected
			// Also if the current node is infected make it clean, if it's clean infect it 
			if (isInfected(currentNode)) // If the current node is infected
			{
				//System.out.println("Turning right!");
				direction += 1; // Turn right
				clean(currentNode); // Clean the current node as it's infected
			}
			else // The current node is not infected
			{
				//System.out.println("Turning left!");
				direction -= 1; // Turn left
				infect(currentNode); // Infect the current node as it's clean
				//System.out.println("Infecting: " + Arrays.toString(currentNode));
				numberOfInfectionsCaused++; // Increment the number of infections caused
			}
			
			// Wrap around
			if (direction > 3) // If we need to wrap around
			{
				direction -= 4; // Wrap around
			}
			else if (direction < 0) // If we need to wrap around
			{
				direction += 4; // Wrap around
			}
			
			// Move
			if (direction == 0) // If the direction is up
			{
				y += 1; // Move up
			}
			else if (direction == 1) // If the direction is right
			{
				x += 1; // Move right
			}
			else if (direction == 2) // If the direction is down
			{
				y -= 1; // Move down
			}
			else if (direction == 3) // If the direction is left
			{
				x -= 1; // Move left
			}
			
			//printInfectedNodes();
		}
		
		System.out.println(numberOfInfectionsCaused + " infections were caused!");
	}
	
	private void printInfectedNodes() // Debug method to print the infected nodes
	{
		for (int[] node : infectedNodes) // For each node
		{
			System.out.println(Arrays.toString(node)); // Print the node
		}
	}
	
	private void clean(int[] node) // To clean a node
	{
		for (int[] infectedNode : infectedNodes) // For each infected node
		{
			if (Arrays.equals(infectedNode, node)) // They are equal
			{
				infectedNodes.remove(infectedNode); // Remove the node from the array
				break; // Break from the loop
			}
		}
	}
	
	private void infect(int[] node) // To infect a node
	{
		infectedNodes.add(node); // Add the node to the infected nodes array
	}
	
	private boolean isInfected(int[] node) // To check if a node is infected
	{
		boolean infected = false; // Store whether the node is infected
		
		for (int[] infectedNode : infectedNodes) // For each infected node
		{
			if (Arrays.equals(infectedNode, node)) // If they are equal
			{
				infected = true; // The node is infected
				break; // Break from the loop
			}
		}
		
		return infected;
	}
	
	private ArrayList<int[]> loadStartingMap() // To load the puzzle input file
	{
		BufferedReader puzzleInput = null; // Initalise a new variable to store the BufferedReader
		
		try
		{
			puzzleInput = new BufferedReader(new FileReader(FILE_NAME)); // Open the text file
		}
		catch (Exception e)
		{
			e.printStackTrace(); // Print what went wrong
		}
		
		String currentLine = ""; // Initalise a variable to store the current line
		
		int numberOfLines = 0; // Track the number of lines
		
		ArrayList<String> grid = new ArrayList<String>(); // Create an arraylist to store the grid
		
		while (currentLine != null) // While the current line isn't null
		{
			try
			{
				currentLine = puzzleInput.readLine(); // Get the next line
			}
			catch (Exception e)
			{
				e.printStackTrace(); // Print what went wrong
			}
			
			if (currentLine != null) // If the line isn't null
			{
				grid.add(currentLine); // Add the current line to the grid
				numberOfLines++; // Increment the number of lines
			}
			
		}
		
		ArrayList<int[]> loadedInfectedNodes = new ArrayList<int[]>(); // Initalise a new ArrayList
		
		int startingIndex = ((numberOfLines + 1)/2 - 1); // Calculate the starting index
		
		for (int i = 0; i < grid.size(); i++) // For each row
		{
			char[] line = grid.get(i).toCharArray(); // Get the line and convert it to character array
			
			for (int j = 0; j < line.length; j++) // For each character
			{
				if (line[j] == '#') // If it's infected
				{
					int[] coordinates = {-startingIndex+j ,startingIndex-i}; // Calculate the coordinates
					loadedInfectedNodes.add(coordinates); // Add the node to the infected nodes array with the correct coordinates
				}
			}
		}
		
		return loadedInfectedNodes; // Return the loaded nodes
	}

}