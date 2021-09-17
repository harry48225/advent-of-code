import { Directions, findDigits, findNextPosition, parseInput, partTwoKeypad } from "./2";
import { readFile } from "fs/promises";
import { resolve } from "path";
import { partTwo } from "../1/1";

describe("2", () => {
    describe("part one", () => {
        it("test input short line", () => {
            expect(
                findNextPosition(
                    {row: 1, column: 1}, 
                    [Directions.Up, Directions.Left, Directions.Left],
                    [[true, true, true], [true, true, true], [true, true, true]]
                )
            ).toEqual({row: 0, column: 0})    
        });

        it("test input long line", () => {
            expect(
                findNextPosition(
                    {row: 2, column: 2}, 
                    [Directions.Left, Directions.Up, Directions.Right, Directions.Down, Directions.Left],
                    [[true, true, true], [true, true, true], [true, true, true]]
                    )
            ).toEqual({row: 2, column: 1})    
        });

        it("correct keypad digit for single line test input", () => {
            expect(findDigits([[Directions.Up, Directions.Left, Directions.Left]])).toEqual("1");
        });

        it("correct digits for test input", () => {
            expect(findDigits([
                [Directions.Up, Directions.Left, Directions.Left],
                [Directions.Right, Directions.Right, Directions.Down, Directions.Down, Directions.Down],
                [Directions.Left, Directions.Up, Directions.Right, Directions.Down, Directions.Left],
                [Directions.Up, Directions.Up, Directions.Up, Directions.Up, Directions.Down]
            ])).toEqual("1985");
        });

        it("correctly parses test input", () => {
            const testInput = 'ULL\nRRDDD\nLURDL\nUUUUD'
            expect(parseInput(testInput)).toEqual([
                [Directions.Up, Directions.Left, Directions.Left],
                [Directions.Right, Directions.Right, Directions.Down, Directions.Down, Directions.Down],
                [Directions.Left, Directions.Up, Directions.Right, Directions.Down, Directions.Left],
                [Directions.Up, Directions.Up, Directions.Up, Directions.Up, Directions.Down]
            ]);
        });

        it("puzzle input", async() => {
            const puzzleInput = parseInput((await readFile(resolve(__dirname, "./input.txt"))).toString());
            expect(findDigits(puzzleInput)).toEqual("56855");
        });
    });

    describe("part two", () => {
        it("test input", () => {
            expect(findDigits([
                [Directions.Up, Directions.Left, Directions.Left],
                [Directions.Right, Directions.Right, Directions.Down, Directions.Down, Directions.Down],
                [Directions.Left, Directions.Up, Directions.Right, Directions.Down, Directions.Left],
                [Directions.Up, Directions.Up, Directions.Up, Directions.Up, Directions.Down]
            ], partTwoKeypad, {row: 2, column: 0})).toEqual("5DB3");
        });

        it("puzzle input", async() => {
            const puzzleInput = parseInput((await readFile(resolve(__dirname, "./input.txt"))).toString());
            expect(findDigits(puzzleInput, partTwoKeypad, {row: 2, column: 0})).toEqual("B3C27");
        });
    });
});