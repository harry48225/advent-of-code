NUMBER_OF_ROWS = 128
NUMBER_OF_COLUMNS = 8

def binary_search(search_string, total_length, front_char, back_char):
    
    upper_bound = total_length - 1
    lower_bound = 0
    
    for char in search_string:
        
        mid_point = int((upper_bound + lower_bound)/2)
        if char == front_char:
            upper_bound = mid_point
        elif char == back_char:
            lower_bound = mid_point + 1
            
        #print("upper: " + str(upper_bound) + " lower: " + str(lower_bound))
            
            
    if upper_bound == lower_bound:
        
        return upper_bound
    
    else:
        return -1 # Something must have gone wrong
    
    

with open("input.txt") as f:
    boarding_passes = [line.rstrip() for line in f.readlines()]


largest_seat_id = 0 # For part 1
seats = [["e" for j in range(NUMBER_OF_COLUMNS)] for i in range(NUMBER_OF_ROWS)]

for boarding_pass in boarding_passes:
    
    row_search_string = boarding_pass[:7]
    col_search_string = boarding_pass[7:]
    
    row_number = binary_search(row_search_string, NUMBER_OF_ROWS, "F", "B")
    col_number = binary_search(col_search_string, NUMBER_OF_COLUMNS, "L", "R")
    
    #print("{0}, {1}".format(row_number, col_number))
    
    seat_id = (row_number * 8) + col_number

    largest_seat_id = max(seat_id, largest_seat_id)
    
    seats[row_number][col_number] = "o"
    
print(largest_seat_id)

# Find the empty seat
for row_number, row in enumerate(seats):
    
    if "".join(row) == "e"*NUMBER_OF_COLUMNS: # If it's an empty row
        continue
    
    # Row must be somewhat non-empty
    if "".join(sorted(row)) == "e" + ("o" * (NUMBER_OF_COLUMNS - 1)):
        col_number = row.index("e")
        
        print((row_number * 8) + col_number) # Print the seat id
            

            
        
    
