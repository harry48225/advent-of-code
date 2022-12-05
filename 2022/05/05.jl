f = open("input.txt")


# read the stacks

raw_stacks = Vector{String}()
raw_instructions = Vector{String}()

reading_stacks = true
for line in eachline(f)
  if isempty(line)
    global reading_stacks = false
    pop!(raw_stacks) 
  end

  if reading_stacks
    push!(raw_stacks, line)
  else
    push!(raw_instructions, line)
  end
end

popfirst!(raw_instructions)

stacks = Vector{Vector{String}}()
p = raw_stacks[1]
x = length(p)
number_of_stacks = Int((length(raw_stacks[1]) + 1) // 4)

for i in 1:number_of_stacks
  push!(stacks, Vector{String}())
end

for line in raw_stacks
  v_line = collect(line)
  for i in 1:number_of_stacks
    item = string(v_line[(i * 4) - 2])
    if item != " "
      pushfirst!(stacks[i], item)
    end
  end
end

for raw_instruction in raw_instructions
  words = collect(split(raw_instruction, " "))
  amount = parse(Int, words[2])
  from = parse(Int, words[4])
  to = parse(Int, words[6])

  for _ in 1:amount
    push!(stacks[to], pop!(stacks[from]))
  end

end

top = join(map(stack -> stack[end], stacks))

println(top)

# part two

f = open("input.txt")


# read the stacks

raw_stacks = Vector{String}()
raw_instructions = Vector{String}()

reading_stacks = true
for line in eachline(f)
  if isempty(line)
    global reading_stacks = false
    pop!(raw_stacks) 
  end

  if reading_stacks
    push!(raw_stacks, line)
  else
    push!(raw_instructions, line)
  end
end

popfirst!(raw_instructions)

stacks = Vector{Vector{String}}()
p = raw_stacks[1]
x = length(p)
number_of_stacks = Int((length(raw_stacks[1]) + 1) // 4)

for i in 1:number_of_stacks
  push!(stacks, Vector{String}())
end

for line in raw_stacks
  v_line = collect(line)
  for i in 1:number_of_stacks
    item = string(v_line[(i * 4) - 2])
    if item != " "
      pushfirst!(stacks[i], item)
    end
  end
end

for raw_instruction in raw_instructions
  words = collect(split(raw_instruction, " "))
  amount = parse(Int, words[2])
  from = parse(Int, words[4])
  to = parse(Int, words[6])

  buffer = Vector{String}()
  for _ in 1:amount
    push!(buffer, pop!(stacks[from]))
  end

  for _ in 1:amount
    push!(stacks[to], pop!(buffer))
  end

end

top = join(map(stack -> stack[end], stacks))

println(top)