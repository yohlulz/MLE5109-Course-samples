package com.tora.puzzles;

/**
 * @author Ovidiu Maja<ovi.dan89@gmail.com>
 * @version Nov 19, 2017
 *
 *
 * Credits: http://wouter.coekaerts.be/2012/puzzle-dreams-solution
 */
class Dream {
    private static void doWait(Sleeper s) {
        try {
            s.wait(200);
        } catch (InterruptedException e) {
        }
    }

    public void dream(final Sleeper s) {
        new Thread(() -> s.enter(new Dream() {
            @Override
            public void dream(Sleeper s1) {
                doWait(s1);
            }
        })).start();

        doWait(s);
    }
}

public class Sleeper {
    private int level;

    public synchronized int enter(Dream dream) {
        level++;
        try {
            dream.dream(this);
        } finally {
            level--;
        }
        return level;
    }

    public static void main(String[] args) {
        final Sleeper sleeper = new Sleeper();
        if (sleeper.enter(new Dream()) != 0) {
            // The goal is to reach this line
            System.out.println("Am I still dreaming?");
        }
    }
}
