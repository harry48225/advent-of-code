

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


def runProgram(phase, inputValue):
    
    program = [int(p) for p in line.rstrip().split(",")] # Split at the delimiter

    #program[1] = initalValue1
    #program[2] = initalValue2

    currentPosition = 0
    
    inputNumber = 0

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
            
            if inputNumber == 0:
                inp = phase
            elif inputNumber == 1:
                inp = inputValue

            else:
                inp = int(input("Enter a value: "))

            outaddr = program[currentPosition + 1]

            program[outaddr] = inp

            inputNumber += 1
            currentPosition += 2

        elif opcode == 4: # Outputs the value stored at the parameter
            
            inp = program[currentPosition + 1]

            if instruction[2] == "0": # Position mode
                return program[inp]
            elif instruction[2] == "1": # Immediate mode
                return inp

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



highestOutput = 0

# Part 1
for i in range(5):
    for j in range(5):
        for k in range(5):
            for l in range(5):
                for m in range(5):

                    if len([i,j,k,l,m]) == len(list(set([i,j,k,l,m]))): # If they're all different
                        amp1 = runProgram(i, 0)
                        amp2 = runProgram(j, amp1)
                        amp3 = runProgram(k, amp2)
                        amp4 = runProgram(l, amp3)
                        amp5 = runProgram(m, amp4)

                        if amp5 > highestOutput:
                            highestOutput = amp5
                            print(amp5)
                            print(" ".join([str(n) for n in [i,j,k,l,m]]))

                    

# Part 2

class Amplifier():
    
    
    def __init__(self, phase):
        self.phase = phase
        self.program = [int(p) for p in line.rstrip().split(",")] # Split at the delimiter
        self.currentPosition = 0
        self.inputNumber = 0

    def runProgram(self, inputValue):
    

        phase = self.phase

        #program[1] = initalValue1

        while self.currentPosition < len(self.program):

            '''ABCDE
                1002

                DE - two-digit opcode,      02 == opcode 2
                C - mode of 1st parameter,  0 == position mode
                B - mode of 2nd parameter,  1 == immediate mode
                A - mode of 3rd parameter,  0 == position mode,
                                                omitted due to being a leading zero
            '''
            instruction = str(self.program[self.currentPosition]).zfill(5)
            opcode = int(instruction[-2:])

            #print(instruction)

            """  oprand1 = self.program[self.program[i+1]]
            oprand2 = self.program[self.program[i+2]]
            outaddr = self.program[i+3] """
            
            

            if opcode == 1: # Addition
                
                # Need to get our parameters

                # Addition takes 2
                
                numberOfParameters = 2
                
                parameters = getParameters(self.program, self.currentPosition, instruction, numberOfParameters)

                outaddr = self.program[self.currentPosition + numberOfParameters + 1]

                    

                self.program[outaddr] = parameters[0] + parameters[1]

                self.currentPosition += 4


            elif opcode == 2: # Multiplication
                # Need to get our parameters

                # Multiplication takes 2
                
                numberOfParameters = 2
                
                parameters = getParameters(self.program, self.currentPosition, instruction, numberOfParameters)

                outaddr = self.program[self.currentPosition + numberOfParameters + 1]

                    

                self.program[outaddr] = parameters[0] * parameters[1]


                self.currentPosition += 4
                

            elif opcode == 3: # Takes an input and stores it at position
                
                if self.inputNumber == 0:
                    inp = phase
                else:
                    inp = inputValue

                outaddr = self.program[self.currentPosition + 1]

                self.program[outaddr] = inp

                self.inputNumber += 1
                self.currentPosition += 2

            elif opcode == 4: # Outputs the value stored at the parameter
                

                inp = self.program[self.currentPosition + 1]

                self.currentPosition += 2

                if instruction[2] == "0": # Position mode
                    
                    return self.program[inp]
                elif instruction[2] == "1": # Immediate mode
                    return inp

                

            elif opcode == 5: # Jump if true

                numberOfParameters = 2
                
                parameters = getParameters(self.program, self.currentPosition, instruction, numberOfParameters)

                if parameters[0] != 0:
                    self.currentPosition = parameters[1]
                else:
                    self.currentPosition += 3

            elif opcode == 6: # Jump if false

                numberOfParameters = 2
                
                parameters = getParameters(self.program, self.currentPosition, instruction, numberOfParameters)

                if parameters[0] == 0:
                    self.currentPosition = parameters[1]
                else:
                    self.currentPosition += 3

            elif opcode == 7: # Less than

                numberOfParameters = 2

                parameters = getParameters(self.program, self.currentPosition, instruction, numberOfParameters)

                outaddr = self.program[self.currentPosition + numberOfParameters + 1]

                if parameters[0] < parameters[1]:
                    self.program[outaddr] = 1
                else:
                    self.program[outaddr] = 0

                self.currentPosition += 4

            elif opcode == 8: # Equals

                numberOfParameters = 2

                parameters = getParameters(self.program, self.currentPosition, instruction, numberOfParameters)

                outaddr = self.program[self.currentPosition + numberOfParameters + 1]

                if parameters[0] == parameters[1]:
                    self.program[outaddr] = 1
                else:
                    self.program[outaddr] = 0

                self.currentPosition += 4

            elif opcode == 99: # Terminate

                return -1 # Return -1 to signify a halt
                break

        return self.program[0]


print("---------------PART TWO---------------------")
highestOutput = 0
for i in range(5, 10):
    for j in range(5, 10):
        for k in range(5, 10):
            for l in range(5, 10):
                for m in range(5, 10):
                    #print(i)
                    if len([i,j,k,l,m]) == len(list(set([i,j,k,l,m]))): # If they're all different
                        #print("Trying: " + " ".join([str(n) for n in [i,j,k,l,m]]))
                        amp1 = Amplifier(i)
                        amp2 = Amplifier(j)
                        amp3 = Amplifier(k)
                        amp4 = Amplifier(l)
                        amp5 = Amplifier(m)

                        amps = [amp1, amp2, amp3, amp4, amp5]

                        # Run the amplifier loop until we get a halt

                        

                        inp = amps[0].runProgram(0)
                        numHalts = 0

                        currentAmp = 1
                        while numHalts < 5:
                            
                            inp = amps[currentAmp].runProgram(inp)
                            #print(inp)
                            if inp == -1:
                                numHalts += 1
                                #print("HALTTTT")
                            if currentAmp == 4:
                                #print(inp)
                                if inp > highestOutput:

                                    highestOutput = inp
                                    print(inp)
                                    print(" ".join([str(n) for n in [i,j,k,l,m]]))
                            
                            currentAmp += 1
                            currentAmp = currentAmp % 5

                        