package com.ruoyi.web.controller.notice;

import com.ruoyi.framework.util.ShiroUtils;
import com.ruoyi.system.domain.SysPasswordPolicy;
import com.ruoyi.system.domain.SysUser;
import com.ruoyi.system.service.ISysPasswordPolicyService;
import com.ruoyi.system.service.IUserPasswordPolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;

@Controller
@RequestMapping("/app")
public class NoticeController {

    @Autowired
    private ISysPasswordPolicyService iSysPasswordPolicyService;

    @Autowired
    private IUserPasswordPolicyService userPasswordPolicyService;

    @PostMapping("/changePassword")
    @ResponseBody
    public String changePasswordNotice()
    {
        try {
            SysUser user = ShiroUtils.getSysUser();
            SysPasswordPolicy passwordPolicy = iSysPasswordPolicyService.checkPasswordPolicyUnique(null);
            Integer changePeriod = passwordPolicy.getChangePeriod();
            Date expireDate = userPasswordPolicyService.selectExpireDateByUserId(user.getUserId());
            Date date = new Date();
            if(expireDate != null){
                LocalDateTime localDateTime = new Timestamp(expireDate.getTime()).toLocalDateTime();
                if(LocalDateTime.now().isAfter(localDateTime)){
                    Duration duration = Duration.between(localDateTime, LocalDateTime.now());
                    long days = duration.toDays()+changePeriod;
                    String msg = days+"天未修改密码，请修改";
                    return msg;
                }else{
                    Duration duration = Duration.between(localDateTime, LocalDateTime.now());
                    long days = duration.toDays();
                    if(days <= 2){
                        String msg = changePeriod+"天未修改密码，请修改";
                        return msg;
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  null;
    }
}
