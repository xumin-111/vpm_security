package com.ruoyi.web.controller.system;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.framework.util.ShiroUtils;
import com.ruoyi.system.domain.SysPasswordPolicy;
import com.ruoyi.system.service.ISysPasswordPolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/system/password")
public class SysPasswordPolicyController extends BaseController {

    @Autowired
    private ISysPasswordPolicyService iSysPasswordPolicyService;

    /**
     * 修改或新增密码策略
     */
    @Log(title = "密码策略", businessType = BusinessType.INSERT)
    /*@RequiresPermissions("system:dict:add")*/
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(@Validated SysPasswordPolicy  policy)
    {
        SysPasswordPolicy passwordPolicy = iSysPasswordPolicyService.checkPasswordPolicyUnique(policy);
        if (passwordPolicy != null)
        {
            //return error("新增字典'" + policy.getDictName() + "'失败，字典类型已存在");
            //修改密码策略
            policy.setUpdateBy(ShiroUtils.getLoginName());
            policy.setId(passwordPolicy.getId());
            return toAjax(iSysPasswordPolicyService.updatePasswordPolicy(policy));
        }
        //新增密码策略
        policy.setCreateBy(ShiroUtils.getLoginName());
        return toAjax(iSysPasswordPolicyService.insertPasswordPolicy(policy));
    }
}
