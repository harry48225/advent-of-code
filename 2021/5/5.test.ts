import { resolve } from 'path';
import { readFile } from 'fs/promises';
import { markPoint, numberOfMarks, parseLine, partOne, partTwo, Point, PointList } from './5';

describe("part one", () => {
  it("marks point", () => {
    const a: Point = {x:1, y:2};
    const b: Point = {x:1, y:2};


    const map: PointList = new Map<string, number>();
    expect(numberOfMarks(a, map)).toEqual(0);
    expect(numberOfMarks(b, map)).toEqual(0);
    
    markPoint(a, map);

    expect(numberOfMarks(a, map)).toEqual(1);
    expect(numberOfMarks(b, map)).toEqual(1);

    markPoint(b, map);
    expect(numberOfMarks(a, map)).toEqual(2);
  });

  it("parses a line", () => {
    expect(parseLine("0,9 -> 5,9")).toEqual({start: {x: 0, y: 9}, end: {x:5, y:9}});
  })

  it("test input", () => {
    const testInput = "0,9 -> 5,9\n8,0 -> 0,8\n9,4 -> 3,4\n2,2 -> 2,1\n7,0 -> 7,4\n6,4 -> 2,0\n0,9 -> 2,9\n3,4 -> 1,4\n0,0 -> 8,8\n5,5 -> 8,2";
    expect(partOne(testInput)).toEqual(5);
  });

  it("puzzle input", async () => {
    const puzzleInput = (await readFile(resolve(__dirname, "input.txt"))).toString()
    expect(partOne(puzzleInput)).toEqual(7473);
  });
});

describe("part two", () => {
  it("test input", () => {
    const testInput = "0,9 -> 5,9\n8,0 -> 0,8\n9,4 -> 3,4\n2,2 -> 2,1\n7,0 -> 7,4\n6,4 -> 2,0\n0,9 -> 2,9\n3,4 -> 1,4\n0,0 -> 8,8\n5,5 -> 8,2";
    expect(partTwo(testInput)).toEqual(12);
  });

  it("puzzle input", async () => {
    const puzzleInput = (await readFile(resolve(__dirname, "input.txt"))).toString()
    expect(partTwo(puzzleInput)).toEqual(24164);
  });
});