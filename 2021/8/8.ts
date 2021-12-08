export type Signal = {
  signalPattern: string[],
  outputCode: string[]
};

export const parseInput = (puzzleInput: string): Signal[] => {
  return puzzleInput.trim().split("\n").map(line => line.split(" | ")).map(sections => ({
    signalPattern: sections[0].split(" "),
    outputCode: sections[1].split(" ")
  }));
}

export const countOccurences = (signal: Signal, length: number): number => {
  return signal.outputCode.filter(code => [...code].length == length).length;
}

export const partOne = (puzzleInput: string): number => {
  const signals = parseInput(puzzleInput);
  let total = 0

  // 1 has 2
  // 4 has 4
  // 7 has 3
  // 8 has 7
  for (let length of [2, 4, 3, 7]) {
    for (const signal of signals) {
      total += countOccurences(signal, length);
    }
  }
  
  return total;
};

export const partTwo = (puzzleInput: string): number => {
  const signals = parseInput(puzzleInput);
  let total = 0

  // 1 has 2
  // 4 has 4
  // 7 has 3
  // 8 has 7
  for (const signal of signals) {
    // the mapping is decryped to encoded
    const decoding = new Map<string, string[] | undefined>();
    // decoding.set("a", undefined);
    // decoding.set("b", undefined);
    // decoding.set("c", undefined);
    // decoding.set("d", undefined);
    // decoding.set("e", undefined);
    // decoding.set("f", undefined);
    // decoding.set("g", undefined);

    decoding.set("a", undefined);
    decoding.set("b", undefined);
    decoding.set("c", undefined);
    decoding.set("d", undefined);
    decoding.set("e", undefined);
    decoding.set("f", undefined);
    decoding.set("g", undefined);
    
    const one = signal.signalPattern.filter(code => code.length == 2)[0];
    const four = signal.signalPattern.filter(code => code.length == 4)[0];
    const seven = signal.signalPattern.filter(code => code.length == 3)[0];
    const eight = signal.signalPattern.filter(code => code.length == 7)[0];
    
    const letters = ["a", "b", "c", "d", "e", "f", "g"];
    // a is in 7 but not 1
    const a = letters.filter(letter => seven.includes(letter) && !one.includes(letter))[0];
    decoding.set("a", [a]);
    decoding.set("c", [...one]); // should be length 2
    decoding.set("f", [...one]); // should be length 2
    
    const eAndG = [...eight].filter(letter => !(seven.includes(letter) || four.includes(letter)));
    decoding.set("e", [...eAndG]); // should be length 2
    decoding.set("g", [...eAndG]); // should be length 2

    const bAndD = [...four].filter(letter => !one.includes(letter));
    decoding.set("b", [...bAndD]); // should be length 2
    decoding.set("d", [...bAndD]); // should be length 2

    // now work out the possible encodings
    // plain maps to encoded

    // pairs are
    // c:f
    // b:d
    // e:g
    const possibleEncodings = [];
    possibleEncodings.push({a: decoding.get("a")![0], b: decoding.get("b")![0], c: decoding.get("c")![0], d: decoding.get("d")![1], e: decoding.get("e")![0], f: decoding.get("f")![1], g: decoding.get("g")![1]});
    possibleEncodings.push({a: decoding.get("a")![0], b: decoding.get("b")![0], c: decoding.get("c")![1], d: decoding.get("d")![1], e: decoding.get("e")![0], f: decoding.get("f")![0], g: decoding.get("g")![1]});
    possibleEncodings.push({a: decoding.get("a")![0], b: decoding.get("b")![1], c: decoding.get("c")![0], d: decoding.get("d")![0], e: decoding.get("e")![0], f: decoding.get("f")![1], g: decoding.get("g")![1]});
    possibleEncodings.push({a: decoding.get("a")![0], b: decoding.get("b")![1], c: decoding.get("c")![1], d: decoding.get("d")![0], e: decoding.get("e")![0], f: decoding.get("f")![0], g: decoding.get("g")![1]});
    possibleEncodings.push({a: decoding.get("a")![0], b: decoding.get("b")![0], c: decoding.get("c")![0], d: decoding.get("d")![1], e: decoding.get("e")![1], f: decoding.get("f")![1], g: decoding.get("g")![0]});
    possibleEncodings.push({a: decoding.get("a")![0], b: decoding.get("b")![0], c: decoding.get("c")![1], d: decoding.get("d")![1], e: decoding.get("e")![1], f: decoding.get("f")![0], g: decoding.get("g")![0]});
    possibleEncodings.push({a: decoding.get("a")![0], b: decoding.get("b")![1], c: decoding.get("c")![0], d: decoding.get("d")![0], e: decoding.get("e")![1], f: decoding.get("f")![1], g: decoding.get("g")![0]});
    possibleEncodings.push({a: decoding.get("a")![0], b: decoding.get("b")![1], c: decoding.get("c")![1], d: decoding.get("d")![0], e: decoding.get("e")![1], f: decoding.get("f")![0], g: decoding.get("g")![0]});
  
    // now we need to verify each possible one
    
    const encodedSet = signal.signalPattern.map(segments => [...segments].sort().join("")).sort();
    const numbers = ["abcefg", "cf", "acdeg", "acdfg", "bcdf", "abdfg", "abdefg", "acf", "abcdefg", "abcdfg"];
    let correctEncoding  = undefined;
    for (const encoding of possibleEncodings) {
      // encode the list of numbers
      const encodedNumbers = numbers.map(number => [...number].map(letter => encoding[letter as 'a' | 'b' | 'c' | 'd' | 'e' | 'f' | 'g']).sort().join("")).sort();
      if (JSON.stringify(encodedNumbers) == JSON.stringify(encodedSet)) {
        correctEncoding = encoding;
        break;
      }
    }

    if (!correctEncoding) {
      throw Error("No encoding found! This is very bad.");
    }

    // invert to get a decoding
    const correctDecoding = Object.fromEntries(Object.entries(correctEncoding).map(([key, value]) => [value, key]));
    const digits = signal.outputCode.map(encoded => [...encoded].map(letter => correctDecoding[letter]).sort().join("")).map(decoded => numbers.indexOf(decoded));
    total += +digits.map(digit => `${digit}`).join("");
  }
  
  return total;
};
