export type Polymer = string;
export type Rule = {match: string, insertion: string}

export const parseInput = (puzzleInput: string): [Polymer, Rule[]] => {
  const splitLines = puzzleInput.trim().split("\n")

  const polymer: Polymer = splitLines[0];
  const rules: Rule[] = [];
  for (let i = 2; i < splitLines.length; i++) {
    const line = splitLines[i].split(" -> ");
    rules.push({match: line[0], insertion: line[1]})
  }

  return [polymer, rules];
}

export const performInsertions = (polymer: Polymer, rules: Rule[]): Polymer => {
  const pairs = [];
  for (let i = 0; i < polymer.length - 1; i++) {
    pairs.push(polymer[i] + polymer[i+1]);
  }

  let newPolymer: Polymer = "";

  for (const pair of pairs) {
    newPolymer += pair[0]
    for (const rule of rules) {
      if (rule.match == pair) {
        newPolymer += rule.insertion
        break;
      }
    }
  }
  newPolymer += polymer.charAt(polymer.length - 1);
  return newPolymer;
}

export const partOne = (puzzleInput: string): number => {
  let [polymer, rules] = parseInput(puzzleInput);
  for (let i = 0; i < 10; i++) {
    polymer = performInsertions(polymer, rules);
  }

  const count = new Map<string, number>();

  for (const char of [...polymer]) {
    count.set(char, (count.get(char) ?? 0) + 1);
  }

  const sorted = [...count.entries()].sort(([char, quantity], [chartwo, quantity2]) => quantity2 - quantity);

  return sorted[0][1] - sorted[sorted.length -1][1];
};

export type State = Map<string, number>;

export const performInsertionsFast = (state: State, rules: Rule[]) => {
  const newState: State = new Map<string, number>();
  
  for (const pair of state.keys()) {
    for (const rule of rules) {
      if (pair == rule.match) {
        newState.set(pair[0] + rule.insertion, (newState.get(pair[0] + rule.insertion) ?? 0) + (state.get(pair) ?? 0));
        newState.set(rule.insertion + pair[1], (newState.get(rule.insertion + pair[1]) ?? 0) + (state.get(pair) ?? 0));
      }
    }
  }

  return newState;
}

export const partTwo = (puzzleInput: string): number => {
  let [polymer, rules] = parseInput(puzzleInput);
  const pairs = [];
  for (let i = 0; i < polymer.length - 1; i++) {
    pairs.push(polymer[i] + polymer[i+1]);
  }

  let state: State = new Map<string, number>();
  for (const pair of pairs) {
    state.set(pair, (state.get(pair) ?? 0) + 1);
  }

  for (let i = 0; i < 40; i++) {
    state = performInsertionsFast(state, rules);
  }

  const count = new Map<string, number>();

  for (const [pair, amount] of state.entries()) {
    count.set(pair[0], (count.get(pair[0]) ?? 0) + amount);
    count.set(pair[1], (count.get(pair[1]) ?? 0) + amount);
  }

  const sorted = [...count.entries()].sort(([char, quantity], [chartwo, quantity2]) => quantity2 - quantity);

  return ((sorted[0][1] - sorted[sorted.length -1][1]) + 1) / 2; // as each letter is counted in two pairs
};

