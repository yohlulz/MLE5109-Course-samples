package com.tora.instrumented;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

public class Benchmarks {
    private static final Collection<String> c = Arrays.asList("Snap", "Crackle", "Pop");

    @State(Scope.Benchmark)
    public static class InheritanceState {
        InstrumentedHashSet<String> set = new InstrumentedHashSet<>();
    }

    @State(Scope.Benchmark)
    public static class CompositionState {
        InstrumentedSet<String> set = new InstrumentedSet<>(new HashSet<>());

    }

    @Benchmark
//    @BenchmarkMode(Mode.Throughput)
//    @OutputTimeUnit(TimeUnit.SECONDS)
//    @Warmup(iterations = 5, time = 2, timeUnit = TimeUnit.SECONDS)
//    @Measurement(iterations = 5, time = 2, timeUnit = TimeUnit.SECONDS, batchSize=100)
//    @Threads(4)
    public void add_Inheritance(InheritanceState state) {
        state.set.addAll(c);

    }

    @Benchmark
    public void add_Composition(CompositionState state) {
        state.set.addAll(c);
    }

    /*
     * ============================== HOW TO RUN THIS TEST: ====================================
     *
     *
     * You can run this test:
     *
     * a) Via the command line:
     *    $ mvn clean install
     *    $ java -jar target/benchmarks.jar JMHSample_26 -f 1
     *
     * b) Via the Java API:
     *    (see the JMH homepage for possible caveats when running from IDE:
     *      http://openjdk.java.net/projects/code-tools/jmh/)
     */

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(Benchmarks.class.getSimpleName())
//                .addProfiler(HotspotMemoryProfiler.class)
                .forks(1)
                .build();

        new Runner(opt).run();
    }
}
