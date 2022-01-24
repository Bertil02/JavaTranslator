package com.company;

import java.util.concurrent.atomic.AtomicInteger;

public class LabelCounterUtil {

    private static final AtomicInteger ifLabelCounter = new AtomicInteger(0);
    private static final AtomicInteger whileLabelCounter = new AtomicInteger(0);
    private static final AtomicInteger condLabelCounter = new AtomicInteger(0);
    private static final AtomicInteger tmpLabelCounter = new AtomicInteger(0);

    private LabelCounterUtil() {
    }

    public static Integer getIncrementIfLabelCounter() {
        return ifLabelCounter.getAndIncrement();
    }

    public static Integer getIfLabelCounter() {
        return ifLabelCounter.get();
    }

    public static Integer getIncrementWhileLabelCounter() {
        return whileLabelCounter.getAndIncrement();
    }

    public static Integer getIncrementTmpLabelCounter() {
        return tmpLabelCounter.getAndIncrement();
    }

    public static Integer getIncrementCondLabelCounter() {
        return condLabelCounter.getAndIncrement();
    }

    public static Integer getCondLabelCounter() {
        return condLabelCounter.get();
    }

    public static void resetCounters() {
        ifLabelCounter.set(0);
        whileLabelCounter.set(0);
        tmpLabelCounter.set(0);
        condLabelCounter.set(0);
    }
}
