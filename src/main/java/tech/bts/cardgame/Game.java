package tech.bts.cardgame;

public class Game {

    private final Player player1;
    private final Player player2;
    private final Deck deck;
    private final static  int INITIAL_HAND_SIZE = 5;
    private final static  int FINAL_HAND_SIZE = 3;
    private final static  int NUM_CARDS_TO_DISCARD = INITIAL_HAND_SIZE - FINAL_HAND_SIZE;

    public Game(Player player1, Player player2, Deck deck) {

        this.player1 = player1;
        this.player2 = player2;
        this.deck = deck;
    }

    public void play() {

        Hand hand1 = deck.deal(INITIAL_HAND_SIZE);
        Hand hand2 = deck.deal(INITIAL_HAND_SIZE);

        //TODO: develop the discard method later
        //player1.discard(NUM_CARDS_TO_DISCARD);
        //player2.discard(NUM_CARDS_TO_DISCARD);

        int result = compare(hand1, hand2);

        System.out.println("Hand 1: " + hand1);
        System.out.println("Hand 2: " + hand2);


        if (result > 0){
            System.out.println("Player 1: "+player1.getName()+" wins!");
        } else if (result < 0) {
            System.out.println("Player 2: "+player2.getName()+" wins!");
        } else {
            System.out.println("No one wins...");
        }




    }

    private int compare(Hand hand1, Hand hand2) {

        Card total1 = hand1.calculate();
        Card total2 = hand2.calculate();

        int result = 0;

        if (total1.getMagic() > total2.getMagic()) {
            result++;
        } else if (total1.getMagic() < total2.getMagic()) {
            result--;
        }

        if (total1.getStrength() > total2.getStrength()) {
            result++;
        } else if (total1.getStrength() < total2.getStrength()) {
            result--;
        }

        if (total1.getIntelligence() > total2.getIntelligence()) {
            result++;
        } else if (total1.getIntelligence() < total2.getIntelligence()) {
            result--;
        }

        return result;
    }
}
