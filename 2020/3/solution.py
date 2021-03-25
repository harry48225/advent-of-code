with open('input.txt') as f:

    inp = [list(line.strip()) for line in f.readlines()]

# Part 1
slope = {'right': 3, 'down': 1}

x = 0
y = 0

trees = 0

while y < len(inp):
    if inp[y][x % len(inp[y])] == "#":
        trees += 1

    x += slope['right']
    y += slope['down']

print(trees)

# Part 2

slopes = [{'right': 1, 'down': 1}, {'right': 3, 'down': 1}, {'right': 5, 'down': 1}, {'right': 7, 'down': 1}, {'right': 1, 'down': 2}]

result = 1

for slope in slopes:
    x = 0
    y = 0

    trees = 0

    while y < len(inp):
        if inp[y][x % len(inp[y])] == "#":
            trees += 1

        x += slope['right']
        y += slope['down']

    result *= trees
print(result)