SERIAL_NUMBER = 8561

def calculatePowerLevel(x,y):
    
    rackID = x + 10
    powerLevel = rackID * y
    powerLevel += SERIAL_NUMBER
    powerLevel *= rackID
    # Need to get just the 100s digit
    
    if powerLevel >= 100:
        powerLevel = int(str(powerLevel)[-3])
    else:
        powerLevel = 0

    powerLevel -= 5

    return powerLevel


def calculateEntireGrid():
    grid = [[0 for i in range(300)] for j in range(300)]

    for x in range(300):
        for y in range(300):
            grid[y][x] = calculatePowerLevel(x+1,y+1)

    return grid

grid = calculateEntireGrid()

maxTotalPower = 0
maxX = 0
maxY = 0

for y in range (1,301-2): # 1 to 300
    for x in range (1,301-2):
        
        # Add together the power levels of the 3x3 grid with the top left as the current coordinate
        
        totalPower = 0

        for yi in range(y, y+3):
            for xi in range(x, x+3):
                #totalPower += calculatePowerLevel(xi,yi)
                totalPower += grid[yi-1][xi-1]
        if totalPower > maxTotalPower:
            maxTotalPower = totalPower
            maxX = x
            maxY = y


print(str(maxX) + "," + str(maxY) + " " + str(maxTotalPower))

# PART TWO

maxTotalPower = 0
maxX = 0
maxY = 0
maxSize = 0


for y in range (1,301): # 1 to 300
    print(y)
    for x in range (1,301):
        #print(x)
        # Add together the power levels of the nxn grid with the top left as the current coordinate
        
        totalPower = 0
        for size in range(1, min(301-x, 301-y)):

            xRow = x + size - 1
            yColumn = y + size - 1

            # Add all of the powers in the column up to the y coordinate
            for yi in range(y, y+size):
                totalPower += grid[yi-1][xRow-1]

            # Add all of the powers in the row up to the x coordinate
            for xi in range(x, x+size):
                totalPower += grid[yColumn-1][xi-1]

            totalPower -= grid[yColumn-1][xRow-1] # Remove double counting

            if totalPower > maxTotalPower:
                maxTotalPower = totalPower
                maxX = x
                maxY = y
                maxSize = size


print(str(maxX) + "," + str(maxY) + " " + str(maxTotalPower) + " Size:" + str(maxSize))
