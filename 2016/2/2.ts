const partOneKeypad = [['1','2','3'],
                            ['4','5','6'],
                            ['7','8','9']];

export const partTwoKeypad = [
    ['-1', '-1', '1', '-1', '-1'],
    ['-1', '2', '3', '4', '-1'],
    ['5', '6', '7', '8', '9'],
    ['-1', 'A', 'B', 'C', '-1'],
    ['-1', '-1', 'D', '-1', '-1']]

type Position = {row: number, column: number};

export enum Directions {
    Up = "U",
    Left = "L",
    Right = "R",
    Down = "D"
};

const currentPosition: Position = {row: 1, column: 1};

export const findDigits = (instructions: Array<Array<Directions>>, keypad: string[][] = partOneKeypad, startLocation: Position = {row: 1, column: 1}): string => {
    const digits = new Array<string>();
    const restriction = keypad.map(row => row.map(digit => digit != '-1'));
    let currentPosition= startLocation;

    for (const instructionSet of instructions) {
        currentPosition = findNextPosition(currentPosition, instructionSet, restriction);
        digits.push(keypad[currentPosition.row][currentPosition.column]);
    }

    return digits.join("");
};

export const findNextPosition = (startPosition: Position, 
    instructions: Array<Directions>, 
    restriction: Array<Array<boolean>>): Position => {
    let currentPosition = startPosition;

    for (const instruction of instructions) {
        let oldPosition: Position = {row: currentPosition.row, column: currentPosition.column};
        switch (instruction) {
            case Directions.Up:
                currentPosition.row -= 1;
                break;
            case Directions.Down:
                currentPosition.row += 1;
                break;
            case Directions.Left:
                currentPosition.column -= 1;
                break;
            case Directions.Right:
                currentPosition.column += 1;
                break;
        }

        if (currentPosition.row < 0 || currentPosition.row >= restriction.length || currentPosition.column < 0 || currentPosition.column >= restriction[currentPosition.row].length || !restriction[currentPosition.row][currentPosition.column]) {
            currentPosition = oldPosition;
            continue;
        }
    }
    return currentPosition;
}

export const partTwo = (puzzleInput: string): string => {
    return findDigits(parseInput(puzzleInput), partTwoKeypad);
}

export const parseInput = (puzzleInput: String): Array<Array<Directions>> => {
    let splitInput = puzzleInput.split('\n');
    const parsedInput = splitInput.map(row => [...row].map(direction => direction as Directions))
    return parsedInput.filter(instructionSet => instructionSet.length != 0);
}