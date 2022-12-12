function letterVal(letter)
  if letter == 'S'
    letter = 'a'
  end

  if letter == 'E'
    letter = 'z'
  end
  return Int(codepoint(letter))
end

inp = map(line->collect(line), readlines("input.txt"))

function findShortest(startI, startJ)
  queue = [[[startI, startJ]]]

  visited = Set()

  while length(queue) > 0
    current = popfirst!(queue)
    pos = current[end]

    i = pos[1]
    j = pos[2]
    letter = inp[i][j] 
    
    serial = "$(i),$(j)"
    if in(serial, visited)
      continue
    end
    push!(visited, serial)
    # println(letter)
    if letter == 'E'
      return length(current) - 1
    end

    offsets = [[1, 0], [0, 1], [-1, 0], [0, -1]]

    for offset in offsets
      # now we try to move on
      newI = i + offset[1]
      newJ = j + offset[2]
    
      if newI <= length(inp) && newI >= 1 && newJ >= 1 && newJ <= length(inp[newI]) && inp[newI][newJ] != 'S' && letterVal(inp[newI][newJ]) <= letterVal(letter) + 1
        push!(queue, [current; [[newI, newJ]]])
      end
    end
  end

  return length(inp)^2
end


# find the start location
for i in eachindex(inp)
  for j in eachindex(inp[i])
    if inp[i][j] == 'S'
      println(findShortest(i, j))
    end
  end
end


mostScenic = length(inp)^2

# find the start location
for i in eachindex(inp)
  for j in eachindex(inp[i])
    if inp[i][j] == 'a'
      global mostScenic = min(mostScenic, findShortest(i, j))
    end
  end
end

println(mostScenic)