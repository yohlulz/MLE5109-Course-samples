package com.tora.puzzles;

/**
 * @author Ovidiu Maja<ovi.dan89@gmail.com>
 * @version Nov 19, 2017
 */
public class Foo {
    public static void main(String args[]) {
        new Thread(A::initMe).start();
        B.initMe();
    }

    private static class A {
        private static final B b = new B();

        static void initMe() {
        }
    }

    private static class B {
        private static final A a = new A();

        static void initMe() {
        }
    }
}
