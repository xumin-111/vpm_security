package com.ruoyi.system.domain;

/**
 * @author xumin
 * 涉密数据统计
 * @create 2020-12-22 12:59
 */

public class History {

    /**
     * 项目
     */
    private String project;

    /**
     * 数据密级
     */
    private String secret;

    /**
     * 内部数量
     */
    private String secretNum1;

    /**
     * 秘密数量
     */
    private String secretNum2;

    /**
     * 机密数量
     */
    private String secretNum3;

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getSecretNum1() {
        return secretNum1;
    }

    public void setSecretNum1(String secretNum1) {
        this.secretNum1 = secretNum1;
    }

    public String getSecretNum2() {
        return secretNum2;
    }

    public void setSecretNum2(String secretNum2) {
        this.secretNum2 = secretNum2;
    }

    public String getSecretNum3() {
        return secretNum3;
    }

    public void setSecretNum3(String secretNum3) {
        this.secretNum3 = secretNum3;
    }
}
