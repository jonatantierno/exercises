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
    }

    private final List<InputLine> inputLines = new ArrayList<>();
    private Iterator<InputLine> iterator;

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
                inputLines.add(new InputLine(scanner.next()));
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        iterator = inputLines.iterator();
    }

    List<InputLine> getLinesRead() {
        return inputLines;
    }

    InputLine getLine(int i) {
        return inputLines.get(i);
    }

}
