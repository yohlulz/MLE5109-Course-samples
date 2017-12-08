/**
 * PendingRequests.java
 *
 * Copyright(c) Tora Holdings Limited 2001 - 2017
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * Tora Holdings Limited. Your rights, if any, with respect to the
 * software are governed by your license agreement with Tora.
 */
package com.tora.model;

import com.tora.model.util.LockUtils;
import org.apache.log4j.Logger;

import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Stores pending requests per group.
 * Also offers the ability to run particular tasks per group under lock {@link #run(Runnable, String)}
 *
 * @author Ovidiu Maja <ovidiu.maja@tora.com>
 * @version Dec 05, 2017
 */
public class PendingRequests {
    private static final Logger LOGGER = Logger.getLogger(PendingRequests.class);
    private final Map<String, Lock> groupLocks;
    private final Map<String, Set<String>> requestSourceToRequestID;
    private final ReadWriteLock rwLock;

    PendingRequests(final Collection<String> groups, final ReadWriteLock rwLock) {
        Map<String, Set<String>> requestSourceToRequestID = new HashMap<>();
        Map<String, Lock> groupLocks = new HashMap<>();

        for (String group : groups) {
            groupLocks.put(group, new ReentrantLock());
            requestSourceToRequestID.put(group, new HashSet<>());
        }
        this.requestSourceToRequestID = Collections.unmodifiableMap(requestSourceToRequestID);
        this.groupLocks = Collections.unmodifiableMap(groupLocks);
        this.rwLock = rwLock;
    }

    PendingRequests(Collection<String> groups) {
        this(groups, null);
    }

    public Set<String> getPendingRequestIds(String group) {
        Set<String> result = requestSourceToRequestID.get(group);
        if (null == result) {
            result = new HashSet<>();
            requestSourceToRequestID.put(group, result);
        }
        return result;
    }

    public void removeRequest(String group, Collection<String> requestId) {
        /* lock using the general lock */
        LockUtils.lockIfExists(rwLock, false);
        try {
            Set<String> reqIdsToRemove = new HashSet<String>(requestId);
            /* lock per group/subpart */
            LockUtils.lockIfExists(groupLocks.get(group));
            try {
                requestSourceToRequestID.get(group).removeAll(reqIdsToRemove);

            } finally {
                LockUtils.unlockIfExists(groupLocks.get(group));
            }
        } finally {
            LockUtils.unlockIfExists(rwLock, false);
        }
    }

    public boolean requestExists(String group, String requestId) {
        /* lock using the general lock */
        LockUtils.lockIfExists(rwLock, false);
        try {
            /* lock per group/subpart */
            LockUtils.lockIfExists(groupLocks.get(group));
            boolean result;
            try {
                result = requestSourceToRequestID.get(group).contains(requestId);
            } finally {
                LockUtils.unlockIfExists(groupLocks.get(group));
            }
            return result;
        } finally {
            LockUtils.unlockIfExists(rwLock, false);
        }
    }

    public void run(Runnable r, String group) {
        /* lock using the general lock */
        LockUtils.lockIfExists(rwLock, false);
        try {
            /* lock per group/subpart */
            LockUtils.lockIfExists(groupLocks.get(group));
            try {
                r.run();
            } finally {
                LockUtils.unlockIfExists(groupLocks.get(group));
            }
        } finally {
            LockUtils.unlockIfExists(rwLock, false);
        }
    }
}

