package org.willwt.idplus.segment.dao;

/**
 * 序列数据库访问对象的包装器
 * 实现类中要调用序列的DAO，实现序列的查询和更新。
 * @author willwt
 * @date 2023/08/10 00:42
 */
public interface SequenceDAOWrapper {

    /**
     * 获取该类型的序列信息
     * @param bizCode   业务code
     * @return
     */
    SequenceDO getSequence(String bizCode);

    /**
     * 每次以固定的步长更新序列，更新成功，就代表获得序列下一段区间的序号值。
     * 这里需要使用数据库的乐观锁来避免并发更新。
     * @param bizCode  业务code
     * @param curValue 当前序列值
     * @return
     */
    boolean increaseSequence(String bizCode, long curValue);

}
