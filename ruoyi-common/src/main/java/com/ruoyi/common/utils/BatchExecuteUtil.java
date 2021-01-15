package com.ruoyi.common.utils;

import java.io.*;
import java.util.Date;

public class BatchExecuteUtil {

    /**
     * 返回值
     * @param locationCmd
     */
    public static String  callCmd(String locationCmd){
        StringBuilder sb = new StringBuilder();
        try {
            Process child = Runtime.getRuntime().exec(locationCmd);
            InputStream in = child.getInputStream();
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(in));
            String line;
            while((line=bufferedReader.readLine())!=null)
            {
                sb.append(line + "\n");
            }
            in.close();
            try {
                child.waitFor();
            } catch (InterruptedException e) {
                System.out.println(e);
            }
            System.out.println("callCmd execute finished");
        } catch (IOException e) {
            System.out.println(e);
        }
        return sb.toString();
    }

    /**
     * 无返回值
     * @param cmd
     */
    public static void exeCmd(String cmd){
        Process process = null;
        Runtime runtime = Runtime.getRuntime();
        try {
            process = runtime.exec(cmd);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void createPassPolicyBatchAndExecute(Integer minPsLength,Integer maxPsLength,Integer changePeriod,Integer allowWrongCount,Integer complexity,String batchPath){
        //生成文件
        String fileName = "policy.bat";
        String pathMark = batchPath+"/"+new Date().getTime()+"";
        File batchFile = new File(pathMark);
        if(!batchFile.exists()){
            batchFile.mkdirs();
        }
        FileWriter writer = null;
        try {
            writer=new FileWriter(pathMark+"/"+fileName);
            writer.write("@echo off\n");
            writer.write("cacls.exe \"%SystemDrive%\\System Volume Information\" >nul 2>nul\n");
            writer.write("if %errorlevel%==0 goto Admin\n");
            writer.write("if exist \"%temp%\\getadmin.vbs\" del /f /q \"%temp%\\getadmin.vbs\"\n");
            writer.write("echo Set RequestUAC = CreateObject^(\"Shell.Application\"^)>\"%temp%\\getadmin.vbs\"\n");
            writer.write("echo RequestUAC.ShellExecute \"%~s0\",\"\",\"\",\"runas\",1 >>\"%temp%\\getadmin.vbs\"\n");
            writer.write("echo WScript.Quit >>\"%temp%\\getadmin.vbs\"\n");
            writer.write("\"%temp%\\getadmin.vbs\" /f\n");
            writer.write("if exist \"%temp%\\getadmin.vbs\" del /f /q \"%temp%\\getadmin.vbs\"\n");
            writer.write("exit\n");
            writer.write("\n");
            writer.write(":Admin\n");
            writer.write("echo [version]>gp.inf\n");
            writer.write("echo signature=\"$CHICAGO$\">>gp.inf\n");
            writer.write("echo [System Access]>>gp.inf\n");
            writer.write("echo MinimumPasswordAge = 15 >>gp.inf\n");
            writer.write("echo MaximumPasswordAge = 30 >>gp.inf\n");
            writer.write("echo MinimumPasswordLength = 6 >>gp.inf\n");
            writer.write("echo PasswordComplexity = 1 >>gp.inf\n");
            writer.write("echo LockoutBadCount = 5 >>gp.inf\n");
            writer.write("echo LockoutDuration = 30 >>gp.inf\n");
            writer.write("secedit /configure /db gp.sdb /cfg gp.inf\n");
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(writer != null){
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        //执行bat
        exeCmd(pathMark+"/"+fileName);
    }

    /*
    public static void main(String[] args) {
        //String s = callCmd("cmd.exe /C start /b C:\\Users\\MrDumpling\\Desktop\\aa.bat");
        //exeCmd("C:\\Users\\MrDumpling\\Desktop\\policy.bat");
        //System.out.println("xx..."+s);
        createPassPolicyBatchAndExecute(1,1,1,1,1,"D:/vpmSecurity/passwordPolicy");
    }*/
}
