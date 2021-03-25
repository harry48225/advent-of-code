with open("input.txt") as f:

    inp = [line.strip() for line in f.readlines()]


player_one_deck = []
player_two_deck = []

player_decks = [player_one_deck, player_two_deck]

current_deck_index = -1

for line in inp:

    if "Player" in line:
        current_deck_index += 1

    elif line == "":
        continue

    else:
        player_decks[current_deck_index].append(int(line))


print(player_decks)


# Play the game


while all([len(deck) > 0 for deck in player_decks]):

    # Both players draw the top card

    if player_one_deck[0] > player_two_deck[0]:

        player_one_deck.append(player_one_deck.pop(0))
        player_one_deck.append(player_two_deck.pop(0))

    elif player_one_deck[0] < player_two_deck[0]:

        player_two_deck.append(player_two_deck.pop(0))
        player_two_deck.append(player_one_deck.pop(0))


    print(player_decks)
    
# Print the score of the winner

for deck in player_decks:

    if len(deck) != 0:

        # Calculate score

        score = 0

        for i, card in enumerate(deck):
            print("card: {}, i: {}".format(card, i))
            score += ((len(deck) - i) * card)

        print(score)



# Part 2


# Reset the decks
player_one_deck = []
player_two_deck = []

player_decks = [player_one_deck, player_two_deck]

current_deck_index = -1

for line in inp:

    if "Player" in line:
        current_deck_index += 1

    elif line == "":
        continue

    else:
        player_decks[current_deck_index].append(int(line))


print(player_decks)


print("---- start part 2 ----")

from copy import deepcopy

memos = {'length':50}
lowest_length = 50

def make_round_string(deck_one, deck_two):

    return ",".join([str(card) for card in deck_one]) + "|" + ",".join([str(card) for card in deck_two])


def recursive_combat(deck_one, deck_two):

    # Return True if player one wins, false if player two wins

    rounds = [] # Store the rounds that have already occured

    while len(deck_one) > 0 and len(deck_two) > 0:

        if make_round_string(deck_one, deck_two) in rounds:
            return True
        else:
            rounds.append(make_round_string(deck_one, deck_two))
        # Draw the top card from each deck

        top_card_one = deck_one.pop(0)
        top_card_two = deck_two.pop(0)
        round_winner = True # True if player one wins, False if player two wins
        
        if top_card_one <= len(deck_one) and top_card_two <= len(deck_two):

            if min(len(deck_one), len(deck_two)) < memos['length']:

                memos['length'] = min(len(deck_one), len(deck_two))
                print(memos['length'])

            #print("starting round with deck one: {} deck two: {}".format(deck_one, deck_two))
            round_string = make_round_string(deck_one, deck_two)
            if round_string not in memos.keys():
                memos[round_string] = recursive_combat(deepcopy(deck_one)[:top_card_one], deepcopy(deck_two)[:top_card_two])
            round_winner = memos[round_string]
            #input()


        else:

            round_winner = top_card_one > top_card_two

        
        if round_winner == True: # Player one wins

            deck_one.append(top_card_one)
            deck_one.append(top_card_two)

        else:

            deck_two.append(top_card_two)
            deck_two.append(top_card_one)

    return len(deck_two) == 0 # Return if player one won, they won if they have all the cards


if recursive_combat(player_one_deck, player_two_deck): # If player one wins
    print("Player 1 win")
else:
    print("Player 1 lose")


player_decks = [player_one_deck, player_two_deck]
for deck in player_decks:

    if len(deck) != 0:

        # Calculate score

        score = 0

        for i, card in enumerate(deck):
            print("card: {}, i: {}".format(card, i))
            score += ((len(deck) - i) * card)

        print("part 2:" + str(score))

        
print(player_one_deck)
print(player_two_deck)