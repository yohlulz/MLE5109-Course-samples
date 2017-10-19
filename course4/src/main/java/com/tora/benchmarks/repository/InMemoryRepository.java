package com.tora.benchmarks.repository;

/**
 * @author Ovidiu Maja<ovi.dan89@gmail.com>
 * @version Oct 18, 2017
 */
public interface InMemoryRepository<T> {
    boolean add(T e);

    boolean contains(T e);

    boolean remove(T e);

    void clear();
}
