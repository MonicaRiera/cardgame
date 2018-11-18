package tech.bts.cardgame;

import tech.bts.cardgame.exceptions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Game {

    public enum State {OPEN, PLAYING, FINISHED}

    private final Deck deck;
    private State state;
    private List<Player> players;
    private Map<String, Card> pickedCardByUserName;
    private Map<String, Integer> discardedCardsByUserName;
    private Map<String, Hand> hands;

    public Game(Deck deck) {

        this.deck = deck;
        this.state = State.OPEN;
        this.players = new ArrayList<>();
        this.pickedCardByUserName = new HashMap<>();
        this.discardedCardsByUserName = new HashMap<>();
        this.hands = new HashMap<>();
    }

    /**When a user joins the game, a new player is created and added to a list of players
     * After two players joined the status of the game is changed to PLAYING and no other
     * users are allowed to enter the game*/
    public void join(String userName) {
        if (this.state != State.OPEN) {
            throw new JoiningNotAllowedException();
        }

        this.players.add(new Player(userName));
        if (players.size() == 2) {
            this.state = State.PLAYING;
        }
    }

    /**When the status is PLAYING, only the users in the game can pick cards
     * If a user has more than three cards TooManyCardsInHandException is executed.
     * If a user tries to pick more than one card before deciding what to do with
     * the previous one, CannotPickTwoCardsInARowException is executed*/
    public Card pickCard(String userName) {

        if (this.state != State.PLAYING) {
            throw new NotPlayingYetException();
        } else {

            if (!getPlayersName().contains(userName)){
                throw new PlayerNotInTheGameException();
            }

            if (hands.get(userName) != null && hands.get(userName).size() >= 3) {
                throw new TooManyCardsInHandException();
            }

            Card pickedCard = pickedCardByUserName.get(userName);
            if (pickedCard != null) {
                throw new CannotPickTwoCardsInARowException();
            }
            Card newPickedCard = deck.pickCard();
            pickedCardByUserName.put(userName, newPickedCard);
            return newPickedCard;
        }
    }

    /**If the user discards a card, it is removed from the pickedCardsByUserName map and
     * the counter of discardedCardsByUserName is augmented by 1.
     * After that, it executes checkAutoComplete and it auto-fills the rest of the hand if the user has
     * already discarded two cards.
     * If a user tries to discard a card before picking it, PickingNeededBeforeActingException
     * is executed*/
    public void discard(String userName) {
        if (pickedCardByUserName.get(userName) != null) {

            if (discardedCardsByUserName.get(userName) == null) {
                pickedCardByUserName.remove(userName);
                discardedCardsByUserName.put(userName, 1);

            } else if (discardedCardsByUserName.get(userName) < 2) {
                pickedCardByUserName.remove(userName);
                int i = discardedCardsByUserName.get(userName) + 1;
                discardedCardsByUserName.put(userName, i);
            }

            checkAutoComplete(userName);

        } else {
            throw new PickingNeededBeforeActingException();
        }
    }

    /**When a player has discarded 2 cards, the hand of that player is completed automatically
     * by adding cards to it until the player has 3 cards. For example, if a player keeps 2 cards and discards 2 cards,
     * their hand is automatically completed with 1 card more*/
    public void checkAutoComplete(String userName) {
        if (discardedCardsByUserName.get(userName) != null && discardedCardsByUserName.get(userName) == 2) {

            if (hands.get(userName) != null) {
                Hand hand = hands.get(userName);
                while (hand.size() < 3) {
                    pickCard(userName);
                    keepCard(userName);
                }

            } else {
                for (int i = 0; i < 3; i++) {
                    pickCard(userName);
                    keepCard(userName);
                }
            }
        }
    }

    /**If the user keeps the card, it is added to a hand unless it has already three cards,
     * that TooManyCardsInHandException is executed.
     * Once the two players have three cards in hand, compare() method is called to start the battle.
     * If a user tries to keep a card before picking it, PickingNeededBeforeActingException
     * is executed*/
    public void keepCard(String userName) {

        if (pickedCardByUserName.get(userName) != null) {

            if (hands.get(userName) != null) {

                Hand hand = hands.get(userName);
                if (hand.size() < 3) {
                    hand.add(pickedCardByUserName.get(userName));
                    pickedCardByUserName.remove(userName);
                    hands.put(userName, hand);
                } else {
                    throw new TooManyCardsInHandException();
                }

            } else {
                List<Card> cards = new ArrayList<>();
                cards.add(pickedCardByUserName.get(userName));
                Hand hand = new Hand(cards);
                hands.put(userName, hand);
                pickedCardByUserName.remove(userName);
            }

            int handsCompleted = 0;
            for (Player player : players) {
                String name = player.getName();
                if (hands.get(name) != null && hands.get(name).size() == 3) {
                    handsCompleted++;
                }
            }

            if (handsCompleted == 2) {
                compare();
            }

        } else {
            throw new PickingNeededBeforeActingException();
        }
    }

    /**When the hands of the 2 players are complete (3 cards each)
     * the battle is performed and the winning player (if any) gets a point.
     * After that, the control maps (like pickedCardByUserName) are restarted
     * and if the deck has less than ten cards, the game state is set to FINISHED*/
    private void compare() {
        Card total1 = hands.get(players.get(0).getName()).calculate();
        Card total2 = hands.get(players.get(1).getName()).calculate();
        //System.out.println("Result player 1:\nM: " + total1.getMagic() + ", S: " + total1.getStrength() + ", I: " + total1.getIntelligence() + "\n");
        //System.out.println("Result player 2:\nM: " + total2.getMagic() + ", S: " + total2.getStrength() + ", I: " + total2.getIntelligence() + "\n");

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

        if (result > 0) {
            players.get(0).setPoints(1);

        } else if (result < 0) {
            players.get(1).setPoints(1);
        } else {
            //System.out.println("No one wins...");
        }

        this.pickedCardByUserName = new HashMap<>();
        this.hands = new HashMap<>();
        this.discardedCardsByUserName = new HashMap<>();

        if (deck.size() < 10) {
            this.state = State.FINISHED;
        }
    }


    public Hand gatUserHand(String userName) {
        return hands.get(userName);
    }

    public List<Player> getPlayers() {
        return players;
    }

    public List<String> getPlayersName() {
        List<String> names = new ArrayList<>();
        for (Player player : players) {
            names.add(player.getName());
        }
        return names;
    }

    public State getState() {
        return this.state;
    }

    public Deck getDeck() {
        return deck;
    }

    public Map<String, Card> getPickedCardByUserName() {
        return pickedCardByUserName;
    }

    public Map<String, Integer> getDiscardedCardsByUserName() {
        return discardedCardsByUserName;
    }

    public Map<String, Hand> getHands() {
        return hands;
    }
}
