import { resolve } from 'path';
import { readFile } from 'fs/promises';
import { partOne, partTwo } from './11';

const testInput = "5483143223\n2745854711\n5264556173\n6141336146\n6357385478\n4167524645\n2176841721\n6882881134\n4846848554\n5283751526";

describe("part one", () => {
  it("test input", () => {
    expect(partOne(testInput)).toEqual(1656);
  });

  it("puzzle input", async () => {
    const puzzleInput = (await readFile(resolve(__dirname, "input.txt"))).toString()
    expect(partOne(puzzleInput)).toEqual(1747);
  });
});

describe("part two", () => {
  it("test input", () => {
    expect(partTwo(testInput)).toEqual(195);
  });

  it("puzzle input", async () => {
    const puzzleInput = (await readFile(resolve(__dirname, "input.txt"))).toString()
    expect(partTwo(puzzleInput)).toEqual(505);
  });
});