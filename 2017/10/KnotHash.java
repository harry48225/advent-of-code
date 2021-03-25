import java.io.*;
import java.util.Arrays;
public class KnotHash
{
	private final String FILE_NAME = "puzzleInput.txt"; // Store the file name of the puzzle input file

	public static void main(String[] args)
	{
		KnotHash kH = new KnotHash(); // Create a new instance of the class
		kH.startProgram(); // Call the start program method
	}
	
	private void startProgram()
	{
		partTwo(); // Call the partTwo method
	}
	
	private void partOne() // For part one of the challenge
	{
		String[] inputLengths = openAndReadFile(); // Get the input lengths
		
		//System.out.println(Arrays.toString(inputLengths));
		
		int[] intArray = constructIntArray(256); // Create an int array with 5 eleements
		
		//System.out.println(Arrays.toString(intArray));
		
		int currentPosition = 0; // Start at 0
		int skipSize = 0; // Store the skipSize
		for (String stringLength : inputLengths) // Iterate over each length in the array
		{
			int length = Integer.parseInt(stringLength); // Convert the string to an int
			
			// We need to select the number of elements required and store them in a temp array
			int[] tempArray = new int[length]; // Create the temp array
			int tempPosition = currentPosition; // Store the position in the int array while selecting
			
			for (int i = 0; i<length; i++) // Do this 'length' many times
			{
				tempArray[i] = intArray[tempPosition]; // Store the int from intArray into the temp array
				
				tempPosition = tempPosition+=1; // Increment the position
				
				if (tempPosition >= intArray.length) // If we need to wrap around
				{
					tempPosition -= intArray.length; // Wrap around
				}
			}
			
			//System.out.println(Arrays.toString(tempArray));
			
			// Now that we have the tempArray we need to store the reverse in intArray
			tempPosition = currentPosition; // Reset temp position
			for (int i = 0; i<tempArray.length; i++) // Iterate over each element in tempArray
			{
				intArray[tempPosition] = tempArray[tempArray.length-1-i]; // Store the reverse in intArray
				
				tempPosition = tempPosition+=1; // Increment the position
				
				if (tempPosition >= intArray.length) // If we need to wrap around
				{
					tempPosition -= intArray.length; // Wrap around
				}
			}
			
			//System.out.println(Arrays.toString(intArray));
			
			// We should now have completed the twist
			// We just need to observe the skipSize and changes to the currentPosition
			
			currentPosition = currentPosition + length + skipSize; // Increment the current position by the length + skipSize
			
			while (currentPosition >= intArray.length) // While we need to wrap around
			{
				currentPosition -= intArray.length; // Wrap around
			}
			
			skipSize++; // Increment the skip size
			
		}
		
		// We now just have to multiply the first two elements together
		System.out.println(intArray[0] * intArray[1]);
	}
	
	private void partTwo() // For part two of the challenge
	{
		int[] inputLengths = constructByteString(); // Get the input lengths
		
		//System.out.println(Arrays.toString(inputLengths));
		
		int[] intArray = constructIntArray(256); // Create an int array with 5 eleements
		
		//System.out.println(Arrays.toString(intArray));
		
		int currentPosition = 0; // Start at 0
		int skipSize = 0; // Store the skipSize
		for (int rounds=0; rounds<64; rounds++) // Do this for 64 rounds (i.e. 64 times)
		{
			for (int length : inputLengths) // Iterate over each length in the array
			{
				// We need to select the number of elements required and store them in a temp array
				int[] tempArray = new int[length]; // Create the temp array
				int tempPosition = currentPosition; // Store the position in the int array while selecting
				
				for (int i = 0; i<length; i++) // Do this 'length' many times
				{
					tempArray[i] = intArray[tempPosition]; // Store the int from intArray into the temp array
					
					tempPosition = tempPosition+=1; // Increment the position
					
					if (tempPosition >= intArray.length) // If we need to wrap around
					{
						tempPosition -= intArray.length; // Wrap around
					}
				}
				
				//System.out.println(Arrays.toString(tempArray));
				
				// Now that we have the tempArray we need to store the reverse in intArray
				tempPosition = currentPosition; // Reset temp position
				for (int i = 0; i<tempArray.length; i++) // Iterate over each element in tempArray
				{
					intArray[tempPosition] = tempArray[tempArray.length-1-i]; // Store the reverse in intArray
					
					tempPosition = tempPosition+=1; // Increment the position
					
					if (tempPosition >= intArray.length) // If we need to wrap around
					{
						tempPosition -= intArray.length; // Wrap around
					}
				}
				
				//System.out.println(Arrays.toString(intArray));
				
				// We should now have completed the twist
				// We just need to observe the skipSize and changes to the currentPosition
				
				currentPosition = currentPosition + length + skipSize; // Increment the current position by the length + skipSize
				
				while (currentPosition >= intArray.length) // While we need to wrap around
				{
					currentPosition -= intArray.length; // Wrap around
				}
				
				skipSize++; // Increment the skip size
				
			}
		}
		// We now just have to xor the elements in blocks of 16
		String hashString = ""; // Store the hash as a string
		for (int i=0; i<16; i++) // Do this 16 times
		{
			int denaryXOR = 0; // Store the total of the 16 elements XORed together in denary
			for (int j=0; j<16; j++) // Do this 16 times
			{
				denaryXOR = denaryXOR ^ intArray[(i*16)+j]; // Get the next element from the array and xor it
			}
			// Now we need to convert it to hex and add it to the hashString
			String hexXOR = Integer.toHexString(denaryXOR);
			if (hexXOR.length() < 2) // If it's too short
			{
				hexXOR = "0" + hexXOR; // Add a leading 0
			}
			hashString += hexXOR; // add hexXOR to the hash
		}
		
		System.out.println("The hash is: " + hashString);
		System.out.println("It's of length: " + hashString.length());
	}
	private String[] openAndReadFile() // Reads the file and returns the input lengths
	{
		BufferedReader puzzleInput = null; // Initalise the puzzleInput BufferedReader
		
		try
		{
			puzzleInput = new BufferedReader(new FileReader(FILE_NAME)); // Open the file
		}
		catch (Exception e)
		{
			System.out.println("Something went wrong opening the file!"); // Tell the user that something went wrong
			e.printStackTrace(); // Print the stack trace of the error
		}
		
		String currentLine = ""; // Initalise the currentLine variable
		// There's only one line in the text file
		try
		{
			currentLine = puzzleInput.readLine(); // Read the next line of the file
		}
		catch (Exception e)
		{
			System.out.println("Something went wrong reading the file"); // Tell the user that something went wrong
			e.printStackTrace(); // Print the stack trace
		}
		
		return currentLine.split(","); // Return the array of inputLengths by splitting the currentLine at the , s
	}
	
	private int[] openAndReadFileAsByteString() // To open and read the file as a byte string
	{
		BufferedReader puzzleInput = null; // Initalise the puzzleInput BufferedReader
		
		try
		{
			puzzleInput = new BufferedReader(new FileReader(FILE_NAME)); // Open the file
		}
		catch (Exception e)
		{
			System.out.println("Something went wrong opening the file!"); // Tell the user that something went wrong
			e.printStackTrace(); // Print the stack trace of the error
		}
		
		String byteString = ""; // Initalise the byte string variable 
		// There's only one line in the text file
		try
		{
			byteString = puzzleInput.readLine(); // Read the next line of the file
		}
		catch (Exception e)
		{
			System.out.println("Something went wrong reading the file"); // Tell the user that something went wrong
			e.printStackTrace(); // Print the stack trace
		}

		
		char[] byteStringCharArray = byteString.toCharArray(); // Convert the string to a char[]
		
		int[] byteStringValues = new int[byteStringCharArray.length]; // Inital the array to store the values of each character
		
		for (int i = 0; i < byteStringCharArray.length; i++)
		{
			byteStringValues[i] = (int) byteStringCharArray[i]; // Get the ascii value of the char and store it in byteStringValues
		}
		
		return byteStringValues;
	}
	
	private int[] constructByteString() // To construct the byteString
	{
		int[] startOfByteString = openAndReadFileAsByteString();
		// We need to add the lengths 17, 31, 73, 47, 23 to the end
		int[] byteString = Arrays.copyOf(startOfByteString, startOfByteString.length + 5); // Create a new array that is 5 longer and a copy of startOfByteString to store the new elements
		// Store the new elements
		byteString[byteString.length-5] = 17;
		byteString[byteString.length-4] = 31;
		byteString[byteString.length-3] = 73;
		byteString[byteString.length-2] = 47;
		byteString[byteString.length-1] = 23;
		
		return byteString;
			
	}
	
	private int[] constructIntArray(int size) // To construct the int array of given size
	{
		int[] intArray = new int[size]; // Create a new int[]
		
		for (int i = 0; i < intArray.length; i++) // Iterate over each element
		{
			intArray[i] = i; // Store i at i
			// Example:
			// when i is 0 0 will be stored 
			// so intArray[0] = 0
		}
		
		return intArray;
	}
} 