export enum FloorItemTypes {
  Microchip,
  Generator
}

type FloorItem = {
  type: FloorItemTypes,
  element: string
}

type State = {
  floors: FloorItem[][],
  elevatorLocation: number,
}

export const isStateValid = (state: State): boolean => {
  for (const floor of state.floors) {
    const microchips = floor.filter(item => item.type == FloorItemTypes.Microchip);
    const generators = floor.filter(item => item.type == FloorItemTypes.Generator);
    for (const microchip of microchips) {
      if (generators.length > 0 && generators.every(generator => generator.element != microchip.element)) {
        return false;
      }
    }
  }
  return true;
}

export const parseInput = (puzzleInput: string): State  => {
  let floors: FloorItem[][] = [];
  const splitLines = puzzleInput.trim().split("\n").map(line => line.trim());
  for (const floor of splitLines) {
    const items: FloorItem[] = []
    const rawItemsString = floor.split("floor contains ")[1];
    if (!rawItemsString.includes("nothing relevant")) {
      const rawItems = rawItemsString.replace("and", ", ").split(", ").map(item => item.trim().slice(2));
      for (const item of rawItems) {
        if (item.includes("microchip")) {
          items.push({type: FloorItemTypes.Microchip, element: item.split("-compatible")[0]});
        } else if (item.includes("generator")) {
          items.push({type: FloorItemTypes.Generator, element: item.split(" ")[0]});
        }
      }
    }
    floors.push(items);
  }
  
  return {floors, elevatorLocation: 0};
}

export const serialiseState = (state: State): string => {
  // first digit is the elevator location
  // rest of the digits are the microchip location and then the generator location in increasing microchip location order and then increasing generator location order if they're the same
  let serialised = "";
  serialised += state.elevatorLocation;
  const microchipGeneratorPairs: string[] = [];
  const microchips = state.floors.flat().filter(item => item.type == FloorItemTypes.Microchip);
  for (const microchip of microchips) {
    let microchipFloor = -1;
    let generatorFloor = -1;
    // find the floor the microchip is on
    for (const [floorNumber, floor] of state.floors.entries()) {
      if (floor.find(item => item.type == FloorItemTypes.Microchip && item.element == microchip.element)) {
        microchipFloor = floorNumber;
      }

      if (floor.find(item => item.type == FloorItemTypes.Generator && item.element == microchip.element)) {
        generatorFloor = floorNumber;
      }
    }
    microchipGeneratorPairs.push(`${microchipFloor}${generatorFloor}`);
  }
  return serialised + microchipGeneratorPairs.sort().join("");
}

export const isStateComplete = (state: State): boolean => {
  return state.floors.reverse().slice(1).every(floor => floor.length == 0);
}

const deepCloneState = (state: State): State => JSON.parse(JSON.stringify(state));

export const findMinimumNumberOfSteps = (initalState: State): number => {
  type StateWithStepsAndHistory = {state: State, steps: number, history: State[]};
  const stateQueue: StateWithStepsAndHistory[] = [{state: deepCloneState(initalState), steps: 0, history: [deepCloneState(initalState)]}];
  const seenStates = new Set<string>();
  seenStates.add(serialiseState(initalState));
  while (!isStateComplete(deepCloneState(stateQueue[0].state))) {
    const currentStateWithSteps = stateQueue.shift();
    const floorNumber = currentStateWithSteps!.state.elevatorLocation;
    const floor = currentStateWithSteps!.state.floors[floorNumber];
    for (const [itemIndex, item] of floor.entries()) {
      // Try moving all the single items up and down
      const numberOfFloors = currentStateWithSteps!.state.floors.length;
      if (floorNumber < numberOfFloors - 1) {
        const newState = deepCloneState(currentStateWithSteps!.state);
        newState.floors[floorNumber].splice(itemIndex, 1); // Remove the item
        newState.floors[floorNumber + 1].push(item);
        newState.elevatorLocation = floorNumber + 1;
        if (!seenStates.has(serialiseState(newState)) && isStateValid(deepCloneState(newState))) {
          stateQueue.push({state: newState, steps: currentStateWithSteps!.steps + 1, history: [...currentStateWithSteps!.history, deepCloneState(newState)]});
          seenStates.add(serialiseState(newState));
        }
      }

      if (floorNumber > 0) {
        const newState = deepCloneState(currentStateWithSteps!.state);
        newState.floors[floorNumber].splice(itemIndex, 1); // Remove the item
        newState.floors[floorNumber - 1].push(item);
        newState.elevatorLocation = floorNumber - 1;
        if (!seenStates.has(serialiseState(newState)) && isStateValid(deepCloneState(newState))) {
          stateQueue.push({state: newState, steps: currentStateWithSteps!.steps + 1, history: [...currentStateWithSteps!.history, deepCloneState(newState)]});
          seenStates.add(serialiseState(newState));
        }
      }

      for (const [secondItemIndexOffset, secondItem] of floor.slice(itemIndex + 1).entries()) {
        if (floorNumber < numberOfFloors - 1) {
          const newState = deepCloneState(currentStateWithSteps!.state);
          newState.floors[floorNumber].splice(itemIndex, 1); // Remove the item
          newState.floors[floorNumber].splice(secondItemIndexOffset + itemIndex, 1); // Remove the item
          newState.floors[floorNumber + 1].push(item);
          newState.floors[floorNumber + 1].push(secondItem);
          newState.elevatorLocation = floorNumber + 1;
          if (!seenStates.has(serialiseState(newState)) && isStateValid(deepCloneState(newState))) {
            stateQueue.push({state: newState, steps: currentStateWithSteps!.steps + 1, history: [...currentStateWithSteps!.history, deepCloneState(newState)]});
            seenStates.add(serialiseState(newState));
          }
        }

        if (floorNumber > 0) {
          const newState = deepCloneState(currentStateWithSteps!.state);
          newState.floors[floorNumber].splice(itemIndex, 1); // Remove the item
          newState.floors[floorNumber].splice(secondItemIndexOffset + itemIndex, 1); // Remove the item
          newState.floors[floorNumber - 1].push(item);
          newState.floors[floorNumber - 1].push(secondItem);
          newState.elevatorLocation = floorNumber - 1;
          if (!seenStates.has(serialiseState(newState)) && isStateValid(deepCloneState(newState))) {
            stateQueue.push({state: newState, steps: currentStateWithSteps!.steps + 1, history: [...currentStateWithSteps!.history, deepCloneState(newState)]});
            seenStates.add(serialiseState(newState));
          }
        }
      }
    }
  }
  //stateQueue[0].history.forEach(state => printState(state));
  return stateQueue[0].steps;
}

const printState = (state: State) => {
  let output: string[] = [];
  for (const [floorNumber, floor] of state.floors.entries()) {
    let string = `F${floorNumber} ${state.elevatorLocation == floorNumber ? "E" : "."} `;
    string += floor.map(item => `${item.element.at(0)?.toUpperCase()}${item.type == FloorItemTypes.Generator ? "G" : "M"}`).join(" ");
    output.push(string);
  }

  console.log(output.reverse().join("\n"));
}