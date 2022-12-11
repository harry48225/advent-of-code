input = readlines("input.txt")

struct Monkey
  items::Vector{Int}
  operation::Function
  test::Function # takes the worry level and returns the number of the monkey it gets thrown to
  inspections::Vector{Int}
end

monkeys = Vector{Monkey}()

lineNumber = 0
while lineNumber <= length(input)
  global lineNumber += 1
  # number = parse(Int, split(split(input[lineNumber], " ")[2], ":")[1])
  global lineNumber += 1
  items = map(x -> parse(Int, x), split(input[lineNumber][length("  Starting items: "):end], ", "))
  global lineNumber += 1
  rawOperation = split(input[lineNumber][length("  Operation: new = ")+1:end], " ")
  # operation is binary
  function operation(old)
    register = rawOperation[1] == "old" ? old : parse(Int, rawOperation[1])
    operand = rawOperation[3] == "old" ? old : parse(Int, rawOperation[3])
    if rawOperation[2] == "+"
      register += operand
    elseif rawOperation[2] == "*"
      register *= operand 
    end
    
    return register
  end

  global lineNumber += 1
  divisor = parse(Int, input[lineNumber][length("  Test: divisible by "):end])
  global lineNumber += 1
  trueMonkey = parse(Int, input[lineNumber][length("    If true: throw to monkey "):end])
  global lineNumber += 1
  falseMonkey = parse(Int, input[lineNumber][length("    If false: throw to monkey "):end])
  function test(worryAmount) 
    if mod(worryAmount, divisor) == 0
      return trueMonkey + 1
    else
      return falseMonkey + 1
    end
  end

  monkey = Monkey(items, operation, test, [0])
  push!(monkeys, monkey)

  global lineNumber += 1
end

for roundNumber in 1:20
  for monkey in monkeys
    while length(monkey.items) > 0
      monkey.inspections[1] += 1
      item = popfirst!(monkey.items)
      item = floor(monkey.operation(item) / 3)
      push!(monkeys[monkey.test(item)].items, item)
    end
  end
end

mostInspections = sort(map(m -> m.inspections[1], monkeys), rev = true)
println(mostInspections[1] * mostInspections[2])
monkeys = Vector{Monkey}()

base = 1
lineNumber = 0
while lineNumber <= length(input)
  global lineNumber += 1
  # number = parse(Int, split(split(input[lineNumber], " ")[2], ":")[1])
  global lineNumber += 1
  items = map(x -> parse(Int, x), split(input[lineNumber][length("  Starting items: "):end], ", "))
  global lineNumber += 1
  rawOperation = split(input[lineNumber][length("  Operation: new = ")+1:end], " ")
  # operation is binary
  function operation(old)
    register = rawOperation[1] == "old" ? old : parse(Int, rawOperation[1])
    operand = rawOperation[3] == "old" ? old : parse(Int, rawOperation[3])
    if rawOperation[2] == "+"
      register += operand
    elseif rawOperation[2] == "*"
      register *= operand 
    end
    
    return register
  end

  global lineNumber += 1
  divisor = parse(Int, input[lineNumber][length("  Test: divisible by "):end])
  global base *= divisor
  global lineNumber += 1
  trueMonkey = parse(Int, input[lineNumber][length("    If true: throw to monkey "):end])
  global lineNumber += 1
  falseMonkey = parse(Int, input[lineNumber][length("    If false: throw to monkey "):end])
  function test(worryAmount) 
    if mod(worryAmount, divisor) == 0
      return trueMonkey + 1
    else
      return falseMonkey + 1
    end
  end

  monkey = Monkey(items, operation, test, [0])
  push!(monkeys, monkey)

  global lineNumber += 1
end

for roundNumber in 1:10000
  for monkey in monkeys
    while length(monkey.items) > 0
      monkey.inspections[1] += 1
      item = popfirst!(monkey.items)
      item = mod(monkey.operation(item), base)
      push!(monkeys[monkey.test(item)].items, item)
    end
  end
end

mostInspections = sort(map(m -> m.inspections[1], monkeys), rev = true)
println(mostInspections[1] * mostInspections[2])