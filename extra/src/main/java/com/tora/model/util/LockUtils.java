/**
 * LockUtils.java
 *
 * Copyright(c) Tora Holdings Limited 2001 - 2017
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * Tora Holdings Limited. Your rights, if any, with respect to the
 * software are governed by your license agreement with Tora.
 */
package com.tora.model.util;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;

/**
 * @author Ovidiu Maja <ovidiu.maja@tora.com>
 * @version Dec 05, 2017
 */
public class LockUtils {

    private LockUtils() {
        /* utility class */
    }

    public static boolean lockIfExists(final Lock lock) {
        if (null == lock) {
            return false;
        }
        lock.lock();
        return true;
    }

    public static boolean unlockIfExists(final Lock lock) {
        if (null == lock) {
            return false;
        }
        lock.unlock();
        return true;
    }

    public static boolean lockIfExists(final ReadWriteLock rwLock, boolean exclusive) {
        if (null == rwLock) {
            return false;
        }
        final Lock lock = exclusive ? rwLock.writeLock() : rwLock.readLock();
        return lockIfExists(lock);
    }

    public static boolean unlockIfExists(final ReadWriteLock rwLock, boolean exclusive) {
        if (null == rwLock) {
            return false;
        }
        final Lock lock = exclusive ? rwLock.writeLock() : rwLock.readLock();
        return unlockIfExists(lock);
    }
}
