trees = map(row -> map(tree -> parse(Int, tree), collect(row)), collect(readlines("input.txt")))

visibleTrees = Set()

for i in 1:length(trees)
  largestTree = -1
  for j in 1:length(trees[i])
    tree = trees[i][j]
    if tree > largestTree
      push!(visibleTrees, "$(i),$(j)")
      largestTree = max(tree, largestTree)
    end
  end
end

for i in 1:length(trees)
  largestTree = -1
  for j in reverse(1:length(trees[i]))
    tree = trees[i][j]
    if tree > largestTree
      push!(visibleTrees, "$(i),$(j)")
      largestTree = max(tree, largestTree)
    end
  end
end

for j in 1:length(trees[1])
  largestTree = -1
  for i in 1:length(trees)
    tree = trees[i][j]
    if tree > largestTree
      push!(visibleTrees, "$(i),$(j)")
      largestTree = max(tree, largestTree)
    end
  end
end

for j in 1:length(trees[1])
  largestTree = -1
  for i in reverse(1:length(trees))
    tree = trees[i][j]
    if tree > largestTree
      push!(visibleTrees, "$(i),$(j)")
      largestTree = max(tree, largestTree)
    end
  end
end

println(length(visibleTrees))

score = 0

for i in eachindex(trees)
  for j in eachindex(trees[i])
    tree = trees[i][j]

    scenicScore = 1

    count = 0
    x = 1
    while i-x >= 1
      count += 1
      if trees[i-x][j] >= tree
        break
      end
      x += 1
    end
    scenicScore *= count
    count = 0
    x = 1
    while i+x <= length(trees)
      count += 1
      if trees[i+x][j] >= tree
        break
      end
      x += 1
    end
    scenicScore *= count
    count = 0
    x = 1
    while j-x >= 1
      count += 1
      if trees[i][j-x] >= tree
        break
      end
      x += 1
    end
    scenicScore *= count
    count = 0
    x = 1
    while j+x <= length(trees[i])
      count += 1
      if trees[i][j+x] >= tree
        break
      end
      x += 1
      
    end
    scenicScore *= count
    
    global score = max(scenicScore, score)
  end
end

println(score)