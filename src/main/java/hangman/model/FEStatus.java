package hangman.model;

import java.util.ArrayList;
import java.util.Set;

public class FEStatus {

    private final String roundId;
    private int intNumLives;
    private Set<Character> lettersUsed;
    private final Set<String> wordsUsed;
    private final ArrayList<Character> output;
    private String message;

    public FEStatus(String roundId, int intNumLives, Set<Character> lettersUsed, Set<String> wordsUsed,
                    ArrayList<Character> output, String message) {
        this.roundId = roundId;
        this.intNumLives = intNumLives;
        this.lettersUsed = lettersUsed;
        this.wordsUsed = wordsUsed;
        this.output = output;
        this.message = message;
    }

    public String getRoundId() {
        return roundId;
    }

    public int getIntNumLives() {
        return intNumLives;
    }

    public void setIntNumLives(int intNumLives) {
        this.intNumLives = intNumLives;
    }

    public void setLettersUsed(Set<Character> lettersUsed) {
        this.lettersUsed = lettersUsed;
    }

    public Set<Character> getLettersUsed() {
        return lettersUsed;
    }

    public Set<String> getWordsUsed() {
        return wordsUsed;
    }

    public ArrayList<Character> getOutput() {
        return output;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
