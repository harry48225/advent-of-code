type Image = Map<string, boolean> // Contains the coordinates of the lit pixels
type EnhancementAlgorithm = boolean[];

export const parseInput = (puzzleInput: string): [image: Image, algorithm: EnhancementAlgorithm] => {
  let lines = puzzleInput.trim().split("\n").filter(line => line != "");
  let algorithm = [...lines.shift()!].map(pixel => pixel == "#");
  let image: Image = new Map<string, boolean>();
  for (let i = 0; i < lines.length; i++) {
    const line = [...lines[i]]
    for (let j = 0; j < line.length; j++) {
      image.set(`${i},${j}`, line[j] == '#');
    }
  }
  image.set('other', false);
  return [image, algorithm];
}

export const enhanceImage = (image: Image, algorithm: EnhancementAlgorithm): Image => {
  const getPixel = (i: number, j: number): boolean => {
    return image.get(`${i},${j}`) ?? image.get('other')!;
  }
  const newImage: Image = new Map<string, boolean>();

  // Work out the bounds of the image
  const numericImage = [...image.keys()].filter(key => key != 'other').map(pixel => pixel.split(",").map(n => +n));
  const minX = Math.min(...numericImage.map(pixel => pixel[0]))
  const maxX = Math.max(...numericImage.map(pixel => pixel[0]))
  const minY = Math.min(...numericImage.map(pixel => pixel[1]))
  const maxY = Math.max(...numericImage.map(pixel => pixel[1]))
  let debugPrint = "";
  for (let i = minX - 1; i <= maxX + 1; i++) {
    for (let j = minY - 1; j <= maxY + 1; j++) {
      const c = [i,j];
    
      // Get the 9 pixels around it.
      const adjBinary = [[c[0] - 1, c[1] - 1], [c[0]-1, c[1]], [c[0]-1, c[1] + 1],
                                [c[0], c[1] - 1], [c[0], c[1]], [c[0], c[1] + 1],
                                [c[0]+1, c[1] - 1], [c[0]+1, c[1]], [c[0] + 1, c[1] + 1]].map(coordainte => getPixel(coordainte[0], coordainte[1])).map(isLit => isLit ? '1' : '0').join("");
      const adjNumber = parseInt(adjBinary, 2);
      const newPixel = algorithm[adjNumber];
      newImage.set(`${i},${j}`, newPixel);
      if (newPixel) {
        debugPrint += "#";
      } else {
        debugPrint += ".";
      }
    }
    debugPrint += "\n";
  }

  // Now deal with the other
  const currentOther = image.get('other')!;
  let other = false;
  if (currentOther) {
    other = algorithm[algorithm.length - 1];
  } else {
    other = algorithm[0];
  }
  newImage.set('other', other);
  //console.log(debugPrint);
  return newImage;
}

export const partOne = (puzzleInput: string): number => {
  let [image, algorithm] = parseInput(puzzleInput);
  image = enhanceImage(image, algorithm);
  image = enhanceImage(image, algorithm);

  // Count the lit pixels
  return [...image.values()].filter(value => !!value).length;
};

export const partTwo = (puzzleInput: string): number => {
  let [image, algorithm] = parseInput(puzzleInput);
  for (let _ = 0; _ < 50; _++) {
    image = enhanceImage(image, algorithm);
  }
  // Count the lit pixels
  return [...image.values()].filter(value => !!value).length;
};

