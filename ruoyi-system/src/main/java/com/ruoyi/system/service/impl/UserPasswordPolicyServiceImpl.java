package com.ruoyi.system.service.impl;

import com.ruoyi.system.domain.UserPasswordPolicy;
import com.ruoyi.system.mapper.UserPasswordPolicyMapper;
import com.ruoyi.system.service.IUserPasswordPolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserPasswordPolicyServiceImpl implements IUserPasswordPolicyService {

    @Autowired
    private UserPasswordPolicyMapper userPasswordPolicyMapper;



    @Override
    public void updateUserPasswordPolicy(UserPasswordPolicy userPasswordPolicy) {
        userPasswordPolicyMapper.updateUserPasswordPolicy(userPasswordPolicy);
    }

    @Override
    public Date selectExpireDateByUserId(Long userId) {
        return userPasswordPolicyMapper.selectExpireDateByUserId(userId);
    }
}
