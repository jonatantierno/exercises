package com.jonatantierno.countingcards;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class CountingCards{
    final Game game;

    public CountingCards() {
        game = new Game();

        game.add(Player.SHADY);
        game.add(Player.ROCKY);
        game.add(Player.DANNY);
        game.add(Player.LIL);
        game.add(Player.DISCARD);
        game.add(Player.TRANSIT);
    }

    private final List<Turn> inputLines = new ArrayList<>();
    private Iterator<Turn> iterator;

    /**
     * Main method.
     * @param args receives the name of the INPUT file.
     */
    public static void main(String[] args) {
        new CountingCards().parse(args);
    }


    void parse(String[] strings) {
        try {
            Scanner scanner = new Scanner(new File(strings[0])).useDelimiter("\\n");
            while (scanner.hasNext()){
                Turn line = new Turn(scanner.next());

                if (line.getPlayer() != Player.SIGNAL) {
                    inputLines.add(line);
                } else {
                    Turn lilsLastTurn = inputLines.get(inputLines.size() - 1);

                    assert lilsLastTurn.getPlayer() == Player.LIL;

                    lilsLastTurn.addSignal(Player.LIL,line);
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        iterator = inputLines.iterator();
    }

    List<Turn> getLinesRead() {
        return inputLines;
    }

    Turn getLine(int i) {
        return inputLines.get(i);
    }

    public static List<Action> toActions(List<Turn> lines) {
        List<Action> actions = new ArrayList<>(lines.size());

        for (Turn line : lines){
            actions.addAll(line.getActions());
        }
        return actions;
    }
}
