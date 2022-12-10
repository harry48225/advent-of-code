instructions = readlines("input.txt")

cycleCount = 0
registerX = 1
totalSignalStrength = 0
display = []
currentLine = []
for instruction in instructions
  opcode, operand = [split(instruction, " "); [""]]
  cyclesRemaining = 0
  if opcode == "addx"
    cyclesRemaining += 2
  elseif opcode == "noop"
    cyclesRemaining += 1
  end

  while cyclesRemaining > 0
    global cycleCount += 1
    cyclesRemaining -= 1

    currentPixel = length(currentLine) 
    if currentPixel >= registerX - 1 && currentPixel <= registerX + 1
      push!(currentLine, "#")
    else
      push!(currentLine, " ")
    end

    if mod(cycleCount + 20, 40) == 0
      global totalSignalStrength += cycleCount * registerX
    end

    if mod(cycleCount, 40) == 0
      push!(display, currentLine)
      global currentLine = []
    end
  end

  if opcode == "addx"
    global registerX += parse(Int, operand)
  elseif opcode == "noop"
  end
  
end

println(totalSignalStrength)

for line in display
  println(join(line))
end