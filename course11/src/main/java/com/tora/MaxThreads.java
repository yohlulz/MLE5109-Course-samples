package com.tora;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

/**
 * @author Ovidiu Maja<ovi.dan89@gmail.com>
 * @version Dec 06, 2017
 */
public class MaxThreads {
    public static void main(String[] args) {
        final ExecutorService executor = Executors.newCachedThreadPool();
        IntStream.rangeClosed(0, Integer.MAX_VALUE)
                .forEach(i -> executor.execute(() -> {
                    System.out.println("thread " + i);
                    try {
                        Thread.sleep(Integer.MAX_VALUE);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }));
    }
}
