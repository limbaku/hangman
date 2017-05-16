package main.java;


import java.util.ArrayList;

public class Round {
    private int roundId;
    private Player currentPlayer;
    private int numRetries;

    public void setHiddenWord(ArrayList<Character> hiddenWord) {
        this.hiddenWord = hiddenWord;
    }

    private ArrayList<Character> hiddenWord=new ArrayList<Character>();

    public ArrayList<Character> getHiddenWord() {
        return hiddenWord;
    }

    public Round(int roundId, Player currentPlayer, int numRetries) {
        this.roundId = roundId;
        this.currentPlayer = currentPlayer;
        this.numRetries = numRetries;
    }

    public int getRoundId() {
        return roundId;
    }

    public void setRoundId(int roundId) {
        this.roundId = roundId;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public int getNumRetries() {
        return numRetries;
    }

    public void setNumRetries(int numRetires) {
        this.numRetries = numRetires;
    }




}
