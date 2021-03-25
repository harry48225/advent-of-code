with open("input.txt") as f:
    wire1 = f.readline().rstrip().split(",")
    wire2 = f.readline().rstrip().split(",")


grid = {} # Contains each coordinate as "x,y":[] where the list is which wires are in that tile

def traceWire(wire, wireIdentifier):
    
    steps = 0
    x = 0
    y = 0
    for move in wire:
        direction = move[0] # The first character is the direction
        amount = int(move[1:])

        while amount > 0:
            if direction == "U":
                y += 1
            elif direction == "D":
                y -= 1
            elif direction == "L":
                x -= 1
            elif direction == "R":
                x += 1
            
            steps += 1
            coordinateString = ",".join([str(n) for n in [x,y]])

            if coordinateString not in grid.keys():
                grid[coordinateString] = {}

            if wireIdentifier not in grid[coordinateString].keys():
                grid[coordinateString][wireIdentifier] = steps

            amount -= 1

traceWire(wire1, "1")
traceWire(wire2, "2")


#print(grid)

smallestDistance = 10000000000
leastSteps = 1000000000000

for coordinate in grid.keys():
    #print(grid[coordinate])
    if list(grid[coordinate].keys()) == ["1", "2"]: # If it's an intersection
        
        #print(grid[coordinate])

        

        distance = sum([abs(int(c)) for c in coordinate.split(",")])
        
        # Part one
        if distance < smallestDistance:
            smallestDistance = distance

        steps = grid[coordinate]["1"] + grid[coordinate]["2"]

        if steps < leastSteps:
            leastSteps = steps

print(smallestDistance)
print(leastSteps)


