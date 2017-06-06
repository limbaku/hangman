package hangman.model;

import java.util.ArrayList;
import java.util.Set;

public class FEStatus {

    private final String gameId;
    private int intNumLives;
    private Set<Character> lettersUsed;
    private ArrayList<Character> output;
    private String message;

    public FEStatus(String gameId, int intNumLives, Set<Character> lettersUsed, ArrayList<Character> output, String message) {
        this.gameId = gameId;
        this.intNumLives = intNumLives;
        this.lettersUsed = lettersUsed;
        this.output = output;
        this.message = message;
    }

    public String getGameId() {
        return gameId;
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

    public void setOutput(ArrayList<Character> output) {this.output = output;}

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
