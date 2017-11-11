package com.tora.puzzles;

/**
 * @author Ovidiu Maja<ovi.dan89@gmail.com>
 * @version Nov 10, 2017
 */
public class SearchString {
    public static void main(String[] args) {
        String quote = "An *onion* a day keeps everyone away!";

        // match the word delimited by *'s
        int startDelimit = quote.indexOf('*');
        int endDelimit = quote.lastIndexOf("*");
        System.out.println(quote.substring(startDelimit, endDelimit));
    }
}
