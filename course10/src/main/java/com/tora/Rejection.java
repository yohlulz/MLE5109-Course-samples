package com.tora;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Ovidiu Maja<ovi.dan89@gmail.com>
 * @version Dec 04, 2017
 */
public class Rejection {

    public static void main(String[] args) throws InterruptedException {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 2, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(2));

        executor.setRejectedExecutionHandler((task, exec) -> System.out.println(task.toString() + " : Rejected"));

        // Add some tasks
        for (int i = 0; i < 9; i++) {
            executor.execute(new WorkerTask("Task " + (i + 1)));
            // Sleep
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
        executor.shutdown();
        executor.awaitTermination(20, TimeUnit.SECONDS);
    }

    static class WorkerTask implements Runnable {
        // Task name
        private String name;

        public WorkerTask(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            System.out.println(name + " : Running");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
            }
            System.out.println(name + " : Done");
        }

        @Override
        public String toString() {
            return name;
        }
    }

}

