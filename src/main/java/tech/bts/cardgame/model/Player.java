package tech.bts.cardgame.model;

public class Player {

    private String name;
    private Hand hand;
    private int points;
    private Card pickedCard;
    private int discardedCards;


    public Player(String name) {
        this.name = name;
        this.points = 0;
        this.discardedCards = 0;
        this.pickedCard = null;
        this.hand = null;
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

    public Card getPickedCard() {
        return pickedCard;
    }

    public int getDiscardedCards() {
        return discardedCards;
    }

    public void setPickedCard(Card pickedCard) {
        this.pickedCard = pickedCard;
    }

    public void setDiscardedCards(int discardedCards) {
        this.discardedCards = discardedCards;
    }

}
