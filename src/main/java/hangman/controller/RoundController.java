package hangman.controller;

import hangman.model.FEStatus;
import hangman.model.Round;
import hangman.model.WordGenerator;
import hangman.service.HangmanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@RestController
public class RoundController {

    @Autowired
    HangmanService hangmanService;

    @Autowired
    WordGenerator wordGenerator;

    @RequestMapping(path = "/round/", method = RequestMethod.GET)
    public ResponseEntity<Collection<FEStatus>> listAllRounds() {

        Collection<Round> vacationRentals = hangmanService.findAllRounds();

        Stream<Round> stream = vacationRentals.stream();
        Stream<FEStatus> newStream = stream.map(s -> s.getFeStatus());
        Collection<FEStatus> output = newStream.collect(Collectors.toList());

        if (output.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(output, HttpStatus.OK);
    }

    @RequestMapping(path = "/round/{key}", method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FEStatus> getStatus(@PathVariable String key) {

        Round round = hangmanService.findRoundById(key);

        if (round == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(round.getFeStatus(),HttpStatus.OK);
    }

    @RequestMapping(path = "/round/createRound", method = RequestMethod.POST)
    public ResponseEntity<FEStatus> createRound() {

        String hiddenWord = wordGenerator.getNextWord();
        String roundId = UUID.randomUUID().toString();
        ArrayList<Character> output = new ArrayList<>(hiddenWord.length());
        Set<Character> lettersUsed = new HashSet<>();
        Set<String> wordsUsed = new HashSet<>();
        for(int i=0;i<hiddenWord.length();i++)
            output.add(i,'-');
        FEStatus feStatus = new FEStatus(roundId,3,lettersUsed,wordsUsed,output,null);
        Round round = new Round(hiddenWord,feStatus);

        hangmanService.createRound(round);

        return new ResponseEntity<>(round.getFeStatus(),HttpStatus.OK);
    }

    @RequestMapping(path = "/round/verifyWord/{key}", method = RequestMethod.PUT)
    public ResponseEntity<FEStatus> verifyWord(@PathVariable String key,@RequestBody Map <String, String> map) {
        String word = map.get("word");
        Round round = hangmanService.findRoundById(key);

        if (round == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        FEStatus feStatus = round.getFeStatus();

        Set<String> usedWords= feStatus.getWordsUsed();

        int numLives = feStatus.getIntNumLives();

        if (numLives <=0){
            feStatus.setMessage("You do not have more lives to continue playing this round");
        }
        else {

            if (round.getHiddenWord().equals(word)) {
                feStatus.setMessage("You found the hidden word");
            } else {
                numLives = numLives - 1;
                feStatus.setIntNumLives(numLives);

                if (feStatus.getWordsUsed().contains(word)) {
                    feStatus.setMessage("You already used that word");
                } else {
                    feStatus.setMessage("You have lost a life but you can continue playing");
                    usedWords.add(word);
                }
            }

            if (numLives == 0) {
                feStatus.setMessage("Game Over: You have lost all your lives");
            }

            hangmanService.updateRound(key, round);
        }
        return new ResponseEntity<>(feStatus,HttpStatus.OK);

    }

    @RequestMapping(path = "/round/verifyLetter/{key}", method = RequestMethod.PUT)
    public ResponseEntity<FEStatus> verifyLetter(@PathVariable String key,@RequestBody Map <String, String> map) {
        String letter = map.get("letter");
        Round round = hangmanService.findRoundById(key);

        if (round == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        FEStatus feStatus = round.getFeStatus();

        Set<Character> usedLetters= feStatus.getLettersUsed();

        int numLives = feStatus.getIntNumLives();

        if (numLives <=0){
            feStatus.setMessage("You do not have more lives to continue playing this round");
        }
        else {

            if (round.getHiddenWord().contains(letter)) {
                feStatus.setMessage("Updating the hidden word with the letter");
                updateOutput(round,letter);
                //feStatus.setOutput(list);

            } else {
                numLives = numLives - 1;
                feStatus.setIntNumLives(numLives);

                if (feStatus.getLettersUsed().contains(letter.charAt(0))) {
                    feStatus.setMessage("You already used that letter");
                } else {
                    feStatus.setMessage("You have lost a life but you can continue playing");
                    usedLetters.add(letter.charAt(0));
                }
            }

            if (numLives == 0) {
                feStatus.setMessage("Game Over: You have lost all your lives");
            }

            hangmanService.updateRound(key, round);
        }
        return new ResponseEntity<>(feStatus,HttpStatus.OK);

    }

    private void updateOutput(Round round, String letter){
        ArrayList<Character> arrayHiddenWord = convertHiddenWord(round.getHiddenWord());
        int [] intList = IntStream.range(0,round.getHiddenWord().length()).
                filter(i -> arrayHiddenWord.get(i).equals(letter.charAt(0))).
                toArray();
        for (int i=0;i<intList.length;i++)
            round.getFeStatus().getOutput().set(intList[i],letter.charAt(0));

    }

    private ArrayList<Character> convertHiddenWord(String hiddenWord){
        ArrayList<Character> array = new ArrayList<>();
        for (char c : hiddenWord.toCharArray()) {
            array.add(c);
        }

        return array;
    }

}
