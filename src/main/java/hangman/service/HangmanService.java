package hangman.service;

import hangman.model.Game;
import java.util.Collection;

public interface HangmanService {
    Collection<Game> findAllGames();
    Game findGameById(String id);
    void createGame (Game game);
    void updateGame (String id,Game game);
}
