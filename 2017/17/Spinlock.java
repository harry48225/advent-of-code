import java.util.*;

public class Spinlock
{
	private final int puzzleInput = 348;
	
	public static void main(String[] args)
	{
		Spinlock sl = new Spinlock(); // Create a new instance of the class
		sl.partOne();
		sl.partTwo(); // Call the part two method
	}
	
	private void partOne() // For part one of the challenge
	{
		int[] circularBuffer = {0}; // Create an int array to store the circular buffer and put 0 in as the inital value
		
		int currentIndex = 0; // Store the current index in the circular buffer
		
		for (int i = 1; i <= 2017; i++) // Do this 2017 times
		{
			int requiredIndex = currentIndex + puzzleInput; // Store the index where the new element needs to be added after
			
			requiredIndex += 1; // Add one to the required index as we want to store it at the index after it
			
			while (requiredIndex >= circularBuffer.length) // While we need to wrap around
			{
				requiredIndex -= circularBuffer.length; // Wrap around
			}
			
			int[] newCircularBuffer = new int[circularBuffer.length + 1]; // Create a new int[] with one more space
			
			for (int j = 0; j<newCircularBuffer.length; j++) // For each element
			{
				if (j < requiredIndex) // If we've not reached the required index
				{
					newCircularBuffer[j] = circularBuffer[j]; // Copy elements from previous array
				}
				else if (j == requiredIndex) // If we've reached the required index
				{
					newCircularBuffer[j] = i; // Store i in the array there
				}
				else if (j > requiredIndex) // If we're past the required index
				{
					newCircularBuffer[j] = circularBuffer[j-1]; // Copy elements from previous array 
				}
			}
			
			circularBuffer = Arrays.copyOf(newCircularBuffer, newCircularBuffer.length); // Store the new buffer as the old one
			
			currentIndex = requiredIndex; // Set the current position as the required index
		}
		
		//System.out.println(Arrays.toString(circularBuffer));
		
		for (int i = 0; i<circularBuffer.length; i++) // Iterate over the circular buffer
		{
			if (circularBuffer[i] == 2017) // If we've found the 2017 item
			{
				int requiredIndex = i + 1; // We want the element after this one
				
				while (requiredIndex >= circularBuffer.length) // While we need to wrap around
				{
					requiredIndex -= circularBuffer.length; // Wrap around
				}
				System.out.println(circularBuffer[requiredIndex]); // Print the next element
				break; // Break from the loop
			}
		}
	}
	
	private void partTwo() // For part two of the challenge
	{
		int currentIndex = 0; // Store the current index in the circular buffer
		int zeroIndex = 0; // Store the index that zero is stored at
		int circularBufferLength = 1; // Store the length of the circular buffer
		int valueAfterZero = 0; // Store the value after 0
		
		for (int i = 1; i <= 50000000; i++) // Do this 50 000 000 times
		{
			float percentageComplete = ((float) i / 50000000) * 100;
			if (percentageComplete % 1 == 0)
			{
				System.out.println(percentageComplete);
			}
			
			int requiredIndex = currentIndex + puzzleInput + 1; // Store the index where the new element needs to be added we add one because we want to store it in the one after
			
			while (requiredIndex >= circularBufferLength) // While we need to wrap around
			{
				requiredIndex -= circularBufferLength; // Wrap around
			}
			
			circularBufferLength += 1; // Increase the length of the circular buffer
			
			if (requiredIndex < zeroIndex) // If the required index is less than zero's index i.e. we are putting it in the array before 0
			{
				zeroIndex += 1; // Increment the index that zero is at by one as it'll be pushed up to accomodate the new element
			}
			else if (requiredIndex == zeroIndex)// If we are putting it straight after zero
			{
				valueAfterZero = i;
			}
			
			currentIndex = requiredIndex; // Set the current position as the required index
		}
		
		System.out.println(valueAfterZero + " length " + circularBufferLength);
		
	}
}