import { resolve } from 'path';
import { readFile } from 'fs/promises';
import { GameOutcomes, partOne, partTwo, Player, playMultiverseGame } from './21';

const testInput = "Player 1 starting position: 4\nPlayer 2 starting position: 8";

describe("part one", () => {
  it("test input", () => {
    expect(partOne(testInput)).toEqual(739785);
  });

  it("puzzle input", async () => {
    const puzzleInput = (await readFile(resolve(__dirname, "input.txt"))).toString()
    expect(partOne(puzzleInput)).toEqual(504972);
  });
});

describe("part two", () => {
  it("simple game", () => {
    const seenStates = new Map<string, GameOutcomes>();
    const players: Player[] = [{score: 11, position: 1, name:'0'},{score: 11, position: 1, name:'1'}]
    //const outcome = playMultiverseGame(players, seenStates)
    //expect(seenStates).toEqual(1);
  })

  it("test input", () => {
    expect(partTwo(testInput)).toEqual(444356092776315);
  });

  it("puzzle input", async () => {
    const puzzleInput = (await readFile(resolve(__dirname, "input.txt"))).toString()
    expect(partTwo(puzzleInput)).toEqual(446968027750017);
  });
});