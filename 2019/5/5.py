

with open("input.txt") as f:
    line = f.readline()


def getParameters(program, currentPosition, instruction, numberOfParameters):

    parameters = []
    for i in range(numberOfParameters):

        paramMode = instruction[2-i]
        if paramMode == "0": # Position mode
            parameters.append(program[program[currentPosition+1+i]])
        elif paramMode == "1": # Immediate mode
            parameters.append(program[currentPosition+1+i])

    return parameters


def runProgram():
    
    program = [int(p) for p in line.split(",")] # Split at the delimiter

    #program[1] = initalValue1
    #program[2] = initalValue2

    currentPosition = 0
    
    while currentPosition < len(program):

        '''ABCDE
            1002

            DE - two-digit opcode,      02 == opcode 2
            C - mode of 1st parameter,  0 == position mode
            B - mode of 2nd parameter,  1 == immediate mode
            A - mode of 3rd parameter,  0 == position mode,
                                            omitted due to being a leading zero
        '''
        instruction = str(program[currentPosition]).zfill(5)
        opcode = int(instruction[-2:])

        #print(instruction)

        """  oprand1 = program[program[i+1]]
        oprand2 = program[program[i+2]]
        outaddr = program[i+3] """
        

        if opcode == 1: # Addition
            
            # Need to get our parameters

            # Addition takes 2
            
            numberOfParameters = 2
            
            parameters = getParameters(program, currentPosition, instruction, numberOfParameters)

            outaddr = program[currentPosition + numberOfParameters + 1]

                

            program[outaddr] = parameters[0] + parameters[1]

            currentPosition += 4


        elif opcode == 2: # Multiplication
            # Need to get our parameters

            # Multiplication takes 2
            
            numberOfParameters = 2
            
            parameters = getParameters(program, currentPosition, instruction, numberOfParameters)

            outaddr = program[currentPosition + numberOfParameters + 1]

                

            program[outaddr] = parameters[0] * parameters[1]


            currentPosition += 4
            

        elif opcode == 3: # Takes an input and stores it at position
            
            inp = int(input("Enter a value: "))

            outaddr = program[currentPosition + 1]

            program[outaddr] = inp

            currentPosition += 2

        elif opcode == 4: # Outputs the value stored at the parameter
            
            inp = program[currentPosition + 1]

            if instruction[2] == "0": # Position mode
                print(program[inp])
            elif instruction[2] == "1": # Immediate mode
                print(inp)

            currentPosition += 2

        elif opcode == 5: # Jump if true

            numberOfParameters = 2
            
            parameters = getParameters(program, currentPosition, instruction, numberOfParameters)

            if parameters[0] != 0:
                currentPosition = parameters[1]
            else:
                currentPosition += 3

        elif opcode == 6: # Jump if false

            numberOfParameters = 2
            
            parameters = getParameters(program, currentPosition, instruction, numberOfParameters)

            if parameters[0] == 0:
                currentPosition = parameters[1]
            else:
                currentPosition += 3

        elif opcode == 7: # Less than

            numberOfParameters = 2

            parameters = getParameters(program, currentPosition, instruction, numberOfParameters)

            outaddr = program[currentPosition + numberOfParameters + 1]

            if parameters[0] < parameters[1]:
                program[outaddr] = 1
            else:
                program[outaddr] = 0

            currentPosition += 4

        elif opcode == 8: # Equals

            numberOfParameters = 2

            parameters = getParameters(program, currentPosition, instruction, numberOfParameters)

            outaddr = program[currentPosition + numberOfParameters + 1]

            if parameters[0] == parameters[1]:
                program[outaddr] = 1
            else:
                program[outaddr] = 0

            currentPosition += 4

        elif opcode == 99: # Terminate
            break

    return program[0]


runProgram()
