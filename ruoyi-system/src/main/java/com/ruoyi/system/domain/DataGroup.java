package com.ruoyi.system.domain;

/**
 * 数据组
 */

import java.io.Serializable;

public class DataGroup implements Serializable {
    private String dataName;

    private String dataValue;

    public String getDataName() {
        return dataName;
    }

    public void setDataName(String dataName) {
        this.dataName = dataName;
    }

    public String getDataValue() {
        return dataValue;
    }

    public void setDataValue(String dataValue) {
        this.dataValue = dataValue;
    }
}
