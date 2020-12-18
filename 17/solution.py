import copy

ACTIVE = "#"
INACTIVE = "."

with open("input.txt") as f:

    inp = [list(line.strip()) for line in f.readlines()]

def set_state(x, y, z, cube_state, space):

    space["{},{},{}".format(x, y,z)] = cube_state

def get_state(x, y, z, space):

    key = "{},{},{}".format(x, y,z)

    if key not in space.keys():

        space[key] = INACTIVE

    return space[key]


space = {}

def run_cycle(initial_state_size, state_size):

    old_space = copy.deepcopy(space)

    state_offset = state_size - initial_state_size

    start = int(0 - state_offset/2)
    end = int(initial_state_size + state_offset/2)
    for x in range(start, end + 1):

        for y in range(start, end + 1):

            for z in range(-int(state_size/2) - 1, int(state_size/2)+ 2):
                active_neighbours = 0
                # Then do neighbours
                for i in range(-1, 2):
                    for j in range(-1, 2):
                        for k in range(-1, 2):
                            if i == 0 and j == 0 and k == 0:
                                continue
                            if get_state(x + i, y + j, z + k, old_space) == ACTIVE:
                                active_neighbours += 1

                current_state = get_state(x, y, z, old_space)

                if current_state == ACTIVE and active_neighbours not in [2,3]:
                    set_state(x, y, z, INACTIVE, space)

                elif current_state == INACTIVE and active_neighbours == 3:
                    set_state(x, y, z, ACTIVE, space)

                #print("trying: ({},{},{})".format(x,y,z))


# Apply initial data

for y, row in enumerate(inp):

    for x, cube_state in enumerate(row):

        set_state(x, y, 0, cube_state, space)

initial_state_size = len(inp)
state_size = initial_state_size # Cube is state_size x state_size x state_size, each cycle this increases by 2

#print(state_size)
#print(space)
for i in range(6):

    run_cycle(initial_state_size, state_size)

    state_size += 2

    #print(space)
    total = 0
    for cube in space.values():
        if cube == "#":
            total += 1

print("Part 1: " + str(total))

# Part 2
import copy

ACTIVE = "#"
INACTIVE = "."

with open("input.txt") as f:

    inp = [list(line.strip()) for line in f.readlines()]

def set_state_hyper(x, y, z, w, cube_state, space):

    space["{},{},{},{}".format(x, y, z, w)] = cube_state

def get_state_hyper(x, y, z, w, space):

    key = "{},{},{},{}".format(x, y, z, w)

    if key not in space.keys():

        space[key] = INACTIVE

    return space[key]


space = {}

def run_cycle_hyper(initial_state_size, state_size):

    old_space = copy.deepcopy(space)

    state_offset = state_size - initial_state_size

    start = int(0 - state_offset/2)
    end = int(initial_state_size + state_offset/2)
    for x in range(start, end + 1):

        for y in range(start, end + 1):

            for z in range(-int(state_size/2) - 1, int(state_size/2)+ 2):
                
                for w in range(-int(state_size/2) - 1, int(state_size/2)+ 2):
                    active_neighbours = 0
                    # Then do neighbours
                    for i in range(-1, 2):
                        for j in range(-1, 2):
                            for k in range(-1, 2):
                                for l in range(-1,2):
                                    if i == 0 and j == 0 and k == 0 and l == 0:
                                        continue
                                    if get_state_hyper(x + i, y + j, z + k, w + l, old_space) == ACTIVE:
                                        active_neighbours += 1

                    current_state = get_state_hyper(x, y, z, w, old_space)

                    if current_state == ACTIVE and active_neighbours not in [2,3]:
                        set_state_hyper(x, y, z, w, INACTIVE, space)

                    elif current_state == INACTIVE and active_neighbours == 3:
                        set_state_hyper(x, y, z, w, ACTIVE, space)

                    #print("trying: ({},{},{})".format(x,y,z))


# Apply initial data

for y, row in enumerate(inp):

    for x, cube_state in enumerate(row):

        set_state_hyper(x, y, 0, 0, cube_state, space)

initial_state_size = len(inp)
state_size = initial_state_size # Cube is state_size x state_size x state_size, each cycle this increases by 2

#print(state_size)
#print(space)
for i in range(6):

    run_cycle_hyper(initial_state_size, state_size)

    state_size += 2

    #print(space)
    total = 0
    for cube in space.values():
        if cube == "#":
            total += 1

print("part2: " + str(total))
    #input()