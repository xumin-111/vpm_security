package com.ruoyi.web.controller.system;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.system.domain.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 上下文管理
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
        if (!"".equals(access.getContextName())) {
            list1.add(access);
        } else {
            Access access1 = new Access();
            access1.setContextId("12345");
            access1.setContextName("Admin");
            list1.add(access1);
        }
        System.out.println("调用获取权限接口======" + access.getContextName());
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
     * 修改权限时展示权限列表
     * @return
     */
    @PostMapping("/accessList/{contextId}")
    @ResponseBody
    public TableDataInfo accessList() {
        startPage();
        //todo dumpling 根据上下文Id获取权限Access列表
        List<Access> list1 = new ArrayList<Access>();
        Access access = new Access();
        access.setAccessId("123");
        access.setAccessType("VPMadmin");
        access.setActionGroup("操作1");
        access.setDataGroup("数据1");
        list1.add(access);
        return getDataTable(list1);
    }


    /**
     * 修改上下文内的权限
     */
    @GetMapping("/editAccess/{contextId}")
    public String editAccess(@PathVariable("contextId") String contextId, ModelMap mmap) {
        Context context = new Context();
        context.setContextId("12345");
        context.setContextName("VPMADMIN.DEFAULT");
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
        access.setAccessType("VPM");
        access.setActionGroup("1111");
        mmap.put("access", access);
        return prefix + "/addAccess";
    }


    /**
     * 针对上下文新增权限
     */
    @GetMapping("/createAccess/{contextId}")
    public String createAccess(@PathVariable("contextId") String contextId, ModelMap mmap) {
        Access access = new Access();
        access.setContextId("12345");
        access.setContextName("Admin");
        access.setAccessType("VPM");
        access.setActionGroup("LOGIN");
        access.setDataGroup("DataGroupTest");
        mmap.put("access", access);
        return prefix + "/createAccess";
    }
}