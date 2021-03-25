import java.util.*;
import java.io.*;

public class DigitalPlumber
{
	private final String FILE_NAME = "puzzleInput.txt";
	
	public static void main(String[] args)
	{
		DigitalPlumber dP = new DigitalPlumber(); // Create a new instance of the class
		dP.partTwo(); // Call the part two samethod
	}
	
	private void partOne() // For part one of the challenge
	{
		HashMap<String, String[]> programLinks = new HashMap<String, String[]>(); // Store the links between the programs
		BufferedReader puzzleInput = null; // Initalise the puzzleInput BufferedReader
		try
		{
			puzzleInput = new BufferedReader(new FileReader(FILE_NAME)); // Open the file
		}
		catch (Exception e)
		{
			e.printStackTrace(); // Print what went wrong
		}
		
		String currentLine = ""; // Initalise the currentLine
		
		while (currentLine != null) // While there are lines to be read
		{
			try 
			{
				currentLine = puzzleInput.readLine(); // Read from the file
			}
			catch (Exception e)
			{
				e.printStackTrace(); // Print what went wrong
			}
			
			if (currentLine == null) // If the line is empty
			{
				break; // Break from the loop
			}
			
			// A line looks like this 0 <-> 396, 1867
			//                         /    \
			//                          Split here
			
			String[] splitLine = currentLine.split(" <-> "); // Split the line at " <-> "
			
			programLinks.put(splitLine[0], splitLine[1].split(", ")); // Store the link in the HashMap
			
		}
		
		for (String program : programLinks.keySet())
		{
			System.out.println(program + " links to " + Arrays.toString(programLinks.get(program)));
		}
		
		
		
		ArrayList<String> programsVisited = new ArrayList<String>(); // Store the programs that have already been visited
		followLinks("0", programsVisited, programLinks); // Follow the links
		System.out.println(programsVisited.size());
	}
	
	private void partTwo() // For part two of the challenge
	{
		HashMap<String, String[]> programLinks = new HashMap<String, String[]>(); // Store the links between the programs
		ArrayList<String> programsNotInAGroup = new ArrayList<String>(); // Track/store the programs that aren't in a group
		
		BufferedReader puzzleInput = null; // Initalise the puzzleInput BufferedReader
		try
		{
			puzzleInput = new BufferedReader(new FileReader(FILE_NAME)); // Open the file
		}
		catch (Exception e)
		{
			e.printStackTrace(); // Print what went wrong
		}
		
		String currentLine = ""; // Initalise the currentLine
		
		while (currentLine != null) // While there are lines to be read
		{
			try 
			{
				currentLine = puzzleInput.readLine(); // Read from the file
			}
			catch (Exception e)
			{
				e.printStackTrace(); // Print what went wrong
			}
			
			if (currentLine == null) // If the line is empty
			{
				break; // Break from the loop
			}
			
			// A line looks like this 0 <-> 396, 1867
			//                         /    \
			//                          Split here
			
			String[] splitLine = currentLine.split(" <-> "); // Split the line at " <-> "
			
			programLinks.put(splitLine[0], splitLine[1].split(", ")); // Store the link in the HashMap
			programsNotInAGroup.add(splitLine[0]); // Add the program to the ArrayList that contains the programs that aren't in a group
		}
		
		for (String program : programLinks.keySet())
		{
			//System.out.println(program + " links to " + Arrays.toString(programLinks.get(program)));
		}
		
		
		
		int numberOfGroups = 0; // Store the number of groups
		
		while (programsNotInAGroup.size() > 0) // While there are program not in a group
		{
			//System.out.println(programsNotInAGroup.size());
			ArrayList<String> programsVisited = new ArrayList<String>(); // Store the programs that have already been visited
			followLinksPartTwo(programsNotInAGroup.get(0), programsVisited, programLinks, programsNotInAGroup);
			numberOfGroups++; // Increment the number of groups
		}
		
		System.out.println("There are " + numberOfGroups + " groups!");
	}
	private void followLinks(String startProgram, ArrayList<String> visited, HashMap<String, String[]> links) // Follow the links
	{
		String[] programsToLinkTo = links.get(startProgram); // Get the links
		
		for (String link : programsToLinkTo) // Iterate over each link
		{
			if (visited.contains(link) == false) // If we've not already been to the program
			{
				visited.add(link); // Add it to the programs visited arrayList
				followLinks(link, visited, links); // Follow the links
			}
		}
	}
	private void followLinksPartTwo(String startProgram, ArrayList<String> visited, HashMap<String, String[]> links, ArrayList<String> notInAGroup) // Follow the links for part two
	{
		String[] programsToLinkTo = links.get(startProgram); // Get the links
		
		for (String link : programsToLinkTo) // Iterate over each link
		{
			if (visited.contains(link) == false) // If we've not already been to the program
			{
				visited.add(link); // Add it to the programs visited arrayList
				notInAGroup.remove(link); // Remove the program from the notInAGroup ArrayList
				followLinksPartTwo(link, visited, links, notInAGroup); // Follow the links
			}
		}
	}
}