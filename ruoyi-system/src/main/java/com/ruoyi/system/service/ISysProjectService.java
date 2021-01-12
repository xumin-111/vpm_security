package com.ruoyi.system.service;

import com.ruoyi.system.domain.Project;

import java.util.List;

/**
 * @author xumin
 * @create 2020-12-30 17:22
 */

public interface ISysProjectService {
    List<Project> getProjectList(Project project, String exportPath);
}
