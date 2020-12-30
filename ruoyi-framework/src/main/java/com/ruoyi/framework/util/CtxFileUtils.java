package com.ruoyi.framework.util;

import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.ctxCache.CtxMapCache;
import com.ruoyi.system.domain.Access;
import com.ruoyi.system.domain.VpmContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * 上下文文件帮助类
 */
public class CtxFileUtils {
    private static Logger logger = LoggerFactory.getLogger(CtxFileUtils.class);

    private static CtxMapCache ctxMapCache = new CtxMapCache();

    public static void readCtxFile(String strFile,Access accessParam){
        List<Access> accessList = new ArrayList<>();
        List<String> ctxNames = new ArrayList<>();
        try {
            File file = new File(strFile);
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String strLine = null;
            int lineCount = 1;
            int ctxBeginLine = 100000;
            int accessBeginLine = 100000;
            while(null != (strLine = bufferedReader.readLine())){
                if(strLine != null && strLine.indexOf("RscContext") != -1){
                    ctxBeginLine = lineCount;
                }
                if(strLine != null && strLine.startsWith("*CTX") && lineCount > ctxBeginLine){
                    strLine = strLine.substring("*CTX ".length()).replaceAll(";",".");
                    VpmContext ctx = new VpmContext();
                    ctx.setContextName(strLine);
                    ctx.setContextProject(strLine.split("\\.")[2]);
                    ctx.setContextOrganization(strLine.split("\\.")[1]);
                    ctx.setContextRole(strLine.split("\\.")[0]);
                    //缓存增加context信息，key：contextName
                    ctxMapCache.add(strLine,ctx,60 * 1000 * 5);

                    if(accessParam != null && StringUtils.isNotEmpty(accessParam.getContextName())){
                        if(strLine.indexOf(accessParam.getContextName()) != -1){
                            ctxNames.add(strLine);
                        }
                    }else{
                        ctxNames.add(strLine);
                    }
                    logger.debug("第[" + lineCount + "]行context数据:[" + strLine + "]");
                }

                if(strLine != null && strLine.indexOf("privilege list") != -1){
                    accessBeginLine = lineCount;
                }
                if(strLine != null && strLine.startsWith("*PRIV") && lineCount > accessBeginLine){
                    Access access = new Access();
                    String accessStr = strLine.split(" ")[1];
                    if(accessStr.indexOf("CTX=") == -1){
                        //不是上下文的权限
                        continue;
                    }
                    String[] accessInfo = accessStr.split(";");
                    //第一个数字暂时没用
                    for(int i=1;i<accessInfo.length;i++){
                        if(accessInfo[i].contains("CTX=")){
                            String ctxName = accessInfo[i].split("=")[1];
                            if(ctxNames.contains(ctxName)){
                                ctxNames.remove(ctxName);
                            }
                            access.setContextName(ctxName);
                        }else if(accessInfo[i].contains("PROCESS=")){
                            String type = accessInfo[i].split("=")[1].split("\\.")[0];
                            String oper = accessInfo[i].split("=")[1].split("\\.")[1];
                            access.setAccessType(type);
                            access.setActionGroup(oper);
                        }else if(accessInfo[i].contains("PROCESS_GROUP=")){
                            String operGroup = accessInfo[i].split("=")[1];
                            access.setActionGroup(operGroup);
                        }

                        if(i == accessInfo.length-2){

                        }
                        if(i == accessInfo.length-1 && !accessInfo[i].contains("PROCESS")){
                            access.setDataGroup(accessInfo[i]);
                        }
                    }
                    if(accessParam != null && StringUtils.isNotEmpty(accessParam.getContextName())){
                        if(access.getContextName().indexOf(accessParam.getContextName()) != -1){
                            accessList.add(access);
                        }
                    }
                    //ctxMapCache.add("mainAccessList",strLine,60 * 1000 * 5);
                    logger.debug("第[" + lineCount + "]行priv数据:[" + accessStr + "]");
                }
                lineCount++;
            }
            for (String ctxName : ctxNames) {
                Access access = new Access();
                access.setContextName(ctxName);
                accessList.add(access);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        System.out.println(accessList);
    }

    public static void main(String[] args) {
        Access accessParam = new Access();
        accessParam.setContextName("VPMADMIN");
        CtxFileUtils.readCtxFile("E:/vpmContext/PnO",accessParam);

        /*Object rscContext = ctxMapCache.get("RscContext");
        System.out.println("xxx"+rscContext.toString());*/

    }

}
