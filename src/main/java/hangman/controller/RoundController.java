package hangman.controller;

import hangman.domain.FEStatus;
import hangman.domain.Round;
import hangman.domain.WordGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@RestController
public class RoundController {

    @Autowired
    ConcurrentHashMap<String,Round> concurrentHashMap;

    @RequestMapping(path = "/round/", method = RequestMethod.GET)
    public ResponseEntity<Collection<Round>> listAllRounds() {

        Collection<Round> rounds = concurrentHashMap.values();

        if (rounds.isEmpty()) {
            return new ResponseEntity<Collection<Round>>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<Collection<Round>>(rounds, HttpStatus.OK);
    }

    @RequestMapping(path = "/round/{key}", method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FEStatus> getStatus(@PathVariable String key) {

        Round round = concurrentHashMap.get(key);

        if (round == null) {
            return new ResponseEntity<FEStatus>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<FEStatus>(round.getFeStatus(),HttpStatus.OK);
    }

    @RequestMapping(path = "/round/", method = RequestMethod.POST)
    public ResponseEntity<FEStatus> createRound() {

        WordGenerator wordGenerator = new WordGenerator();
        String hiddenWord = wordGenerator.nextWord();
        int roundId = new Random().nextInt(50);
        ArrayList<Character> output = new ArrayList<>(hiddenWord.length());
        for(int i=0;i<hiddenWord.length();i++)
            output.add(i,'-');
        FEStatus feStatus = new FEStatus(roundId,3,null,null,output,null);
        Round round = new Round(hiddenWord,feStatus);

        concurrentHashMap.put(Integer.toString(roundId),round);

        return new ResponseEntity<FEStatus>(feStatus,HttpStatus.OK);
    }

    @RequestMapping(path = "/round/{key}", method = RequestMethod.PUT)
    public ResponseEntity<FEStatus> updateRound(@PathVariable String key,@RequestBody String word) {
        Round round = concurrentHashMap.get(key);
        FEStatus feStatus = round.getFeStatus();
        ArrayList<String> messages= new ArrayList<String>();
        int numLifes = feStatus.getIntNumLifes();

        if (round == null) {
            return new ResponseEntity<FEStatus>(HttpStatus.NOT_FOUND);
        }

        if (round.getHiddenWord().equals(word)){
            messages.add("You found the hidden word");
            feStatus.setMessageCodes(messages);
        }

        else{
            numLifes = numLifes-1;
            feStatus.setIntNumLifes(numLifes--);
        }

        if(numLifes < 0){
            messages.add("Game Over: You have lost all your lifes");
            feStatus.setMessageCodes(messages);
        }

        return new ResponseEntity<FEStatus>(feStatus,HttpStatus.OK);

    }


}
