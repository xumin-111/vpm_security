package com.ruoyi.web.controller.system;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.Ztree;
import com.ruoyi.common.core.page.PageDomain;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.page.TableSupport;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.domain.*;
import com.ruoyi.system.service.ISysContextService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 上下文管理
 *
 * @author Maxj
 */
@Controller
@RequestMapping("/system/context")
public class SysContextController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(SysContextController.class);

    private String prefix = "system/context";

    @Value(value = "${ctxFile.exportPath}")
    private String exportPath;

    @Autowired
    private ISysContextService contextService;

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
        logger.debug("调用获取上下文(权限)接口======" + access.getContextName());
        //判断ctx文件生成时间，超过20分钟重新导出
        File file = new File(exportPath);
        String fileName = "";
        while (true) {
            if (file.listFiles().length > 0) {
                break;
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        for (File exportFile : file.listFiles()) {
            fileName = exportFile.getName();
            //>20min 删除
        }
        if (StringUtils.isEmpty(fileName)) {
            //调用cmd生成
        }
        //todo dumpling每次获取列表时判断文件生成时间

        List<Access> accessList = contextService.getAccessList(access, exportPath + "/" + fileName);
        PageDomain pageDomain = TableSupport.buildPageRequest();
        Integer pageNum = pageDomain.getPageNum();
        Integer pageSize = pageDomain.getPageSize();
        List pageList = accessList.stream().skip(pageSize * (pageNum - 1)).limit(pageSize).collect(Collectors.toList());

        TableDataInfo rspData = new TableDataInfo();
        rspData.setCode(0);
        rspData.setRows(pageList);
        rspData.setTotal(accessList.size());
        return rspData;
    }

    /**
     * 新增上下文  同时在内存中初始化下拉菜单内容
     */
    @GetMapping("/add")
    public String add() {
        File file = new File(exportPath);
        String fileName = "";
        for (File exportFile : file.listFiles()) {
            fileName = exportFile.getName();
            //>20min 删除
        }
        if (StringUtils.isEmpty(fileName)) {
            //调用cmd生成
        }
        //初始化所有下拉菜单内容
        contextService.initAllCtxSelect(exportPath + "/" + fileName);
        return prefix + "/add";
    }

    /**
     * 修改上下文
     */
    @GetMapping("/editContext/{contextName}")
    public String editContext(@PathVariable("contextName") String contextName, ModelMap mmap) {
        System.out.println("contextName=========" + contextName);
        String[] contextNameArr = contextName.split("\\.");
        Project project = new Project();
        project.setProjectNumber(contextNameArr[2]);
        mmap.put("project", project);
        SysRole sysRole = new SysRole();
        sysRole.setRoleName(contextNameArr[0]);
        mmap.put("role", sysRole);
        Organization organization = new Organization();
        organization.setDepartmentNumber(contextNameArr[1]);
        mmap.put("organization", organization);
        //mmap.put("user", userService.selectUserById(userId));
        //mmap.put("roles", roleService.selectRolesByUserId(userId));
        //mmap.put("posts", postService.selectPostsByUserId(userId));
        return prefix + "/editContext";
    }


    /**
     * 修改权限时展示权限列表
     *
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
        VpmContext context = new VpmContext();
        context.setContextId("12345");
        context.setContextName("VPMADMIN.DEFAULT");
        mmap.put("context", context);
        //mmap.put("user", userService.selectUserById(userId));
        //mmap.put("roles", roleService.selectRolesByUserId(userId));
        //mmap.put("posts", postService.selectPostsByUserId(userId));
        return prefix + "/editAccess";
    }

    /**
     * 新增权限 同时在内存中初始化下拉菜单内容
     */
    @GetMapping("/addAccess")
    public String addAccess() {
        File file = new File(exportPath);
        String fileName = "";
        for (File exportFile : file.listFiles()) {
            fileName = exportFile.getName();
            //>20min 删除
        }
        if (StringUtils.isEmpty(fileName)) {
            //调用cmd生成
        }
        //初始化所有下拉菜单内容
        contextService.initAllCtxSelect(exportPath + "/" + fileName);
        return prefix + "/addAccess";
    }


    /**
     * 新增权限保存
     */
    //@RequiresPermissions("system:user:add")
    //@Log(title = "用户管理", businessType = BusinessType.INSERT)
    @PostMapping("/addAccess")
    @ResponseBody
    public AjaxResult addAccessSave(@Validated Access access) {
        File file = new File(exportPath);
        String fileName = "";
        for (File exportFile : file.listFiles()) {
            fileName = exportFile.getName();
            //>20min 删除
        }
        if (StringUtils.isEmpty(fileName)) {
            //调用cmd生成
        }
        Access accessParam = new Access();
        accessParam.setContextName(access.getContextName());
        List<Access> accessList = contextService.getAccessList(accessParam, exportPath + "/" + fileName);
        for (Access pri : accessList) {
            String accessType = pri.getAccessType();
            String actionGroup = pri.getActionGroup();
            String dataGroup = pri.getDataGroup();
            boolean flag = false;
            if (StringUtils.isEmpty(dataGroup) && StringUtils.isEmpty(access.getDataGroup())) {
                flag = true;
            } else if (dataGroup != null && access.getDataGroup() != null && access.getDataGroup().indexOf(dataGroup) != -1) {
                flag = true;
            }
            boolean typeFlag = false;
            if (StringUtils.isEmpty(accessType) && StringUtils.isEmpty(access.getAccessType())) {
                typeFlag = true;
            } else if (accessType != null && access.getAccessType() != null && access.getAccessType().indexOf(accessType) != -1) {
                typeFlag = true;
            }
            if (typeFlag && actionGroup.equals(access.getActionGroup())
                    && flag) {
                return error("新增权限失败，已存在");
            }
        }
        return toAjax(contextService.insertAccess(access, exportPath, fileName));
    }

    /**
     * 新增上下文保存
     */
    //@RequiresPermissions("system:user:add")
    //@Log(title = "用户管理", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addContextSave(@Validated VpmContext vpmContext) {
        File file = new File(exportPath);
        String fileName = "";
        for (File exportFile : file.listFiles()) {
            fileName = exportFile.getName();
            //>20min 删除
        }
        if (StringUtils.isEmpty(fileName)) {
            //调用cmd生成
        }
        vpmContext.setContextName(vpmContext.getContextRole() + "." + vpmContext.getContextOrganization() + "." + vpmContext.getContextProject());
        List<VpmContext> contextList = contextService.getContextList(vpmContext, exportPath + "/" + fileName);
        for (VpmContext pri : contextList) {
            String contextName = pri.getContextName();
            boolean flag = false;
            if (StringUtils.isEmpty(contextName) && StringUtils.isEmpty(vpmContext.getContextName())) {
                flag = true;
            } else if (contextName != null && vpmContext.getContextName() != null && vpmContext.getContextName().indexOf(contextName) != -1) {
                flag = true;
            }
            if (flag) {
                return error("新增上下文失败失败，已存在");
            }
        }
        return toAjax(contextService.insertContext(vpmContext, exportPath, fileName));
        //return toAjax(1);
    }

    /**
     * 针对上下文新增权限
     */
    @GetMapping("/createAccess/{contextId}")
    public String createAccess(@PathVariable("contextId") String contextId, ModelMap mmap) {
        VpmContext vpmContext = contextService.getContextByName(contextId);
        mmap.put("context", vpmContext);
        File file = new File(exportPath);
        String fileName = "";
        for (File exportFile : file.listFiles()) {
            fileName = exportFile.getName();
            //>20min 删除
        }
        if (StringUtils.isEmpty(fileName)) {
            //调用cmd生成
        }
        //初始化所有下拉菜单内容
        contextService.initAllCtxSelect(exportPath + "/" + fileName);
        return prefix + "/createAccess";
    }

    /**
     * 选择操作树
     */
    @GetMapping("/selectProcessTree")
    public String selectProcessTree() {
        return prefix + "/tree";
    }

    /**
     * 加载操作列表树
     */
    @GetMapping("/treeData")
    @ResponseBody
    public List<Ztree> treeData() {
        List<Ztree> ztrees = contextService.selectProcessTree(null);
        return ztrees;
    }

    /**
     * 选择人员
     */
    @GetMapping("/selectPersonTree")
    public String selectPersonTree() {
        return prefix + "/personTree";
    }

    /**
     * 加载人员操作列表树
     */
    @GetMapping("/personTreeData")
    @ResponseBody
    public List<Ztree> personTreeData() {
        List<Ztree> ztrees = contextService.selectPersonTree(null);
        return ztrees;
    }
}