package org.willwt.idplus.segment.core;

import org.willwt.idplus.segment.dao.SequenceDAOWrapper;

/**
 * 序列生成器
 * @author willwt
 * @date 2023/08/10 00:43
 */
public interface SequenceGenerator {

    /**
     * 设置序列名称
     * @param bizCode
     */
    void setBizCode(String bizCode);

    /**
     * 设置更新序列的DAO实现
     * @param sequenceDAOWrapper
     */
    void setSequenceDAO(SequenceDAOWrapper sequenceDAOWrapper);

    /**
     * 设置获取序列区间的重试次数
     * @param retryTimes
     */
    void setRetryTimes(int retryTimes);


    /**
     * 获取序列的下一个值
     * @return
     */
    long nextValue();


}
