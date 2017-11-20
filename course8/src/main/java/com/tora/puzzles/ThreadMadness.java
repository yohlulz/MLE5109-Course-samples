package com.tora.puzzles;

/**
 * @author Ovidiu Maja<ovi.dan89@gmail.com>
 * @version Nov 15, 2017
 */
public class ThreadMadness {
    public static void main(String[] args) {
        new Thread(() -> {
            synchronized (Thread.class) {
                try {
                    Thread.sleep(Long.MAX_VALUE);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        new Thread(() -> System.out.println("Running...")).start();
    }
}
