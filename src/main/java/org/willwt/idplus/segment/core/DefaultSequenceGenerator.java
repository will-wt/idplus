package org.willwt.idplus.segment.core;

import org.willwt.idplus.common.AssertUtil;
import org.willwt.idplus.segment.dao.SequenceDAOWrapper;
import org.willwt.idplus.segment.dao.SequenceDO;

import java.util.Objects;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author willwt
 * @date 2023/08/10 00:45
 */
public class DefaultSequenceGenerator implements SequenceGenerator {

    private static final int DEFAULT_RETRY_TIMES = 10;
    private final Lock lock = new ReentrantLock();

    private String name;
    private SequenceDAOWrapper sequenceDAOWrapper;
    private int retryTimes = DEFAULT_RETRY_TIMES;

    /** 待获取的序列区间 */
    private volatile SequenceRange sequenceRange;

    @Override
    public void setBizCode(String bizCode) {
        Objects.requireNonNull(bizCode, "sequence name required");
        this.name = bizCode;
    }

    @Override
    public void setSequenceDAO(SequenceDAOWrapper sequenceDAOWrapper) {
        Objects.requireNonNull(sequenceDAOWrapper, "sequenceDAOWrapper required");
        this.sequenceDAOWrapper = sequenceDAOWrapper;
    }

    @Override
    public void setRetryTimes(int retryTimes) {
        AssertUtil.isTrue(retryTimes > 0, "retryTimes invalid");
        this.retryTimes = retryTimes;
    }

    @Override
    public long nextValue() {
        if (sequenceRange == null){
            lock.lock();
            try {
                if (sequenceRange == null){
                    sequenceRange = fetchNextSequenceRange(name);
                }
            }finally {
                lock.unlock();
            }
        }

        long value = sequenceRange.getAndIncrement();
        if (value == -1){
            lock.lock();
            try {
                if (sequenceRange.isUseOver()){
                    sequenceRange = fetchNextSequenceRange(name);
                }

                value = sequenceRange.getAndIncrement();

            }finally {
                lock.unlock();
            }
        }

        AssertUtil.isTrue(value > 0, "sequence value invalid, value="+ value);
        return value;
    }

    private SequenceRange fetchNextSequenceRange(String name) {
        for (int i=0; i<retryTimes; i++){
            SequenceDO sequenceDO = sequenceDAOWrapper.getSequence(name);
            AssertUtil.notNull(sequenceDO, "sequence name record not exist, name="+ name);
            AssertUtil.isTrue(sequenceDO.getCurValue() != null && sequenceDO.getCurValue() >= 0,
                    "sequence value must greater than 0, name={}, value={}",
                    name, sequenceDO.getCurValue());

            Long curValue = sequenceDO.getCurValue();

            // 更新成功，就获得下一段区间的序列
            boolean updateResult = sequenceDAOWrapper.increaseSequence(name, curValue);
            if (!updateResult){
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {

                }
                continue;
            }

            long minValue = curValue + 1;
            long maxValue = curValue + sequenceDO.getStep();
            return new SequenceRange(minValue, maxValue);
        }

        throw new IllegalArgumentException("retry too many times, name="+ name +", retryTimes="+ retryTimes);
    }

}
