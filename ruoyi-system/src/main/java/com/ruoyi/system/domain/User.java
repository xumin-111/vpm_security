package com.ruoyi.system.domain;

/**
 * @author xumin
 * @create 2020-12-21 14:39
 */

public class User {


    /**
     * 用户ID
     */
    private String userId;

    /**
     * 用户名
     */
    private String userFirstName;

    /**
     * 全名
     */
    private String userFullName;

    /**
     * 用户姓
     */
    private String userLastName;

    /**
     * 用户电话
     */
    private String userPhone;

    /**
     * 用户地址
     */
    private String userAddress;

    /**
     * 用户电话
     */
    private String userEmail;

    /**
     * 用户组织
     */
    private String deptName;

    /**
     * 用户状态（0正常 1锁定）
     */
    private String status;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 用户密级
     */
    private String secret;

    /**
     * 用户登录方式
     */
    private String loginType;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getUserFullName() {
        return userFullName;
    }

    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getLoginType() {
        return loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }
}
