package com.ruoyi.web.controller.system;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.page.PageDomain;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.page.TableSupport;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.DesUtils;
import com.ruoyi.system.domain.*;
import com.ruoyi.system.mapper.SysOperLogMapper;
import com.ruoyi.system.service.ISysLogininforService;
import com.ruoyi.system.service.ISysOperLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户信息
 *
 * @author Maxj
 */
@Controller
@RequestMapping("/system/event")
public class SysEventController extends BaseController {
    private String prefix = "system/event";

    @Autowired
    private ISysOperLogService iSysOperLogService;

    @Autowired
    private ISysLogininforService iSysLogininforService;

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

    @Log(title = "审计日志查询统计", businessType = BusinessType.EXPORT, actionType = "审计日志")
    @PostMapping("/adminEvent")
    @ResponseBody
    public TableDataInfo list1(Event event) {
        System.out.println("调管理员行为数据获取接口");
        System.out.println("如果event对象存在则调用单个的对象====" + event.getType());
        List<SysOperLog> sysOperLogList = iSysOperLogService.selectOperLogList(new SysOperLog());
        List<SysLogininfor> selectLogininforList = iSysLogininforService.selectLogininforList(new SysLogininfor());
        System.out.println("===========================" + sysOperLogList.size());
        startPage();
        List<Event> eventList = new ArrayList<Event>();
        if (event.getType() != null && !"".equals(event.getType())) {
            eventList.add(event);
        } else {
            for (int i = 0; i < sysOperLogList.size(); i++) {
                SysOperLog sysOperLog = sysOperLogList.get(i);
                Event eventOne = new Event();
                eventOne.setUserName(DesUtils.decrypt(sysOperLog.getOperName(), "Qwerty12345"));
                eventOne.setUserSecret("秘密");
                eventOne.setIp(DesUtils.decrypt(sysOperLog.getOperIp(), "Qwerty12345"));
                eventOne.setObject(getObjectName(sysOperLog));
                eventOne.setObjectSecret("");
                eventOne.setType(DesUtils.decrypt(sysOperLog.getTitle(), "Qwerty12345"));
                eventOne.setLevel("1");
                eventOne.setBeginDate(getFormateDate(sysOperLog.getOperTime()));
                eventOne.setDescription(DesUtils.decrypt(sysOperLog.getJsonResult(), "Qwerty12345"));
                eventList.add(eventOne);
            }
            for (int i = 0; i < selectLogininforList.size(); i++) {
                SysLogininfor sysLogininfor = selectLogininforList.get(i);
                Event eventOne = new Event();
                eventOne.setUserName(sysLogininfor.getLoginName());
                eventOne.setUserSecret("秘密");
                eventOne.setIp(sysLogininfor.getIpaddr());
                String msg = sysLogininfor.getMsg();
                if (msg.equals("登录成功") || msg.equals("验证码错误")) {
                    msg = "登录";
                } else {
                    msg = "退出";
                }
                eventOne.setObject(sysLogininfor.getLoginName());
                eventOne.setObjectSecret("");
                eventOne.setType(msg);
                eventOne.setLevel("1");
                eventOne.setBeginDate(getFormateDate(sysLogininfor.getLoginTime()));
                eventOne.setDescription(sysLogininfor.getMsg());
                eventList.add(eventOne);
            }
        }
        PageDomain pageDomain = TableSupport.buildPageRequest();
        Integer pageNum = pageDomain.getPageNum();
        Integer pageSize = pageDomain.getPageSize();
        List pageList = eventList.stream().skip(pageSize * (pageNum - 1)).limit(pageSize).collect(Collectors.toList());
        TableDataInfo rspData = new TableDataInfo();
        rspData.setCode(0);
        rspData.setRows(pageList);
        rspData.setTotal(eventList.size());
        return rspData;
    }

    public String getObjectName(SysOperLog sysOperLog) {
        if ((DesUtils.decrypt(sysOperLog.getMethod(), "Qwerty12345")).equals("上下文")) {
            String operParam = DesUtils.decrypt(sysOperLog.getOperParam(), "Qwerty12345");
            //System.out.println("===========================" + operParam);
            JSONObject jsonObject = JSONObject.parseObject(operParam);
            //jsonObject.value(operParam);
            String contextRole = jsonObject.get("contextRole").toString();
            String contextOrganization = jsonObject.get("contextOrganization").toString();
            String contextProject = jsonObject.get("contextProject").toString();
            return "上下文" + removeSymbol(contextRole) + "." + removeSymbol(contextOrganization) + "." + removeSymbol(contextProject);
        }
        return (DesUtils.decrypt(sysOperLog.getMethod(), "Qwerty12345"));
    }


    public String removeSymbol(String symbol) {
        return symbol.substring(2, symbol.length() - 2);
    }

    public String getMsg(SysOperLog sysOperLog) {
        String jsonResult = sysOperLog.getJsonResult();
        JSONObject jsonObject = JSONObject.parseObject(jsonResult);
        return jsonObject.get("msg").toString().equals("0") ? "" : jsonObject.get("msg").toString();
    }

    public String getFormateDate(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(date);
    }
}