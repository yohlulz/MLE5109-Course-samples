package com.tora.puzzles;

class StackTest {
    public static void main(String[] args) {
        StackTest stackTest = new StackTest();
        stackTest.test(getVal());
    }

    public static <T> T getVal() {
        return (T) new Double(5d);
    }

    public void test(String a) {
        System.out.println("string" + a);
    }

    public void test(Object bla) {
        System.out.println("object " + bla);
    }
}

