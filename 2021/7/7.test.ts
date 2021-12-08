import { resolve } from 'path';
import { readFile } from 'fs/promises';
import { partOne, partTwo } from './7';

const testInput = "16,1,2,0,4,2,7,1,2,14";

describe("part one", () => {
  it("test input", () => {
    expect(partOne(testInput)).toEqual(37);
  });

  it("puzzle input", async () => {
    const puzzleInput = (await readFile(resolve(__dirname, "input.txt"))).toString()
    expect(partOne(puzzleInput)).toEqual(347011);
  });
});

describe("part two", () => {
  it("test input", () => {
    expect(partTwo(testInput)).toEqual(168);
  });

  it("puzzle input", async () => {
    const puzzleInput = (await readFile(resolve(__dirname, "input.txt"))).toString()
    expect(partTwo(puzzleInput)).toEqual(98363777);
  });
});