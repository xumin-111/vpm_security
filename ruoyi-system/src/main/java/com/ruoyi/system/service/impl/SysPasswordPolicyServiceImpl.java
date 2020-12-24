package com.ruoyi.system.service.impl;

import com.ruoyi.system.domain.SysPasswordPolicy;
import com.ruoyi.system.mapper.SysPasswordPolicyMapper;
import com.ruoyi.system.service.ISysPasswordPolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysPasswordPolicyServiceImpl implements ISysPasswordPolicyService {

    @Autowired
    private SysPasswordPolicyMapper sysPasswordPolicyMapper;

    @Override
    public Integer getMaxRetryCount() {
        return sysPasswordPolicyMapper.selectMaxRetryCount();
    }

    @Override
    public SysPasswordPolicy checkPasswordPolicyUnique(SysPasswordPolicy policy) {
        return sysPasswordPolicyMapper.selectAll();
    }

    @Override
    public int updatePasswordPolicy(SysPasswordPolicy policy) {
        return  sysPasswordPolicyMapper.updatePasswordPolicy(policy);
    }

    @Override
    public int insertPasswordPolicy(SysPasswordPolicy policy) {
        return sysPasswordPolicyMapper.insertPasswordPolicy(policy);
    }
}
