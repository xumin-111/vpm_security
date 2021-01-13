package com.ruoyi.system.service;

import com.ruoyi.common.core.domain.Ztree;
import com.ruoyi.system.domain.*;

import java.util.List;
import java.util.Map;

public interface ISysContextService {
    List<Access> getAccessList(Access access, String exportPath);

    List<VpmContext> getContextList(VpmContext vpmContext, String exportPath);

    List<VpmContext> getVpmContextList();

    void initAllCtxSelect(String exportPath);

    List<Map> getVpmType();

    List<Ztree> selectProcessTree(String param);

    List<Ztree> selectPersonTree(String param);

    List<DataGroup> getVpmDataGroup();

    int insertAccess(Access access, String exportFile, String exportPath);

    int insertContext(VpmContext vpmContext, String exportFile, String exportPath);

    VpmContext getContextByName(String contextId);

    List<Project> getVpmDataProject();

    List<SysRole> getVpmDataRole();

    List<Organization> getVpmDataOrganization();
}
