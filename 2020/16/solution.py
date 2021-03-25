def parse_ticket(ticket):

    return [int(x) for x in ticket.split(",")]

with open("input.txt") as f:

    inp = [line.strip() for line in f.readlines()]


# Read the rules

rules = {}
inp = iter(inp)
line = next(inp)
while line != "":
    #print(line)
    line = line.split(": ")

    name = line[0]

    ranges = []
    for raw_range in line[1].split(" or "):

        raw_range = raw_range.split("-")
        start = int(raw_range[0])
        end = int(raw_range[1])
        ranges.append(range(start, end+1)) # +1 needed as python ranges are end exclusive

    rules[name] = ranges

    line = next(inp) 

print(rules)
while line != "your ticket:":
    line = next(inp)

line = next(inp)

my_ticket = parse_ticket(line)

print(my_ticket)

while line != "nearby tickets:":
    line = next(inp)

nearby_tickets = []

line = next(inp)
while True:

    nearby_tickets.append(parse_ticket(line))

    try:
        line = next(inp)    
    except StopIteration:
        break
    

#print(nearby_tickets)

not_valid_sum = 0

valid_tickets = []
for ticket in nearby_tickets:
    ticket_valid = True
    for field in ticket:

        valid = False
        for range_set in rules.values():

            #print([(field in valid_range) for valid_range in range_set])
            if any([(field in valid_range) for valid_range in range_set]):
                
                valid = True

                break

        if not valid:
            not_valid_sum += field
            ticket_valid = False

    if ticket_valid:
        valid_tickets.append(ticket)
    
#print(not_valid_sum)

# Part 2

field_valid_indices = {}

for field_name in rules.keys():

    field_valid_indices[field_name] = []

for rule in rules.keys():
    for i in range(len(valid_tickets[0])):

        field_valid = True
        for ticket in valid_tickets:
            
            field = ticket[i]
            #print([(field in valid_range) for valid_range in range_set])
            if not any([(field in valid_range) for valid_range in rules[rule]]):
                
                field_valid = False

                break

            if not field_valid:
                break

        if field_valid:
            field_valid_indices[rule].append(i)

print(field_valid_indices)

field_indices = {}

# Yep its a mess... but it works ...
while len(field_indices.keys()) != len(rules.keys()):

    for field_index in range((len(field_valid_indices.keys()))):

        field = list(field_valid_indices.keys())[field_index]
        if len(field_valid_indices[field]) == 1:

            final_index = field_valid_indices[field][0]
            print("assigning: " + str(final_index))
            field_indices[field] = final_index

            for potential_indices in field_valid_indices.values():

                if final_index in potential_indices:
                    potential_indices.remove(final_index)

            #print(field_valid_indices)

            break


print(field_indices)

total = 1
for field in field_indices:

    if "departure" in field:
        print("yes")
        total *= my_ticket[field_indices[field]]


print(total)
# 37050 too low