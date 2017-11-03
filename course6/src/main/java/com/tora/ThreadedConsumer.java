package com.tora;

import java.nio.file.Path;
import java.util.Collection;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author Ovidiu Maja<ovi.dan89@gmail.com>
 * @version Nov 03, 2017
 */
public class ThreadedConsumer implements Runnable {
    private final CountDownLatch waitLatch;
    private final Path path;
    private final PathConsumer consumer;


    public ThreadedConsumer(int workers, Path path, PathConsumer consumer) {
        waitLatch = new CountDownLatch(workers);
        this.path = path;
        this.consumer = consumer;

        final ExecutorService taskExecutor = Executors.newCachedThreadPool();
        final Collection<CompletableFuture<Void>> tasks = IntStream.range(0, workers)
                .mapToObj(i -> CompletableFuture.runAsync(this, taskExecutor))
                .collect(Collectors.toList());

        try {
            /* wait for completion */
            CompletableFuture.allOf(tasks.toArray(new CompletableFuture[tasks.size()])).get();
            /* close the executor */
            taskExecutor.shutdown();

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        waitLatch.countDown();
        try {
            waitLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        consumer.accept(path);
    }
}
