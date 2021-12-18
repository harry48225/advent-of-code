import { numberOfMarks } from "../5/5";

export type SnailNumber = string;
export type Homework = SnailNumber[];

export const isDigit = (s: string) => /^-?\d+$/.test(s);

export const reduceNumber = (number: SnailNumber): SnailNumber => {
  let fullyReduced = false;
  while (!fullyReduced) {
    fullyReduced = true;
    
    // Look for splits
    let currentDepth = -1;
    for (let i = 0; i < number.length; i++) {
      const currentChar = number[i];

      if (currentChar == "[") currentDepth++;
      else if (currentChar == "]") currentDepth--;

      if (currentDepth >= 4) {
        fullyReduced = false;

        let reducedNumber = "";
        let pair = ""
        const pairStart = i; // "["
        let pairEnd = pairStart + 1; // "]"
        while (number[pairEnd] != "]") {
          pair += number[pairEnd];
          pairEnd++;
        }

        const parsedPair = pair.split(",").map(n => +n);

        // Add the left number to the first regular number to the left.
        let leftRegularNumberEnd = pairStart;
        while (leftRegularNumberEnd >= 0 && !isDigit(number[leftRegularNumberEnd])) {
          leftRegularNumberEnd--;
        }

        // if there is a number to the left
        if (leftRegularNumberEnd > 0) {
          let leftRegularNumberStart = leftRegularNumberEnd;
          while (leftRegularNumberStart >= 0 && isDigit(number[leftRegularNumberStart])) {
            leftRegularNumberStart--;
          }
          leftRegularNumberStart++;

          // Now get the number
          let leftRegularNumber = +number.slice(leftRegularNumberStart, leftRegularNumberEnd + 1);
          leftRegularNumber += parsedPair[0];

          reducedNumber += number.slice(0, leftRegularNumberStart);
          reducedNumber += leftRegularNumber;
          reducedNumber += number.slice(leftRegularNumberEnd + 1, pairStart)
        } else {
          reducedNumber += number.slice(0, pairStart);
        }

        // Add the 0 to replace the pair
        reducedNumber += '0';

        // Now look for a number to the right.
        let rightRegularNumberStart = pairEnd; // "]"
        while (rightRegularNumberStart < number.length && !isDigit(number[rightRegularNumberStart])) {
          rightRegularNumberStart++;
        }

        if (rightRegularNumberStart < number.length) {
          let rightRegularNumberEnd = rightRegularNumberStart;
          while (isDigit(number[rightRegularNumberEnd])) {
            rightRegularNumberEnd++;
          }
          rightRegularNumberEnd--;

          let rightRegularNumber = +number.slice(rightRegularNumberStart, rightRegularNumberEnd + 1);
          rightRegularNumber += parsedPair[1];

          reducedNumber += number.slice(pairEnd + 1, rightRegularNumberStart);
          reducedNumber += rightRegularNumber;
          reducedNumber += number.slice(rightRegularNumberEnd + 1);

        } else {
          reducedNumber += number.slice(pairEnd + 1);
        }

        number = reducedNumber;
        break;
      }
    }

    // Look for more explodes if a explode occured
    if (!fullyReduced) {
      continue;
    }

    // Now look for splits
    let numberStart = 0;
    let numberEnd = -1;
    while (numberStart < number.length) {
      while (numberStart < number.length && !isDigit(number[numberStart])) numberStart++;
      numberEnd = numberStart
      while (isDigit(number[numberEnd])) numberEnd++;
      numberEnd--;

      // Now we have a number
      let splitCandidate = +number.slice(numberStart, numberEnd + 1);

      if (splitCandidate >= 10) {
        fullyReduced = false;

        // Replace with a pair
        const newPair = [Math.floor(splitCandidate/2), Math.ceil(splitCandidate/2)];
        
        let reducedNumber = number.slice(0, numberStart);
        reducedNumber += `[${newPair[0]},${newPair[1]}]`;
        reducedNumber += number.slice(numberEnd + 1);

        number = reducedNumber;
      } else {
        numberStart = numberEnd + 1;
      }


      if (!fullyReduced) {
        break;
      }
    }
  }

  return number;
}

export const parseInput = (puzzleInput: string): Homework => {
  return puzzleInput.trim().split("\n");
}

export type ArraySnailNumber = number[] | ArraySnailNumber[];
export const computeMagnitude = (number: ArraySnailNumber): number => {
  let total = 0
  if ((typeof number[0]) == 'number') {
    total += 3 * (number[0] as number);
  } else {
    total += 3 * computeMagnitude(number[0] as ArraySnailNumber)
  }

  if ((typeof number[1]) == 'number') {
    total += 2 * (number[1] as number);
  } else {
    total += 2 * computeMagnitude(number[1] as ArraySnailNumber)
  }

  return total;
}

export const doHomework = (homework: Homework): SnailNumber => {
  let number: SnailNumber = homework.shift()!;

  while (homework.length > 0) {
    number = reduceNumber(`[${number},${homework.shift()}]`);
  }
  return number;
}

export const partOne = (puzzleInput: string): number => {
  const homework = parseInput(puzzleInput);
  const solution = doHomework(homework);
  return computeMagnitude(JSON.parse(solution) as ArraySnailNumber);
};

export const partTwo = (puzzleInput: string): number => {
  const homework = parseInput(puzzleInput);

  let greatestMagitude = 0;
  // Now find all permutations of numbers in the homework
  for (let i = 0; i < homework.length; i++) {
    for (let j = 0; j < homework.length; j++) {
      if (i == j) continue;
      const solution = doHomework([homework[i], homework[j]]);
      greatestMagitude = Math.max(greatestMagitude, computeMagnitude(JSON.parse(solution) as ArraySnailNumber));
    }
  }

  return greatestMagitude;
};

