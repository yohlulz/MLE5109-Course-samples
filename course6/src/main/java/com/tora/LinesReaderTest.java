package com.tora;


import java.util.Arrays;

import static com.tora.Utils.PATHS;
import static com.tora.Utils.executeWithDuration;

/**
 * @author Ovidiu Maja<ovi.dan89@gmail.com>
 * @version Nov 03, 2017
 */
public class LinesReaderTest {
    public static void main(String[] args) throws InterruptedException {
        Arrays.stream(PathConsumer.values())
                .forEach(consumer -> executeWithDuration(consumer, PATHS.stream()));
    }
}
