GRID_SIZE = 10000

class lightPoint():
    def __init__(self, x, y, xVel, yVel):
        self.x = x
        self.y = y
        self.xVel = xVel
        self.yVel = yVel # The y vel in the puzzle input is negative

        print("New point " + str(self.x) + " " + str(self.y) + " vel " + str(self.xVel) + " " + str(self.yVel))
    def doSecond(self):
        self.x += self.xVel
        self.y += self.yVel


def drawPointGrid(lightPoints):
    # Takes the list of the light points
    # and draws them

    anyDrawn = False # Should really be all drawn

    # Take grid[GRID_SIZE/2][GRID_SIZE/2] to be 0,0
    maxX = 0
    maxY = 0
    sumOfCoordinates = 0

    for point in lightPoints:
        x = point.x
        y = point.y

        if abs(x) > maxX:
            maxX = x
        if abs(y) > maxY:
            maxY = y

        sumOfCoordinates += (abs(x) + abs(y))

    if sumOfCoordinates < 100000: #82452:
        print(sumOfCoordinates)
        anyDrawn = True
    
    GRID_SIZE = max(maxX, maxY) * 3
    
    
    if anyDrawn:
        anyDrawn = False
        for point in lightPoints:

            x = int((GRID_SIZE/2) + point.x)
            y = int((GRID_SIZE/2) + point.y)

            if x > 0 and x < GRID_SIZE and y > 0 and y < GRID_SIZE:
                if anyDrawn == False:
                    grid = [["." for i in range(GRID_SIZE)] for j in range (GRID_SIZE)]
                grid[y][x] = "#"
                anyDrawn = True
            else:
                print("breaking")
                anyDrawn = False
                break

        if anyDrawn:
            with open("output.txt", "w") as f:
                for row in grid:
                    f.write("".join(row) + "\n")
                f.close()
    
    return anyDrawn
    

lightPoints = []


with open("puzzleInput.txt") as f:

    for line in f.readlines():
        # We need to extract the position and the velocity

        # Test data
        #position = line[10:16].split(",")
        #velocity = line[28:34].split(",")
        
        position = line[10:24].split(",")
        velocity = line[36:42].split(",")

        lightPoints.append(lightPoint(int(position[0]), int(position[1]), int(velocity[0]), int(velocity[1])))

secondsWaited = 0

while True:
    for point in lightPoints:
        point.doSecond()

    secondsWaited += 1

    if drawPointGrid(lightPoints):
        print("DRAWN")
        print(secondsWaited)
        x = input()

