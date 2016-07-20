package com.mf.common.model;


import java.io.Serializable;
import java.util.Date;



import org.springframework.format.annotation.DateTimeFormat;

/**
 * Created by pony on 2016/7/5.
 */
public class UserInfo implements Serializable {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -6305079839940354722L;

    /**
     * 用户登录账号.
     */
    protected String userId;

    /**
     * 用户的名字, 小于等于30位长度.
     */
    protected String userName;

    /**
     * 用户的登录密码.
     */
    protected String loginPassword;

    /**
     * 用户员工号, 可能为空.
     */
    protected String employeeNo;

    /**
     * 用户属于的组织机构中文名字.
     */
    /*protected String orgName;*/

    /**
     * 用户属于的组织机构代码.
     */
    protected String orgCode;

    /**
     * 用户状态
     */
    protected String userStatus;

    /**
     * 用户类型
     */
    protected String userType;

    /**
     * 用户电邮.
     */
    protected String email;

    /**
     *
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    protected Date entryDate;

    /**
     *
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    protected Date pwdExpiredDate;

    /**
     *
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    protected Date leaveDate;

    /**
     * 身份证号
     */
    protected String identityNo;

    /**
     * 别名
     */
    protected String alias;
    /**
     *
     */
    public UserInfo() {
    }

    /**
     * @param userId
     */
    public UserInfo(String userId) {
        this.userId = userId;
    }

    /**
     * @param userId
     * @param userName
     */
    public UserInfo(String userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }

    /**
     * @return the userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @param userId
     *            the userId to set
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName
     *            the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return the loginPassword
     */
    public String getLoginPassword() {
        return loginPassword;
    }

    /**
     * @param loginPassword
     *            the loginPassword to set
     */
    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword;
    }

    /**
     * @return the employeeNo
     */
    public String getEmployeeNo() {
        return employeeNo;
    }

    /**
     * @param employeeNo
     *            the employeeNo to set
     */
    public void setEmployeeNo(String employeeNo) {
        this.employeeNo = employeeNo;
    }

    /**
     * @return the orgName
     */
   /* public String getOrgName() {
        return orgName;
    }*/

    /**
     * @param orgName
     *            the orgName to set
     */
   /* public void setOrgName(String orgName) {
        this.orgName = orgName;
    }*/

    /**
     * @return the orgCode
     */
    public String getOrgCode() {
        return orgCode;
    }

    /**
     * @param orgCode
     *            the orgCode to set
     */
    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    /**
     * @return the userStatus
     */
    public String getUserStatus() {
        return userStatus;
    }

    /**
     * @param userStatus
     *            the userStatus to set
     */
    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    /**
     * @return the userType
     */
    public String getUserType() {
        return userType;
    }

    /**
     * @param userType
     *            the userType to set
     */
    public void setUserType(String userType) {
        this.userType = userType;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email
     *            the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the entryDate
     */
    public Date getEntryDate() {
        return entryDate;
    }

    /**
     * @param entryDate
     *            the entryDate to set
     */
    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }

    /**
     * @return the pwdExpiredDate
     */
    public Date getPwdExpiredDate() {
        return pwdExpiredDate;
    }

    /**
     * @param pwdExpiredDate
     *            the pwdExpiredDate to set
     */
    public void setPwdExpiredDate(Date pwdExpiredDate) {
        this.pwdExpiredDate = pwdExpiredDate;
    }

    /**
     * @return the leaveDate
     */
    public Date getLeaveDate() {
        return leaveDate;
    }

    /**
     * @param leaveDate
     *            the leaveDate to set
     */
    public void setLeaveDate(Date leaveDate) {
        this.leaveDate = leaveDate;
    }

    /**
     * @return the identityNo
     */
    public String getIdentityNo() {
        return identityNo;
    }

    /**
     * @param identityNo
     *            the identityNo to set
     */
    public void setIdentityNo(String identityNo) {
        this.identityNo = identityNo;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((userId == null) ? 0 : userId.hashCode());
        return result;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        UserInfo other = (UserInfo) obj;
        if (userId == null) {
            if (other.userId != null)
                return false;
        } else if (!userId.equals(other.userId))
            return false;
        return true;
    }


}
