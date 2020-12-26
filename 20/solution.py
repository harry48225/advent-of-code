import copy
import math
import random
def get_tile_edges(tile):

    edges = {}

    edges['N'] = tile[0]
    edges['S'] = tile[-1]
    edges['E'] = "".join([tile[i][-1] for i in range(len(tile))])
    edges['W'] = "".join([tile[i][0] for i in range(len(tile))])

    return edges

def reverse(string):

    return string[::-1]

def get_coord_string(x,y):

    return "{},{}".format(x,y)

def transpose_tile(tile):

    tile = [list(row) for row in tile]
    #print(tile)
    new_tile = [[tile[j][i] for j in range(len(tile[i]))] for i in range(len(tile))]

    new_tile = ["".join(new_tile[i]) for i in range(len(new_tile))]
    #print(new_tile)

    return new_tile

def horizontal_flip_tile(tile):

    return [part for part in reversed(tile)]

def vertical_flip_tile(tile):

    return transpose_tile(horizontal_flip_tile(transpose_tile(tile)))

def rotate_tile_anticlockwise_once(tile):

    # Rotates a tile anticlockwise by 90

    # Transpose the matrix and then horizontal flip
    return horizontal_flip_tile(transpose_tile(tile))

with open("input.txt") as f:

    inp = [line.strip() for line in f.readlines()]


tiles = {}

current_tile_number = ""
current_tile = []
for line in inp:

    if "Tile" in line:
        current_tile_number = line.split(" ")[1].replace(":", "")

        print(current_tile_number)

    
    elif line == "":
        tiles[current_tile_number] = current_tile
        current_tile_number = ""
        current_tile = []

    else:

        current_tile.append(line)

for tile_num in tiles.keys():

    print(tile_num)

    for part in tiles[tile_num]:
        print(part)

    print("transpose")
    for part in transpose_tile(tiles[tile_num]):
        print(part)

    print("horizontal flip")
    for part in horizontal_flip_tile(tiles[tile_num]):
        print(part)

    print("vertical flip")
    for part in vertical_flip_tile(tiles[tile_num]):
        print(part)
    print("anticlockwise rotation")
    for part in rotate_tile_anticlockwise_once(tiles[tile_num]):
        print(part)
    print()

    print()


# Try to get them to match up
# Try every combination? -- too many?
# Maybe each tile only matches with one other

map = {} # stores the positions of the tiles, keys are x, y coordinates, values are a dictionary {'tile': the tile in the correct orientation, 'tile_number':tile_number}

remaining_tiles = copy.deepcopy(tiles)
def build_map(x,y):
    # x, y should be int

    # Fill in the tiles adjacent
    
    # Terminating condition
    if len(remaining_tiles) == 0:
        return

    NORTH = [0,1]
    SOUTH = [0, -1]
    EAST = [1, 0]
    WEST = [-1, 0]
    offsets = [NORTH, EAST, SOUTH, WEST]

    for offset in offsets:

        new_x = x + offset[0]
        new_y = y + offset[1]

        coord_string = get_coord_string(new_x, new_y)
        if coord_string not in map.keys():

            current_tile = map[get_coord_string(x,y)]['tile']
            current_tile_number = map[get_coord_string(x,y)]['tile_number']
            if offset == NORTH:
                edge_direction = 'N'
            elif offset == EAST:
                edge_direction = 'E'
            elif offset == SOUTH:
                edge_direction = 'S'
            elif offset == WEST:
                edge_direction = 'W'

            edge_to_match = get_tile_edges(current_tile)[edge_direction]

            # Now we have an edge to match so look for its match
            for adj_tile_number in remaining_tiles.keys():
                match = False
                #print("{}: {}".format(adj_tile_number, get_tile_edges(tiles[adj_tile_number])))
                for potential_edge_match_direction in get_tile_edges(tiles[adj_tile_number]).keys():
                    
                    potential_edge_match = get_tile_edges(tiles[adj_tile_number])[potential_edge_match_direction]

                    # Now try the permutations
                    if edge_to_match == potential_edge_match or edge_to_match == reverse(potential_edge_match):

                        #print("{} of tile number: {}, matches with {} of tile number: {}".format(edge_direction, current_tile_number, potential_edge_match_direction, adj_tile_number))
                        match = True

                        # Now need to orientate the tile correctly

                        # Work out how to rotate

                        # Clever maths
                        cardinals = ['N', 'W', 'S', 'E'] # Cardinals in anticlockwise order
                        number_of_rotations = (cardinals.index(edge_direction) - cardinals.index(potential_edge_match_direction) + 2) % 4

                        
                        new_tile = copy.deepcopy(tiles[adj_tile_number])
                        del remaining_tiles[adj_tile_number]


                        
                        for _ in range(number_of_rotations):
                            new_tile = rotate_tile_anticlockwise_once(new_tile)

                        # Work out if we need to flip the tile
                        if edge_direction == "N" and edge_to_match == reverse(get_tile_edges(new_tile)["S"]):
                            new_tile = vertical_flip_tile(new_tile)
                        if edge_direction == "E" and edge_to_match == reverse(get_tile_edges(new_tile)["W"]):
                            new_tile = horizontal_flip_tile(new_tile)
                        if edge_direction == "S" and edge_to_match == reverse(get_tile_edges(new_tile)["N"]):
                            new_tile = vertical_flip_tile(new_tile)
                        if edge_direction == "W" and edge_to_match == reverse(get_tile_edges(new_tile)["E"]):
                            new_tile = horizontal_flip_tile(new_tile)

                        '''
                        if (edge_to_match == reverse(potential_edge_match) and number_of_rotations % 2 != 0) or (edge_to_match == potential_edge_match and number_of_rotations % 2 == 0):

                            #print("flipped")
                            print(edge_direction)
                            if edge_direction in ["N", "S"]:
                                new_tile = vertical_flip_tile(new_tile)
                            else:
                                new_tile = horizontal_flip_tile(new_tile)
                        '''

                        # Add to the map

                        map[get_coord_string(new_x, new_y)] = {'tile':new_tile, 'tile_number':adj_tile_number}

                        build_map(new_x, new_y)
                    if match:
                        break
                if match:
                    break


# Try starting from each tile until the resulting map is a square, not actually nessesary
totals = set()
for start_tile_index in list(tiles.keys()):

    start_tile = tiles[start_tile_index]

    remaining_tiles = copy.deepcopy(tiles)

    del remaining_tiles[start_tile_index]
    map[get_coord_string(0,0)] = {'tile':start_tile, 'tile_number':start_tile_index}
    build_map(0,0)

    '''
    for key in map.keys():
        print("coord: {}, tile: {}".format(key, map[key]['tile_number']))

    print(remaining_tiles)
    '''
    # Now work out the corners

    side_length = int(math.sqrt(len(tiles)))

    
    # Find the coordinates of the bottom left corner

    x = 0
    y = 0

    while get_coord_string(x,y) in map.keys():

        x -= 1

    x+=1

    while get_coord_string(x,y) in map.keys():

        y -= 1

    y += 1

    #print(map.keys())

    #print("bottom_left: {},{}".format(x,y))

    corners = [[x, y], [x + side_length -1, y], [x, y + side_length -1], [x + side_length -1, y + side_length -1]]
    corner_coord_strings = [get_coord_string(corner[0], corner[1]) for corner in corners]
    if not all([(corner_string in map.keys()) for corner_string in corner_coord_strings]):
        print("NON SQUARE TOTAL")
        continue
    corner_total = int(map[get_coord_string(x,y)]['tile_number'])

    # Goto bottom right
    while get_coord_string(x,y) in map.keys():

        x += 1

    x-=1

    corner_total *= int(map[get_coord_string(x,y)]['tile_number'])

    # Goto top right

    while get_coord_string(x,y) in map.keys():

        y += 1

    y -= 1

    corner_total *= int(map[get_coord_string(x,y)]['tile_number'])

    # Goto top left
    while get_coord_string(x,y) in map.keys():

        x -= 1

    x+=1

    corner_total *= int(map[get_coord_string(x,y)]['tile_number'])

    #print(side_length)
    #corner_total = int(map[get_coord_string(x, y)]['tile_number']) * int(map[get_coord_string(x + side_length - 1, y)]['tile_number']) * int(map[get_coord_string(x, y + side_length - 1)]['tile_number']) * int(map[get_coord_string(x + side_length - 1, y + side_length - 1)]['tile_number'])

    #print("square total: " + str(corner_total))
    #print(tiles.keys())
    totals.add(str(corner_total))
    # 31190672389321 too high, 23120468610431 too high, 11979834479201 too high 62179601129917, 5775714912743

for total in totals:
    print(total)