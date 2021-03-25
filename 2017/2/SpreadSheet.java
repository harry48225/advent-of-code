import java.io.*;

public class SpreadSheet 
{
	private final String FILE_NAME = "puzzleInput.txt"; // The file name
	
	public static void main(String[] args) 
	{
		SpreadSheet mySpreadSheet = new SpreadSheet(); // Create a new instance of the class
		mySpreadSheet.startProgram(); // Call the start program method
	}
	
	public void startProgram() 
	{
		partTwo(); // Run part two
	}
	
	private String[] loadSpreadSheet(String fileName) // Read the spreadsheet from a file
	{
		String[] tempSpreadSheet = new String[50]; // String array to store the rows of the spreadsheet
		
		try // To read the file
		{	
			BufferedReader puzzleInputReader = new BufferedReader(new FileReader(fileName)); // Open the file
			String currentLine;
			int lineNumber = 0; // Track the line number
			boolean linesToRead = true; // Store whether there are lines to read in the file
			
			while (linesToRead == true) // While there are lines to be read 
			{	
				currentLine = puzzleInputReader.readLine(); // Read the next line of the file 
				System.out.println(currentLine); // Store the line in a tempString
				if (currentLine == null) // If the line returns as null
				{
					System.out.println("File reading finished"); // Tell the user that file reading has finished
					linesToRead = false; // linesToRead to false as to break from the loop
					puzzleInputReader.close(); // Close the file
				}
				tempSpreadSheet[lineNumber] = currentLine; // Store the line read in the array at the index corressponding to the line number
				lineNumber++; // Increment the line number
			}
		}
		catch (Exception e)
		{
			System.out.println("Something went wrong reading the file");
			e.printStackTrace(); // Print the stack trace
		}
		
		return tempSpreadSheet; // Return the read spreadsheet
	}

	private void partOne() // Part one of the challenge
	{
		String[] spreadSheet = loadSpreadSheet(FILE_NAME); // Load the spread sheet
		
		boolean performingChecksum = true; // Store whether the program is still checking the spreadsheet
		
		int rowNumber = 0; // Store the current row number
		int checksum = 0; // Store the checksum
		while (performingChecksum == true)
		{
			String currentRow = spreadSheet[rowNumber]; // Store the current row in currentRow
			if (currentRow != null) // If the current row isn't null
			{
				String[] rowContent = currentRow.split("	"); // Split the row at "\tab\"
				
				int highestNumber = -1;
				int lowestNumber = -1;
					
				for (int i = 0; i<rowContent.length; i++) // Iterate over the numbers in the row
				{
					int currentNumber = Integer.parseInt(rowContent[i]); // Get the int from the number 
					if (highestNumber == -1 && lowestNumber == -1) // If the highest and lowest number haven't been set for the first time yet
					{
						highestNumber = currentNumber; // Set the highest number to be equal to the current number
						lowestNumber = currentNumber; // Set the lowest number to be equal to the current number
					}
					else // If the numbers have been set
					{
						if (currentNumber > highestNumber) // If the current number is greater than the highest number
						{
							highestNumber = currentNumber; // Set the highest number to be equal to the current number
						} 
						else if (currentNumber < lowestNumber) // If the current number is lower than the lowest number
						{
							lowestNumber = currentNumber; // Set the lowest number to be equal to the current number
						}
					}
					
				}
				
				int range = highestNumber - lowestNumber; // Calculate the range of the row
				checksum += range; // Add the range to the checksum
				rowNumber++; // Increment the row number
			} 
			else // The current row must be null
			{
				performingChecksum = false; // We have finished calculating the check sum. This will break from the loop
			}
		}
		
		System.out.println("The checksum is: " + checksum); // Output the checksum
	}
	
	private void partTwo() // Part two of the challenge
	{
		String[] spreadSheet = loadSpreadSheet(FILE_NAME); // Load the spread sheet
		
		boolean performingChecksum = true; // Store whether the program is still checking the spreadsheet
		
		int rowNumber = 0; // Store the current row number
		int checksum = 0; // Store the checksum
		while (performingChecksum == true)
		{
			String currentRow = spreadSheet[rowNumber]; // Store the current row in currentRow
			if (currentRow != null) // If the current row isn't null
			{
				String[] rowContent = currentRow.split("	"); // Split the row at "\tab\"
				boolean evenDivisionFound = false; // Store whether the even division has been found
				for (int i = 0; i<rowContent.length; i++) // Iterate over the number in the row
				{
					int currentNumber = Integer.parseInt(rowContent[i]); // Get the int from the number 
					for (int j = 0; j<rowContent.length; j++) // Iterate over all of the numbers again
					{
						int divisor = Integer.parseInt(rowContent[j]); // Get the divisor
						
						if ((currentNumber % divisor == 0) && (currentNumber != divisor)) // If it divides evenly and it's not the same number i.e. not itself
						{
							checksum += currentNumber/divisor; // Add the result of the division to the checksum
							evenDivisionFound = true;
							break; // We don't need to check anymore numbers
						}
					}
					
					if (evenDivisionFound == true) // If we have found the even division
					{
						break; // Break from the loop
					}
					
				}
				
				rowNumber++; // Increment the row number
			} 
			else // The current row must be null
			{
				performingChecksum = false; // We have finished calculating the check sum. This will break from the loop
			}
		}
		
		System.out.println("The checksum is: " + checksum); // Output the checksum
	}
}
