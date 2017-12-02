package com.tora.puzzles;

/**
 * @author Ovidiu Maja<ovi.dan89@gmail.com>
 * @version Nov 26, 2017
 */
public class SelfInterruptionCorrect {
    public static void main(String[] args) {
        Thread.currentThread().interrupt();
        if (Thread.currentThread().isInterrupted()) {
            System.out.println("Interrupted: " + Thread.currentThread().isInterrupted());
        } else {
            System.out.println("Not interrupted: " + Thread.currentThread().isInterrupted());
        }
    }
}