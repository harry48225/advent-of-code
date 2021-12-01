import { aggregate, countIncreases } from "./1";
import { readFile } from 'fs/promises';
import { resolve } from 'path';


describe("part one", () => {
  it("example input", () => {

    const testInput = [199, 200, 208, 210, 200, 207, 240, 269, 260, 263];
    expect(countIncreases(testInput)).toBeLessThanOrEqual(7);
  });

  it("puzzle input", async () => {
    const raw_input = (await readFile(resolve(__dirname, "input.txt"))).toString();
    const puzzleInput = raw_input.split("\n").map(depth => parseInt(depth));
    expect(countIncreases(puzzleInput)).toEqual(1390);
  });
})

describe("part two", () => {
  it("aggregates", () => {
    expect(aggregate([199,200,208,210])).toEqual([607, 618]);
  });

  it("test input", () => {
    const testInput = [199, 200, 208, 210, 200, 207, 240, 269, 260, 263];
    expect(countIncreases(aggregate(testInput))).toEqual(5);
  });

  it("puzzle input", async () => {
    const raw_input = (await readFile(resolve(__dirname, "input.txt"))).toString();
    const puzzleInput = raw_input.split("\n").map(depth => parseInt(depth));
    expect(countIncreases(aggregate(puzzleInput))).toEqual(1457);
  });
})