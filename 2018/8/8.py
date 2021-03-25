with open("puzzleInput.txt") as f:
    data = [int(x) for x in "".join(f.readlines()).split(" ")]

def parseData(startIndex):

    print("New node finder started")

    metaDataSum = 0
    endIndex = startIndex
    i = startIndex+2

    print(data[startIndex])

    if startIndex < len(data)-1:

        numberOfNodesToFind =data[startIndex]
        numberOfMetadata = data[startIndex + 1] 
        # Recusively finds the nodes
        

        while i < len(data)-1:
            if numberOfNodesToFind == 0:
                # Find the metadata
                for j in range(i, numberOfMetadata+i):
                    metaDataSum += data[j]
                    print("Found metadata " + str(data[j]))
                i=j
                break
            else:
                if data[i] >= 0:
                    i, newMetadataToAdd = parseData(i)
                    numberOfNodesToFind -= 1
                    metaDataSum+=newMetadataToAdd
                    i -= 1

            i+=1

    print("Node finder quit" +  " " + str(i+1))
    return i+1, metaDataSum


#print(parseData(0))

# Part 2
nodeValues = {}
def doNodeValues(startIndex):

    print("New node finder started")

    metaDataSum = 0
    endIndex = startIndex
    i = startIndex+2

    print(data[startIndex])
    childNodes = []

    if startIndex < len(data)-1:

        numberOfNodesToFind =data[startIndex]
        numberOfMetadata = data[startIndex + 1] 
        initalNumberOfNodesToFind = data[startIndex]
        # Recusively finds the nodes
        

        while i < len(data):
            if initalNumberOfNodesToFind == 0:
                for j in range(i, numberOfMetadata+i):
                    metaDataSum += data[j]
                    print("Found metadata " + str(data[j]))
                i=j
                break
            if numberOfNodesToFind == 0:
                # Find the metadata
                for j in range(i, numberOfMetadata+i):
                    
                    if data[j] - 1 < len(childNodes) and data[j] != 0:
                        print("Adding child " + str(data[j]) + " " + str(childNodes[data[j] - 1]))
                        metaDataSum += childNodes[data[j] - 1]
                i=j
                break
            else:
                if data[i] >= 0:
                    i, newMetadataToAdd = doNodeValues(i)
                    numberOfNodesToFind -= 1
                    childNodes.append(newMetadataToAdd)
                    i -= 1

            i+=1

    print("Node finder quit" +  " " + str(metaDataSum))
    return i+1, metaDataSum


print(doNodeValues(0))