package com.tora.puzzles;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @author Ovidiu Maja<ovi.dan89@gmail.com>
 * @version Nov 04, 2017
 */
public class ExceptionTest {
    public static void thrower() throws Exception {
        try {
            throw new IOException();
        } finally {
            throw new FileNotFoundException();
        }
    }

    public static void main(String[] args) {
        try {
            thrower();
        } catch (Throwable throwable) {
            System.out.println(throwable);
        }
    }
}
