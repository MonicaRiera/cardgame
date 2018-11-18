package tech.bts.cardgame;

public class Player {

    private String name;
    private Hand hand;
    private int points;

    public Player(String name) {
        this.name = name;
        this.points = 0;
    }

    public void setHand(Hand hand) {
        this.hand = hand;
    }

    public Hand getHand() {
        return hand;
    }

    public String getName() {
        return name;
    }

    public void setPoints(int points) {
        this.points += points;
    }

    public int getPoints() {
        return points;
    }
}
