with open("input.txt") as f:
    

    groups = []
    current_group = {'size': 0}
    for line in f.readlines():

        response = line.strip().rstrip()

        if response != "":
            current_group['size'] += 1
            for question in response:

                if question in current_group.keys():

                    current_group[question] += 1
                
                else:
                    current_group[question] = 1
        else:
            groups.append(current_group)
            current_group = {'size': 0}

    if current_group != {}:
        groups.append(current_group)

# Part 1
total = 0
for group in groups:

    total += len(group.keys())

print(total)

# Part 2
total = 0
for group in groups:

    for question in group.keys():

        
        if question != 'size' and group[question] == group['size']:
            total += 1

print(total)

