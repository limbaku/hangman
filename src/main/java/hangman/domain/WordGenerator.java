package hangman.domain;

import java.util.Random;

public class WordGenerator {

    private static final String[] dictionary = {
            "hello",
            "camera",
            "rasperry",
            "dress",
            "transformers",
            "vueling",
            "fly",
            "alternate",
            "house",
            "dress",
            "transformers",};

    private static Random randomizer = new Random();

    public static String nextWord() {
        return dictionary[randomizer.nextInt(dictionary.length)];
    }
}

