import { hashString, partOne, partTwo } from "./5"

describe("part one", () => {
  it("test input md5 hash", () => {
    expect(hashString("abc5017308")).toMatch(/000008f82.*/)
  });

  it("test input", () => {
    expect(partOne("abc")).toEqual("18f47a30");
  });

  it("puzzle input", () => {
    expect(partOne("ffykfhsq")).toEqual("c6697b55");
  });
})

describe("part two", () => {
  it("test input", () => {
    expect(partTwo("abc")).toEqual("05ace8e3");
  });

  it("puzzle input", () => {
    expect(partTwo("ffykfhsq")).toEqual("8c35d1ab");
  });
})