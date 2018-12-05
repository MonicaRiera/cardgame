package tech.bts.cardgame.model;

public class GameUser {

    private long gameId;
    private String username;

    public GameUser(){}

    public GameUser(long gameId, String username) {
        this.gameId = gameId;
        this.username = username;
    }

    public long getGameId() {
        return gameId;
    }

    public String getUsername() {
        return username;
    }

    public void setGameId(long gameId) {
        this.gameId = gameId;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
