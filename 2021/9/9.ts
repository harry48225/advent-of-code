type HeightMap = number[][];

export const parseInput = (puzzleInput: string): HeightMap => {
  return puzzleInput.trim().split("\n").map(row => [...row].map(digit => +digit));
};

export const isLowerThanNeighbours = (i: number, j: number, heightMap: HeightMap): boolean => {
  // top edge
  if (i > 0 && heightMap[i-1][j] <= heightMap[i][j]) {
    return false;
  }
  // bottom edge
  if (i < heightMap.length - 1 && heightMap[i+1][j] <= heightMap[i][j]) {
    return false;
  }
  // left edge
  if (j > 0 && heightMap[i][j-1] <= heightMap[i][j]) {
    return false;
  }
  // right edge
  if (j < heightMap[i].length - 1 && heightMap[i][j+1] <= heightMap[i][j]) {
    return false;
  }
  return true;
}

export const partOne = (puzzleInput: string): number => {
  const heightMap  = parseInput(puzzleInput);

  let riskLevel = 0;
  for (let i = 0; i < heightMap.length; i++) {
    for (let j = 0; j < heightMap[i].length; j++) {
      if (isLowerThanNeighbours(i,j, heightMap)) {
        riskLevel += (heightMap[i][j] + 1)
      }
    }
  }

  return riskLevel;
};

export const growBasin = (i: number, j: number, visited: Set<string>, heightMap: HeightMap): void => {
  if (i < 0 || i >= heightMap.length || j < 0 || j >= heightMap[i].length || visited.has(`${i},${j}`) || heightMap[i][j] == 9) {
    return;
  }

  visited.add(`${i},${j}`);
  growBasin(i+1, j, visited, heightMap);
  growBasin(i-1, j, visited, heightMap);
  growBasin(i, j+1, visited, heightMap);
  growBasin(i, j-1, visited, heightMap);
}


export const partTwo = (puzzleInput: string): number => {
  const heightMap  = parseInput(puzzleInput);

  const basins: number[][] = [];

  let riskLevel = 0;
  for (let i = 0; i < heightMap.length; i++) {
    for (let j = 0; j < heightMap[i].length; j++) {
      if (isLowerThanNeighbours(i,j, heightMap)) {
        basins.push([i,j]);
      }
    }
  }

  let basinSizes: number[] = []
  for (const basin of basins) {
    const visited = new Set<string>();
    growBasin(basin[0], basin[1], visited, heightMap);
    basinSizes.push(visited.size);
  }

  basinSizes = basinSizes.sort((a,b) => b-a);
  return basinSizes[0] * basinSizes[1] * basinSizes[2];
};

