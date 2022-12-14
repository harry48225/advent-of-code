using LinearAlgebra

function parseNode(node)
  return map(x -> parse(Int, x), split(node, ","))
end

function s(pos)
  return "$(pos[1]),$(pos[2])"
end

inp = readlines("input.txt")

lineCoords = Set()

for line in inp
  nodes = map(parseNode, split(line, " -> "))

  previousNode = nodes[1]
  for nodeI in 2:length(nodes)
    currentNode = nodes[nodeI]
    # Assume that they're all straight line
    direction = Int.(normalize(currentNode - previousNode))
    pos = deepcopy(previousNode)
    while pos != currentNode
      push!(lineCoords, s(pos))
      pos += direction
    end
    push!(lineCoords, s(currentNode))
    previousNode = currentNode
  end
end

sandOrigin = [500, 0]
lowestY = reverse(sort(parseNode.(collect(lineCoords)), by=x->x[2]))[1][2]
sands = Set()

full = false
while !full
  pos = deepcopy(sandOrigin)

  while true
    if pos[2] > lowestY
      global full = true
      break
    end
    if !(s(pos + [0,1]) in lineCoords) && !(pos + [0,1] in sands)
      pos += [0,1]
    elseif !(s(pos + [-1,1]) in lineCoords) && !(pos + [-1,1] in sands)
      pos += [-1,1]
    elseif !(s(pos + [1,1]) in lineCoords) && !(pos + [1,1] in sands)
      pos += [1,1]
    else
      push!(sands, pos)
      break
    end
  end
end

println(length(sands))

sandOrigin = [500, 0]
lowestY = reverse(sort(parseNode.(collect(lineCoords)), by=x->x[2]))[1][2] + 2
sands = Set()

full = false
while !full
  pos = deepcopy(sandOrigin)

  while true
    if !(s(pos + [0,1]) in lineCoords) && !(pos + [0,1] in sands) && !(pos[2]+1 == lowestY)
      pos += [0,1]
    elseif !(s(pos + [-1,1]) in lineCoords) && !(pos + [-1,1] in sands) && !(pos[2]+1 == lowestY)
      pos += [-1,1]
    elseif !(s(pos + [1,1]) in lineCoords) && !(pos + [1,1] in sands) && !(pos[2]+1 == lowestY)
      pos += [1,1]
    else
      push!(sands, pos)
      if pos[1] == 500 && pos[2] == 0
        global full = true
      end
      break
    end
  end
end

println(length(sands))