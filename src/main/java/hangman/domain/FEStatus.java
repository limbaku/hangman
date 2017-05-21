package hangman.domain;

import java.util.ArrayList;

public class FEStatus {

    private int roundId;
    private int intNumLifes;
    private ArrayList<Character> lettersUsed;
    private ArrayList<String> wordsUsed;
    private ArrayList<Character> output;
    private ArrayList<String> messageCodes;

    public FEStatus(int roundId, int intNumLifes, ArrayList<Character> lettersUsed, ArrayList<String> wordsUsed,
                    ArrayList<Character> output, ArrayList<String> messageCodes) {
        this.roundId = roundId;
        this.intNumLifes = intNumLifes;
        this.lettersUsed = lettersUsed;
        this.wordsUsed = wordsUsed;
        this.output = output;
        this.messageCodes = messageCodes;
    }

    public int getRoundId() {
        return roundId;
    }

    public void setRoundId(int roundId) {
        this.roundId = roundId;
    }

    public int getIntNumLifes() {
        return intNumLifes;
    }

    public void setIntNumLifes(int intNumLifes) {
        this.intNumLifes = intNumLifes;
    }

    public ArrayList<Character> getLettersUsed() {
        return lettersUsed;
    }

    public void setLettersUsed(ArrayList<Character> lettersUsed) {
        this.lettersUsed = lettersUsed;
    }

    public ArrayList<String> getWordsUsed() {
        return wordsUsed;
    }

    public void setWordsUsed(ArrayList<String> wordsUsed) {
        this.wordsUsed = wordsUsed;
    }

    public ArrayList<Character> getOutput() {
        return output;
    }

    public void setOutput(ArrayList<Character> output) {
        this.output = output;
    }

    public ArrayList<String> getMessageCodes() {
        return messageCodes;
    }

    public void setMessageCodes(ArrayList<String> messageCodes) {
        this.messageCodes = messageCodes;
    }

}
