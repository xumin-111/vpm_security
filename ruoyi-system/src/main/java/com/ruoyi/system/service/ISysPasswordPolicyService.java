package com.ruoyi.system.service;

import com.ruoyi.system.domain.SysPasswordPolicy;

public interface ISysPasswordPolicyService {
    Integer getMaxRetryCount();

    SysPasswordPolicy checkPasswordPolicyUnique(SysPasswordPolicy policy);

    int updatePasswordPolicy(SysPasswordPolicy policy);

    int insertPasswordPolicy(SysPasswordPolicy policy);
}
