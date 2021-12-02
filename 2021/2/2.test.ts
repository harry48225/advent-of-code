import { CommandVerb, parseCommand, partOne, partTwo, processCommand } from "./2"
import { readFile } from 'fs/promises';
import { resolve } from 'path';

describe("part one", () => {
  it("parses forward command", () => {
    expect(parseCommand("forward 5")).toEqual({verb: CommandVerb.Forward, arg: 5});
  });

  it("parses down command", () => {
    expect(parseCommand("down 5")).toEqual({verb: CommandVerb.Down, arg: 5});
  });

  it("parses up command", () => {
    expect(parseCommand("up 3")).toEqual({verb: CommandVerb.Up, arg: 3});
  });

  it("processes forward command", () => {
    expect(processCommand({depth: 0, horizontal: 0},{verb: CommandVerb.Forward, arg: 1})).toEqual({depth: 0, horizontal: 1});
  });

  it("processes down command", () => {
    expect(processCommand({depth: 0, horizontal: 0},{verb: CommandVerb.Down, arg: 1})).toEqual({depth: 1, horizontal: 0});
  });

  it("processes up command", () => {
    expect(processCommand({depth: 6, horizontal: 0},{verb: CommandVerb.Up, arg: 1})).toEqual({depth: 5, horizontal: 0});
  });

  it("test input", () => {
    const testInput = "forward 5\ndown 5\nforward 8\nup 3\ndown 8\nforward 2"
    expect(partOne(testInput)).toEqual(150);
  });

  it("puzzle input", async () => {
    const puzzleInput = (await readFile(resolve(__dirname, "input.txt"))).toString();
    expect(partOne(puzzleInput)).toEqual(1714950);
  });
});

describe("part two", () => {
  it("test input", () => {
    const testInput = "forward 5\ndown 5\nforward 8\nup 3\ndown 8\nforward 2"
    expect(partTwo(testInput)).toEqual(900);
  });

  it("puzzle input", async () => {
    const puzzleInput = (await readFile(resolve(__dirname, "input.txt"))).toString();
    expect(partTwo(puzzleInput)).toEqual(1281977850);
  });
})