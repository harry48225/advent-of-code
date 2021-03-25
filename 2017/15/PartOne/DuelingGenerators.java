import java.math.*;

public class DuelingGenerators
{
	public static void main(String[] args)
	{
		DuelingGenerators dG = new DuelingGenerators(); // Create a new instance of the class
		dG.partOne(); // Call the part one method
	}
	
	private void partOne()
	{
		Generator a = new Generator(16807, 883); // Create a new generator for a
		Generator b = new Generator(48271, 879); // Create a new generator for b
		int numberOfMatches = 0; // Store the number of matches
		
		for (int i = 0; i<40000000; i++) // Do this 40 million times
		{
			if (a.generateValue().equals(b.generateValue())) // If we have a match
			{
				numberOfMatches++; // Increment the number of matches
			}
			
		}
		
		System.out.println("There were " + numberOfMatches + " matches");
	}
	
	private class Generator // Generator class
	{
		private int factor = -1;
		private long previousValue = -1;
		
		public Generator(int tempFactor, int tempStartingValue) // Constructor
		{
			factor = tempFactor; // Set the factor
			previousValue = tempStartingValue; // Set the starting value
		}
		
		public String generateValue() // Method to generate a value
		{
			long newValue = previousValue * factor; // Multiply by the factor
			newValue = newValue % 2147483647; // Get the remainder of dividing by 2147483647
			previousValue = newValue; // Set the new value as the previous value
			String newValueBinaryString = Long.toBinaryString(newValue); // Convert it to a binary string
			
			while (newValueBinaryString.length() < 16) // While the string isn't long enough
			{
				newValueBinaryString = "0" + newValueBinaryString; // Pad the string
			}
			return newValueBinaryString.substring(newValueBinaryString.length()-16, newValueBinaryString.length()); // Return the last 16 digits as a string
		}
	}
}