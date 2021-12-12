export type Node = string;

export type Edge = {
  start: Node
  end: Node
}

export const parseInput = (puzzleInput: string): Edge[] => {
  return puzzleInput.trim().split("\n")
  .map(line => line.split("-"))
  .map(ends => ({
      start: ends[0], end: ends[1]
    })
  );
}

export const getVisitableNodes = (start: Node, edges: Edge[]) => {
  const visitable: Node[] = [];

  for (const edge of edges) {
    if (edge.start == start) {
      visitable.push(edge.end);
    } else if (edge.end == start) {
      visitable.push(edge.start);
    }
  }

  return visitable;
}


export const exploreCaves = (current: Node, visited: Node[], edges: Edge[], routes: Set<string>) => {
  if (current == "end") {
    routes.add(visited.join(","));
    return;
  }
  
  // Explore the caves, if the node is small you can only enter it once
  visited = JSON.parse(JSON.stringify(visited));
  for (const node of getVisitableNodes(current, edges)) {
    if (node.toLowerCase() == node && visited.includes(node)) {
      continue;
    }

    exploreCaves(node, [...visited, node], edges, routes);
  }
}

export const partOne = (puzzleInput: string): number => {
  const edges = parseInput(puzzleInput);
  const routes = new Set<string>();

  exploreCaves("start", ["start"], edges, routes);

  return routes.size;
};

export const exploreCavesTwo = (current: Node, visited: Node[], edges: Edge[], routes: Set<string>, canDoubleVisit: boolean) => {
  if (current == "end") {
    routes.add(visited.join(","));
    return;
  }
  
  // Explore the caves, if the node is small you can only enter it once
  visited = JSON.parse(JSON.stringify(visited));
  for (const node of getVisitableNodes(current, edges)) {
    if (node == "start") {
      continue;
    } else if (node == node.toLowerCase() && visited.includes(node)) {
      if (canDoubleVisit) {
        exploreCavesTwo(node, [...visited, node], edges, routes, false);
      }
    } else {
      exploreCavesTwo(node, [...visited, node], edges, routes, canDoubleVisit);
    }
  }
}

export const partTwo = (puzzleInput: string): number => {
  const edges = parseInput(puzzleInput);
  const routes = new Set<string>();
  exploreCavesTwo("start", ["start"], edges, routes, true);
  return routes.size;
};

