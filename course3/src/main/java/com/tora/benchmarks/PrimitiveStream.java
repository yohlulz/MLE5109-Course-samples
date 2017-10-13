package com.tora.benchmarks;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;
import java.util.stream.Stream;


@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 10, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 20, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(1)
@State(Scope.Benchmark)
public class PrimitiveStream {
    @Param({"1", "31", "65", "101", "103", "1024", "10240", "65535", "21474836"})
    public int size;

    @Benchmark
    public void primitive(Blackhole consumer) {
        consumer.consume(IntStream.rangeClosed(0, size)
                .map(i -> i + 1)
                .max());
    }

    @Benchmark
    public void primitive_parallel(Blackhole consumer) {
        consumer.consume(IntStream.rangeClosed(0, size)
                .map(i -> i + 1)
                .parallel()
                .max());
    }

    @Benchmark
    public void boxing(Blackhole consumer) {
        consumer.consume(Stream.iterate(0, i -> i + 1)
                .limit(size)
                .max(Integer::compareTo));
    }

    @Benchmark
    public void boxing_parallel(Blackhole consumer) {
        consumer.consume(Stream.iterate(0, i -> i + 1)
                .limit(size)
                .parallel()
                .max(Integer::compareTo));
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
                .include(PrimitiveStream.class.getSimpleName())
//                .addProfiler(HotspotMemoryProfiler.class)
                .forks(1)
                .build();

        new Runner(opt).run();
    }
}
