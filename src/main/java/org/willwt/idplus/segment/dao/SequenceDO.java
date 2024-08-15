package org.willwt.idplus.segment.dao;

import java.util.Date;

/**
 * 序列DO
 * @author willwt
 * @date 2023/08/10 23:29
 */
public class SequenceDO {

    private Date gmtCreate;
    private Date gmtModified;


    /** 业务代码 */
    private String bizCode;

    /** 当前值 */
    private Long curValue;

    /** 增长步长 */
    private Integer step;

    /** 更新时间 */
    private Date updateTime;



    public String getBizCode() {
        return bizCode;
    }

    public void setBizCode(String bizCode) {
        this.bizCode = bizCode;
    }

    public Long getCurValue() {
        return curValue;
    }

    public void setCurValue(Long curValue) {
        this.curValue = curValue;
    }

    public Integer getStep() {
        return step;
    }

    public void setStep(Integer step) {
        this.step = step;
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
                "gmtCreate=" + gmtCreate +
                ", gmtModified=" + gmtModified +
                ", bizCode='" + bizCode + '\'' +
                ", curValue=" + curValue +
                ", step=" + step +
                ", updateTime=" + updateTime +
                '}';
    }
}
