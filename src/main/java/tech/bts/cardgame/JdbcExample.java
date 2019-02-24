package tech.bts.cardgame;

import tech.bts.cardgame.repository.DataSourceUtil;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JdbcExample {

    public static void main(String[] args) throws SQLException {

        DataSource dataSource = DataSourceUtil.getDataSourceInPath();
        Connection connection = dataSource.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM games");

        while (resultSet.next()) {

            int id = resultSet.getInt("id");
            String state = resultSet.getString("state");
            String players = resultSet.getString("players");

            System.out.println(id + ", " + state + ", " + players);
        }

        resultSet.close();
        statement.close();
        connection.close();
    }
}
