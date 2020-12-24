package com.ruoyi.system.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.annotation.Excel.ColumnType;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 密码策略实体类
 * sys_password_policy
 */
public class SysPasswordPolicy extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**主键 */
    @Excel(name = "字典主键", cellType = ColumnType.NUMERIC)
    private Long id;

    /** 密码最大长度 */
    @Excel(name = "密码最大长度")
    private Integer maxPsLength;

    /** 密码最小长度 */
    @Excel(name = "密码最小长度")
    private Integer minPsLength;

    /** 密码更换周期 */
    @Excel(name = "密码更换周期")
    private Integer changePeriod;

    /** 密码允许错误次数 */
    @Excel(name = "密码允许错误次数")
    private Integer allowWrongCount;

    /** 密码复杂程度 */
    @Excel(name = "密码复杂程度")
    private Integer complexity;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getMaxPsLength() {
        return maxPsLength;
    }

    public void setMaxPsLength(Integer maxPsLength) {
        this.maxPsLength = maxPsLength;
    }

    public Integer getMinPsLength() {
        return minPsLength;
    }

    public void setMinPsLength(Integer minPsLength) {
        this.minPsLength = minPsLength;
    }

    public Integer getChangePeriod() {
        return changePeriod;
    }

    public void setChangePeriod(Integer changePeriod) {
        this.changePeriod = changePeriod;
    }

    public Integer getAllowWrongCount() {
        return allowWrongCount;
    }

    public void setAllowWrongCount(Integer allowWrongCount) {
        this.allowWrongCount = allowWrongCount;
    }

    public Integer getComplexity() {
        return complexity;
    }

    public void setComplexity(Integer complexity) {
        this.complexity = complexity;
    }

/*    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("dictId", getDictId())
                .append("dictName", getDictName())
                .append("dictType", getDictType())
                .append("status", getStatus())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("remark", getRemark())
                .toString();
    }*/
}
