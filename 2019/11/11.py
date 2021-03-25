

with open("input.txt") as f:
    line = f.readline()


def getParameters(program, currentPosition, instruction, numberOfParameters, relativeBase):

    parameters = []
    for i in range(numberOfParameters):

        paramMode = instruction[2-i]
        if paramMode == "0": # Position mode
            parameters.append(program[program[currentPosition+1+i]])
        elif paramMode == "1": # Immediate mode
            parameters.append(program[currentPosition+1+i])
        elif paramMode == "2": # Relative mode
            actualAddress = relativeBase + program[currentPosition+1+i]

            parameters.append(program[actualAddress])

    return parameters


def getOutAddr(program, currentPosition, relativeBase, instruction):
    paramMode = instruction[0]

    outAddr = -1

    if paramMode == "0" or paramMode == "1":
        outAddr = program[currentPosition]
    elif paramMode == "2":
        #print(paramMode)
        outAddr = program[currentPosition] + relativeBase

    return outAddr

def runProgram():
    
    program = [int(p) for p in line.split(",")] # Split at the delimiter
    program.extend([0 for i in range(100000)])
    #program[1] = initalValue1
    #program[2] = initalValue2

    currentPosition = 0

    relativeBase = 0


    robotPosition = [0,0]
    robotDirection = 0 # 0 for up, 1 for right, 2 for down, 3 for right.
    panelsPainted = set() # Stores the coordinates of all the panels that the robot has painted. Is a set add items using .add

    whitePanels = [] # The panels which are white any panel that isn't in here is black

    # Part 2
    whitePanels.append((0,0))
    colourOutput = True
    
    moves = 0
    instructionsRan = 0
    numOutputs = 0
    while currentPosition < len(program):

        instructionsRan += 1
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
            
            parameters = getParameters(program, currentPosition, instruction, numberOfParameters, relativeBase)

            outaddr = getOutAddr(program, (currentPosition + numberOfParameters + 1), relativeBase, instruction)

                

            program[outaddr] = parameters[0] + parameters[1]

            currentPosition += 4


        elif opcode == 2: # Multiplication
            # Need to get our parameters

            # Multiplication takes 2
            
            numberOfParameters = 2
            
            parameters = getParameters(program, currentPosition, instruction, numberOfParameters, relativeBase)

            outaddr = getOutAddr(program, (currentPosition + numberOfParameters + 1), relativeBase, instruction)

                

            program[outaddr] = parameters[0] * parameters[1]


            currentPosition += 4
            

        elif opcode == 3: # Takes an input and stores it at position
            
            #print(instruction)
            #inp = int(input("Enter a value: "))


            # Inp should be the colour of the tile that the robot is on

            if (robotPosition[0], robotPosition[1]) in whitePanels:
                inp = 1
            else:
                inp = 0
            
            #print(instruction)

            #print(program[currentPosition])
            #print(program[currentPosition+1])
            paramMode = instruction[2]

            if paramMode == '0' or paramMode == '1':
                outAddr = program[currentPosition+1]
            elif paramMode == '2':
                #print(paramMode)
                outAddr = program[currentPosition+1] + relativeBase
            else:
                print(paramMode)
                print(type(paramMode))

            #print(outAddr)

            program[outAddr] = inp

            currentPosition += 2

        elif opcode == 4: # Outputs the value stored at the parameter
            
            numOutputs += 1
            #inp = program[currentPosition + 1]

            numberOfParameters = 1

            #print(instruction)
            #print(program[currentPosition+1])

            out = getParameters(program, currentPosition, instruction, numberOfParameters, relativeBase)[0]

            if colourOutput:
                
                #print("Colour: " + str(out))
                # Paint the panel the 
                # colour given by out

                if out == 0: # If the colour to paint is black
                    if (robotPosition[0], robotPosition[1]) in whitePanels:
                        #print(len(whitePanels))
                        whitePanels.remove((robotPosition[0], robotPosition[1]))
                        #print("Painted black")
                        #print(len(whitePanels))

                elif out == 1: # If the colour to paint is white

                    whitePanels.append((robotPosition[0], robotPosition[1]))

                
                panelsPainted.add((robotPosition[0], robotPosition[1]))

                colourOutput = False
            else:
                
                #print("Turn: " + str(out))
                if out == 0: # Left turn
                    robotDirection = (robotDirection - 1) % 4
                elif out == 1: # Right turn
                    robotDirection = (robotDirection + 1) % 4

                colourOutput = True


                # Move the robot forward

                moves += 1
                if robotDirection == 0: # Up
                    robotPosition[1] += 1
                elif robotDirection == 1: # Right
                    robotPosition[0] += 1
                elif robotDirection == 2: # Down
                    robotPosition[1] -= 1
                elif robotDirection == 3: # Left
                    robotPosition[0] -= 1


            #print("output: "+  str(out[0]))
            
            currentPosition += 2

        elif opcode == 5: # Jump if true

            numberOfParameters = 2
            
            parameters = getParameters(program, currentPosition, instruction, numberOfParameters, relativeBase)

            if parameters[0] != 0:
                currentPosition = parameters[1]
            else:
                currentPosition += 3

        elif opcode == 6: # Jump if false

            numberOfParameters = 2
            
            parameters = getParameters(program, currentPosition, instruction, numberOfParameters, relativeBase)

            if parameters[0] == 0:
                currentPosition = parameters[1]
            else:
                currentPosition += 3

        elif opcode == 7: # Less than

            numberOfParameters = 2

            parameters = getParameters(program, currentPosition, instruction, numberOfParameters, relativeBase)

            outaddr = getOutAddr(program, (currentPosition + numberOfParameters + 1), relativeBase, instruction)

            if parameters[0] < parameters[1]:
                program[outaddr] = 1
            else:
                program[outaddr] = 0

            currentPosition += 4

        elif opcode == 8: # Equals

            numberOfParameters = 2

            parameters = getParameters(program, currentPosition, instruction, numberOfParameters, relativeBase)

            outaddr = getOutAddr(program, (currentPosition + numberOfParameters + 1), relativeBase, instruction)

            if parameters[0] == parameters[1]:
                program[outaddr] = 1
            else:
                program[outaddr] = 0

            currentPosition += 4

        elif opcode == 9: #Adjust relative base

            
            numberOfParameters = 1

            adjustment = getParameters(program, currentPosition, instruction, numberOfParameters, relativeBase)

            relativeBase += adjustment[0]


            currentPosition += 2


        elif opcode == 99: # Terminate
            break

    print(moves)
    print(instructionsRan)
    print(numOutputs)
    print(len(panelsPainted))
    return whitePanels


painted = runProgram()

print(painted)
# 50 x 100 grid


for i in range(-50, 50): # Rows

    row = ""
    for j in range(-100, 100): # Columns

        if (i, j) in painted:
            row += "O"
        else:
            row += "_"


    print(row)