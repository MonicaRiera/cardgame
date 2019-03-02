package tech.bts.cardgame;

import tech.bts.cardgame.model.Deck;
import tech.bts.cardgame.model.Game;
import tech.bts.cardgame.model.Player;
import tech.bts.cardgame.repository.DataSourceUtil;
import tech.bts.cardgame.repository.GameRepository;
import tech.bts.cardgame.repository.GameRepositoryJdbc;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class JdbcExample {

    public static void main(String[] args) throws SQLException {

        GameRepositoryJdbc gameRepository = new GameRepositoryJdbc();
        System.out.println(gameRepository.getAll());
        System.out.println(gameRepository.gameById(1));

        /**Exercise 1
         *
         * DataSource dataSource = DataSourceUtil.getDataSourceInPath();
        Connection connection = dataSource.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM games");

        List<Game> games = new ArrayList<>();

        while (resultSet.next()) {

            int id = resultSet.getInt("id");
            String state = resultSet.getString("state");
            String players = resultSet.getString("players");

            System.out.println(id + ", " + state + ", " + players);

            Deck deck = new Deck();
            Game game = new Game(deck);
            game.setId(id);

            String[] arr = players.split(",");
            for ( String player : arr) {
                game.join(player);
            }
            game.setState(Game.State.valueOf(state));
            games.add(game);
        }

        resultSet.close();
        statement.close();
        connection.close();*/
    }
}
