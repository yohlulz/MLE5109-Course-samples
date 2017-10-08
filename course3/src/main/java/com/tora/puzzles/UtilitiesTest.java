package com.tora.puzzles;

import java.util.LinkedList;
import java.util.List;

class UtilitiesTest {
    public static void main(String[] args) {
        List<Integer> intList = new LinkedList<>();
        List<Double> dblList = new LinkedList<>();

        System.out.println("First type: " + intList.getClass());
        System.out.println("Second type:" + dblList.getClass());
    }
}
