package com.tora.puzzles;

import java.util.Comparator;

public class BrokenComparator {
    public static void main(String[] args) {
        // Broken comparator - can you spot the flaw?
        Comparator<Integer> naturalOrder = (first, second) -> first < second ? -1 :  (first == second ? 0 : 1);

        System.out.println(naturalOrder.compare(new Integer(42), new Integer(42)));
        System.out.println(naturalOrder.compare(42, 42));

        System.out.println(naturalOrder.compare(126, 126));
        System.out.println(naturalOrder.compare(127, 127));

        System.out.println(naturalOrder.compare(128, 128));
        System.out.println(naturalOrder.compare(129, 129));
    }
}
