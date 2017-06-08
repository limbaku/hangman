package hangman.controller;

import hangman.model.FEStatus;
import hangman.model.Game;
import hangman.model.WordGenerator;
import hangman.service.HangmanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.apache.commons.lang3.math.NumberUtils.isNumber;

@Controller
public class GameController {

    @Autowired
    HangmanService hangmanService;

    @Autowired
    WordGenerator wordGenerator;

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String index() {
        return "redirect:/index.html";

    }

    @CrossOrigin
    @RequestMapping(path = "/game/", method = RequestMethod.GET)
    public ResponseEntity<Collection<FEStatus>> getAllGames() {

        Collection<Game> vacationRentals = hangmanService.findAllGames();

        Stream<Game> stream = vacationRentals.stream();
        Stream<FEStatus> newStream = stream.map(s -> s.getFeStatus());
        Collection<FEStatus> output = newStream.collect(Collectors.toList());

        return new ResponseEntity<>(output, HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(path = "/game/{key}", method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FEStatus> getGame(@PathVariable String key) {

        Game game = hangmanService.findGameById(key);

        if (game == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(game.getFeStatus(),HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(path = "/game", method = RequestMethod.POST)
    public ResponseEntity<FEStatus> createGame() {

        String hiddenWord = wordGenerator.getNextWord();
        String gameId = UUID.randomUUID().toString();
        ArrayList<Character> output = new ArrayList<>(hiddenWord.length());
        Set<Character> lettersUsed = new HashSet<>();
        for (int i = 0; i < hiddenWord.length(); i++)
            output.add(i, '-');
        FEStatus feStatus = new FEStatus(gameId, 5, lettersUsed, output, null);
        Game game = new Game(hiddenWord, feStatus);
        Game existedGame = hangmanService.findGameById(gameId);

        if (existedGame == null) {
            hangmanService.createGame(game);
            return new ResponseEntity<>(game.getFeStatus(), HttpStatus.OK);
        }
        return new ResponseEntity<>(existedGame.getFeStatus(), HttpStatus.CONFLICT);
    }

    @CrossOrigin
    @RequestMapping(path = "/game/{key}", method = RequestMethod.PUT)
    public ResponseEntity<FEStatus> verifyLetter(@PathVariable String key,@RequestBody Map <String, String> map) {
        String letter = map.get("letter");
        Game game = hangmanService.findGameById(key);

        if ((game == null) || (key == null)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        FEStatus feStatus = game.getFeStatus();

        if(!isNumber(letter) && (letter != null) && (!letter.isEmpty())) {

            Set<Character> usedLetters = feStatus.getLettersUsed();

            int numLives = feStatus.getIntNumLives();

            if (numLives <= 0) {
                feStatus.setMessage("You do not have more lives to continue playing this game");
            } else {

                if (game.getHiddenWord().contains(letter)) {
                    feStatus.setMessage("Hidden word updated including the letter");
                    updateHiddenWord(game, letter);

                } else {
                    if (feStatus.getLettersUsed().contains(letter.charAt(0))) {
                        feStatus.setMessage("You have already used that letter");
                    } else {
                        numLives = numLives - 1;
                        feStatus.setIntNumLives(numLives);
                        feStatus.setMessage("You have lost a life but you can continue playing");
                        usedLetters.add(letter.charAt(0));
                    }

                    if (numLives == 0) {
                        feStatus.setMessage("Game Over: You have lost all your lives. Hidden word was: " + game.getHiddenWord());
                    }
                }
                hangmanService.updateGame(key, game);
            }
        }
        else{
            feStatus.setMessage("The character you have entered is not a letter");
        }
        return new ResponseEntity<>(feStatus,HttpStatus.OK);

    }

    private void verifyWord(ArrayList<Character> hiddenWord,Game game){

        if (hiddenWord.equals(game.getFeStatus().getOutput())){
            game.getFeStatus().setMessage("Yippe!! You have found the hidden word");
            game.getFeStatus().setIntNumLives(0);
        }
    }

    private void updateHiddenWord(Game game, String letter){
        ArrayList<Character> arrayHiddenWord = convertHiddenWord(game.getHiddenWord());
        int [] intList = IntStream.range(0,game.getHiddenWord().length()).
                filter(i -> arrayHiddenWord.get(i).equals(letter.charAt(0))).
                toArray();
        for (int i=0;i<intList.length;i++)
            game.getFeStatus().getOutput().set(intList[i],letter.charAt(0));
        verifyWord(arrayHiddenWord,game);
    }

    private ArrayList<Character> convertHiddenWord(String hiddenWord){
        ArrayList<Character> array = new ArrayList<>();
        for (char c : hiddenWord.toCharArray()) {
            array.add(c);
        }

        return array;
    }

}
