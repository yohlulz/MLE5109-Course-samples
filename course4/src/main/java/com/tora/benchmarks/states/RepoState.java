package com.tora.benchmarks.states;

import com.tora.benchmarks.repository.InMemoryRepository;
import com.tora.benchmarks.repository.Order;
import org.openjdk.jmh.annotations.*;

import java.util.stream.IntStream;

/**
 * @author Ovidiu Maja<ovi.dan89@gmail.com>
 * @version Oct 18, 2017
 */
@State(Scope.Benchmark)
public class RepoState {
    @Param
    private RepositorySupplier repositorySupplier;

    public InMemoryRepository<Order> orders;

    /* run before each benchmark */
    @Setup
    public void setUp(SizeState sizeState) {
        System.out.println(getClass().getSimpleName() + " > setup > populate");
        orders = repositorySupplier.get();

        IntStream.rangeClosed(1, sizeState.size)
                .mapToObj(Order::new)
                .forEach(orders::add);
    }

    /* run after each benchmark */
    @TearDown
    public void tearDown() {
        System.out.println(getClass().getSimpleName() + " > teardown > clear");
        orders.clear();
        System.gc();
    }
}
