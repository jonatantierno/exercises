package com.jonatantierno.countingcards;

import com.jonatantierno.countingcards.core.GameNode;
import com.jonatantierno.countingcards.core.Player;
import com.jonatantierno.countingcards.core.Turn;
import com.jonatantierno.countingcards.rockygame.RockyGame;
import com.jonatantierno.countingcards.rockygame.RockyPlayers;
import com.jonatantierno.countingcards.rockygame.TurnParser;
import com.jonatantierno.countingcards.rockygame.serializer.ActionFactory;
import com.jonatantierno.countingcards.rockygame.serializer.ActionSerializer;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;

/**
 * Tests for processing the sample_input file
 */
public class GameSingleRoundTest {
    GameNode initialTurn;
    private ActionFactory actionFactory;
    private TurnParser turnParser;

    @Before
    public void setup() throws FileNotFoundException {
        initialTurn = CountingCards.parse(ParseTest.PATH+"SAMPLE_INPUT_2.txt");
        actionFactory = new ActionFactory(RockyPlayers.ROCKY_GAME_PLAYERS);

        turnParser = new TurnParser();
    }

    @Test
    public void whenTooManyPossibilitiesInTurnThenDoNotAdd(){

        Turn turn = turnParser.readTurn("Lil +??");
        turnParser.addSignal(turn, turnParser.readTurn("* +8H"));
        turnParser.addSignal(turn, turnParser.readTurn("* +9H +4C"));

        assertEquals(1, turn.signals.size());
        assertEquals(1, turn.getActions().get(0).possibilities.size());

        ActionSerializer serializer = new ActionSerializer();
        assertEquals("Lil: +8H", serializer.toString(turn.getActions().get(0).possibilities.get(0)));
    }

    @Test
    public void sample2ShouldWork() throws FileNotFoundException {
        GameNode turn = initialTurn;

        Scanner scanner = new Scanner(new File(ParseTest.PATH+"SAMPLE_SOLUTION_2.txt")).useDelimiter("\\n");


        List<GameNode> solutions = turn.findSolutions();
        assertEquals(1, solutions.size());

        String resultAsString = solutions.get(0).getResultAsString();
        Scanner actualScanner = new Scanner(resultAsString).useDelimiter("\\n");

        // Solution contains last round only.Skip.
        actualScanner.next();

        assertEquals(scanner.next(), actualScanner.next());
    }

    @Test
    public void shouldProcessRoundsAccordingToFirstPlayer(){
        assertEquals(RockyPlayers.PARTNER, initialTurn.getNextPlayer());

        GameNode turn = initialTurn.advanceRound();

        assertEquals(RockyPlayers.PARTNER, turn.getNextPlayer());
    }

    @Test
    public void whenFirstActionInTurnIsKnownShouldObtainNumberOfPossibilities(){
        Turn turn = turnParser.readTurn("Lil +2H +??");

        assertEquals(0, turn.getNumberOfPossibilities());

        turn.getActions().get(0).possibilities.add(actionFactory.build(RockyPlayers.PARTNER, "+5H"));
        assertEquals(1, turn.getNumberOfPossibilities());

        turn.getActions().get(0).possibilities.add(actionFactory.build(RockyPlayers.PARTNER, "+7H"));
        assertEquals(2, turn.getNumberOfPossibilities());
    }
    @Test
    public void shouldHandleSeveralPossibleGames(){
        RockyGame game = new RockyGame();
        game.getPile(RockyPlayers.PARTNER).add("6H");

        Turn turn= turnParser.readTurn("Lil +??");
        turn.getActions().get(0).possibilities.add(actionFactory.build(RockyPlayers.PARTNER, "+5H"));
        turn.getActions().get(0).possibilities.add(actionFactory.build(RockyPlayers.PARTNER, "+7H"));

        GameNode gameNode = new GameNode(Player.NULL, game, Collections.singletonList(turn));

        List<GameNode> possibleTurns = gameNode.buildPossibilities();

        assertEquals(2,possibleTurns.size());
        assertEquals(gameNode,possibleTurns.get(0).parent);
    }

    @Test
    public void shouldMarkImpossibleGamesAsImpossible(){
        RockyGame game = new RockyGame();
        game.getPile(RockyPlayers.PARTNER).add("6H");

        Turn impossibleTurn = turnParser.readTurn("Lil +??");
        impossibleTurn.getActions().get(0).possibilities.add(actionFactory.build(RockyPlayers.PARTNER, "+6H"));

        GameNode gameNode = new GameNode(Player.NULL, game, Collections.singletonList(impossibleTurn));

        List<GameNode> children = gameNode.buildPossibilities();

        assertEquals(1,children.size());
        assertEquals(RockyGame.IMPOSSIBLE,children.get(0).game);
    }
}
