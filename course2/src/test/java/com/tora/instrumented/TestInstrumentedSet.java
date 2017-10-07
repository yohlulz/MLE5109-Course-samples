package com.tora.instrumented;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class TestInstrumentedSet {

    @Test
    public void add() {
        final InstrumentedSet<String> s = new InstrumentedSet<>(new HashSet<>());
        s.addAll(Arrays.asList("Snap", "Crackle", "Pop"));

        assertThat(s.getAddCount(), is(3));
    }
}
