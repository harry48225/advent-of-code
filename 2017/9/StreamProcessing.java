import java.io.*;
import java.util.*;

public class StreamProcessing
{
	private final String FILE_NAME = "puzzleInput.txt"; // what the puzzle input is called
	
	private ArrayList<Character> charactersInPuzzleInput = new ArrayList<Character>(); // Initalise the charactersInPuzzleInput arraylist
	private Iterator charactersInPuzzleInputIterator = null; // Initalise the charactersInPuzzleInputIterator Iterator
	
	private int totalNumberOfGarbageCharacters = 0; // Store the total number of garbage characters 
	
	public static void main(String[] args)
	{
		StreamProcessing sP = new StreamProcessing(); // Create a new instance of the class
		sP.startProgram(); // Call the start program method
	}
	
	private void startProgram() // startProgram method
	{
		parts(); // Call the parts
	}
	
	private void parts() // For both parts of the challenge
	{
		
		readFile(); // read puzzleInput
		charactersInPuzzleInputIterator = charactersInPuzzleInput.iterator(); // Get the iterator of the arrayList
		
		charactersInPuzzleInputIterator.next(); // This will be a {
		// So call readGroup() 
		// and the program is away
		System.out.println(readGroup(1)); // This should print the values of the group. the 1 tells it that the level it's on is 1
		System.out.println("There were " + totalNumberOfGarbageCharacters +  " garbage characters!");
	}	
	
	private int readGroup(int level) // Reads the groups from the ArrayList
	{
		int levelTotal = level; // Store the total
		while (true) // Do this until we return
		{
			Character currentCharacter = (Character) charactersInPuzzleInputIterator.next(); // Get the next character from charactersInPuzzleInputIterator
			
			if (currentCharacter.equals('<')) // If we've encountered the start of garbage
			{
				readGarbage(); // Read the garbage
			}
			else if (currentCharacter.equals('{')) // If we've encountered the start of another group
			{
				levelTotal += readGroup(level+1); // Read the group and add it to the total. We pass it level+1 as it's one level below us
			}
			else if (currentCharacter.equals('}')) // If we've reached the end of the group
			{
				return levelTotal; // Return levelTotal
			}
		}
	}
	
	private void readGarbage() // Reads the garbage
	{
		int numberOfCharacters = 0; // Store the number of characters in the garbage
		// This should not include < or > or ! or any canceled characters
		
		while (true) // Do this until we return
		{
			Character currentCharacter = (Character) charactersInPuzzleInputIterator.next(); // Get the next character
			
			if (currentCharacter.equals('!')) // If we've encountered a	!
			{
				charactersInPuzzleInputIterator.next(); // Discard the next character
			}
			else if (currentCharacter.equals('>')) // If we've reached the end of the garbage
			{
				totalNumberOfGarbageCharacters += numberOfCharacters; // Add the number of characters in this garbage to the total
				return; // Return 
			}
			else // We must have a regular character
			{
				numberOfCharacters++; // Increment number of characters
			}
		}
	}
	
	private void readFile() // reads the text file and stores the characters in an ArrayList
	{
		FileReader puzzleInputStream = null; // Initalise the puzzleInputStream variable
		try
		{
			puzzleInputStream = new FileReader(FILE_NAME); // Open the file
		}
		catch (Exception e)
		{
			e.printStackTrace(); // Print what went wrong
		}
		int currentCharValue = 1; // Initalise the currentCharValue variable
		char currentChar = '-'; // Initalise the currentChar variable
		
		while (currentCharValue != -1) // While there are still more characters to be read
		{
			try
			{
				currentCharValue = puzzleInputStream.read(); // Read the next character
				currentChar = (char) currentCharValue; // Get the character that the currentCharValue represents
				//System.out.println(currentChar);
			}
			catch (Exception e)
			{
				System.out.println("Something went wrong reading the file"); // Tell the user that something went wrong
				e.printStackTrace(); // Print what went wrong
			}
			
			if (currentCharValue!=-1) // If we've not reached the end of the file
			{
				charactersInPuzzleInput.add(currentChar); // Add the current character to the charactersInPuzzleInput arraylist
			}
		}
		System.out.println("file reading finished!");
	}

}