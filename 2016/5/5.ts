import { createHash, Hash } from "crypto";

export const hashString = (input: string) => createHash('MD5').update(input).digest('hex');

export const partOne = (doorId: string): string => {
  let password = "";
  let index = 0;
  while (password.length < 8) {
    const hash = hashString(`${doorId}${index}`);
    if (hash.startsWith("00000")) {
      password += hash.charAt(5);
    }

    index += 1
  }
  return password;
}

export const partTwo = (doorId: string): string => {
  const passwordArray: (string | null)[] = [null, null, null, null, null, null, null, null];
  let index = 0;
  while (passwordArray.includes(null)) {
    const hash = hashString(`${doorId}${index}`);
    if (hash.startsWith("00000") && /[0-7]/.test(hash.charAt(5))) {
      const passwordCharacterIndex = parseInt(hash.charAt(5));
      if (passwordArray[passwordCharacterIndex] == null) {
        passwordArray[passwordCharacterIndex] = hash.charAt(6);
      }
    }

    index += 1
  }

  return passwordArray.join("");
}