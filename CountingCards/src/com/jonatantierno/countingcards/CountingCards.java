package com.jonatantierno.countingcards;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CountingCards{

    public CountingCards() {
    }

    /**
     * Main method.
     * @param args receives the name of the INPUT file.
     */
    public static void main(String[] args) {
        CountingCards objectUnderTest = new CountingCards();
        GameNode root= null;

        try {
            root = objectUnderTest.parse("INPUT.txt");
        } catch (FileNotFoundException e) {
            System.out.println("File INPUT.txt not found");
            return;
        }

        List<GameNode> leaves = root.createSolutionTree();

        if (leaves.size() == 0){
            System.out.println("No solutions found");
            return;
        }

        System.out.println(leaves.get(0).getResultAsString());

        if (leaves.size() > 1){
            System.out.println("\n\n"+leaves.size()+" solutions found");
            return;
        }
    }


    static final GameNode parse(String filePath) throws FileNotFoundException {
            Game game = new Game();

            List<Turn> inputLines = new ArrayList<>();

            Scanner scanner = new Scanner(new File(filePath)).useDelimiter("\\n");
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

            return new GameNode(Player.NONE, game, inputLines);
    }
}
