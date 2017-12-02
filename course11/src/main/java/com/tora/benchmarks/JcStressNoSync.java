package com.tora.benchmarks;

import org.openjdk.jcstress.annotations.*;
import org.openjdk.jcstress.infra.results.I_Result;

import static org.openjdk.jcstress.annotations.Expect.ACCEPTABLE;
import static org.openjdk.jcstress.annotations.Expect.ACCEPTABLE_INTERESTING;

/**
 * @author Ovidiu Maja<ovi.dan89@gmail.com>
 * @version Nov 30, 2017
 */
@JCStressTest
@Outcome(id = "1", expect = ACCEPTABLE_INTERESTING, desc = "One update lost")
@Outcome(id = "2", expect = ACCEPTABLE,  desc = "Both updates")
@State
public class JcStressNoSync {
    private int value;

    @Actor
    public void actor1() {
        value++;
    }

    @Actor
    public void actor2() {
        value++;
    }

    @Arbiter
    public void arbiter(I_Result r) {
        r.r1 = value;
    }
}