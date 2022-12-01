# Part 1
open("input.txt") do file
  global max_calories = 0
  global current_calories = 0
  for line in eachline(file)
    if (!isempty(line))
      global current_calories += parse(Int, line)
    else
      global max_calories = max(max_calories, current_calories)
      global current_calories = 0
    end
  end
end

println(max_calories)

# Part 2
elf_calories = Vector{Int}()
open("input.txt") do file
  global current_calories = 0
  for line in eachline(file)
    if (!isempty(line))
      global current_calories += parse(Int, line)
    else
      push!(elf_calories, current_calories)
      global current_calories = 0
    end
  end
end

println(sum(sort(elf_calories, rev=true)[1:3]))