export type Point = {
  x: number,
  y: number
}

export type Line = {
  start: Point,
  end: Point
}

export type PointList = Map<string, number>

export const markPoint = (point: Point, list: PointList) => {
  list.set(JSON.stringify(point), (list.get(JSON.stringify(point)) ?? 0) + 1);
};

export const numberOfMarks = (point: Point, list: PointList): number => {
  return list.get(JSON.stringify(point)) ?? 0;
};

export const parseLine = (line: string): Line => {
  const lineParts = line.split(" -> ");
  const start = lineParts[0].split(",");
  const end = lineParts[1].split(",");
  return {start: {x: +start[0], y: +start[1]}, end: {x: +end[0], y: +end[1]}};
};

export const isHorizontal = (line: Line): boolean => {
  return line.start.y == line.end.y;
}

export const isVertical = (line: Line): boolean => {
  return line.start.x == line.end.x;
}

export const parseInput = (input: string): Line[] => {
  return input.trim().split("\n").map(line => parseLine(line));
}

export const partOne = (puzzleInput: string): number => {
  const lines = parseInput(puzzleInput).filter(line => (isHorizontal(line) || isVertical(line)))
  const marks: PointList = new Map<string, number>();

  for (const line of lines) {
    const direction = (line.end.x - line.start.x)/(Math.abs(line.end.x - line.start.x)) || (line.end.y - line.start.y)/(Math.abs(line.end.y - line.start.y));
    const horizontal = isHorizontal(line);
    let currentPoint = {...line.start};
    markPoint(currentPoint, marks);
    while (currentPoint.x != line.end.x || currentPoint.y != line.end.y)
    {
      if (horizontal) {
        currentPoint.x += direction;
      } else {
        currentPoint.y += direction;
      }
      markPoint(currentPoint, marks);
    }
  }
  
  return [...marks.values()].filter(value => value > 1).length;
};

export const partTwo = (puzzleInput: string): number => {
  const lines = parseInput(puzzleInput);
  const marks: PointList = new Map<string, number>();

  for (const line of lines) {
    let directionX = (line.end.x - line.start.x)/(Math.abs(line.end.x - line.start.x));
    let directionY = (line.end.y - line.start.y)/(Math.abs(line.end.y - line.start.y));

    if (Number.isNaN(directionX)) {
      directionX = 0;
    }

    if (Number.isNaN(directionY)) {
      directionY = 0;
    }
    const horizontal = isHorizontal(line);
    let currentPoint = {...line.start};
    markPoint(currentPoint, marks);
    while (currentPoint.x != line.end.x || currentPoint.y != line.end.y)
    {
      currentPoint.x += directionX;
      currentPoint.y += directionY;
      markPoint(currentPoint, marks);
    }
  }
  
  return [...marks.values()].filter(value => value > 1).length;
};