import java.io.*; 
import java.util.HashMap;

public class JumpInstructions
{
	private final String FILE_NAME = "puzzleInput.txt";
	
	private HashMap<String, Integer> registerContents = new HashMap<String, Integer>(); // Store the contents of each register
	
	public static void main(String[] args) // Main method
	{
		JumpInstructions jI = new JumpInstructions(); // Create a new instance of the class
		jI.startProgram(); // Call the start program method
	}
	
	private void startProgram() // Start program method
	{
		partTwo(); // Call the partTwo method
	}
	
	private void partOne() // For part one of the challenge
	{
		BufferedReader puzzleInput = null; // Initalise puzzleInput
		try
		{
			puzzleInput = new BufferedReader(new FileReader(FILE_NAME)); // Open the file
		}
		catch (Exception e)
		{
			System.out.println("Something went wrong opening the file! "); // Tell the user that something went wrong
			e.printStackTrace();
		}
		
		boolean linesToRead = true; // Store whether there are more lines to read in the file
		String currentInstruction = ""; // Initalise the current instruction variable
		int numberOfLinesRead = 0;
		while (linesToRead == true) // While there are still lines to read in the file
		{
			try 
			{
				currentInstruction = puzzleInput.readLine(); // Read the next line from the file
			}
			catch (Exception e)
			{
				System.out.println("Something went wrong reading the file!");
			}
			
			if (currentInstruction == null) // If the line is empty
			{
				linesToRead = false;
				break; // Go to the top of the loop and so break
			}
			String[] currentInstructionBrokendown = currentInstruction.split("if"); // Split the instruction up into the opcode and condition
			String[] registerOpcodeAndOperand = currentInstructionBrokendown[0].split(" "); // split the start of the instruction into the opcode and operand
			
			String currentRegister = registerOpcodeAndOperand[0];
			String opcode = registerOpcodeAndOperand[1];
			Integer operand = Integer.parseInt(registerOpcodeAndOperand[2]);
			
			String[] conditionBrokendown = currentInstructionBrokendown[1].trim().split(" "); // Split the condition by space to get the three component parts.
			String conditionRegister = conditionBrokendown[0];
			String conditionCheck = conditionBrokendown[1];
			Integer conditionValue = Integer.parseInt(conditionBrokendown[2]);

			System.out.println("cR: " + currentRegister + " opcode: " + opcode + " operand: " + operand + " condR: " + conditionRegister + "(" + getContentsOfRegister(conditionRegister) + ")" + " condCheck: " + conditionCheck +  " condValue: " + conditionValue);
			
			// Check condition
			boolean shouldExecuteInstruction = checkCondition(conditionRegister, conditionCheck, conditionValue); // Check the condition and store it in shouldExecuteInstruction
			if (shouldExecuteInstruction == true) // If the instruction should be executed
			{	
				System.out.println("Executed!");
				//System.out.println(getContentsOfRegister(conditionRegister) + " " + conditionCheck + " " + conditionValue);
				executeInstruction(currentRegister, opcode, operand); // Execute the instruction
				System.out.println(getContentsOfRegister(currentRegister));
			}
			
			numberOfLinesRead++;
		}
		System.out.println("Read " + numberOfLinesRead + " lines!");
		Integer highestValue = null; // Store the highest value
		String highestValueName = null; // Store the name of the register with the highest value
		for (String register : registerContents.keySet()) // Iterate over the registers
		{
			System.out.println(register + " has value: " + getContentsOfRegister(register));
			
			if (highestValue == null) // If there is no highest value
			{
				highestValue = registerContents.get(register);
				highestValueName = register;
			}
			
			if ((int) registerContents.get(register) > (int) highestValue) // If we have a new highest value
			{
				highestValue = registerContents.get(register);
				highestValueName = register;
			}
		}
		
		System.out.println(highestValueName + " had the highest value which was: " + highestValue);
		
	}
	
	private Integer getContentsOfRegister(String registerName) // Gets the contents of the register from the hashmap registerContents
	{
		if (registerContents.containsKey(registerName) == false) // If the register isn't in the hashmap
		{
			System.out.println(registerName + " is not contained!");
			registerContents.put(registerName, (Integer) 0); // Put the register in the hashmap with value 0
		}
		
		return registerContents.get(registerName);
	}
	
	private Boolean checkCondition(String register, String check, int value) // To check the conditions
	{
		int registerValue = (int) getContentsOfRegister(register); // Get the contents of the register
		// There are 6 different conditions
		// > < >= <= == !=
		Boolean returnValue = null; // Initalise the return value
		if (check.equals(">"))
		{
			returnValue = (registerValue > value); // Perform the check
		}
		else if (check.equals(">="))
		{
			returnValue = (registerValue >= value); // Perform the check
		}
		else if (check.equals("<"))
		{
			returnValue = (registerValue < value); // Perform the check
		}
		else if (check.equals("<="))
		{
			returnValue = (registerValue <= value); // Perform the check
		}
		else if (check.equals("=="))
		{
			returnValue = (registerValue == value); // Perform the check
		}
		else if (check.equals("!="))
		{
			returnValue = (registerValue != value); // Perform the check
		}
		else
		{
			System.out.println("UNDEFINED CONDITION CHECK");
		}
		return returnValue;
	}	

	private void executeInstruction(String execRegister, String execOpcode, Integer execOperand) // Method to perform the instruction
	{
		Integer registerValue = getContentsOfRegister(execRegister); // Get the contents of the register
		// There are only 2 opcodes
		// inc and dec
		
		if (execOpcode.equals("inc")) // If we need to increment
		{
			System.out.println("Increasing " + registerValue + " by " + execOperand);
			registerValue += execOperand; // Perform the instruction
			System.out.println(registerValue);
			
		}
		else if (execOpcode.equals("dec")) // If we need to decrement
		{
			System.out.println("Decreasing " + registerValue + " by " + execOperand);
			registerValue -= execOperand; // Perform the instruction
			System.out.println(registerValue);
		}
		else
		{
			System.out.println("UNDEFINED OPCODE!");
		}
		registerContents.put(execRegister, registerValue); // Store the new value in the hashmap
		//System.out.println(getContentsOfRegister(register));
		
	}
	
	private void partTwo() // For part two of the challenge
	{
		BufferedReader puzzleInput = null; // Initalise puzzleInput
		try
		{
			puzzleInput = new BufferedReader(new FileReader(FILE_NAME)); // Open the file
		}
		catch (Exception e)
		{
			System.out.println("Something went wrong opening the file! "); // Tell the user that something went wrong
			e.printStackTrace();
		}
		
		boolean linesToRead = true; // Store whether there are more lines to read in the file
		String currentInstruction = ""; // Initalise the current instruction variable
		int numberOfLinesRead = 0;
		Integer highestValueEver = null; // Store the highest ever value
		while (linesToRead == true) // While there are still lines to read in the file
		{
			try 
			{
				currentInstruction = puzzleInput.readLine(); // Read the next line from the file
			}
			catch (Exception e)
			{
				System.out.println("Something went wrong reading the file!");
			}
			
			if (currentInstruction == null) // If the line is empty
			{
				linesToRead = false;
				break; // Go to the top of the loop and so break
			}
			String[] currentInstructionBrokendown = currentInstruction.split("if"); // Split the instruction up into the opcode and condition
			String[] registerOpcodeAndOperand = currentInstructionBrokendown[0].split(" "); // split the start of the instruction into the opcode and operand
			
			String currentRegister = registerOpcodeAndOperand[0];
			String opcode = registerOpcodeAndOperand[1];
			Integer operand = Integer.parseInt(registerOpcodeAndOperand[2]);
			
			String[] conditionBrokendown = currentInstructionBrokendown[1].trim().split(" "); // Split the condition by space to get the three component parts.
			String conditionRegister = conditionBrokendown[0];
			String conditionCheck = conditionBrokendown[1];
			Integer conditionValue = Integer.parseInt(conditionBrokendown[2]);

			System.out.println("cR: " + currentRegister + " opcode: " + opcode + " operand: " + operand + " condR: " + conditionRegister + "(" + getContentsOfRegister(conditionRegister) + ")" + " condCheck: " + conditionCheck +  " condValue: " + conditionValue);
			
			// Check condition
			boolean shouldExecuteInstruction = checkCondition(conditionRegister, conditionCheck, conditionValue); // Check the condition and store it in shouldExecuteInstruction
			if (shouldExecuteInstruction == true) // If the instruction should be executed
			{	
				System.out.println("Executed!");
				//System.out.println(getContentsOfRegister(conditionRegister) + " " + conditionCheck + " " + conditionValue);
				executeInstruction(currentRegister, opcode, operand); // Execute the instruction
				System.out.println(getContentsOfRegister(currentRegister));
			}
			
			if (highestValueEver == null) // If the highest value ever hasn't been set yet
			{
				highestValueEver = getContentsOfRegister(currentRegister); // Set it as the value of the current register
			}
			else if ((int) getContentsOfRegister(currentRegister) > (int)highestValueEver) // If we have a new highest
			{
				highestValueEver = getContentsOfRegister(currentRegister);
			}
			
			numberOfLinesRead++;
		}
		System.out.println("Read " + numberOfLinesRead + " lines!");
		Integer highestValue = null; // Store the highest value
		String highestValueName = null; // Store the name of the register with the highest value
		for (String register : registerContents.keySet()) // Iterate over the registers
		{
			System.out.println(register + " has value: " + getContentsOfRegister(register));
			
			if (highestValue == null) // If there is no highest value
			{
				highestValue = registerContents.get(register);
				highestValueName = register;
			}
			
			if ((int) registerContents.get(register) > (int) highestValue) // If we have a new highest value
			{
				highestValue = registerContents.get(register);
				highestValueName = register;
			}
		}
		
		System.out.println(highestValueName + " had the highest value which was: " + highestValue);
		System.out.println("The highest value ever was " + highestValueEver);
			
	}
}