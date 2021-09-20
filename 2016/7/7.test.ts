import { containsAbba, isAba, isAbba, parseIpAddress, partOne, partTwo, supportsSsl, supportsTls } from "./7"
import { readFile } from "fs/promises";
import { resolve } from "path";

describe("part one", () => {
  describe("identifies ABBAs", () => {
    it("rejects too long", () => {
      expect(isAbba("aaaaa")).toBeFalsy();
    });

    it("rejects too many different characters", () => {
      expect(isAbba("abcd")).toBeFalsy();  
    });

    it("rejects non palindrome", () => {
      expect(isAbba("abab")).toBeFalsy();  
    });

    it("accepts valid ABBA", () => {
      expect(isAbba("ABBA")).toBeTruthy();  
    });

    it("detects abba in longer string", () => {
      expect(containsAbba("asasdasdasdABBAa")).toBeTruthy();
    });

    it("detects abba at the end of longer string", () => {
      expect(containsAbba("asasdasdasdABBA")).toBeTruthy();
    });

    it("rejects longer string with no abba", () => {
      expect(containsAbba("asdasdasdasd")).toBeFalsy();
    })
  });

  it("correctly parses IP address", () => {
    expect(parseIpAddress("abba[mnop]qrst")).toEqual({supernetSequences: ["abba", "qrst"], hypernetSequences: ["mnop"]});
    expect(parseIpAddress("[abba][mnop][qrst]")).toEqual({supernetSequences: [], hypernetSequences: ["abba", "mnop", "qrst"]});
    expect(parseIpAddress("abbamnopqrst")).toEqual({supernetSequences: ["abbamnopqrst"], hypernetSequences: []});
  });

  describe("determines Tls", () => {
    it("typical test input", () => {
      expect(supportsTls(parseIpAddress("abba[mnop]qrst"))).toBeTruthy();
      expect(supportsTls(parseIpAddress("ioxxoj[asdfgh]zxcvbn"))).toBeTruthy();
    });

    it("typical false test input", () => {
      expect(supportsTls(parseIpAddress("abcd[bddb]xyyx"))).toBeFalsy();
      expect(supportsTls(parseIpAddress("aaaa[qwer]tyui"))).toBeFalsy();
    });
  })

  it("puzzle input", async () => {
    const puzzleInput = (await readFile(resolve(__dirname, './input.txt'))).toString();
    expect(partOne(puzzleInput)).toEqual(118);
  });
});

describe("part two", () => {
  describe("detects abas", () => {
    it("valid abas", () => {
      expect(isAba('aba')).toBeTruthy();
      expect(isAba('bab')).toBeTruthy();
      expect(isAba('pop')).toBeTruthy();
    });

    it("invalid abas", () => {
      expect(isAba('abaa')).toBeFalsy();
      expect(isAba('ABB')).toBeFalsy();
      expect(isAba('ABC')).toBeFalsy();
      expect(isAba('')).toBeFalsy();
    })
  });

  it("detects valid SSL", () => {
    expect(supportsSsl(parseIpAddress("aba[bab]xyz"))).toBeTruthy();
    expect(supportsSsl(parseIpAddress("aaa[kek]eke"))).toBeTruthy();
    expect(supportsSsl(parseIpAddress("zazbz[bzb]cdb"))).toBeTruthy();
  });

  it("detects invalid SSL", () => {
    expect(supportsSsl(parseIpAddress("xyx[xyx]xyx"))).toBeFalsy();
  });

  it("puzzle input", async () => {
    const puzzleInput = (await readFile(resolve(__dirname, './input.txt'))).toString();
    expect(partTwo(puzzleInput)).toEqual(260);
  });
})