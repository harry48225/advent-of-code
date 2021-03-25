inputRange = range(387638, 919123)

#inputRange=["112233", "123444", "111122"]

part1 = 0
part2 = 0
for i in inputRange:
    numberList = list(str(i))

    #print(numberList)
    #print(sorted(numberList))
    #print(set(numberList))
    if sorted(numberList) == numberList and len(set(numberList)) < 6:
       # print(str(i))
        part1 += 1


    digits = set(numberList)

    # See if there is a double digit

    doubleDigit = False
    for digit in digits:
        if numberList.count(digit) == 2:
            doubleDigit = True
            break
        
    if sorted(numberList) == numberList and doubleDigit:
        part2 += 1



print(part1)
print(part2)