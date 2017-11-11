package com.tora.puzzles;

import java.util.Random;

/**
 * @author Ovidiu Maja<ovi.dan89@gmail.com>
 * @version Nov 10, 2017
 */
public class Rhymes {
    private static Random rnd = new Random();
    public static void main(String[] args) {
        StringBuilder word = null;
        switch(rnd.nextInt(2)) {
            case 1: word = new StringBuilder('P');
            case 2: word = new StringBuilder('G');
            default: word = new StringBuilder('M');
        }
        word.append('a');
        word.append('i');
        word.append('n');
        System.out.println(word);
    }
}
