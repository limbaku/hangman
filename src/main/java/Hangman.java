package main.java;

import java.util.ArrayList;
import java.util.Scanner;

public class Hangman {

    public static void main (String args[]){

        Player player = new Player(123456);
        Round round = new Round(12345,player, 3);
        int numRetries = round.getNumRetries();
        boolean finishRound=false;

        System.out.println("Welcome to hangman");

        ArrayList<Character> hiddenWord = new ArrayList<Character>(6);
        hiddenWord.add(0,'s');
        hiddenWord.add(1,'a');
        hiddenWord.add(2,'n');
        hiddenWord.add(3,'d');
        hiddenWord.add(4,'r');
        hiddenWord.add(5,'e');

        ArrayList<Character> output = new ArrayList<Character>(6);

        for (int i=0;i<hiddenWord.size();i++){
            output.add(i, '_');
        }

        System.out.println();

        while ((numRetries > 0) && (!finishRound)){
            System.out.println("Guess a letter: ");
            Scanner scanner = new Scanner(System.in);
            Character guessedLetter = scanner.findInLine(".").charAt(0);

            if (hiddenWord.contains(guessedLetter)){
                int position = hiddenWord.indexOf(guessedLetter);
                output.remove(position);
                output.add(position,guessedLetter);
            }
            else{
                numRetries = numRetries -1;
            }
            for (int j=0;j<output.size();j++){
                System.out.print(output.get(j));
            }

            if (hiddenWord.equals(output)){
                finishRound=true;
            }
        }

        System.out.println();
        if (finishRound){
            System.out.println("Congrats, you have found the hidden word");
        }
        else{
            System.out.println("I will be next time");
        }
    }
}
