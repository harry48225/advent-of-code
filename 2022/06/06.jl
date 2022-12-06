# note this was written by chat gpt

# Read the entire contents of the file into a string
str = read("input.txt", String)

# Convert the string to a character vector
buffer = Vector{Char}(str)

# Define the find_first_marker function
function find_first_marker(buffer::Vector{Char})
  # Keep track of the last four characters received
  last_four = [0, 0, 0, 0]

  # Loop through the datastream buffer
  for i in 1:length(buffer)
    # Shift the last four characters to the left
    last_four[1:3] = last_four[2:4]

    # Add the current character to the last four characters
    last_four[4] = buffer[i]

    # Check if the last four characters are all different
    if last_four[1] != last_four[2] && last_four[1] != last_four[3] && last_four[1] != last_four[4] && last_four[2] != last_four[3] && last_four[2] != last_four[4] && last_four[3] != last_four[4]
      # If they are all different, return the current position in the buffer
      return i
    end
  end

  # If no marker was found, return -1
  return -1
end

# Find the first marker
marker_pos = find_first_marker(buffer)
println(marker_pos)

# Read the input file
lines = readlines("input.txt")

function find_start_of_packet_marker(s::AbstractString)
  # Iterate through the characters of the input string
  for i in 1:length(s)
    # Check if the last 14 characters are all different
    if length(unique(s[i:i+13])) == 14
      # Return the index of the character plus 14 (since the marker consists of 14 characters)
      return i + 13
    end
  end
end

# Apply the function to the input string
println(find_start_of_packet_marker(lines[1]))
