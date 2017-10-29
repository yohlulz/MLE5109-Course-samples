package com.tora;

import java.io.*;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

/**
 * @author Ovidiu Maja<ovi.dan89@gmail.com>
 * @version Oct 27, 2017
 */
public class BigDecimalSerialization {
    private static final File storageFile = new File(System.getProperty("java.io.tmpdir"), "serial.data");

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        final long size = 100_000_00;
        final Random random = new Random(size);

        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(storageFile))) {
            out.writeLong(size);
            LongStream.rangeClosed(1, size)
                    .map(x -> random.nextLong())
                    .mapToObj(BigDecimal::valueOf)
                    .forEach(value -> {
                        try {
                            out.writeUnshared(value);
                            out.flush();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
        }

        try (ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(new FileInputStream(storageFile)))) {
            BigDecimal value;
            long recoveredSize = in.readLong();
            while (recoveredSize-- > 0) {
                System.out.println((BigDecimal)in.readUnshared());
            }
        }
    }

}
