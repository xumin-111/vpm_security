package com.ruoyi.system.service.impl;

import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.ctxCache.CtxMapCache;
import com.ruoyi.system.domain.Project;
import com.ruoyi.system.service.ISysProjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xumin
 * @create 2020-12-30 17:36
 */
@Service
public class SysProjectServiceImpl implements ISysProjectService {

    private static Logger logger = LoggerFactory.getLogger(SysProjectServiceImpl.class);

    @Autowired
    private CtxMapCache ctxMapCache;

    @Override
    public List<Project> getProjectList(Project projectParam, String exportPath) {
        List<Project> projectList = new ArrayList<Project>();
        List<Project> projectsListCache = (List<Project>) ctxMapCache.get("mainProjectList");
        if (projectsListCache == null || projectsListCache.size() == 0) {
            try {
                List<Project> mainProjectList = new ArrayList<Project>();
                List<String> projectNames = new ArrayList<String>();
                File file = new File(exportPath);
                BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
                String strLine = null;
                int lineCount = 1;
                while (null != (strLine = bufferedReader.readLine())) {
                    if (strLine != null && strLine.startsWith("*PRJ")) {
                        logger.debug(strLine);
                        strLine = strLine.substring("*PRJ".length());
                        Project project = new Project();
                        String projectName = strLine.split(";")[0].trim();
                        project.setProjectNumber(projectName);
                        //缓存增加context信息，key：contextName
                        ctxMapCache.add(strLine, project, 60 * 1000 * 5);
                        if (projectParam != null && StringUtils.isNotEmpty(projectParam.getProjectNumber())) {
                            if (strLine.indexOf(projectParam.getProjectNumber()) != -1) {
                                projectNames.add(projectName);
                            }
                        } else {
                            projectNames.add(projectName);
                        }

                        logger.debug("第[" + lineCount + "]行project数据:[" + projectName + "]");
                    }
                    lineCount++;
                }
                for (String projectName : projectNames) {
                    Project project = new Project();
                    project.setProjectNumber(projectName);
                    projectList.add(project);
                    mainProjectList.add(project);
                }
                //ctxMapCache.add("mainProjectList", mainProjectList, 60 * 1000 * 5);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            for (Project project : projectsListCache) {
                if (projectParam != null && StringUtils.isNotEmpty(projectParam.getProjectNumber())) {
                    if (project.getProjectNumber().indexOf(projectParam.getProjectNumber()) != -1) {
                        projectList.add(project);
                    }
                } else {
                    projectList.add(project);
                }
            }
        }
        return projectList;
    }
}
