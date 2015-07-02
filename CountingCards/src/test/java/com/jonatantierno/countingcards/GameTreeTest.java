package com.jonatantierno.countingcards;

import com.jonatantierno.countingcards.core.GameNode;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Tests for processing the sample_input file
 */
public class GameTreeTest {

    @Test
    public void sample3ShouldWork() throws FileNotFoundException {
        Scanner expectedScanner = new Scanner(new File(ParseTest.PATH+"SAMPLE_SOLUTION.txt")).useDelimiter("\\n");


        List<GameNode> solutions = CountingCards.parse(ParseTest.PATH+"SAMPLE_INPUT_WITH_OPTIONS.txt").findSolutions();
        String resultAsString = solutions.get(0).getResultAsString();
        Scanner actualScanner = new Scanner(resultAsString).useDelimiter("\\n");

        while(expectedScanner.hasNext()) {
            assertTrue(actualScanner.hasNext());

            assertEquals(expectedScanner.nextLine(), actualScanner.nextLine());
        }

    }

}
