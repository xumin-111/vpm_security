package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.SysPasswordPolicy;

public interface SysPasswordPolicyMapper {
    Integer selectMaxRetryCount();

    SysPasswordPolicy selectAll();

    Integer updatePasswordPolicy(SysPasswordPolicy policy);

    Integer insertPasswordPolicy(SysPasswordPolicy policy);
}
