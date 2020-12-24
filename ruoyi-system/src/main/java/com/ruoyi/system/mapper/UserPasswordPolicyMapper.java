package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.UserPasswordPolicy;

import java.util.Date;

public interface UserPasswordPolicyMapper {
    void updateUserPasswordPolicy(UserPasswordPolicy userPasswordPolicy);

    Date selectExpireDateByUserId(Long userId);
}
