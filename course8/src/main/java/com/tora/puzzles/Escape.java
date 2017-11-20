package com.tora.puzzles;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Ovidiu Maja<ovi.dan89@gmail.com>
 * @version Nov 17, 2017
 */
public class Escape {
    private Collection<String> entries = new ArrayList<>();

    Escape(Leaker leaker) {
        leaker.onValue(this);
        entries.add("secret");
    }

    public Collection<String> getEntries() {
        return entries;
    }

    public static void main(String[] args) {
        new Escape(new Leaker());
    }

    private static class Leaker {
        void onValue(Escape escape) {
            new Thread(() -> System.out.println(escape.getEntries())).start();
            try {
                /* simulate some work */
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
