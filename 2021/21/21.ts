export type Player = {
  position: number
  score: number
  name?: string
}

class Die {
  timesRolled: number;
  currentValue: number;
  constructor() {
    this.timesRolled = 0;
    this.currentValue = 0;
  }
  
  roll(): number {
    this.currentValue++;
    if (this.currentValue > 100) {
      this.currentValue = 1
    }
    this.timesRolled++;
    return this.currentValue;
  }
}

export const parseInput = (puzzleInput: string): Player[] => {
  const startingPositions = puzzleInput.trim().split("\n").map(line => +line.split(" starting position: ")[1])

  return startingPositions.map(position => ({position, score: 0}));
}

export const playGame = (players: Player[]): {winner: Player, loser: Player, timesDieRolled: number} => {
  let die = new Die();
  
  while (true) {
    for (let i = 0; i < 2; i++) {
      const player = players[i];
      // roll the dice three times
      const movement = die.roll() + die.roll() + die.roll();
      // move the player
      player.position += movement;
      player.position = ((player.position - 1) % 10) + 1;
      player.score += player.position;

      if (player.score >= 1000) {
        const loser: Player = players[(i+1) % players.length]
        return {winner: player, loser, timesDieRolled: die.timesRolled}
      }
    }
  }
}

export type GameOutcomes = {
  playerOneWins: number,
  playerZeroWins: number
}

export const playMultiverseGame = (players: Player[], seenStates: Map<string, GameOutcomes>): GameOutcomes => {
  // check for win
  for (const player of players) {
    if (player.score >= 21) {
      if (player.name == '0') {
        return {playerZeroWins: 1, playerOneWins: 0} as GameOutcomes
      } else {
        return {playerZeroWins: 0, playerOneWins: 1} as GameOutcomes
      }
    }
  }

  const outcome: GameOutcomes = {playerZeroWins: 0, playerOneWins: 0};

  // start new games
  for (let firstRoll = 1; firstRoll <= 3; firstRoll++) {
    for (let secondRoll = 1; secondRoll <= 3; secondRoll++) {
      for (let thirdRoll = 1; thirdRoll <= 3; thirdRoll++) {
        // deep clone the players
        let newPlayers: Player[] = JSON.parse(JSON.stringify(players))
        newPlayers[0].position += firstRoll + secondRoll + thirdRoll;
        newPlayers[0].position = ((newPlayers[0].position - 1) % 10) + 1;
        newPlayers[0].score += newPlayers[0].position;
        
        // state, player zeros score, player one's score, whos next to play
        let state = "";
        if (newPlayers[0].name == '0') {
          state = `${newPlayers[0].score}:${newPlayers[0].position},${newPlayers[1].score}:${newPlayers[1].position},${newPlayers[1].name}`
        } else {
          state = `${newPlayers[1].score}:${newPlayers[1].position},${newPlayers[0].score}:${newPlayers[0].position},${newPlayers[1].name}`
        }

        if (!seenStates.has(state)) {
          seenStates.set(state, playMultiverseGame([newPlayers[1], newPlayers[0]], seenStates));
        }
        
        const gameOutcome = seenStates.get(state)!;
        outcome.playerOneWins += gameOutcome.playerOneWins;
        outcome.playerZeroWins += gameOutcome.playerZeroWins;
      }
    }
  }

  return outcome;
}

export const partOne = (puzzleInput: string): number => {
  const players = parseInput(puzzleInput);
  const outcome = playGame(players);
  return outcome.loser.score * outcome.timesDieRolled;
};

export const partTwo = (puzzleInput: string): number => {
  const players = parseInput(puzzleInput);
  players[0].name = "0";
  players[1].name = "1";
  const seenStates = new Map<string, GameOutcomes>();
  const outcome = playMultiverseGame(players, seenStates);
  //console.log(outcome);
  //console.log(seenStates);
  return Math.max(outcome.playerOneWins, outcome.playerZeroWins);
};

