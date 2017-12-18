package com.tora.benchmarks;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntBinaryOperator;

/**
 * @author Ovidiu Maja<ovi.dan89@gmail.com>
 * @version Dec 02, 2017
 */
@State(Scope.Thread)
@BenchmarkMode(Mode.Throughput)
@Measurement(batchSize = 100_000, iterations = 20, timeUnit = TimeUnit.NANOSECONDS)
@Warmup(batchSize = 100_000, iterations = 10, timeUnit = TimeUnit.NANOSECONDS)
@Fork(2)
public class MethodInvocation {
    private AtomicInteger counter = new AtomicInteger();

    private Method methodToCall;
    private IntBinaryOperator lambda;
    private MethodHandle methodHandle;

    @Setup
    public void setUp() throws NoSuchMethodException, IllegalAccessException {
        lambda = this::method;
        methodToCall = MethodInvocation.class.getDeclaredMethod("method", int.class, int.class);
        methodHandle = MethodHandles.lookup().unreflect(methodToCall);
    }

    @Benchmark
    public int directCall() {
        return method(counter.get(), counter.incrementAndGet());
    }

    @Benchmark
    public Object reflection() throws InvocationTargetException, IllegalAccessException {
        return methodToCall.invoke(this, counter.get(), counter.incrementAndGet());
    }

    @Benchmark
    public int lambda() {
        return lambda.applyAsInt(counter.get(), counter.incrementAndGet());
    }

    @Benchmark
    public int methodHandle() throws Throwable {
        return (int) methodHandle.invokeExact(this, counter.get(), counter.incrementAndGet());
    }

    private int method(int a, int b) {
        return a < b ? a : b;
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
                .include(MethodInvocation.class.getSimpleName() + ".*")
//                .jvmArgs("-Xms3048m", "-Xmx3048m", "-XX:+UseG1GC", "-XX:-OptimizeStringConcat")
//                .addProfiler(HotspotMemoryProfiler.class)
//                .forks(1)
                .build();

        new Runner(opt).run();
    }
}
