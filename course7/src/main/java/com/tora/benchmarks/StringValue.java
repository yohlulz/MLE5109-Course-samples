package com.tora.benchmarks;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

/**
 * @author Ovidiu Maja<ovi.dan89@gmail.com>
 * @version Nov 05, 2017
 */
@State(Scope.Thread)
@BenchmarkMode(Mode.Throughput)
@Measurement(batchSize = 10_000, iterations = 20, timeUnit = TimeUnit.NANOSECONDS)
@Warmup(batchSize = 10_000, iterations = 10, timeUnit = TimeUnit.NANOSECONDS)
@Fork(2)
public class StringValue {
    private int value = 1802;

    @Setup(Level.Iteration)
    public void setup() {
        System.gc();
    }

    @Benchmark
    public String pre() {
        return "" + value;
    }

    @Benchmark
    public String post() {
        return value + "";
    }

    @Benchmark
    public String valueToString() {
        return Integer.toString(value);
    }

    @Benchmark
    public String valueOf() {
        return String.valueOf(value);
    }

    /*
     * ============================== HOW TO RUN THIS TEST: ====================================
     *
     *
     * You can run this test:
     *
     * a) Via the command line:
     *    $ mvn clean install
     *    $ java -jar target/benchmarks.jar StringConcat
     *
     * b) Via the Java API:
     *    (see the JMH homepage for possible caveats when running from IDE:
     *      http://openjdk.java.net/projects/code-tools/jmh/)
     */
    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(StringValue.class.getSimpleName()+".*")
                .jvmArgs("-Xms3048m", "-Xmx3048m", "-XX:+UseG1GC", "-XX:-OptimizeStringConcat")
//                .addProfiler(HotspotMemoryProfiler.class)
//                .forks(1)
                .build();

        new Runner(opt).run();
    }
}

