package com.tora.puzzles;

import java.util.Arrays;
import java.util.Optional;

/**
 * @author Ovidiu Maja<ovi.dan89@gmail.com>
 * @version Dec 02, 2017
 */

public class StringSplitAndConcatenate {
    public static void main(String[] args) {
        sequential();
        parallel();
        parallelReduce();
    }

    private static void sequential() {
        String words[] = "the quick brown fox jumps over the lazy dog".split(" ");
        Arrays.stream(words).forEach(StringConcatenator::concatStr);
        System.out.println("sequential: " + StringConcatenator.result);
    }

    private static void parallel() {
        String words[] = "the quick brown fox jumps over the lazy dog".split(" ");
        Arrays.stream(words).parallel().forEach(StringConcatenator::concatStr);
        System.out.println("parallel: " + StringConcatenator.result);
    }

    private static void parallelReduce() {
        String words[] = "the quick brown fox jumps over the lazy dog".split(" ");
        Optional<String> originalString =
                (Arrays.stream(words).parallel().reduce((a, b) -> a + " " + b));
        System.out.println("parallel reduce: " + originalString.get());
    }

    static class StringConcatenator {
        public static String result = "";
        public static void concatStr(String str) {
            result = result + " " + str;
        }
    }

}
