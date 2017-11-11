package com.tora.puzzles;

/**
 * @author Ovidiu Maja<ovi.dan89@gmail.com>
 * @version Nov 10, 2017
 */
public class AnimalFarm {
    public static void main(String[] args) {
        final String pig = "length: 10";
        final String cat = "length: 10";
        final String dog = "length: " + pig.length();
        System.out.println("Animals are equal: " + pig == dog);
        System.out.println("Animals are equal: " + (pig == cat));
    }
}