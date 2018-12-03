package tech.bts.cardgame.repository;

import org.springframework.stereotype.Repository;
import tech.bts.cardgame.model.Game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class GameRepository {

    private Map<Long, Game> gameMap;
    private long nextId;

    public GameRepository() {
        gameMap = new HashMap<>();
        nextId = 0;
    }

    public void createGame(Game game) {
        game.setId(nextId);
        gameMap.put(game.getId(), game);
        nextId++;
    }

    public Game gameById(long id) {
        return gameMap.get(id);
    }

    public List<Game> getAll() {

        return new ArrayList<>(gameMap.values());
    }
}
