import copy

with open("input.txt") as f:

    seats = [list(line.strip().rstrip()) for line in f.readlines()]

#Part 1
def get_number_of_adjacent_occupied_seats(row, column, seats):

    adjacent_seats =    [[row-1, column-1], [row-1, column], [row-1, column+1],
                        [row, column-1],                [row, column+1],
                        [row+1, column-1], [row+1, column], [row+1, column+1]]

    total_occupied = 0
    for seat in adjacent_seats:

        if seat[0] >= 0 and seat[0] < len(seats):
            if seat[1] >= 0 and seat[1] < len(seats[seat[0]]):
                
                seat_state = seats[seat[0]][seat[1]]

                if seat_state == "#":
                    total_occupied += 1

    return total_occupied


while True:
    old_seats = copy.deepcopy(seats)
    for row_number in range(len(seats)):
        for col_number in range(len(seats[row_number])):

            current_seat = seats[row_number][col_number]
            #print(current_seat)
            if current_seat != ".":

                #print(current_seat)

                number_occupied = get_number_of_adjacent_occupied_seats(row_number, col_number, old_seats)

                if current_seat == "L" and number_occupied == 0:

                    seats[row_number][col_number] = "#"

                elif current_seat == "#" and number_occupied >= 4:
                    seats[row_number][col_number] = "L"


    if old_seats == seats:

        total_occupied = 0

        for row in seats:
            for seat in row:

                if seat == "#":
                    total_occupied += 1

        print(total_occupied)

        break


# Part 2

with open("input.txt") as f:

    seats = [list(line.strip().rstrip()) for line in f.readlines()]

def get_number_of_adjacent_occupied_seats_raycast(row, column, seats):

    directions = [[-1, 0], [1, 0], [0, 1], [0, -1], [1, 1], [-1, 1], [1, -1], [-1, -1]]

    total_occupied = 0
    
    for direction in directions:
        current_row = row + direction[0]
        current_column = column + direction[1]

        while (current_row >= 0 and current_row < len(seats)) and (current_column >= 0 and current_column < len(seats[current_row])):
            
            current_seat = seats[current_row][current_column]

            if current_seat == "L":
                break
            elif current_seat == "#":
                total_occupied += 1
                break
            
            current_row += direction[0]
            current_column += direction[1]


    return total_occupied


while True:
    old_seats = copy.deepcopy(seats)
    for row_number in range(len(seats)):
        for col_number in range(len(seats[row_number])):

            current_seat = seats[row_number][col_number]
            #print(current_seat)
            if current_seat != ".":

                #print(current_seat)

                number_occupied = get_number_of_adjacent_occupied_seats_raycast(row_number, col_number, old_seats)

                if current_seat == "L" and number_occupied == 0:

                    seats[row_number][col_number] = "#"

                elif current_seat == "#" and number_occupied >= 5:
                    seats[row_number][col_number] = "L"


    if old_seats == seats:

        total_occupied = 0

        for row in seats:
            for seat in row:

                if seat == "#":
                    total_occupied += 1

        print(total_occupied)

        break


