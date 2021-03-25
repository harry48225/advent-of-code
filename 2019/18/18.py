import string
import copy
from dijkstar import *

with open("inputylist_id	The Spotify ID for the playlist..txt") as f:
    rawMap = [list(l.rstrip()) for l in f.readlines()]

for row in rawMap:
    print("".join(row))

journeys = set()

shortestPathsToKeys = {}

shortestDistance = 999999
def getKeys(map, journeyLength, keyString):

    global journeys
    global shortestDistance


    if journeyLength >= shortestDistance:
        return

    keys = {}
    doors = {}
    reachablePositions = set()
    #print(map)
    for i in range(len(map)):
        for j in range(len(map[i])):

            currentIcon = map[i][j]

            if currentIcon in list(string.ascii_lowercase):
                keys[currentIcon] = (i,j)
                reachablePositions.add((i,j))
            elif currentIcon in list(string.ascii_uppercase):
                doors[currentIcon] = (i,j)
            elif currentIcon == "@":
                currentPosition = (i,j)
                reachablePositions.add((i,j))
            elif currentIcon == ".":
                reachablePositions.add((i,j))


    #print(str(keys.keys()) + keyString + " " + str(len(keys.keys()) + len(keyString)))
    graph = Graph()

    for coord in reachablePositions:
        
        # There are potentially four coordinates that we can go to

        x = coord[0]
        y = coord[1]

        newCoord = (x+1, y)
        if newCoord in reachablePositions:
            graph.add_edge(str(coord), str(newCoord), 1)

        newCoord = (x-1, y)
        if newCoord in reachablePositions:
            graph.add_edge(str(coord), str(newCoord), 1)

        newCoord = (x, y+1)
        if newCoord in reachablePositions:
            graph.add_edge(str(coord), str(newCoord), 1)

        newCoord = (x, y-1)
        if newCoord in reachablePositions:
            graph.add_edge(str(coord), str(newCoord), 1)

    # Find the closest keys
    reachableKeys = {}
    for key in keys.keys():

        try:
            '''
            if keyString != "" and (keyString[-1] + " " + key) in shortestPathsToKeys.keys():
                reachableKeys[key] = shortestPathsToKeys[keyString[-1] + " " + key]
                #print("cached")
            else:
                '''
            path = find_path(graph, str(currentPosition), str(keys[key]))
            #print(str(key) + ": " + str(path[3]))

            reachableKeys[key] = path[3]
            
        except:
            pass

    # Sort reachableKeys by distance
    for key in reachableKeys.keys():

        #Unlock the door and remove the key

        newMap = copy.deepcopy(map)

        if key.upper() in doors.keys():
            doorLocation = doors[key.upper()]
            newMap[doorLocation[0]][doorLocation[1]] = "."
            
        keyLocation = keys[key]
        newMap[keyLocation[0]][keyLocation[1]] = "@"
        #print("removed " + key)
        # Start from that
        #print(journeyLength + reachableKeys[key])

        newMap[currentPosition[0]][currentPosition[1]] = "."

        #newCurrentPosition = keyLocation

        #print(newMap)
        #input()

        # If we collected the last key
        if len(keys.keys()) == 1:
            journeys.add((journeyLength + reachableKeys[key], keyString + key))
            if journeyLength + reachableKeys[key] < shortestDistance:
                shortestDistance = journeyLength + reachableKeys[key]

                print((journeyLength + reachableKeys[key], keyString + key))

            return

        else:
            getKeys(copy.deepcopy(newMap), journeyLength + reachableKeys[key], keyString + key)
        

#print(graph)
#print(find_path(graph, "0,0", "14,-14")) # Cost here is one too low

# Precalculate the distance between all keys

keys = {}
doors = {}
reachablePositions = set()
#print(map)
map = copy.deepcopy(rawMap)
for i in range(len(map)):
    for j in range(len(map[i])):

        currentIcon = map[i][j]

        if currentIcon in list(string.ascii_lowercase):
            keys[currentIcon] = (i,j)
            reachablePositions.add((i,j))
        elif currentIcon in list(string.ascii_uppercase):
            doors[currentIcon] = (i,j)
            reachablePositions.add((i,j))
        elif currentIcon == "@":
            currentPosition = (i,j)
            reachablePositions.add((i,j))
        elif currentIcon == ".":
            reachablePositions.add((i,j))


#print(keys)
#print(doors)
#input()
graph = Graph()

for coord in reachablePositions:
    
    # There are potentially four coordinates that we can go to

    x = coord[0]
    y = coord[1]

    newCoord = (x+1, y)
    if newCoord in reachablePositions:
        graph.add_edge(str(coord), str(newCoord), 1)

    newCoord = (x-1, y)
    if newCoord in reachablePositions:
        graph.add_edge(str(coord), str(newCoord), 1)

    newCoord = (x, y+1)
    if newCoord in reachablePositions:
        graph.add_edge(str(coord), str(newCoord), 1)

    newCoord = (x, y-1)
    if newCoord in reachablePositions:
        graph.add_edge(str(coord), str(newCoord), 1)


for key in keys:
    for destinationKey in keys:

        if destinationKey != key:
            pathLength = find_path(graph, str(keys[key]), str(keys[destinationKey]))[3]
            shortestPathsToKeys[str(key) + " " + str(destinationKey)] = pathLength

print(shortestPathsToKeys)

getKeys(copy.deepcopy(rawMap), 0, "")

print(journeys)