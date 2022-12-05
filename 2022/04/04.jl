lines = collect(eachline(open("input.txt"))) 

count = 0

for line in lines
  pairs = split(line, ",")
  pairs = map(pair -> map(x -> parse(Int, x), split(pair, "-")), pairs)
  
  if (pairs[1][1] >= pairs[2][1] && pairs[1][2] <= pairs[2][2]) || (pairs[1][1] <= pairs[2][1] && pairs[1][2] >= pairs[2][2])
    global count += 1
  end
end

println(count)

count = 0

for line in lines
  pairs = split(line, ",")
  pairs = map(pair -> map(x -> parse(Int, x), split(pair, "-")), pairs)
  
  range = max(pairs[1][2], pairs[2][2]) - min(pairs[1][1], pairs[2][1])

  if ((pairs[1][2] - pairs[1][1]) + (pairs[2][2] - pairs[2][1])) >= range
    global count += 1
  end
end

println(count)