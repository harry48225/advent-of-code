import { calculateScore, checkForWin, markBoard, parseBoard, parseInput, partOne, partTwo } from "./4"
import { resolve } from 'path';
import { readFile } from 'fs/promises';

describe("part one", () => {
  it("parses a board", () => {
    expect(parseBoard("14 21 17\n12 23 67\n24 67 89")).toEqual([[14,21,17],[12,23,67],[24,67,89]]);
  });

  it("marks a board", () => {
    expect(markBoard([[14,21,17],[12,23,67],[24,67,89]], 14)).toEqual([['',21,17],[12,23,67],[24,67,89]])
  });

  it("doesn't find non-existent win", () => {
    const board = [[14,21,17],[12,23,67],[24,67,89]];
    expect(checkForWin(board)).toBeFalsy();
  });

  it("finds horizontal win", () => {
    const board = [[14,21,17],["","",""],[24,"",89]];
    expect(checkForWin(board)).toBeTruthy();
  });

  it("finds vertical win", () => {
    const board = [[14,"",17],[23,"",""],[24,"",89]];
    expect(checkForWin(board)).toBeTruthy();
  });

  it("calculates score", () => {
    const board = [[14,"",17],[23,"",""],[24,"",89]];
    expect(calculateScore(board)).toEqual(14+17+23+24+89); 
  });

  it("parses input", () => {
    const testInput = "7,4,9,5,11,17,23,2,0,14,21,24,10,16,13,6,15,25,12,22,18,20,8,19,3,26,1\n\n22 13 17 11  0\n 8  2 23  4 24\n21  9 14 16  7\n 6 10  3 18  5\n 1 12 20 15 19\n\n 3 15  0  2 22\n 9 18 13 17  5\n19  8  7 25 23\n20 11 10 24  4\n14 21 16 12  6\n\n14 21 17 24  4\n10 16 15  9 19\n18  8 23 26 20\n22 11 13  6  5\n 2  0 12  3  7\n";
    expect(parseInput(testInput)).toEqual(
      {"tape":[7,4,9,5,11,17,23,2,0,14,21,24,10,16,13,6,15,25,12,22,18,20,8,19,3,26,1],"boards":[[[22,13,17,11,0],[8,2,23,4,24],[21,9,14,16,7],[6,10,3,18,5],[1,12,20,15,19]],[[3,15,0,2,22],[9,18,13,17,5],[19,8,7,25,23],[20,11,10,24,4],[14,21,16,12,6]],[[14,21,17,24,4],[10,16,15,9,19],[18,8,23,26,20],[22,11,13,6,5],[2,0,12,3,7]]]}
    )
  });

  it("test input", () => {
    const testInput = "7,4,9,5,11,17,23,2,0,14,21,24,10,16,13,6,15,25,12,22,18,20,8,19,3,26,1\n\n22 13 17 11  0\n 8  2 23  4 24\n21  9 14 16  7\n 6 10  3 18  5\n 1 12 20 15 19\n\n 3 15  0  2 22\n 9 18 13 17  5\n19  8  7 25 23\n20 11 10 24  4\n14 21 16 12  6\n\n14 21 17 24  4\n10 16 15  9 19\n18  8 23 26 20\n22 11 13  6  5\n 2  0 12  3  7\n";
    expect(partOne(testInput)).toEqual(4512);
  });

  it("puzzle input", async () => {
    const puzzleInput = (await readFile(resolve(__dirname, "input.txt"))).toString()
    expect(partOne(puzzleInput)).toEqual(12796);
  });
})

describe("part two", () => {
  it("test input", () => {
    const testInput = "7,4,9,5,11,17,23,2,0,14,21,24,10,16,13,6,15,25,12,22,18,20,8,19,3,26,1\n\n22 13 17 11  0\n 8  2 23  4 24\n21  9 14 16  7\n 6 10  3 18  5\n 1 12 20 15 19\n\n 3 15  0  2 22\n 9 18 13 17  5\n19  8  7 25 23\n20 11 10 24  4\n14 21 16 12  6\n\n14 21 17 24  4\n10 16 15  9 19\n18  8 23 26 20\n22 11 13  6  5\n 2  0 12  3  7\n";
    expect(partTwo(testInput)).toEqual(1924);
  });

  it("puzzle input", async () => {
    const puzzleInput = (await readFile(resolve(__dirname, "input.txt"))).toString()
    expect(partTwo(puzzleInput)).toEqual(18063);
  });
})