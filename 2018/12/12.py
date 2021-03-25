with open("puzzleInput.txt") as f:
    pInput = f.readlines()
    f.close()

# Prepare the plant row based on the inital state

initalState = list(pInput[0].rstrip().split(": ")[1])
print("".join(initalState))

plantRow = ["." for i in range((len(initalState) * 2))]

for i in range(len(plantRow)/4, (len(plantRow)/4) + len(initalState)):
    plantRow[i] = initalState[i - (len(plantRow)/4)]


# Load the rules

rulesRaw = pInput[2:]

rules = {}

for rule in rulesRaw:
    rule = rule.rstrip().split(" => ")

    pattern = rule[0]
    result = rule[1]

    rules[pattern] = result

for _ in range(20): # Do 20 generations
    nextGen = ["." for i in range(len(plantRow))]
    for planti in range(2, len(plantRow) - 2): # Don't do the plants that don't have 2 plants either side
        plants = "".join(plantRow[planti-2:planti+3])
        if plants not in rules.keys():
            nextGen[planti] = "."
        else:
            nextGen[planti] = rules[plants]

    plantRow = nextGen


print("".join(plantRow[(len(plantRow)/4):(len(plantRow)/4) + len(initalState)]))

potZero = len(plantRow)/4
total = 0
for i in range(len(plantRow)):
    potNumber = i - potZero

    if plantRow[i] == "#": # If the pot has a plant in it
        total += potNumber

print(total)

# PART TWO

# Reset the row

plantRow = ["." for i in range((len(initalState) * 5))]

for i in range(len(plantRow)/4, (len(plantRow)/4) + len(initalState)):
    plantRow[i] = initalState[i - (len(plantRow)/4)]

lastNumberOfPlants = 0

for genNum in range(200): # Do 50000000000 generations
    print(genNum)

    nextGen = ["." for i in range(len(plantRow))]
    for planti in range(2, len(plantRow) - 2): # Don't do the plants that don't have 2 plants either side
        plants = "".join(plantRow[planti-2:planti+3])
        if plants not in rules.keys():
            nextGen[planti] = "."
        else:
            nextGen[planti] = rules[plants]

    plantRow = nextGen
    with open("output.txt", "a+") as f:
        f.write("".join(plantRow) + "\n")
        f.close()


    potZero = len(plantRow)/4
    numberOfPlants = 0
    total = 0
    for i in range(len(plantRow)):
        potNumber = i - potZero

        if plantRow[i] == "#": # If the pot has a plant in it
            total += potNumber
            numberOfPlants += 1

    if numberOfPlants == lastNumberOfPlants:
        print("SAME NUMBER OF PLANTS ON GEN " + str(genNum))
        print(str(numberOfPlants) + " plants with value: " + str(total))
        

    lastNumberOfPlants = numberOfPlants

print(genNum)
numberOfGensLeft = 50000000000 - genNum - 1

total += (numberOfGensLeft*20) # 20 gets added on each gen. (From analysing the pattern)

print(total)