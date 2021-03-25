from blist import blist # Faster insertion operations as the insert was what was slowing the whole thing down

NUMBER_OF_PLAYERS = 426
LAST_MARBLE = 7205800

playerArray = [0 for i in range(NUMBER_OF_PLAYERS)]
marbleloop = blist([0])

currentMarble = 1
currentMarbleLocation = 0

def placeMarbleInLoop(newMarble):
    # Places a marble between the marbles that
    # are 1 and 2 marbles clockwise of the current marble

    if len(marbleloop) == 1:
        newMarbleLocation = 1
    else:
        newMarbleLocation = (currentMarbleLocation + 2) % (len(marbleloop))

    marbleloop.insert(newMarbleLocation, newMarble)
    return newMarbleLocation

while currentMarble <= LAST_MARBLE:

    for player in range(NUMBER_OF_PLAYERS):

        #print(marbleloop)
        if currentMarble % 23 != 0: # If it's not a multiple of 23
            
            currentMarbleLocation = placeMarbleInLoop(currentMarble)
            #print(currentMarbleLocation)
        
        else: # It's a multiple of 23
            
            playerArray[player] += currentMarble

            locationOfMarbleToRemove = (currentMarbleLocation - 7) % (len(marbleloop))

            scoreToAdd = marbleloop.pop(locationOfMarbleToRemove)

            currentMarbleLocation = locationOfMarbleToRemove

            playerArray[player] += scoreToAdd
        
        currentMarble += 1
        print((currentMarble * 100) / LAST_MARBLE)
        if currentMarble > LAST_MARBLE:
            break

print(max(playerArray))