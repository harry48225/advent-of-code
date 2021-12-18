import { resolve } from 'path';
import { readFile } from 'fs/promises';
import { partOne, partTwo } from './17';

const testInput = "target area: x=20..30, y=-10..-5";

describe("part one", () => {
  it("test input", () => {
    expect(partOne(testInput)).toEqual(45);
  });

  it("puzzle input", async () => {
    const puzzleInput = (await readFile(resolve(__dirname, "input.txt"))).toString()
    expect(partOne(puzzleInput)).toEqual(7381);
  });
});

describe("part two", () => {
  it("test input", () => {
    expect(partTwo(testInput)).toEqual(112);
  });

  it("puzzle input", async () => {
    const puzzleInput = (await readFile(resolve(__dirname, "input.txt"))).toString()
    expect(partTwo(puzzleInput)).toEqual(3019);

    // 2840 too low
  });
});