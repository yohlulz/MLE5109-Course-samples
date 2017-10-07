package com.tora.instrumented;

import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class TestInstrumentedHashSet {

    @Test
    public void add() {
        InstrumentedHashSet<String> s = new InstrumentedHashSet<>();
        s.addAll(Arrays.asList("Snap", "Crackle", "Pop"));

        assertThat(s.getAddCount(), is(3));
    }
}
