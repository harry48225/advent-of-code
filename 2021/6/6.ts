export type Fish = number;

export const parseInput = (input: string): Fish[] => {
  return input.trim().split(",").map(digit => +digit);
}

export const partOne = (puzzleInput: string): number => {
  let fishes: Fish[] = parseInput(puzzleInput);
  
  for (let dayNumber = 0; dayNumber < 80; dayNumber++) {
    let newFishes: Fish[] = [];
    for (const fish of fishes) {
      if (fish > 0) {
        newFishes.push(fish - 1);
      }
      if (fish == 0) {
        newFishes.push(6); // reset timer
        newFishes.push(8); // spawn new fish
      }
    }
    fishes = [...newFishes];
  }

  return fishes.length;
};

export const partTwo = (puzzleInput: string): number => {
  let rawFishes: Fish[] = parseInput(puzzleInput);
  let fishes = [0,0,0,0,0,0,0,0,0];

  for (const fish of rawFishes) {
    fishes[fish]++;
  }
  
  for (let dayNumber = 0; dayNumber < 256; dayNumber++) {
    let newFishes = [0,0,0,0,0,0,0,0,0];
    for (let fishAge = 0; fishAge < fishes.length; fishAge++) {
      if (fishAge > 0) {
        newFishes[fishAge - 1] += fishes[fishAge];
      }
      if (fishAge == 0) {
        newFishes[6] += fishes[0]; // reset timer
        newFishes[8] += fishes[0]; // spawn new fish
      }
    }
    fishes = [...newFishes];
  }

  return fishes.reduce((acc, current) => acc + current, 0);
};
