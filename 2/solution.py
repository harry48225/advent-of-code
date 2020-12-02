
INPUT_FILE = "input.txt"

with open(INPUT_FILE, 'r') as f:
    inp = [x.strip() for x in f.readlines()]


# Part one
number_of_valid_passwords = 0
for password_line in inp:

    password_line = password_line.split(":")

    # Extract the rules

    rule = password_line[0].split(" ")
    password = password_line[1].replace(" ", "") # Get rid of white spaces

    target_letter = rule[1]

    raw_range = rule[0].split("-")

    min = int(raw_range[0])
    max = int(raw_range[1])

    print("target character: " + target_letter,  " min: " + str(min) + " max: " + str(max))

    # Count the occurances

    count = 0
    for letter in password:
        if letter == target_letter:
            count += 1

    
    if count >= min and count <= max:
        number_of_valid_passwords += 1

print(number_of_valid_passwords)

# Part two
number_of_valid_passwords = 0
for password_line in inp:

    password_line = password_line.split(":")

    # Extract the rules

    rule = password_line[0].split(" ")
    password = password_line[1].replace(" ", "") # Get rid of white spaces

    target_letter = rule[1]

    raw_range = rule[0].split("-")

    pos_one = int(raw_range[0]) - 1
    pos_two = int(raw_range[1]) - 1

    # Count the occurances

    present_at_pos_one = password[pos_one] == target_letter
    present_at_pos_two = password[pos_two] == target_letter
    # XOR 
    if (present_at_pos_one or present_at_pos_two) and not (present_at_pos_one and present_at_pos_two):
        number_of_valid_passwords += 1
print(number_of_valid_passwords)

