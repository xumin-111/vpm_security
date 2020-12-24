package com.ruoyi.web.controller.system;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.shiro.service.SysPasswordService;
import com.ruoyi.framework.util.ShiroUtils;
import com.ruoyi.system.domain.*;
import com.ruoyi.system.service.ISysPostService;
import com.ruoyi.system.service.ISysRoleService;
import com.ruoyi.system.service.ISysUserService;
import com.sun.org.apache.bcel.internal.generic.NEW;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户信息
 *
 * @author Maxj
 */
@Controller
@RequestMapping("/system/context")
public class SysContextController extends BaseController {
    private String prefix = "system/context";

    @GetMapping()
    public String context() {
        return prefix + "/context";
    }

    /**
     * 权限列表
     *
     * @param access
     * @return
     */
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Access access) {
        startPage();
        List<Access> list1 = new ArrayList<Access>();
        if (!"".equals(access.getContextId())) {
            list1.add(access);
        } else {
            Access access1 = new Access();
            access1.setContextId("12345");
            access1.setContextName("Admin");
            list1.add(access1);
        }
        System.out.println("调用获取权限接口======" + access.getContextId());
        return getDataTable(list1);
    }


    /**
     * 新增用户
     */
    @GetMapping("/add")
    public String add(ModelMap mmap) {
        Project project = new Project();
        project.setProjectNumber("project001");
        mmap.put("project", project);
        SysRole sysRole = new SysRole();
        sysRole.setRoleName("role001");
        mmap.put("role", sysRole);
        Organization organization = new Organization();
        organization.setDepartmentNumber("dept001");
        mmap.put("organization", organization);
        //mmap.put("roles", roleService.selectRoleAll());
        //mmap.put("posts", postService.selectPostAll());
        System.out.println("11111");
        return prefix + "/add";
    }


    /**
     * 修改上下文
     */
    @GetMapping("/editContext/{contextId}")
    public String editContext(@PathVariable("contextId") String contextId, ModelMap mmap) {
        Project project = new Project();
        project.setProjectNumber("project001");
        mmap.put("project", project);
        SysRole sysRole = new SysRole();
        sysRole.setRoleName("role001");
        mmap.put("role", sysRole);
        Organization organization = new Organization();
        organization.setDepartmentNumber("dept001");
        mmap.put("organization", organization);
        //mmap.put("user", userService.selectUserById(userId));
        //mmap.put("roles", roleService.selectRolesByUserId(userId));
        //mmap.put("posts", postService.selectPostsByUserId(userId));
        return prefix + "/editContext";
    }

    /**
     * 修改权限
     */
    @GetMapping("/editAccess/{contextId}")
    public String editAccess(@PathVariable("contextId") String contextId, ModelMap mmap) {
        Context context = new Context();
        context.setContextName("999999");
        mmap.put("context", context);
        //mmap.put("user", userService.selectUserById(userId));
        //mmap.put("roles", roleService.selectRolesByUserId(userId));
        //mmap.put("posts", postService.selectPostsByUserId(userId));
        return prefix + "/editAccess";
    }

    /**
     * 新增权限
     */
    @GetMapping("/addAccess")
    public String addAccess(ModelMap mmap) {
        Access access = new Access();
        access.setContextId("12345");
        access.setContextName("Admin");
        access.setContextAction("VPM");
        access.setContextData("1111");
        mmap.put("access", access);
        return prefix + "/addAccess";
    }

}