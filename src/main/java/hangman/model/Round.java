package hangman.model;

public class Round {

    private final FEStatus feStatus;
    private final String hiddenWord;


    public Round(String hiddenWord, FEStatus feStatus) {
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
