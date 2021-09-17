import { InstructionDirections, parseInput, partTwo, shortestPath, turnLeft, turnRight } from "./1";
import { readFile } from 'fs/promises';
import { resolve } from 'path';

describe("1", () => {

    it("turns vector left", () => {
        expect(turnLeft({x: 1, y: 2})).toEqual({x: -2, y: 1});
    });

    it("turns vector right", () => {
        expect(turnRight({x: -2, y: 10})).toEqual({x: 10, y: 2});
    });

    it("1st test input", () => {
        expect(shortestPath(
        [
            {direction: InstructionDirections.RIGHT, amount: 2},
            {direction: InstructionDirections.LEFT, amount: 3}
        ]
        )).toEqual(5);
    });

    it("2nd test input", () => {
        expect(shortestPath(
        [
            {direction: InstructionDirections.RIGHT, amount: 2},
            {direction: InstructionDirections.RIGHT, amount: 2},
            {direction: InstructionDirections.RIGHT, amount: 2}
        ]
        )).toEqual(2);
    });

    it("3rd test input", () => {
        expect(shortestPath(
        [
            {direction: InstructionDirections.RIGHT, amount: 5},
            {direction: InstructionDirections.LEFT, amount: 5},
            {direction: InstructionDirections.RIGHT, amount: 5},
            {direction: InstructionDirections.RIGHT, amount: 3}
        ]
        )).toEqual(12);
    });

    it("parsing single instruction input", () => {
        expect(parseInput("L1")).toEqual([{direction: InstructionDirections.LEFT, amount: 1}]);
    });

    it("parsing multi instruction input", () => {
        expect(parseInput("L1, R1, R12"))
        .toEqual(
            [{direction: InstructionDirections.LEFT, amount: 1},
            {direction: InstructionDirections.RIGHT, amount: 1},
            {direction: InstructionDirections.RIGHT, amount: 12}]
        );
    });

    it("part one", async () => {
        const input = (await readFile(resolve(__dirname, "./input.txt"))).toString();
        expect(shortestPath(parseInput(input))).toEqual(279);
    });

    it("part two test input", () => {
        expect(partTwo([
            {direction: InstructionDirections.RIGHT, amount: 8},
            {direction: InstructionDirections.RIGHT, amount: 4},
            {direction: InstructionDirections.RIGHT, amount: 4},
            {direction: InstructionDirections.RIGHT, amount: 8}
        ])).toEqual(4);
    });

    it("part two extended test input", () => {
        expect(partTwo([
            {direction: InstructionDirections.LEFT, amount: 12},
            {direction: InstructionDirections.LEFT, amount: 1},
            {direction: InstructionDirections.LEFT, amount: 5},
            {direction: InstructionDirections.LEFT, amount: 1},
            {direction: InstructionDirections.RIGHT, amount: 10}
        ])).toEqual(7);
    });

    it("part two", async () => {
        const input = (await readFile(resolve(__dirname, "./input.txt"))).toString();
        expect(partTwo(parseInput(input))).toEqual(163);
    });
})