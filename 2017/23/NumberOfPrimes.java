public class NumberOfPrimes
{
	public static void main(String[] args)
	{
		NumberOfPrimes nOP = new NumberOfPrimes(); // Create a new instance of the class
		nOP.startProgram(); // Call the startProgram method
	}
	
	private void startProgram()
	{
		int numberOfPrimes = 0; // Track the number of primes
		int numberOfNonPrimes = 0; // Track the number of non primes
		for (int i = 106700; i <= 123700; i+=17) // For the numbers 1-1000
		{
			boolean prime = true;
			
			for (int j = 2; j < i; j ++)
			{
				if (i % j == 0)
				{
					prime = false;
					break;
				}
			}
			
			if (prime)
			{	
				numberOfPrimes++; // Increment the number of primes
			}
			else
			{
				numberOfNonPrimes++;
			}
		}
		
		System.out.println("Number of non primes: " + (numberOfNonPrimes));
	}
}