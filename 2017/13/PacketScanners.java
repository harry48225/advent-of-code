import java.io.*;
import java.util.*;

public class PacketScanners
{
	private static String FILE_NAME = "puzzleInput.txt";
	
	private HashMap<Integer, Integer> rangesAtDepths = new HashMap<Integer, Integer>(); // Create a new HashMap to store the depth of each layer and its range
	private HashMap<Integer, Integer> scannerPositions = new HashMap<Integer, Integer>(); // Create a HashMap to store the positions of the scanner on each layer
	private HashMap<Integer, Integer> scannerDirections = new HashMap<Integer, Integer>(); // Create a HashMap to store the direction that each scanner is moving in
	
	private Integer highestDepth = 0; // Store the highest depth
	
	public static void main(String[] args)
	{
		PacketScanners pS = new PacketScanners(); // Create a new instance of the class
		pS.partTwo(); // Call the partTwo method
	}
	
	private void partOne() // For part one of the challenge
	{
		BufferedReader puzzleInput = null; // Initalise the puzzleInput BufferedReader
		try
		{
			puzzleInput = new BufferedReader(new FileReader(FILE_NAME)); // Open the puzzleInput text file
		}
		catch (Exception e)
		{
			e.printStackTrace(); // Print what went wrong
		}
		
		String currentLine = ""; // Initalise the current line variable
		
		Integer highestDepth = 0; // Store the highest depth
		
		try
		{
			while ((currentLine = puzzleInput.readLine()) != null) // While there are lines to be read
			{
				String[] depthAndRange = currentLine.split(": "); // Split the line at ": "
				Integer depth = Integer.parseInt(depthAndRange[0]); // Get the depth
				Integer range = Integer.parseInt(depthAndRange[1]); // Get the range
				highestDepth = depth; // Set the current depth as the highest depth 
				rangesAtDepths.put(depth, range); // Store the range and depth in the HashMap
				scannerDirections.put(depth, -1); // Store the depth in the HashMap and give it a direction of -1 i.e. it's moving up, the direction will get flipped as soon as the first moveScanners is called
				scannerPositions.put(depth, 1); // Start each scanner at position 1
			}
		}
		catch (Exception e)
		{
			e.printStackTrace(); // Print what went wrong
		}
		
		System.out.println("The highest depth was: " + highestDepth); // Tell the user what the highest depth was
		Integer totalSeverity = 0; // Store the total severity
		Integer packetPosition = -1; // The position of the packet
		while (packetPosition < highestDepth) // While we've not reached the highest depth
		{
			packetPosition += 1; // Increment the packet's position
			
			if (rangesAtDepths.keySet().contains(packetPosition)) // If there is a scanner at the current depth
			{
				boolean caught = scannerPositions.get(packetPosition).equals(1); // Check if the scanner is at position 1 i.e. the top of the depth the packet is currently at
				
				if (caught) // If the packet has been caught by the scanner
				{
					totalSeverity += packetPosition * rangesAtDepths.get(packetPosition); // Add depth * range (the severity) to the total severity 
				}
			}
			moveScanners(); // Move the scanners
		}
		
		System.out.println("The total severity was: " + totalSeverity);
		
	}
	
	private void partTwo() // For part two of the challenge
	{
		BufferedReader puzzleInput = null; // Initalise the puzzleInput BufferedReader
		try
		{
			puzzleInput = new BufferedReader(new FileReader(FILE_NAME)); // Open the puzzleInput text file
		}
		catch (Exception e)
		{
			e.printStackTrace(); // Print what went wrong
		}
		
		String currentLine = ""; // Initalise the current line variable
		
		
		try
		{
			while ((currentLine = puzzleInput.readLine()) != null) // While there are lines to be read
			{
				String[] depthAndRange = currentLine.split(": "); // Split the line at ": "
				Integer depth = Integer.parseInt(depthAndRange[0]); // Get the depth
				Integer range = Integer.parseInt(depthAndRange[1]); // Get the range
				highestDepth = depth; // Set the current depth as the highest depth 
				rangesAtDepths.put(depth, range); // Store the range and depth in the HashMap
				scannerDirections.put(depth, -1); // Store the depth in the HashMap and give it a direction of -1 i.e. it's moving up, the direction will get flipped as soon as the first moveScanners is called
				scannerPositions.put(depth, 1); // Start each scanner at position 1
			}
		}
		catch (Exception e)
		{
			e.printStackTrace(); // Print what went wrong
		}
		
		System.out.println("The highest depth was: " + highestDepth); // Tell the user what the highest depth was
		
		// Clone the hashmaps so we can reset them
		HashMap<Integer, Integer> rangesAtDepthsUnaltered = (HashMap) rangesAtDepths.clone();
		HashMap<Integer, Integer> scannerPositionsUnaltered = (HashMap) scannerPositions.clone();
		HashMap<Integer, Integer> scannerDirectionsUnaltered = (HashMap) scannerDirections.clone();
	
		Integer delay = 0; // Store the delay in picoseconds
		boolean caught = true;
		while (caught) // While we get caught when we run the simulation
		{
			delay++; // Increment the delay
			//System.out.println(delay);
			
			// Reset the hashmaps
			rangesAtDepths = (HashMap) rangesAtDepthsUnaltered.clone(); 
			scannerPositions = (HashMap) scannerPositionsUnaltered.clone();
			scannerDirections = (HashMap) scannerDirectionsUnaltered.clone();
			moveScanners(); // Move the scanners for 1 more picosecond of delay
			
			// Clone the hashmaps so we can reset them
			rangesAtDepthsUnaltered = (HashMap) rangesAtDepths.clone();
			scannerPositionsUnaltered = (HashMap) scannerPositions.clone();
			scannerDirectionsUnaltered = (HashMap) scannerDirections.clone();
			
			caught = runSimulation();
		}
		
		System.out.println("The delay should be: " + delay + " picoseconds!");
		
		
		
	}
	
	private void moveScanners() // Method to move the scanners
	{
		for (Integer scanner : scannerPositions.keySet()) // For each scanner in scannerPositions
		{
			boolean reachedTheBottom = scannerPositions.get(scanner).equals(rangesAtDepths.get(scanner)); // If the position is the range i.e. the bottom
			boolean reachedTheTop = scannerPositions.get(scanner).equals(1); // If the position is 1 i.e. the top
			if (reachedTheBottom || reachedTheTop) // If we've reached the bottom or reached the top
			{
				scannerDirections.put(scanner, scannerDirections.get(scanner)*-1); // Update and flip the direction
			}
			
			scannerPositions.put(scanner, scannerPositions.get(scanner) + scannerDirections.get(scanner)); // Move the scanner
			
			//System.out.println(scanner + " moved to: " + scannerPositions.get(scanner));
		}
		
	}
	
	private boolean runSimulation() // To move the packet through the maze
	{
		Integer packetPosition = -1; // The position of the packet
		boolean hasBeenCaught = false;
		while (packetPosition < highestDepth) // While we've not reached the highest depth
		{
			packetPosition += 1; // Increment the packet's position
			
			if (rangesAtDepths.keySet().contains(packetPosition)) // If there is a scanner at the current depth
			{
				boolean caught = scannerPositions.get(packetPosition).equals(1); // Check if the scanner is at position 1 i.e. the top of the depth the packet is currently at
				
				if (caught) // If the packet has been caught by the scanner
				{
					hasBeenCaught = true;
					break; // Break so the function returns
				}
			}
			moveScanners(); // Move the scanners
		}

		//System.out.println(hasBeenCaught);
		
		return hasBeenCaught; // Return whether the packet was caught
	}
}