package tech.bts.cardgame.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tech.bts.cardgame.model.Game;
import tech.bts.cardgame.model.GameUser;
import tech.bts.cardgame.service.GameService;
import tech.bts.cardgame.util.HandlebarsUtil;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.*;
import static tech.bts.cardgame.model.Game.State.*;

@RestController
@RequestMapping(path = "/games")
public class GameWebController {

    private GameService gameService;

    @Autowired
    public GameWebController(GameService gameService) {
        this.gameService = gameService;
    }

    /** Returns the games as HTML */
    @RequestMapping(method = GET)
    public String getAllGames() throws IOException {
        Map<String, Object> values = new HashMap<>();
        values.put("games", gameService.getAllGames());
        return HandlebarsUtil.getTemplate("game-list", values);
    }

    /** Returns the game details with a given game id */
    @RequestMapping(method = GET, path = "/{gameId}")
    public String getGameById(@PathVariable long gameId) throws IOException {
        Game game = gameService.getGameById(gameId);
        Map<String, Object> values = new HashMap<>();
        values.put("game", game);
        values.put("gameIsOpen", game.getState() == OPEN);
        return HandlebarsUtil.getTemplate("game-detail", values);
    }


    @RequestMapping(method = GET, path = "/create")
    public void createGame(HttpServletResponse response) throws IOException {
        gameService.createGame();
        response.sendRedirect("/games");
    }

    @RequestMapping(method = POST, path = "/join")
    public void joinGame(HttpServletResponse response, GameUser gameUser) throws IOException {
        gameService.joinGame(gameUser);
        response.sendRedirect("/games/" + gameUser.getGameId());
    }

}

