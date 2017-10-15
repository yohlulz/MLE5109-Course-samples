package com.tora.puzzles;

import java.util.Comparator;

public class BrokenComparator {
    public static void main(String[] args) {
        // Broken comparator - can you spot the flaw?
        Comparator<Integer> naturalOrder = (first, second) -> first < second ? -1 :  (first == second ? 0 : 1);

        System.out.println(naturalOrder.compare(new Integer(42), new Integer(42)));
    }
}
