with open("puzzleInput.txt", 'r') as f:
    words = f.readlines()
    f.close()


# Part 1
doubles = 0
triples = 0

for word in words:
    letters = {}
    for letter in word:
        if letter not in letters.keys():
            letters[letter] = 0
        letters[letter] += 1

    if any(count == 3 for count in letters.values()):
        triples += 1
    if any(count == 2 for count in letters.values()):
        doubles += 1
    
print(triples)
print(doubles)
print(triples * doubles)

# Part 2
for word1 in words: # Check each letter of each word against every other word
    for word2 in words:
        numberOfDifferences = 0
        for i in range(len(word1)):
            if word1[i] != word2[i]:
                numberOfDifferences+=1
                if numberOfDifferences > 1:
                    break
        if numberOfDifferences == 1:
            print(word1)
            print(word2)
            exit()
