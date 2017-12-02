package com.tora.puzzles;

/**
 * @author Ovidiu Maja<ovi.dan89@gmail.com>
 * @version Nov 26, 2017
 */
public class SelfInterruption {
    public static void main(String[] args) {
        Thread.currentThread().interrupt();
        if (Thread.interrupted()) {
            System.out.println("Interrupted: " + Thread.interrupted());
        } else {
            System.out.println("Not interrupted: " + Thread.interrupted());
        }
    }
}
