

with open("input.txt") as f:
    line = f.readline()


def runProgram(initalValue1, initalValue2):
    program = [int(p) for p in line.split(",")] # Split at the delimiter

    program[1] = initalValue1
    program[2] = initalValue2

    for i in range(0, int(len(program)), 4):

        opcode = program[i]
        oprand1 = program[program[i+1]]
        oprand2 = program[program[i+2]]
        outaddr = program[i+3]
        

        if opcode == 1: # Addition
            program[outaddr] = oprand1 + oprand2
        elif opcode == 2: # Multiplication
            program[outaddr] = oprand1 * oprand2
        elif opcode == 99: # Terminate
            break

    return program[0]

#Part 1

print(runProgram(12,2))


# Part 2

for i in range(100):
    for j in range(100):
        if runProgram(i,j) == 19690720:
            print(i)
            print(j)
            print("answer: " + str(100*i + j))