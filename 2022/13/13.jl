function toList(packet)
  return eval(Meta.parse(packet))
end

function isNumber(packet)
  return isa(packet, Int)
end

function isList(packet)
  return isa(packet, Vector)
end

function compare(oldLeft, oldRight)
  left = deepcopy(oldLeft)
  right = deepcopy(oldRight)
  if isNumber(left) && isNumber(right)
    # println("$(left) and $(right) are numbers")
    if left > right
      return false
    elseif right > left
      return true
    else
      return nothing
    end
  else
    if isNumber(left)
      left = [left]
    end

    if isNumber(right)
      right = [right]
    end

    # println("starting have $(left) and $(right)")

    for i in 1:max(length(left), length(right))+1
      if i > length(left) && i > length(right)
        # println("out of bounds with i:$(i) on $(left) and $(right)")
        return nothing
      elseif i > length(left)
        return true
      elseif i > length(right)
        return false
      end

      # println("we have $(left[i]) and $(right[i]) at i: $(i)")
      # println("comparing $(left[i]) to $(right[i])")
      comparison = compare(left[i], right[i])
      # println("and got $(comparison)")

      if comparison == true
        return true
      elseif comparison == false
        return false
      end
    end
  end
end

packets = readlines("input.txt")

total = 0
for packetNumber in 1:3:length(packets)-1
  left = toList(packets[packetNumber])
  right = toList(packets[packetNumber+1])

  if compare(left, right)
    global total += Int((packetNumber-1)/3)+1
  end
end

println(total) 
# 6240

# now do a bubble sort
proessedPackets = map(p -> toList(p), filter(p -> !isempty(p), packets))
append!(proessedPackets, [[[2]], [[6]]])

sorted = sort(proessedPackets, lt=compare)

product = 1
for i in eachindex(sorted)
  if sorted[i] == [[2]] || sorted[i] == [[6]]
    global product *= i
  end
end

println(product)
# 23142