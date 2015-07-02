package com.jonatantierno.countingcards;

import com.jonatantierno.countingcards.core.GameNode;
import com.jonatantierno.countingcards.core.Player;
import com.jonatantierno.countingcards.core.Turn;
import com.jonatantierno.countingcards.rockygame.RockyGame;
import com.jonatantierno.countingcards.rockygame.RockyPlayers;
import com.jonatantierno.countingcards.rockygame.TurnParser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CountingCards{

    /**
     * Main method.
     * @param args receives the name of the INPUT file.
     */
    public static void main(String[] args) {
        CountingCards mainObject = new CountingCards();
        GameNode rootGameNode= null;

        try {
            rootGameNode = mainObject.parse("INPUT.txt");
        } catch (FileNotFoundException e) {
            System.out.println("File INPUT.txt not found");
            return;
        }

        List<GameNode> solutions = rootGameNode.findSolutions();

        if (solutions.size() <= 0){
            System.out.println("No solutions found");
        } else if (solutions.size() == 1){
            printSolution(solutions);
        } else if (solutions.size() > 1){
            printAllPossibleSolutions(solutions);
        }
    }

    private static void printSolution(List<GameNode> leaves) {
        System.out.println(leaves.get(0).getResultAsString());
    }

    private static void printAllPossibleSolutions(List<GameNode> solutions) {
        System.out.println("\n\n"+solutions.size()+" solutions found");

        System.out.println(solutions.get(0).getResultAsString());

        for (int i=1; i<solutions.size(); i++){
            System.out.println("\n____\n");
            System.out.println(solutions.get(i).getResultAsString());
        }
    }


    static final GameNode parse(String filePath) throws FileNotFoundException {
            TurnParser turnParser = new TurnParser();

            List<Turn> inputLines = new ArrayList<>();

            Scanner scanner = new Scanner(new File(filePath)).useDelimiter("\\n");

            Turn currentTurn = null;

            while (scanner.hasNext()){
                Turn line = turnParser.readTurn(scanner.next());

                if (isSignal(line)) {
                    turnParser.addSignal(currentTurn, line);
                } else {
                    inputLines.add(line);
                    currentTurn = line;
                }
            }

            return new GameNode(Player.NULL, new RockyGame(), inputLines);
    }

    private static boolean isSignal(Turn line) {
        return RockyPlayers.SIGNAL.equals(line.getPlayer());
    }

}
