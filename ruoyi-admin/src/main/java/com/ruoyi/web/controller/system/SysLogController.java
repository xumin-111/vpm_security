package com.ruoyi.web.controller.system;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.exception.job.TaskException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.quartz.domain.SysJob;
import com.ruoyi.quartz.service.ISysJobService;
import com.ruoyi.system.domain.LogPolicy;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 日志转存
 * @author dumpling
 */
@Controller
@RequestMapping("/system/log")
public class SysLogController extends BaseController {
    private String prefix = "system/log";

    @Autowired
    private ISysJobService jobService;
    /**
     * 日志转存展示
     */
    @GetMapping("/edit")
    public String edit(ModelMap mmap) {
        SysJob sysJob = jobService.selectJobByGroup("LOG");
        if(sysJob == null){
            sysJob = new SysJob();
            sysJob.setJobName("审计日志转存");
            sysJob.setJobGroup("LOG");
            sysJob.setInvokeTarget("ryTask.logPolicyTask");
            sysJob.setCronExpression("0 0 23 * * ? *");//每天23：00通过判断满足条件执行转存
            //sysJob.setCronExpression("0/5 * * * * ? *");
            sysJob.setMisfirePolicy("1");
            sysJob.setConcurrent("1");
            sysJob.setStatus("0");
            try {
                jobService.insertJob(sysJob);
            } catch (SchedulerException e) {
                e.printStackTrace();
            } catch (TaskException e) {
                e.printStackTrace();
            }
        }
        if(StringUtils.isEmpty(sysJob.getRemark())){
            sysJob.setRemark("0");
        }
        mmap.put("logPolicy", sysJob);
        return prefix + "/log";
    }

    /**
     * 日志转存策略保存
     *
     * @return
     */
    //@Log(title = "新增组织", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(@Validated LogPolicy log) throws TaskException, SchedulerException {
        //System.out.println("调用日志转存接口=======" + log.getRelativelyAll());
        SysJob job = new SysJob();
        Long jobId = log.getJobId();
        if(jobId == null){
            SysJob sysJob = jobService.selectJobByGroup("LOG");
            jobId = sysJob.getJobId();
        }
        job.setJobId(jobId);
        job.setRemark(log.getRelativelyAll());
        return toAjax(jobService.updateJobOnly(job));
    }

    /**
     * 转存日志
     */
    //@Log(title = "定时任务", businessType = BusinessType.UPDATE)
    @PostMapping("/transferSaveLog")
    @ResponseBody
    public AjaxResult run(SysJob job) throws SchedulerException
    {
        jobService.run(job);
        return success();
    }
}