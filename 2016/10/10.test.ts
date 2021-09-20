import { parseInstructions, partOne, partTwo, runSimulation } from "./10";
import { readFile } from "fs/promises";
import { resolve } from "path";

describe("part one", () => {
  describe("parses instructions", () => {
    it("absolute", () => {
      expect(parseInstructions(["value 5 goes to bot 2"])).toEqual({absolutes: new Map([[5, "bot 2"]]), gifts: new Map<number, string>()});
    });

    it("gifts", () => {
      expect(parseInstructions(["bot 2 gives low to bot 1 and high to bot 0"])).toEqual({absolutes: new Map(), gifts: new Map([["bot 2", {low: "bot 1", high: "bot 0"}]])});
    });
  });

  it("test input", () => {
    expect(runSimulation(parseInstructions(["value 5 goes to bot 2",
                                            "bot 2 gives low to bot 1 and high to bot 0",
                                            "value 3 goes to bot 1",
                                            "bot 1 gives low to output 1 and high to bot 0",
                                            "bot 0 gives low to output 2 and high to output 0",
                                            "value 2 goes to bot 2"])).bots.get("bot 2")?.sort()).toEqual([2, 5].sort());
  });

  it("puzzle input", async () => {
    const puzzleInput = (await readFile(resolve(__dirname, "./input.txt"))).toString();
    expect(partOne(puzzleInput)).toEqual("bot 181");
  });
});

describe("part two", () => {
  it("puzzle input", async () => {
    const puzzleInput = (await readFile(resolve(__dirname, "./input.txt"))).toString();
    expect(partTwo(puzzleInput)).toEqual(12567);
  });
})