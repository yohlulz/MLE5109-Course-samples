package com.tora.puzzles;

import java.util.stream.IntStream;

/**
 * @author Ovidiu Maja<ovi.dan89@gmail.com>
 * @version Oct 26, 2017
 */
public class StreamReuse {
    public static void main(String[] args) {
        IntStream chars = "bookkeep".chars();
        System.out.println(chars.count());
        chars.distinct()
                .sorted()
                .forEach(ch -> System.out.printf("%c ", ch));
    }
}
