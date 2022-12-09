inp = collect(readlines("input.txt"))


head = [0,0]
tail = [0,0]
visited = Set()
push!(visited, "$(tail)")

for instruction in inp
  args = split(instruction, " ")

  direction = args[1]
  amount = parse(Int, args[2])
  # println(instruction)
  while amount > 0
    amount -= 1
    if direction == "U"
      head[2] += 1
    elseif direction == "D"
      head[2] -= 1
    elseif direction == "L"
      head[1] -= 1
    elseif direction == "R"
      head[1] += 1
    end

    if max(abs((head[1] - tail[1])), abs(head[2] - tail[2])) > 1
      if head[1] == tail[1]
        if head[2] == tail[2] + 2
          tail[2] += 1
        elseif head[2] == tail[2] - 2
          tail[2] -= 1
        end
      elseif head[2] == tail[2]
        if head[1] == tail[1] + 2
          tail[1] += 1
        elseif head[1] == tail[1] - 2
          tail[1] -= 1
        end
      elseif head[1] != tail[1] && head[2] != tail[2]
        if head[1] <= tail[1] && head[2] >= tail[2]
          tail[1] -= 1
          tail[2] += 1
        elseif head[1] >= tail[1] && head[2] >= tail[2]
          tail[1] += 1
          tail[2] += 1
        elseif head[1] <= tail[1] && head[2] <= tail[2]
          tail[1] -= 1
          tail[2] -= 1
        elseif head[1] >= tail[1] && head[2] <= tail[2]
          tail[1] += 1
          tail[2] -= 1
        end
      end
    end
    
    # println("$(head), $(tail)")
    push!(visited, "$(tail)")
  end
end

println(length(visited))


inp = collect(readlines("input.txt"))


snake = [[0,0],[0,0], [0,0], [0,0], [0,0], [0,0], [0,0], [0,0], [0,0], [0,0]]
visited = Set()
push!(visited, "$(snake[end])")

for instruction in inp
  args = split(instruction, " ")

  direction = args[1]
  amount = parse(Int, args[2])
  # println(instruction)
  while amount > 0
    amount -= 1
    if direction == "U"
      snake[1][2] += 1
    elseif direction == "D"
      snake[1][2] -= 1
    elseif direction == "L"
      snake[1][1] -= 1
    elseif direction == "R"
      snake[1][1] += 1
    end

    for i in 2:length(snake)
      local head = snake[i-1]
      local tail = snake[i]
      if max(abs((head[1] - tail[1])), abs(head[2] - tail[2])) > 1
        if head[1] == tail[1]
          if head[2] == tail[2] + 2
            tail[2] += 1
          elseif head[2] == tail[2] - 2
            tail[2] -= 1
          end
        elseif head[2] == tail[2]
          if head[1] == tail[1] + 2
            tail[1] += 1
          elseif head[1] == tail[1] - 2
            tail[1] -= 1
          end
        elseif head[1] != tail[1] && head[2] != tail[2]
          if head[1] <= tail[1] && head[2] >= tail[2]
            tail[1] -= 1
            tail[2] += 1
          elseif head[1] >= tail[1] && head[2] >= tail[2]
            tail[1] += 1
            tail[2] += 1
          elseif head[1] <= tail[1] && head[2] <= tail[2]
            tail[1] -= 1
            tail[2] -= 1
          elseif head[1] >= tail[1] && head[2] <= tail[2]
            tail[1] += 1
            tail[2] -= 1
          end
        end
      end
    end
    
    # println("$(head), $(tail)")
    push!(visited, "$(snake[end])")
  end
end

println(length(visited))