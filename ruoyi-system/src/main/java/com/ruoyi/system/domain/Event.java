package com.ruoyi.system.domain;

/**
 * @author xumin
 * @create 2020-12-22 13:27
 */

public class Event {

    /**
     * 用户帐号
     */
    private String userName;

    /**
     * 用户密级
     */
    private String userSecret;

    /**
     * 操作ip
     */
    private String ip;

    /**
     * 操作对象
     */
    private String object;

    /**
     * 操作类型
     */
    private String type;

    /**
     * 对应等级
     */
    private String level;

    /**
     * 详细描述
     */
    private String description;

    /**
     * 开始时间
     */
    private String beginDate;

    /**
     * 结束时间
     */
    private String endDate;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserSecret() {
        return userSecret;
    }

    public void setUserSecret(String userSecret) {
        this.userSecret = userSecret;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
