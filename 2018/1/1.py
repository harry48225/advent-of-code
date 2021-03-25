numbers = []
with open ("puzzleInput.txt", "r") as f:
    numbers = f.readlines()

total = 0
frequencies = []

foundDuplicate = False

while not foundDuplicate:
    for number in numbers:
        total += int(number)

        if str(total) in frequencies:
            print(total)
            foundDuplicate = True
            break
        frequencies.append(str(total))


print(total)
