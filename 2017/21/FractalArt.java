import java.io.*;
import java.util.*;

public class FractalArt
{
	private final String FILE_NAME = "puzzleInput.txt";
	
	private HashMap<int[][], int[][]> artistsBook; 
	
	public static void main(String[] args)
	{
		FractalArt fA = new FractalArt(); // Create a new instance of the class
		fA.partOne(); // Call the part one method
	}
	
	private void partOne() // For part one of the challenge
	{
		artistsBook = loadArtistsBook(); // Load the artists book
		
		// For debugging
		for (int[][] key : artistsBook.keySet()) // For each key
		{
			for (int[] row : key) 
			{
				System.out.println(Arrays.toString(row));
			}
			
			System.out.println("Links to ");
			
			for (int[] row : artistsBook.get(key)) 
			{
				System.out.println(Arrays.toString(row));
			}
			
			System.out.println("---------");
		}
		
		/*
		int[][] testMatrixBase = {{0,1,0},
								  {0,0,1},
								  {1,1,1}};
		rotate(testMatrixBase, "clockwise");
		System.out.println("-------------");
		rotate(testMatrixBase, "anticlockwise");
		System.out.println("-------------");
		flip(testMatrixBase, "horizontal");
		System.out.println("-------------");
		flip(testMatrixBase, "vertical");
		*/
		
		
		int[][] image = {{0,1,0},
						 {0,0,1},
						 {1,1,1}}; // Starting image
				
		for (int count = 0; count < 18; count++) // Do this five times // Or 18 times for part two
		{
			int size = image[0].length; // Get the size of the grid
			
			int newSize = -1; // Declare a variable to store the new size of the grid
			
			int [][] newGrid = null; // To store the new grid
			
			if (size % 2 == 0) // If 2 divides into the size of the grid
			{
				//System.out.println("Divisable by 2!");
				newSize = size + size/2; // Calculate the new size of the grid
			
				// The grid needs to be divided into 2x2 squares
				
				newGrid = new int[newSize][newSize]; // Create a new grid of the new size
				
				for (int i = 0; i <= size - 2; i += 2) // Go down the rows
				{
					for (int j = 0; j <= size - 2; j += 2) // Go along the columns
					{
						//int[][] subMatrix = new int[2][2]; // Create a new 2d array to store the submatrix
						//System.out.println("i: " + i);
						//System.out.println("j: " + j);
						
						int[][] subMatrix = {{image[i][j], image[i][j+1]},
											{image[i+1][j], image[i+1][j+1]}}; // Get the sub matrix
											
						//System.out.println("Sub matrix");
						//for (int[] row : subMatrix)
						//{
						//	System.out.println(Arrays.toString(row));
						//}
						//System.out.println("--------------");						
						// We now need to check if there is an enhancement for it
						
						int[][] enhancedMatrix = null; // Declare a new matrix to store the enhancement
						
						if (getFromArtistsBook(subMatrix) != null) // If it's in the book
						{
							enhancedMatrix = getFromArtistsBook(subMatrix); // Get it from the book
						}
						else if (getFromArtistsBook(rotate(subMatrix, "clockwise")) != null) // If it's in the book
						{
							enhancedMatrix = getFromArtistsBook(rotate(subMatrix, "clockwise")); // Get it from the book
						}
						else if (getFromArtistsBook(rotate(subMatrix, "anticlockwise")) != null) // If it's in the book
						{
							enhancedMatrix = getFromArtistsBook(rotate(subMatrix, "anticlockwise")); // Get it from the book
						}
						else if (getFromArtistsBook(flip(subMatrix, "horizontal")) != null) // If it's in the book
						{
							enhancedMatrix = getFromArtistsBook(flip(subMatrix, "horizontal")); // Get it from the book
						}
						else if (getFromArtistsBook(flip(subMatrix, "vertical")) != null) // If it's in the book
						{
							enhancedMatrix = getFromArtistsBook(flip(subMatrix, "vertical")); // Get it from the book
						}
						else if (getFromArtistsBook(flip(rotate(subMatrix, "clockwise"), "vertical")) != null) // If it's in the book
						{
							enhancedMatrix = getFromArtistsBook(flip(rotate(subMatrix, "clockwise"), "vertical")); // Get it from the book
						}
						else if (getFromArtistsBook(flip(rotate(subMatrix, "anticlockwise"), "vertical")) != null) // If it's in the book
						{
							enhancedMatrix = getFromArtistsBook(flip(rotate(subMatrix, "anticlockwise"), "vertical")); // Get it from the book
						}
						else if (getFromArtistsBook(flip(flip(subMatrix, "horizontal"), "vertical")) != null) // If it's in the book
						{
							enhancedMatrix = getFromArtistsBook(flip(flip(subMatrix, "horizontal"), "vertical")); // Get it from the book
						}
						else
						{
							System.out.println("Not found in artists book!"); // Tell the user that it wasn't found in the artists book
						}
						
						// Now we need to insert it into the new matrix
						newGrid = insertIntoMatrix(newGrid, enhancedMatrix, (i + i/2), (j + j/2)); // Insert into the new matrix
					}
				}
				
			}
			else if (size % 3 == 0) // If 3 divides into the size of the grid
			{
				newSize = size + size/3; // Calculate the new size of the grid
			
				// The grid needs to be divided into 2x2 squares
				
				newGrid = new int[newSize][newSize]; // Create a new grid of the new size
				
				for (int i = 0; i <= size - 3; i += 3) // Go down the rows
				{
					for (int j = 0; j <= size - 3; j += 3) // Go along the columns
					{
						//int[][] subMatrix = new int[3][3]; // Create a new 2d array to store the submatrix
						
						int[][] subMatrix = {{image[i][j], image[i][j+1], image[i][j+2]},
											{image[i+1][j], image[i+1][j+1], image[i+1][j+2]},
											{image[i+2][j], image[i+2][j+1], image[i+2][j+2]}}; // Get the sub matrix
									 
						// We now need to check if there is an enhancement for it
						/*
						System.out.println("Sub matrix");
						for (int[] row : subMatrix)
						{
							System.out.println(Arrays.toString(row));
						}
						System.out.println("--------------");
						*/
						int[][] enhancedMatrix = null; // Declare a new matrix to store the enhancement
						
						if (getFromArtistsBook(subMatrix) != null) // If it's in the book
						{
							enhancedMatrix = getFromArtistsBook(subMatrix); // Get it from the book
						}
						else if (getFromArtistsBook(rotate(subMatrix, "clockwise")) != null) // If it's in the book
						{
							enhancedMatrix = getFromArtistsBook(rotate(subMatrix, "clockwise")); // Get it from the book
						}
						else if (getFromArtistsBook(rotate(subMatrix, "anticlockwise")) != null) // If it's in the book
						{
							enhancedMatrix = getFromArtistsBook(rotate(subMatrix, "anticlockwise")); // Get it from the book
						}
						else if (getFromArtistsBook(flip(subMatrix, "horizontal")) != null) // If it's in the book
						{
							enhancedMatrix = getFromArtistsBook(flip(subMatrix, "horizontal")); // Get it from the book
						}
						else if (getFromArtistsBook(flip(subMatrix, "vertical")) != null) // If it's in the book
						{
							enhancedMatrix = getFromArtistsBook(flip(subMatrix, "vertical")); // Get it from the book
						}
						else if (getFromArtistsBook(flip(rotate(subMatrix, "clockwise"), "vertical")) != null) // If it's in the book
						{
							enhancedMatrix = getFromArtistsBook(flip(rotate(subMatrix, "clockwise"), "vertical")); // Get it from the book
						}
						else if (getFromArtistsBook(flip(rotate(subMatrix, "anticlockwise"), "vertical")) != null) // If it's in the book
						{
							enhancedMatrix = getFromArtistsBook(flip(rotate(subMatrix, "anticlockwise"), "vertical")); // Get it from the book
						}
						else if (getFromArtistsBook(flip(flip(subMatrix, "horizontal"), "vertical")) != null) // If it's in the book
						{
							enhancedMatrix = getFromArtistsBook(flip(flip(subMatrix, "horizontal"), "vertical")); // Get it from the book
						}
						else
						{
							System.out.println("Not found in artists book!"); // Tell the user that it wasn't found in the artists book
						}
						
						//System.out.println(enhancedMatrix);
						
						// Now we need to insert it into the new matrix
						newGrid = insertIntoMatrix(newGrid, enhancedMatrix, (i + i/3), (j + j/3)); // Insert into the new matrix
					}
				}
				
			}
			
			System.out.println("Cycle " + count + " complete!");
			image = newGrid; // Update the image
			int totalPixelsOn = 0; // Store the total number of pixels that are on
			for (int[] row : image) 
			{
				System.out.println(Arrays.toString(row));
				
				for (int pixel : row) // For each pixel in the row
				{
					if (pixel == 1) // If the pixel is on
					{
						totalPixelsOn += 1; // Add one to the total
					}
				}
			}
			
			System.out.println("Done printing array!");
			System.out.println("There are " + totalPixelsOn + " pixels that are on!");
		}
						 
		
	}
	
	private int[][] insertIntoMatrix(int[][] mainMatrix, int[][] subMatrix, int row, int column) // Inserts a sub matrix into the main matrix
	{
		int size = subMatrix.length; // Get the size of the matrix
		
		for (int i = 0; i < size; i++) // For each row
		{
			for (int j = 0; j < size; j++) // For each column
			{
				mainMatrix[row + i][j + column] = subMatrix[i][j]; // Insert into the main matrix
			}
		}
		
		return mainMatrix; // Return the matrix
	}
	private HashMap<int[][], int[][]> loadArtistsBook() // Method to load the puzzle input
	{
		HashMap<int[][], int[][]> outputBook = new HashMap<int[][], int[][]>(); // Initalise the HashMap
		
		BufferedReader puzzleInput = null; // Initalise a variable to store the BufferedReader
		
		try
		{
			puzzleInput = new BufferedReader(new FileReader(FILE_NAME)); // Open the file
		}
		catch (Exception e) // Something went wrong
		{
			e.printStackTrace(); // Print what went wrong
		}
		
		String line = ""; // Initalise the current line variable
		
		while (line != null) // While there are lines to read
		{
			try
			{
				line = puzzleInput.readLine(); // Read the next line from the file
			}
			catch (Exception e) // Something went wrong
			{
				e.printStackTrace(); // Print what went wrong
			}
			
			if (line != null) // If the line isn't empty
			{
				String[] matrices = line.split(" => "); // Split the line into the two matrices
				
				outputBook.put(parseMatrix(matrices[0]), parseMatrix(matrices[1])); // Store the parsed matricies in the HashMap
			}
		}
		
		return outputBook; // Return the laoded book

	}
	
	private int[][] getFromArtistsBook(int[][] arrayToCheck) // To check if an array is in the artists book
	{
		boolean theSame = true; // Store whether the arrays are the same
		int[][] pattern = null; // Store the pattern from the book
		
		for (int[][] rule : artistsBook.keySet()) // For each rule in the book
		{
			if (rule.length == arrayToCheck.length) // If they are the same length check them
			{
				theSame = true; // Store whether the arrays are the same
				
				for (int i = 0; i < rule.length; i++) // For each row in the rule
				{
					/*
					System.out.println("Checking");
					System.out.println(Arrays.toString(arrayToCheck[i]));
					System.out.println(Arrays.toString(rule[i]));
					*/
					
					if (Arrays.equals(rule[i], arrayToCheck[i]) == false) // If they are not equal
					{
						theSame = false; // They are not the same
					}
				}
				
				if (theSame == true) // If they are the same
				{
					//System.out.println("They are the same");
					pattern = artistsBook.get(rule); // Get the pattern from the artist's book
					break; // Break from the loop
				}
			}
			else
			{
				theSame = false; // They are not the same
			}
		}
		
		return pattern; // Return the pattern from the book (or null if it wasn't in the book)
	}
	
	private int[][] parseMatrix(String matrixString) // Parses a matrix from a string
	{
		String[] rows = matrixString.split("/"); // Split into the rows
		
		int[][] matrix = new int[rows.length][rows.length]; // Create a new int array to store the output matrix
		
		for (int i = 0; i < rows.length; i++) // For each row
		{
			char[] pixels = rows[i].toCharArray(); // Get each individual pixel
			
			for (int j = 0; j < pixels.length; j++) // For each pixel
			{
				if (pixels[j] == '.') // If it's an empty pixel
				{
					matrix[i][j] = 0; // Store a zero in the output matrix
				}
				else if (pixels[j] == '#') // If it's full
				{
					matrix[i][j] = 1; // Store a one in the output matrix
				}
			}
		}
		
		return matrix; // Return the matrix
	}

	private int[][] rotate(int[][] baseMatrix, String direction) // To rotate the matrix
	{
		int size = baseMatrix[0].length; // Get the size
		int [][] outputMatrixBase = new int[size][size]; // Create an int to store the output matrix
		
		
		if (direction.equals("clockwise"))
		{
			// Clock wise
			// 1st row = first column reversed
			// .....
			// last row = last column reversed
			
			for (int i = 0; i < size; i++) // For each row to populate the output array
			{
				// We need to get the relevant column
				int[] reversedColumn = new int[size]; // Create an int array to store the column
				for (int j = size-1; j >= 0; j--) // For each row in the baseMatrix starting from the bottom
				{
					reversedColumn[(size-1)-j] = baseMatrix[j][i]; // Get the value stored in the relevant column and store it, working from the end, in reversedColumn Array
				}
				outputMatrixBase[i] = reversedColumn; // Store the reversed column
			}
		}
		else if (direction.equals("anticlockwise"))
		{
			// Anti clock wise
			// 1st row = last column
			// .....
			// last row = first column
			
			for (int i = 0; i < size; i++) // For each row to populate the output array
			{
				// We need to get the relevant column
				int[] column = new int[size]; // Create an int array to store the column
				for (int j = 0; j < size; j++) // For each row in the baseMatrix starting from the top
				{
					column[j] = baseMatrix[j][(size-1)-i]; // Get the value stored in the relevant column (starting from the right) and store it, working from the end, in reversedColumn Array
				}
				outputMatrixBase[i] = column; // Store the reversed column
			}
		}
		
		return outputMatrixBase; // Return the output matrix 
		
	}
	
	public int[][] flip(int[][] baseMatrix, String direction) // To flip the matrix
	{
		int size = baseMatrix[0].length; // Get the size
		int [][] outputMatrixBase = new int[size][size]; // Create an int to store the output matrix
		
		if (direction.equals("horizontal")) // If it's a horizontal flip
		{
			for (int i = 0; i < size; i++) // For each row to populate the output array
			{
				// We need to get the relevant column
				int[] reversedRow = new int[size]; // Create an int array to store the reversed row
				for (int j = 0; j < size; j++) // For each column in the baseMatrix row
				{
					reversedRow[(size-1)-j] = baseMatrix[i][j]; // Get the value stored in the relevant column and store it, working from the end, in reversedColumn Array
				}
				outputMatrixBase[i] = reversedRow; // Store the reversed row
			}
		}
		else if (direction.equals("vertical")) // If it's a vertical flip
		{
			for (int i = 0; i < size; i++) // For each row to populate the output array
			{
				// We need to get the relevant column
				int[] row = new int[size]; // Create an int array to store the row
				for (int j = 0; j < size; j++) // For each column in the baseMatrix row
				{
					// Take the value from the rows starting from the bottom row
					row[j] = baseMatrix[(size-1)-i][j]; // Get the value stored in the relevant column and store it working from the start
				}
				outputMatrixBase[i] = row; // Store the row
			}
		}
		
		return outputMatrixBase;  // Return the output matrix
		
	}

}