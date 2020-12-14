with open("input.txt") as f:

    inp = [line.strip().rstrip() for line in f.readlines()]



memory = {}

def read_from_memory(address, value):

    if address not in memory.keys():
        memory[address] = '0'*36

    return memory[address]

def write_to_memory(address, value):

    memory[address] = value

def apply_mask(mask, number):

    #print(mask)
    #print(number)

    mask = list(mask)
    number = list(number)
    #print("mask: {}".format(mask))
    for i in range(len(number)-1, -1, -1):
        if mask[i] != 'X':
            #print("applying {} at {}".format(mask[i], i))
            number[i] = mask[i]

    return "".join(number)

def pad_binary_to_36_bits(number):

    return ('0' * (36 - len(number))) + number

# Part 1
for operation in inp:

    if "mask" in operation:
        mask = operation.split(" = ")[1]
        continue

    operation = operation.split(" = ")

    address = operation[0].split("[")[1][:-1]
    value = int(operation[1])

    # First convert the value into binary
    value = pad_binary_to_36_bits(bin(value).split("b")[1])
    
    # Apply the mask to the value

    value = apply_mask(mask, value)

    write_to_memory(address, value)

#print(memory)
total = sum([int(value, 2) for value in memory.values()])
print(total)

# Part 2

memory = {}

def apply_mask_floating(mask, number):

    mask = list(mask)
    number = list(number)
    #print("mask: {}".format(mask))
    for i in range(len(number)-1, -1, -1):
        
        if mask[i] in ["1", "X"]:
            number[i] = mask[i]

    return "".join(number)

def write_using_float_mask(built_address, remaining_address, value):
    #print("built: {}, remaining: {}".format(built_address, remaining_address))
    if "X" in remaining_address:

        for i, number in enumerate(remaining_address):

            if number != "X":

                built_address += number

            else:

                write_using_float_mask(built_address + "0", remaining_address[i+1:],value)
                write_using_float_mask(built_address + "1", remaining_address[i+1:], value)

                break

    else:

        built_address += remaining_address

        # Now write

        memory[built_address] = value

for operation in inp:

    if "mask" in operation:
        mask = operation.split(" = ")[1]
        continue

    operation = operation.split(" = ")

    address = operation[0].split("[")[1][:-1]
    #print(address)
    value = int(operation[1])

    #convert both into binary
    address = pad_binary_to_36_bits(bin(int(address)).split("b")[1])
    value = pad_binary_to_36_bits(bin(value).split("b")[1])
    #print(address)
    address = apply_mask_floating(mask, address)

    #print(address)
    # Write to the addresses
    write_using_float_mask('', address, value)

#print(memory.keys())
#print(memory)
total = sum([int(value, 2) for value in memory.values()])
print(total)
