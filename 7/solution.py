with open("input.txt") as f:

    rules_raw = [line.strip().rstrip() for line in f.readlines()]

rules = {}

for rule_raw in rules_raw:
    
    rule_raw = rule_raw.split("bags contain")
    outer_colour = rule_raw[0].strip()

    inner_colours  =[]
    for inner_colour in rule_raw[1].strip().split(","):

        inner_colour = inner_colour.strip().split(" ")

        if inner_colour[0] == "no":
            quantity = 0
        else:
            quantity = int(inner_colour[0])
        colour = inner_colour[1] + " " + inner_colour[2]

        for i in range(quantity):
            inner_colours.append(colour)

    rules[outer_colour] = inner_colours



# Now rules is a dictionary with outerbags as keys and then a list containing which inner bags - with multiplicity


# Part 1
def bag_search(inner_colour, valid_outers):

    # Looks for all the bags which can contain the inner colour

    for outer_colour in rules.keys():

        if inner_colour in rules[outer_colour]:
            #print(inner_colour + " in " + outer_colour)
            if (outer_colour) not in valid_outers:
                valid_outers.append(outer_colour)

            valid_outers = bag_search(outer_colour, valid_outers)

    return valid_outers


number_of_outer_bag_colours = len(bag_search("shiny gold", []))

print(number_of_outer_bag_colours)



# Part 2

def bag_count(bag_colour):
    total = 0
    inner_bags_full = rules[bag_colour]

    #print(bag_colour + " contains: " + (" ".join(inner_bags_full)))
    for bag in inner_bags_full:
        total += 1
        total += bag_count(bag)

    return total


print(bag_count("shiny gold"))

