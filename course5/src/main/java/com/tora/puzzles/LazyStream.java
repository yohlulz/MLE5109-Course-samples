package com.tora.puzzles;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author Ovidiu Maja<ovi.dan89@gmail.com>
 * @version Oct 26, 2017
 */
public class LazyStream {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");


        Stream stream = list.stream();
        list.add("4");

        stream.forEach(System.out::println);
    }
}
