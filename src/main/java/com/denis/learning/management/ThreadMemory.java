package com.denis.learning.management;

import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.ReflectionException;
import java.lang.management.ManagementFactory;

public class ThreadMemory {
    public static long threadAllocatedBytes() {
        try {
            MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();

            Object allocatedBytes = mBeanServer.invoke(
                    new ObjectName(ManagementFactory.THREAD_MXBEAN_NAME),
                    "getThreadAllocatedBytes",
                    new Object[] {Thread.currentThread().getId()},
                    new String[] {long.class.getName()}
            );

            return (long) allocatedBytes;
        }
        catch (InstanceNotFoundException | MBeanException | ReflectionException | MalformedObjectNameException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
