package com.tora.puzzles;

import java.util.stream.Stream;

/**
 * @author Ovidiu Maja<ovi.dan89@gmail.com>
 * @version Oct 26, 2017
 */
public class Comparing {
    public static void main(String[] args) {
        System.out.println(
                Stream.of(-3, -2, -1 , 0, 1, 2, 3)
                        .max(Math::max)
                        .get());
    }
}
