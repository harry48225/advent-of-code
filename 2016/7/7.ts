export const isAbba = (candidate: string) => {
  if (candidate.length != 4) {
    return false;
  }

  if ((new Set([...candidate])).size != 2) {
    return false;
  }

  if (candidate != [...candidate].reverse().join("")) {
    return false;
  }

  return true;
}

export const containsAbba = (candidate: string) => {
  for (let i = 0; i < candidate.length - 3; i += 1) {
    if (isAbba(candidate.slice(i, i+4))) {
      return true;
    }
  }
  return false;
}

type DecodedIp = {
  supernetSequences: string[],
  hypernetSequences: string[]
}

export const parseIpAddress = (ip: string): DecodedIp => {
  let decodedIp: DecodedIp = {supernetSequences: [], hypernetSequences: []};
  let isHypernet = false;
  let currentSequence = "";
  for (const character of ip) {
    if (character == "[") {
      if (currentSequence) {
        decodedIp.supernetSequences.push(currentSequence);
        currentSequence = "";
      }
      isHypernet = true;
    } else if (character == "]") {
      if (currentSequence) {
        decodedIp.hypernetSequences.push(currentSequence);
        currentSequence = "";
      }
      isHypernet = false;
    } else {
      currentSequence += character;
    }
  }

  if (currentSequence) {
    isHypernet ? decodedIp.hypernetSequences.push(currentSequence) : decodedIp.supernetSequences.push(currentSequence);
  }

  return decodedIp;
}

export const supportsTls = (ip: DecodedIp): boolean => (ip.supernetSequences.filter(containsAbba).length > 0 && ip.hypernetSequences.filter(containsAbba).length == 0);

export const partOne = (puzzleInput: string) => puzzleInput.trim().split("\n").filter(ip => supportsTls(parseIpAddress(ip))).length;

export const isAba = (candidate: string) => {
  if (candidate.length != 3) {
    return false;
  }

  return candidate[0] == candidate[2] && candidate[1] != candidate[0];
}

export const supportsSsl = (ip : DecodedIp): boolean => {
  for (const supernetSequence of ip.supernetSequences) {
    for (let i = 0; i < supernetSequence.length - 2; i++) {
      const candidateAba = supernetSequence.slice(i, i+3);
      if (isAba(candidateAba)) {
        const bab = `${candidateAba[1]}${candidateAba[0]}${candidateAba[1]}`;
        if (ip.hypernetSequences.find(sequence => sequence.includes(bab))) {
          return true;
        }
      }
    }
  }
  return false;
}

export const partTwo = (puzzleInput: string) => puzzleInput.trim().split("\n").filter(ip => supportsSsl(parseIpAddress(ip))).length;