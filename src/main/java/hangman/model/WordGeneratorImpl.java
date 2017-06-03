package hangman.model;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class WordGeneratorImpl implements WordGenerator{

    private static final String[] dictionary = {
            "hello",
            "camera",
            "raspberry",
            "dress",
            "transformers",
            "visitor",
            "fly",
            "alternate",
            "house",
            "dress",
            "transformation"};

    private static Random randomizer = new Random();

    public String getNextWord() {
        return dictionary[randomizer.nextInt(dictionary.length)];
    }
}

