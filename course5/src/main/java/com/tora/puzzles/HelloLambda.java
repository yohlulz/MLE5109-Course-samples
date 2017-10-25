package com.tora.puzzles;

/**
 * @author Ovidiu Maja<ovi.dan89@gmail.com>
 * @version Oct 25, 2017
 */
class Forest {
    public Runnable wrooom() {
        return () -> System.out.println("Hello, lambda!");
    }
}

class RunForestRun {
    private Runnable r = new Forest()::wrooom;

    public void foo() {
        r.run();
    }
}

public class HelloLambda {
    public static void main(String[] args) {
        new RunForestRun().foo();
    }
}
