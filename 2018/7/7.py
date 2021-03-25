with open("puzzleInput.txt") as f:
    data = f.readlines()

instructions = {}

for line in data:
    before = line[5]
    canBegin = line[36]

    if canBegin not in instructions.keys():
        instructions[canBegin] = []

    if before not in instructions.keys():
        instructions[before] = []

    instructions[canBegin] += before

print(instructions)

executed = []
canExecute = []

while len(instructions.keys()) > 0:

    for instruction in instructions:

        # Determine if all of the steps for the instruction have been done
        if instruction not in canExecute and (instructions[instruction] == [] or all(letter in executed for letter in instructions[instruction])):
            canExecute += instruction

    canExecute = sorted(canExecute)

    if len(canExecute) > 0:

        # Execute the first one in the canExecute list

        toExecute = canExecute[0]
        executed += toExecute

        del canExecute[0]

        del instructions[toExecute]
    else:
        break

print("".join(executed))
        
# Part 2

# Regenerate the instructions
instructions = {}

for line in data:
    before = line[5]
    canBegin = line[36]

    if canBegin not in instructions.keys():
        instructions[canBegin] = []

    if before not in instructions.keys():
        instructions[before] = []

    instructions[canBegin] += before

executed = []
canExecute = []
inExecution = []

workers = {'1':[0,'.'], '2':[0,'.'], '3':[0,'.'], '4':[0,'.'], '5':[0,'.']} # Five workers

done = False
timeTaken = 0

while not done:

    for worker in workers.keys():
        
        if workers[worker][0] == 0: # If the worker is free
            
            if workers[worker][1] != '.': # If they are working on something
                executed += workers[worker][1]
                del instructions[workers[worker][1]]
                inExecution.remove(workers[worker][1])
                workers[worker][1] = '.'
    
    for instruction in instructions:

        # Determine if all of the steps for the instruction have been done
        if instruction not in executed and instruction not in inExecution and instruction not in canExecute and (instructions[instruction] == [] or all(letter in executed for letter in instructions[instruction])):
            canExecute += instruction

    canExecute = sorted(canExecute)


    for worker in workers.keys():
        
        if workers[worker][0] == 0: # If the worker is free
                
            if len(canExecute) > 0: # If there are instructions to execute
                instruction = canExecute.pop(0)

                timeToComplete = 60 + ord(instruction) - 64
                workers[worker] = [timeToComplete, instruction] 

                inExecution += instruction

            else:
                workers[worker] = [0, '.']
        
    # Increment the time
    timeTaken += 1

    for worker in workers.values():
        if worker[1] != '.':
            worker[0] -= 1

    print(workers)

    if len(instructions.keys()) == 0:
        done = True

print(timeTaken-1)