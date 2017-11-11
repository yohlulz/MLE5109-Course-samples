package com.tora.benchmarks;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

/**
 * @author Ovidiu Maja<ovi.dan89@gmail.com>
 * @version Nov 10, 2017
 */
@State(Scope.Thread)
@BenchmarkMode(Mode.Throughput)
@Measurement(iterations = 20, timeUnit = TimeUnit.NANOSECONDS)
@Warmup(iterations = 10, timeUnit = TimeUnit.NANOSECONDS)
@Fork(2)
public class StringSplit {
    private String text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Cras tristique nulla quis lectus feugiat, vitae aliquet felis ultricies. Praesent vitae lacus tempor, blandit est nec, aliquet velit. Pellentesque eros velit, consectetur ac ex nec, tempus tincidunt turpis. Maecenas pellentesque sed lectus ut egestas. Fusce nec congue enim. Vestibulum vestibulum iaculis nulla vitae hendrerit. Nunc viverra metus nec libero sodales mattis. Donec auctor suscipit nisl. Mauris velit ligula, tincidunt eu tellus eget, vestibulum tempus lectus. Cras sodales euismod lectus sit amet facilisis. Donec in ipsum at lacus vulputate finibus. Nam quis rutrum tortor. Integer lectus urna, finibus quis nisl hendrerit, fringilla scelerisque odio. Integer gravida ac eros at fringilla. Donec congue tempor ex, vitae vulputate dolor dignissim et. Suspendisse cursus, dolor at venenatis aliquam, dui lectus finibus elit, eget ornare augue urna pulvinar lacus.";
    private String textDuplicateSpace = text.replaceAll(" ", "  ");
    private Pattern pattern = Pattern.compile(" ");

    @Benchmark
    public String[] split() {
        return text.split(" ");
    }

    @Benchmark
    public String[] splitMultiple() {
        return textDuplicateSpace.split( "  ");
    }

    @Benchmark
    public String[] splitPattern() {
        return pattern.split(text);
    }

    /*
 * ============================== HOW TO RUN THIS TEST: ====================================
 *
 *
 * You can run this test:
 *
 * a) Via the command line:
 *    $ mvn clean install
 *    $ java -jar target/benchmarks.jar StringSplit
 *
 * b) Via the Java API:
 *    (see the JMH homepage for possible caveats when running from IDE:
 *      http://openjdk.java.net/projects/code-tools/jmh/)
 */
    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(StringSplit.class.getSimpleName()+".*")
                .jvmArgs("-Xms3048m", "-Xmx3048m", "-XX:+UseG1GC", "-XX:-OptimizeStringConcat")
//                .addProfiler(HotspotMemoryProfiler.class)
//                .forks(1)
                .build();

        new Runner(opt).run();
    }
}
