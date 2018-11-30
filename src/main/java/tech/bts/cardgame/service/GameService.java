package tech.bts.cardgame.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.bts.cardgame.model.Deck;
import tech.bts.cardgame.model.Game;
import tech.bts.cardgame.model.JoinGame;
import tech.bts.cardgame.repository.GameRepository;

@Service
public class GameService {

    private GameRepository gameRepository;

    @Autowired
    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public void createGame(){
        Deck deck = new Deck();
        deck.generate();
        deck.shuffle();
        Game game = new Game(deck);
        gameRepository.createGame(game);
    }

    public void joinGame(JoinGame joinGame) {
        Game game = gameRepository.gameById(joinGame.getGameId());
        game.join(joinGame.getUsername());
    }

}
