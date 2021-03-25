import java.io.*;

public class PassPhrase 
{
	private final String FILE_NAME = "puzzleInput.txt"; // The file name
	
	public static void main(String[] args) 
	{
		PassPhrase pP = new PassPhrase(); // Create a new instance of the class
		pP.startProgram(); // Call the startProgram method
	}
	
	public void startProgram() // startProgram method
	{
		partTwo(); // Call the part two method
	}
	
	private void partOne() // For part one of the challenge
	{
		BufferedReader puzzleInputReader = null; // Declare the puzzle input reader
		try 
		{
			puzzleInputReader = new BufferedReader(new FileReader(FILE_NAME)); // Create a new buffered reader
		}
		catch (FileNotFoundException e) // Catch the FileNotFoundException
		{
			System.out.println("File not found!"); // Tell the user that the file couldn't be found
		}
		boolean linesToRead = true; // Store whether there are lines to read
		
		int invalidPhrases = 0; // Store how many phrases are invalid
		int totalPhrases = 0;  // Store the total number of phrases
		while (linesToRead == true)  // While there are lines to read
		{
			String line = null; // Declare a string to store the current line
			
			try 
			{
				line = puzzleInputReader.readLine(); // Read the next line of the file
			}
			catch (Exception e) // Something went wrong reading the file
			{
				System.out.println("Something went wrong reading the file."); // Tell the user that something went wrong
			}
			
			if (line == null) // If the line is empty
			{
				System.out.println("File reading finished!"); // Tell the user that file reading has finished
				linesToRead = false; // Set linesToRead to false as there are no more lines to read.
			}
			
			else // There must be content in the line
			{
				String[] passPhraseWords = line.split(" "); // Split the lines at "\space\" to get the words
				boolean passPhraseIsInvalid = false; // Store whether the passphrase is invalid
				for (int i = 0; i < passPhraseWords.length; i++) // Iterate over each word
				{
					for (int j = 0; j < passPhraseWords.length; j++) // Iterate over each word again
					{
						if (i!=j) // If we are not checking a word with itself
						{
							if (passPhraseWords[i].equals(passPhraseWords[j])) // If the words are the same
							{
								passPhraseIsInvalid = true; // Set passPhraseIsInvalid to true as the pass phrase is invalid
							}
						}
					}
					
				}
				
				if (passPhraseIsInvalid == true) // If the phrase has already been found to be invalid
				{
					System.out.println(line + "\n is not valid!"); // Tell the user that the passphrase is not valid
					invalidPhrases++; // Increment the number of invalid phrases
				}
				totalPhrases++; // Increment the amount of phrases
			}
			
				
		}
		
		System.out.println("Out of a total " + totalPhrases + " phrases, " + invalidPhrases + " were invalid, and " + (totalPhrases-invalidPhrases) + " were valid!"); // Tell the user how many were invalid
	}
	
	private void partTwo() // Part two of the challenge
	{
		
		BufferedReader puzzleInputReader = null; // Declore the puzzle input reader
		try 
		{
			puzzleInputReader = new BufferedReader(new FileReader(FILE_NAME)); // Create a new buffered reader
		}
		catch (FileNotFoundException e) // Catch the FileNotFoundException
		{
			System.out.println("File not found!"); // Tell the user that the file couldn't be found
		}
		boolean linesToRead = true; // Store whether there are lines to read
		
		int invalidPhrases = 0; // Store how many phrases are invalid
		int totalPhrases = 0;  // Store the total number of phrases
		while (linesToRead == true)  // While there are lines to read
		{
			String line = null; // Declare a string to store the current line
			
			try 
			{
				line = puzzleInputReader.readLine(); // Read the next line of the file
			}
			catch (Exception e) // Something went wrong reading the file
			{
				System.out.println("Something went wrong reading the file."); // Tell the user that something went wrong
			}
			
			if (line == null) // If the line is empty
			{
				System.out.println("File reading finished!"); // Tell the user that file reading has finished
				linesToRead = false; // Set linesToRead to false as there are no more lines to read.
			}
			
			else // There must be content in the line
			{
				String[] passPhraseWords = line.split(" "); // Split the lines at "\space\" to get the words
				boolean passPhraseIsInvalid = false; // Store whether the passphrase is invalid
				for (int i = 0; i < passPhraseWords.length; i++) // Iterate over each word
				{
					char[] lettersOfCurrentWord = passPhraseWords[i].toCharArray(); // Covert the word into letter in a char[]
					
					for (int j = 0; j < passPhraseWords.length; j++) // Iterate over each word again
					{
						if (i!=j) // If we are not checking a word with itself
						{
							char[] lettersOfWordToCheck = passPhraseWords[j].toCharArray(); // Covert the word to check into letters in a char[]
							// Check each letter in each word against each other.
							
							int lettersTheSame = 0; // Keep track of the number of letters that are the same
							
							if (lettersOfCurrentWord.length == lettersOfWordToCheck.length) // If they have the same amount of letters
							{	// Check to see if they have all the same letters
								for (int k = 0; k < lettersOfCurrentWord.length; k++) // Iterate over the letters of the current word
								{
									for (int l = 0; l < lettersOfWordToCheck.length; l++) // Iterate over the letters of the word to check
									{
										if (lettersOfCurrentWord[k] == lettersOfWordToCheck[l]) // If the letters are the same
										{
											lettersTheSame++; // Increment the amount of letters the same
											lettersOfWordToCheck[l] = '@'; // Set the letter to a rogue character to ensure that it doesn't match any other checks 
											break; // Break as to move onto the next letter
										}
									}
								}
								
								if (lettersTheSame == lettersOfCurrentWord.length) // If all the letters are the same
								{
									passPhraseIsInvalid = true; // Set passPhraseIsInvalid to true as it's invalid because there is an anagram.
								}
							}
							
						}
					}
					
				}
				
				if (passPhraseIsInvalid == true) // If the phrase has already been found to be invalid
				{
					System.out.println(line + "\n is not valid!"); // Tell the user that the passphrase is not valid
					invalidPhrases++; // Increment the number of invalid phrases
				}
				totalPhrases++; // Increment the amount of phrases
			}
			
				
		}
		
		System.out.println("Out of a total " + totalPhrases + " phrases, " + invalidPhrases + " were invalid, and " + (totalPhrases-invalidPhrases) + " were valid!"); // Tell the user how many were invalid
	}
}
