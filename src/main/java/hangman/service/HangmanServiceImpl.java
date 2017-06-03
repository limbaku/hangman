package hangman.service;

import hangman.model.Round;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class HangmanServiceImpl implements HangmanService {

    private ConcurrentHashMap<String,Round> concurrentHashMap= new ConcurrentHashMap<>();

    @Override
    public Collection<Round> findAllRounds() {

        return concurrentHashMap.values();
    }

    @Override
    public Round findRoundById(String id) {
        return concurrentHashMap.get(id);
    }

    @Override
    public void createRound(Round round) {
        concurrentHashMap.put(round.getFeStatus().getRoundId(),round);
    }

    @Override
    public void updateRound(String id, Round round) {
        concurrentHashMap.put(id,round);
    }

}
