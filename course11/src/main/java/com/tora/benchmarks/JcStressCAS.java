package com.tora.benchmarks;

import org.openjdk.jcstress.annotations.Actor;
import org.openjdk.jcstress.annotations.JCStressTest;
import org.openjdk.jcstress.annotations.Outcome;
import org.openjdk.jcstress.annotations.State;
import org.openjdk.jcstress.infra.results.II_Result;

import static org.openjdk.jcstress.annotations.Expect.ACCEPTABLE;

/**
 * @author Ovidiu Maja<ovi.dan89@gmail.com>
 * @version Nov 30, 2017
 */
@JCStressTest
@Outcome(id = "5, 2", expect = ACCEPTABLE,  desc = "Actor1 -> Actor2 execution")
@Outcome(id = "1, 10", expect = ACCEPTABLE, desc = "Actor2 -> Actor1 execution")
public class JcStressCAS {

    @State
    public static class Value {
        private int value;
        public boolean compareAndSet(int expectedValue, int newValue) {
            if (value == expectedValue) {
                value = newValue;
                return true;
            }
            return false;
        }
    }
    @Actor
    public void actor1(Value value, II_Result result) {
        result.r1 = value.compareAndSet(0, 5) ? 5 : 1 ;
    }

    @Actor
    public void actor2(Value value, II_Result result) {
        result.r2 = value.compareAndSet(0, 10) ? 10 : 2;
    }
}
