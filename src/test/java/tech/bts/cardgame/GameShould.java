package tech.bts.cardgame;

import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class GameShould {

    /**
     * Creating a game:
     * - A game is created with a deck of cards (each card has 3 numbers (>=1) that added make 10).
     *   - Note: the 3 numbers represent magic, strength, intelligence
     * - When a game is created, its state is OPEN.
     *
     * Joining a game:
     * - A player can join an OPEN game (for simplicity, a player is indicated by its username).
     * - When 2 players join the game, the state of the game changes to PLAYING.
     * - A player can't join if the game state is not OPEN (throw an exception if someone tries).
     *
     * Picking cards:
     * - When the game is PLAYING, any player that joined the game can pick a card.
     * - After picking a card, a player must keep it or discard it.
     * - A player can only discard 2 cards (i.e. must pick 3 cards).
     *
     * The battle (point calculation):
     * - When the 2 players have picked 3 cards, the winner of that round is calculated:
     *   - Each player adds all magics, all strengths and all intelligences
     *   - Totals of each category is compared between players
     *   - Player who wins in 2 categories earns a point (there may be no winner)
     *
     * - After the points are calculated, a new battle starts (players pick cards again)
     * - If there are less than 10 cards in the deck, the game changes to state FINISHED
     */

    @Test
    public void be_open_when_created() {

        Game game = new Game(new Deck());
        assertThat(game.getState(), is(Game.State.OPEN));
    }

    @Test
    public void allow_joining_when_open() {
        Game game = new Game(new Deck());
        game.join("Ayça");
        assertThat(game.getPlayersName(), is(Arrays.asList("Ayça")));
        assertThat(game.getState(), is(Game.State.OPEN));

    }

    @Test
    public void be_playing_when_2_players_join() {
        Game game = new Game(new Deck());
        game.join("Ayça");
        game.join("Monica");
        assertThat(game.getState(), is(Game.State.PLAYING));
    }

    @Test(expected = JoiningNotAllowedException.class)
    public void not_allow_joining_if_not_open() {
        Game game = new Game(new Deck());
        game.join("Ayça");
        game.join("Monica");
        game.join("Viktor");
    }

    @Test
    public void allow_player_pick_a_card_when_playing() {
        Deck deck = new Deck();
        Card card = new Card(3, 2, 5);
        deck.add(card);
        Game game = new Game(deck);
        game.join("Ayça");
        game.join("Monica");

        Card pickedCard = game.pickCard("Ayça");
        assertThat(pickedCard, is(card));
    }

    @Test(expected = PlayerNotInTheGameException.class)
    public void not_allow_picking_card_non_players() {
        Game game = new Game(new Deck());
        game.join("Ayça");
        game.join("Monica");
        game.pickCard("Alex");
    }

    @Test(expected = CannotPickTwoCardsInARowException.class)
    public void not_allow_picking_two_cards_in_a_row() {
        Deck deck = new Deck();
        deck.add(new Card(3, 2, 5));
        deck.add(new Card(3, 2, 5));
        Game game = new Game(deck);

        game.join("Ayça");
        game.join("Monica");

        game.pickCard("Ayça");
        game.pickCard("Ayça");
    }

    @Test
    public void allow_picking_if_previous_card_was_discarded() {
        Deck deck = new Deck();
        Card card1 = new Card(3, 2, 5);
        deck.add(card1);
        Card card2 = new Card(2, 7, 1);
        deck.add(card2);
        Game game = new Game(deck);

        game.join("Ayça");
        game.join("Monica");

        Card pickedCard1 = game.pickCard("Ayça");
        game.discard("Ayça");
        Card pickedCard2 = game.pickCard("Ayça");

        assertThat(pickedCard1, is(card2));
        assertThat(pickedCard2, is(card1));
    }
}