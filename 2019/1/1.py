with open("input.txt") as f:
    inputFile = f.readlines()


# Part one
totalFuel = 0

for fuel in inputFile:
    totalFuel += int(int(fuel)/3) -2

print(totalFuel)


# Part two

totalFuel = 0

for fuel in inputFile:
    fuel = int(fuel)
    
    fuel = int(fuel/3) - 2
    while fuel > 0:
        totalFuel += fuel
        fuel = int(fuel/3) - 2
        

print(totalFuel)
    