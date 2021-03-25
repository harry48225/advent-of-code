public class DiskDefragmentation
{
	private final String PUZZLE_INPUT = "jxqlasbh"; // The puzzle input
	
	private int[][] visitedSquares = new int[128][128];
	private int[][] grid = new int[128][128]; // Grid of disk
	
	public static void main(String[] args)
	{
		DiskDefragmentation dD = new DiskDefragmentation(); // Create a new instance of the class
		dD.partTwo(); // Call the part two method
	}
	
	private void partOne() // For part one of the challenge
	{
		int numberOfUsedSquares = 0; // Store the number of used squares
		for (int i = 0; i<128; i++) // Do this from 0 to 127
		{
			String hash = KnotHash.hash(PUZZLE_INPUT + "-" + i); // Hash the puzzleinput
			for (int j = 0; j<128; j++) // Do this from 0 to 127
			{
				if (hash.charAt(j) == '0')
				{
					grid[i][j] = 0; // Store zero in it's location in the grid
				}
				else if (hash.charAt(j) == '1')
				{
					grid[i][j] = 1; // Store one in it's location in the grid
					numberOfUsedSquares++; // Increment the number of used squares
				}
			}
		}
		System.out.println("There are " + numberOfUsedSquares + " used squares!");
	}
	
	private void partTwo() // For part two of the challenge
	{
		
		for (int i = 0; i<128; i++) // Do this from 0 to 127
		{
			String hash = KnotHash.hash(PUZZLE_INPUT + "-" + i); // Hash the puzzleinput
			for (int j = 0; j<128; j++) // Do this from 0 to 127
			{
				if (hash.charAt(j) == '0')
				{
					grid[i][j] = 0; // Store zero in it's location in the grid
				}
				else if (hash.charAt(j) == '1')
				{
					grid[i][j] = 1; // Store one in it's location in the grid
				}
			}
		}
		
		int numberOfRegions = 0; // Count the number of regions
		
		for (int i = 0; i<128; i++) // Iterate over the rows
		{
			for (int j = 0; j<128; j++) // Iterate over the columns
			{
				if (grid[i][j] == 1 && visitedSquares[i][j] == 0) // If it's filled/used and we've not visited the square before
				{
					findRegions(i, j); // Find the region i.e. the region that the square is part of and find all the other connected squares
					numberOfRegions++; // Increment the number of regions
				}
			}
		}
		
		System.out.println("There were " + numberOfRegions + " regions!");
	}
	
	private void findRegions(int x, int y) // Find the regions starting from a given coordinate
	{
		visitedSquares[x][y] = 1; // Set the square as visited
		// We need to check the four squares around it
		if (grid[x][y] != 0) // If the the square is occupied
		{
			
			// Initalise variables
			int xToCheck = x;
			int yToCheck = y;
			
			xToCheck = x + 1;
			
			if ((xToCheck <= 127) && (xToCheck >= 0) && (visitedSquares[xToCheck][yToCheck] == 0)) // If it's in the grid and it's not been visited before
			{
				findRegions(xToCheck, yToCheck);
			}
			
			xToCheck = x - 1;
			
			if ((xToCheck <= 127) && (xToCheck >= 0) && (visitedSquares[xToCheck][yToCheck] == 0)) // If it's in the grid and it's not been visited before
			{
				findRegions(xToCheck, yToCheck);
			}
			
			xToCheck = x;
			yToCheck = y + 1;
			
			if ((yToCheck <= 127) && (yToCheck >= 0) && (visitedSquares[xToCheck][yToCheck] == 0)) // If it's in the grid and it's not been visited before
			{
				findRegions(xToCheck, yToCheck);
			}
			
			yToCheck = y - 1;
			
			if ((yToCheck <= 127) && (yToCheck >= 0) && (visitedSquares[xToCheck][yToCheck] == 0)) // If it's in the grid and it's not been visited before
			{
				findRegions(xToCheck, yToCheck);
			}
		
		}
	}
}