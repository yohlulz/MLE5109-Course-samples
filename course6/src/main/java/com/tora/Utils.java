package com.tora;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.Collection;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * @author Ovidiu Maja<ovi.dan89@gmail.com>
 * @version Nov 03, 2017
 */
public class Utils {
    private Utils() {}

    public static final Path DEV_NULL = Paths.get("/dev", "null");
    public static final Path FILE_1M = Paths.get(System.getProperty("java.io.tmpdir"), "1M");
    public static final Path FILE_10M = Paths.get(System.getProperty("java.io.tmpdir"), "10M");
    public static final Path FILE_100M = Paths.get(System.getProperty("java.io.tmpdir"), "100M");
    public static final Path FILE_200M = Paths.get(System.getProperty("java.io.tmpdir"), "200M");
    public static final Path FILE_1G = Paths.get(System.getProperty("java.io.tmpdir"), "1G");

    public static Collection<Path> PATHS = Arrays.asList(FILE_1M, FILE_10M, FILE_100M, FILE_200M, FILE_1G);


    public static void executeWithDuration(Consumer<Path> consumer, Stream<Path> paths) {
        System.gc();
        paths.parallel().forEach(path -> {
            final Instant start = Instant.now();
            consumer.accept(path);
            final Instant end = Instant.now();
            System.out.println(String.format("%s, %s, duration %s", consumer, path, Duration.between(start, end)));
        });
        System.out.println("===========================");
    }


}
