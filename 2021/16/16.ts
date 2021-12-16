export type Packet = {
  version: number,
  typeId: number,
  value?: number,
  subpackets?: Packet[]
}

export const inputStream = (puzzleInput: string): string[] => {
  const binary = [...puzzleInput.trim()].map(hex => parseInt(hex, 16).toString(2).padStart(4, '0')).join("");

  return [...binary];
}

const readBits = (stream: Array<string>, numberOfBits: number) => {
  let out = "";

  for (let i = 0; i < numberOfBits; i++) {
    out += stream.shift();
  }

  return out;
}

export const readPackets = (stream: Array<string>, singlePacket = false): Packet[] => {

  const packets: Packet[] = [];
  while (true) {
    if (stream.length == 0 || stream.every(element => element == '0')) {
      break;
    }
    const version = parseInt(readBits(stream, 3), 2);

    // Next three encode type id
    const typeId = parseInt(readBits(stream, 3), 2);

    let value: number | undefined = undefined;
    let subpackets: Packet[] | undefined = undefined;

    // the packet is a literal value
    if (typeId == 4) {
      let binaryNumber = "";

      while (stream.shift() == '1') {
        binaryNumber += readBits(stream, 4);
      }
      binaryNumber += readBits(stream, 4);
      value = parseInt(binaryNumber, 2);
    } else {
      // We need to read subpackets
      const lengthTypeId = stream.shift();

      if (lengthTypeId == '0') {
        // Next 15 bits is a number that tells us the total length in bits of the subpackets contained by this packet.
        const rawLength = readBits(stream, 15);
        const subLength = parseInt(rawLength, 2);
        const rawSubStream = readBits(stream, subLength);
        const subStream = [...rawSubStream];

        subpackets = readPackets(subStream);
      } else if (lengthTypeId == '1') {
        const numberOfSubpackets = parseInt(readBits(stream, 11), 2);
        subpackets = [];

        while (subpackets!.length < numberOfSubpackets) {
          subpackets.push(...readPackets(stream, true));
        }
      }
    }
    packets.push({version, typeId, value, subpackets});
    if (singlePacket) {
      break;
    }
  }
  return packets;
}

export const addUpVersionNumbers = (packet: Packet): number => {
  let total = packet.version;

  if (packet.subpackets) {
    for (const subPacket of packet.subpackets) {
      total += addUpVersionNumbers(subPacket);
    }
  }

  return total;
}

export const partOne = (puzzleInput: string): number => {
  const stream = inputStream(puzzleInput);
  const packets = readPackets(stream);
  return addUpVersionNumbers(packets[0]);
};

const getValue = (packet: Packet): number => {
  let value: number = -1;

  switch (packet.typeId) {
    case 0:
      value = packet.subpackets!.map(packet => getValue(packet)).reduce((acc, packet) => acc + packet);
      break;
    case 1:
      value = packet.subpackets!.map(packet => getValue(packet)).reduce((acc, packet) => acc * packet);
      break;
    case 2:
      value = packet.subpackets!.map(packet => getValue(packet)).reduce((acc, packet) => Math.min(acc, packet));
      break;
    case 3:
      value = packet.subpackets!.map(packet => getValue(packet)).reduce((acc, packet) => Math.max(acc, packet));
      break;
    case 4:
      value = packet.value!;
      break;
    case 5:
      const subPacketsGreater = packet.subpackets!.map(packet => getValue(packet));
      value = subPacketsGreater[0] > subPacketsGreater[1] ? 1 : 0;
      break;
    case 6:
      const subPacketsLess = packet.subpackets!.map(packet => getValue(packet));
      value = subPacketsLess[0] < subPacketsLess[1] ? 1 : 0;
      break;
    case 7:
      const subPacketsEqual = packet.subpackets!.map(packet => getValue(packet));
      value = subPacketsEqual[0] == subPacketsEqual[1] ? 1 : 0;
      break;

  }

  return value;
}

export const partTwo = (puzzleInput: string): number => {
  const stream = inputStream(puzzleInput);
  const packets = readPackets(stream);
  return getValue(packets[0]);
};

