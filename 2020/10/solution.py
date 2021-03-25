with open("input.txt") as f:
    
    adapters = [int(line.strip().rstrip()) for line in f.readlines()]


device_rating = max(adapters) + 3


current_rating = 0

def adapater_chainer(current_rating, adapters, current_chain):

    next_ratings = [current_rating + 1, current_rating + 2, current_rating + 3]


    if device_rating in next_ratings:
        current_chain.append(device_rating)
        return current_chain

    for rating in next_ratings:

        
        if rating in adapters:
            #print("found: " + str(rating))
            new_adapters = list(adapters)
            new_adapters.remove(rating)

            new_chain = list(current_chain)
            new_chain.append(rating)

            final_chain = adapater_chainer(rating, new_adapters, new_chain)

            if final_chain[-1] == device_rating:
                return final_chain

    return [-1]

def count_number_of_differences(length, chain):

    count = 0

    for i in range(len(chain) - 1):

        if chain[i + 1] - chain[i] == length:
            count += 1
    
    return count

adapter_chain = adapater_chainer(0, adapters, [0])
print(adapter_chain)
number_of_one_jolt_differences = count_number_of_differences(1, adapter_chain)
number_of_three_jolt_differences = count_number_of_differences(3, adapter_chain)

print(number_of_one_jolt_differences * number_of_three_jolt_differences)


# Part 2
memos = {}
# Work out how many different ways to remove the 1 or 2 jump gaps
def chain_optimiser(chain):

    number_valid = 1
    for i in range(len(chain)-2):

        if chain[i+2] - chain[i] <= 3:
            
            # Can remove i+1

            new_chain = list(chain)[i:]
            del new_chain[1]

            chain_key = "".join([str(num) for num in new_chain])
            if chain_key not in memos.keys():
                memos[chain_key] = chain_optimiser(new_chain)

            number_valid += memos[chain_key]

    #if (number_valid % 6 == 0):
        #print(number_valid)
    return number_valid

print(chain_optimiser(adapter_chain))
