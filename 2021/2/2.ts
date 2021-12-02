export enum CommandVerb {
  Forward = "forward",
  Down = "down",
  Up = "up"
};

export type Command = {
  verb: CommandVerb,
  arg: number
};

export type Position = {
  horizontal: number,
  depth: number
}

export type PositionWithAim = {
  horizontal: number,
  depth: number,
  aim: number
}

export const parseCommand = (rawCommand: string): Command => {
  let splitCommand = rawCommand.split(" ");
  const verb = splitCommand[0] as CommandVerb;
  const arg = +splitCommand[1];
  return {verb, arg};
};

export const processCommand = (currentPosition: Position, command: Command): Position => {
  let newPosition = currentPosition;
  switch (command.verb) {
    case CommandVerb.Forward:
      newPosition.horizontal += command.arg;
      break;
    case CommandVerb.Down:
      newPosition.depth += command.arg;
      break;
    case CommandVerb.Up:
      newPosition.depth -= command.arg;
      break;
  }

  return newPosition;
} 

export const processCommandPartTwo = (currentPosition: PositionWithAim, command: Command): PositionWithAim => {
  let newPosition = currentPosition;

  switch (command.verb) {
    case CommandVerb.Forward:
      newPosition.horizontal += command.arg;
      newPosition.depth += command.arg * currentPosition.aim;
      break;
    case CommandVerb.Down:
      newPosition.aim += command.arg;
      break;
    case CommandVerb.Up:
      newPosition.aim -= command.arg;
      break;
  }

  return newPosition;
};

export const partOne = (puzzleInput: string): number => {
  const commands = puzzleInput.split("\n").map(command => parseCommand(command));

  let position: Position = {depth: 0, horizontal: 0};

  for (const command of commands) {
    position = processCommand(position, command);
  }

  return position.depth * position.horizontal;
}

export const partTwo = (puzzleInput: string): number => {
  const commands = puzzleInput.split("\n").map(command => parseCommand(command));

  let position: PositionWithAim = {depth: 0, horizontal: 0, aim:0};

  for (const command of commands) {
    position = processCommandPartTwo(position, command);
  }

  return position.depth * position.horizontal;
}