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

  const sortedLetters = [...letterCount.entries()].sort().sort((a,b) => b[1] - a[1]);
  return ([0,1,2,3,4].reduce((prev, next) => prev && sortedLetterCount[next][0] == room.checksum[next], true));
}