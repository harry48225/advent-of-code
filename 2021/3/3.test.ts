import { countOccurances, partOne, partTwo } from "./3";
import { readFile } from 'fs/promises';
import { resolve } from 'path';


describe("part one", () => {
  it("counts single occurances", () => {
    expect(countOccurances([[0,1,1]])).toEqual([0,1,1]);
  });

  it("counts multiple occurances", () => {
    expect(countOccurances([[0,1,1],
                                              [0,1,1],
                                              [1,0,0]])).toEqual([1,2,2]);
  });

  it("test input", () => {
    const testInput = "00100\n11110\n10110\n10111\n10101\n01111\n00111\n11100\n10000\n11001\n00010\n01010";
    expect(partOne(testInput)).toEqual(198);
  });

  it("puzzle input", async () => {
    const puzzleInput = (await readFile(resolve(__dirname, "input.txt"))).toString();
    expect(partOne(puzzleInput)).toEqual(3009600);
  });
});

describe("part two", () => {
  it("test input", () => {
    const testInput = "00100\n11110\n10110\n10111\n10101\n01111\n00111\n11100\n10000\n11001\n00010\n01010";
    expect(partTwo(testInput)).toEqual(230);
  });

  it("puzzle input", async () => {
    const puzzleInput = (await readFile(resolve(__dirname, "input.txt"))).toString();
    expect(partTwo(puzzleInput)).toEqual(6940518);
  });
});