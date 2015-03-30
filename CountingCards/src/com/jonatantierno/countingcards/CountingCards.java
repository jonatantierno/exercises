package com.jonatantierno.countingcards;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CountingCards{

    private final List<InputLine> inputLines = new ArrayList<>();

    /**
     * Main method.
     * @param args receives the name of the INPUT file.
     */
    public static void main(String[] args) {
        new CountingCards().solve(args);
    }


    void solve(String[] strings) {
        try {
            Scanner scanner = new Scanner(new File(strings[0])).useDelimiter("\\n");
            while (scanner.hasNext()){
                inputLines.add(new InputLine(scanner.next()));
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    List<InputLine> getLinesRead() {
        return inputLines;
    }

    InputLine getLine(int i) {
        return inputLines.get(i);
    }
}
