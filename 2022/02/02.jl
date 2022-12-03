function wins(ourMove, theirMove)
  if ourMove == "X" && theirMove == "C"
    return true
  end
  if ourMove == "Y" && theirMove == "A"
    return true
  end
  if ourMove == "Z" && theirMove == "B"
    return true
  end
  return false
end

function draw(ourMove, theirMove)
  if ourMove == "X" && theirMove == "A"
    return true
  end
  if ourMove == "Y" && theirMove == "B"
    return true
  end
  if ourMove == "Z" && theirMove == "C"
    return true
  end
  return false
end

score = 0 

open("input.txt") do file
  for line in eachline(file)
    moves = split(line, " ")
    ourMove = moves[2]
    theirMove = moves[1]

    if (ourMove == "X")
      global score += 1
    elseif (ourMove == "Y")
      global score += 2
    elseif (ourMove == "Z")
      global score += 3
    end

    if (draw(ourMove, theirMove))
      global score += 3
    elseif (wins(ourMove, theirMove))
      global score += 6
    end 
  end
end

println(score)

#part 2
score = 0 

open("input.txt") do file
  for line in eachline(file)
    moves = split(line, " ")
    outcome = moves[2]
    theirMove = moves[1]

    ourMove = outcome
    if (outcome == "X")
      if (theirMove == "A")
        ourMove = "Z"
      elseif (theirMove == "B")
        ourMove = "X"
      elseif (theirMove == "C")
        ourMove = "Y"
      end
    elseif (outcome == "Y")
      if (theirMove == "A")
        ourMove = "X"
      elseif (theirMove == "B")
        ourMove = "Y"
      elseif (theirMove == "C")
        ourMove = "Z"
      end
    elseif (outcome == "Z")
      if (theirMove == "A")
        ourMove = "Y"
      elseif (theirMove == "B")
        ourMove = "Z"
      elseif (theirMove == "C")
        ourMove = "X"
      end
    end

    if (ourMove == "X")
      global score += 1
    elseif (ourMove == "Y")
      global score += 2
    elseif (ourMove == "Z")
      global score += 3
    end

    if (draw(ourMove, theirMove))
      global score += 3
    elseif (wins(ourMove, theirMove))
      global score += 6
    end 
  end
end

println(score)