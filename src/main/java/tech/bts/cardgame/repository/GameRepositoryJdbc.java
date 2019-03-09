package tech.bts.cardgame.repository;

import org.springframework.stereotype.Repository;
import tech.bts.cardgame.model.Deck;
import tech.bts.cardgame.model.Game;

import javax.sql.DataSource;
import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class GameRepositoryJdbc {

    private Map<Long, Game> gameMap;
    private long nextId;
    private DataSource dataSource;


    public GameRepositoryJdbc() throws SQLException{
        gameMap = new HashMap<>();
        nextId = 0;
        dataSource = DataSourceUtil.getDataSourceInPath();

    }

    public void createGame(Game game) {

        try {

            Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate("INSERT INTO GAMES (state, players) VALUES (" + game.getState() + ", NULL)");
        } catch (Exception e) {
            throw new RuntimeException("Error creating game", e);
        }
    }


    public Game gameById(long id) {
        try {

            Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM games WHERE id =" + id);
            Game game = null;

            if (resultSet.next()) {
                game = getGame(resultSet);
            }

            resultSet.close();
            statement.close();
            connection.close();

            return game;
        } catch (Exception e) {
            throw new RuntimeException("Error getting the game", e);
        }
    }

    public List<Game> getAll() {

        try {

            Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM games");
            //executeUpdate(instert into games...) to create game
            List<Game> games = new ArrayList<>();
            while (resultSet.next()) {
                Game game = getGame(resultSet);
                games.add(game);
            }

            resultSet.close();
            statement.close();
            connection.close();

            return games;

        } catch (Exception e) {
            throw new RuntimeException("Error getting the games", e);
        }


    }

    private Game getGame(ResultSet resultSet) throws SQLException {

        int id = resultSet.getInt("id");
        String state = resultSet.getString("state");
        String players = resultSet.getString("players");

        Deck deck = new Deck();
        Game game = new Game(deck);
        game.setId(id);

        String[] arr = players.split(",");
        for (String player : arr) {
            game.join(player);
        }
        game.setState(Game.State.valueOf(state));
        return game;
    }
}
