package tech.bts.cardgame.controller;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tech.bts.cardgame.model.Game;
import tech.bts.cardgame.model.GameUser;
import tech.bts.cardgame.service.GameService;
import tech.bts.cardgame.util.Util;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
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

    @RequestMapping(method = GET)
    public String getAllGames(){
        String result = "<h1>List of games</h1>" +
                "<a href=\"/games/create\">Create game</a><ul>";
        List<Game> games = gameService.getAllGames();
        for (Game game : games) {
            result += "<li><a href=\"/games/" + game.getId() + "\">Game ID: " + game.getId() + "</a> " +
                    "State: " + game.getState() + " " +
                    "Players: " + game.getPlayersName() + "</li>";
        }
        result += "</ul>";
        return result;
    }

    @RequestMapping(method = GET, path = "/{gameId}")
    public String getGameById(@PathVariable long gameId) throws IOException {
        Game game = gameService.getGameById(gameId);

        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix(".hbs");
        Handlebars handlebars = new Handlebars(loader);

        Template template = handlebars.compile("game-detail");

        Map<String, Object> values = new HashMap<>();
        values.put("game", game);
        values.put("gameIsOpen", game.getState() == OPEN);
        return template.apply(values);
    }


    @RequestMapping(method = GET, path = "/create")
    public void createGame(HttpServletResponse response) throws IOException {
        gameService.createGame();
        response.sendRedirect("/games");
    }

    @RequestMapping(method = GET, path = "/{gameId}/join")
    public void joinGame(HttpServletResponse response, @PathVariable long gameId) throws IOException {
        gameService.joinGame(new GameUser(gameId, "Monica"));
        response.sendRedirect("/games/" + gameId);
    }
}

