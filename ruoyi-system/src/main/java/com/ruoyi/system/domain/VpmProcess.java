package com.ruoyi.system.domain;

import java.io.Serializable;

public class VpmProcess implements Serializable {
    private Integer processId;

    private Integer processGroupId;

    private String processName;


    public Integer getProcessId() {
        return processId;
    }

    public void setProcessId(Integer processId) {
        this.processId = processId;
    }

    public Integer getProcessGroupId() {
        return processGroupId;
    }

    public void setProcessGroupId(Integer processGroupId) {
        this.processGroupId = processGroupId;
    }

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }
}
