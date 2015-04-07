package com.jonatantierno.countingcards;

import com.jonatantierno.countingcards.actions.Action;
import com.jonatantierno.countingcards.actions.ActionFactory;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Test for modifying a player's hand with moves
 * Created by jonatan on 30/03/15.
 */
public class PileTest {
    Game game;

    @Before
    public void setup(){
        setInitialHand();
    }

    private void setInitialHand() {
        game = new Game();

        List<Action> actions= new ArrayList<>();
        actions.add(ActionFactory.build(Player.ROCKY, "+QD"));
        actions.add(ActionFactory.build(Player.ROCKY, "+7S"));
        actions.add(ActionFactory.build(Player.ROCKY, "+6C"));
        actions.add(ActionFactory.build(Player.ROCKY, "+AH"));

        Game game1 = game;
        for(Action action : actions){
            game1 = action.performCertain(game1);
        }
        game = game1;
    }

    @Test
    public void shouldGetCards(){

        List<String> rockyHand = game.getPile(Player.ROCKY);

        assertEquals(4, rockyHand.size());

        assertEquals("QD", rockyHand.get(0));
        assertEquals("7S", rockyHand.get(1));
        assertEquals("6C", rockyHand.get(2));
        assertEquals("AH", rockyHand.get(3));
    }

    @Test
    public void shouldDiscard(){
        game = ActionFactory.build(Player.ROCKY, "-QD:discard").performCertain(game);

        List<String> rockyHand = game.getPile(Player.ROCKY);

        assertEquals(3, rockyHand.size());
        assertFalse(rockyHand.contains("QD"));
    }
}
