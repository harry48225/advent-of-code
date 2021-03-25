
INPUT_FILE = "input.txt"

with open(INPUT_FILE, 'r') as f:
    inp = sorted([int(x) for x in f.readlines()])


# Part one
# Two pointers technique

start = 0
end = len(inp) - 1

sum = -1

TARGET_SUM = 2020

while sum != TARGET_SUM:

    sum = inp[start] + inp[end]

    if sum < TARGET_SUM:
        start += 1
    elif sum > TARGET_SUM:
        end -= 1

print(inp[start] * inp[end])


# Part two

# Find three numbers which sum to 2020
# Modified two pointers


def twoPointers(start, end, target):

    sum = -1

    TARGET_SUM = target

    while sum != TARGET_SUM and start < end:

        sum = inp[start] + inp[end]

        if sum < TARGET_SUM:
            start += 1
        elif sum > TARGET_SUM:
            end -= 1

    if (sum == target):
        return (inp[start] * inp[end])
    
    return -1


sum = -1

TARGET_SUM = 2020

for i in range(len(inp) - 2):

    result = twoPointers(i + 1, len(inp) - 1, TARGET_SUM - inp[i])

    if result != -1:
        print(inp[i] * result)

    
