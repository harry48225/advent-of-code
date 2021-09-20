type Instructions = {
  absolutes: Map<number, string>,
  gifts: Map<string, {low: string, high: string}>
}

export const parseInstructions = (rawInstructions: string[]): Instructions => {
  let absolutes = new Map<number,string>();
  let gifts = new Map<string, {low: string, high:string}>();

  for (const instruction of rawInstructions) {
    if (instruction.startsWith("value")) {
      const splitInstruction = instruction.split(" ");
      absolutes.set(parseInt(splitInstruction[1]), splitInstruction.slice(-2).join(" "));
    } else if (instruction.startsWith("bot")) {
      const splitInstruction = instruction.split(" ");
      gifts.set(splitInstruction.slice(0,2).join(" "), {low: splitInstruction.slice(5, 7).join(" "), high: splitInstruction.slice(-2).join(" ")});
    }
  }
  return {absolutes, gifts};
}

type BotResponsibilities = Map<string, [number, number]>;
type Outputs = Map<string, number[]>;

export const runSimulation = (instructions: Instructions): {bots: BotResponsibilities, outputs: Outputs} => {
  const botResponsibilities: BotResponsibilities = new Map();
  const currentState = new Map<string, number[]>();

  // allocate inital values
  for (const [value, destination] of instructions.absolutes.entries()) {
    currentState.set(destination, [...(currentState.get(destination) ?? []), value]);
  }

  let finished = false
  while (!finished) {
    finished = true;
    for (const [bot, hand] of currentState) {
      if (bot.startsWith("bot") && hand.length == 2) {
        finished = false;
        botResponsibilities.set(bot, [hand[0], hand[1]]);
        const instruction = instructions.gifts.get(bot)!;
        currentState.set(instruction.low, [...(currentState.get(instruction.low) ?? []), Math.min(...hand)]);
        currentState.set(instruction.high, [...(currentState.get(instruction.high) ?? []), Math.max(...hand)]);
        currentState.set(bot, []);
      }
    }
  }

  let outputs = new Map([...currentState.entries()].filter(([bot, values]) => bot.startsWith("output")));
  return {bots: botResponsibilities, outputs};
}

export const partOne = (puzzleInput: string) => {
  const instructions = parseInstructions(puzzleInput.trim().split("\n").map(line => line.trim()));
  return [...runSimulation(instructions).bots.entries()].find(([bot, responsibility]) => responsibility.sort().toString() == [61, 17].sort().toString())?.at(0);
}

export const partTwo = (puzzleInput: string) => {
  const instructions = parseInstructions(puzzleInput.trim().split("\n").map(line => line.trim()));
  const outputs = runSimulation(instructions).outputs;
  return (outputs.get("output 0")?.at(0) ?? 1) * (outputs.get("output 1")?.at(0) ?? 1) * (outputs.get("output 2")?.at(0) ?? 1);
}