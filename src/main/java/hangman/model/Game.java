package hangman.model;

public class Game {

    private final FEStatus feStatus;
    private final String hiddenWord;


    public Game(String hiddenWord, FEStatus feStatus) {
        this.hiddenWord = hiddenWord;
        this.feStatus = feStatus;
    }

    public String getHiddenWord() {
        return hiddenWord;
    }

    public FEStatus getFeStatus() {
        return feStatus;
    }

}
