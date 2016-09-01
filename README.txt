=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
CIS 120 Game Project
PennKey: cbros
=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=

** In my original game proposal, I said I was going to implement
   checkers, but I decided to change my game to Connect4, so at
   the bottom of this README.txt I have written the concepts that
   I have used when implementing the game. **

=================
=: Game README :=
=================

- Game: Connect 4.

- Classes created: 

   * Game: class where all the main game logic is located. Runs the game.
   * TokenContainer: class that extends JButton and represents a cell in the 
                     Connect4 board. There is a 6x7 2D array of TokenContainers 
                     in the Game class, representing the board.
   * NicknameAsker: class that extends JFrame and represents each of the 2 
                    windows that appear when the game is ran. Each window
                    ask a different player for their desired nickname. There
                    are two instances of NicknameAsker in the Game class, one
                    for each of the two players, and there are JButtons that
                    can be pressed to change the nicknames throughout the game.
   * Instructions: class that extends JFrame and contains a JTextPane to 
                   display the instructions of the game. It is shown when a 
                   JButton in the Game class titled "Instructions" is clicked.
   * HighScores: class that extends JFrame and contains a JTextPane that
                 interacts with the file highscores.txt using I/O to update
                 the game's high scores. There is space for 10 high scores, and
                 a high score is a game that finished with less moves than a 
                 previous high score game. The nickname of the player that won
                 is shown in each high score. There is one instance of this 
                 class in the Game class that is shown when the JButton 
                 "High Scores" is clicked.
                 
                 
- How to play:

  The GUI basically has 3 main parts: the top panel, the main panel, and the
  bottom panel.
  
  * Top panel: Instructions button: shows game instructions when clicked.
               Game status label: shows the status of the game. While playing,
                                  it shows PLAY! and the current number of 
                                  moves done in the game.
                                  When a player wins, it shows the nickname
                                  of the winner.
                                  If no one wins, it says that the game was
                                  a tie and that the user should press reset
                                  to start a new game.
               High Scores button: shows the 10 top scores for the game. High
                                   scores are determined by the number of moves
                                   a player needed to win. Less moves, better
                                   score. The high score window shows the 
                                   nickname of the player that won, and the
                                   number of moves the player used to win.
               Reset button: starts a new game.
               
   * Main panel: this is where the game board is, and where the players click
                 to add tokens to the board. It contains a 2D array of buttons,
                 each of which add a token to their column. To play, players
                 can click on any legal cell in the board. A legal empty
                 cell is a cell that is white (empty) and that either has a 
                 non-white cell below, or is located in the bottom row (if a 
                 user clicks on an illegal cell, nothing happens). When a
                 player clicks on a legal cell, a token of the appropriate 
                 color (red for player 1, green for player 2) will be added 
                 to the clicked legal cell.
                 Once a player connects 4 tokens of their color horizontally,
                 vertically or diagonally, that player wins, and no player
                 can add any more tokens unless they start a new game.
                 
   * Bottom panel: this panel just contains two buttons, the first one opens
                   a window that lets player 1 change its nickname, and the 
                   second does the same for player 2.
  
  

====================
=: 1-Core Concept :=
====================

Concept 1: 2D arrays

- Specific feature of the game that uses this concept:
The board of the game is a 2D array of JButtons.

- Why does it make sense to implement this feature with this concept?
It makes sense because in this way, a player can click on any JButton to
add tokens to the board. It also makes easier the task of checking if a game 
has finished, because with a 2D array it's possible to check every single cell. 


====================
=: 2-Core Concept :=
====================

Concept 2: Use of I/O 

- Specific feature of the game that uses this concept:
High scores.

- Why does it make sense to implement this feature with this concept?
Because having a text file with the high scores is the best way of maintaining
a high score board across all instances of the Game class. By using I/O, it's
possible to read and write scores to the high scores file, and if the game is
closed, the scores are saved in the file.


====================
=: 3-Core Concept :=
====================

Concept 3: JUnit testing

- Features of the game tested:
Nearly every class is tested: for invalid inputs, to check if valid inputs
return the desired results, etc.


====================
=: 4-Game Concept :=
====================

Concept 4: Complex Game State

- Specific feature of the game that uses this concept:
Checking if a game has finished is complex. The Game class must check for
horizontal, vertical or diagonal wins, and also must check if the whole
board is full, meaning that there is a tie. All of this requires a complex
search through the 2D array that represents the board.

