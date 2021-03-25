with open ("puzzleInput.txt") as f:
    pInput = f.readlines()
    f.close()

fabric = [[0 for col in range(1000)] for row in range(1000)]
print(fabric)
twoOrMoreClaims = 0

for claim in pInput:
    claim = claim.replace(" ", "")
    split = claim.rstrip().split("@")[1]
    
    coordinates = split.split(":")[0].split(",")
    width = int(split.split(":")[1].split("x")[0])
    height = int(split.split(":")[1].split("x")[1])

    x = int(coordinates[0])
    y = int(coordinates[1])
    print(str(x) + "," + str(y) + " " + str(width) + "x" + str(height))


    for i in range(width):
        for j in range(height):
            #print(str(x + i) + " " + str(y + j))
            fabric[y + j][x + i] += 1
            if (fabric[y + j][x + i] == 2):
                twoOrMoreClaims += 1

   # print(fabric)

#print(fabric)
print(twoOrMoreClaims)

totalClaims = 0
for row in fabric:
    for item in row:
        if item >= 2:
            totalClaims+=1

print(totalClaims)


# Go back over the claims and look for a claim with all ones
for claim in pInput:
    claim = claim.replace(" ", "")
    split = claim.rstrip().split("@")[1]
    cid = claim.rstrip().split("@")[0]
    coordinates = split.split(":")[0].split(",")
    width = int(split.split(":")[1].split("x")[0])
    height = int(split.split(":")[1].split("x")[1])

    x = int(coordinates[0])
    y = int(coordinates[1])
    print(cid + " " + str(x) + "," + str(y) + " " + str(width) + "x" + str(height))

    noOverlap = True

    for i in range(width):
        for j in range(height):
            #print(str(x + i) + " " + str(y + j))
            if fabric[y + j][x + i] != 1:
                noOverlap = False
                break

    if noOverlap:
        print(cid)
        exit()


