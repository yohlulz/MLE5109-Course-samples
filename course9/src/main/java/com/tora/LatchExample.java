package com.tora;

import java.util.concurrent.CountDownLatch;

/**
 * @author Ovidiu Maja<ovi.dan89@gmail.com>
 * @version Nov 23, 2017
 */
public class LatchExample {
    public static void main(String[] args) throws InterruptedException {
        final int nThreads = 100;
        Runnable task = () -> System.out.println(Thread.currentThread());

        final CountDownLatch startGate = new CountDownLatch(1);
        final CountDownLatch endGate = new CountDownLatch(nThreads);

        for (int i = 0; i < nThreads; i++) {
            new Thread(() -> {
                try {
                    startGate.await();
                    try {
                        task.run();
                    } finally {
                        endGate.countDown();
                    }
                } catch (InterruptedException ignored) { }
            }).start();
        }

        startGate.countDown();
        endGate.await();
    }
}
