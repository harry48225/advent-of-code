import java.util.Arrays;
public class MemoryReallocation
{
	public static void main(String[] args)
	{
		MemoryReallocation mR = new MemoryReallocation(); // Create a new instance of the class
		mR.startProgram(); // Call the start program method
	}
	
	private void startProgram() // startProgram method
	{
		partTwo(); // call the partOne method
	}
	
	private void partOne() // partOne method
	{
		int[] puzzleInput = {4,1,15,12,0,9,9,5,5,8,7,3,14,5,12,3}; // int[] to store the puzzle input
		//int[] puzzleInput = {0,2,7,0}; // For testing
		
		int[] puzzleInputsSeenBefore [] = new int[100000][];// To store a copy of all the inputs seen before
		boolean seenBefore = false; // Store whether the array has been seen before
		int numberOfCyclesTaken = 0; // Store the number of cycles taken
		while (seenBefore == false) // While we've not seen the array before
		{
			int highestValue = -1; // Store the highest value
			int highestValueIndex = -1; // Store the index of the highest value
			for (int i = 0; i<puzzleInput.length; i++) // Iterate over each entry in the array
			{
				if (puzzleInput[i] > highestValue) // If we have found the new highest value
				{
					highestValue = puzzleInput[i]; // Set the highest value to the value found
					highestValueIndex = i; // Store the index that the highest value was found at
				}
			}
			
			// We should now have the highest value so we need to redistribute it
			
			// Before starting the redistribution
			puzzleInputsSeenBefore[numberOfCyclesTaken] = Arrays.copyOf(puzzleInput, puzzleInput.length); // Store puzzle input in the array of seen arrays. Make a copy so that actual array isn't stored
			System.out.println(Arrays.toString(puzzleInputsSeenBefore[numberOfCyclesTaken]));
		
		// Let's do the redistribution
			int blocksToRedistribute = highestValue; // Get the blocks to redistribute
			puzzleInput[highestValueIndex] = 0; // Set the number at that index to 0 as we've taken all of the blocks
			
			int currentIndex = highestValueIndex; // Store the current index
			
			System.out.println(Arrays.toString(puzzleInput));
			System.out.println("There are " + blocksToRedistribute + " blocks to redistribute");
			
			while (blocksToRedistribute > 0) // While we have blocks left to redistribute
			{
				currentIndex++; // Increment the current index
				if (currentIndex >= puzzleInput.length) // If we need to wrap around
				{
					currentIndex -= puzzleInput.length; // Do the wrap around
				}
				//System.out.println(currentIndex);
				puzzleInput[currentIndex] += 1; // Increment the memory bank
				blocksToRedistribute-=1; // Decrement the amount of blocksToRedistribute
			}
			
			System.out.println(Arrays.toString(puzzleInput));
			
			
			// Check to see if we've seen the array before
			for (int i = 0; i<puzzleInputsSeenBefore.length; i++) // Iterate over each array seen before
			{
				if (puzzleInputsSeenBefore[i] == null) // If there is no array there
				{
					break; // Stop checking as there are no more arrays to check with i.e. we've not seen it before
				}
				else // There must be an array to check with
				{
					for (int j = 0; j<puzzleInputsSeenBefore[i].length; j++) // Check each value
					{
						seenBefore = true; // Set seen before to true
						//System.out.println("Checking " + puzzleInput[j] + " with " + puzzleInputsSeenBefore[i][j]);
						if (puzzleInput[j] != puzzleInputsSeenBefore[i][j]) // If the value doesn't match
						{
							//System.out.println("Definitely not seen before!");
							seenBefore = false; // Set seen before to false
							break; // Stop checking as it's definitely not  been seen before
						}
					}
					
				}
				
				if (seenBefore == true) // If we've seen it before
				{
					break; // Stop checking as we've seen before
				}
			}
			
			numberOfCyclesTaken++; // Increment the number of cycles taken
		}
		
		System.out.println("That took " + numberOfCyclesTaken + " cycles"); // Tell the user how many cycles taken
	}
	
	private void partTwo()
	{
		int[] puzzleInput = {4,1,15,12,0,9,9,5,5,8,7,3,14,5,12,3}; // int[] to store the puzzle input
		//int[] puzzleInput = {0,2,7,0}; // For testing
		int[] puzzleOutput = performCycles(puzzleInput);
		System.out.println("The ending array is " + Arrays.toString(puzzleOutput));
		System.out.println("Performing again");
		int[] newPuzzleOutput = performCycles(puzzleOutput); // Perform the redistribution again to find how big the loop is.
		System.out.println("The ending array is " + Arrays.toString(puzzleOutput));
	}
	
	private int[] performCycles(int[] puzzleInput) // A method to perform the redistribution
	{
		int[] puzzleInputsSeenBefore [] = new int[100000][];// To store a copy of all the inputs seen before
		boolean seenBefore = false; // Store whether the array has been seen before
		int numberOfCyclesTaken = 0; // Store the number of cycles taken
		while (seenBefore == false) // While we've not seen the array before
		{
			int highestValue = -1; // Store the highest value
			int highestValueIndex = -1; // Store the index of the highest value
			for (int i = 0; i<puzzleInput.length; i++) // Iterate over each entry in the array
			{
				if (puzzleInput[i] > highestValue) // If we have found the new highest value
				{
					highestValue = puzzleInput[i]; // Set the highest value to the value found
					highestValueIndex = i; // Store the index that the highest value was found at
				}
			}
			
			// We should now have the highest value so we need to redistribute it
			
			// Before starting the redistribution
			puzzleInputsSeenBefore[numberOfCyclesTaken] = Arrays.copyOf(puzzleInput, puzzleInput.length); // Store puzzle input in the array of seen arrays. Make a copy so that actual array isn't stored
			//System.out.println(Arrays.toString(puzzleInputsSeenBefore[numberOfCyclesTaken]));
		
		// Let's do the redistribution
			int blocksToRedistribute = highestValue; // Get the blocks to redistribute
			puzzleInput[highestValueIndex] = 0; // Set the number at that index to 0 as we've taken all of the blocks
			
			int currentIndex = highestValueIndex; // Store the current index
			
			//System.out.println(Arrays.toString(puzzleInput));
			//System.out.println("There are " + blocksToRedistribute + " blocks to redistribute");
			
			while (blocksToRedistribute > 0) // While we have blocks left to redistribute
			{
				currentIndex++; // Increment the current index
				if (currentIndex >= puzzleInput.length) // If we need to wrap around
				{
					currentIndex -= puzzleInput.length; // Do the wrap around
				}
				//System.out.println(currentIndex);
				puzzleInput[currentIndex] += 1; // Increment the memory bank
				blocksToRedistribute-=1; // Decrement the amount of blocksToRedistribute
			}
			
			//System.out.println(Arrays.toString(puzzleInput));
			
			
			// Check to see if we've seen the array before
			for (int i = 0; i<puzzleInputsSeenBefore.length; i++) // Iterate over each array seen before
			{
				if (puzzleInputsSeenBefore[i] == null) // If there is no array there
				{
					break; // Stop checking as there are no more arrays to check with i.e. we've not seen it before
				}
				else // There must be an array to check with
				{
					for (int j = 0; j<puzzleInputsSeenBefore[i].length; j++) // Check each value
					{
						seenBefore = true; // Set seen before to true
						//System.out.println("Checking " + puzzleInput[j] + " with " + puzzleInputsSeenBefore[i][j]);
						if (puzzleInput[j] != puzzleInputsSeenBefore[i][j]) // If the value doesn't match
						{
							//System.out.println("Definitely not seen before!");
							seenBefore = false; // Set seen before to false
							break; // Stop checking as it's definitely not  been seen before
						}
					}
					
				}
				
				if (seenBefore == true) // If we've seen it before
				{
					break; // Stop checking as we've seen before
				}
			}
			
			numberOfCyclesTaken++; // Increment the number of cycles taken
		}
		
		System.out.println("That took " + numberOfCyclesTaken + " cycles"); // Tell the user how many cycles taken
		return puzzleInput;
	}
	
	
}