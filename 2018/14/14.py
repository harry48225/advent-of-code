from blist import blist

class Elf(object):
    
    def __init__(self):
        self.currentRecipe = -1
        self.currentIndex = -1

    def giveRecipe(self, recipe, index):
        self.currentRecipe = recipe
        self.currentIndex = index

def makeRecipe(elves):
    recipeTotal = 0
    for elf in elves:
        recipeTotal += elf.currentRecipe
    newRecipes = [int(x) for x in list(str(recipeTotal))]
    return newRecipes
        
'''
scoreBoard = [3,7]
elves = [Elf(), Elf()]

for i in range(len(scoreBoard)):
    elves[i].giveRecipe(scoreBoard[i], i)

NUMBER_OF_RECIPES = 360781
numberMade = 0
while numberMade < (NUMBER_OF_RECIPES + 10):

    # Make new recipes
    newRecipes = makeRecipe(elves)
    numberMade += len(newRecipes)

    for recipe in newRecipes:
        scoreBoard.append(recipe)

    for elf in elves:
        recipeIndex = ((elf.currentIndex + elf.currentRecipe + 1) % len(scoreBoard))
        elf.giveRecipe(scoreBoard[recipeIndex], recipeIndex)

print("".join(str(x) for x in scoreBoard[-12:-2]))
'''
# PART 2

scoreBoard = blist([3,7])
#elves = [Elf(), Elf()]

#for i in range(len(scoreBoard)):
#    elves[i].giveRecipe(scoreBoard[i], i)

elf1 = 0
elf2 = 1
RECIPE_SEQUENCE = str(360781)
RECIPE_SEQUENCE_LENGTH = len(RECIPE_SEQUENCE)
numberMade = 0
scoreBoardString = "37"
while 1:

    # Make new recipes
    scoreBoardString += str(int(scoreBoardString[elf1]) + int(scoreBoardString[elf2]))
    #numberMade = len(scoreBoard) - 2

    elf1 = int(((elf1 + int(scoreBoardString[elf1]) + 1) % len(scoreBoardString)))
    elf2 = int(((elf2 + int(scoreBoardString[elf2]) + 1) % len(scoreBoardString)))

    if RECIPE_SEQUENCE in scoreBoardString[-7:]:
        print("TOTAL")
        print(scoreBoardString.index(RECIPE_SEQUENCE))
        break

    #print(scoreBoard)
    #print(numberMade + 2)

'''
    if "".join(str(x) for x in scoreBoard[-RECIPE_SEQUENCE_LENGTH:]) == RECIPE_SEQUENCE:
        
        break
        '''

