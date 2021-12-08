import { resolve } from 'path';
import { readFile } from 'fs/promises';
import { partOne, partTwo } from './6';

const testInput = "3,4,3,1,2";

describe("part one", () => {
  it("test input", () => {
    expect(partOne(testInput)).toEqual(5934);
  });

  it("puzzle input", async () => {
    const puzzleInput = (await readFile(resolve(__dirname, "input.txt"))).toString()
    expect(partOne(puzzleInput)).toEqual(361169);
  });
});

describe("part two", () => {
  it("test input", () => {
    expect(partTwo(testInput)).toEqual(26984457539);
  });

  it("puzzle input", async () => {
    const puzzleInput = (await readFile(resolve(__dirname, "input.txt"))).toString()
    expect(partTwo(puzzleInput)).toEqual(1634946868992);
  });
});