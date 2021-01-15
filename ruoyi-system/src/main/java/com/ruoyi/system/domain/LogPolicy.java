package com.ruoyi.system.domain;

/**
 * @author xumin
 * 日志
 * @create 2020-12-21 16:32
 */

public class LogPolicy {

    /**
     * jobId
     */
    private Long jobId;

    /**
     * 审计日志周期
     */
    private String relatively;

    /**
     * 审计日志周期阈值
     */
    private String relativelyAll;

    public String getRelatively() {
        return relatively;
    }

    public void setRelatively(String relatively) {
        this.relatively = relatively;
    }

    public String getRelativelyAll() {
        return relativelyAll;
    }

    public void setRelativelyAll(String relativelyAll) {
        this.relativelyAll = relativelyAll;
    }

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }
}
