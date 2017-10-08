package com.tora.puzzles;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListPuzzle {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        Integer[] arr = {2,10,3};
        list = Arrays.asList(arr);
        list.set(0, 3);
        System.out.println(list);
        list.add(1);
        System.out.println(list);
    }
}
