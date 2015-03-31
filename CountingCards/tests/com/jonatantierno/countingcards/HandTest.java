package com.jonatantierno.countingcards;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.fail;
import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test for modifying a player's hand with moves
 * Created by jonatan on 30/03/15.
 */
public class HandTest {
    Hand rockyHand;

    @Before
    public void setup(){
        setInitialHand();
    }
    @Test
    public void shouldGetCards(){

        assertEquals(4, rockyHand.cards.size());

        assertEquals(new Card("QD"), rockyHand.cards.get(0));
        assertEquals(new Card("7S"), rockyHand.cards.get(1));
        assertEquals(new Card("6C"),rockyHand.cards.get(2));
        assertEquals(new Card("AH"), rockyHand.cards.get(3));
    }

    private void setInitialHand() {
        rockyHand = new Hand(Player.ROCKY);

        assertEquals(Player.ROCKY, rockyHand.player);
        assertEquals(0, rockyHand.cards.size());

        List<Action> actions= new ArrayList<Action>();
        actions.add(new Action("+QD"));
        actions.add(new Action("+7S"));
        actions.add(new Action("+6C"));
        actions.add(new Action("+AH"));

        rockyHand.perform(actions);
    }

    @Test
    public void shouldDiscard(){
        boolean success = rockyHand.perform(new Action("-QD:discard"));

        assertTrue(success);
        assertEquals(3, rockyHand.cards.size());
        assertFalse(rockyHand.cards.contains(new Card("QD")));

    }
    @Test
    public void whenDiscardAndCardIsNotInHandThenNotify(){
        assertFalse(rockyHand.perform(new Action("-QH:discard")));
    }
}
