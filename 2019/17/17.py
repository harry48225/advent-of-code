

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

    videoFeed = [] # Should be a 2d array
    
    currentRow = []
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
         

            program[outAddr] = inp

            currentPosition += 2

        elif opcode == 4: # Outputs the value stored at the parameter
            
            inp = program[currentPosition + 1]

            numberOfParameters = 1

            out = getParameters(program, currentPosition, instruction, numberOfParameters, relativeBase)[0]

            if out == 10: # If it's a new line

                if len(currentRow) != 0:
                    videoFeed.append(currentRow)
                currentRow = []
            else:
                # Convert the number to ascii
                currentRow.append(chr(out))
            
            #print("output: "+  str(out[0]))
            '''
            if instruction[2] == "0": # Position mode
                print(program[inp])
            elif instruction[2] == "1": # Immediate mode
                print(inp)
            '''
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

    return videoFeed


cameraView = runProgram()

for row in cameraView:
    print("".join(row))

# Now we need to find all intersections

# An intersection looks like
#   #
#  ###
#   #
totalAlignment = 0

for i in range(1,len(cameraView)-1):
    for j in range(1,len(cameraView[i])-1):
        if cameraView[i][j] == "#" and cameraView[i-1][j] == "#" and cameraView[i+1][j] == "#" and cameraView[i][j-1] == "#" and cameraView[i][j+1] == "#":
            
            # We have an intersection

            cameraView[i][j] = "O"

            alignmentParameter = i * j

            totalAlignment+= alignmentParameter
            print(alignmentParameter)



for row in cameraView:
    print("".join(row))

print(totalAlignment)


#
#
# PART 2
#
#

# Work out the route needed through the scaffolding

# Robot starts facing ^ find it

for i in range(len(cameraView)):
    for j in range(len(cameraView[i])):
        if cameraView[i][j] == "^":
            robotPos = [i,j]
            break

print(robotPos)
direction = "^"
# Move the robot around and move

def getSquareInFront(robotPos, direction):

    if direction == "^":

        if robotPos[0]-1 < 0:
            return -1
        return cameraView[robotPos[0]-1][robotPos[1]]
    elif direction == "<":
        if robotPos[1]-1 < 0:
            return -1
        return cameraView[robotPos[0]][robotPos[1]-1]
    elif direction == ">":
        if robotPos[1] + 1 >= len(cameraView[robotPos[0]]):
            return -1
        return cameraView[robotPos[0]][robotPos[1]+1]
    elif direction == "v":

        if robotPos[0] + 1 >= len(cameraView):
            return -1
        return cameraView[robotPos[0]+1][robotPos[1]]

def turnLeft(direction):
    if direction == "^":
        return "<"
    elif direction == "<":
        return "v"
    elif direction == ">":
        return "^"
    elif direction == "v":
        return ">"

def turnRight(direction):
    if direction == "^":
        return ">"
    elif direction == "<":
        return "^"
    elif direction == ">":
        return "v"
    elif direction == "v":
        return "<"

def moveForward(robotPos, direction):
    if direction == "^":
        robotPos[0] -= 1
    elif direction == "<":
        robotPos[1] -= 1
    elif direction == ">":
        robotPos[1] += 1
    elif direction == "v":
        robotPos[0] +=1

    return robotPos


# Make sure that there is scaffolding infront of the robot
instructionString = ""

while(True):
    if getSquareInFront(robotPos, direction) != "#":
        if getSquareInFront(robotPos, turnLeft(direction)) == "#":
            direction = turnLeft(direction)
            instructionString += " L"
        elif getSquareInFront(robotPos, turnRight(direction)) == "#":
            direction = turnRight(direction)
            instructionString += " R"

        else:
            print("END")
            break

    movementCounter = 0

    while getSquareInFront(robotPos, direction) in ["#", "O"]:

        robotPos = moveForward(robotPos, direction)

        if cameraView[robotPos[0]][robotPos[1]] != "O":
            cameraView[robotPos[0]][robotPos[1]] = direction


        #for row in cameraView:
        #    print("".join(row))

        #print()
        #print(robotPos)
        #print()
        #input()
        movementCounter += 1
    instructionString += str(movementCounter)

print(instructionString)

# A 
# L,12,L,10,R,8,L,12
A = [76, 44, 49, 50, 44, 76, 44, 49, 48, 44, 82, 44, 56, 44, 76, 44, 49, 50, 10]

# B
# R,8,R,10,R,12
B = [82, 44, 56, 44, 82, 44, 49, 48, 44, 82, 44, 49, 50, 10]

# C
# L,10,R,12,R,8
C = [76, 44, 49, 48, 44, 82, 44, 49, 50, 44, 82, 44, 56, 10]

# Order
# A,B,A,B,C,C,B,A,B,C
order = [65, 44, 66, 44, 65, 44, 66, 44, 67, 44, 67, 44, 66, 44, 65, 44, 66, 44, 67, 10]

inputArray = iter(order + A + B + C + [110, 10])

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
    program[0] = 2 # Wakeup the robot
    #program[2] = initalValue2

    currentPosition = 0

    relativeBase = 0

    videoFeed = [] # Should be a 2d array
    
    currentRow = []
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
            
            inp = next(inputArray) #int(input("Enter a value: "))

            paramMode = instruction[2]

            if paramMode == "0" or paramMode == "1":
                outAddr = program[currentPosition+1]
            elif paramMode == "2":
                #print(paramMode)
                outAddr = program[currentPosition+1] + relativeBase
         

            program[outAddr] = inp

            currentPosition += 2

        elif opcode == 4: # Outputs the value stored at the parameter
            
            inp = program[currentPosition + 1]

            numberOfParameters = 1

            out = getParameters(program, currentPosition, instruction, numberOfParameters, relativeBase)[0]

            '''
            if out == 10: # If it's a new line

                if len(currentRow) != 0:
                    videoFeed.append(currentRow)
                currentRow = []
            else:
                # Convert the number to ascii
                currentRow.append(chr(out))
            '''
            print("output: "+  str(out))
            '''
            if instruction[2] == "0": # Position mode
                print(program[inp])
            elif instruction[2] == "1": # Immediate mode
                print(inp)
            '''
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

    return videoFeed


cameraView = runProgram()
