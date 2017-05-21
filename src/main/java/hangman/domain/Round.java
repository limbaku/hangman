package hangman.domain;

import java.util.ArrayList;

public class Round {

    private String hiddenWord;
    private FEStatus feStatus;

    public Round(String hiddenWord, FEStatus feStatus) {
        this.hiddenWord = hiddenWord;
        this.feStatus = feStatus;
    }

    public Round(FEStatus feStatus) {
        this.feStatus = feStatus;
    }

    public String getHiddenWord() {
        return hiddenWord;
    }

    public void setHiddenWOrd(String hiddenWord) {
        this.hiddenWord = hiddenWord;
    }

    public FEStatus getFeStatus() {
        return feStatus;
    }

    public void setFeStatus(FEStatus feStatus) {
        this.feStatus = feStatus;
    }

    public void letterBelongToHiddenWord(Character letter, ArrayList<Character> hiddenWOrd){

    }

    public void verifyWord(String word, String hiddenWOrd){

    }
}
