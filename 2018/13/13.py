class Cart(object):

    def __init__(self, direction, x, y):
        self.direction = direction
        self.x = x
        self.y = y
        
        self.nextDirectionToTurn = "left"

        self.moved = False

    def move(self, track):

        self.moved = True

        if self.direction == "left":
            self.x -= 1

            currentTile = track[self.y][self.x]
            if currentTile == "\\":
                self.direction = "up"
            elif currentTile == "/":
                self.direction = "down"

        elif self.direction == "right":
            self.x += 1

            currentTile = track[self.y][self.x]
            if currentTile == "\\":
                self.direction = "down"
            elif currentTile == "/":
                self.direction = "up"

        elif self.direction == "up":
            self.y += -1

            currentTile = track[self.y][self.x]
            if currentTile == "\\":
                self.direction = "left"
            elif currentTile == "/":
                self.direction = "right"

        elif self.direction == "down":
            self.y += 1

            currentTile = track[self.y][self.x]
            if currentTile == "\\":
                self.direction = "right"
            elif currentTile == "/":
                self.direction = "left"

        if currentTile == "+":
            self.turn()

    def turn(self):
        if self.nextDirectionToTurn == "left":
            if self.direction == "left":
                self.direction = "down"
            elif self.direction == "right":
                self.direction = "up"
            elif self.direction == "up":
                self.direction = "left"
            elif self.direction == "down":
                self.direction = "right"

            self.nextDirectionToTurn = "none"

            return

        elif self.nextDirectionToTurn == "right":
            if self.direction == "left":
                self.direction = "up"
            elif self.direction == "right":
                self.direction = "down"
            elif self.direction == "up":
                self.direction = "right"
            elif self.direction == "down":
                self.direction = "left"

            self.nextDirectionToTurn = "left"

            return

        elif self.nextDirectionToTurn == "none":
            self.nextDirectionToTurn = "right"

            return

        
def printTrack(track):
    for row in track:
        print("".join(row))

        

with open("puzzleInput.txt") as f:
    pInput = f.readlines()

# Build the track
def buildTrack():

    track = [["" for i in range(len(pInput[0]))] for j in range(len(pInput))]

    for y, row in enumerate(pInput):
        row = row.rstrip()
        for x, char in enumerate(list(row)):
            if char == ">":
                char = "-"
            elif char == "<":
                char = "-"
            elif char == "^":
                char = "|"
            elif char == "v":
                char = "|"
            track[y][x] = char

    return track

track = [["" for i in range(len(pInput[0]))] for j in range(len(pInput))]
carts = []

for y, row in enumerate(pInput):
    row = row.rstrip()
    for x, char in enumerate(list(row)):
        if char == ">":
            carts.append(Cart("right",x,y))
            char = "-"
        elif char == "<":
            carts.append(Cart("left",x,y))
            char = "-"
        elif char == "^":
            carts.append(Cart("up",x,y))
            char = "|"
        elif char == "v":
            carts.append(Cart("down",x,y))
            char = "|"
        track[y][x] = char

'''
# PART 1
printTrack(track)
trackWithCarts = buildTrack()

while True:
    cartSprite = ""
    for y in range(len(track)):
        for x in range(len(track[y])):
            for cart in carts:
                if cart.x == x and cart.y == y and not cart.moved:
                    
                    trackWithCarts[cart.y][cart.x] = track[cart.y][cart.x]

                    cart.move(track)

                    if cart.direction == "up":
                        cartSprite = "^"
                    elif cart.direction == "down":
                        cartSprite = "v"
                    elif cart.direction == "left":
                        cartSprite = "<"
                    elif cart.direction == "right":
                        cartSprite = ">"
                    
                    if trackWithCarts[cart.y][cart.x] in ["v", "^", ">", "<"]: # If there is a cart in the square
                        cartSprite = "X"
                        print(str(cart.x) + "," + str(cart.y))
                        break
                    trackWithCarts[cart.y][cart.x] = cartSprite

                    #printTrack(trackWithCarts)

                    #input()

                    if cartSprite == "X":
                        break
            if cartSprite == "X":
                break
        if cartSprite == "X":
                break

    for cart in carts:
        cart.moved = False

    #printTrack(trackWithCarts)

    if cartSprite == "X":
        break
    #input()

print(str(cart.x) + "," + str(cart.y))
'''

# PART 2

printTrack(track)
trackWithCarts = buildTrack()

while len(carts) > 1:
    cartSprite = ""
    for y in range(len(track)):
        for x in range(len(track[y])):
            for cart in carts:
                if cart.x == x and cart.y == y and not cart.moved:
                    
                    trackWithCarts[cart.y][cart.x] = track[cart.y][cart.x]

                    cart.move(track)

                    if cart.direction == "up":
                        cartSprite = "^"
                    elif cart.direction == "down":
                        cartSprite = "v"
                    elif cart.direction == "left":
                        cartSprite = "<"
                    elif cart.direction == "right":
                        cartSprite = ">"
                    
                    if trackWithCarts[cart.y][cart.x] in ["v", "^", ">", "<"]: # If there is a cart in the square
                        cartSprite = "X"
                        #print(str(cart.x) + "," + str(cart.y))
                        carts.remove(cart)
                        for carti in carts:
                            if carti.x == cart.x and carti.y == cart.y:
                                carts.remove(carti)

                        print(len(carts))

                    trackWithCarts[cart.y][cart.x] = cartSprite

                    #printTrack(trackWithCarts)

                    #input()

    for cart in carts:
        cart.moved = False

    #printTrack(trackWithCarts)

    if len(carts) == 1:
        print(str(carts[0].x) + "," + str(carts[0].y))
        break
    #input()