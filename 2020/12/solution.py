import math

NORTH = [0, 1]
EAST = [1, 0]
SOUTH = [0, -1]
WEST = [-1, 0]


def left_matrix_multiply(vector, matrix):

    # Takes a 2d vector and 2d matrix
    new_vector = [0, 0]

    new_vector[0] = matrix[0][0]*vector[0] + matrix[0][1]*vector[1]
    new_vector[1] = matrix[1][0]*vector[0] + matrix[1][1]*vector[1]

    return new_vector

def vector_addition(vector1, vector2):

    return [vector1[0] + vector2[0], vector1[1] + vector2[1]]

def vector_scale(vector, scale_factor):

    return [vector[0] * scale_factor, vector[1] * scale_factor]

def degrees_to_radians(degrees):

    return (degrees/360) * (2*math.pi)

def get_rotation_matrix(angle):
    
    # Angle should be in radians
    
    matrix = [[int(math.cos(angle)), -int(math.sin(angle))],
                [int(math.sin(angle)), int(math.cos(angle))]]

    return matrix


with open("input.txt") as f:

    directions = [line.strip() for line in f.readlines()]
        
# part 1
currentpos = [0,0]
current_direction = EAST
for direction in directions:

    operation = direction[0]
    amount = int(direction[1:])

    #print("operation: {0}, amount: {1}".format(operation, amount))
    if operation == "N":
        currentpos = vector_addition(currentpos, vector_scale(NORTH, amount))
    elif operation == "E":
        currentpos = vector_addition(currentpos, vector_scale(EAST, amount))
    elif operation == "S":
        currentpos = vector_addition(currentpos, vector_scale(SOUTH, amount))
    elif operation == "W":
        currentpos = vector_addition(currentpos, vector_scale(WEST, amount))

    elif operation == "L":
        current_direction = left_matrix_multiply(current_direction, get_rotation_matrix(degrees_to_radians(amount)))
    elif operation == "R":
        current_direction = left_matrix_multiply(current_direction, get_rotation_matrix(degrees_to_radians(-amount)))

    elif operation == "F":
        currentpos = vector_addition(currentpos, vector_scale(current_direction, amount))
    #print(currentpos)

print(abs(currentpos[0]) + abs(currentpos[1]))

# part 2
ship_pos = [0,0]
waypoint_pos = [10, 1]
current_direction = EAST
for direction in directions:

    operation = direction[0]
    amount = int(direction[1:])

    print("operation: {0}, amount: {1}".format(operation, amount))
    if operation == "N":
        waypoint_pos = vector_addition(waypoint_pos, vector_scale(NORTH, amount))
    elif operation == "E":
        waypoint_pos = vector_addition(waypoint_pos, vector_scale(EAST, amount))
    elif operation == "S":
        waypoint_pos = vector_addition(waypoint_pos, vector_scale(SOUTH, amount))
    elif operation == "W":
        waypoint_pos = vector_addition(waypoint_pos, vector_scale(WEST, amount))

    # Rotate the waypoint

    elif operation in ["L", "R"]:

        if operation == "R":
            amount = -amount

        waypoint_pos = vector_addition(left_matrix_multiply(vector_addition(waypoint_pos, vector_scale(ship_pos, -1)), get_rotation_matrix(degrees_to_radians(amount))), ship_pos)

    elif operation == "F":

        waypoint_direction = vector_addition(waypoint_pos, vector_scale(ship_pos, -1)) # waypoint_direction = waypoint_pos - ship_pos

        print("waypoint direction: " + str(waypoint_direction))
        ship_pos = vector_addition(ship_pos, vector_scale(waypoint_direction, amount))
        waypoint_pos = vector_addition(waypoint_pos, vector_scale(waypoint_direction, amount))
    print("waypoint: {0} ship: {1}".format(waypoint_pos, ship_pos))

print(abs(ship_pos[0]) + abs(ship_pos[1]))
