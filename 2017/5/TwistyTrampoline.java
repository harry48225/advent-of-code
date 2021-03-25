import java.io.*;
import java.util.Arrays;

public class TwistyTrampoline
{
	private final String FILE_NAME = "puzzleInput.txt"; // The name of the puzzle input file 
	
	public static void main(String[] args)
	{
		TwistyTrampoline tT = new TwistyTrampoline(); // Create a new instance of the class
		tT.startProgram(); // Call the start program method
	}
	
	private void startProgram() // Start program method
	{
		partTwo(); // Call the start program method.
	}
	
	private void partOne() // For part one of the challenge
	{
		BufferedReader puzzleInput = null; // Initalise the puzzleInput variable
		try 
		{
			puzzleInput = new BufferedReader(new FileReader(FILE_NAME)); // Create a new buffered reader and pass it the puzzleInput text file
		} 
		catch (Exception e) 
		{
			System.out.println("There was a problem reading the file. "); // Tell the user that there was a problem
		}
		
		int[] maze = new int[10000]; // Maze large enough to store the readings from the file. 
		boolean linesToRead = true; // Toggle to tell if there are lines left in the file to read
		
		String currentLine = null; // Initalise the current line variable
		int currentLineNumber = 0; // Store the number of the current line
		int totalNumberOfLines = 0; // Store the total number of lines
		
		while (linesToRead == true) // While there are lines to read
		{
			try
			{
				currentLine = puzzleInput.readLine(); // Read the next line of the file
			}
			catch (Exception e)
			{
				System.out.println("There was an error"); // Tell the user that there was an error
			}
			
			
			if (currentLine != null) // If the current line isn't empty
			{	
				
				maze[currentLineNumber] = Integer.parseInt(currentLine); // Store the line in the array
				currentLineNumber++; // Increment the current line number
				totalNumberOfLines++; // Increment the total number of lines
			}
			else // The end of the file must have been reached
			{
				linesToRead = false; // Stop reading the file
			}
		}
		// File reading will have finished at this point.
		
		System.out.println("File reading finished!"); // Tell the user that file reading has finished 
		
		int currentPosition = 0; // Store the current position in the maze
		int numberOfStepsTaken = 0; // Track the number of steps taken
		
		while (currentPosition < totalNumberOfLines) // While we are still in the maze
		{
			//System.out.println(Arrays.toString(maze));
			int oldPosition = currentPosition; // Set old position to current position
			currentPosition = currentPosition + maze[currentPosition]; // Make the move
			maze[oldPosition] += 1; // Increment the old position by 1
			numberOfStepsTaken++; // Increment the number of steps taken
		}
		
		System.out.println("Maze escaped. That took " + numberOfStepsTaken + " steps!"); // Tell the user how many steps it took
	}
	
	private void partTwo() // For part one of the challenge
	{
		BufferedReader puzzleInput = null; // Initalise the puzzleInput variable
		try 
		{
			puzzleInput = new BufferedReader(new FileReader(FILE_NAME)); // Create a new buffered reader and pass it the puzzleInput text file
		} 
		catch (Exception e) 
		{
			System.out.println("There was a problem reading the file. "); // Tell the user that there was a problem
		}
		
		int[] maze = new int[10000]; // Maze large enough to store the readings from the file. 
		boolean linesToRead = true; // Toggle to tell if there are lines left in the file to read
		
		String currentLine = null; // Initalise the current line variable
		int currentLineNumber = 0; // Store the number of the current line
		int totalNumberOfLines = 0; // Store the total number of lines
		
		while (linesToRead == true) // While there are lines to read
		{
			try
			{
				currentLine = puzzleInput.readLine(); // Read the next line of the file
			}
			catch (Exception e)
			{
				System.out.println("There was an error"); // Tell the user that there was an error
			}
			
			
			if (currentLine != null) // If the current line isn't empty
			{	
				
				maze[currentLineNumber] = Integer.parseInt(currentLine); // Store the line in the array
				currentLineNumber++; // Increment the current line number
				totalNumberOfLines++; // Increment the total number of lines
			}
			else // The end of the file must have been reached
			{
				linesToRead = false; // Stop reading the file
			}
		}
		// File reading will have finished at this point.
		
		System.out.println("File reading finished!"); // Tell the user that file reading has finished 
		
		int currentPosition = 0; // Store the current position in the maze
		int numberOfStepsTaken = 0; // Track the number of steps taken
		
		while (currentPosition < totalNumberOfLines) // While we are still in the maze
		{
			//System.out.println(Arrays.toString(maze));
			int oldPosition = currentPosition; // Set old position to current position
			currentPosition = currentPosition + maze[currentPosition]; // Make the move
			if (maze[oldPosition] >= 3) // If the value was 3 or more
			{
				maze[oldPosition] -= 1; // Decrease the old position by 1
			} 
			else // Otherwise
			{
				maze[oldPosition] += 1; // Increase the old position by 1
			}
			numberOfStepsTaken++; // Increment the number of steps taken
		}
		
		System.out.println("Maze escaped. That took " + numberOfStepsTaken + " steps!"); // Tell the user how many steps it took
	}
}