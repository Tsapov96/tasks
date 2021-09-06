package com.tsystems.javaschool.tasks.subsequence;

import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class Subsequence {

    /**
     * Checks if it is possible to get a sequence which is equal to the first
     * one by removing some elements from the second one.
     *
     * @param x first sequence
     * @param y second sequence
     * @return <code>true</code> if possible, otherwise <code>false</code>
     */
    @SuppressWarnings("rawtypes")
    public boolean find(List x, List y) {
        // TODO: Implement the logic here
        if ((x == null) || (y == null)) throw new IllegalArgumentException();
        if (x.isEmpty()) return true;
        int xCounter = 0;
        for (int yCounter = 0; yCounter < y.size(); yCounter++){
            if (y.get(yCounter).equals(x.get(xCounter))) xCounter++;
            else if (y.get(yCounter).equals(x.get(0))){
                return find(x, y.subList(yCounter, y.size()));
            }
            if (xCounter == x.size()-1) break;
        }
        return xCounter == x.size()-1;
    }
}

