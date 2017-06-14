package com.denis.learning.management;

import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;
import java.util.concurrent.atomic.AtomicLong;

public class ByteWatcherSingleThread {
    private static final String ALLOCATED = " allocated ";
    private static final String GET_THREAD_ALLOCATED_BYTES = "getThreadAllocatedBytes";
    private static final String[] SIGNATURE = new String[]{long.class.getName()};
    private static final MBeanServer mBeanServer;
    private static final ObjectName name;

    private final String threadName;
    private final Thread thread;

    private final Object[] PARAMS;
    private final AtomicLong allocated = new AtomicLong();
    private final long MEASURING_COST_IN_BYTES; // usually 336
    private final long tid;
    private final boolean checkThreadSafety;

    static {
        try {
            name = new ObjectName(ManagementFactory.THREAD_MXBEAN_NAME);
            mBeanServer = ManagementFactory.getPlatformMBeanServer();
        } catch (MalformedObjectNameException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public ByteWatcherSingleThread() {
        this(Thread.currentThread(), true);
    }

    public ByteWatcherSingleThread(Thread thread) {
        this(thread, false);
    }

    private ByteWatcherSingleThread(Thread thread, boolean checkThreadSafety) {
        this.checkThreadSafety = checkThreadSafety;
        this.tid = thread.getId();
        this.thread = thread;
        this.threadName = thread.getName();
        this.PARAMS = new Object[]{tid};

        long calibrate = threadAllocatedBytes();
        //calibrate
        for (int repeats = 0; repeats < 10; repeats++) {
            for (int i = 0; i < 10_000; i++) {
                // run a few loops to allow for startup anomalies
                calibrate = threadAllocatedBytes();
            }

            sleep();
        }

        MEASURING_COST_IN_BYTES = threadAllocatedBytes() - calibrate;
        reset();
    }

    public long getMeasuringCostInBytes() {
        return MEASURING_COST_IN_BYTES;
    }

    /**
     * Calculates the number of bytes allocated since the last
     * reset().
     */
    public long calculateAllocations() {
        checkThreadSafety();
        return ((threadAllocatedBytes() - MEASURING_COST_IN_BYTES) - allocated.get());
    }

    void reset() {
        checkThreadSafety();

        allocated.set(threadAllocatedBytes());
    }

    private void sleep() {
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    long threadAllocatedBytes() {
        try {
            return (long) mBeanServer.invoke(
                    name,
                    GET_THREAD_ALLOCATED_BYTES,
                    PARAMS,
                    SIGNATURE
            );
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    private void checkThreadSafety() {
        if (checkThreadSafety && tid != Thread.currentThread().getId())
            throw new IllegalStateException("AllocationMeasure must not be " +
                            "used over more than 1 thread.");
    }

    public Thread getThread() {
        return thread;
    }

    @Override
    public String toString() {
        return thread.getName() + ALLOCATED + calculateAllocations();
    }

}
