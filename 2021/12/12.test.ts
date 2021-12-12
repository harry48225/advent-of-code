import { resolve } from 'path';
import { readFile } from 'fs/promises';
import { partOne, partTwo } from './12';

const testInput = "dc-end\nHN-start\nstart-kj\ndc-start\ndc-HN\nLN-dc\nHN-end\nkj-sa\nkj-HN\nkj-dc";

describe("part one", () => {

  it("small test input", () => {
    const input = "start-A\nstart-b\nA-c\nA-b\nb-d\nA-end\nb-end";
    expect(partOne(input)).toEqual(10);
  })

  it("test input", () => {
    expect(partOne(testInput)).toEqual(19);
  });

  it("puzzle input", async () => {
    const puzzleInput = (await readFile(resolve(__dirname, "input.txt"))).toString()
    expect(partOne(puzzleInput)).toEqual(5333);
  });
});

describe("part two", () => {
  it("test input", () => {
    const smallTestInput = "start-A\nstart-b\nA-c\nA-b\nb-d\nA-end\nb-end";
    expect(partTwo(smallTestInput)).toEqual(36);
  });

  it("puzzle input", async () => {
    const puzzleInput = (await readFile(resolve(__dirname, "input.txt"))).toString()
    expect(partTwo(puzzleInput)).toEqual(146553);
  });
});