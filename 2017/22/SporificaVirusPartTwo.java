import java.io.*;
import java.util.*;

public class SporificaVirusPartTwo
{
	private final String FILE_NAME = "puzzleInput.txt";
	
	private ArrayList<int[]> infectedNodes = null; // An array list to store the nodes that are infected
	private ArrayList<int[]> weakenedNodes = new ArrayList<int[]>(); // Initalise an arraylist to store the weakened nodes
	private ArrayList<int[]> flaggedNodes = new ArrayList<int[]>(); // Initalise an arraylist to store the flagged nodes
	
	public static void main(String[] args)
	{
		SporificaVirusPartTwo sV = new SporificaVirusPartTwo(); // Create a new instance of the class
		sV.partTwo(); // Call the part two method
	}
	
	private void partTwo() // For part two of the challenge
	{
		infectedNodes = loadStartingMap(); // Get the already infected nodes from the puzzle input
		
		//printInfectedNodes();
		
		// The coordinates of the virus
		int x = 0;
		int y = 0; 
		
		// 0 is up, 1 is right, 2 is down, 3 is left
		int direction = 0; // Store the current direction
		
		int numberOfInfectionsCaused = 0; // Store the number of infections causes
		
		for (int i = 0; i < 10000000; i++) // Do this 10 000 000 times
		{
			if (i % 100000 == 0)
			{
				System.out.println((float)i/10000000);
			}
			//System.out.println("x: " + x + " y: " + y);
			int[] currentNode = {x, y}; // Declare an int array that stores the current coordinates
			
			String nodeState = getNodeState(currentNode); // Get the current node's state
			
			// We need to turn based on whether the node state
			// Also change the state of the node as required
			
			if (nodeState.equals("infected")) // If the current node is infected
			{
				//System.out.println("Turning right!");
				direction += 1; // Turn right
				flag(currentNode); // Flag the current node as it's infected
			}
			else if (nodeState.equals("weakened")) // If the current node is weakened
			{
				infect(currentNode); // Infect the node
				numberOfInfectionsCaused++; // Increment the number of infections caused
			}
			else if (nodeState.equals("flagged")) // If the current node is flagged
			{
				clean(currentNode); // Clean the current node
				direction += 2; // Reverse direction i.e. turn 180
			}
			else // The current node is not infected
			{
				//System.out.println("Turning left!");
				direction -= 1; // Turn left
				weaken(currentNode); // weaken the current node as it's clean
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
			
			/*
			System.out.println("infected");
			printNodes(infectedNodes);
			System.out.println("flagged");
			printNodes(flaggedNodes);
			System.out.println("weakened");
			printNodes(weakenedNodes);
			*/
		}
		
		System.out.println(numberOfInfectionsCaused + " infections were caused!");
	}
	
	private void printNodes(ArrayList<int[]> nodes) // Debug method to print the nodes
	{
		for (int[] node : nodes) // For each node
		{
			System.out.println(Arrays.toString(node)); // Print the node
		}
	}
	
	private String getNodeState(int[] node) // To get the state of the node
	{
		String state = null; // Store the state of the node
		
		
		if (state == null) // If the state hasn't been found yet
		{
			// Check the infected nodes
			for (int[] infectedNode : infectedNodes) // For each infected node
			{
				if (Arrays.equals(infectedNode, node)) // If they are equal
				{
					state = "infected"; // The node is infected
					infectedNodes.remove(infectedNode); // Remove it from the array
					break;
				}
			}
		}
		if (state == null) // If the state hasn't been found yet
		{
			// Check the weakened nodes
			for (int[] weakenedNode : weakenedNodes) // For each weakened node
			{
				if (Arrays.equals(weakenedNode, node)) // If they are equal
				{
					state = "weakened"; // The node is weakened
					weakenedNodes.remove(weakenedNode); // Remove it from the array
					break;
				}
			}
		}	
		if (state == null) // If the state hasn't been found yet
		{
			// Check the flagged nodes
			for (int[] flaggedNode : flaggedNodes) // For each flagged node
			{
				if (Arrays.equals(flaggedNode, node)) // If they are equal
				{
					state = "flagged"; // The node is flagged
					flaggedNodes.remove(flaggedNode); // Remove it from the array
					break;
				}
			}
		}
		
		if (state == null) // Last check the node must be clean
		{
			state = "clean"; // Set the state as clean
		}
		return state;
	}
	
	
	private void flag(int[] node) // To flag a node
	{
		/*
		// First we need to remove it from the infected nodes array
		for (int[] infectedNode : infectedNodes) // For each infected node
		{
			if (Arrays.equals(infectedNode, node)) // If they are equal
			{
				infectedNodes.remove(infectedNode); // Remove it from the infected nodes array
				break; // Break from the loop
			}
		}
		*/
		
		flaggedNodes.add(node); // Add it to the flagged nodes array
	}
	
	private void clean(int[] node) // To clean a node
	{
		for (int[] flaggedNode : flaggedNodes) // For each flagged node
		{
			if (Arrays.equals(flaggedNode, node)) // They are equal
			{
				flaggedNodes.remove(flaggedNode); // Remove the node from the array
				break; // Break from the loop
			}
		}
	}
	
	private void weaken(int[] node) // To weaken a node
	{
		weakenedNodes.add(node); // Add it to the weakened nodes array
	}
	
	private void infect(int[] node) // To infect a node
	{	
		// First we need to remove the node from the weakened nodes array
		/*
		for (int[] weakenedNode : weakenedNodes) // For each weakened node
		{
			if (Arrays.equals(weakenedNode, node)) // If they are equal
			{
				weakenedNodes.remove(weakenedNode); // Remove it from the weakened nodes array
				break; // Break from the loop
			}
		}
		*/
		
		infectedNodes.add(node); // Add the node to the infected nodes array
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