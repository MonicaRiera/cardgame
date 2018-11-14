package tech.bts.cardgame;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Deck {

    private List<Card> cards;

    public Deck() {
        this.cards = new ArrayList<Card>();
    }

    public void add(Card card) {
        this.cards.add(card);
    }

    public void generate() {
        for (int m = 1; m <= 8; m++) {
            for (int s = 1; s <= 9-m; s++) {
                int i = 10 - (m + s);
                this.add(new Card(m, s, i));
            }
        }
    }

    public void shuffle(int times){
        Random random = new Random();

        for (int t = 0; t < times; t++) {

            for (int i = 0; i < cards.size(); i++) {

                int randomIndex = random.nextInt(this.cards.size());

                Card card1 = this.cards.get(i);
                Card card2 = this.cards.get(randomIndex);

                this.cards.set(randomIndex, card1);
                this.cards.set(i, card2);
            }
        }
    }

    public Card pickCard() {
        return this.cards.remove(cards.size()-1);
    }
}
