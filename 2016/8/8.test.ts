import { CommandVerb, parseCommand, partOne, rect, rotateColumn, rotateRow } from "./8"
import { readFile } from "fs/promises";
import { resolve } from "path";

describe("part one", () => {
  it("rect", () => {
    const display = [[false, false, false],
                    [false, false, false],
                    [false, false, false]];
    expect(rect(2, 1, display)).toEqual([[true, true, false],
                                        [false, false, false],
                                        [false, false, false]]);
  });

  it("rotate row", () => {
    const display = [[true, true, false],
                    [false, false, false],
                    [false, false, false]];
    expect(rotateRow(0, 4, display)).toEqual([[false, true, true],
                                              [false, false, false],
                                              [false, false, false]]);
  });

  it("rotate column", () => {
    const display = [[true, true, false],
                    [true, false, false],
                    [false, false, false]];
    expect(rotateColumn(0, 4, display)).toEqual([[false, true, false],
                                              [true, false, false],
                                              [true, false, false]]);
  });

  describe("parses command", () => {
    it("rect", () => {
      expect(parseCommand("rect 1x2")).toEqual({verb: CommandVerb.Rect, args: [1,2]});
    });

    it("rotate row", () => {
      expect(parseCommand("rotate row y=4 by 20")).toEqual({verb: CommandVerb.RotateRow, args: [4, 20]});
    });

    it("rotate column", () => {
      expect(parseCommand("rotate column x=19 by 10")).toEqual({verb: CommandVerb.RotateColumn, args: [19, 10]});
    });
  });

  it("puzzle input", async () => {
    const puzzleInput = (await readFile(resolve(__dirname, "./input.txt"))).toString();
    expect(partOne(puzzleInput).on).toEqual(110);
  });
});

describe("part two", () => {
  it("puzzle input", async () => {
    const puzzleInput = (await readFile(resolve(__dirname, "./input.txt"))).toString();
    console.log(partOne(puzzleInput).display.map(line => line.map(pixel => pixel ? "#" : ".")).map(line => line.join("")).join("\n"));
  })
  
})