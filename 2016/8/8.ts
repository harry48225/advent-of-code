type Display = boolean[][];
export const getBlankDisplay= (): Display => Array(6).fill(Array(50).fill(false)); // 6 x 50 array containing false
export const deepCopyDisplay = (display: Display): Display => JSON.parse(JSON.stringify(display));


type Command = {
  verb: CommandVerb,
  args: number[]
}

export enum CommandVerb {
  Rect = 'rect',
  RotateRow = 'rotate row',
  RotateColumn = 'rotate column'
};

export const rect = (width: number, height: number, display: Display): Display => {
  const outputDisplay = deepCopyDisplay(display);
  for (let i = 0; i < height; i++) {
    for (let j = 0; j < width; j++) {
      outputDisplay[i][j] = true;
    }
  }
  return outputDisplay;
}

export const rotateRow = (row: number, shift: number, display: Display): Display => {
  const outputDisplay = deepCopyDisplay(display);
  const displayWidth = display[row].length;
  for (let i = 0; i < displayWidth; i++) {
    outputDisplay[row][(i + shift) % displayWidth] = display[row][i];
  }
  return outputDisplay;
}

export const rotateColumn = (column: number, shift: number, display: Display): Display => {
  const outputDisplay = deepCopyDisplay(display);
  const displayHeight = display.length;
  for (let j = 0; j < displayHeight; j++) {
    outputDisplay[(j + shift) % displayHeight][column] = display[j][column];
  }
  return outputDisplay;
}

export const parseCommand = (command: string): Command => {
  let verb: CommandVerb;
  let args: number[];

  if (command.startsWith(CommandVerb.Rect)) {
    verb = CommandVerb.Rect;
    args = command.split(" ")[1].split("x").map(arg => parseInt(arg));
  } else if (command.startsWith(CommandVerb.RotateRow)) {
    verb = CommandVerb.RotateRow;
    args = command.split(" row ")[1].split(" by ").map(arg => arg.split("=").at(-1)!).map(arg => parseInt(arg));
  } else if (command.startsWith(CommandVerb.RotateColumn)) {
    verb = CommandVerb.RotateColumn;
    args = command.split(" column ")[1].split(" by ").map(arg => arg.split("=").at(-1)!).map(arg => parseInt(arg));
  } else {
    throw new Error(`unknown command verb for command: ${command}`);
  }
  return {verb, args};
}

export const partOne = (puzzleInput: string) => {
  const commands = puzzleInput.trim().split("\n").map(command => parseCommand(command.trim()));
  let display = getBlankDisplay();
  for (const command of commands) {
    switch (command.verb) {
      case CommandVerb.Rect:
        display = rect(command.args[0], command.args[1], display);
        break;
      case CommandVerb.RotateRow:
        display = rotateRow(command.args[0], command.args[1], display);
        break;
      case CommandVerb.RotateColumn:
        display = rotateColumn(command.args[0], command.args[1], display);
        break;
      default:
        throw new Error("unknown command");
        break;
    }
  }

  return {on: display.flat().filter(pixel => pixel).length, display};
}