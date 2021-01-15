package com.ruoyi.quartz.task;

import org.springframework.stereotype.Component;
import com.ruoyi.common.utils.StringUtils;

/**
 * 定时任务调度测试
 * 
 * @author dumpling
 */
@Component("ryTask")
public class RyTask
{
    public void ryMultipleParams(String s, Boolean b, Long l, Double d, Integer i)
    {
        System.out.println(StringUtils.format("执行多参方法： 字符串类型{}，布尔类型{}，长整型{}，浮点型{}，整形{}", s, b, l, d, i));
    }

    public void ryParams(String params)
    {
        System.out.println("执行有参方法：" + params);
    }

    public void ryNoParams()
    {
        System.out.println("执行无参方法");
    }

    /**
     * 转存日志task
     */
    public void logPolicyTask(){
        System.out.println("....................................logPolicyTask.....");
        //todo zhouhao 查询sys_job_log 条件job_group，最新的时间即上次转存的时间
        //跟sys_job的remark比较，符合条件 执行转存
        //导出文件
        //删除表记录
    }
}
