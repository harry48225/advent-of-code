with open("input.txt") as f:

    starting_numbers = [num.strip() for num in f.readline().split(",")]


number_log = {}
most_recent_number = -1
turn_number = 1

def say_number(number, turn_number):

    number = str(number)

    if number not in number_log.keys():
        number_log[number] = {'times_spoken': 0, 'last_spoken': -1, 'second_last_spoken': -1}

    number_log[number] = {'times_spoken': number_log[number]['times_spoken'] + 1, 'last_spoken': turn_number, 'second_last_spoken': number_log[number]['last_spoken']}

for num in starting_numbers:

    number_log[num] = {'times_spoken': 1, 'last_spoken': turn_number, 'second_last_spoken': -1}

    turn_number += 1

    most_recent_number = num

while turn_number <= 2020:

    #print(most_recent_number)
    if number_log[most_recent_number]['times_spoken'] == 1:
        # Say 0

        say_number('0', turn_number)
        most_recent_number = '0'

    else:

        difference = str(int(number_log[most_recent_number]['last_spoken']) - int(number_log[most_recent_number]['second_last_spoken']))

        # Say difference

        say_number(difference, turn_number)
        most_recent_number = difference


    turn_number += 1

    #input()

print(most_recent_number)

# Part 2
# Not the best, takes ~ 30 seconds not a big deal

with open("input.txt") as f:

    starting_numbers = [num.strip() for num in f.readline().split(",")]


number_log = {}
most_recent_number = -1
turn_number = 1

def say_number(number, turn_number):

    number = str(number)

    if number not in number_log.keys():
        number_log[number] = {'times_spoken': 0, 'last_spoken': -1, 'second_last_spoken': -1}

    number_log[number] = {'times_spoken': number_log[number]['times_spoken'] + 1, 'last_spoken': turn_number, 'second_last_spoken': number_log[number]['last_spoken']}

for num in starting_numbers:

    number_log[num] = {'times_spoken': 1, 'last_spoken': turn_number, 'second_last_spoken': -1}

    turn_number += 1

    most_recent_number = num

while turn_number <= 30000000:

    # Progress report
    if (turn_number % 100000 == 0):
        print(int(turn_number/300000))

        
    #print(most_recent_number)
    if number_log[most_recent_number]['times_spoken'] == 1:
        # Say 0

        say_number('0', turn_number)
        most_recent_number = '0'

    else:

        difference = str(int(number_log[most_recent_number]['last_spoken']) - int(number_log[most_recent_number]['second_last_spoken']))

        # Say difference

        say_number(difference, turn_number)
        most_recent_number = difference


    turn_number += 1

    #input()

print(most_recent_number)