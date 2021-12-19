export type Scanner = {
  beacons: Beacon[]
}

export type Beacon = number[]

export const parseInput = (puzzleInput: string): Scanner[] => {
  let splitInput = puzzleInput.trim().split("\n");

  const scanners: Scanner[] = [];
  let currentBeacons: Beacon[] = [];
  for (const line of splitInput) {
    if (line.startsWith("--- scanner")) {
      if (currentBeacons.length > 0) {
        scanners.push({beacons: currentBeacons})
      }
      currentBeacons = [];
      continue;
    }
    if (line.trim() == "") {
      continue;
    }
    currentBeacons.push(line.split(",").map(n => +n));
  }
  scanners.push({beacons: currentBeacons})
  return scanners;
}

export const scannerMatches = (map: Beacon[], candidate: Beacon[]) => {
  // Places the candidate's origin at the origin x and y and then determins if 12 or more beacons overlap
  
  const stringCoordintes = map.map(coordinate => coordinate.join());

  const matches = candidate.map(coordinate => coordinate.join()).filter(coordinate => stringCoordintes.includes(coordinate));

  return matches.length >= 12;
}
 
export const doJigsaw = (startingMap: Beacon[], pieces: Scanner[]): Beacon[] => {
  let map = startingMap;
  let unmatchedScanners = [{beacons:startingMap}, ...pieces];
  while (unmatchedScanners.length > 1) {
    let newUnmatchedScanners: Scanner[] = [];
    // try to fit two together
    // for each scanner
    for (let i = 0; i < unmatchedScanners.length; i++) {
      let matchFound = false;
      for (let j = i+1; j < unmatchedScanners.length; j++) {
        if (i == j) continue;
        const transformedBeacons = fitPiece(unmatchedScanners[i].beacons, unmatchedScanners[j].beacons);
        // if there is a match
        if (transformedBeacons.length > 0) {
          const stringMap = unmatchedScanners[i].beacons.map(beacon => JSON.stringify(beacon));
          const newBeacons = transformedBeacons.filter(beacon => !stringMap.includes(JSON.stringify(beacon)))
          newUnmatchedScanners = unmatchedScanners.slice(0, i);
          newUnmatchedScanners.push({beacons: [...unmatchedScanners[i].beacons, ...newBeacons]})
          newUnmatchedScanners.push(...unmatchedScanners.slice(i+1, j))
          newUnmatchedScanners.push(...unmatchedScanners.slice(j+1));
          matchFound = true;
          break;
        }
      }
      if (matchFound)
      {
        unmatchedScanners = newUnmatchedScanners;
        break;
      }
      
    }

    // for (const scanner of unmatchedScanners) {
    //   const transformedBeacons = fitPiece(map, scanner.beacons);
    //   const stringMap = map.map(beacon => JSON.stringify(beacon));
    //   const newBeacons = transformedBeacons.filter(beacon => !stringMap.includes(JSON.stringify(beacon)))
    //   if (newBeacons.length > 0) {
    //     map = [...map, ...newBeacons]; // Concatenate and remove duplicates
    //   } else {
    //     newUnmatchedScanners.push(scanner);
    //   }
    // }
    // //console.log(newUnmatchedScanners)
    // //unmatchedScanners = [];
    // unmatchedScanners = newUnmatchedScanners;
  }

  return unmatchedScanners[0].beacons;
}

export const fitPiece = (map: Beacon[], beacons: Beacon[]): Beacon[] => {
  const permutations = [
    (c: Beacon) => [1*c[0], 1*c[1], 1*c[2], ...(c.length == 4 ? [c[3]] : [])],
    (c: Beacon) => [1*c[0], 1*c[2], -1*c[1], ...(c.length == 4 ? [c[3]] : [])],
    (c: Beacon) => [1*c[0], -1*c[1], -1*c[2], ...(c.length == 4 ? [c[3]] : [])],
    (c: Beacon) => [1*c[0], -1*c[2], 1*c[1], ...(c.length == 4 ? [c[3]] : [])],
    (c: Beacon) => [-1*c[0], 1*c[1], -1*c[2], ...(c.length == 4 ? [c[3]] : [])],
    (c: Beacon) => [-1*c[0], 1*c[2], 1*c[1], ...(c.length == 4 ? [c[3]] : [])],
    (c: Beacon) => [-1*c[0], -1*c[1], 1*c[2], ...(c.length == 4 ? [c[3]] : [])],
    (c: Beacon) => [-1*c[0], -1*c[2], -1*c[1], ...(c.length == 4 ? [c[3]] : [])],
    (c: Beacon) => [1*c[1], 1*c[0], -1*c[2], ...(c.length == 4 ? [c[3]] : [])],
    (c: Beacon) => [1*c[1], -1*c[2], -1*c[0], ...(c.length == 4 ? [c[3]] : [])],
    (c: Beacon) => [1*c[1], -1*c[0], 1*c[2], ...(c.length == 4 ? [c[3]] : [])],
    (c: Beacon) => [1*c[1], 1*c[2], 1*c[0], ...(c.length == 4 ? [c[3]] : [])],
    (c: Beacon) => [-1*c[1], 1*c[0], 1*c[2], ...(c.length == 4 ? [c[3]] : [])],
    (c: Beacon) => [-1*c[1], 1*c[2], -1*c[0], ...(c.length == 4 ? [c[3]] : [])],
    (c: Beacon) => [-1*c[1], -1*c[0], -1*c[2], ...(c.length == 4 ? [c[3]] : [])],
    (c: Beacon) => [1*c[1], -1*c[2], 1*c[0], ...(c.length == 4 ? [c[3]] : [])],
    (c: Beacon) => [1*c[2], 1*c[1], -1*c[0], ...(c.length == 4 ? [c[3]] : [])],
    (c: Beacon) => [1*c[2], -1*c[0], -1*c[1], ...(c.length == 4 ? [c[3]] : [])],
    (c: Beacon) => [1*c[2], -1*c[1], 1*c[0], ...(c.length == 4 ? [c[3]] : [])],
    (c: Beacon) => [1*c[2], 1*c[0], 1*c[1], ...(c.length == 4 ? [c[3]] : [])],
    (c: Beacon) => [-1*c[2], 1*c[1], 1*c[0], ...(c.length == 4 ? [c[3]] : [])],
    (c: Beacon) => [-1*c[2], 1*c[0], -1*c[1], ...(c.length == 4 ? [c[3]] : [])],
    (c: Beacon) => [-1*c[2], -1*c[1], -1*c[0], ...(c.length == 4 ? [c[3]] : [])],
    (c: Beacon) => [-1*c[2], -1*c[0], 1*c[1], ...(c.length == 4 ? [c[3]] : [])]
  ]

  for (const permuation of permutations) {
    for (const mapBeacon of map) {
      const permutedBeacons = beacons.map(beacon => permuation(beacon));
      for (const beacon of permutedBeacons) {
        const origin = mapBeacon.map((coord, index) => coord - beacon[index]);
        const transformedBeacons = permutedBeacons.map(beacon => [beacon[0] + origin[0], beacon[1] + origin[1], beacon[2] + origin[2], ...(beacon.length == 4 ? [beacon[3]] : [])]);
        if (scannerMatches(map, transformedBeacons)) {
          return transformedBeacons;
        }
      }
    }
  }

  //throw new Error(`no match! for ${beacons}`);
  return [];
}

export const partOne = (puzzleInput: string): number => {
  const beacons = parseInput(puzzleInput);
  const map = doJigsaw(beacons.shift()!.beacons, beacons);
  //console.log(map.sort((a, b) => a[0] - b[0]));
  return map.length;
};

export const partTwo = (puzzleInput: string): number => {
  let beacons = parseInput(puzzleInput);
  // Inject scanners into each beacons
  const beaconsWithScanners = beacons.map(scanner => ({beacons: [[0,0,0,-1], ...scanner.beacons]}))

  beacons = beaconsWithScanners;
  const map = doJigsaw(beacons.shift()!.beacons, beacons);
  //console.log(map.sort((a, b) => a[0] - b[0]));

  let largestDistance = 0;
  // Find the largest distance between two scanners
  const scanners = map.filter(beacon => beacon.length == 4);
  for (let i = 0; i < scanners.length; i++) {
    for (let j = i+1; j < scanners.length; j++) {
      if (i == j) continue;
      const distance = Math.abs(scanners[i][0] - scanners[j][0]) + Math.abs(scanners[i][1] - scanners[j][1]) + Math.abs(scanners[i][2] - scanners[j][2])
      largestDistance = Math.max(distance, largestDistance)
    }
  }

  return largestDistance;
};

