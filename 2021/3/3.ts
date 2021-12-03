export const partOne = (puzzleInput: string): number => {
  const numberList = puzzleInput.trim().split("\n").map(number => [...number].map(num => +num));
  const threshold = numberList.length / 2;

  const countedOccurances = countOccurances(numberList);
  const gammaBinary = countedOccurances.map(count => count > threshold ? 1 : 0);
  const epsilonBinary = gammaBinary.map(bit => 1-bit);

  const gamma = parseInt(gammaBinary.join(""), 2);
  const epsilon = parseInt(epsilonBinary.join(""), 2);
  return gamma * epsilon;
} 

export const countOccurances = (numberList: number[][]): number[] => {
  return numberList.reduce((count, currentNumber) => (
    count.map((current, i) => current + currentNumber[i])
  ), new Array(numberList[0].length).fill(0));
}

export const partTwo = (puzzleInput: string): number => {
  const numberList = puzzleInput.trim().split("\n").map(number => [...number].map(num => +num));
  const threshold = numberList.length / 2;

  let oxygenGeneratorRatings = numberList;
  let i = 0;
  while (oxygenGeneratorRatings.length > 1) {
    const countedOccurances = countOccurances(oxygenGeneratorRatings);
    const threshold = oxygenGeneratorRatings.length / 2;
    const oxygenFilter = countedOccurances.map(count => count >= threshold ? 1 : 0);
    oxygenGeneratorRatings = oxygenGeneratorRatings.filter(number => number[i] == oxygenFilter[i]);
    i++;
  }

  const oxygenGeneratorRating = parseInt(oxygenGeneratorRatings[0].join(""),2);

  let C02ScrubberRatings = numberList;
  i = 0;
  while (C02ScrubberRatings.length > 1) {
    const countedOccurances = countOccurances(C02ScrubberRatings);
    const threshold = C02ScrubberRatings.length / 2;
    const C02Filter = countedOccurances.map(count => count >= threshold ? 0 : 1);
    C02ScrubberRatings = C02ScrubberRatings.filter(number => number[i] == C02Filter[i]);
    i++;
  }
  const CO2ScrubberRating = parseInt(C02ScrubberRatings[0].join(""),2);

  return oxygenGeneratorRating * CO2ScrubberRating; 
}