import operator

grid = [["" for i in range(500)] for j in range(500)]

with open("puzzleInput.txt") as f:
    points = f.readlines()


# Part 1

areas = {}

for rowNum, row in enumerate(grid):
    for colNum, col in enumerate(row):
        closest = 1000000
        closetNumber = ""

        for pointNumber, point in enumerate(points):
            splitPoint = point.split(", ")
            x = int(splitPoint[0])
            y = int(splitPoint[1])
            
            xDistance = abs(colNum - x)
            yDistance = abs(rowNum - y)

            distance = xDistance + yDistance

            if distance < closest:
                closest = distance
                closetNumber = str(pointNumber)
            elif distance == closest:
                closetNumber = "."

        grid[rowNum][colNum] = closetNumber 
        
        if closetNumber not in areas.keys():
            areas[closetNumber] = 0

        # If the area is infinite we don't want to include it
        if rowNum == 0 or colNum == 0 or rowNum == len(grid) - 1 or colNum == len(grid[0]) -1:
            areas[closetNumber] = -1

        if areas[closetNumber] != -1:
            areas[closetNumber] += 1

print(areas)
print(sorted(areas.items(), key=operator.itemgetter(1)))

# Part 2

numberMeetingDistanceToAllCoordinates = 0

for rowNum, row in enumerate(grid):
    for colNum, col in enumerate(row):
        
        totalDistance = 0

        for pointNumber, point in enumerate(points):
            splitPoint = point.split(", ")
            x = int(splitPoint[0])
            y = int(splitPoint[1])
            
            xDistance = abs(colNum - x)
            yDistance = abs(rowNum - y)

            distance = xDistance + yDistance

            totalDistance += distance
        if totalDistance < 10000:
            numberMeetingDistanceToAllCoordinates += 1

print(numberMeetingDistanceToAllCoordinates)