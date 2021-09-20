import { decompress, findDecompressedLength, partOne } from "./9";
import { readFile } from "fs/promises";
import { resolve } from "path";

describe("part one", () => {
  it("decompresses plain test input", () => {
    expect(decompress("ADVENT")).toEqual("ADVENT");
  });

  it("decompresses test input with single marker", () => {
    expect(decompress("A(1x5)BC")).toEqual("ABBBBBC");
  });

  it("decompresses test input starting with a marker", () => {
    expect(decompress("(3x3)XYZ")).toEqual("XYZXYZXYZ");
  });

  it("decompresses test input with two markers", () => {
    expect(decompress("A(2x2)BCD(2x2)EFG")).toEqual("ABCBCDEFEFG");
  });

  it("decompresses test input with decoy marker", () => {
    expect(decompress("(6x1)(1x3)A")).toEqual("(1x3)A");
  });

  it("decompresses final test input", () => {
    expect(decompress("X(8x2)(3x3)ABCY")).toEqual("X(3x3)ABC(3x3)ABCY");
  });

  it("puzzle input", async () => {
    const puzzleInput = (await readFile(resolve(__dirname, "./input.txt"))).toString();
    expect(partOne(puzzleInput)).toEqual(123908);
  });
});

describe("part two", () => {
  it("depth 1 test input", () => {
    expect(findDecompressedLength("(3x3)XYZ")).toEqual(9);
  });

  it("depth 2 test input", () => {
    expect(findDecompressedLength("X(8x2)(3x3)ABCY")).toEqual(20);
  });

  it("depth 5 test input", () => {
    expect(findDecompressedLength("(27x12)(20x12)(13x14)(7x10)(1x12)A")).toEqual(241920);
  });

  it("final test input", () => {
    expect(findDecompressedLength("(25x3)(3x3)ABC(2x3)XY(5x2)PQRSTX(18x9)(3x2)TWO(5x7)SEVEN")).toEqual(445);
  });

  it("puzzle input", async () => {
    const puzzleInput = (await readFile(resolve(__dirname, "./input.txt"))).toString();
    expect(findDecompressedLength(puzzleInput)).toEqual(10755693147);
  });
});