type Octopus = number | "F";

export const parseInput = (puzzleInput: string): Octopus[][] => {
  return puzzleInput.trim().split("\n").map(line => [...line].map(octopus => parseInt(octopus) as Octopus));
}

export const partOne = (puzzleInput: string): number => {
  let octopodes = parseInput(puzzleInput);
  let numberOfFlashes = 0;
  // do 100 steps
  for (let step = 0; step < 100; step++) {
    // increase the energy level of each octopus
    for (let i = 0; i < octopodes.length; i ++) {
      for (let j = 0; j < octopodes[i].length; j++) {
        const octopusValue = octopodes[i][j];
        if (octopusValue != "F") {
          octopodes[i][j] = octopusValue + 1;
        }
      }
    }
    
    // any octopus with an energy level greater than 9 flashes
    while (!octopodes.flat().every(octopus => octopus == "F" || octopus <= 9)) {
      const newOctopodes = JSON.parse(JSON.stringify(octopodes));
      for (let i = 0; i < octopodes.length; i++) {
        for (let j = 0; j < octopodes[i].length; j++) {
          const octopusValue = octopodes[i][j];
          if (octopusValue == "F") {
            continue;
          } else if (octopusValue > 9) {
            // flash the octopus
            newOctopodes[i][j] = "F";
            numberOfFlashes++;
            if (i > 0) {
              if (newOctopodes[i-1][j] != "F") {
                newOctopodes[i-1][j]! += 1;
              }
            }
            if (i < octopodes.length - 1) {
              if (newOctopodes[i+1][j] != "F") {
                newOctopodes[i+1][j]! += 1;
              }
            }
            if (j > 0) {
              if (newOctopodes[i][j-1] != "F") {
                newOctopodes[i][j-1]! += 1;
              }
            }
            if (j < octopodes[i].length - 1) {
              if (newOctopodes[i][j+1] != "F") {
                newOctopodes[i][j+1]! += 1;
              }
            }
            if (i > 0 && j > 0) {
              if (newOctopodes[i-1][j-1] != "F") {
                newOctopodes[i-1][j-1]! += 1;
              }
            }
            if (i > 0 && j < octopodes[i].length - 1) {
              if (newOctopodes[i-1][j+1] != "F") {
                newOctopodes[i-1][j+1]! += 1;
              }
            }
            if (i < octopodes.length - 1 && j > 0) {
              if (newOctopodes[i+1][j-1] != "F") {
                newOctopodes[i+1][j-1]! += 1;
              }
            }
            if (i < octopodes.length - 1 && j < octopodes[i].length - 1) {
              if (newOctopodes[i+1][j+1] != "F") {
                newOctopodes[i+1][j+1]! += 1;
              }
            }
          }
        }
      }
      octopodes = JSON.parse(JSON.stringify(newOctopodes));
    }

    // reset all flashes octopodes to 0
    for (let i = 0; i < octopodes.length; i ++) {
      for (let j = 0; j < octopodes[i].length; j++) {
        const octopusValue = octopodes[i][j];
        if (octopusValue == "F") {
          octopodes[i][j] = 0;
        }
      }
    }
  }
  return numberOfFlashes;
};

export const partTwo = (puzzleInput: string): number => {
  let octopodes = parseInput(puzzleInput);
  let step = 0
  // do 100 steps
  while (!octopodes.flat().every(octopus => octopus == 0)) {
    // increase the energy level of each octopus
    for (let i = 0; i < octopodes.length; i ++) {
      for (let j = 0; j < octopodes[i].length; j++) {
        const octopusValue = octopodes[i][j];
        if (octopusValue != "F") {
          octopodes[i][j] = octopusValue + 1;
        }
      }
    }
    
    // any octopus with an energy level greater than 9 flashes
    while (!octopodes.flat().every(octopus => octopus == "F" || octopus <= 9)) {
      const newOctopodes = JSON.parse(JSON.stringify(octopodes));
      for (let i = 0; i < octopodes.length; i++) {
        for (let j = 0; j < octopodes[i].length; j++) {
          const octopusValue = octopodes[i][j];
          if (octopusValue == "F") {
            continue;
          } else if (octopusValue > 9) {
            // flash the octopus
            newOctopodes[i][j] = "F";
            if (i > 0) {
              if (newOctopodes[i-1][j] != "F") {
                newOctopodes[i-1][j]! += 1;
              }
            }
            if (i < octopodes.length - 1) {
              if (newOctopodes[i+1][j] != "F") {
                newOctopodes[i+1][j]! += 1;
              }
            }
            if (j > 0) {
              if (newOctopodes[i][j-1] != "F") {
                newOctopodes[i][j-1]! += 1;
              }
            }
            if (j < octopodes[i].length - 1) {
              if (newOctopodes[i][j+1] != "F") {
                newOctopodes[i][j+1]! += 1;
              }
            }
            if (i > 0 && j > 0) {
              if (newOctopodes[i-1][j-1] != "F") {
                newOctopodes[i-1][j-1]! += 1;
              }
            }
            if (i > 0 && j < octopodes[i].length - 1) {
              if (newOctopodes[i-1][j+1] != "F") {
                newOctopodes[i-1][j+1]! += 1;
              }
            }
            if (i < octopodes.length - 1 && j > 0) {
              if (newOctopodes[i+1][j-1] != "F") {
                newOctopodes[i+1][j-1]! += 1;
              }
            }
            if (i < octopodes.length - 1 && j < octopodes[i].length - 1) {
              if (newOctopodes[i+1][j+1] != "F") {
                newOctopodes[i+1][j+1]! += 1;
              }
            }
          }
        }
      }
      octopodes = JSON.parse(JSON.stringify(newOctopodes));
    }

    // reset all flashes octopodes to 0
    for (let i = 0; i < octopodes.length; i ++) {
      for (let j = 0; j < octopodes[i].length; j++) {
        const octopusValue = octopodes[i][j];
        if (octopusValue == "F") {
          octopodes[i][j] = 0;
        }
      }
    }

    step++;
  }
  return step;
};

