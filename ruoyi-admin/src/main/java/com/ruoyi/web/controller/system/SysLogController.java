package com.ruoyi.web.controller.system;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.domain.Project;
import com.ruoyi.system.domain.SysDept;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 用户信息
 *
 * @author Maxj
 */
@Controller
@RequestMapping("/system/log")
public class SysLogController extends BaseController {
    private String prefix = "system/log";

    @GetMapping()
    public String log() {
        return prefix + "/log";
    }

    /**
     * 修改
     */
    @GetMapping("/edit")
    public String edit(ModelMap mmap) {
        com.ruoyi.system.domain.Log log = new com.ruoyi.system.domain.Log();
        log.setRelatively("123");
        mmap.put("log", log);
        System.out.println("展示日志转存页面");
        return prefix + "/log";
    }

    /**
     * 日志转存修改接口
     *
     * @return
     */
    @Log(title = "新增组织", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(@Validated com.ruoyi.system.domain.Log log) {
        System.out.println("调用日志转存接口=======" + log.getRelativelyAll());
        //dept.setCreateBy(ShiroUtils.getLoginName());
        return toAjax(true);
    }
}