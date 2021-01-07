package com.ruoyi.framework.web.service;

import com.ruoyi.system.domain.DataGroup;
import com.ruoyi.system.domain.VpmContext;
import com.ruoyi.system.service.ISysContextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("vpmCtx")
public class VpmContextService {
    @Autowired
    private ISysContextService sysContextService;

    /**
     * 获取系统内上下文
     * @return
     */
    public List<VpmContext> getVpmContextList(){
        return sysContextService.getVpmContextList();
    }

    public List<Map> getVpmType(){
        return sysContextService.getVpmType();
    }

    public List<DataGroup> getVpmDataGroup(){
        return sysContextService.getVpmDataGroup();
    }
}
