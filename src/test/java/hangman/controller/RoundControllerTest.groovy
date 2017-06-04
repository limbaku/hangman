package hangman.controller

import hangman.model.FEStatus
import hangman.model.Round
import hangman.service.HangmanService
import org.springframework.http.HttpStatus
import spock.lang.Specification

class RoundControllerTest extends Specification {
    RoundController roundController = new RoundController()
    HangmanService hangmanService = Mock()

    def setup(){
        roundController.hangmanService = hangmanService
    }

    def "Test VerifyWord when letter belongs to hiddenWord"() {
        given:
        def round = createRound("camera",3)
        def output = round.getFeStatus().getOutput()

        when:
        def map = new HashMap<>()
        map.put("letter","c")
        hangmanService.findRoundById(_) >> round

        def response = roundController.verifyLetter(round.getFeStatus().getRoundId(),map)
        then:
        response.statusCode.equals(HttpStatus.OK)
        round.getFeStatus().getIntNumLives() == 3
        round.getFeStatus().message.equals("Hidden word updated including the letter")
        round.getFeStatus().getOutput().equals(output)
    }

    def "Test VerifyWord when letter does not belong to hiddenWord"() {
        given:
        def round = createRound("camera",3)
        def output = round.getFeStatus().getOutput()

        when:
        def map = new HashMap<>()
        map.put("letter","i")
        hangmanService.findRoundById(_) >> round

        def response = roundController.verifyLetter(round.getFeStatus().getRoundId(),map)
        then:
        response.statusCode.equals(HttpStatus.OK)
        round.getFeStatus().getIntNumLives() == 2
        round.getFeStatus().message.equals("You have lost a life but you can continue playing")
        round.getFeStatus().getOutput().equals(output)
    }

    def "Test VerifyWord when you do not have more lives to play"() {
        given:
        def round = createRound("camera",0);

        when:
        def map = new HashMap<>()
        map.put("letter","i")
        hangmanService.findRoundById(_) >> round

        def response = roundController.verifyLetter(round.getFeStatus().getRoundId(),map)
        then:
        response.statusCode.equals(HttpStatus.OK)
        round.getFeStatus().getIntNumLives() == 0
        round.getFeStatus().message.equals("You do not have more lives to continue playing this round")
    }

    def "Test VerifyWord when round does not exist"() {
        given:
        def id="e7a5e2a0-2426-4b50-81a1-2e8dc9b3cf4b"
        when:
        def map = new HashMap<>()
        map.put("letter","c")

        def response = roundController.verifyLetter(id,map)
        then:
        response.statusCode.equals(HttpStatus.NOT_FOUND)

    }

    def "Test VerifyWord when word has already been used"() {
        given:
        def round = createRound("camera",2)
        def letterUsed = new HashSet<>()
        letterUsed.add('p')
        round.getFeStatus().setLettersUsed(letterUsed)
        when:
        def map = new HashMap<>()
        map.put("letter","p")
        hangmanService.findRoundById(_) >> round

        def response = roundController.verifyLetter(round.getFeStatus().getRoundId(),map)
        then:
        response.statusCode.equals(HttpStatus.OK)
        round.getFeStatus().getIntNumLives() == 2
        round.getFeStatus().message.equals("You have already used that letter")
    }

    private Round createRound(String hiddenWord, int numLives){
        String roundId = UUID.randomUUID().toString();
        ArrayList<Character> output = new ArrayList<>(hiddenWord.length())
        Set<Character> lettersUsed = new HashSet<>()
        Set<String> wordsUsed = new HashSet<>()
        for(int i=0;i<hiddenWord.length();i++)
            output.add(i,'-');
        FEStatus feStatus = new FEStatus(roundId,numLives,lettersUsed,wordsUsed,output,null)
        Round round = new Round(hiddenWord,feStatus)

        return round
    }
}
