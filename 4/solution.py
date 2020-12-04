passports = []

# Parse the passports

with open("input.txt") as f:

    passport = {}
    for line in f.readlines():

        line = line.strip()

        if line != "":

            attributes = line.split(" ")

            for attr in attributes:

                attr = attr.split(":")
                key = attr[0]
                value = attr[1]

                passport[key] = value

        elif line == "":
            passports.append(passport)
            passport = {}

if passport != {}:
    passports.append(passport)
# Validate the passports
print(len(passports))
number_of_valid_passports = 0

for passport in passports:

    if set(["byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid"]) <= set(passport.keys()): # Check if subset
        number_of_valid_passports += 1

print(number_of_valid_passports)

# Part two

number_of_valid_passports = 0
for passport in passports:

    if set(["byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid"]) <= set(passport.keys()): # Check if subset
        
        valid = True

        if not (int(passport["byr"]) >= 1920 and int(passport["byr"]) <= 2002):
            valid = False
        
        if not (int(passport["iyr"]) >= 2010 and int(passport["iyr"]) <= 2020):
            valid = False

        if not (int(passport["eyr"]) >= 2020 and int(passport["eyr"]) <= 2030):
            valid = False

        if passport["hgt"][-2:] in ["cm", "in"]:

            height = int(passport["hgt"][:-2])
            if passport["hgt"][-2:] == "cm":
                if not (height >= 150 and height <= 193):
                    valid = False

            if passport["hgt"][-2:] == "in":
                if not (height >= 59 and height <= 76):
                    valid = False
        else:
            valid = False


        if not (passport["hcl"][0] == "#" and set(passport["hcl"][1:]) <= set(["0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"])):
            valid = False
            
        if not (passport["ecl"] in ["amb", "blu", "brn", "gry", "grn", "hzl", "oth"]):
            valid = False

        if not (len(passport["pid"]) == 9):
            valid = False

        if valid:
            number_of_valid_passports += 1

print(number_of_valid_passports)
