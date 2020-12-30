package com.ruoyi.system.service;

import com.ruoyi.system.domain.Access;

import java.util.List;

public interface ISysContextService {
    List<Access> getAccessList(Access access,String exportPath);
}
