package com.ruoyi.system.service.impl;

import com.ruoyi.common.core.domain.Ztree;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.ctxCache.CtxMapCache;
import com.ruoyi.system.domain.*;
import com.ruoyi.system.service.ISysContextService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;

@Service
public class SysContextServiceImpl implements ISysContextService {

    private static Logger logger = LoggerFactory.getLogger(SysContextServiceImpl.class);

    @Autowired
    private CtxMapCache ctxMapCache;

    @Override
    public List<Access> getAccessList(Access accessParam, String exportPath) {
        List<Access> accessList = new ArrayList<>();
        List<Access> accessListCache = (List<Access>) ctxMapCache.get("mainAccessList");
        if (accessListCache == null || accessListCache.size() == 0) {
            BufferedReader bufferedReader = null;
            try {
                List<Access> mainAccessList = new ArrayList<>();
                List<String> ctxNames = new ArrayList<>();
                List<VpmContext> ctxNamesAll = new ArrayList<>();
                File file = new File(exportPath);
                bufferedReader = new BufferedReader(new FileReader(file));
                String strLine = null;
                int lineCount = 1;
                int ctxBeginLine = 100000;
                int accessBeginLine = 100000;
                while (null != (strLine = bufferedReader.readLine())) {
                    if (strLine != null && strLine.indexOf("RscContext") != -1) {
                        ctxBeginLine = lineCount;
                    }
                    if (strLine != null && strLine.startsWith("*CTX") && lineCount > ctxBeginLine) {
                        strLine = strLine.substring("*CTX ".length()).replaceAll(";", ".");
                        VpmContext ctx = new VpmContext();
                        ctx.setContextName(strLine);
                        ctx.setContextProject(strLine.split("\\.")[2]);
                        ctx.setContextOrganization(strLine.split("\\.")[1]);
                        ctx.setContextRole(strLine.split("\\.")[0]);
                        //缓存增加context信息，key：contextName
                        ctxMapCache.add(strLine, ctx, 60 * 1000 * 5);
                        if (accessParam != null && StringUtils.isNotEmpty(accessParam.getContextName())) {
                            if (strLine.indexOf(accessParam.getContextName()) != -1) {
                                ctxNames.add(strLine);
                            }
                        } else {
                            ctxNames.add(strLine);
                        }
                        ctxNamesAll.add(ctx);
                        logger.debug("第[" + lineCount + "]行context数据:[" + strLine + "]");
                    }

                    if (strLine != null && strLine.indexOf("privilege list") != -1) {
                        accessBeginLine = lineCount;
                    }
                    if (strLine != null && strLine.startsWith("*PRIV") && lineCount > accessBeginLine) {
                        Access access = new Access();
                        String accessStr = strLine.split(" ")[1];
                        if (accessStr.indexOf("CTX=") == -1) {
                            //不是上下文的权限
                            continue;
                        }
                        String[] accessInfo = accessStr.split(";");
                        //第一个数字暂时没用
                        for (int i = 1; i < accessInfo.length; i++) {
                            if (accessInfo[i].contains("CTX=")) {
                                String ctxName = accessInfo[i].split("=")[1];
                                if (ctxNames.contains(ctxName)) {
                                    ctxNames.remove(ctxName);
                                }
                                access.setContextName(ctxName);
                            } else if (accessInfo[i].contains("PROCESS=")) {
                                String type = accessInfo[i].split("=")[1].split("\\.")[0];
                                String oper = accessInfo[i].split("=")[1].split("\\.")[1];
                                access.setAccessType(type);
                                access.setActionGroup(oper);
                            } else if (accessInfo[i].contains("PROCESS_GROUP=")) {
                                String operGroup = accessInfo[i].split("=")[1];
                                access.setActionGroup(operGroup);
                            }

                            if (i == accessInfo.length - 2) {

                            }
                            if (i == accessInfo.length - 1 && !accessInfo[i].contains("PROCESS")) {
                                access.setDataGroup(accessInfo[i]);
                            }
                        }
                        if (accessParam != null && StringUtils.isNotEmpty(accessParam.getContextName())) {
                            if (access.getContextName().indexOf(accessParam.getContextName()) != -1) {
                                accessList.add(access);
                            }
                        } else {
                            accessList.add(access);
                        }
                        mainAccessList.add(access);
                        //ctxMapCache.add("mainAccessList",strLine,60 * 1000 * 5);
                        logger.debug("第[" + lineCount + "]行priv数据:[" + accessStr + "]");
                    }
                    lineCount++;
                }
                for (String ctxName : ctxNames) {
                    Access access = new Access();
                    access.setContextName(ctxName);
                    accessList.add(access);
                    mainAccessList.add(access);
                }
                ctxMapCache.add("ctxList", ctxNamesAll, 60 * 1000 * 5);
                ctxMapCache.add("mainAccessList", mainAccessList, 60 * 1000 * 5);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (bufferedReader != null)
                        bufferedReader.close();//关闭读取缓冲区
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            for (Access access : accessListCache) {
                if (accessParam != null && StringUtils.isNotEmpty(accessParam.getContextName())) {
                    if (access.getContextName().indexOf(accessParam.getContextName()) != -1) {
                        accessList.add(access);
                    }
                } else {
                    accessList.add(access);
                }
            }
        }
        return accessList;
    }

    @Override
    public List<VpmContext> getContextList(VpmContext vpmContextParam, String exportPath) {
        List<VpmContext> contextList = new ArrayList<>();
        List<VpmContext> contexListCache = (List<VpmContext>) ctxMapCache.get("ctxList");
        if (contexListCache == null || contexListCache.size() == 0) {
            BufferedReader bufferedReader = null;
            try {
                File file = new File(exportPath);
                bufferedReader = new BufferedReader(new FileReader(file));
                String strLine = null;
                int lineCount = 1;
                int ctxBeginLine = 100000;
                int accessBeginLine = 100000;
                while (null != (strLine = bufferedReader.readLine())) {
                    if (strLine != null && strLine.indexOf("RscContext") != -1) {
                        ctxBeginLine = lineCount;
                    }
                    if (strLine != null && strLine.startsWith("*CTX") && lineCount > ctxBeginLine) {
                        strLine = strLine.substring("*CTX ".length()).replaceAll(";", ".");
                        VpmContext ctx = new VpmContext();
                        ctx.setContextName(strLine);
                        ctx.setContextProject(strLine.split("\\.")[2]);
                        ctx.setContextOrganization(strLine.split("\\.")[1]);
                        ctx.setContextRole(strLine.split("\\.")[0]);
                        contextList.add(ctx);
                        logger.debug("第[" + lineCount + "]行context数据:[" + strLine + "]");
                    }
                    lineCount++;
                }
                ctxMapCache.add("ctxList", contextList, 60 * 1000 * 5);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (bufferedReader != null)
                        bufferedReader.close();//关闭读取缓冲区
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            contextList = contexListCache;
        }
        return contextList;
    }

    @Override
    public List<VpmContext> getVpmContextList() {
        List<VpmContext> ctxListCache = (List<VpmContext>) ctxMapCache.get("ctxList");
        if (ctxListCache == null || ctxListCache.size() == 0) {
            //缓存中没有上下文内容
            logger.info("info=========================：缓存中没有上下文列表");
        }
        return ctxListCache;
    }

    @Override
    public void initAllCtxSelect(String exportPath) {
        List<VpmContext> ctxNames = new ArrayList<>();
        Set<String> ctxTypes = new HashSet<>();
        List<DataGroup> dataGroups = new ArrayList<>();
        List<VpmProcess> vpmProcesses = new ArrayList<>();
        List<Project> dataProjects = new ArrayList<>();
        List<SysRole> dataRoles = new ArrayList<>();
        List<User> dataUsers = new ArrayList<>();
        List<Organization> dataOrganizations = new ArrayList<>();
        BufferedReader bufferedReader = null;
        try {
            File file = new File(exportPath);
            bufferedReader = new BufferedReader(new FileReader(file));
            String strLine = null;
            int lineCount = 1;
            int ctxBeginLine = 100000;
            int processBeginLine = 100000;
            int dataBeginLine = 100000;
            int projectBeginLine = 100000;
            int roleBeginLine = 100000;
            int organizationBeginLine = 100000;
            int personBeginLine = 100000;
            int currentProGroup = 0;
            while (null != (strLine = bufferedReader.readLine())) {
                if (strLine != null && strLine.indexOf("RscContext") != -1) {
                    ctxBeginLine = lineCount;
                }
                if (strLine != null && strLine.startsWith("*CTX") && lineCount > ctxBeginLine) {
                    strLine = strLine.substring("*CTX ".length()).replaceAll(";", ".");
                    VpmContext ctx = new VpmContext();
                    ctx.setContextName(strLine);
                    ctx.setContextProject(strLine.split("\\.")[2]);
                    ctx.setContextOrganization(strLine.split("\\.")[1]);
                    ctx.setContextRole(strLine.split("\\.")[0]);
                    //缓存增加context信息，key：contextName
                    ctxMapCache.add(strLine, ctx, 60 * 1000 * 5);
                    logger.debug("第[" + lineCount + "]行context数据:[" + strLine + "]");
                    ctxNames.add(ctx);
                }
                //读取类型，操作/操作组信息
                if (strLine != null && strLine.indexOf("process_group list") != -1) {
                    processBeginLine = lineCount;
                }
                if (strLine != null && strLine.startsWith("*PGROUP") && lineCount > processBeginLine) {
                    String proStr = strLine.split(" ")[1];
                    VpmProcess vpmProcess = new VpmProcess();
                    vpmProcess.setProcessId(lineCount);
                    vpmProcess.setProcessGroupId(0);
                    vpmProcess.setProcessName(proStr.split(";")[0]);
                    vpmProcesses.add(vpmProcess);
                    currentProGroup = lineCount;
                }
                if (strLine != null && strLine.startsWith("+PROCESS") && lineCount > processBeginLine) {
                    String proStr = strLine.split(" ")[1];
                    //操作/操作组集合
                    VpmProcess vpmProcess = new VpmProcess();
                    vpmProcess.setProcessId(lineCount);
                    vpmProcess.setProcessGroupId(currentProGroup);
                    if ("$".equals(proStr.split(";")[2])) {
                        vpmProcess.setProcessName(proStr.split(";")[1]);
                    } else {
                        vpmProcess.setProcessName(proStr.split(";")[2]);
                    }
                    vpmProcesses.add(vpmProcess);
                    //类型集合
                    ctxTypes.add(proStr.split(";")[0]);
                }
                //读取数据组信息
                if (strLine != null && strLine.indexOf("data_group list") != -1) {
                    dataBeginLine = lineCount;
                }
                if (strLine != null && strLine.startsWith("*DATA") && lineCount > dataBeginLine) {
                    String dataStr = strLine.split(" ")[1];
                    DataGroup dataGroup = new DataGroup();
                    dataGroup.setDataName(dataStr.split(";")[0]);
                    dataGroup.setDataValue(dataStr.split(";")[1] + ";" + dataStr.split(";")[0]);
                    dataGroups.add(dataGroup);
                }
                //读取项目信息
                if (strLine != null && strLine.indexOf("RscProject list") != -1) {
                    projectBeginLine = lineCount;
                }
                if (strLine != null && strLine.startsWith("*PRJ") && lineCount > projectBeginLine) {
                    strLine = strLine.substring("*PRJ".length());
                    String projectName = strLine.split(";")[0].trim();
                    Project project = new Project();
                    project.setProjectNumber(projectName);
                    dataProjects.add(project);
                }
                //读取角色信息
                if (strLine != null && strLine.indexOf("RscRole list") != -1) {
                    roleBeginLine = lineCount;
                }
                if (strLine != null && strLine.startsWith("*ROLE") && lineCount > roleBeginLine) {
                    strLine = strLine.substring("*ROLE".length());
                    String roleName = strLine.split(";")[0].trim();
                    SysRole sysRole = new SysRole();
                    sysRole.setRoleName(roleName);
                    dataRoles.add(sysRole);
                }
                //读取组织信息
                if (strLine != null && strLine.indexOf("RscOrg list") != -1) {
                    organizationBeginLine = lineCount;
                }
                if (strLine != null && strLine.startsWith("*ORG") && lineCount > organizationBeginLine) {
                    strLine = strLine.substring("*ORG".length());
                    String organizationName = strLine.split(";")[0].trim();
                    Organization organization = new Organization();
                    organization.setDepartmentNumber(organizationName);
                    dataOrganizations.add(organization);
                }
                //读取人员信息
                if (strLine != null && strLine.indexOf("RscPerson list") != -1) {
                    personBeginLine = lineCount;
                }
                if (strLine != null && strLine.startsWith("*PERSON") && lineCount > personBeginLine) {
                    strLine = strLine.substring("*PERSON".length());
                    String userName = strLine.split(";")[0].trim();
                    User user = new User();
                    user.setUserFullName(userName);
                    dataUsers.add(user);
                }
                lineCount++;
            }
            ctxMapCache.add("ctxList", ctxNames, 60 * 1000 * 5);
            ctxMapCache.add("ctxTypes", ctxTypes, 60 * 1000 * 5);
            ctxMapCache.add("vpmProcesses", vpmProcesses, 60 * 1000 * 5);
            ctxMapCache.add("dataGroups", dataGroups, 60 * 1000 * 5);
            ctxMapCache.add("dataProjects", dataProjects, 60 * 1000 * 5);
            ctxMapCache.add("dataRoles", dataRoles, 60 * 1000 * 5);
            ctxMapCache.add("dataUsers", dataUsers, 60 * 1000 * 5);
            ctxMapCache.add("dataOrganizations", dataOrganizations, 60 * 1000 * 5);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedReader != null)
                    bufferedReader.close();//关闭读取缓冲区
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<Map> getVpmType() {
        List<Map> result = new ArrayList<>();
        Set<String> ctxTypesCache = (Set<String>) ctxMapCache.get("ctxTypes");
        if (ctxTypesCache == null || ctxTypesCache.size() == 0) {
            logger.info("info=========================：缓存中没有上下文类型列表");
        } else {
            for (String s : ctxTypesCache) {
                Map<String, String> map = new HashMap<>();
                map.put("name", s);
                result.add(map);
            }
        }
        return result;
    }

    @Override
    public List<Ztree> selectProcessTree(String o) {
        List<VpmProcess> processList = (List<VpmProcess>) ctxMapCache.get("vpmProcesses");
        List<Ztree> ztrees = initZtree(processList);
        return ztrees;
    }

    @Override
    public List<Ztree> selectPersonTree(String o) {
        List<User> userList = (List<User>) ctxMapCache.get("dataUsers");
        List<Ztree> ztrees = initUserZtree(userList);
        return ztrees;
    }

    @Override
    public List<DataGroup> getVpmDataGroup() {
        List<DataGroup> ctxDataGroupsCache = (List<DataGroup>) ctxMapCache.get("dataGroups");
        if (ctxDataGroupsCache == null || ctxDataGroupsCache.size() == 0) {
            logger.info("info=========================：缓存中没有数据组列表");
        }
        return ctxDataGroupsCache;
    }

    @Override
    public List<Project> getVpmDataProject() {
        List<Project> ctxDataGroupsCache = (List<Project>) ctxMapCache.get("dataProjects");
        if (ctxDataGroupsCache == null || ctxDataGroupsCache.size() == 0) {
            logger.info("info=========================：缓存中没有项目列表");
        }
        return ctxDataGroupsCache;
    }

    @Override
    public List<SysRole> getVpmDataRole() {
        List<SysRole> ctxDataGroupsCache = (List<SysRole>) ctxMapCache.get("dataRoles");
        if (ctxDataGroupsCache == null || ctxDataGroupsCache.size() == 0) {
            logger.info("info=========================：缓存中没有角色列表");
        }
        return ctxDataGroupsCache;
    }

    @Override
    public List<Organization> getVpmDataOrganization() {
        List<Organization> ctxDataGroupsCache = (List<Organization>) ctxMapCache.get("dataOrganizations");
        if (ctxDataGroupsCache == null || ctxDataGroupsCache.size() == 0) {
            logger.info("info=========================：缓存中没有组织列表");
        }
        return ctxDataGroupsCache;
    }

    /**
     * 缓存中更新权限列表（mainAccessList），生成新的导入文件，完成导入
     *
     * @param access
     * @return
     */
    @Override
    public int insertAccess(Access access, String exportPath, String exportFileName) {
        //更新文件
        FileInputStream fis = null;
        InputStreamReader isr = null;
        BufferedReader bReader = null;
        OutputStreamWriter osw = null;
        FileOutputStream fos = null;
        BufferedWriter bWriter = null;
        List<String> ctxNames = new ArrayList<>();
        List<Access> mainAccessList = new ArrayList<>();
        String importFileName = exportFileName + "_" + new Date().getTime();
        try {
            String line;
            fis = new FileInputStream(exportPath + "/" + exportFileName);//定义输入文件
            fos = new FileOutputStream(exportPath + "/" + importFileName);//定义输出文件
            isr = new InputStreamReader(fis);//读取输入文件
            osw = new OutputStreamWriter(fos);//写入输入文件
            bReader = new BufferedReader(isr);//读取缓冲区
            bWriter = new BufferedWriter(osw);//写入缓存区
            StringBuffer addAccessStr = new StringBuffer("*PRIV 1;CTX=");
            addAccessStr.append(access.getContextName());
            if (StringUtils.isNotEmpty(access.getAccessType())) {
                addAccessStr.append(";PROCESS=" + access.getAccessType() + "." + access.getActionGroup());
            } else {
                addAccessStr.append(";PROCESS_GROUP=" + access.getActionGroup());
            }
            if (StringUtils.isNotEmpty(access.getDataGroup())) {
                addAccessStr.append(";" + access.getDataGroup());
            }
            int lineCount = 1;
            int endPrivline = 100000;
            while ((line = bReader.readLine()) != null) { //按行读取数据
                String wrtiteStr = line;
                if (line != null && line.startsWith("*CTX")) {
                    line = line.substring("*CTX ".length()).replaceAll(";", ".");
                    ctxNames.add(line);
                }

                if (line != null && line.startsWith("*PRIV")) {
                    Access accessRead = new Access();
                    String accessStr = line.split(" ")[1];
                    if (accessStr.indexOf("CTX=") == -1) {
                        //不是上下文的权限
                        //continue;
                    } else {
                        String[] accessInfo = accessStr.split(";");
                        //第一个数字暂时没用
                        for (int i = 1; i < accessInfo.length; i++) {
                            if (accessInfo[i].contains("CTX=")) {
                                String ctxName = accessInfo[i].split("=")[1];
                                if (ctxNames.contains(ctxName)) {
                                    ctxNames.remove(ctxName);
                                }
                                accessRead.setContextName(ctxName);
                            } else if (accessInfo[i].contains("PROCESS=")) {
                                String type = accessInfo[i].split("=")[1].split("\\.")[0];
                                String oper = accessInfo[i].split("=")[1].split("\\.")[1];
                                accessRead.setAccessType(type);
                                accessRead.setActionGroup(oper);
                            } else if (accessInfo[i].contains("PROCESS_GROUP=")) {
                                String operGroup = accessInfo[i].split("=")[1];
                                accessRead.setActionGroup(operGroup);
                            }

                            if (i == accessInfo.length - 2) {

                            }
                            if (i == accessInfo.length - 1 && !accessInfo[i].contains("PROCESS")) {
                                accessRead.setDataGroup(accessInfo[i]);
                            }
                        }
                        mainAccessList.add(accessRead);
                    }
                    endPrivline = lineCount;
                }

                if (lineCount == endPrivline + 1) {
                    bWriter.write(addAccessStr.toString() + "\r\n");
                }
                bWriter.write(wrtiteStr + "\r\n");//将拼结果按行写入出入文件中
                lineCount++;
            }
            for (String ctxName : ctxNames) {
                Access accessIn = new Access();
                accessIn.setContextName(ctxName);
                mainAccessList.add(accessIn);
            }
        } catch (FileNotFoundException e) {
            System.out.println("找不到文件");
        } catch (IOException e) {
            System.out.println("读取文件失败");
        } finally {
            try {
                bReader.close();//关闭读取缓冲区
                isr.close();//关闭读取文件内容
                fis.close();//关闭读取文件
                bWriter.close();//关闭写入缓存区
                osw.close();//关闭写入文件内容
                fos.close();//关闭写入文件
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //删除原来的文件
        File oldFile = new File(exportPath + "/" + exportFileName);
        if (oldFile.exists()) {
            oldFile.delete();
        }

        //更新旧的权限
        Access accessIn = new Access();
        accessIn.setContextName(access.getContextName());
        accessIn.setAccessType(access.getAccessType());
        accessIn.setActionGroup(access.getActionGroup());
        if (StringUtils.isNotEmpty(access.getDataGroup())) {
            accessIn.setDataGroup(access.getDataGroup().split(";")[1]);
        }
        mainAccessList.add(accessIn);
        ctxMapCache.add("mainAccessList", mainAccessList, 60 * 1000 * 5);

        //todo dumplinmg 调用cmd导入更新后的文件
        return 1;
    }


    /**
     * 缓存中更新上下文列表（ctxList），生成新的导入文件，完成导入
     *
     * @param
     * @return
     */
    @Override
    public int insertContext(VpmContext vpmContext, String exportPath, String exportFileName) {
        //更新文件
        FileInputStream fis = null;
        InputStreamReader isr = null;
        BufferedReader bReader = null;
        OutputStreamWriter osw = null;
        FileOutputStream fos = null;
        BufferedWriter bWriter = null;
        List<VpmContext> contextList = new ArrayList<>();
        String importFileName = exportFileName.split("_")[0] + "_" + new Date().getTime();
        try {
            String line;
            fis = new FileInputStream(exportPath + "/" + exportFileName);//定义输入文件
            fos = new FileOutputStream(exportPath + "/" + importFileName);//定义输出文件
            isr = new InputStreamReader(fis);//读取输入文件
            osw = new OutputStreamWriter(fos);//写入输入文件
            bReader = new BufferedReader(isr);//读取缓冲区
            bWriter = new BufferedWriter(osw);//写入缓存区
            StringBuffer addContextStr = new StringBuffer("*CTX ");
            addContextStr.append(vpmContext.getContextName()).append("\r\n");
            if (StringUtils.isNotEmpty(vpmContext.getAddPersonList())) {
                List<String> personList = vpmContext.getAddPersonList();
                for (int i = 0; i < personList.size(); i++) {
                    String[] personArr = personList.get(i).split(";");
                    for (int j = 0; j < personArr.length; j++) {
                        addContextStr.append("+Person " + personArr[j] + "\r\n");
                    }
                }
            }
            int lineCount = 1;
            int endPrivline = 100000;
            while ((line = bReader.readLine()) != null) { //按行读取数据
                String wrtiteStr = line;
                if (line != null && line.startsWith("*CTX")) {
                    line = line.substring("*CTX ".length()).replaceAll(";", ".");
                    VpmContext ctx = new VpmContext();
                    ctx.setContextName(line);
                    ctx.setContextProject(line.split("\\.")[2]);
                    ctx.setContextOrganization(line.split("\\.")[1]);
                    ctx.setContextRole(line.split("\\.")[0]);
                    //缓存增加context信息，key：contextName
                    contextList.add(ctx);
                    endPrivline = lineCount;
                }
                if (line.startsWith("+PERSON") || line.startsWith("+MASK") || line.startsWith("*CTX")) {
                    endPrivline = lineCount;
                }
                if (lineCount == endPrivline + 1) {
                    bWriter.write(addContextStr.toString() + "\r\n");
                }
                bWriter.write(wrtiteStr + "\r\n");//将拼结果按行写入出入文件中
                lineCount++;
            }
        } catch (FileNotFoundException e) {
            System.out.println("找不到文件");
        } catch (IOException e) {
            System.out.println("读取文件失败");
        } finally {
            try {
                bReader.close();//关闭读取缓冲区
                isr.close();//关闭读取文件内容
                fis.close();//关闭读取文件
                bWriter.close();//关闭写入缓存区
                osw.close();//关闭写入文件内容
                fos.close();//关闭写入文件
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //删除原来的文件
        File oldFile = new File(exportPath + "/" + exportFileName);
        if (oldFile.exists()) {
            oldFile.delete();
        }

        //更新旧的上下文
        contextList.add(vpmContext);
        ctxMapCache.add("ctxList", contextList, 60 * 1000 * 5);

        //todo dumplinmg 调用cmd导入更新后的文件
        return 1;
    }

    @Override
    public VpmContext getContextByName(String contextId) {
        List<VpmContext> ctxList = (List<VpmContext>) ctxMapCache.get("ctxList");
        for (VpmContext context : ctxList) {
            if (context.getContextName().equals(contextId)) {
                return context;
            }
        }
        return null;
    }


    private List<Ztree> initZtree(List<VpmProcess> processList) {
        return initZtree(processList, null);
    }

    private List<Ztree> initUserZtree(List<User> userList) {
        return initUserZtree(userList, null);
    }

    private List<Ztree> initZtree(List<VpmProcess> processList, List<String> roleDeptList) {

        List<Ztree> ztrees = new ArrayList<Ztree>();
        boolean isCheck = StringUtils.isNotNull(roleDeptList);
        for (VpmProcess process : processList) {
            Ztree ztree = new Ztree();
            ztree.setId((long) process.getProcessId());
            ztree.setpId((long) process.getProcessGroupId());
            ztree.setName(process.getProcessName());
            ztree.setTitle(process.getProcessName());
            if (isCheck) {
                //ztree.setChecked(roleDeptList.contains(dept.getDeptId() + dept.getDeptName()));
            }
            ztrees.add(ztree);
        }
        return ztrees;
    }

    private List<Ztree> initUserZtree(List<User> userList, List<String> roleDeptList) {

        List<Ztree> ztrees = new ArrayList<Ztree>();
        boolean isCheck = StringUtils.isNotNull(roleDeptList);
        long i = 0;
        Ztree z0tree = new Ztree();
        z0tree.setId(i);
        z0tree.setName("人员选择");
        z0tree.setTitle("人员选择");
        ztrees.add(z0tree);
        for (User user : userList) {
            Ztree ztree = new Ztree();
            ztree.setId((long) ++i);
            ztree.setpId((long) 0);
            ztree.setName(user.getUserFullName());
            ztree.setTitle(user.getUserFullName());
            if (isCheck) {
                //ztree.setChecked(roleDeptList.contains(dept.getDeptId() + dept.getDeptName()));
            }
            ztrees.add(ztree);
        }
        return ztrees;
    }
}
