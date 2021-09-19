import { parseRoom } from "./4";

describe("day 4", () => {
  describe("part one", () => {
    it("parses single room input", () => {
      expect(parseRoom("aaaaa-bbb-z-y-x-123[abxyz]")).toEqual({
        encryptedName: ["aaaaa", "bbb", "z", "y", "x"],
        sectorId: 123,
        checksum: "abxyz"
      });
    })
  });
});