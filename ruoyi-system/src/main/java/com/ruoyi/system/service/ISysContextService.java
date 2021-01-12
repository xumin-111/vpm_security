package com.ruoyi.system.service;

import com.ruoyi.common.core.domain.Ztree;
import com.ruoyi.system.domain.Access;
import com.ruoyi.system.domain.DataGroup;
import com.ruoyi.system.domain.VpmContext;

import java.util.List;
import java.util.Map;

public interface ISysContextService {
    List<Access> getAccessList(Access access,String exportPath);

    List<VpmContext> getVpmContextList();

    void initAllCtxSelect(String exportPath);

    List<Map> getVpmType();

    List<Ztree> selectProcessTree(String param);

    List<DataGroup> getVpmDataGroup();

    int insertAccess(Access access,String exportFile,String exportPath);

    VpmContext getContextByName(String contextId);

    int deleteAccess(String ids,String exportPath,String fileName);
}
