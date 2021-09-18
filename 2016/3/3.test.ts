import { isTriangleValid, numberOfValidTriangles, parseTrianglesByColumn } from "./3";
import { readFile } from 'fs/promises';
import { resolve } from 'path';

describe("day 3", () => {
  describe("part one", () => {
    it("identifies valid triangle", () => {
      expect(isTriangleValid([3,4,5])).toBeTruthy();
    });

    it("identifies invalid triangle", () => {
      expect(isTriangleValid([5,10,25])).toBeFalsy();
    });

    it("puzzle input", async () => {
      const puzzleInput = (await readFile(resolve(__dirname, "./input.txt"))).toString().trim();
      const triangles = puzzleInput
      .split('\n')
      .map(row => 
        row
        .trim()
        .split(/\s+/g)
        .map(digit => parseInt(digit)
      ));
      expect(numberOfValidTriangles(triangles)).toEqual(983);
    });
  });

  describe("part two", () => {
    it("parsing by column", () => {
      const testInput = '101 301 501\n102 302 502\n103 303 503\n201 401 601\n202 402 602\n203 403 603\n'
      expect(parseTrianglesByColumn(testInput)).toEqual([[101, 102, 103], [201,202,203],[301,302,303],[401,402,403],[501,502,503],[601,602,603]]);
    });

    it("puzzle input", async () => {
      const puzzleInput = (await readFile(resolve(__dirname, "./input.txt"))).toString();
      expect(numberOfValidTriangles(parseTrianglesByColumn(puzzleInput))).toEqual(1836);
    });
  })
})