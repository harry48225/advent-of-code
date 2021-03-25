
# Simulate motion in time steps:
#   Update velocity by applying gravity for all moons
#   Update position by apply velocity for all moons
#   Progress time by 1 step

import numpy as np

with open("input.txt") as f:
    rawMoons = f.readlines()


moons = []
for moon in rawMoons:
    moonData = moon.rstrip().replace("<", "").replace(">", "").split(",")
    

    moonData = [int(l.split("=")[1]) for l in moonData]

    moons.append([moonData, [0,0,0]])



numberOfSteps = 1000


for _ in range(numberOfSteps):
    
    # Update the velocities

    # Consider every pair of moons and apply gravity

    '''
    for moon1 in moons:
        for moon2 in moons:

            if moon1 != moon2:
                # Consider each axis

                for i in range(3):
                    if moon1[0][i] > moon2[0][i]: # If the position of moon 1 is greater than the position of moon 2
                        moon1[1][i] -= 1
                        #moon2[1][i] -= 1
                    elif moon1[0][i] < moon2[0][i]:
                        moon1[1][i] += 1
                        #moon2[1][i] += 1

            else:
                pass
                #print("Matching moon")
    '''

    # sort on x



    # Velocities are now applied
    
    # Apply velocity to position
    
    for moon in moons:

        # Add the velocity of the moon to its own position

        for i in range(3):
            moon[0][i] += moon[1][i]

    #print(moons)
        
# Calculate total energy in the system

# Total energy for a moon is kinetic * potential

total = 0 
for moon in moons:

    potential = sum([abs(c) for c in moon[0]]) # The sum of the absolute values of the moon's position
    kinetic = sum([abs(v) for v in moon[1]]) # The sum of the absolute values of the moon's velocity

    total += potential * kinetic

print(total)

# Part 2

# Work out how long it takes for the moons to go back to an exact previous state

with open("input.txt") as f:
    rawMoons = f.readlines()


moons = []
for moon in rawMoons:
    moonData = moon.rstrip().replace("<", "").replace(">", "").split(",")
    

    moonData = [int(l.split("=")[1]) for l in moonData]

    moons.append([moonData, [0,0,0]])


pastStates = set() # Each state is just a csv of each coordinate and 

def getState(moons): # Creates a state string

    stateStringArray = []

    for moon in moons:

        position = ",".join([str(x) for x in moon[0]])
        velocity = ",".join([str(x) for x in moon[1]])

        stateStringArray.append(",".join([position, velocity]))


    return "|".join(stateStringArray)


startingStates = getState(moons).split("|")

print(startingStates)

lcms = {}
lcmList = []
numSteps = 0
appended = [1,2,3]

'''
while True: #getState(moons) not in pastStates:
    numSteps += 1
    #pastStates.add(getState(moons))
    
    # Update the velocities

    # Consider every pair of moons and apply gravity


    # Pairs
    # 0 1
    # 0 2
    # 0 3
    # 1 2
    # 1 3
    # 2 3

    for tup in [(0,1), (0,2), (0,3), (1,2), (1,3), (2,3)]:

        one, two = tup  

        for i in range(3):


            if moons[one][0][i] > moons[two][0][i]:
                moons[one][1][i] -= 1
                moons[two][1][i] += 1
            elif moons[one][0][i] < moons[two][0][i]:
                moons[one][1][i] += 1
                moons[two][1][i] -= 1

    
    for moon1 in moons[:2]:
        for moon2 in moons:

            if moon1 != moon2:
                # Consider each axis

                for i in range(3):
                    if moon1[0][i] > moon2[0][i]: # If the position of moon 1 is greater than the position of moon 2
                        moon1[1][i] -= 1
                        #moon2[1][i] -= 1
                    elif moon1[0][i] < moon2[0][i]:
                        moon1[1][i] += 1
                        #moon2[1][i] += 1

            else:
                pass
                #print("Matching moon")
    

    # Velocities are now applied
    
    # Apply velocity to position
    
    for moon in moons:

        # Add the velocity of the moon to its own position

        for i in range(3):
            moon[0][i] += moon[1][i]

    # Work out if a single moon is back in its starting state

    states = getState(moons).split("|")
    #print(states)
    for i in appended: # For each axis

        inStarting = True
        for j in range(4): # For each moon

            # If every moon for this axis is in the starting state

            if not(startingStates[j][i+3] == 0 and startingStates[j][i] == states[j][i]): 

                inStarting = False
                
        if inStarting:
            

            if i not in appended:
                lcmList.append(numSteps+1)
                print(lcmList)
                appended.remove(i)
                print(appended)
                #if str(i) not in lcms.keys():
                    #lcms[str(i)] = {}

                #if str(j) not in lcms[str(i)].keys():

                 #   lcms[str(i)][str(j)] = numSteps
                    #lcmList.append(numSteps)
                  #  print(lcms)

                #print(str(i) + " axis: " + str(j) + " in starting place: Steps: " + str(numSteps))



    #print(moons)
    if numSteps % 10000 == 0:

        print(numSteps)

    if len(lcmList) == 3:
        break

'''


# Do each axis individually

def doAxis(moons):

    startingPositions = ",".join([str(moon[0]) for moon in moons])

    numSteps = 0

    while True:
        
        for tup in [(0,1), (0,2), (0,3), (1,2), (1,3), (2,3)]:

            one, two = tup  


            if moons[one][0] > moons[two][0]:
                moons[one][1] -= 1
                moons[two][1] += 1
            elif moons[one][0] < moons[two][0]:
                moons[one][1] += 1
                moons[two][1] -= 1

        # Apply velocity to position
        
        for moon in moons:

            # Add the velocity of the moon to its own position

            moon[0] += moon[1]

        allZero = True

        for moon in moons:

            if moon[1] != 0:
                allZero = False
                break

        if allZero:
            if ",".join([str(moon[0]) for moon in moons]) == startingPositions:
                return numSteps + 1

        numSteps += 1

# Work out lcm

lcmList = []
# For each axis
for i in range(3):

    newMoons = [[moon[0][i], moon[1][i]] for moon in moons]

    lcmList.append(doAxis(newMoons))



print(np.lcm.reduce(lcmList))