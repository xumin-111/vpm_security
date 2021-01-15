package com.ruoyi.system.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import java.util.Date;

public class UserPasswordPolicy extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 用户id 主键 */
    @Excel(name = "用户id")
    private Long userId;

    @Excel(name = "过期时间")
    private Date lastPasswordUpdate;

    @Excel(name = "锁定事件")
    private Date lockTime;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getLastPasswordUpdate() {
        return lastPasswordUpdate;
    }

    public void setLastPasswordUpdate(Date lastPasswordUpdate) {
        this.lastPasswordUpdate = lastPasswordUpdate;
    }

    public Date getLockTime() {
        return lockTime;
    }

    public void setLockTime(Date lockTime) {
        this.lockTime = lockTime;
    }
}
