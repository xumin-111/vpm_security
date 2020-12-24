package com.ruoyi.system.domain;

/**
 * @author xumin
 * @create 2020-12-22 10:09
 */

public class Access {

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
    private String contextType;

    /**
     * 操作/操作组
     */
    private String contextAction;

    /**
     * 数据组
     */
    private String contextData;

    /**
     * 项目编号
     */
    private String contextProject;

    /**
     *
     */
    private String contextRole;

    /**
     * 数据组
     */
    private String contextOrganization;


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

    public String getContextType() {
        return contextType;
    }

    public void setContextType(String contextType) {
        this.contextType = contextType;
    }

    public String getContextAction() {
        return contextAction;
    }

    public void setContextAction(String contextAction) {
        this.contextAction = contextAction;
    }

    public String getContextData() {
        return contextData;
    }

    public void setContextData(String contextData) {
        this.contextData = contextData;
    }
}
