with open("input.txt") as f:
    inputSignal = [int(x) for x in list(f.readline().rstrip())]


basePattern = [0,1,0,-1]

numberOfPhasesToCalculate = 100

'''
for phaseNum in range(numberOfPhasesToCalculate):

    outputList = []


    for i in range(len(inputSignal)):

        # Generate the repeating pattern

        repeatingPattern = [int(item) for number in basePattern for item in [number]*(i+1)]

        #print(repeatingPattern)

        repeatingPattern = repeatingPattern[1:] + repeatingPattern * int(len(inputSignal)/len(repeatingPattern))

        # Calculate element i of the output list

        total = 0

        for j, num in enumerate(inputSignal):
            total += num*repeatingPattern[j]

        outputList.append(int(str(total)[len(str(total))-1]))

    #print("".join([str(x) for x in outputList]))
    print(phaseNum)

    inputSignal = outputList


print("".join([str(x) for x in outputList][:8]))
'''

# PART 2

with open("input.txt") as f:
    inputSignal = [int(x) for x in list(f.readline().rstrip())]*10000

    offSet = int("".join(str(x) for x in inputSignal[:7]))

    print(offSet)

    inputSignal = inputSignal[offSet:]


print(len(inputSignal))
basePattern = [1,0,-1,0]

numberOfPhasesToCalculate = 100


for phaseNum in range(numberOfPhasesToCalculate):

    outputList = []

    total = 0
    for i in range(0, len(inputSignal)):

        # Generate the repeating pattern

        #repeatingPattern = [int(item) for number in basePattern for item in [number]*(i+1)]

        #print(repeatingPattern)

        #repeatingPattern = repeatingPattern * int(len(inputSignal)/len(repeatingPattern))

        #repeatingPattern = [0]*

        # Calculate element i of the output list

        total += inputSignal[len(inputSignal)-1-i]


        outputList.append(int(str(total)[len(str(total))-1]))

    outputList.reverse()
    #print("".join([str(x) for x in outputList]))
    print(phaseNum)

    inputSignal = outputList

print(outputList[:8])


