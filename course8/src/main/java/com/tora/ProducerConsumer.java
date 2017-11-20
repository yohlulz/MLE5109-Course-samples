package com.tora;

import java.util.concurrent.*;
import java.util.stream.IntStream;

/**
 * @author Ovidiu Maja<ovi.dan89@gmail.com>
 * @version Nov 17, 2017
 */
public class ProducerConsumer {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        final ExecutorService executorService = Executors.newFixedThreadPool(2);

        /* buffer queue */
        final BlockingQueue<Entry> entries = new LinkedBlockingQueue<>();

        /* consumer */
        executorService.submit(() -> {
            while (true) {
                try {
                    entries.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        /* producer */
        executorService.submit(() -> {
            IntStream.rangeClosed(0, Integer.MAX_VALUE)
                    .mapToObj(String::valueOf)
                    .map(Entry::new)
                    .forEach(entries::offer);
        }).get();

        executorService.shutdownNow();
    }

    private static class Entry {
        private final String message;

        Entry(String message) {
            this.message = message;
        }

        @Override
        public String toString() {
            return message;
        }
    }
}
