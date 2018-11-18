package tech.bts.cardgame;

import java.util.List;

public class Hand {

    private List<Card> cards;

    public Hand(List<Card> cards) {

        this.cards = cards;
    }

    public void add(Card card) {
        this.cards.add(card);
    }

    public Card calculate() {

        int magic = 0;
        int strength = 0;
        int intelligence = 0;

        for (Card card : cards) {
            magic += card.getMagic();
            strength += card.getStrength();
            intelligence += card.getIntelligence();
        }

        return new Card(magic, strength, intelligence);
    }

    @Override
    public String toString() {
        String string ="";
        for (Card card : cards) {
            string += "M: " + card.getMagic() + ", S: " + card.getStrength() + ", I: " + card.getIntelligence() + "\n";
        }

        return string;
    }

    public List<Card> getCards() {
        return cards;
    }

    public int size() {
        return this.cards.size();
    }
}
