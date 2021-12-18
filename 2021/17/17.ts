
type Probe = {
  x: number,
  y: number,
  xv: number,
  yv: number
}

type TargetArea = {
  startX: number,
  startY: number,
  endX: number,
  endY: number
}

export const parseInput = (puzzleInput:string): TargetArea => {
  const rawCoords = puzzleInput.trim().slice(13).trim().split(", ").map(coord => coord.slice(2).split(".."))
  return {startX: +rawCoords[0][0], endX: +rawCoords[0][1], startY: +rawCoords[1][0], endY: +rawCoords[1][1]};
}

export const inTargetArea = (probe: Probe, target: TargetArea): boolean => {
  return (probe.x >= target.startX && probe.x <= target.endX) && 
  (probe.y >= target.startY && probe.y <= target.endY);
}

export const exceededTargetArea = (probe: Probe, target: TargetArea): boolean => {
  return probe.x >= target.endX || probe.y <= target.startY;
}

export const doTimeStep = (probe: Probe): Probe => {
  probe.x += probe.xv;
  probe.y += probe.yv;
  probe.xv += -Math.sign(probe.xv) * 1;
  probe.yv -= 1;

  return probe;
}

export const partOne = (puzzleInput: string): number => {
  const target = parseInput(puzzleInput);

  return Math.floor(0.5 * (Math.abs(target.startY)-1) * (Math.abs(target.startY)))
};

export const partTwo = (puzzleInput: string): number => {
  const target = parseInput(puzzleInput);
  const velocities = new Set<string>();

  const BOUND = 500;
  for (let xv = 0; xv < BOUND; xv++) {
    for (let yv = -BOUND; yv < BOUND; yv++) {
      // Launch a probe.

      let probe = {x: 0, y: 0, xv, yv};

      while (!exceededTargetArea(probe, target)) {
        probe = doTimeStep(probe);
        if (inTargetArea(probe, target)) {
          velocities.add(`${xv},${yv}`);
          break;
        }
      }
    }
  }
  return velocities.size;
};

