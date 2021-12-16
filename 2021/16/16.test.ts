import { resolve } from 'path';
import { readFile } from 'fs/promises';
import { inputStream, partOne, partTwo, readPackets } from './16';

const testInput = "A0016C880162017C3686B18A3D4780";

describe("part one", () => {
  it("reads literal packet", () => {
    expect(readPackets(inputStream("D2FE28"))).toEqual([{
      "subpackets": undefined,
      "typeId": 4,
      "value": 2021,
      "version": 6}]);
  });

  it("reads packet with two subpackets, length type 0", () => {
    expect(readPackets(inputStream("38006F45291200"))).toEqual([
      {
        "subpackets": [
          {
            "subpackets": undefined,
            "typeId": 4,
            "value": 10,
            "version": 6,
          },
          {
            "subpackets": undefined,
            "typeId": 4,
            "value": 20,
            "version": 2,
          },
        ],
        "typeId": 6,
        "value": undefined,
        "version": 1,
      },
    ]);
  });

  it("reads packet with length type 1", () => {
    expect(readPackets(inputStream("EE00D40C823060"))).toEqual([
      {
        "subpackets": [
          {
            "subpackets": undefined,
            "typeId": 4,
            "value": 1,
            "version": 2,
          },
          {
            "subpackets": undefined,
            "typeId": 4,
            "value": 2,
            "version": 4,
          },
          {
            "subpackets": undefined,
            "typeId": 4,
            "value": 3,
            "version": 1,
          },
        ],
        "typeId": 3,
        "value": undefined,
        "version": 7,
      },
    ]);
  })

  it("test input", () => {
    expect(partOne(testInput)).toEqual(31);
    expect(partOne("8A004A801A8002F478")).toEqual(16);
    expect(partOne("620080001611562C8802118E34")).toEqual(12);
    expect(partOne("C0015000016115A2E0802F182340")).toEqual(23);
  });

  it("puzzle input", async () => {
    const puzzleInput = (await readFile(resolve(__dirname, "input.txt"))).toString()
    expect(partOne(puzzleInput)).toEqual(904);
  });
});

describe("part two", () => {
  it("test input", () => {
    expect(partTwo("C200B40A82")).toEqual(3);
  });

  it("puzzle input", async () => {
    const puzzleInput = (await readFile(resolve(__dirname, "input.txt"))).toString()
    expect(partTwo(puzzleInput)).toEqual(200476472872);
  });
});