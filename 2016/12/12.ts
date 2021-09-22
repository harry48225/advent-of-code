type Registers = {
  a: number,
  b: number,
  c: number,
  d: number
};

export enum InstructionVerbs {
  COPY = 'cpy',
  INCREASE = 'inc',
  DECREASE = 'dec',
  JUMP_IF_NOT_ZERO = 'jnz'
};

type Instruction = {
  verb: InstructionVerbs,
  args: any[]
};

export type State = {
  registers: Registers,
  programCounter: number
};

const deepCloneState = (state: State): State => JSON.parse(JSON.stringify(state));

const isDigit = (candidate: string) => (/\d+/.test(candidate));
export const parseInstruction = (rawInstruction: string): Instruction => {
  const splitInstruction = rawInstruction.split(" ");
  return {verb: splitInstruction[0] as InstructionVerbs, args: splitInstruction.slice(1)};
};

const isRegister = (candidate: string): candidate is 'a' | 'b' | 'c' | 'd' => (
  ['a','b','c','d'].includes(candidate)
)

export const runInstruction = (instruction: Instruction, state: State): State => {
  let newState = deepCloneState(state);

  switch (instruction.verb) {
    case InstructionVerbs.COPY:
      const value = isRegister(instruction.args[0]) ? state.registers[instruction.args[0]] : parseInt(instruction.args[0]);
      if (!isRegister(instruction.args[1])) {
        throw Error(`invalid register ${instruction.args[1]}`)
      }
      newState.registers[instruction.args[1]] = value;
      break;
    case InstructionVerbs.INCREASE:
      if (!isRegister(instruction.args[0])) {
        throw Error(`invalid register ${instruction.args[1]}`)
      }
      newState.registers[instruction.args[0]] += 1;
      break;
    case InstructionVerbs.DECREASE:
      if (!isRegister(instruction.args[0])) {
        throw Error(`invalid register ${instruction.args[1]}`)
      }
      newState.registers[instruction.args[0]] -= 1;
      break;
    case InstructionVerbs.JUMP_IF_NOT_ZERO:
      const condition = isRegister(instruction.args[0]) ? state.registers[instruction.args[0]] : parseInt(instruction.args[0]);
      const jumpAmount = isRegister(instruction.args[1]) ? state.registers[instruction.args[1]] : parseInt(instruction.args[1]);
      if (condition != 0) {
        newState.programCounter += jumpAmount - 1;
      }
      break;
  }
  newState.programCounter++;
  return newState;
};

export const runProgram = (instructions: Instruction[], initalState: State = {programCounter: 0, registers: {a:0,b:0,c:0,d:0}}): State => {
  let state: State = initalState;

  while (state.programCounter < instructions.length) {
    state = runInstruction(instructions[state.programCounter], state);
  }

  return state;
};

export const parseInput = (puzzleInput: string) => puzzleInput.trim().split("\n").map(line => parseInstruction(line.trim()));