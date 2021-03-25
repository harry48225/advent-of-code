import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class RecursiveCircus
{
	private final String FILE_NAME = "puzzleInput.txt"; // File where the puzzle input is stored
	
	private String startProgramName;
	private HashMap<String, String[]> programNamesLinked; 
	private HashMap<String, Integer> programWeights;
	
	private HashMap<String, Integer[]> oddOnesOut = new HashMap<String, Integer[]>(); // Store the odd ones out and how much they were out by
	
	public static void main(String[] args)
	{
		RecursiveCircus rC = new RecursiveCircus(); // Create a new instance of the class
		rC.startProgram(); // Call the startProgram method
	}
	
	private void startProgram() // startProgram method
	{
		startProgramName = partOne();
		System.out.println("The start program is: " + startProgramName);
		partTwo(); // Call the partTwo method
	}
	
	private String partOne() // method for part one of the challenge
	{
		BufferedReader puzzleInput = null; // Declare a new buffered reader
		String startProgramFound = ""; // Initalise a variable to store the start program
		try 
		{
			puzzleInput = new BufferedReader(new FileReader(FILE_NAME)); // Try to open the file
		}
		catch (Exception e) 
		{
			System.out.println("Something went wrong opening the file!"); // Tell the user that something went wrong
		}
		
		// The file should be open now
		String currentLine = null; // Initalise the currentLine variable
		try
		{
			currentLine = puzzleInput.readLine(); // Read the next line
		}
		catch (Exception e)
		{
			System.out.println("Something went wrong reading the file!");
		}
		
		String[] programNamesAllButBottom = new String[10000]; // Create an array to store the program names this will store all but the very bottom
		String[] programNames = new String[10000]; // To store all of the program names
		
		int currentIndexAllButBottom = 0; // Store the current index in programNamesAllButBottom in order to not overwrite names/ to store names in the correct location
		int currentIndex = 0; // Store the current index in programNames
		while (currentLine != null) // While thfere are lines to read
		{
			// Check if the currentLine contains "->" 
			// If it does then we can discard the element as it's obviously on the top layer
			if (currentLine.contains("->") == true) // If the line contains "->"
			{
				// Line looks like fwft (72) -> ktlj, cntj, xhth
				// We want                      /---------------\ <- this section
				String[] splitLine = currentLine.split("-> "); // Spilt the line at "-> "
				// "-> " is nessesary to get rid of the trailing space
				
				String[] names = splitLine[1].split(", "); // Split at splitLine[1] (Everything after the -> ) 
				// at ", " to get the individual program names
				for (int i = 0; i<names.length; i++) // Iterate over each name
				{
					//System.out.println("Added a name!");
					programNamesAllButBottom[currentIndexAllButBottom] = names[i]; // Store the name in the array of program names
					currentIndexAllButBottom++;
				}
				
				
				// SplitLine looks like ["fwft (72)", " -> ktlj, cntj, xhth"]
				// We want                /       \ <-- We want this
				// And from this we want   /\ so we need to split at space and take the first element
				
				programNames[currentIndex] = splitLine[0].split(" ")[0]; // Store the program name at the start of the line into the array
			}
			else // "->" is not in the line
			{
				programNames[currentIndex] = currentLine.split(" ")[0]; // Store the program name in the array
			}
			
			try
			{
				currentLine = puzzleInput.readLine(); // Read the next line of the text file
			}
			catch (Exception e)
			{
				System.out.println("Something went wrong reading the file!");
			}
			currentIndex++; // Increment the currentIndex
		}
		try 
		{
			puzzleInput.close(); // Close the file.
		}
		catch (Exception e)
		{
			System.out.println("Something went wrong closing the file");
		}
		// The array "programNamesAllButBottom" should contain all program but the very bottom one
		// We need to iterate over it and find which one is missing
		// The missing one is the program at the bottom
		//System.out.println(Arrays.toString(programNames));
		//System.out.println(Arrays.toString(programNamesAllButBottom));
		// Iterate over both arrays to see what's missing
		
		for (int i = 0; i < programNames.length; i++) // Iterate over the program names array
		{
			if (programNames[i] != null) // If there is an actual value
			{
				boolean present = false; // Store if the program is in the all but bottom array
				for (int j = 0; j < programNamesAllButBottom.length; j++) // Iterate over the programNamesAllButBottom array
				{
					if (programNames[i].equals(programNamesAllButBottom[j])) // If the item from programNames is in programNamesAllButBottom
					{
						present = true; // Set present to true as it's present
					}
				}
				
				if (present == false) // If we've found the item not present in programNamesAllButBottom
				{
					startProgramFound = programNames[i]; // Print the value to the console
					break; // We don't need to search for more values
				}
			}
			else
			{
				break; // No more values so break
			}
		}
		
		return startProgramFound; // Return startProgramFound
	}
	
	private void partTwo() // method for part two of the challenge
	{
		BufferedReader puzzleInput = null; // Declare a new buffered reader
		
		try 
		{
			puzzleInput = new BufferedReader(new FileReader(FILE_NAME)); // Try to open the file
		}
		catch (Exception e) 
		{
			System.out.println("Something went wrong opening the file!"); // Tell the user that something went wrong
		}
		
		// The file should be open now
		String currentLine = ""; // Initalise the currentLine variable
		
		programNamesLinked = new HashMap<String, String[]>(); // Create a hashmap to store the names and links
		programWeights = new HashMap<String, Integer>(); // Create a hashmap to store the program weights
		
		int currentIndex = 0; // Store the current index in programNames
		while (currentLine != null) // While thfere are lines to read
		{
			String programName = ""; // Initalise program name
			String programWeight = ""; // Initalise program weight
			try
			{
				currentLine = puzzleInput.readLine(); // Read the next line of the text file
			}
			catch (Exception e)
			{
				System.out.println("Something went wrong reading the file!");
			}
			
			if (currentLine == null) // If the line is empty
			{
				break;
			}
			// Check if the currentLine contains "->" 
			// If it does then we can discard the element as it's obviously on the top layer
			if (currentLine.contains("->") == true) // If the line contains "->"
			{
				// Line looks like fwft (72) -> ktlj, cntj, xhth
				// We want                      /---------------\ <- this section
				String[] splitLine = currentLine.split("-> "); // Spilt the line at "-> "
				// "-> " is nessesary to get rid of the trailing space
				
				String[] names = splitLine[1].split(", "); // Split at splitLine[1] (Everything after the -> ) 
				// at ", " to get the individual program names

				// SplitLine looks like ["fwft (72)", " -> ktlj, cntj, xhth"]
				// We want                /       \ <-- We want this
				// From this we want      /\ so we need to split at space and take the first element
				//System.out.println(splitLine[0] + " " + Arrays.toString(names));
				String[] programNameAndValue = splitLine[0].split(" "); // Split the first bit of splitLine at " " 
				programName = programNameAndValue[0];
				programWeight = programNameAndValue[1];
				programNamesLinked.put(programName, Arrays.copyOf(names, names.length)); // Store the program name at the start of the line into the hashmap and link it to the program names after it
			}
			else // "->" is not in the line
			{
				String[] programNameAndValue = currentLine.split(" "); // Split the line at " " 
				programName = programNameAndValue[0];
				programWeight = programNameAndValue[1];
				programNamesLinked.put(programName, null); // Store the program name in the hashmap with a null value linked to it
			}
			programWeight = programWeight.replace('(', ' ').replace(')', ' ').trim();// Get rid of the brackets
			programWeights.put(programName, Integer.parseInt(programWeight)); // Store the program name and weight in the dictionary
			currentIndex++; // Increment the currentIndex
		}
		try 
		{
			puzzleInput.close(); // Close the file.
		}
		catch (Exception e)
		{
			System.out.println("Something went wrong closing the file");
		}
		System.out.println("Finished reading the file");
		
		// We now have 2 hashmaps
		// One has all the program names and what they link to
		// The other has all the weights of the programs
		// We need a recursive function now to figure out the total.
		
		// Old code for printing the hashmaps
		/*for (Map.Entry<String, String[]> entry : programNamesLinked.entrySet())  // Print out the hashmap
		{
			//System.out.println(entry.getKey() + " " + Arrays.toString(entry.getValue()));
			//System.out.println(programWeights.get(entry.getKey()));
		}
		*/ 
		findDodgyTower(startProgramName); // Call the find dodgy tower method
	}
	
	private Integer getTotalWeight(String startValue)
	{
		// Recursive function to calculate weight of programs
		int totalWeight = 0; // Initalise total weight variable
		totalWeight = programWeights.get(startValue); // Get the programs weight and add it to the total
		if (programNamesLinked.get(startValue) != null) // If it's not a top layer element
		{
			for (String programName : programNamesLinked.get(startValue)) // For each program on top of the current one
			{
				totalWeight += getTotalWeight(programName); // Get their total weight and add it to the total
			}
		}
		
		return totalWeight;
	}
	
	private String findDodgyTower(String programToStartAt) // To find the dodgy tower
	{
		HashMap<Integer, String> stackWeights = new HashMap<Integer, String>(); // To store the tower weights and the program they belong to 
		Integer[] stackWeightsValuesOnly = new Integer[100]; // To store the stack weights by index as they'll overwrite each other in the hashmap
		int stackWeightsValuesOnlyIndex = 0; // Store the current index
		
		for (String program : programNamesLinked.get(programToStartAt)) // For each tower on top of the startProgram
		{
			Integer weight = getTotalWeight(program);
			System.out.println(weight); // Get and print their weight
			stackWeights.put(weight, program); // Store the program name in the stackWeights hashMap under the weight
			stackWeightsValuesOnly[stackWeightsValuesOnlyIndex] = weight; // Store in the stack weights values only array
			stackWeightsValuesOnlyIndex++; // Increment the index
		}
		
		String oddOneOut = null; // Initalise the odd one out variable
		// We need to find the odd one out
		stackWeightsValuesOnly = Arrays.copyOf(stackWeightsValuesOnly, stackWeightsValuesOnlyIndex); // Create a copy of the array to avoid pass by reference 
		for (Integer weight : stackWeightsValuesOnly) // Iterate over each weight
		{
			int numberOfNonOccurances = 0;
			System.out.println("Trying weight " + weight);
			for (Integer weightToCompareWith : stackWeightsValuesOnly) // Iterate over each weight again
			{
				if (weight.equals(weightToCompareWith) == false) // If it's not a match
				{
					System.out.println(weightToCompareWith + " != " + weight);
					numberOfNonOccurances++; // Increment number of non occurances
					System.out.println("Number of non occurances is " + numberOfNonOccurances);
				}
				
			}
			
			if (numberOfNonOccurances > 1 ) // If it's not there more than once then it's the odd one out
			{
				oddOneOut = stackWeights.get(weight); // Store it as the odd one out
				System.out.println("The odd one out was: " + oddOneOut + " , " + weight);
				oddOnesOut.put(oddOneOut, Arrays.copyOf(stackWeightsValuesOnly, stackWeightsValuesOnly.length)); // Store the startProgram and it's values
				
				break;
			}
		}
		
		
		if (oddOneOut == null) // If there was no odd one out
		{
			System.out.println("The dodgy program was " + programToStartAt);
			System.out.println(Arrays.toString(oddOnesOut.get(programToStartAt))); // Get the list of values it's part of e.g. the values of the other programs at it's level
			System.out.println(programWeights.get(programToStartAt)); // Print it's weight
			return ""; // Return null to stop the recursion
		}
		
		return findDodgyTower(oddOneOut); // Find the next dodgy tower
		
	}
}