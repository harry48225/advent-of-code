export const isTriangleValid = (sides: number[]) => {
  const combinations = [[0,1,2], [0,2,1], [1,2,0]];

  for (const combination of combinations) {
    if (sides[combination[0]] + sides[combination[1]] <= sides[combination[2]]) {
      return false;
    }
  }
  return true;
};

export const parseTrianglesByColumn = (puzzleInput: string): number[][] => {
  const triangles = puzzleInput
      .trim()
      .split('\n')
      .map(row => 
        row
        .trim()
        .split(/\s+/g)
        .map(digit => parseInt(digit)
      ));
  const transpose = triangles[0].map((_, colIndex) => triangles.map(row => row[colIndex])).flat();
  const newTriangles = [];

  for (let i = 0; i < transpose.length; i+=3) {
    newTriangles.push([transpose[i], transpose[i+1], transpose[i+2]]);
  }

  return newTriangles;
} 

export const numberOfValidTriangles = (triangles: number[][]) => {
  return triangles.filter(isTriangleValid).length;
}