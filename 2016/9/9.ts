enum State {
  LookingForMarker,
  FoundMarker,
  Decompressing
};

type Marker = {
  length: number,
  repeatCount: number
}

export const decompress = (compressed: string): string => {
  let decompressed = '';

  let state = State.LookingForMarker;
  let markerBuffer = "";
  let repeatBuffer = "";
  let marker: Marker = {length: -1, repeatCount: -1};

  for (const char of compressed) {
    if (state == State.LookingForMarker && char == "(") {
      state = State.FoundMarker;
      markerBuffer = "";
    } else if (state == State.FoundMarker) {
      if (char == ")") {
        state = State.Decompressing;
        const splitMarkerBuffer = markerBuffer.split("x").map(digit => parseInt(digit));
        marker = {length: splitMarkerBuffer[0], repeatCount: splitMarkerBuffer[1]};
        repeatBuffer = ""
      } else {
        markerBuffer += char;
      }
    } else if (state == State.Decompressing) {
      if (marker.length > 0) {
        repeatBuffer += char;
        marker.length -= 1;
      }

      if (marker.length == 0) {
        decompressed += repeatBuffer.repeat(marker.repeatCount);
        state = State.LookingForMarker;
      }
    } else {
      decompressed += char;
    }
  }
  return decompressed;
}

export const partOne = (puzzleInput: string): number => {
  const parsedPuzzleInput = puzzleInput.trim().split("\n").map(line => line.trim()).join("").replace(/\s+/g, "");
  return decompress(parsedPuzzleInput).length;
}

export const findDecompressedLength = (compressed: string): number => {
  let length = 0;
  let state = State.LookingForMarker;
  let markerBuffer = "";
  let repeatBuffer = "";
  let marker: Marker = {length: -1, repeatCount: -1};

  for (const char of compressed) {
    if (state == State.LookingForMarker && char == "(") {
      state = State.FoundMarker;
      markerBuffer = "";
    } else if (state == State.FoundMarker) {
      if (char == ")") {
        state = State.Decompressing;
        const splitMarkerBuffer = markerBuffer.split("x").map(digit => parseInt(digit));
        marker = {length: splitMarkerBuffer[0], repeatCount: splitMarkerBuffer[1]};
        repeatBuffer = ""
      } else {
        markerBuffer += char;
      }
    } else if (state == State.Decompressing) {
      if (marker.length > 0) {
        repeatBuffer += char;
        marker.length -= 1;
      }

      if (marker.length == 0) {
        length += findDecompressedLength(repeatBuffer) * marker.repeatCount;
        state = State.LookingForMarker;
      }
    } else if (!/\s+/.test(char)) {
      length += 1;
    }
  }
  return length;
}