/**
 * LimitApproveService.java
 *
 * Copyright(c) Tora Holdings Limited 2001 - 2017
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * Tora Holdings Limited. Your rights, if any, with respect to the
 * software are governed by your license agreement with Tora.
 */
package com.tora.model;

import com.tora.model.messages.Message;
import com.tora.model.messages.MessageDataModel;
import com.tora.model.messages.RecoveryMessage;
import com.tora.model.util.Utils;
import org.apache.log4j.Logger;

import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author Ovidiu Maja <ovidiu.maja@tora.com>
 * @version Dec 08, 2017
 */
public class Service implements AutoCloseable {
    private final Logger LOGGER = Logger.getLogger(Service.class);
    private final Collection<String> groups;
    protected final PendingRequests pendingRequests;
    protected final ReadWriteLock rwDMPersistenceSupraLock = new ReentrantReadWriteLock();

    private final AbstractProcessor<MessageDataModel> messageProcessor;
    private final AbstractProcessor<RecoveryMessage> recoveryMessageProcessor;
    private final ExecutorService threadPool;
    private final CountDownLatch serviceStarted;
    private AtomicBoolean isActive = new AtomicBoolean();

    public Service(Collection<String> groups, int poolSize) {
        this.groups = groups;
        pendingRequests = new PendingRequests(groups, rwDMPersistenceSupraLock);
        threadPool = Executors.newFixedThreadPool(poolSize, new ThreadFactory() {
            private final AtomicLong counter = new AtomicLong();
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "TPWorker" + counter.incrementAndGet());
            }
        });

        messageProcessor = new AbstractProcessor<MessageDataModel>() {
            @Override
            protected void doProcess(MessageDataModel element) {
                pendingRequests.run(() -> processRequest(element), element.getGroup());
            }
        };
        recoveryMessageProcessor = new AbstractProcessor<RecoveryMessage>() {
            @Override
            protected void doProcess(RecoveryMessage element) {
                if (pendingRequests.requestExists(element.getGroup(), element.getId())) {
                    Service.this.process(new MessageDataModel(element.data));
                }
            }
        };
        serviceStarted = new CountDownLatch(1);
        LOGGER.info("Created " + getClass().getSimpleName());
    }

    public void start() {
        if (serviceStarted.getCount() == 0) {
            return;
        }
        LOGGER.info("Starting...");
        /* simulate some work, DO NOT MODIFY this part */
        Utils.feelingLucky();
        recoveryMessageProcessor.activate();
        messageProcessor.activate();
        serviceStarted.countDown();
        isActive.set(true);
        LOGGER.info("Started");
    }

    private void processRequest(MessageDataModel message) {
        pendingRequests.run(() -> {
            if (!pendingRequests.requestExists(message.getGroup(), message.getId()) && !isActive.get()) {
            /* will process message only after recovery is done */
                LOGGER.debug("Dropping request " + message.getId());
                return;
            }
            LOGGER.info("Processed " + message);
        }, message.getGroup());
    }

    public void recover(RecoveryMessage message) {
        pendingRequests.run(() -> recoveryMessageProcessor.process(message), message.getGroup());
    }

    public void process(Message message) {
        threadPool.execute(createProcessTask(message));
    }

    public PendingRequests getPendingRequests() {
        return pendingRequests;
    }

    public Collection<String> getGroups() {
        return groups;
    }

    public void removeMessage(Message message) {
        pendingRequests.run(() -> pendingRequests.removeRequest(message.getGroup(), Collections.singleton(message.getId())), message.getGroup());
    }

    public void savePendingRequest(Message message) {
        pendingRequests.getPendingRequestIds(message.getGroup()).add(message.getId());
        LOGGER.info("pending " + message.getId());
    }

    protected Runnable createProcessTask(Message message) {
        if (message instanceof RecoveryMessage) {
            return () -> {
                savePendingRequest(message);
                recover((RecoveryMessage) message);
            };
        }
        if (message instanceof MessageDataModel) {
            return () -> {
                processRequest((MessageDataModel) message);
                removeMessage(message);
            };
        }
        return null;
    }

    @Override
    public void close() throws Exception {
        threadPool.shutdown();
        isActive.set(false);
    }
}
