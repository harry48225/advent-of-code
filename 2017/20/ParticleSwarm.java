import java.util.*;
import java.io.*;

public class ParticleSwarm
{
	private final String FILE_NAME = "puzzleInput.txt";
	
	public static void main(String[] args)
	{
		ParticleSwarm pS = new ParticleSwarm(); // Create a new instance of the class
		pS.partTwo(); // Call the part two method
	}
	
	private void partOne() // For part one of the challenge
	{
		BufferedReader puzzleInput = null; // Initalise the variable to store the BufferedReader
		
		try
		{
			puzzleInput = new BufferedReader(new FileReader(FILE_NAME)); // Open the file
		}
		catch (Exception e) // Something went wrong
		{
			e.printStackTrace(); // Print what went wrong
		}
		
		ArrayList<long[][]> particles = new ArrayList<long[][]>(); // Initalise a new arraylist to store the particles
		String line = ""; // Initalise the line variable
		
		// Load the particles
		while (line != null) // While there are lines to read
		{
			try
			{
				line = puzzleInput.readLine(); // Read the next line
			}
			catch (Exception e) // Something went wrong
			{
				e.printStackTrace(); // Print what went wrong
			}
			
			if (line != null) // If there is content in the line
			{
				
				String[] splitLine = line.split(", "); // Split the line at ", "
				
				long[][] particleDetails = new long[3][3]; // Create a new long to store the information about the particle
				
				// Get the position
				particleDetails[0] = parseValues(splitLine[0]); // Parse the position part of the line and store it in the array
				// Get the velocity
				particleDetails[1] = parseValues(splitLine[1]); // Parse the velocity part of the line and store it in the array
				// Get the acceleration
				particleDetails[2] = parseValues(splitLine[2]); // Parse the acceleration part of the line and store it in the array
				
				particles.add(particleDetails); // Add the particle to the array
			}
		}
		
		System.out.println(particles.size() + " particles were loaded!");
		
		// We've now got the particles loaded so we need to run the simulation
		
		
		int loopCount = 0; // Store the count
		
		while (true) // Until the user manually terminates
		{
			int closestParticleIndex = -1; // Store the closest particles number
			long closestParticleDistance = -1; // Store the distance of the closest particle
			// Move each particle
			for (int i = 0; i < particles.size(); i++) // Iterate over each particle
			{
				long[][] particle = particles.get(i); // Get the particle
				
				for (int j = 0; j < 3; j++) // Add each acceleration to the velocity
				{
					particle[1][j] += particle[2][j]; // Add the acceleration to the velocity
				}
				
				for (int j = 0; j < 3; j++) // Add each velocity to the position
				{
					particle[0][j] += particle[1][j]; // Add the velocity to the position
				}
				
				//System.out.println(Arrays.toString(particle[0]) + " " + Arrays.toString(particle[1]));
				
				long distanceFromZero = Math.abs(particle[0][0]) + Math.abs(particle[0][1]) + Math.abs(particle[0][2]); // Add the positions to find the distance from zero
				//System.out.println(distanceFromZero);
				if (closestParticleIndex == -1 || closestParticleDistance == -1) // If the closest particles hasn't been set for the first time yet
				{
					//System.out.println("Set new closest particle INITAL");
					closestParticleIndex = i; // Set this particle as the closest
					closestParticleDistance = distanceFromZero; // Set the distance as the closest
				}
				else // The closest particle has been set for the first time
				{
					if (distanceFromZero < closestParticleDistance) // We have a new closest
					{
						//System.out.println("Set new closest particle");
						closestParticleIndex = i; // Set this particle as the closest
						closestParticleDistance = distanceFromZero; // Set the distance as the closest
					}
				}
				
				//particles.set(i, particle); // Set the index to the new value
			}
			
			if (loopCount % 100000 == 0)
			{
				System.out.println(closestParticleIndex); // Tell the user what the closest particle is
			}
			
			loopCount++; // Increment loop count
		}
	}
	
	private void partTwo() // For part two of the challenge
	{
		BufferedReader puzzleInput = null; // Initalise the variable to store the BufferedReader
		
		try
		{
			puzzleInput = new BufferedReader(new FileReader(FILE_NAME)); // Open the file
		}
		catch (Exception e) // Something went wrong
		{
			e.printStackTrace(); // Print what went wrong
		}
		
		ArrayList<long[][]> particles = new ArrayList<long[][]>(); // Initalise a new arraylist to store the particles
		String line = ""; // Initalise the line variable
		
		// Load the particles
		while (line != null) // While there are lines to read
		{
			try
			{
				line = puzzleInput.readLine(); // Read the next line
			}
			catch (Exception e) // Something went wrong
			{
				e.printStackTrace(); // Print what went wrong
			}
			
			if (line != null) // If there is content in the line
			{
				
				String[] splitLine = line.split(", "); // Split the line at ", "
				
				long[][] particleDetails = new long[3][3]; // Create a new int to store the information about the particle
				
				// Get the position
				particleDetails[0] = parseValues(splitLine[0]); // Parse the position part of the line and store it in the array
				// Get the velocity
				particleDetails[1] = parseValues(splitLine[1]); // Parse the velocity part of the line and store it in the array
				// Get the acceleration
				particleDetails[2] = parseValues(splitLine[2]); // Parse the acceleration part of the line and store it in the array
				
				particles.add(particleDetails); // Add the particle to the array
			}
		}
		
		System.out.println(particles.size() + " particles were loaded!");
		
		// We've now got the particles loaded so we need to run the simulation
		
		
		int loopCount = 0; // Store the count
		
		while (true) // Until the user manually terminates
		{
			// Move each particle
			for (int i = 0; i < particles.size(); i++) // Iterate over each particle
			{
				long[][] particle = particles.get(i); // Get the particle
				
				for (int j = 0; j < 3; j++) // Add each acceleration to the velocity
				{
					particle[1][j] += particle[2][j]; // Add the acceleration to the velocity
				}
				
				for (int j = 0; j < 3; j++) // Add each velocity to the position
				{
					particle[0][j] += particle[1][j]; // Add the velocity to the position
				}
				
			}
			
			// Now we need to check if any particles have collided
			ArrayList<long[][]> particlesToRemove = new ArrayList<long[][]>(); // Create an array list to store the particles to remove
			
			for (long[][] particle : particles) // For each particle
			{
				boolean collision = false; // Store whether there has been a collision
				
				for (long[][] particleToCheckWith : particles) // For every other particle
				{
					if (particle != particleToCheckWith) // If we are not checking a particle with itself
					{
						//System.out.println("Checking for collision");
						
						//System.out.println(Arrays.toString(particle[0]) + " " + Arrays.toString(particleToCheckWith[0]));
						
						if (particlesToRemove.contains(particle)) // If the particle has already been removed
						{
							//System.out.println("contains");
						}
						else if ((Arrays.equals(particle[0], particleToCheckWith[0]))) // If they have the same position and the particle isn't already in the list
						{
							//System.out.println("collision!");
							collision = true; // There has been a collision
							particlesToRemove.add(particle); // Add the particle to the list of particles to remove
						}
					}
				}
				
				if (collision) // If the particle collided with something
				{
					particlesToRemove.add(particle); // Add the particle to the list of particles to remove
				}
			}
			
			for (long[][] particle : particlesToRemove) // For each particle to remove
			{
				particles.remove(particle); // Remove the particle
			}
			
			if (loopCount % 100 == 0)
			{
				System.out.println(particles.size()); // Tell the user how many particles are left
			}
			
			loopCount++; // Increment loop count
		}
	}
	
	private long[] parseValues(String values) // To extract the details from the values of each line
	{
		// The values string will look like this
		// p=<5528,2008,1661>
		// We want the stuff inside the <>
		
		values = values.split("<")[1]; // Get the part of the string after the <
		values = values.replace(">", ""); // Get rid of the ">" at the end
		
		String[] splitValues = values.split(","); // Get the individual values
		
		long[] longValues = new long[3]; // Create a new long[] to store all three values
		
		for (int i = 0; i < longValues.length; i++) // For each string value
		{
			longValues[i] = Long.parseLong(splitValues[i].trim()); // Convert the value to a long and add it to the array
		}
		
		//System.out.println(Arrays.toString(intValues));
		
		return longValues; // Return the array
	}
}