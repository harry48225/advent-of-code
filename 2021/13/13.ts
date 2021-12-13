export type Paper = boolean[][];
export type Fold = {direction: 'x' | 'y', coordinate: number};

export const parseInput = (puzzleInput: string): [Paper, Fold[]] => {
  let splitInput = puzzleInput.trim().split("\n");
  const points: number[][] = [];
  const rawFolds = [];
  let readingPoints = true;
  for (const line of splitInput) {
    if (line == "") {
      readingPoints = false;
      continue;
    }

    if (readingPoints) {
      points.push(line.split(",").map(coordinate => +coordinate));
    } else {
      rawFolds.push(line);
    }
  }

  // Find the largest x and y
  const maxX = Math.max(...points.map(point => point[0]));
  const maxY = Math.max(...points.map(point => point[1]));

  const paper: Paper = createBlankPaper(maxY, maxX);

  for (const point of points) {
    paper[point[1]][point[0]] = true;
  }

  // Now parse the folds
  const folds: Fold[] = [];
  for (const fold of rawFolds) {
    const amount = fold.replace("fold along ", "").split("=");
    folds.push({direction: amount[0] as 'x' | 'y', coordinate: +amount[1]})
  }

  return [paper, folds];
}

export const createBlankPaper = (height: number, width: number): Paper => {
  return (new Array(height + 1)).fill(false).map(row => (new Array(width + 1)).fill(false));
}

export const doFold = (paper: Paper, fold: Fold): Paper => {
  // Work out the new dimensions
  let height = paper.length;
  let width = paper[0].length;
  
  if (fold.direction == "y") {
    height = Math.max(fold.coordinate, height -1 - fold.coordinate);
  } else {
    width = Math.max(fold.coordinate, width -1 - fold.coordinate);
  }

  const newPaper = createBlankPaper(height-1, width-1);

  // Now copy across the points.
  for (let row = 0; row < height; row++) {
    for (let col = 0; col < width; col ++) {
      if (fold.direction == "y") {
        newPaper[row][col] = paper[row][col] || paper[paper.length - 1 - row][col];
      } else {
        newPaper[row][col] = paper[row][col] || paper[row][paper[row].length - 1 - col];
      }
    }
  }
  return newPaper;
}

export const partOne = (puzzleInput: string): number => {
  const [paper, folds] = parseInput(puzzleInput);

  const newPaper = doFold(paper, folds[0]);
  return newPaper.flat().filter(mark => mark).length;
};

export const partTwo = (puzzleInput: string): void => {
  let [paper, folds] = parseInput(puzzleInput);

  for (const fold of folds) {
    paper = doFold(paper, fold);
  }
  
  console.log(paper.map(row => row.map(mark => mark ? '#' : ' ').join("")).join("\n"));
};
