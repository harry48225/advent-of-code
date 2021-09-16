export enum InstructionDirections {
    LEFT = "L",
    RIGHT = "R"
}

export type Instruction = {
    direction: InstructionDirections
    amount: number
}

type Vector = {
    x: number,
    y: number
}

export const turnLeft = (v: Vector): Vector => {
    return {x: -v.y, y: v.x};
}

export const turnRight = (v: Vector): Vector => {
    return {x: v.y, y: -v.x};
}

export const shortestPath = (instructions: Array<Instruction>): number => {
    const currentPosition: Vector = {x: 0, y: 0};
    let currentDirection: Vector  = {x: 0, y: 1};

    for (const instruction of instructions) {
        switch (instruction.direction) {
            case InstructionDirections.LEFT:
                currentDirection = turnLeft(currentDirection);
                break;
            case InstructionDirections.RIGHT:
                currentDirection = turnRight(currentDirection);
                break;
        }

        currentPosition.x += currentDirection.x * instruction.amount;
        currentPosition.y += currentDirection.y * instruction.amount;
    }

    return Math.abs(currentPosition.x) + Math.abs(currentPosition.y);
}

export const parseInput = (puzzleInput: string): Array<Instruction> => {
    let splitInput = puzzleInput.replace(/\s+/g, "").split(",");

    return splitInput.map( instruction => {
        const rawDirection = instruction.slice(0,1);
        let direction;
        if (rawDirection == InstructionDirections.LEFT) {
            direction = InstructionDirections.LEFT;
        } else if (rawDirection == InstructionDirections.RIGHT) {
            direction = InstructionDirections.RIGHT;
        } else {
            throw Error(`Invalid input direction ${rawDirection}`);
        }

        return {
            direction: direction,
            amount: parseInt(instruction.slice(1))
        }
    }
    );
}