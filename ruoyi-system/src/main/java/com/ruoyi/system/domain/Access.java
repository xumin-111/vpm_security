package com.ruoyi.system.domain;

import java.io.Serializable;

/**
 * @author xumin
 * @create 2020-12-22 10:09
 * @desc 权限实体类
 */

public class Access implements Serializable {

    /**
     * 主键id
     */
    private String accessId;

    /**
     * 上下文ID
     */
    private String contextId;

    /**
     * 上下文名称
     */
    private String contextName;

    /**
     * 类型
     */
    private String accessType;

    /**
     * 操作/操作组
     */
    private String actionGroup;

    /**
     * 数据组
     */
    private String dataGroup;


    public String getAccessId() {
        return accessId;
    }

    public void setAccessId(String accessId) {
        this.accessId = accessId;
    }

    public String getContextId() {
        return contextId;
    }

    public void setContextId(String contextId) {
        this.contextId = contextId;
    }

    public String getContextName() {
        return contextName;
    }

    public void setContextName(String contextName) {
        this.contextName = contextName;
    }

    public String getAccessType() {
        return accessType;
    }

    public void setAccessType(String accessType) {
        this.accessType = accessType;
    }

    public String getActionGroup() {
        return actionGroup;
    }

    public void setActionGroup(String actionGroup) {
        this.actionGroup = actionGroup;
    }

    public String getDataGroup() {
        return dataGroup;
    }

    public void setDataGroup(String dataGroup) {
        this.dataGroup = dataGroup;
    }
}
