package tech.bts.cardgame.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tech.bts.cardgame.model.Game;
import tech.bts.cardgame.model.GameUser;
import tech.bts.cardgame.service.GameService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
        String result = "<h1>Game " + game.getId() + "</h1> <a href=\"/games\" <p>Go back to the games</p></a><p>State: " + game.getState() + "</p><p>Players: " + game.getPlayersName() + "</p>";
        if (game.getState() == Game.State.OPEN) {
            result += "<p><a href=\"/games/" + game.getId() + "/join\"> Join this game</p>";
        }
        return result;
    }


    @RequestMapping(method = GET, path = "/create")
    public void createGame(HttpServletResponse response) throws IOException {
        gameService.createGame();
        response.sendRedirect("/games");
    }

    @RequestMapping(method = GET, path = "/{gameId}/join")
    public void joinGame(HttpServletResponse response, @PathVariable long gameId) throws IOException {
        GameUser gameUser = new GameUser(gameId, "Monica");
        gameService.joinGame(gameUser);
        response.sendRedirect("/games/" + gameId);
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

