package com.tora.puzzles;

/**
 * @author Ovidiu Maja<ovi.dan89@gmail.com>
 * @version Nov 15, 2017
 */
public class Counter {
    public static final int MAX_COUNTER = 1;
    private static int counter;

    public static void main(String[] args) throws InterruptedException {
        Runnable task = () -> {
            if (counter >= MAX_COUNTER) {
                return;
            }
            counter++;
            System.out.println(Thread.currentThread() + " counter: " + counter);
        };
        final Thread thread1 = new Thread(task);
        final Thread thread2 = new Thread(task);

        thread1.start();
        thread2.start();

        System.out.println("Counter: " + counter);
    }
}
