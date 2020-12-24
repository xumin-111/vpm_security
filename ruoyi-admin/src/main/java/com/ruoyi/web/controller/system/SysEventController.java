package com.ruoyi.web.controller.system;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.system.domain.Event;
import com.ruoyi.system.domain.History;
import com.ruoyi.system.domain.Project;
import com.ruoyi.system.domain.SysUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户信息
 *
 * @author Maxj
 */
@Controller
@RequestMapping("/system/event")
public class SysEventController extends BaseController {
    private String prefix = "system/event";

    @GetMapping("/safetyEvent")
    public String safetyEvent() {
        return prefix + "/safetyEvent";
    }

    @GetMapping("/adminEvent")
    public String adminEvent() {
        return prefix + "/adminEvent";
    }

    @PostMapping("/safetyEvent")
    @ResponseBody
    public TableDataInfo list(Event event) {
        startPage();
        System.out.println("调普通用户数据获取接口");
        System.out.println("如果event对象存在则调用单个的对象====" + event.getType());
        List<Event> list1 = new ArrayList<Event>();
        if (event.getType() != null && !"".equals(event.getType())) {
            list1.add(event);
        } else {
            Event event1 = new Event();
            event.setUserName("xumin111");
            list1.add(event);
        }
        return getDataTable(list1);
    }

    @PostMapping("/adminEvent")
    @ResponseBody
    public TableDataInfo list1(Event event) {
        startPage();
        System.out.println("调管理员行为数据获取接口");
        System.out.println("如果event对象存在则调用单个的对象====" + event.getType());
        List<Event> list1 = new ArrayList<Event>();
        if (event.getType() != null && !"".equals(event.getType())) {
            list1.add(event);
        } else {
            Event event1 = new Event();
            event.setUserName("xumin22222");
            list1.add(event);
        }
        return getDataTable(list1);
    }

}