import { resolve } from 'path';
import { readFile } from 'fs/promises';
import { doHomework, parseInput, partOne, partTwo, reduceNumber } from './18';

const testInput = "[[[0,[5,8]],[[1,7],[9,6]]],[[4,[1,2]],[[1,4],2]]]\n[[[5,[2,8]],4],[5,[[9,9],0]]]\n[6,[[[6,2],[5,6]],[[7,6],[4,7]]]]\n[[[6,[0,7]],[0,9]],[4,[9,[9,0]]]]\n[[[7,[6,4]],[3,[1,3]]],[[[5,5],1],9]]\n[[6,[[7,3],[3,2]]],[[[3,8],[5,7]],4]]\n[[[[5,4],[7,7]],8],[[8,3],8]]\n[[9,3],[[9,9],[6,[4,9]]]]\n[[2,[[7,7],7]],[[5,8],[[9,3],[0,2]]]]\n[[[[5,2],5],[8,[3,7]]],[[5,[7,5]],[4,4]]]\n";

describe("part one", () => {
  it("handles signle explodes", () => {
    expect(reduceNumber("[[[[[9,8],1],2],3],4]")).toEqual("[[[[0,9],2],3],4]");
    expect(reduceNumber("[7,[6,[5,[4,[3,2]]]]]")).toEqual("[7,[6,[5,[7,0]]]]");
    expect(reduceNumber("[[6,[5,[4,[3,2]]]],1]")).toEqual("[[6,[5,[7,0]]],3]");
    expect(reduceNumber("[[3,[2,[8,0]]],[9,[5,[4,[3,2]]]]]")).toEqual("[[3,[2,[8,0]]],[9,[5,[7,0]]]]");
  })

  it("does homework", () => {
    const homework = ["[1,1]","[2,2]","[3,3]","[4,4]"];
    expect(doHomework(homework)).toEqual("[[[[1,1],[2,2]],[3,3]],[4,4]]");

    expect(doHomework(["[1,1]","[2,2]","[3,3]","[4,4]","[5,5]"])).toEqual("[[[[3,0],[5,3]],[4,4]],[5,5]]");
    expect(
      doHomework(
        ["[[[0,[4,5]],[0,0]],[[[4,5],[2,6]],[9,5]]]","[7,[[[3,7],[4,3]],[[6,3],[8,8]]]]","[[2,[[0,8],[3,4]]],[[[6,7],1],[7,[1,6]]]]","[[[[2,4],7],[6,[0,5]]],[[[6,8],[2,8]],[[2,1],[4,5]]]]","[7,[5,[[3,8],[1,4]]]]","[[2,[2,2]],[8,[8,1]]]","[2,9]","[1,[[[9,3],9],[[9,0],[0,7]]]]","[[[5,[7,4]],7],1]","[[[[4,2],2],6],[8,7]]"]
        )).toEqual("[[[[8,7],[7,7]],[[8,6],[7,7]]],[[[0,7],[6,6]],[8,7]]]");
  })

  it("test input", () => {
    expect(partOne(testInput)).toEqual(4140);
  });

  it("puzzle input", async () => {
    const puzzleInput = (await readFile(resolve(__dirname, "input.txt"))).toString()
    expect(partOne(puzzleInput)).toEqual(4391);
  });
});

describe("part two", () => {
  it("test input", () => {
    expect(partTwo(testInput)).toEqual(3993);
  });

  it("puzzle input", async () => {
    const puzzleInput = (await readFile(resolve(__dirname, "input.txt"))).toString()
    expect(partTwo(puzzleInput)).toEqual(4626);
  });
});