import { string } from "yargs";

type Room = {
  encryptedName: string[],
  sectorId: number,
  checksum: string
}

export const parseRoom = (roomString: string): Room => {
  const splitRoomString = roomString.trim().split("-");
  return {
    encryptedName: splitRoomString.slice(0, splitRoomString.length - 1),
    sectorId: parseInt(splitRoomString[splitRoomString.length-1].split("[")[0]),
    checksum: splitRoomString[splitRoomString.length-1].split("[")[1].slice(0,5)
  }
}

export const isRoomReal = (room: Room): boolean => {
  const name = room.encryptedName.join("");
  const letterCount = new Map<string, number>(); 

  for (const char of name) {
    letterCount.set(char, (letterCount.get(char) ?? 0) + 1);
  }

  const sortedLetterCount = [...letterCount.entries()].sort().sort((a,b) => b[1] - a[1]);
  
  let isReal = true;
  for (let i = 0; i < 5; i++) {
    isReal = isReal && (sortedLetterCount[i][0] == room.checksum[i]);
  }
  return isReal;
}

export const parsePuzzleInput = (puzzleInput: string): Room[] => (
  puzzleInput
    .trim()
    .split("\n")
    .map(roomString => parseRoom(roomString.trim()))
);

export const partOne = (rooms: Room[]): number => (
  rooms
    .filter(isRoomReal)
    .reduce((acc, room) => acc + room.sectorId, 0)
);

export const shiftCharacter = (char: string, shift: number): string => {
  const startCharCode = 'a'.charCodeAt(0);
  return String.fromCharCode(startCharCode + ((char.charCodeAt(0) - startCharCode + shift) % 26));
}

export const decryptRoomName = (encryptedName: string[], sectorId: number): string => {
  return encryptedName.map(word => [...word].map(letter => shiftCharacter(letter, sectorId)).join("")).join(" ");
}

export const partTwo = (rooms: Room[]): number => (
  rooms.filter(isRoomReal).filter(room => decryptRoomName(room.encryptedName, room.sectorId).includes("northpole object storage"))[0].sectorId
)