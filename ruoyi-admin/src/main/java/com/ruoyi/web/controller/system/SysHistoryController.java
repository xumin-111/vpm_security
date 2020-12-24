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
 * 用户信息
 *
 * @author Maxj
 */
@Controller
@RequestMapping("/system/history")
public class SysHistoryController extends BaseController {
    private String prefix = "system/history";

    @GetMapping()
    public String history(ModelMap mmap) {
        Project project = new Project();
        project.setProjectNumber("project001");
        mmap.put("project", project);
        return prefix + "/history";
    }

    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(History history) {
        startPage();
        System.out.println("调涉密数据获取接口");
        System.out.println("如果user对象存在则调用单个的对象===" + history.getProject());
        List<History> list1 = new ArrayList<History>();
        if (history.getProject() != null && !"".equals(history.getProject())) {
            list1.add(history);
        } else {
            History history1 = new History();
            history1.setProject("gggggggg");
            list1.add(history);
        }
        return getDataTable(list1);
    }

}