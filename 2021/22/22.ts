export type Interval = number[]
export type RebootStep = {x: Interval, y: Interval, z: Interval, turnOn: boolean}

export type Coordinate = string;
export type ReactorCore = Map<Coordinate, boolean>; 

export type Cuboid = {x: number[], y: number[], z: number[], name: string};
export type IntervalIntersection = {bounds: number[], names: string[]}
export type NamedInterval = {bounds: number[], name: string}

export const unionNamedIntervals = (intervals: NamedInterval[]): IntervalIntersection[] => {
  type MapEndpoint = {starts: string[], ends: string[], exclusiveStarts: string[], exclusiveEnds: string[]}
  let endpointMap= new Map<number, MapEndpoint>();
  for (const interval of intervals) {
    const start = endpointMap.get(interval.bounds[0]) ?? {starts: [], ends: [], exclusiveStarts: [], exclusiveEnds: []};
    start.starts.push(interval.name)
    endpointMap.set(interval.bounds[0] , start);
    const exclusiveStart = endpointMap.get(interval.bounds[0] - 1) ?? {starts: [], ends: [], exclusiveStarts: [], exclusiveEnds: []};
    exclusiveStart.exclusiveStarts.push(interval.name)
    endpointMap.set(interval.bounds[0]-1 , exclusiveStart);
    const end = endpointMap.get(interval.bounds[1]) ?? {starts: [], ends: [], exclusiveStarts: [], exclusiveEnds: []};
    end.ends.push(interval.name)
    endpointMap.set(interval.bounds[1] , end);
    const exclusiveEnd = endpointMap.get(interval.bounds[1] + 1) ?? {starts: [], ends: [], exclusiveStarts: [], exclusiveEnds: []};
    exclusiveEnd.exclusiveEnds.push(interval.name)
    endpointMap.set(interval.bounds[1]+1, exclusiveEnd);
  }

  type Endpoint = {location: number, names: string[], isStart: boolean, isExclusive: boolean}
  let endpoints: Endpoint[] = [...endpointMap.entries()]
    .sort((a, b) => a[0] - b[0])
    .flatMap(([location, endpoint]) => [
      {location, names: endpoint.exclusiveEnds, isStart: false, isExclusive: true},
      {location, names: endpoint.starts, isStart: true, isExclusive: false},
      {location, names: endpoint.ends, isStart: false, isExclusive: false},
      {location, names: endpoint.exclusiveStarts, isStart: true, isExclusive: true}
      ])
    .filter(endpoint => endpoint.names.length != 0);

  // build the output array
  endpoints.shift();
  const first = endpoints.shift()!;
  let containedNames = [...first!.names]
  let start: number | undefined = first.location;
  const outputIntervals: IntervalIntersection[] = [];
  for (const endpoint of endpoints) {
    if (endpoint.isExclusive) {
      if (endpoint.isStart) {
        outputIntervals.push({bounds: [start!, endpoint.location], names: [...containedNames]})
      }
      continue;
    } else {
      if (endpoint.isStart) {
        if (start !== undefined) {
          start = endpoint.location;
        }
        containedNames.push(...endpoint.names);
        start = endpoint.location;
      } else {
        outputIntervals.push({bounds: [start!, endpoint.location], names: [...containedNames]})
        containedNames = containedNames.filter(name => !endpoint.names.includes(name));
        if (containedNames.length == 0) {
          start = undefined;
        } else {
          if (endpoint.isExclusive) {
            start = endpoint.location;
          } else {
            start = endpoint.location + 1;
          }
        }
      }
    }
  }
  
  return outputIntervals;
}

export const subtractCuboid = (boids: Cuboid[], deadlyBoid: Cuboid) => {
  deadlyBoid.name = "hurty"; // rename the deadly boid
  boids.push(deadlyBoid); // owo what's this
  // Project the cuboids onto the coordinate axes
  const xProjection: NamedInterval[] = boids.map(boid => ({bounds: boid.x, name: boid.name}))
  const xReduction = unionNamedIntervals(xProjection);
  const yProjection: NamedInterval[] = boids.map(boid => ({bounds: boid.y, name: boid.name}))
  const yReduction = unionNamedIntervals(yProjection);
  const zProjection: NamedInterval[] = boids.map(boid => ({bounds: boid.z, name: boid.name}))
  const zReduction = unionNamedIntervals(zProjection);

  let currentName = 0;
  const newBoids: Cuboid[] = [];
  for (const xInterval of xReduction) {
    for (const yInterval of yReduction) {
      for (const zInterval of zReduction) {
        const namesInCommon = xInterval.names.filter(name => yInterval.names.includes(name)).filter(name => zInterval.names.includes(name));
        if (namesInCommon.length > 0 && !namesInCommon.includes("hurty")) { //&& xInterval.bounds[0] != xInterval.bounds[1] && yInterval.bounds[0] != yInterval.bounds[1] && zInterval.bounds[0] != zInterval.bounds[1]) {
          // create a new boid
          currentName++; // Get a new name
          const name = currentName + "";
          newBoids.push(JSON.parse(JSON.stringify({x: xInterval.bounds, y: yInterval.bounds, z: zInterval.bounds, name})));
        }
      }
    }
  }
  return joinBoids(JSON.parse(JSON.stringify(newBoids)));
}

export const joinBoids = (boids: Cuboid[]): Cuboid[] => {
  let changed = true;
  
  const same = (a: number[], b: number[]) => a.map((n, i) => a[i] == b[i]).every(i => i)
  const overlaps = (a: number[], b: number[]) => {
    return Math.max(a[0], b[0]) -1/2 <= Math.min(a[1], b[1]) + 1/2
  }

  const join = (a: number[], b: number[]) => {
    return [Math.min(a[0], b[0]), Math.max(a[1], b[1])];
  }

  let oldBoids: Cuboid[] = JSON.parse(JSON.stringify(boids));
  let timesRan = 0;
  while (changed && timesRan < oldBoids.length) {
    changed = false;
    let newBoids = [];
    const currentBoid = oldBoids.shift()!;
    for (const candidateBoid of oldBoids) {
      // join on z
      if (same(currentBoid.x, candidateBoid.x) && same(currentBoid.y, candidateBoid.y) && overlaps(currentBoid.z, candidateBoid.z)) {
        currentBoid.z = join(candidateBoid.z, currentBoid.z);
        changed = true;
      } else if (same(currentBoid.x, candidateBoid.x) && same(currentBoid.z, candidateBoid.z) && overlaps(currentBoid.y, candidateBoid.y)){
        currentBoid.y = join(candidateBoid.y, currentBoid.y);
        changed = true;
      } else if (same(currentBoid.y, candidateBoid.y) && same(currentBoid.z, candidateBoid.z) && overlaps(currentBoid.x, candidateBoid.x)) {
        currentBoid.x = join(candidateBoid.x, currentBoid.x);
        changed = true;
      } else {
        newBoids.push(candidateBoid);
      }
    }

    newBoids.push(currentBoid);
    oldBoids = newBoids;

    if (!changed) {
      timesRan++;
    } else {
      timesRan = 0;
    }
  }

  return oldBoids;
}

// Reduces a set of possibly intersecting cuboids into a set of disjoint cuboids
export const unionCuboids = (boids: Cuboid[]): Cuboid[] => {
  // Project the cuboids onto the coordinate axes
  const xProjection: NamedInterval[] = boids.map(boid => ({bounds: boid.x, name: boid.name}))
  const xReduction = unionNamedIntervals(xProjection);
  const yProjection: NamedInterval[] = boids.map(boid => ({bounds: boid.y, name: boid.name}))
  const yReduction = unionNamedIntervals(yProjection);
  const zProjection: NamedInterval[] = boids.map(boid => ({bounds: boid.z, name: boid.name}))
  const zReduction = unionNamedIntervals(zProjection);

  let currentName = 0;
  const newBoids: Cuboid[] = [];
  for (const xInterval of xReduction) {
    for (const yInterval of yReduction) {
      for (const zInterval of zReduction) {
        const namesInCommon = xInterval.names.filter(name => yInterval.names.includes(name)).filter(name => zInterval.names.includes(name));
        if (namesInCommon.length > 0) {
          // create a new boid
          currentName++; // Get a new name
          const name = currentName + "";
          newBoids.push({x: xInterval.bounds, y: yInterval.bounds, z: zInterval.bounds, name});
        }
      }
    }
  }
  return newBoids;
}

export const parseInput = (puzzleInput: string): RebootStep[] => {
  const steps: RebootStep[] = [];
  for (const line of puzzleInput.trim().split("\n")) {
    let splitLine = line.split(" ");
    const turnOn = splitLine[0] == "on";
    const intervals = splitLine[1].split(",").map(interval => interval.substring(2).split("..").map(n => +n));

    steps.push({x: intervals[0], y: intervals[1], z: intervals[2], turnOn});
  }

  return steps;
}

export const intersect = (a: Interval, b: Interval): Interval | undefined => {
  // if a ends before b starts 
  if (a[1] < b[0]) {
    return undefined;
  }

  // if ends before a starts
  if (b[1] < a[0]) {
    return undefined;
  }

  return [Math.max(a[0], b[0]), Math.min(a[1], b[1])]
}

export const initialiseReactor = (): ReactorCore => {
  const core : ReactorCore = new Map<Coordinate, boolean>();

  for (let i = -50; i <= 50; i++) {
    for (let j = -50; j <= 50; j++) {
      for (let k = -50; k <= 50; k++) {
        core.set(`${i},${j},${k}`, false);
      }
    }
  }

  return core;
}

export const calculateVolume = (boids: Cuboid[]) => {
  let total = 0;
  for (const boid of boids) {
    total += (Math.abs(boid.x[0] - boid.x[1]) + 1) * (Math.abs(boid.y[0] - boid.y[1]) + 1) * (Math.abs(boid.z[0] - boid.z[1]) + 1);
  }

  return total;
}

export const reboot = (core: ReactorCore, steps: RebootStep[]): ReactorCore => {
  for (const step of steps) {
    // intersect the intervals
    let X = intersect(step.x, [-50, 50]);
    let Y = intersect(step.y, [-50, 50]);
    let Z = intersect(step.z, [-50, 50]);

    if (X && Y && Z) {
      for (let x = X[0]; x <= X[1]; x++) {
        for (let y = Y[0]; y <= Y[1]; y++) {
          for (let z = Z[0]; z <= Z[1]; z++) {
            core.set(`${x},${y},${z}`, step.turnOn);
          }
        }
      }
    }
  }

  return core;
}

export const partOne = (puzzleInput: string): number => {
  const steps = parseInput(puzzleInput);

  const reactorCore = reboot(initialiseReactor(), steps);
  return [...reactorCore.values()].filter(cube => !!cube).length;
};

export const partTwo = (puzzleInput: string): number => {
  const steps = parseInput(puzzleInput);
  let boids: Cuboid[] = [];
  let number = 0;
  for (const step of steps) {
    const boid: Cuboid = {x: step.x, y: step.y, z: step.z, name: 'from the step'};

    boids = boids.flatMap(healthyBoid => joinBoids(subtractCuboid([healthyBoid], boid)));
    if (step.turnOn) {
      //boids = unionCuboids([...boids, boid]);
      boids = [...boids, boid]
      boids.forEach((boid, index) => boid.name = index + ""); // Rename
    }
    const currentVolume = calculateVolume(boids);
    number++;
  }

  return calculateVolume(boids);
};

