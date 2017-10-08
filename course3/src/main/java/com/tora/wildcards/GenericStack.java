package com.tora.wildcards;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import java.util.Collection;
import java.util.Stack;

public class GenericStack<E> extends Stack<E> {
    public void pushAll(Iterable<? extends E> src) {
        for (E e : src)
            push(e);
    }

    public void popAll(Collection<? super E> dst) {
        while (!isEmpty())
            dst.add(pop());
    }

    public static void main(String[] args) {
        GenericStack<Number> numberStack = new GenericStack<>();
        Collection<Object> objects = Lists.newArrayList();
        Iterable<Integer> integers = Sets.newHashSet(1, 2, 3, 4, 5);

        numberStack.pushAll(integers);
        numberStack.popAll(objects);

        System.out.println(objects);
    }
}
