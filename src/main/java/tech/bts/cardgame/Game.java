package tech.bts.cardgame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Game {

    public enum State {OPEN, PLAYING}

    private final Deck deck;
    private State state;
    private List<String> usernames;
    private Map<String, Card> pickedCardByUserName;
    //private final static  int INITIAL_HAND_SIZE = 5;
    //private final static  int FINAL_HAND_SIZE = 3;
    //private final static  int NUM_CARDS_TO_DISCARD = INITIAL_HAND_SIZE - FINAL_HAND_SIZE;

    public Game(Deck deck) {

        this.deck = deck;
        this.state = State.OPEN;
        this.usernames = new ArrayList<>();
        this.pickedCardByUserName = new HashMap<>();
    }

    /**public void play() {

        Hand hand1 = deck.deal(INITIAL_HAND_SIZE);
        Hand hand2 = deck.deal(INITIAL_HAND_SIZE);

        //TODO: develop the discard method later
        //player1.discard(NUM_CARDS_TO_DISCARD);
        //player2.discard(NUM_CARDS_TO_DISCARD);

        System.out.println("Hand 1: \n" + hand1);
        System.out.println("Hand 2: \n" + hand2);

        int result = compare(hand1, hand2);

        if (result > 0){
            System.out.println("Player 1: "+player1.getName()+" wins!");
        } else if (result < 0) {
            System.out.println("Player 2: "+player2.getName()+" wins!");
        } else {
            System.out.println("No one wins...");
        }




    }*/

    private int compare(Hand hand1, Hand hand2) {

        Card total1 = hand1.calculate();
        Card total2 = hand2.calculate();

        System.out.println("Result player 1:\nM: " + total1.getMagic() + ", S: " + total1.getStrength() + ", I: " + total1.getIntelligence()+"\n");
        System.out.println("Result player 2:\nM: " + total2.getMagic() + ", S: " + total2.getStrength() + ", I: " + total2.getIntelligence()+"\n");

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

    public State getState() {
        return this.state;
    }

    public List<String> getPlayersName() {
        return usernames;
            }

    public void join(String userName) {
        if (this.state != State.OPEN) {
            throw new JoiningNotAllowedException();
        }

        this.usernames.add(userName);
        if (usernames.size() == 2) {
            this.state = State.PLAYING;
        }
    }

    public Card pickCard(String userName) {

        if (!usernames.contains(userName)){
            throw new PlayerNotInTheGameException();
        }
        Card pickedCard = pickedCardByUserName.get(userName);
        if (pickedCard != null) {
            throw new CannotPickTwoCardsInARowException();
        }
        Card newPickedCard = deck.pickCard();
        pickedCardByUserName.put(userName, newPickedCard);
        return newPickedCard;
    }

    public void discard(String userName) {
        pickedCardByUserName.remove(userName);
    }
}
