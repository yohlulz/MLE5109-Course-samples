package com.tora.puzzles;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Ovidiu Maja<ovi.dan89@gmail.com>
 * @version Oct 26, 2017
 */
public class Sort {
    public static void main(String[] args) {
        List<String> strings = Arrays.asList("eeny ", "meeny ", "miny ", "mo ");
        Collections.sort(strings, (str1, str2) -> str2.compareTo(str1));
        strings.forEach(System.out::print);
    }
}
