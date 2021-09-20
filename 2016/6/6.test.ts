import { decodeCharacter, partOne, partTwo } from "./6"
import { readFile } from 'fs/promises';
import { resolve } from 'path';

describe("part one", () => {
  it("finds most frequent letter", () => {
    expect(decodeCharacter(['a','a','b'])).toEqual('a');
  });

  it("decodes test input", () => {
    const testInput = 'eedadn\ndrvtee\neandsr\nraavrd\natevrs\ntsrnev\nsdttsa\nrasrtv\nnssdts\nntnada\nsvetve\ntesnvt\nvntsnd\nvrdear\ndvrsen\nenarar'.split('\n');
    expect(partOne(testInput)).toEqual('easter');
  });

  it("puzzle input", async () => {
    const puzzleInput = (await readFile(resolve(__dirname, "./input.txt"))).toString().trim().split("\n");
    expect(partOne(puzzleInput)).toEqual("kqsdmzft");
  });
});

describe("part two", () => {
  it("finds least frequent letter", () => {
    expect(decodeCharacter(['a','a','b'], true)).toEqual('b');
  });

  it("decodes test input", () => {
    const testInput = 'eedadn\ndrvtee\neandsr\nraavrd\natevrs\ntsrnev\nsdttsa\nrasrtv\nnssdts\nntnada\nsvetve\ntesnvt\nvntsnd\nvrdear\ndvrsen\nenarar'.split('\n');
    expect(partTwo(testInput)).toEqual('advent');
  });

  it("puzzle input", async () => {
    const puzzleInput = (await readFile(resolve(__dirname, "./input.txt"))).toString().trim().split("\n");
    expect(partTwo(puzzleInput)).toEqual("tpooccyo");
  });
});