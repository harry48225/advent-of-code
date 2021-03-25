

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
    program.extend([0 for i in range(10000)])
    #program[1] = initalValue1
    #program[2] = initalValue2

    currentPosition = 0

    relativeBase = 0


    screen = {}

    outputNumber = 0
    
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
            
            inp = int(input("Enter a value: "))

            paramMode = instruction[2]

            if paramMode == "0" or paramMode == "1":
                outAddr = program[currentPosition+1]
            elif paramMode == "2":
                #print(paramMode)
                outAddr = program[currentPosition+1] + relativeBase
         

            program[outaddr] = inp

            currentPosition += 2

        elif opcode == 4: # Outputs the value stored at the parameter
            
            inp = program[currentPosition + 1]

            numberOfParameters = 1

            out = getParameters(program, currentPosition, instruction, numberOfParameters, relativeBase)

            if outputNumber == 0: # It's the x coordinate
                x = out[0]
            elif outputNumber == 1: # It's the y coordinate
                y = out[0]
            elif outputNumber == 2: # It's the tile type
                tileType = out[0]

                # Add to the screen dict

                screen[",".join([str(x),str(y)])] = tileType
            
            outputNumber = (outputNumber + 1) % 3

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

    return screen


screen = runProgram()

# Count the number of blocks

# Blocks have number 2


total = 0
for blockType in screen.values():

    if blockType == 2:
        total += 1

print(total)



# Part 2


def drawScreen(screen):
    
    for i in range(25):

        row = ""
        for j in range(50):
            if str(j) + "," + str(i) in screen.keys():
                
                block = screen[str(j) + "," + str(i)]

                if block == 1:
                    row += "|"

                elif block == 2:
                    row += "#"
                elif block == 3:
                    row += "="
                elif block == 4:
                    row += "o"
                else:
                    row += " "

        print(row)


program = [int(p) for p in line.split(",")] # Split at the delimiter
program.extend([0 for i in range(10000)])
program[0] = 2
#program[2] = initalValue2

currentPosition = 0

relativeBase = 0


outputNumber = 0


score = 0

ballx = -1
paddlex = -1
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

        #drawScreen(screen)

        inp = -3

        '''
        while inp not in [0,1,-1]:
            try:
                inp = int(input("Enter a value: "))
            except:
                pass
        '''

        if ballx < paddlex:
            inp = -1
        elif ballx > paddlex:
            inp = 1

        else:
            inp = 0

        #input()
        paramMode = instruction[2]

        if paramMode == "0" or paramMode == "1":
            outAddr = program[currentPosition+1]
        elif paramMode == "2":
            #print(paramMode)
            outAddr = program[currentPosition+1] + relativeBase
        #print("outAddr value: " + str(program[outAddr]))
        #print(inp)
        #print(outAddr)
        program[outAddr] = inp

        #print("outAddr value right after input: " + str(program[outAddr]))

        currentPosition += 2

    elif opcode == 4: # Outputs the value stored at the parameter
        
        inp = program[currentPosition + 1]

        numberOfParameters = 1

        out = getParameters(program, currentPosition, instruction, numberOfParameters, relativeBase)

        if outputNumber == 0: # It's the x coordinate
            x = out[0]
        elif outputNumber == 1: # It's the y coordinate
            y = out[0]
        elif outputNumber == 2: # It's the tile type
            
            
            tileType = out[0]

            #print(str(x) + "," + str(y) + "," + str(tileType))
            # Add to the screen dict

            if not (x == -1 and y == 0):
                screen[",".join([str(x),str(y)])] = tileType

                if tileType == 3: # If it's the paddle

                    paddlex = x

                if tileType == 4: # If it's the ball

                    ballx = x

            else:
                
                score = tileType

                print("Score: " + str(score))

            
        
        outputNumber = (outputNumber + 1) % 3

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

 