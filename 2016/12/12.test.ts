import { InstructionVerbs, parseInput, parseInstruction, runInstruction, runProgram, State } from "./12";
import { readFile } from 'fs/promises';
import { resolve } from 'path';

describe("part one", () => {
  describe("parses instructions", () => {
    it("copy", () => {
      const testInstruction = "cpy x y";
      expect(parseInstruction(testInstruction)).toEqual({verb: InstructionVerbs.COPY, args: ['x', 'y']});
    });

    it("increase", () => {
      const testInstruction = "inc x";
      expect(parseInstruction(testInstruction)).toEqual({verb: InstructionVerbs.INCREASE, args: ['x']});
    });

    it("decrease", () => {
      const testInstruction = "dec x";
      expect(parseInstruction(testInstruction)).toEqual({verb: InstructionVerbs.DECREASE, args: ['x']});
    });

    it("jump if not zero", () => {
      const testInstruction = "jnz x y";
      expect(parseInstruction(testInstruction)).toEqual({verb: InstructionVerbs.JUMP_IF_NOT_ZERO, args: ['x', 'y']});
    });
  });

  describe("executes instructions", () => {
    describe("copy", () => {
      it("with register", () => {
        const testState: State = {registers: {a:1, b:0, c:0, d:0}, programCounter: 0};
        const testInstruction = {verb: InstructionVerbs.COPY, args: ['a', 'b']};
        expect(runInstruction(testInstruction, testState)).toEqual({registers: {a:1, b:1, c:0, d:0}, programCounter: 1});
      });

      it("with value", () => {
        const testState: State = {registers: {a:1, b:0, c:0, d:0}, programCounter: 0};
        const testInstruction = {verb: InstructionVerbs.COPY, args: ['5', 'b']};
        expect(runInstruction(testInstruction, testState)).toEqual({registers: {a:1, b:5, c:0, d:0}, programCounter: 1});
      });
    });

    it("increase", () => {
      const testState: State = {registers: {a:1, b:0, c:0, d:0}, programCounter: 0};
      const testInstruction = {verb: InstructionVerbs.INCREASE, args: ['a']};
      expect(runInstruction(testInstruction, testState)).toEqual({registers: {a:2, b:0, c:0, d:0}, programCounter: 1});
    });

    it("decrease", () => {
      const testState: State = {registers: {a:1, b:0, c:0, d:0}, programCounter: 0};
      const testInstruction = {verb: InstructionVerbs.DECREASE, args: ['a']};
      expect(runInstruction(testInstruction, testState)).toEqual({registers: {a:0, b:0, c:0, d:0}, programCounter: 1});
    });

    describe("jump if not zero", () => {
      it("jumps when not zero", () => {
        const testState: State = {registers: {a:1, b:0, c:0, d:0}, programCounter: 0};
        const testInstruction = {verb: InstructionVerbs.JUMP_IF_NOT_ZERO, args: [1, 2]};
        expect(runInstruction(testInstruction, testState)).toEqual({registers: {a:1, b:0, c:0, d:0}, programCounter: 2});
      });

      it("doesn't jump when zero", () => {
        const testState: State = {registers: {a:1, b:0, c:0, d:0}, programCounter: 0};
        const testInstruction = {verb: InstructionVerbs.JUMP_IF_NOT_ZERO, args: ['b', 2]};
        expect(runInstruction(testInstruction, testState)).toEqual({registers: {a:1, b:0, c:0, d:0}, programCounter: 1});
      });
    });
  });

  it("test input", () => {
    const testInput = parseInput("cpy 41 a\ninc a\ninc a\ndec a\njnz a 2\ndec a");
    const finalState = runProgram(testInput);
    expect(finalState.registers.a).toEqual(42);
  });

  it("puzzle input", async () => {
    const puzzleInput = (await readFile(resolve(__dirname, "./input.txt"))).toString();
    const finalState = runProgram(parseInput(puzzleInput));
    expect(finalState.registers.a).toEqual(318009);
  });
});

describe("part two", () => {
  it("puzzle input", async () => {
    const puzzleInput = (await readFile(resolve(__dirname, "./input.txt"))).toString();
    const initialState: State = {programCounter: 0, registers: {a:0, b:0, c:1, d:0}};
    const finalState = runProgram(parseInput(puzzleInput), initialState);
    expect(finalState.registers.a).toEqual(9227663);
  });
})