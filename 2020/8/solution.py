with open("input.txt") as f:
    program = [line.strip().rstrip() for line in f.readlines()]



executed = [] # store the line numbers we have executed
program_counter = 0
acc = 0

while True:

    # Fetch
    instruction = program[program_counter].split(" ")

    # Decode
    opcode = instruction[0]
    operand = int(instruction[1])

    # Execute

    if program_counter in executed:
        print(acc)

        break
    
    executed.append(program_counter)

    if opcode == "acc":
        acc += operand
    if opcode == "jmp":

        program_counter += (operand - 1)

    program_counter += 1



# Part 2

def run_program_for_infinite(program):
    executed = [] # store the line numbers we have executed
    program_counter = 0
    acc = 0

    result = None
    while True:

        # Fetch
        instruction = program[program_counter].split(" ")

        # Decode
        opcode = instruction[0]
        operand = int(instruction[1])

        # Execute

        if program_counter in executed:
            break
        executed.append(program_counter)

        if opcode == "acc":
            acc += operand
        if opcode == "jmp":

            program_counter += (operand - 1)

        program_counter += 1

        if program_counter >= len(program):
            result = acc
            break

    return result
    
for line_number, line in enumerate(program):

    new_program = program.copy()

    instruction = line.split(" ")[0]

    if instruction == "jmp":

        new_program[line_number] = line.replace("jmp", "nop")

        result = run_program_for_infinite(new_program)
        if result:

            print("change line: " + line)

            print(result)

            break

    if instruction == "nop":

        new_program[line_number] = line.replace("nop", "jmp")

        result = run_program_for_infinite(new_program)
        if result:

            print("change line: " + line)

            print(result)

            break

    