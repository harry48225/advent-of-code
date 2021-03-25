with open("testpart2.txt") as f:

    inp = [line.strip() for line in f.readlines()]


rules = {}

i = 0
current_line = inp[i]
while current_line != "":
    current_line = current_line.split(": ")

    rule_number = current_line[0]
    criteria = [part.split(" ") for part in current_line[1].split(" | ")]

    rules[rule_number] = criteria

    i+= 1
    current_line = inp[i]

#print(rules)

messages = inp[i+1:]

#print(messages)
# "string" : {"rule_number" : True/False}

# Generate all valid strings

valid_strings = {}


def generate_valid_strings(rule_number):

    rule = rules[rule_number]

    if rule in [ [['"a"']], [['"b"']] ]:
        #print("end rule")
        if rule == [['"a"']]:
            valid_strings[rule_number] = ["a"]
        elif rule == [['"b"']]:
            valid_strings[rule_number] = ["b"]

    else:

        if rule_number not in valid_strings.keys():
            valid_strings[rule_number] = []

        for rule_set in rule:
            
            valid_strings_for_rule = [""]
            for rule_sub_number in rule_set:

                if not rule_sub_number in valid_strings.keys():
                    generate_valid_strings(rule_sub_number)
                
                new_valid_strings_for_rule = []
                for string in valid_strings[rule_sub_number]:
                    for old_string in valid_strings_for_rule:
                        new_valid_strings_for_rule.append(old_string + string)

                valid_strings_for_rule = new_valid_strings_for_rule

            for string in valid_strings_for_rule:
                valid_strings[rule_number].append(string)
            
generate_valid_strings('0')
#print(valid_strings)

#part 1
#print(valid_strings['0'])
total = 0
for message in messages:

    if message in valid_strings['0']:
        print("{} is valid".format(message))
        total +=1 


print("Part 1: {}".format(total))

# Part 2

# Results in an out of memory error
RULE_DEPTH = 3
rules['8'] = [['42' for _ in range(0, num+1)] for num in range(RULE_DEPTH)] # produces [['42'], ['42', '42'], ['42', '42', '42], ... ]
rules['11'] = [['42' for _ in range(0, num+1)] + ['31' for _ in range(0, num+1)] for num in range(RULE_DEPTH)]

# Generate new valid strings for 0 by considering those for 8 and 11 as 0: 8 11


total = 0
for message in messages:

    if message in valid_strings['0']:
        print("{} is valid".format(message))
        total +=1 

print("Part 2: {}".format(total))