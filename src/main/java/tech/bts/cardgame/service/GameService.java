package tech.bts.cardgame.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import tech.bts.cardgame.model.Card;
import tech.bts.cardgame.model.Deck;
import tech.bts.cardgame.model.Game;
import tech.bts.cardgame.model.GameUser;
import tech.bts.cardgame.repository.GameRepository;

import java.util.List;

@Service
public class GameService {

    private GameRepository gameRepository;

    @Autowired
    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public Game createGame(){
        Deck deck = new Deck();
        deck.generate();
        deck.shuffle();
        Game game = new Game(deck);
        gameRepository.createGame(game);
        return game;
    }

    public void joinGame(GameUser gameUser) {
        Game game = gameRepository.gameById(gameUser.getGameId());
        game.join(gameUser.getUsername());
    }

    public Card pickCard(GameUser gameUser) {
        Game game = gameRepository.gameById(gameUser.getGameId());
        return game.pickCard(gameUser.getUsername());
    }

    public List<Game> getAllGames() {
        return gameRepository.getAll();
    }

    public Game getGameById(long gameId) {
        return gameRepository.gameById(gameId);
    }
}
