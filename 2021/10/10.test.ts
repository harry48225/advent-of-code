import { resolve } from 'path';
import { readFile } from 'fs/promises';
import { findSyntaxError, partOne, partTwo } from './10';

const testInput = "[({(<(())[]>[[{[]{<()<>>\n[(()[<>])]({[<{<<[]>>(\n{([(<{}[<>[]}>{[]{[(<()>\n(((({<>}<{<{<>}{[]{[]{}\n[[<[([]))<([[{}[[()]]]\n[{[{({}]{}}([{[{{{}}([]\n{<[[]]>}<{[{[{[]{()[[[]\n[<(<(<(<{}))><([]([]()\n<{([([[(<>()){}]>(<<{{\n<{([{{}}[<[[[<>{}]]]>[]]";

describe("part one", () => {
  it("finds a syntax error", () => {
    expect(findSyntaxError("{([(<{}[<>[]}>{[]{[(<()>")).toEqual("}");
  });

  it("passes on correct line", () => {
    expect(findSyntaxError("[<>({}){}[([])<>]]")).toBeNull();
  });

  it("passes on incomplete line", () => {
    expect(findSyntaxError("(((")).toBeNull();
  });

  it("test input", () => {
    expect(partOne(testInput)).toEqual(26397);
  });

  it("puzzle input", async () => {
    const puzzleInput = (await readFile(resolve(__dirname, "input.txt"))).toString()
    expect(partOne(puzzleInput)).toEqual(366027);
  });
});

describe("part two", () => {
  it("test input", () => {
    expect(partTwo(testInput)).toEqual(288957);
  });

  it("puzzle input", async () => {
    const puzzleInput = (await readFile(resolve(__dirname, "input.txt"))).toString()
    expect(partTwo(puzzleInput)).toEqual(1118645287);
  });
});