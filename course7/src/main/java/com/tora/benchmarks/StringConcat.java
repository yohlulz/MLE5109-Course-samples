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
@Measurement(batchSize = 100, iterations = 20, timeUnit = TimeUnit.NANOSECONDS)
@Warmup(batchSize = 100, iterations = 10, timeUnit = TimeUnit.NANOSECONDS)
@Fork(2)
public class StringConcat {
    private String string;
    private String stringConcat;
    private StringBuilder stringBuilder;
    private StringBuffer stringBuffer;

    @Setup(Level.Iteration)
    public void setup() {
        string = "";
        stringConcat = "";
        stringBuilder = new StringBuilder();
        stringBuffer = new StringBuffer();

        System.gc();
    }

    @Benchmark
    public void plus(Blackhole consumer) {
        string += "some more data";
        consumer.consume(string);
    }

    @Benchmark
    public void concat(Blackhole consumer) {
        stringConcat = stringConcat.concat("some more data");
        consumer.consume(stringConcat);
    }

    @Benchmark
    public StringBuilder stringBuilder() {
        return stringBuilder.append("some more data");
    }

    @Benchmark
    public StringBuffer stringBuffer() {
        return stringBuffer.append("some more data");
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
                .include(StringConcat.class.getSimpleName()+".*")
                .jvmArgs("-Xms3048m", "-Xmx3048m", "-XX:+UseG1GC", "-XX:-OptimizeStringConcat")
//                .addProfiler(HotspotMemoryProfiler.class)
//                .forks(1)
                .build();

        new Runner(opt).run();
    }
}

