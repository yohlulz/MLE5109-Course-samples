package com.tora.benchmarks.repository;

/**
 * @author Ovidiu Maja<ovi.dan89@gmail.com>
 * @version Oct 18, 2017
 */
public interface InMemoryRepositoryPrimitive {
    void add(long e);

    boolean contains(long e);

    void remove(long e);

    void clear();
}
