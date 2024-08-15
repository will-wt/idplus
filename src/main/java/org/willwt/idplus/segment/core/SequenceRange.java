package org.willwt.idplus.segment.core;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 序列区间
 * @author willwt
 * @date 2023/08/10 00:41
 */
public class SequenceRange {

    /** 最小值 */
    private final long minValue;
    /** 最大值 */
    private final long maxValue;
    /** 当前值 */
    private final AtomicLong curValue;
    /** 本区间的序号是否已用完 */
    private volatile boolean useOver = false;


    public SequenceRange(long minValue, long maxValue) {
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.curValue = new AtomicLong(minValue);
    }

    public long getAndIncrement(){
        long id = curValue.getAndIncrement();
        if (id > maxValue){
            useOver = true;
            return -1;
        }

        return id;
    }

    public long getMinValue() {
        return minValue;
    }

    public long getMaxValue() {
        return maxValue;
    }

    public boolean isUseOver() {
        return useOver;
    }

    @Override
    public String toString() {
        return "SequenceRange{" +
                "minValue=" + minValue +
                ", maxValue=" + maxValue +
                ", curValue=" + curValue +
                ", useOver=" + useOver +
                '}';
    }
}
