package org.willwt.sequence.dao;

import java.util.Date;

/**
 * 序列DO
 * @author willwt
 * @date 2023/08/10 23:29
 */
public class SequenceDO {

    /** 名称 */
    private String name;

    /** 当前值 */
    private Long curValue;

    /** 增长步长 */
    private Integer increment;

    /** 更新时间 */
    private Date updateTime;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCurValue() {
        return curValue;
    }

    public void setCurValue(Long curValue) {
        this.curValue = curValue;
    }

    public Integer getIncrement() {
        return increment;
    }

    public void setIncrement(Integer increment) {
        this.increment = increment;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "SequenceDO{" +
                "name='" + name + '\'' +
                ", curValue=" + curValue +
                ", increment=" + increment +
                ", updateTime=" + updateTime +
                '}';
    }

}
