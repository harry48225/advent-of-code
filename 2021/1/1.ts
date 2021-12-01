export const countIncreases = (input: number[]): number => {
  let total = 0;
  let previous: number | undefined = undefined;
  for (const reading of input) {
    if (previous && reading > previous) {
      total++;
    }
    previous = reading;
  }

  return total;
}

export const aggregate = (input: number[]): number[] => {
  let output: number[] = [];
  for (let i = 0; i < input.length - 2; i++) {
    output.push(input[i] + input[i+1] + input[i+2]);
  }
  return output;
}