package main.java;

import java.util.ArrayList;

public class Round {

    private ArrayList<Character> hiddenWOrd;
    private FEStatus feStatus;

    public Round(FEStatus feStatus) {
        this.feStatus = feStatus;
    }

    public ArrayList<Character> getHiddenWOrd() {
        return hiddenWOrd;
    }

    public void setHiddenWOrd(ArrayList<Character> hiddenWOrd) {
        this.hiddenWOrd = hiddenWOrd;
    }

    public FEStatus getFeStatus() {
        return feStatus;
    }

    public void setFeStatus(FEStatus feStatus) {
        this.feStatus = feStatus;
    }

    public void letterBelongToHiddenWord(Character letter, ArrayList<Character> hiddenWOrd){

    }

    public void verifyWord(String word, ArrayList<Character> hiddenWOrd){

    }
}
