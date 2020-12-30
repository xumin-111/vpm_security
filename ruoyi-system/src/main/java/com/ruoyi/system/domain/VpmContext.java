package com.ruoyi.system.domain;

import java.io.Serializable;
import java.util.List;

/**
 * @author xumin
 * @create 2020-12-22 10:09
 */

public class VpmContext implements Serializable {

    /**
     * 上下文ID
     */
    private String contextId;

    /**
     * 上下文名称
     */
    private String contextName;

    /**
     * 项目编号
     */
    private String contextProject;

    /**
     *角色
     */
    private String contextRole;

    /**
     * 组织
     */
    private String contextOrganization;

    /**
     * 增加人员
     */
    private List<String> addPersonList;

    /**
     * 移除人员
     */
    private List<String> removePersonList;

    /**
     * 权限id（行号）
     */
    private String accessId;


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

    public String getContextProject() {
        return contextProject;
    }

    public void setContextProject(String contextProject) {
        this.contextProject = contextProject;
    }

    public String getContextRole() {
        return contextRole;
    }

    public void setContextRole(String contextRole) {
        this.contextRole = contextRole;
    }

    public String getContextOrganization() {
        return contextOrganization;
    }

    public void setContextOrganization(String contextOrganization) {
        this.contextOrganization = contextOrganization;
    }

    public List<String> getAddPersonList() {
        return addPersonList;
    }

    public void setAddPersonList(List<String> addPersonList) {
        this.addPersonList = addPersonList;
    }

    public List<String> getRemovePersonList() {
        return removePersonList;
    }

    public void setRemovePersonList(List<String> removePersonList) {
        this.removePersonList = removePersonList;
    }

    public String getAccessId() {
        return accessId;
    }

    public void setAccessId(String accessId) {
        this.accessId = accessId;
    }

}
