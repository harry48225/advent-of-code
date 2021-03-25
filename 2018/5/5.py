import string

with open("puzzleInput.txt") as f:
    polymer = list(f.readlines()[0].rstrip())


def reduce(polymer):

    noReduction = False

    while not noReduction:
        
        noReduction = True
        i = 0
        while i <= (len(polymer)-2):
            if polymer[i].lower() == polymer[i+1].lower():
                if polymer[i] != polymer[i+1]:
                    del polymer[i]
                    del polymer[i]
                    noReduction = False

            i+=1
                    
    return polymer

rpolymer = reduce(polymer)

print(rpolymer)
print(len(rpolymer))

# Part 2

letters = list(string.ascii_lowercase)
masterPolymer = polymer

minLength = 1000000000

for letter in letters:
    newPolymer = masterPolymer
    newPolymerString = "".join(newPolymer)
    newPolymerString = newPolymerString.replace(letter, "")
    newPolymerString = newPolymerString.replace(letter.upper(), "")
    newPolymer = list(newPolymerString)

    newPolymer = reduce(newPolymer)

    print(len(newPolymer))

    if len(newPolymer) < minLength:
        minLength = len(newPolymer)

print(minLength)