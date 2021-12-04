export type BingoBoard = (number | string)[][]

export const parseBoard = (board: string): BingoBoard => {
  return board.split("\n").filter(row => !!row).map(line => line.split(/\s+/).map(number => +number));
}

export const markBoard = (board: BingoBoard, calledNumber: number): BingoBoard => {
  return board.map(row => row.map(number => number == calledNumber ? '' : number));
}

export const checkForWin = (board: BingoBoard): boolean => {
  for (let i = 0; i < board.length; i++) {
    if (board[i].every(number => number == "")) {return true};
  }

  for (let j = 0; j < board[0].length; j++) {
    const col = board.map(row => row[j]);
    if (col.every(number => number == "")) {return true};
  }

  return false;
} 

export const calculateScore = (board: BingoBoard): number => {
  return board.flat().filter(number => number != "").map(number => +number).reduce((acc, number) => acc + number, 0);
}

export const parseInput = (puzzleInput: string): {boards: BingoBoard[], tape: number[]} => {
  let tape = undefined;
  const boards: BingoBoard[] = []
  let tempBoard = "";
  for (let line of puzzleInput.split("\n")) {
    line = line.trim();
    if (!tape) {tape = line.split(",").map(number => +number); continue;}
    else if (tempBoard == "" && line == "") {continue;}
    else if (line == "") {boards.push(parseBoard(tempBoard)); tempBoard = ""}
    else {tempBoard += (line + "\n")};
  }

  return {tape: tape ?? [-1], boards};
}

export const partOne = (puzzleInput: string): number => {
  const {tape, boards} = parseInput(puzzleInput);

  for (const number of tape) {
    for (let i = 0; i < boards.length; i++) {
      boards[i] = markBoard(boards[i], number);
    }

    // check for wins
    for (const board of boards) {
      if (checkForWin(board)) {
        return calculateScore(board) * number;
      }
    }
  }

  return -1;
}

export const partTwo = (puzzleInput: string): number => {
  let {tape, boards} = parseInput(puzzleInput);

  for (const number of tape) {
    for (let i = 0; i < boards.length; i++) {
      boards[i] = markBoard(boards[i], number);
    }

    if (boards.length > 1) {
      boards = boards.filter(board => !checkForWin(board));
    } else if (checkForWin(boards[0])) {
      return calculateScore(boards[0]) * number;
    }
  }

  return -1;
}