package com.ruoyi.system.domain;

/**
 * @author xumin
 * 日志
 * @create 2020-12-21 16:32
 */

public class Log {

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
}
