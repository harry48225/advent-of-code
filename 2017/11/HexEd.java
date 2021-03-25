import java.io.*;

public class HexEd
{
	private final String FILE_NAME = "puzzleInput.txt";
	
	public static void main(String[] args)
	{
		HexEd hE = new HexEd(); // Create a new instance of the class
		hE.startProgram(); // Call the startProgram method
	}
	
	private void startProgram()
	{
		partTwo(); // Call the partTwo method
	}
	
	private void partOne() // For part one of the challenge
	{
		
		int n, ne, nw, s, se, sw; // Initalise variables to store the directions
		n = 0;
		ne = 0;
		nw = 0;
		s = 0;
		se = 0;
		sw = 0;
		
		BufferedReader puzzleInput = null; // Initalise the puzzleInput BufferedReader
		
		try
		{
			puzzleInput = new BufferedReader(new FileReader(FILE_NAME)); // Open the file
		}
		catch (Exception e)
		{
			e.printStackTrace(); // Print what went wrong
		}
		
		String line = ""; // Initalise the line variable
		
		try 
		{
			line = puzzleInput.readLine(); // There is only one line
		} 
		catch (Exception e)
		{
			e.printStackTrace(); // Print what went wrong
		}
		
		String[] splitLine = line.split(","); // Split the line at ","
		
		for (String direction : splitLine) // Iterate over the input and count the occurances
		{
			if (direction.equals("n"))
			{
				n++;
			}
			else if (direction.equals("ne"))
			{
				ne++;
			}
			else if (direction.equals("nw"))
			{
				nw++;
			}
			else if (direction.equals("s"))
			{
				s++;
			}
			else if (direction.equals("se"))
			{
				se++;
			}
			else if (direction.equals("sw"))
			{
				sw++;
			}
		}
		
		System.out.println("n: " + n + " ne: " + ne + " nw: " + nw + " s: " + s + " se: " + se + " sw: " + sw); // Outpyt the occurances of different directions
			
		// Now we need to simplify
		boolean changed = true; // Store whether a change as happened
		while (changed == true) // While the directions have been simplified i.e. do this until they're fully simplified
		{
			changed = false;
			
			// n and s cancel
			if (n > 0 && s > 0) // If we have norths and souths
			{
				n--; // Cancel them
				s--;
				changed = true; // Set changed to true
			}
			// ne and sw cancel
			else if (ne > 0 && sw > 0)
			{
				ne--; // Cancel them
				sw--;
				changed = true; // Set changed to true
			}
			// nw and se cancel
			else if (nw > 0 && se > 0)
			{
				nw--; // Cancel them
				se--;
				changed = true; // Set changed to true
			}
			// nw and ne cancel to n
			else if (nw > 0 && ne > 0)
			{
				nw--; // Cancel them
				ne--; 
				n++; // Increment n
				changed = true; // Set changed to true
			}
			// sw and se cancel to s
			else if (sw > 0 && se > 0)
			{
				sw--; // Cancel them
				se--;
				s++; // Increment s
				changed = true; // Set changed to true
			}
			// ne and s cancel to se
			else if (ne > 0 && s > 0)
			{
				ne--; // Cancel them
				s--;
				se++; // Increment se
				changed = true; // Set changed to true
			}
			// nw and s cancel to sw
			else if (nw > 0 && s > 0)
			{
				nw--; // Cancel them
				s--;
				sw++; // Increment sw
				changed = true; // Set changed to true
			}
			// se and n cancel to ne
			else if (se > 0 && n > 0)
			{
				se--; // Cancel them
				n--;
				ne++; // Increment ne
				changed = true; // Set changed to true
			}
			// sw and n cancel to nw
			else if (sw > 0 && n > 0)
			{
				sw--; // Cancel them
				n--;
				nw++; // Increment nw
				changed = true; // Set changed to true
			}
			
			//System.out.println("n: " + n + " ne: " + ne + " nw: " + nw + " s: " + s + " se: " + se + " sw: " + sw); // Outpyt the occurances of different directions
		}
		
		System.out.println("Done simplifying the result is: ");
		System.out.println("n: " + n + " ne: " + ne + " nw: " + nw + " s: " + s + " se: " + se + " sw: " + sw); // Outpyt the occurances of different directions
		System.out.println("Which adds to: " + ((int)n + ne + nw + s + se + sw) + " steps!"); 
	}
	
	private void partTwo() // For part two of the challenge
	{
		int n, ne, nw, s, se, sw; // Initalise variables to store the directions
		n = 0;
		ne = 0;
		nw = 0;
		s = 0;
		se = 0;
		sw = 0;
		
		BufferedReader puzzleInput = null; // Initalise the puzzleInput BufferedReader
		
		try
		{
			puzzleInput = new BufferedReader(new FileReader(FILE_NAME)); // Open the file
		}
		catch (Exception e)
		{
			e.printStackTrace(); // Print what went wrong
		}
		
		String line = ""; // Initalise the line variable
		
		try 
		{
			line = puzzleInput.readLine(); // There is only one line
		} 
		catch (Exception e)
		{
			e.printStackTrace(); // Print what went wrong
		}
		
		String[] splitLine = line.split(","); // Split the line at ","
		
		int highestNumberOfStepsAway = 0; // Store the highest number of steps away
		
		for (String direction : splitLine) // Iterate over the input and count the occurances
		{
			if (direction.equals("n"))
			{
				n++;
			}
			else if (direction.equals("ne"))
			{
				ne++;
			}
			else if (direction.equals("nw"))
			{
				nw++;
			}
			else if (direction.equals("s"))
			{
				s++;
			}
			else if (direction.equals("se"))
			{
				se++;
			}
			else if (direction.equals("sw"))
			{
				sw++;
			}
			
			// We need to see how many steps away they are
			int numberOfStepsAway = simplify(n, ne, nw, s, se, sw); // Find how many steps away they are
			if (numberOfStepsAway > highestNumberOfStepsAway) // If we have the new highest
			{
				highestNumberOfStepsAway = numberOfStepsAway; // Set it as the new highest
			}
		}
		
		System.out.println("The furthest number of steps away was: " + highestNumberOfStepsAway);
		
			
		
	}
	
	private int simplify(int n, int ne, int nw, int s, int se, int sw) // Simplifies the steps
	{
		// Now we need to simplify
		boolean changed = true; // Store whether a change as happened
		while (changed == true) // While the directions have been simplified i.e. do this until they're fully simplified
		{
			changed = false;
			
			// n and s cancel
			if (n > 0 && s > 0) // If we have norths and souths
			{
				n--; // Cancel them
				s--;
				changed = true; // Set changed to true
			}
			// ne and sw cancel
			else if (ne > 0 && sw > 0)
			{
				ne--; // Cancel them
				sw--;
				changed = true; // Set changed to true
			}
			// nw and se cancel
			else if (nw > 0 && se > 0)
			{
				nw--; // Cancel them
				se--;
				changed = true; // Set changed to true
			}
			// nw and ne cancel to n
			else if (nw > 0 && ne > 0)
			{
				nw--; // Cancel them
				ne--; 
				n++; // Increment n
				changed = true; // Set changed to true
			}
			// sw and se cancel to s
			else if (sw > 0 && se > 0)
			{
				sw--; // Cancel them
				se--;
				s++; // Increment s
				changed = true; // Set changed to true
			}
			// ne and s cancel to se
			else if (ne > 0 && s > 0)
			{
				ne--; // Cancel them
				s--;
				se++; // Increment se
				changed = true; // Set changed to true
			}
			// nw and s cancel to sw
			else if (nw > 0 && s > 0)
			{
				nw--; // Cancel them
				s--;
				sw++; // Increment sw
				changed = true; // Set changed to true
			}
			// se and n cancel to ne
			else if (se > 0 && n > 0)
			{
				se--; // Cancel them
				n--;
				ne++; // Increment ne
				changed = true; // Set changed to true
			}
			// sw and n cancel to nw
			else if (sw > 0 && n > 0)
			{
				sw--; // Cancel them
				n--;
				nw++; // Increment nw
				changed = true; // Set changed to true
			}
			
			//System.out.println("n: " + n + " ne: " + ne + " nw: " + nw + " s: " + s + " se: " + se + " sw: " + sw); // Outpyt the occurances of different directions
		}
		
		//System.out.println("Done simplifying the result is: ");
		//System.out.println("n: " + n + " ne: " + ne + " nw: " + nw + " s: " + s + " se: " + se + " sw: " + sw); // Outpyt the occurances of different directions
		//System.out.println("Which adds to: " + ((int)n + ne + nw + s + se + sw) + " steps!"); 
		
		return (n + ne + nw + s + se + sw); // Return the total number of steps away
	}
}