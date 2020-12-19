with open("input.txt") as f:

    homework = [line.strip() for line in f.readlines()]


def evaluate_chunk(chunk):

    # First look for brackets
    total = 0
    i = 0
    current_number = ''

    current_operation = ''
    
    operands = []
    while i < len(chunk):
        current_character = chunk[i]
        #print("current_char: " + current_character)
        if current_character in [str(digit) for digit in range(10)]:
            current_number += current_character
        
        if current_character == " " or i == (len(chunk) - 1):
            if current_number != "":
                operands.append(int(current_number))
                current_number = ""

        elif current_character in ["*", "+"]:
            current_operation = current_character
        
        elif current_character == "(":
            bracket_stack = ["("]
            sub_chunk = ""

            while len(bracket_stack) > 0:
                i += 1
                sub_chunk += chunk[i]
                if chunk[i] == "(":
                    bracket_stack.append("(")
                elif chunk[i] == ")":
                    bracket_stack.pop()

            sub_chunk = sub_chunk[:-1]
            #print("evaluating sub_chunk: " + sub_chunk)
            operands.append(evaluate_chunk(sub_chunk))
        i += 1

        if len(operands) == 2:
            
            #print(operands)
            #print(current_operation)
            if current_operation == "+":

                total = (operands[0] + operands[1])

            elif current_operation == "*":

                total = (operands[0] * operands[1])

            operands = [total]

            #print("results in: {}".format(operands))

    #print("before return operands: {}".format(operands))

    if total == 0 and len(operands) == 1:
        total = operands[0]
    return total

total = 0
for line in homework:
    total += evaluate_chunk(line)

print(total)

# Part 2

# Change of precedence addition before multiplication
# Tweak input to put brackets around additions?

new_homework = []

for line in homework:
    #input()
    #print(line)

    new_line = line
    i = 0
    while i < len(new_line):
        
        char = new_line[i]
        #print("i: {}, char: {}".format(i, char))
        if char == "+":

            for direction in [-1 , 1]:
                #print("doing direction: {}".format(direction))
                #print("i is {} and gives character: {}".format(i, new_line[i]))
                current_index = i + (2 * direction) # Skip over the space
                
                current_character = new_line[current_index]
                #print("starting index is {} and gives character: {}, for line: {}".format(current_index, current_character, new_line))
                if current_character in [str(num) for num in range(10)]:

                    while current_index < len(new_line) and current_index >= 0 and current_character in [str(num) for num in range(10)]:
                        
                        current_index += direction

                        if current_index == len(new_line):
                            break
                        current_character = new_line[current_index]

                    insertion_index = current_index - direction

                elif current_character in [")" , "("]:

                    if current_character == ")":
                        open_bracket = ")"
                        close_bracket = "("
                    else:
                        open_bracket = "("
                        close_bracket = ")"
                    
                    bracket_stack = [open_bracket]

                    while len(bracket_stack) > 0:
                        current_index += direction
                        if new_line[current_index] == close_bracket:
                            bracket_stack.pop()
                        elif new_line[current_index] == open_bracket:
                            bracket_stack.append(close_bracket)

                    #print("bracket_end at char: {}, index: {}".format(new_line[current_index], current_index))
                    insertion_index = current_index - direction

                # Insert into the line
                #print("inserting for direction: {}".format(direction))
                if direction == -1:
                    insertion_bracket = "("
                    i += 1
                else:
                    insertion_bracket = ")"
                    insertion_index += 1

                #print("inserting {} at index {}".format(insertion_bracket, insertion_index))
                #print("before: {}".format(new_line))
                listy_line = list(new_line)
                listy_line.insert(insertion_index, insertion_bracket)
                
                new_line = "".join(listy_line)

                #print("after: {}".format(new_line))
                #input()

        i += 1

    #print("inital line: " + line)
    #print("finished line: " + new_line)
    #print("evalutes to: {}".format(evaluate_chunk(new_line)))

    new_homework.append(new_line)


total = 0
for line in new_homework:

    total += evaluate_chunk(line)

print(total)