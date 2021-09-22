import { findMinimumNumberOfSteps, FloorItemTypes, isStateComplete, isStateValid, parseInput, serialiseState } from "./11";
import { readFile } from 'fs/promises';
import { resolve } from 'path';

describe("part one", () => {
  describe("validates state", () => {
    it("all items seperate is valid", () => {
      expect(isStateValid({floors: [
        [],
        [{type: FloorItemTypes.Generator, element: 'L'}],
        [{type: FloorItemTypes.Generator, element: 'H'}],
        [{type: FloorItemTypes.Microchip, element: 'H'}, {type: FloorItemTypes.Microchip, element: 'L'}]], 
        elevatorLocation: 0})).toBeTruthy();
    });

    it("no shield required is valid", () => {
      expect(isStateValid({floors: [
        [],
        [{type: FloorItemTypes.Generator, element: 'L'}],
        [{type: FloorItemTypes.Generator, element: 'H'}, {type: FloorItemTypes.Microchip, element: 'H'}],
        [{type: FloorItemTypes.Microchip, element: 'L'}]], 
        elevatorLocation: 0})).toBeTruthy();
    })

    it("powered microchip needing shield is valid", () => {
      expect(isStateValid({floors: [
        [],
        [{type: FloorItemTypes.Generator, element: 'L'}, {type: FloorItemTypes.Generator, element: 'H'}, {type: FloorItemTypes.Microchip, element: 'H'}],
        [],
        [{type: FloorItemTypes.Microchip, element: 'L'}]], 
        elevatorLocation: 0})).toBeTruthy();
    });

    it("all on same floor is valid", () => {
      expect(isStateValid({floors: [
        [],
        [{type: FloorItemTypes.Generator, element: 'L'}, {type: FloorItemTypes.Generator, element: 'H'}, {type: FloorItemTypes.Microchip, element: 'H'}, {type: FloorItemTypes.Microchip, element: 'L'}],
        [],
        []], 
        elevatorLocation: 0})).toBeTruthy();
    });

    it("no shield is invalid", () => {
      expect(isStateValid({floors: [
        [],
        [{type: FloorItemTypes.Generator, element: 'L'}],
        [{type: FloorItemTypes.Generator, element: 'H'}, {type: FloorItemTypes.Microchip, element: 'L'}],
        [{type: FloorItemTypes.Microchip, element: 'H'}]], 
        elevatorLocation: 0})).toBeFalsy();
    });

    it("no shield is invalid with one protected microchip", () => {
      expect(isStateValid({floors: [
        [],
        [{type: FloorItemTypes.Generator, element: 'L'}],
        [{type: FloorItemTypes.Generator, element: 'H'}, {type: FloorItemTypes.Microchip, element: 'L'}, {type: FloorItemTypes.Microchip, element: 'H'}],
        []], 
        elevatorLocation: 0})).toBeFalsy();
    });
  });

  describe("parses input", () => {
    it("parses test input", () => {
      const testInput = "The first floor contains a hydrogen-compatible microchip and a lithium-compatible microchip.\nThe second floor contains a hydrogen generator.\nThe third floor contains a lithium generator.\nThe fourth floor contains nothing relevant.";
      expect(parseInput(testInput)).toEqual({floors: [
        [{type: FloorItemTypes.Microchip, element: 'hydrogen'}, {type: FloorItemTypes.Microchip, element: 'lithium'}],
        [{type: FloorItemTypes.Generator, element: 'hydrogen'}],
        [{type: FloorItemTypes.Generator, element: 'lithium'}],
        []], 
        elevatorLocation: 0})
    })
  });
  
  it("detects complete state", () => {
    expect(isStateComplete({floors: [
      [],
      [],
      [],
      [{type: FloorItemTypes.Microchip, element: 'H'}, {type: FloorItemTypes.Generator, element: 'H'}, {type: FloorItemTypes.Microchip, element: 'L'}, {type: FloorItemTypes.Generator, element: 'L'}]], 
      elevatorLocation: 0})).toBeTruthy();
  });

  it("serialises state", () => {
    expect(serialiseState({floors: [
      [{type: FloorItemTypes.Microchip, element: 'hydrogen'}, {type: FloorItemTypes.Microchip, element: 'lithium'}],
      [{type: FloorItemTypes.Generator, element: 'hydrogen'}],
      [{type: FloorItemTypes.Generator, element: 'lithium'}],
      []], 
      elevatorLocation: 0})).toEqual("00102");
  })

  it("test input", () => {
    const testInput = "The first floor contains a hydrogen-compatible microchip and a lithium-compatible microchip.\nThe second floor contains a hydrogen generator.\nThe third floor contains a lithium generator.\nThe fourth floor contains nothing relevant.";
    expect(findMinimumNumberOfSteps(parseInput(testInput))).toEqual(11);
  });

  it("puzzle input", async () => {
    const puzzleInput = (await readFile(resolve(__dirname, "./input.txt"))).toString();
    expect(findMinimumNumberOfSteps(parseInput(puzzleInput))).toEqual(33);
  });
});