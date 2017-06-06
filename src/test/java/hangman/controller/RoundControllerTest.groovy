package hangman.controller

import hangman.model.FEStatus
import hangman.model.Game
import hangman.service.HangmanService
import org.springframework.http.HttpStatus
import spock.lang.Specification

class GameControllerTest extends Specification {
    GameController gameController = new GameController()
    HangmanService hangmanService = Mock()

    def setup(){
        gameController.hangmanService = hangmanService
    }

    def "Test VerifyWord when letter belongs to hiddenWord"() {
        given:
        def game = createGame("camera",3)
        def output = game.getFeStatus().getOutput()

        when:
        def map = new HashMap<>()
        map.put("letter","c")
        hangmanService.findGameById(_) >> game

        def response = gameController.verifyLetter(game.getFeStatus().getGameId(),map)
        then:
        response.statusCode.equals(HttpStatus.OK)
        game.getFeStatus().getIntNumLives() == 3
        game.getFeStatus().message.equals("Hidden word updated including the letter")
        game.getFeStatus().getOutput().equals(output)
    }

    def "Test VerifyWord when letter does not belong to hiddenWord"() {
        given:
        def game = createGame("camera",3)
        def output = game.getFeStatus().getOutput()

        when:
        def map = new HashMap<>()
        map.put("letter","i")
        hangmanService.findGameById(_) >> game

        def response = gameController.verifyLetter(game.getFeStatus().getGameId(),map)
        then:
        response.statusCode.equals(HttpStatus.OK)
        game.getFeStatus().getIntNumLives() == 2
        game.getFeStatus().message.equals("You have lost a life but you can continue playing")
        game.getFeStatus().getOutput().equals(output)
    }

    def "Test VerifyWord when you do not have more lives to play"() {
        given:
        def game = createGame("camera",0);

        when:
        def map = new HashMap<>()
        map.put("letter","i")
        hangmanService.findGameById(_) >> game

        def response = gameController.verifyLetter(game.getFeStatus().getGameId(),map)
        then:
        response.statusCode.equals(HttpStatus.OK)
        game.getFeStatus().getIntNumLives() == 0
        game.getFeStatus().message.equals("You do not have more lives to continue playing this game")
    }

    def "Test VerifyWord when game does not exist"() {
        given:
        def id="e7a5e2a0-2426-4b50-81a1-2e8dc9b3cf4b"
        when:
        def map = new HashMap<>()
        map.put("letter","c")

        def response = gameController.verifyLetter(id,map)
        then:
        response.statusCode.equals(HttpStatus.NOT_FOUND)

    }

    def "Test VerifyWord when word has already been used"() {
        given:
        def game = createGame("camera",2)
        def letterUsed = new HashSet<>()
        letterUsed.add("p".charAt(0))
        game.getFeStatus().setLettersUsed(letterUsed)
        when:
        def map = new HashMap<>()
        map.put("letter","p")
        hangmanService.findGameById(_) >> game

        def response = gameController.verifyLetter(game.getFeStatus().getGameId(),map)
        then:
        response.statusCode.equals(HttpStatus.OK)
        game.getFeStatus().getIntNumLives() == 2
        game.getFeStatus().message.equals("You have already used that letter")
    }

    def "Test VerifyWord when you guess last letter of hidden word"() {
        given:
        def game = createGame("camera",2)
        def output = new ArrayList<>()
        output.add(0,"c".charAt(0))
        output.add(1,"a".charAt(0))
        output.add(2,"m".charAt(0))
        output.add(3,"e".charAt(0))
        output.add(4,'-')
        output.add(5,"a".charAt(0))
        game.getFeStatus().setOutput(output)
        when:
        def map = new LinkedHashMap<>()
        map.put("letter","r")
        hangmanService.findGameById(_) >> game

        def response = gameController.verifyLetter(game.getFeStatus().getGameId(),map)
        then:
        response.statusCode.equals(HttpStatus.OK)
        game.getFeStatus().getIntNumLives() == 2
        game.getFeStatus().message.equals("Yipe!! You have found the hidden word")
    }

    def "Test VerifyWord when you fail guessing and do not have more lives"() {
        given:
        def game = createGame("camera",1)
        def output = new ArrayList<>()
        output.add(0,"c".charAt(0))
        output.add(1,"a".charAt(0))
        output.add(2,"m".charAt(0))
        output.add(3,"e".charAt(0))
        output.add(4,'-')
        output.add(5,"a".charAt(0))
        game.getFeStatus().setOutput(output)
        when:
        def map = new LinkedHashMap<>()
        map.put("letter","n")
        hangmanService.findGameById(_) >> game

        def response = gameController.verifyLetter(game.getFeStatus().getGameId(),map)
        then:
        response.statusCode.equals(HttpStatus.OK)
        game.getFeStatus().getIntNumLives() == 0
        game.getFeStatus().message.equals("Game Over: You have lost all your lives. Hidden word was: " + game.getHiddenWord());
    }

    def "Test VerifyWord when character not correct"() {
        given:
        def game = createGame("camera",3)
        def output = game.getFeStatus().getOutput()

        when:
        def map = new HashMap<>()
        map.put("letter","7")
        hangmanService.findGameById(_) >> game

        def response = gameController.verifyLetter(game.getFeStatus().getGameId(),map)
        then:
        response.statusCode.equals(HttpStatus.OK)
        game.getFeStatus().getIntNumLives() == 3
        game.getFeStatus().message.equals("The character you have entered is not a letter")
    }

    def "Test getAllGames return an empty array if no game has been started"() {

    }

    def "Test getAllGames return list of games already started"() {

    }

    def "Test getGame returns NOT FOUND if game does not exist"() {

    }

    def "Test getGame returns game details of game in progress"() {

    }

    def "Test createGame returns CONFLICT if game with gameId already exist"() {

    }

    def "Test createGame returns new game"() {

    }

    private Game createGame(String hiddenWord, int numLives){
        String gameId = UUID.randomUUID().toString();
        ArrayList<Character> output = new ArrayList<>(hiddenWord.length())
        Set<Character> lettersUsed = new HashSet<>()
        for(int i=0;i<hiddenWord.length();i++)
            output.add(i,'-');
        FEStatus feStatus = new FEStatus(gameId,numLives,lettersUsed,output,null)
        Game game = new Game(hiddenWord,feStatus)

        return game
    }

}
