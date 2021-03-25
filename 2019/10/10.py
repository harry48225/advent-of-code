from fractions import gcd
from math import atan2, pi

with open("input.txt") as f:
    inputF = f.readlines()



asteroidsGrid = []

asteroids = []

for x, line in enumerate(inputF):
    line = line.rstrip()

    line = list(line)

    row = []

    for y, char in enumerate(line):

        row.append(char == "#") # Put true where asteroids are and false otherwise

        if char == "#":
            asteroids.append([x,y]) # Add the asteroids coordinates to the grid

    asteroidsGrid.append(row)

#print(asteroids)


# Idea: iterate over the asteroids then
# iterate over the other asteroids and work out which ones have a direct line of sight to the current asteroid
# I.e. work out whether the two asteroids are colinear with another


bestSeen = 0
bestAsteroid = [-1, -1]

for asteroid in asteroids:


    amountSeen = 0
    for target in asteroids:

        if target[0] != asteroid[0] or target[1] != asteroid[1]: # If it's not the same asteroid
            
            parallelVec = [asteroid[0] - target[0], asteroid[1] - target[1]] # This points from the target to the asteroid

            #print(parallelVec)
            # We need to make it so that x and y are the smallest possible integers.
            hcf = abs(gcd(parallelVec[0], parallelVec[1]))

            parallelVec[0] = int(parallelVec[0]/hcf)
            parallelVec[1] = int(parallelVec[1]/hcf)

            
            # Look for other asteroids also on this line

            currentPostion = [target[0], target[1]]
            
            currentPostion[0] += parallelVec[0]
            currentPostion[1] += parallelVec[1]
            
            lineOfSight = True

            while currentPostion != [asteroid[0], asteroid[1]]:


                if asteroidsGrid[currentPostion[0]][currentPostion[1]]:

                    lineOfSight = False

                currentPostion[0] += parallelVec[0]
                currentPostion[1] += parallelVec[1]

            
            if lineOfSight:
                amountSeen += 1


    if amountSeen > bestSeen:
        bestSeen = amountSeen
        bestAsteroid = asteroid

print(bestAsteroid)
print(bestSeen)


def getSeenAsteroids(asteroids, asteroidGrid, asteroid):


    asteroidsSeen = []
    for target in asteroids:

        if target[0] != asteroid[0] or target[1] != asteroid[1]: # If it's not the same asteroid
            
            parallelVec = [asteroid[0] - target[0], asteroid[1] - target[1]] # This points from the target to the asteroid

            #print(parallelVec)
            # We need to make it so that x and y are the smallest possible integers.
            hcf = abs(gcd(parallelVec[0], parallelVec[1]))

            parallelVec[0] = int(parallelVec[0]/hcf)
            parallelVec[1] = int(parallelVec[1]/hcf)

            
            # Look for other asteroids also on this line

            currentPostion = [target[0], target[1]]
            
            currentPostion[0] += parallelVec[0]
            currentPostion[1] += parallelVec[1]
            
            lineOfSight = True

            while currentPostion != [asteroid[0], asteroid[1]]:


                if asteroidsGrid[currentPostion[0]][currentPostion[1]]:

                    lineOfSight = False

                currentPostion[0] += parallelVec[0]
                currentPostion[1] += parallelVec[1]

            
            if lineOfSight:

                angle = atan2(parallelVec[0],-parallelVec[1])

                print(str(-parallelVec[1]) + "," + str(parallelVec[0]) + " " + " Coord: " + str(target))
                if 0 <= angle <= pi/2:
                    angle = pi/2 - angle
                elif pi/2 < angle <= pi:
                    angle = (2*pi - angle) + pi/2
                elif angle < 0:
                    angle = pi/2 - angle


                
               
                asteroidsSeen.append([[target[0],target[1]], angle]) # Append the coordinates of the asteroid and its parallel vec


    return asteroidsSeen

# Part two


destroyed = 0

while destroyed < 200:

    toDestroy = getSeenAsteroids(asteroids, asteroidsGrid, bestAsteroid) # Get the asteroids that are in line of sight


    # Sort by angle

    swapped = True

    while swapped == True:

        swapped = False

        for i in range(len(toDestroy)-1):
            if toDestroy[i][1] > toDestroy[i+1][1]: # Compare angles
                temp = toDestroy[i]
                toDestroy[i] = toDestroy[i+1]
                toDestroy[i+1] = temp

                swapped = True


    print(toDestroy)

    for ast in toDestroy:
        destroyed += 1
        print(ast)

        asteroidsGrid[ast[0][0]][ast[0][1]] = False # Remove from the grid

        total = 0
        for row in asteroidsGrid:
            for astr in row:
                if astr:
                    total += 1

        #print(total)

        #print(asteroids)
        if destroyed == 200:

            print("*********")
            print(ast)
            print("*********")

    #print(asteroids)

    print("------------------------")

    
        




