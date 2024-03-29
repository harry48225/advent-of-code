import { resolve } from 'path';
import { readFile } from 'fs/promises';
import { partOne, partTwo } from './template';

const testInput = "";

describe("part one", () => {
  it("test input", () => {
    expect(partOne(testInput)).toEqual(-1);
  });

  it("puzzle input", async () => {
    const puzzleInput = (await readFile(resolve(__dirname, "input.txt"))).toString()
    expect(partOne(puzzleInput)).toEqual(-1);
  });
});

describe("part two", () => {
  it("test input", () => {
    expect(partTwo(testInput)).toEqual(-1);
  });

  it("puzzle input", async () => {
    const puzzleInput = (await readFile(resolve(__dirname, "input.txt"))).toString()
    expect(partTwo(puzzleInput)).toEqual(-1);
  });
});