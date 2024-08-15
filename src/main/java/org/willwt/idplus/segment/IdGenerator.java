package org.willwt.idplus.segment;

import org.willwt.idplus.common.AssertUtil;
import org.willwt.idplus.segment.core.DefaultSequenceGenerator;
import org.willwt.idplus.segment.core.SequenceGenerator;
import org.willwt.idplus.segment.dao.SequenceDAOWrapper;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ID生成器
 * @author willwt
 * @date 2023/08/10 00:40
 */
public class IdGenerator {

    /** 序列生成器缓存，key：bizCode，value：SequenceGenerator */
    private static Map<String, SequenceGenerator> GENERATOR_MAP = new ConcurrentHashMap<>(12);
    private static Lock lock = new ReentrantLock();

    private SequenceDAOWrapper sequenceDAOWrapper;
    private Integer retryTimes;


    /**
     * 获取序列的下一个ID
     * @param bizCode
     * @return
     */
    public long nextId(String bizCode){
        SequenceGenerator generator = GENERATOR_MAP.get(bizCode);

        if (generator == null){
            lock.lock();
            try {
                // 二次确认是否为null
                generator = GENERATOR_MAP.get(bizCode);

                if (generator == null){
                    AssertUtil.notNull(sequenceDAOWrapper, "SequenceDAO instance not found");
                    generator = new DefaultSequenceGenerator();
                    generator.setBizCode(bizCode);
                    generator.setSequenceDAO(sequenceDAOWrapper);
                    if (retryTimes != null){
                        generator.setRetryTimes(retryTimes);
                    }
                    GENERATOR_MAP.putIfAbsent(bizCode, generator);
                }
            }finally {
                lock.unlock();
            }
        }

        return generator.nextValue();
    }

    public void setSequenceDAO(SequenceDAOWrapper sequenceDAOWrapper) {
        this.sequenceDAOWrapper = sequenceDAOWrapper;
    }

    public void setRetryTimes(Integer retryTimes) {
        this.retryTimes = retryTimes;
    }

}
