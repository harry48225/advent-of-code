with open("test.txt") as f:

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
memos = {}
def check_meets_rule(rule_number, message):
    rule = rules[rule_number]

    match = False

    print("current rule: {}".format(rule))
    #input()
    if rule in [ [['"a"']], [['"b"']] ]:
        #print("end rule")
        if rule == [['"a"']] and message == "a":
            match = True
        elif rule == [['"b"']] and message == "b":
            match = True

    # Otherwise we have a list of rules that we need to satisfy
    else:
        print("rule: {}".format(rule))
        for rule_set in rule:

            print("trying rule_set: {}".format(rule_set))
            # Try to partition the message so that it satisfies all the rules
            left_cut = 0
            right_cut = 1
            current_rule_set_index = 0
            while right_cut <= len(message):

                print("checking if (left: {} right: {}) {}, meets rule: {}".format(left_cut, right_cut, message[left_cut:right_cut], rule_set[current_rule_set_index]))
                print(right_cut)

                meets_rule = False

                message_subset =  message[left_cut:right_cut]
                new_rule_number = rule_set[current_rule_set_index]

                if message_subset in memos.keys():

                    if new_rule_number in memos[message_subset].keys():

                        meets_rule = memos[message_subset][new_rule_number]

                    else:

                        meets_rule = check_meets_rule(new_rule_number, message_subset)

                        memos[message_subset][new_rule_number] = meets_rule
                
                else:
                    
                    meets_rule = check_meets_rule(new_rule_number, message_subset)

                    memos[message_subset] = {}
                    memos[message_subset][new_rule_number] = meets_rule


                if meets_rule:
                    # Make a cut now need to change it so that a bigger cut is tried if this doesn't work out
                    #print("{}, meets rule: {}".format(message[left_cut:right_cut], rule_set[current_rule_set_index]))
                
                    left_cut = right_cut
                    right_cut = right_cut + 1
                    current_rule_set_index += 1

                else:
                    right_cut += 1

                if current_rule_set_index >= len(rule_set) - 1:
                    break
           
            if current_rule_set_index == len(rule_set) - 1 and check_meets_rule(rule_set[current_rule_set_index], message[left_cut:]):
                match = True

            if current_rule_set_index >= len(rule_set):
                match = True
            if match:
                break

    return match

total_valid = 0
for message in messages:

    valid = check_meets_rule('0', message)
    print("message: {}, valid: {}".format(message, valid))

    if valid:
        total_valid += 1

print(total_valid)

#322 too low 389 too low, 644 too high

# Part 2

# Replace rules 8 and 11
#input()

RULE_DEPTH = 100
print(rules['8'])
print(rules['11'])
# AOC wants to replce with 8: 42 | 42 8, but we can be explicit - saves the program
rules['8'] = [list(['42' for _ in range(0, num+1)]) for num in range(RULE_DEPTH)] # produces [['42'], ['42', '42'], ['42', '42', '42], ... ]
# Similarly 11 with 11\: 42 31 | 42 11 31
rules['11'] = [['42' for _ in range(0, num+1)] + ['31' for _ in range(0, num+1)] for num in range(RULE_DEPTH)]
#print(rules['8'])
#print(rules['11'])

# Now rerun


memos = {} # Clear
total_valid = 0
for message in messages:

    valid = check_meets_rule('0', message)
    #print("message: {}, valid: {}".format(message, valid))

    if valid:
        total_valid += 1

print(total_valid)