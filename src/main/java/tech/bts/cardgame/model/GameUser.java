package tech.bts.cardgame.model;

public class GameUser {

    private long gameId;
    private String username;

    public GameUser(){}

    public long getGameId() {
        return gameId;
    }

    public String getUsername() {
        return username;
    }

    public void setGameId(long gameId) {
        this.gameId = gameId;
    }
}
