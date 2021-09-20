const findLetterFrequencies = (characters: string[]) => {
  const count = new Map<string, number>();
  for (const character of characters) {
    count.set(character, (count.get(character) ?? 0) + 1);
  }
  return count;
}

export const decodeCharacter = (characters: string[], leastFrequent = false): string => {
  const mostFrequentLetter = [...findLetterFrequencies(characters).entries()].sort((a,b) => leastFrequent ? (a[1] - b[1]) : (b[1] - a[1]))[0][0];
  return mostFrequentLetter;
}

const decodeCode = (codes: string[], leastFrequent = false): string => {
  let decryptedCode = "";
  for (let i = 0; i < codes[0].length; i++) {
    decryptedCode += decodeCharacter(codes.map(code => code.charAt(i)), leastFrequent);
  }
  return decryptedCode;
}

export const partOne = (codes: string[]) => decodeCode(codes);

export const partTwo = (codes: string[]) => decodeCode(codes, true);