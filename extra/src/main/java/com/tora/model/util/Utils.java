package com.tora.model.util;

import org.apache.log4j.Logger;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author Ovidiu Maja <ovidiu.maja@tora.com>
 * @version Dec 08, 2017
 */
public class Utils {
    private static final Logger LOGGER = Logger.getLogger(Utils.class);
    public static final long DEFAULT_DURATION = TimeUnit.SECONDS.toMillis(1);
    private static final Random RANDOM = new Random();


    private Utils() {
        /* utility class */
    }

    public static void feelingLucky() {
        simulateWork(RANDOM.nextInt((int)DEFAULT_DURATION));
    }

    public static void simulateWork() {
        simulateWork(DEFAULT_DURATION);
    }

    public static void simulateWork(long millis) {
        try {
            LOGGER.info("working for " + millis + "ms...");
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    
}
