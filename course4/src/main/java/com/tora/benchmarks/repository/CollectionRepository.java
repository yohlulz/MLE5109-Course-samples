package com.tora.benchmarks.repository;

import java.util.Collection;
import java.util.function.Supplier;

/**
 * @author Ovidiu Maja<ovi.dan89@gmail.com>
 * @version Oct 18, 2017
 */
public class CollectionRepository<T> implements InMemoryRepository<T> {

    private final Collection<T> delegate;

    public CollectionRepository(Supplier<? extends Collection<T>> supplier) {
        this.delegate = supplier.get();
    }

    @Override
    public boolean add(T e) {
        return delegate.add(e);
    }

    @Override
    public boolean contains(T e) {
        return delegate.contains(e);
    }

    @Override
    public boolean remove(T e) {
        return delegate.remove(e);
    }

    @Override
    public void clear() {
        delegate.clear();
    }

    public int size() {
        return delegate.size();
    }

    @Override
    public String toString() {
        return String.valueOf(delegate);
    }

    protected Collection<T> getDelegate() {
        return delegate;
    }
}
