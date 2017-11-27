package com.tora;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @author Ovidiu Maja<ovi.dan89@gmail.com>
 * @version Nov 15, 2017
 */
public class WorkerFixed extends Thread {
    private volatile boolean quittingTime;

    public void run() {
        while (!quittingTime) {
            pretendToWork();
        }
        System.out.println("Beer is good");
    }

    private void pretendToWork() {
        try {
            Thread.sleep(300); // Sleeping on the job?
        } catch (InterruptedException ex) {
        }
    }

    private final Object lock = new Object();

    // It’s quitting time, wait for worker - Called by good boss
    void quit() throws InterruptedException {
        synchronized (lock) {
            quittingTime = true;
            join();
        }
    }

    // Rescind quitting time - Called by evil boss
    void keepWorking() {
        synchronized (lock) {
            quittingTime = false;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        final WorkerFixed worker = new WorkerFixed();
        worker.start();
        Timer t = new Timer(true); // Daemon thread
        t.schedule(new TimerTask() {
            public void run() {
                worker.keepWorking();
            }
        }, 500);
        Thread.sleep(400);
        worker.quit();
    }
}
