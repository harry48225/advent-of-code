export type Node = string;
export type Edge = {start: Node, end: Node, length: number};

export type Graph = {nodes: Node[], edges: Map<Node, Edge[]>, end: Node}

export const parseInput = (puzzleInput: string): Graph => {
  const grid = puzzleInput.trim().split("\n").map(line => [...line].map(element => +element));

  const nodes: Node[] = [];
  const edges = new Map<Node, Edge[]>();

  const coord = (i: number, j: number) => `${i},${j}`;

  for (let i = 0; i < grid.length; i++) {
    for (let j = 0; j < grid[i].length; j++) {
      const start = coord(i,j);
      nodes.push(start);
      edges.set(start, []);
      
      if (i > 0) {
        edges.set(start, [...(edges.get(start) ?? []), {start, end: coord(i-1,j), length: grid[i-1][j]}]);
      }

      if (i < grid.length - 1) {
        edges.set(start, [...(edges.get(start) ?? []), {start, end: coord(i+1,j), length: grid[i+1][j]}]);
      }

      if (j > 0) {
        edges.set(start, [...(edges.get(start) ?? []), {start, end: coord(i,j-1), length: grid[i][j-1]}]);
      }

      if (j < grid.length - 1) {
        edges.set(start, [...(edges.get(start) ?? []), {start, end: coord(i,j+1), length: grid[i][j+1]}]);
      }
    }
  }

  return {nodes, edges, end: coord(grid.length - 1, grid[grid.length-1].length - 1)};
}

export const findShortestPaths = (startNode: Node, graph: Graph): Map<Node, number> => {
  const visited = new Map<Node, number>();
  const queue = new Map<Node, number>();

  // make the edges a more useful datastructure
  const edgeLookup = new Map<Node, Edge[]>();

  queue.set(startNode, 0);

  while (queue.size > 0) {
    const [currentNode, distanceTravelled] = [...queue.entries()].sort(([n, d], [n2, d2]) => d-d2)[0];
    queue.delete(currentNode);
    visited.set(currentNode, distanceTravelled);
    if (currentNode == graph.end) {
      break;
    }
    
    // Now explore the edges around it
    const adjacentEdges = graph.edges.get(currentNode)?.filter(edge => !visited.has(edge.end)) ?? [];
    for (const edge of adjacentEdges) {
      let distance = distanceTravelled + edge.length;
      if (queue.has(edge.end)) {
        distance = Math.min(distance, queue.get(edge.end)!);
      }
      queue.set(edge.end, distance);
    }
  }
  return visited;
}

export const partOne = (puzzleInput: string): number => {
  const graph = parseInput(puzzleInput);

  const distances = findShortestPaths("0,0", graph);

  return distances.get(graph.end)!;
};

export const tileMap = (puzzleInput: string): string => {
  const grid = puzzleInput.trim().split("\n").map(line => [...line].map(element => +element));
  let outputGrid: string = ""; 
  for (let i = 0; i < 5; i++) {
    for (const gridRow of grid) {
      for (let j = 0; j < 5; j++) {
        outputGrid += gridRow.map(item => (item + i + j)).map(item => item <= 9 ? item : (item % 9)).join("");
      }

      outputGrid += "\n";
    }
  }

  return outputGrid.trimEnd();
}

export const partTwo = (puzzleInput: string): number => {
  return(partOne(tileMap(puzzleInput)));
};

