function readDirectory(currentLine)
  files = Dict()

  inDirectory = true
  while inDirectory && currentLine <= length(inp)
    line = inp[currentLine]
    currentLine += 1
    
    if startswith(line, "\$ cd ")
      directory = split(line, "\$ cd ")[end]
      if (directory == "..")
        global inDirectory = false
        break
      end
      currentLine, files[directory] = readDirectory(currentLine)
    elseif startswith(line, "\$ ls")
    elseif startswith(line, "dir")
    else
      # should  be in the case where we have a file
      size, name = split(line, " ")
      files[name] = parse(Int, size)
    end
  end

  return [currentLine, files]
end

sizeCount = 0
sizeSum = 0

deletionSize = 70000000

function determineSize(directory)
  size = 0
  for key in keys(directory)
    value = directory[key]
    if value isa Int
      size += value
    else
      size += determineSize(value)
    end
  end

  if (size <= 100000)
    global sizeCount += 1
    global sizeSum += size
  end
  if (size > 30000000 - (70000000 - totalSize))
    global deletionSize = min(deletionSize, size) 
  end
  return size
end



inp = collect(readlines("input.txt"))

files = Dict()
files["/"] = readDirectory(2)
totalSize = deletionSize
totalSize = determineSize(files["/"])
determineSize(files["/"])
println(sizeSum)
println(deletionSize)