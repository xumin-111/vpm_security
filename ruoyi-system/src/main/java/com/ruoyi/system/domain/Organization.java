package com.ruoyi.system.domain;

/**
 * @author xumin
 * @create 2020-12-21 16:12
 */

public class Organization {

    /**
     * 组织编号
     */
    private String departmentNumber;

    /**
     * 父组织编号
     */
    private String parentDepartmentNumber;

    public String getDepartmentNumber() {
        return departmentNumber;
    }

    public void setDepartmentNumber(String departmentNumber) {
        this.departmentNumber = departmentNumber;
    }

    public String getParentDepartmentNumber() {
        return parentDepartmentNumber;
    }

    public void setParentDepartmentNumber(String parentDepartmentNumber) {
        this.parentDepartmentNumber = parentDepartmentNumber;
    }
}
