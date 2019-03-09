package tech.bts.cardgame;

import tech.bts.cardgame.repository.GameRepositoryJdbc;

import java.sql.SQLException;

public class JdbcExample {

    public static void main(String[] args) throws SQLException {

        GameRepositoryJdbc gameRepository = new GameRepositoryJdbc();
        System.out.println(gameRepository.getAll());
        System.out.println(gameRepository.gameById(1));


    }
}
