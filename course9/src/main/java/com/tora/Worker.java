package com.tora;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @author Ovidiu Maja<ovi.dan89@gmail.com>
 * @version Nov 15, 2017
 */
public class Worker extends Thread {
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

    // It’s quitting time, wait for worker - Called by good boss
    synchronized void quit() throws InterruptedException {
        quittingTime = true;
        join();
    }

    // Rescind quitting time - Called by evil boss
    synchronized void keepWorking() {
        quittingTime = false;
    }

    public static void main(String[] args) throws InterruptedException {
        final Worker worker = new Worker();
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


