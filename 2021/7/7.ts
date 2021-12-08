export type CrabPositions = number[];

export const parseInput = (input: string): CrabPositions => {
  return input.trim().split(",").map(number => +number);
}

export const partOne = (puzzleInput: string): number => {
  const crabs = parseInput(puzzleInput);
  
  const min = Math.min(...crabs);
  const max = Math.max(...crabs);

  let leastCost = max*crabs.length;
  for (let position = min; position <= max; position++) {
    const cost = crabs.map(crab => Math.abs(position - crab)).reduce((acc, val) => acc + val, 0);
    leastCost = Math.min(leastCost, cost);
  }

  return leastCost;
};

export const partTwo = (puzzleInput: string): number => {
  const crabs = parseInput(puzzleInput);
  
  const min = Math.min(...crabs);
  const max = Math.max(...crabs);

  let leastCost = max*(crabs.length)*(crabs.length);
  for (let position = min; position <= max; position++) {
    const cost = crabs.map(crab => Math.abs(position - crab)).map(d => ((d)*(d+1))/2).reduce((acc, val) => acc + val, 0);
    leastCost = Math.min(leastCost, cost);
  }

  return leastCost;
};

