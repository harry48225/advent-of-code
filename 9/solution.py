def is_sum_of_two_in(target_number, numbers):
    # two pointers

    numbers = sorted(numbers)

    start = 0

    end = len(numbers) - 1

    while start != end:

        current_sum = numbers[start] + numbers[end]

        if current_sum == target_number:
            return True

        elif current_sum > target_number:
            end -= 1

        elif current_sum < target_number:
            start += 1

    return False


with open("input.txt") as f:

    numbers = [int(line.strip().rstrip()) for line in f.readlines()]



# Part 1

previous_numbers = numbers[:25]


for i in range(25, len(numbers), 1):

    current_number = numbers[i]
    #print("target: {0} previous: {1}".format(current_number, previous_numbers))
    if not is_sum_of_two_in(current_number, previous_numbers):

        print(current_number)

        first_invalid = current_number

        break

    previous_numbers.append(current_number)
    previous_numbers.pop(0)


# Part 2

# Find a contigous block of numbers of length at least 2 which sum to first_invalid

culmative = [0] # Memoise the sums

for number in numbers:

    culmative.append(number + culmative[-1])

# Now search

for i in range(len(culmative)-1):
    
    for j in range(i, len(culmative)):

        current_sum = culmative[j] - culmative[i]

        if current_sum == first_invalid:

            range = numbers[i+1:j+1]

            print(max(range) + min(range))

            break

    if current_sum == first_invalid:
        break