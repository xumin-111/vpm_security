package com.ruoyi.system.service;

import com.ruoyi.system.domain.UserPasswordPolicy;

import java.util.Date;

public interface IUserPasswordPolicyService {
    void updateUserPasswordPolicy(UserPasswordPolicy userPasswordPolicy);

    Date selectExpireDateByUserId(Long userId);
}
