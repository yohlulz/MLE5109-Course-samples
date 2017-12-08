/**
 * AbstractProcessor.java
 *
 * Copyright(c) Tora Holdings Limited 2001 - 2017
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * Tora Holdings Limited. Your rights, if any, with respect to the
 * software are governed by your license agreement with Tora.
 */
package com.tora.model;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Accumulates messages until its {@link #activate() activated}.
 * When activated, will process all pending requests and any further ones.
 *
 * @author Ovidiu Maja <ovidiu.maja@tora.com>
 * @version Dec 05, 2017
 */
public abstract class AbstractProcessor<T> {
    private final static int ACCUMULATING = 0;
    private final static int PROCESSING_BACKLOG = 1;
    private final static int PROCESSING = 2;

    private final List<T> storedList;
    private final AtomicInteger state;
    private final Object lock;

    AbstractProcessor() {
        storedList = new LinkedList<>();
        state = new AtomicInteger(ACCUMULATING);
        lock = new Object();
    }

    void process(T element) {
        if (PROCESSING == state.get()) {
            doProcess(element);
            return;
        }

        synchronized (lock) {
            if (PROCESSING == state.get()) {
                doProcess(element);
                return;
            }

            storedList.add(element);
        }
    }

    void activate() {
        synchronized (lock) {
            if (!state.compareAndSet(ACCUMULATING, PROCESSING_BACKLOG)) {
                return;
            }

            try {
                for (T element : storedList) {
                    doProcess(element);
                }
            }
            finally {
                storedList.clear();
                state.set(PROCESSING);
            }
        }
    }

    protected abstract void doProcess(T element);
}
