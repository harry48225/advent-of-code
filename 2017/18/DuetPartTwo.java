import java.io.*;
import java.util.*;

public class DuetPartTwo
{
	private final String FILE_NAME = "puzzleInput.txt";
	
	// even though the register names are a single character I'm storing them as strings for convenience
	
	public static void main(String[] args)
	{
		DuetPartTwo d = new DuetPartTwo(); // Create a new instance of the class
		d.partTwo(); // Call the partTwo method
	}
	
	private void partTwo() // For part two of the challenge
	{
		// Create two new executors
		Executor executor1 = new Executor(0);
		Executor executor2 = new Executor(1);
		
		executor2.valueQueue = new LinkedList<Long>(executor1.execute()); // Send values
		
		while (true) // While the value queues aren't empty i.e. deadlock hasn't happened
		{
			if (executor2.valueQueue.size() > 0 && executor2.executing) // If there are values in its value queue and it is executing
			{
				System.out.println("Program 2");
				//for (long value : executor2.valueQueue)
				//{
				//	System.out.println(value);
				//}
				executor1.valueQueue = new LinkedList<Long>(executor2.execute()); // Send values
				System.out.println("Program 2 end" + " " + executor1.valueQueue.size());
				System.out.println(executor1.valueQueue);
			}
			if (executor1.valueQueue.size() > 0 && executor1.executing)  // If there are values in its value queue and it is executing
			{
				System.out.println("Program 1");
				executor2.valueQueue =  new LinkedList<Long>(executor1.execute()); // Send values
				System.out.println("Program 1 end"+ " " + executor2.valueQueue.size());
				System.out.println(executor2.valueQueue);
			}
			
			if (!(executor2.valueQueue.size() > 0 && executor2.executing || executor1.valueQueue.size() > 0 && executor1.executing)) 
			{
				break;
			}
		}
		
		System.out.println("Deadlock!");
		System.out.println("Program 0 sent " + executor1.numberOfValuesSent + " values!");
		System.out.println("Program 1 sent " + executor2.numberOfValuesSent + " values!");
	}
	
	private class Executor // Executor class
	{
		private String[] instructions = null; // Array to store the instructions
		private int currentInstruction = 0; // Store the instruction that we are currently on
		private HashMap<String, Long> registers = new HashMap<String, Long>(); // Create a hashmap to store the register values 
		
		public boolean executing = true; // Store whether we are still executing
		public int numberOfValuesSent = 0; // Store the number of values sent
		public LinkedList<Long> valueQueue = new LinkedList<Long>(); // Create an queue to store the values recieved
		
		public Executor(int programId) // Constructor
		{
			registers.put("p", new Long(programId));
			instructions = loadInstructions(); // Load the instructions
		}
		
		public LinkedList<Long> execute() // Execute
		{
			LinkedList<Long> valuesToSend = new LinkedList<Long>(); // Create a new queue to store the values to send
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
				
				Long operand2Value = null; // Store the value of the second operand
				try
				{
					operand2Value = Long.parseLong(operand2); // Try to convert it to a long
				}
				catch (NumberFormatException e) // It must be a register
				{
					//System.out.println("Not a number!");
					operand2Value = getRegisterValue(operand2); // Get the value stored in the register 
				}
				
				//System.out.println(opcode + " " + operand1 + " " + operand2Value);
				
				if (opcode.equals("snd")) // If it's the send instruction
				{
					// Check if its a value or a character
					try
					{
						valuesToSend.add(Long.parseLong(operand1)); // Try to send it as an integer
					}
					catch (Exception e) // Something went wrong, it must be character
					{
						valuesToSend.add(getRegisterValue(operand1)); // Send the value stored at that integer
					}
					//System.out.println("Sent value!");
					numberOfValuesSent++; // Increment the number of values sent

				}
				else if (opcode.equals("set")) // If it's the set instruction
				{
					registers.put(operand1, operand2Value); // Store the value of the second register in the first register
				}
				else if (opcode.equals("add")) // If it's the add instruction
				{
					registers.put(operand1, getRegisterValue(operand1) + operand2Value); // Add the values together and store it in the first register
				}
				else if (opcode.equals("mul")) // If it's the multiply instruction 
				{
					registers.put(operand1, getRegisterValue(operand1) * operand2Value); // Multiply the values of the two registers and store it in the first register
				}
				else if (opcode.equals("mod")) // If it's the modulo instruction
				{
					registers.put(operand1, getRegisterValue(operand1) % operand2Value); // Do register 1 mod register 2 and store the result in register 1
				}
				else if (opcode.equals("rcv")) // If it's the recover instruction
				{
					//System.out.println(valueQueue.size());
					if (! valueQueue.isEmpty())
					{
						registers.put(operand1, valueQueue.removeFirst()); // Get the first value in the value queue and store it in the register specifed
					}
					else
					{
						System.out.println("Queue empty");
						break;
					}
					System.out.println(valueQueue);
				}
				else if (opcode.equals("jgz")) // If it's the jump instruction
				{
					try
					{
						if (Integer.parseInt(operand1) > 0) // If the value is greater than 0
						{
							jumpAmount = operand2Value; // Set the jump amount to the value of operand 2
						}
					}
					catch (Exception e)
					{
						if (getRegisterValue(operand1) > 0) // If the value is greater than 0
						{
							jumpAmount = operand2Value; // Set the jump amount to the value of operand 2
						}
					}
				}
				
				currentInstruction += jumpAmount; // Increment the current instruction
					
				if (currentInstruction >= instructions.length || currentInstruction < 0) // If we are out of bounds
				{
				
					System.out.println("Out of bounds");
					executing = false; // We are done executing
				}
				//for (String register : registers.keySet()) // Print the registers
				//{
				//	System.out.println(register + ": " + registers.get(register));
				//}
				
				//System.out.println("-------------------------------");
			}
			
			System.out.println(valuesToSend.size());
			return valuesToSend;
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
}