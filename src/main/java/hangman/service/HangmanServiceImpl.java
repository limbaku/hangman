package hangman.service;

import hangman.model.Game;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class HangmanServiceImpl implements HangmanService {

    private ConcurrentHashMap<String,Game> concurrentHashMap= new ConcurrentHashMap<>();

    @Override
    public Collection<Game> findAllGames() {

        return concurrentHashMap.values();
    }

    @Override
    public Game findGameById(String id) {
        return concurrentHashMap.get(id);
    }

    @Override
    public void createGame(Game game) {
        concurrentHashMap.put(game.getFeStatus().getGameId(),game);
    }

    @Override
    public void updateGame(String id, Game game) {
        concurrentHashMap.put(id,game);
    }

}
