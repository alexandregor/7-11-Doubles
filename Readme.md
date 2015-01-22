This program simulates the 7-11-Doubles drinking game.
======================================================

The object of the game is to make other players drink their favorite beverage. Each 
player takes turns rolling a pair of dice.  A winning roll of the dice occurs if the total 
of the dice is 7 or 11, or the dice show doubles (1-1, 2-2, etc.)  When the current
player rolls a winning combination, they choose some other player to drink their 
beverage and the current player gets to roll again.  Any other combination is a losing 
roll and the current player's turn is over and the dice are passed to the next player.  
If the current player rolls a losing combination and any other player has not 
finished drinking, the current player ignores the losing roll and rolls again. Winning 
rolls are treated as above.  It is possible for more than one other player to be 
drinking at the same time.  In this simulation, the game can begin after two players 
have joined.  The game ends when only one player is left.  A player will leave the 
game after being made to drink a specified number of drinks.  If the player has 
drinks remaining, they must finish them, but should not be assigned any more
 drinks.
 
|Commands                                |      Meaning                          |
|:--------------------------------------:|:--------------------------------------|
|    HELP                                |Print these instructions               |
|    ADD [player name] [drinking time]   |Adds named player.                     |
|                                        |Drinking time is time to finish 1 drink|
|    SPEED [seconds]                     |Number of seconds between rolls.       | 
|                                        |Default 2                              |  
|    MAX [drinks]                        |Number of drinks before player		 |
|                                        |drops out. Default 5.                  |
|    START                               |Start the simulation                   |