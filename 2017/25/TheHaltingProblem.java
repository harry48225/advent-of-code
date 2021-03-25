import java.util.*;

public class TheHaltingProblem
{
    int[] tapePositive = new int[22172063]; // For the positive tape values
    int[] tapeNegative = new int[22172063]; // For the negative tape values

    public static void main(String[] args)
    {
        TheHaltingProblem tHP = new TheHaltingProblem(); // Create a new instance of the class
        tHP.partOne(); // Call the part one method
    }

    private void partOne() // For part one of the challenge
    {
        char state = 'A'; // Store the current state

        int currentIndex = 0; // Store the current index
        System.out.println("Starting steps!");
        for (int i = 0; i < 12172063; i++) // Do 12 172 063 steps
        {
            // System.out.println(state);
            // System.out.println(currentIndex);
            if (state == 'A') // If the state is A
            {
                int currentValue = getFromTape(currentIndex); // Get the value in tape
                if (currentValue == 0) // If the current value is 0
                {
                    storeInTape(currentIndex, 1); // Store 1
                    currentIndex += 1; // Move one slot to the right
                    state = 'B'; // Continue with state B
                }
                else if (currentValue == 1) // If the current value is 1
                {
                    storeInTape(currentIndex, 0); // Store 0
                    currentIndex -= 1; // Move one slot to the left
                    state = 'C'; // Continue with state C
                }
            }
            else if (state == 'B') // If the state is B
            {
                int currentValue = getFromTape(currentIndex); // Get the value in tape
                if (currentValue == 0) // If the current value is 0
                {
                    storeInTape(currentIndex, 1); // Store 1
                    currentIndex -= 1; // Move one slot to the left
                    state = 'A'; // Continue with state A
                }
                else if (currentValue == 1) // If the current value is 1
                {
                    storeInTape(currentIndex, 1); // Store 1
                    currentIndex -= 1; // Move one slot to the left
                    state = 'D'; // Continue with state D
                }
            }
            else if (state == 'C') // If the state is C
            {
                int currentValue = getFromTape(currentIndex); // Get the value in tape
                if (currentValue == 0) // If the current value is 0
                {
                    storeInTape(currentIndex, 1); // Store 1
                    currentIndex += 1; // Move one slot to the right
                    state = 'D'; // Continue with state D
                }
                else if (currentValue == 1) // If the current value is 1
                {
                    storeInTape(currentIndex, 0); // Store 0
                    currentIndex += 1; // Move one slot to the right
                    state = 'C'; // Continue with state C
                }
            }
            else if (state == 'D') // If the state is D
            {
                int currentValue = getFromTape(currentIndex); // Get the value in tape
                if (currentValue == 0) // If the current value is 0
                {
                    storeInTape(currentIndex, 0); // Store 0
                    currentIndex -= 1; // Move one slot to the left
                    state = 'B'; // Continue with state B
                }
                else if (currentValue == 1) // If the current value is 1
                {
                    storeInTape(currentIndex, 0); // Store 0
                    currentIndex += 1; // Move one slot to the right
                    state = 'E'; // Continue with state E
                }
            }
            else if (state == 'E') // If the state is E
            {
                int currentValue = getFromTape(currentIndex); // Get the value in tape
                if (currentValue == 0) // If the current value is 0
                {
                    storeInTape(currentIndex, 1); // Store 1
                    currentIndex += 1; // Move one slot to the right
                    state = 'C'; // Continue with state C
                }
                else if (currentValue == 1) // If the current value is 1
                {
                    storeInTape(currentIndex, 1); // Store 1
                    currentIndex -= 1; // Move one slot to the left
                    state = 'F'; // Continue with state F
                }
            }
            else if (state == 'F') // If the state is F
            {
                int currentValue = getFromTape(currentIndex); // Get the value in tape
                if (currentValue == 0) // If the current value is 0
                {
                    storeInTape(currentIndex, 1); // Store 1
                    currentIndex -= 1; // Move one slot to the left
                    state = 'E'; // Continue with state E
                }
                else if (currentValue == 1) // If the current value is 1
                {
                    storeInTape(currentIndex, 1); // Store 1
                    currentIndex += 1; // Move one slot to the right
                    state = 'A'; // Continue with state A
                }
            }

        }

        // We now need to perform the check sum by adding all of the ones
        int checkSum = 0; // Store the checksum

        for (int element : tapePositive) // For each element in the postive tape
        {
            if (element == 1) // If it's a one
            {
                checkSum += 1; // Add one to the check sum
            }
        }

        for (int element : tapeNegative) // For each element in the negative tape
        {
            if (element == 1) // If it's a one
            {
                checkSum += 1; // Add one to the check sum
            }
        }
        System.out.println("The check sum is: " + checkSum); // Print the checkSum
    }

   private void storeInTape(int index, int value) // To store in the tapes
   {

        if (index >= 0) // If it's a positive index
        {
            tapePositive[index] = value; // Store it in the positive tape
        }
        else if (index < 0) // If it's a negative index
        {
            tapeNegative[Math.abs(index)] = value; // Store it in the negative tape
        }
    }

   private int getFromTape(int index) // To get from the tape
    {
        int value = -1; // Store the value gotten from the tape

        if (index >= 0) // If it's a positive index
        {
            value = tapePositive[index]; // Get the value from the positive tape
        }
        else if (index < 0) // If it's a negative index
        {
            value = tapeNegative[Math.abs(index)]; // Get the value from the negative tape
         }

        return value; // Return the value gotten
    }
}
