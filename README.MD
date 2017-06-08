# Hangman
This version of hangman is designed for a player to guess a hidden word playing against the computer. The game will
generate a random word from a dictionary and the player will try to guess it suggesting letters, with a certain number
of guesses (5).

----------

Endpoints available
-------------
    - GET request to /game/ returns a list of games
    - GET request to /game/gameId returns information of a specific game
    - POST request to /game/ returns a new game
    - PUT request to /game/gameId updates the game based on internal logic

Build
-------------
    ./gradlew clean build

Run
-------------
     Execute ./gradlew bootRun

Test
-------------
     Unit tests available for different scenarios of verifyLetter method.
     Also I defined more tests for the other controller methods but I did not have time to implement them
     To run tests execute ./gradlew clean build test

How to play
-------------
    1. Open a browser and go to http://localhost:8080
    2. You will be redirect to hangman index page
    3. Select from one of the options depending on what you want to do
      - Start a new game
      - Restart a in progress game (Need to specify gameId)
      - View all games (ongoing and finished ones)
    4. For the first 2 options a game will be load and will give you the option of
    suggesting letters till either you find hidden word or you loose all your life
    5. When you loose all your lives the game will give you the option of starting a new one
    6. After viewing all games you will have the option to start or restart one from the
    list
    Note: if in a game the number of guesses is 0 it means the game is already finished (either the player found the
    hidden word or he lost all his lives)