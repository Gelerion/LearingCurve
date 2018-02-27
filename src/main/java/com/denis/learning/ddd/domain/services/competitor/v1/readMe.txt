On completion of an OnlineDeathMatch, each player has his score updated based on how he
performed in the game. One option for locating this logic is to place the score calculation and
reward logic within the OnlineDeathmatch.

One big problem with CalculateNewPlayerScores() being part of the OnlineDeathMatch is
that the rules are flexible; the business may want to award double points or hand out special
prizes at certain times. What the business is saying in cases like this is that score and reward
calculation are fundamentally important concepts within themselves. The same special
promotions are often applied to different types of games: TeamBattle, CaptureTheFlag,
GangCrusade, and so on.