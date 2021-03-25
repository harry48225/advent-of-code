import java.io.*;
import java.util.*;

public class PermutationPromenade
{
	private final String FILE_NAME = "puzzleInput.txt"; // puzzleInput file
	
	public static void main(String[] args)
	{
		PermutationPromenade pP = new PermutationPromenade(); // Create a new instance of the class
		pP.partTwo(); // Call the part two method
	}
	
	private void partOne() // For part one of the challenge
	{
		BufferedReader puzzleInput = null; // Initalise a bufferedReader to read the file
		
		try
		{
			puzzleInput = new BufferedReader(new FileReader(FILE_NAME)); // Open the file
		}
		catch (Exception e)
		{
			e.printStackTrace(); // Print what went wrong
		}
		
		String line = ""; // Initalise a variable to store the read line
		
		try
		{
			line = puzzleInput.readLine(); // Read from the file
		}
		catch (Exception e)
		{
			e.printStackTrace(); // Print what went wrong
		}
		String[] danceMoves = line.split(","); // Split the line to get each dance move
		
		// We now have the dance moves
		
		// Generate the line of programs
		
		char[] programLine = generateProgramLine(16); // Generate a line of programs
		
		System.out.println(Arrays.toString(programLine));
		
		// We now need to perform the dance moves
		// There are three dance moves
		// Spin: sX
		// Exchanage: xA/B
		// Partner: pA/B
		
		for (String danceMove : danceMoves) // For each dance move in danceMoves
		{
			//System.out.println(danceMove);
			
			char moveIdentifier = danceMove.charAt(0); // Get the move identifier
			
			danceMove = danceMove.substring(1); // Cut off the first part of the dance move, the identifier
			// substring(1) return a new string that's the string but only starting from index 1
			
			if (moveIdentifier == 's') // If it's a spin
			{
				int spinSize = Integer.parseInt(danceMove); // Get the spin size
				
				programLine = spin(spinSize, programLine); // Perform the spin
			}
			
			else if (moveIdentifier == 'x') // If it's an exchange
			{
				String[] programIndexes = danceMove.split("/"); // Get the program indexes by splitting at /
				
				int programOneIndex = Integer.parseInt(programIndexes[0]); // Get the int value of the first programs index string
				int programTwoIndex = Integer.parseInt(programIndexes[1]); // Get the int value of the second programs index string
				
				programLine = exchange(programOneIndex, programTwoIndex, programLine); // Perform the exchange
				
			}
			
			else if (moveIdentifier == 'p') // If it's a partner
			
			{
				String[] programNames = danceMove.split("/"); // Split at "/" to get the program names
				
				char programOne = programNames[0].charAt(0); // Get the first programs name
				char programTwo = programNames[1].charAt(0); // Get the second programs name
				
				programLine = partner(programOne, programTwo, programLine); // Perform the partner
			}
		}
		
		System.out.println(Arrays.toString(programLine));
	}
	
	private void partTwo() // For part two of the challenge
	{
		BufferedReader puzzleInput = null; // Initalise a bufferedReader to read the file
		
		try
		{
			puzzleInput = new BufferedReader(new FileReader(FILE_NAME)); // Open the file
		}
		catch (Exception e)
		{
			e.printStackTrace(); // Print what went wrong
		}
		
		String line = ""; // Initalise a variable to store the read line
		
		try
		{
			line = puzzleInput.readLine(); // Read from the file
		}
		catch (Exception e)
		{
			e.printStackTrace(); // Print what went wrong
		}
		String[] danceMoves = line.split(","); // Split the line to get each dance move
		
		// We now have the dance moves
		
		// Generate the line of programs
		
		char[] programLine = generateProgramLine(16); // Generate a line of programs
		
		System.out.println(Arrays.toString(programLine));
		
		// We now need to perform the dance moves
		// There are three dance moves
		// Spin: sX
		// Exchanage: xA/B
		// Partner: pA/B
		
		int dancesLeft = 1000000000; // A billion dances left to do
		
		boolean seenBefore = false; // While we've not seen a dance before
		ArrayList<char[]> previousProgramLines = new ArrayList<char[]>(); // Create an array list to store the program lines that we have seen before
		
		char[] arraySeenBefore = null; // Store the array that has been seen before
		
		while (seenBefore == false) // While we've not encountered a programline twice
		{
			programLine = dance(danceMoves, programLine);
			
			dancesLeft--; // Decrement the amount of dances left
			
			// We need to check if we've seen the array before
			for (char[] previousProgramLine : previousProgramLines) // For each previous program line
			{
				if (Arrays.equals(previousProgramLine, programLine)) // If we've seen it before
				{
					seenBefore = true; // Set seen before to true
					arraySeenBefore = Arrays.copyOf(programLine, programLine.length); // Store program line as the array that was seen before
				}
			}
			previousProgramLines.add(Arrays.copyOf(programLine, programLine.length)); // Add the program line to the arraylist
		}
		System.out.println("Array seen before " + dancesLeft + " dances left!");
		System.out.println(Arrays.toString(programLine));
		
		// Now we need to find the loop size
		
		boolean loopFound = false; // Store whether the loop size has been found
		int loopSize = 0; // Store the loop size
		
		while (loopFound == false) // While we've not found the loop
		{
			loopSize++; // Increment the loop size
			dancesLeft--; // Decrement the number of dances left
			programLine = dance(danceMoves, programLine);
			
			if (Arrays.equals(arraySeenBefore, programLine)) // If we're back to where we started
			{
				loopFound = true; // We've found the loop
			}
		}
		
		System.out.println("The loop size was found it was " + loopSize);
		
		// Find out how many more times we need to dance after performing as many loops as possible
		
		dancesLeft = dancesLeft % loopSize;
		
		System.out.println("There are " + dancesLeft + " dances left");
		
		// Perform the last dances
		
		for (int i = 0; i<dancesLeft; i++) // For each dance left
		{
			programLine = dance(danceMoves, programLine); // Dance
		}
		
		System.out.println("The final order was: " + Arrays.toString(programLine)); // Print the final order
	}
	private char[] generateProgramLine(int numberOfPrograms) // To generate a line of programs with ascending letters
	{
		char startProgram = 'a'; // Start at a
		
		char[] tempProgramLine = new char[numberOfPrograms]; // Create a new char[] to store the programs
		
		char currentProgram = startProgram; // Set the current program to the start program
		
		for (int i = 0; i<numberOfPrograms; i++) // Iterate the required number of times
		{
			tempProgramLine[i] = currentProgram; // Store the char in the array
			currentProgram += 1; // Increment the current char
		}
		
		return tempProgramLine; // Return the array
	}
	
	private char[] spin(int spinSize, char[] programLine) // Perform a spin on a program line
	{
		char[] newProgramLine = new char[programLine.length]; // Create a new array to store the new arrangement of programs
		
		int programLineIndex = (programLine.length-1) - spinSize; // Start copying from this index
		
		for (int i = programLine.length-1; i>=0; i--) // Do this for each element counting backwards
		{
			newProgramLine[i] = programLine[programLineIndex]; // Copy the element
			
			programLineIndex--; // Decrement program line index
			
			if (programLineIndex < 0) // If we need to wrap around
			{
				programLineIndex += programLine.length; // Wrap around
			}
		}
		
		return newProgramLine; // Return the new programLine
	}
	
	
	private char[] exchange(int programOneIndex, int programTwoIndex, char[] programLine) // Perform an exchange on the program line
	{
		char programOne = programLine[programOneIndex]; // Get the program at the first index
		char programTwo = programLine[programTwoIndex]; // Get the program at the second index
		
		// Swap the programs
		programLine[programOneIndex] = programTwo;
		programLine[programTwoIndex] = programOne;
		
		return programLine; // Return the array after the swap
	}
	
	private char[] partner(char programOne, char programTwo, char[] programLine) // Perform a partner on the program line
	{
		// We need to find their indexes
		
		// Declare variables to store their indexes
		
		int programOneIndex = -1;
		int programTwoIndex = -1;
		for (int i = 0; i<programLine.length; i++) // Iterate over the array
		{
			if (programLine[i] == programOne) // If we've found program one
			{
				programOneIndex = i; // We've found the index
			}
			else if (programLine[i] == programTwo) // If we've found program two
			{
				programTwoIndex = i; // We've found the index
			}
		}
		
		// We should now have the indexes
		// We can just perform an exchange now
		
		return exchange(programOneIndex, programTwoIndex, programLine); // Perform the exchange and return it
	}
	
	private char[] dance(String[] danceMoves, char[] programLine) // Method to do the dance
	{
		for (String danceMove : danceMoves) // For each dance move in danceMoves
		{
			//System.out.println(danceMove);
			
			char moveIdentifier = danceMove.charAt(0); // Get the move identifier
			
			danceMove = danceMove.substring(1); // Cut off the first part of the dance move, the identifier
			// substring(1) return a new string that's the string but only starting from index 1
			
			if (moveIdentifier == 's') // If it's a spin
			{
				int spinSize = Integer.parseInt(danceMove); // Get the spin size
				
				programLine = spin(spinSize, programLine); // Perform the spin
			}
			
			else if (moveIdentifier == 'x') // If it's an exchange
			{
				String[] programIndexes = danceMove.split("/"); // Get the program indexes by splitting at /
				
				int programOneIndex = Integer.parseInt(programIndexes[0]); // Get the int value of the first programs index string
				int programTwoIndex = Integer.parseInt(programIndexes[1]); // Get the int value of the second programs index string
				
				programLine = exchange(programOneIndex, programTwoIndex, programLine); // Perform the exchange
				
			}
			
			else if (moveIdentifier == 'p') // If it's a partner
			
			{
				String[] programNames = danceMove.split("/"); // Split at "/" to get the program names
				
				char programOne = programNames[0].charAt(0); // Get the first programs name
				char programTwo = programNames[1].charAt(0); // Get the second programs name
				
				programLine = partner(programOne, programTwo, programLine); // Perform the partner
			}
		}
		
		return programLine;
	}
}