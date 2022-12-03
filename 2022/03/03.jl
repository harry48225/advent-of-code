function priority(char)
  ord = Int(codepoint(char)) - 96 

  if (ord < 0)
    return ord - (Int(codepoint('A'))) + 27 + 96
  end

  return ord
end

score = 0
open("input.txt") do file
  for raw_bag in eachline(file)
    bag = collect(raw_bag) 
    compartments = [bag[1:div(length(bag),2)], bag[div(length(bag),2) + 1:length(bag)]]
    set_compartments = map(x -> Set(x), compartments)
    common = intersect(set_compartments[1], set_compartments[2])
    
    global score += priority(collect(common)[1])
  end
end

println(score)

score = 0
open("input.txt") do file
  lines = collect(eachline(file)) 
  for i = 0:Int(length(lines)//3)-1
    bags = [collect(lines[i*3+1]), collect(lines[i*3+2]), collect(lines[i*3+3])]
    set_bags = map(x -> Set(x), bags)
    common = intersect(set_bags[1], set_bags[2], set_bags[3])
    global score += priority(collect(common)[1])
  end
end

println(score)