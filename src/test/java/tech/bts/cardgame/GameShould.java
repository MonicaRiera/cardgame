package tech.bts.cardgame;

import org.junit.Test;
import tech.bts.cardgame.exceptions.*;
import tech.bts.cardgame.model.*;

import java.util.Map;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static tech.bts.cardgame.model.Game.State.FINISHED;

public class GameShould {

    @Test
    public void be_open_when_created() {

        Game game = new Game(new Deck());
        assertThat(game.getState(), is(Game.State.OPEN));
    }

    @Test
    public void allow_joining_when_open() {
        Game game = new Game(new Deck());
        Player player = game.join("Ayça");
        assertThat(player.getName(), is("Ayça"));
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

    @Test (expected = NotPlayingYetException.class)
    public void not_allow_picking_if_state_is_not_playing() {
        Game game = new Game(new Deck());
        game.join("Ayça");
        game.pickCard("Ayça");
    }

    @Test (expected = PickingNeededBeforeActingException.class)
    public void not_allow_discarding_before_picking() {
        Game game = new Game(new Deck());
        game.join("Ayça");
        game.join("Monica");
        game.discard("Ayça");
    }

    /**This test did not allow to discard more than two cards.
     * Since the program itself auto-completes the hand once the user has discarded two cards,
     * it is not necessary anymore because when the user tries to pick&discard another card
     * TooManyCardsInHandException is executed
     *
     * @Test (expected = TooManyDiscardsException.class)
    public void not_allow_discarding_more_than_two_cards() {
        Deck deck = new Deck();
        deck.generate();
        Game game = new Game(deck);
        game.join("Ayça");
        game.join("Monica");
        game.pickCard("Ayça");
        game.discard("Ayça");
        game.pickCard("Ayça");
        game.discard("Ayça");
        game.pickCard("Ayça");
        game.discard("Ayça");
    }*/

    @Test
    public void keep_chosen_cards() {
        Deck deck = new Deck();
        deck.add(new Card(3, 2, 5));
        deck.add(new Card(6, 1, 3));
        Game game = new Game(deck);
        Player p1 = game.join("Ayça");
        Player p2 = game.join("Monica");
        game.pickCard("Ayça");
        game.keepCard("Ayça");
        Map<String, Hand> result = game.getHands();

        assertThat(p1.getHand().size(), is(1));

        game.pickCard("Ayça");
        game.keepCard("Ayça");

        assertThat(p1.getHand().size(), is(2));
    }

    @Test
    public void return_player_hand() {
        Deck deck = new Deck();
        deck.generate();
        Game game = new Game(deck);
        Player p1 = game.join("Ayça");
        Player p2 = game.join("Monica");
        game.pickCard("Ayça");
        game.keepCard("Ayça");
        game.pickCard("Ayça");
        game.keepCard("Ayça");
        game.pickCard("Ayça");
        game.keepCard("Ayça");

        assertThat(p1.getHand().size(), is(3));
    }

    @Test (expected = TooManyCardsInHandException.class)
    public void limit_hand_size_to_three() {
        Deck deck = new Deck();
        deck.generate();
        Game game = new Game(deck);
        game.join("Ayça");
        game.join("Monica");
        game.pickCard("Ayça");
        game.keepCard("Ayça");
        game.pickCard("Ayça");
        game.keepCard("Ayça");
        game.pickCard("Ayça");
        game.keepCard("Ayça");
        game.pickCard("Ayça");
        game.keepCard("Ayça");
    }

    /**This test checked that the hand size was 3 after auto-completing.
     * Since we've implemented the battle when the hands are ready,
     * the values are deleted once used and the final value is an empty hand*/
    @Test
    public void autocomplete_when_two_cards_are_discarded() {
        Deck deck = new Deck();
        deck.generate();
        Game game = new Game(deck);
        Player p1 = game.join("Ayça");
        Player p2 = game.join("Monica");
        game.pickCard("Ayça");
        game.discard("Ayça");
        game.pickCard("Ayça");
        game.discard("Ayça");
        game.pickCard("Monica");
        game.discard("Monica");
        game.pickCard("Monica");
        game.keepCard("Monica");
        game.pickCard("Monica");
        game.discard("Monica");

        assertEquals(p1.getHand(), p2.getHand());

    }

    @Test
    public void give_a_point_to_winner() {
        Deck deck = new Deck();
        deck.generate();
        Game game = new Game(deck);
        Player p1 = game.join("Ayça");
        Player p2 = game.join("Monica");
        game.pickCard("Ayça");
        game.discard("Ayça");
        game.pickCard("Ayça");
        game.discard("Ayça");
        game.pickCard("Monica");
        game.discard("Monica");
        game.pickCard("Monica");
        game.keepCard("Monica");
        game.pickCard("Monica");
        game.discard("Monica");

        assertThat(p2.getPoints(), is(1));
    }

    @Test
    public void set_control_values_to_default() {
        Deck deck = new Deck();
        deck.add(new Card(1, 2, 7));
        deck.add(new Card(1, 3, 6));
        deck.add(new Card(1, 4, 5));
        deck.add(new Card(1, 5, 4));
        deck.add(new Card(1, 6, 3));
        deck.add(new Card(1, 7, 2));
        deck.add(new Card(1, 8, 1));
        deck.add(new Card(2, 2, 6));
        deck.add(new Card(2, 3, 5));
        deck.add(new Card(2, 4, 4));
        deck.add(new Card(2, 5, 3));
        Game game = new Game(deck);
        Player p1 = game.join("Ayça");
        Player p2 = game.join("Monica");
        game.pickCard("Ayça");
        game.discard("Ayça");
        game.pickCard("Ayça");
        game.discard("Ayça");
        game.pickCard("Monica");
        game.discard("Monica");
        game.pickCard("Monica");
        game.keepCard("Monica");
        game.pickCard("Monica");
        game.discard("Monica");

        assertEquals(p1.getHand(), null);
        assertThat(p2.getDiscardedCards(), is(0));
    }

    @Test
    public void set_state_to_finished_when_less_than_ten_cards() {
        Deck deck = new Deck();
        deck.add(new Card(1, 2, 7));
        deck.add(new Card(1, 3, 6));
        deck.add(new Card(1, 4, 5));
        deck.add(new Card(1, 5, 4));
        deck.add(new Card(1, 6, 3));
        deck.add(new Card(1, 7, 2));
        deck.add(new Card(1, 8, 1));
        deck.add(new Card(2, 2, 6));
        deck.add(new Card(2, 3, 5));
        deck.add(new Card(2, 4, 4));
        deck.add(new Card(2, 5, 3));
        Game game = new Game(deck);
        game.join("Ayça");
        game.join("Monica");
        game.pickCard("Ayça");
        game.discard("Ayça");
        game.pickCard("Ayça");
        game.discard("Ayça");
        game.pickCard("Monica");
        game.discard("Monica");
        game.pickCard("Monica");
        game.keepCard("Monica");
        game.pickCard("Monica");
        game.discard("Monica");

        assertThat(game.getState(), is(FINISHED));
    }
    





}