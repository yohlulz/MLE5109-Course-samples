package com.tora.puzzles;

/**
 * @author Ovidiu Maja<ovi.dan89@gmail.com>
 * @version Nov 15, 2017
 */
public class PingPong {
    public static synchronized void main(String[] a) {
        Thread t = new Thread(PingPong::pong);
        t.run();
        System.out.print("Ping");
    }

    private static synchronized void pong() {
        System.out.print("Pong");
    }
}
