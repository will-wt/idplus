package org.willwt.idplus;

import cn.hutool.core.lang.Assert;
import org.willwt.sequence.core.DefaultSequenceGenerator;
import org.willwt.sequence.core.SequenceGenerator;
import org.willwt.sequence.dao.SequenceDAOWrapper;

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

    /** 序列生成器缓存 */
    private static Map<String, SequenceGenerator> GENERATOR_MAP = new ConcurrentHashMap<>(12);
    private static Lock lock = new ReentrantLock();

    private SequenceDAOWrapper sequenceDAOWrapper;
    private Integer retryTimes;


    /**
     * 获取序列的下一个ID
     * @param name
     * @return
     */
    public long nextId(String name){
        SequenceGenerator generator = GENERATOR_MAP.get(name);

        if (generator == null){
            lock.lock();
            try {
                // 二次确认是否为null
                generator = GENERATOR_MAP.get(name);

                if (generator == null){
                    Assert.notNull(sequenceDAOWrapper, "SequenceDAO instance not found");
                    generator = new DefaultSequenceGenerator();
                    generator.setName(name);
                    generator.setSequenceDAO(sequenceDAOWrapper);
                    if (retryTimes != null){
                        generator.setRetryTimes(retryTimes);
                    }
                    GENERATOR_MAP.putIfAbsent(name, generator);
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
