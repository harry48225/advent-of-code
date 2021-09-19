import { decryptRoomName, isRoomReal, parsePuzzleInput, parseRoom, partOne, shiftCharacter, partTwo } from "./4";
import { readFile } from 'fs/promises';
import { resolve } from 'path';

const loadPuzzleInput = async () => parsePuzzleInput((await readFile(resolve(__dirname, "./input.txt"))).toString());

describe("day 4", () => {
  describe("part one", () => {
    it("parses single room input", () => {
      expect(parseRoom("aaaaa-bbb-z-y-x-123[abxyz]")).toEqual({
        encryptedName: ["aaaaa", "bbb", "z", "y", "x"],
        sectorId: 123,
        checksum: "abxyz"
      });
    });

    it("test typical real room input", () => {
      expect(isRoomReal(parseRoom("aaaaa-bbb-z-y-x-123[abxyz]"))).toBeTruthy();
    });

    it("test tied real room input", () => {
      expect(isRoomReal(parseRoom("a-b-c-d-e-f-g-h-987[abcde]"))).toBeTruthy();
    });

    it("test not real room input", () => {
      expect(isRoomReal(parseRoom("totally-real-room-200[decoy]"))).toBeFalsy();
    });

    it("part one", async () => {
      expect(partOne(await loadPuzzleInput())).toEqual(173787);
    })
  });

  describe("part two", () => {
    it("character shift", () => {
      expect(shiftCharacter('a', 27)).toEqual('b');
    });

    it("decrypt room name", () => {
      expect(decryptRoomName(['qzmt', 'zixmtkozy', 'ivhz'], 343)).toEqual("very encrypted name");
    });

    it("part two", async () => {
      expect(partTwo(await loadPuzzleInput())).toEqual(548);
    });
  })
});