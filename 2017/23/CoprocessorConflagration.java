import java.io.*;
import java.util.*;

public class CoprocessorConflagration
{
	private final String FILE_NAME = "puzzleInput.txt";
	
	private HashMap<String, Long> registers = new HashMap<String, Long>(); // Create a hashmap to store the register values 
	// even though the register names are a single character I'm storing them as strings for convenience
	
	public static void main(String[] args)
	{
		CoprocessorConflagration cC = new CoprocessorConflagration(); // Create a new instance of the class
		cC.partOne(); // Call the partOne method
	}
	
	private void partOne() // For part one of the challenge
	{
		String[] instructions = loadInstructions(); // Load the instructions
		Long lastPlayedSound = null; // Store the last played sound
		int currentInstruction = 0; // Store the instruction that we are currently on
		
		boolean executing = true; // Store whether we are still executing
		
		long numberOfMultiply = 0; // Track the number of time the multiply instruction in invoked
		
		while (executing) // While we are executing
		{
			String[] instructionBrokenDown = instructions[currentInstruction].split(" "); // Split the instruction at " " to get the individual elements
			
			Long jumpAmount = new Long(1); // Store the amount to jump by
			
			String opcode = instructionBrokenDown[0]; // Get the opcode
			String operand1 = instructionBrokenDown[1]; // Get the first operand
			String operand2 = null; // Initalise a variable to store the value of the second operand
			
			if (instructionBrokenDown.length > 2) // If there is a second opcode
			{
				operand2 = instructionBrokenDown[2]; // Get the second operand
			}
			
			Long operand2Value = getOperandValue(operand2); // Get the value
			
			//System.out.println(opcode + " " + operand1 + " " + operand2Value);
			
			if (opcode.equals("snd")) // If it's the sound instruction
			{
				lastPlayedSound = getRegisterValue(operand1); // Set the last played sound to the value of the operand1 register
			}
			else if (opcode.equals("set")) // If it's the set instruction
			{
				registers.put(operand1, operand2Value); // Store the value of the second register in the first register
			}
			else if (opcode.equals("add")) // If it's the add instruction
			{
				registers.put(operand1, getRegisterValue(operand1) + operand2Value); // Add the values together and store it in the first register
			}
			else if (opcode.equals("sub")) // If it's the subtract instruction
			{
				registers.put(operand1, getRegisterValue(operand1) - operand2Value); // Subtract the value of operand two from operand 1 and store it in operand 1
			}
			else if (opcode.equals("mul")) // If it's the multiply instruction 
			{
				registers.put(operand1, getRegisterValue(operand1) * operand2Value); // Multiply the values of the two registers and store it in the first register
				numberOfMultiply++; // Increment the number of times multiplied
			}
			else if (opcode.equals("mod")) // If it's the modulo instruction
			{
				registers.put(operand1, getRegisterValue(operand1) % operand2Value); // Do register 1 mod register 2 and store the result in register 1
			}
			else if (opcode.equals("rcv")) // If it's the recover instruction
			{
				if (getRegisterValue(operand1) != 0) // If the value isn't 0
				{
					System.out.println("The last played frequency was " + lastPlayedSound);
					executing = false; // Stop executing
				}
			}
			else if (opcode.equals("jnz")) // If it's the jump instruction
			{
				//System.out.println(opcode + " " + operand1 + " " + operand2Value);
				System.out.println(getOperandValue(operand1));
				if (getOperandValue(operand1) != 0) // If the value of the register/number is greater than 0
				{
					System.out.println("Jumped!");
					jumpAmount = operand2Value; // Set the jump amount to the value of operand 2
				}
			}
			
			currentInstruction += jumpAmount; // Increment the current instruction
			
			if (currentInstruction >= instructions.length || currentInstruction < 0) // If we are out of bounds
			{
				System.out.println("Out of bounds");
				executing = false; // We are done executing
			}
			
			/*
			for (String register : registers.keySet()) // Print the registers
			{
				System.out.println(register + ": " + registers.get(register));
			}
			
			System.out.println("-------------------------------");
			*/
		}
		System.out.println(numberOfMultiply);
		
	}
	
	private Long getOperandValue(String operand) // To get the value of an operand whether it be a register or number
	{
		Long operandValue = null; // Store the value of the operand
		try
		{
			operandValue = Long.parseLong(operand); // Try to convert it to a long
		}
		catch (NumberFormatException e) // It must be a register
		{
			//System.out.println("Not a number!");
			operandValue = getRegisterValue(operand); // Get the value stored in the register 
		}
		
		return operandValue; // Return the value
	}
	
	private String[] loadInstructions() // To load the instructions from the file
	{
		String[] instructions = new String[999]; // Create a new String[] to store the instructions read from the file
		
		BufferedReader puzzleInput = null; // Initalise a variable to store the bufferedReader
		
		try
		{
			puzzleInput = new BufferedReader(new FileReader(FILE_NAME)); // Open the file
		}
		catch (Exception e)
		{
			e.printStackTrace(); // Print the error
		}
		
		String line = ""; // Store the current line read from the file
		int currentIndex = 0; // Store the current index in the instructions array
		boolean fileFinished = false; // Store whether we have finished reading the file
		while (fileFinished == false) // While we've not finished reading the file
		{
			try 
			{
				line = puzzleInput.readLine(); // Read the next line from the file
			}
			catch (Exception e)
			{
				e.printStackTrace(); // Print what went wrong
			}
			
			if (line!=null) // If the line isn't empty
			{
				instructions[currentIndex] = line; // Store the line in the array
				currentIndex++; // Increment the current index
			}
			else // The line must be empty and so the file must have finished reading
			{
				fileFinished = true; // Set file finished to true
			}
		}
		
		instructions = Arrays.copyOf(instructions, currentIndex); // Make a copy of the array with all the null elements removed
		return instructions;
	}

	private long getRegisterValue(String register) // To get the register value
	{
		if (registers.get(register) == null) // If the register hasn't been initalised
		{
			//System.out.println("register: " + register + " empty!");
			registers.put(register, new Long(0)); // Store 0 in that register
		}		
		
		return registers.get(register);
	}
}