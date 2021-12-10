export const findSyntaxError = (line: string): string | null => {
  const stack = [];

  for (const char of [...line]) {
    switch (char) {
      case '(':
        stack.push(')')
        break;
      case '[':
        stack.push(']')
        break;
      case '{':
        stack.push('}')
        break;
      case '<':
        stack.push('>')
        break;
      case stack[stack.length - 1]:
        stack.pop();
        break;
      default:
        return char;
    }
  }

  return null;
}

export const completeLineAndScore = (line: string): number => {
  const stack = [];

  for (const char of [...line]) {
    switch (char) {
      case '(':
        stack.push(')')
        break;
      case '[':
        stack.push(']')
        break;
      case '{':
        stack.push('}')
        break;
      case '<':
        stack.push('>')
        break;
      case stack[stack.length - 1]:
        stack.pop();
        break;
    }
  }

  // The stack is what we need to complete the string by
  let score = 0;
  while (stack.length > 0) {
    score *= 5;
    switch (stack.pop()) {
      case ')':
        score += 1;
        break;
      case ']':
        score += 2;
        break;
      case '}':
        score += 3;
        break;
      case '>':
        score += 4;
        break;
    }
  }
  return score;
}

export const partOne = (puzzleInput: string): number => {
  const lines = puzzleInput.trim().split("\n");
  let score = 0;

  for (const line of lines) {
    switch (findSyntaxError(line)) {
      case null:
        break;
      case ')':
        score += 3;
        break;
      case ']':
        score += 57;
        break;
      case '}':
        score += 1197;
        break;
      case '>':
        score += 25137;
        break;
    }
  }
  return score;
};

export const partTwo = (puzzleInput: string): number => {
  const lines = puzzleInput.trim().split("\n");
  const incomplete = lines.filter(line => findSyntaxError(line) == null);
  
  const scores = incomplete.map(line => completeLineAndScore(line)).sort((a,b) => a-b);
  return scores[(scores.length -1) / 2];
};

