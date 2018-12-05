package tech.bts.cardgame.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tech.bts.cardgame.model.Deck;
import tech.bts.cardgame.model.Game;
import tech.bts.cardgame.service.GameService;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
@RequestMapping(path = "/games")
public class GameWebController {

    private GameService gameService;

    @Autowired
    public GameWebController(GameService gameService) {
        this.gameService = gameService;
    }

    @RequestMapping(method = GET)
    public String getAllGames(){
        return buildGameList();
    }

    @RequestMapping(method = GET, path = "/{gameId}")
    public String getGameById(@PathVariable long gameId) {
        Game game = gameService.getGameById(gameId);
        return "<h1>Game " + game.getId() + "</h1> <a href=\"/games\" <p>Go back to the games</p></a><p>State: " + game.getState() + "</p><p>Players: " + game.getPlayersName() + "</p>";
    }

    @RequestMapping(method = GET, path = "/create")
    public String createGame() {
        gameService.createGame();
        return buildGameList();
    }

    private String buildGameList() {
        String result = "<h1>List of games</h1><p><a href=\"/games/create\">Create game</a></p><ul>";
        List<Game> games = gameService.getAllGames();
        for (Game game : games) {
            //String players = joinStrings(game.getPlayersName(), " VS ");
            result += "<a href=\"/games/" + game.getId() + "\" target=\"_blank\"><li>Game ID: " + game.getId() + "</a> State: " + game.getState() + " Players: " + game.getPlayersName() + "</li>";
        }
        result += "</ul>";
        return result;
    }
}

