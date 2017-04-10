package com.adaptive.ui.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


/**
 * Created by yeta on 2017/4/6/006.
 * 与User表对应的实体类
 */
@Entity
public class User {
    //用户id
    @Id
    @GeneratedValue
    private Integer userId;
    //部门id
    private Integer userDepaId;
    //班级id
    private Integer userClasId;
    //用户名
    private String userLoginname;
    //密码
    private String userPwd;
    //姓名
    private String userRealname;
    //性别
    private String userGender;
    //用户类型
    private Integer userType;
    //入学时间
    private String userYearOfEntrance;
    //
    private String userCadasExamNum;
    //
    private String userIdNum;
    //邮箱
    private String userEmail;
    //电话
    private String userPhoneNum;
    //地址
    private String userAddress;
    //教育水平
    private String userEduLevel;
    //
    private String userWorkunit;
    //
    private Boolean userVerify;
    //登陆状态
    private Integer userLoginstate;
    //登陆次数
    private Integer userLoginnum;
    //备注
    private String userRemark;
    //用户部门类型
    private Integer userDepartType;
    //用户头像
    private Integer userCoverPictureId;

    //setter和getter方法
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getUserDepaId() {
        return userDepaId;
    }

    public void setUserDepaId(Integer userDepaId) {
        this.userDepaId = userDepaId;
    }

    public Integer getUserClasId() {
        return userClasId;
    }

    public void setUserClasId(Integer userClasId) {
        this.userClasId = userClasId;
    }

    public String getUserLoginname() {
        return userLoginname;
    }

    public void setUserLoginname(String userLoginname) {
        this.userLoginname = userLoginname;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    public String getUserRealname() {
        return userRealname;
    }

    public void setUserRealname(String userRealname) {
        this.userRealname = userRealname;
    }

    public String getUserGender() {
        return userGender;
    }

    public void setUserGender(String userGender) {
        this.userGender = userGender;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public String getUserYearOfEntrance() {
        return userYearOfEntrance;
    }

    public void setUserYearOfEntrance(String userYearOfEntrance) {
        this.userYearOfEntrance = userYearOfEntrance;
    }

    public String getUserCadasExamNum() {
        return userCadasExamNum;
    }

    public void setUserCadasExamNum(String userCadasExamNum) {
        this.userCadasExamNum = userCadasExamNum;
    }

    public String getUserIdNum() {
        return userIdNum;
    }

    public void setUserIdNum(String userIdNum) {
        this.userIdNum = userIdNum;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPhoneNum() {
        return userPhoneNum;
    }

    public void setUserPhoneNum(String userPhoneNum) {
        this.userPhoneNum = userPhoneNum;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getUserEduLevel() {
        return userEduLevel;
    }

    public void setUserEduLevel(String userEduLevel) {
        this.userEduLevel = userEduLevel;
    }

    public String getUserWorkunit() {
        return userWorkunit;
    }

    public void setUserWorkunit(String userWorkunit) {
        this.userWorkunit = userWorkunit;
    }

    public Boolean getUserVerify() {
        return userVerify;
    }

    public void setUserVerify(Boolean userVerify) {
        this.userVerify = userVerify;
    }

    public Integer getUserLoginstate() {
        return userLoginstate;
    }

    public void setUserLoginstate(Integer userLoginstate) {
        this.userLoginstate = userLoginstate;
    }

    public Integer getUserLoginnum() {
        return userLoginnum;
    }

    public void setUserLoginnum(Integer userLoginnum) {
        this.userLoginnum = userLoginnum;
    }

    public String getUserRemark() {
        return userRemark;
    }

    public void setUserRemark(String userRemark) {
        this.userRemark = userRemark;
    }

    public Integer getUserDepartType() {
        return userDepartType;
    }

    public void setUserDepartType(Integer userDepartType) {
        this.userDepartType = userDepartType;
    }

    public Integer getUserCoverPictureId() {
        return userCoverPictureId;
    }

    public void setUserCoverPictureId(Integer userCoverPictureId) {
        this.userCoverPictureId = userCoverPictureId;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userDepaId=" + userDepaId +
                ", userClasId=" + userClasId +
                ", userLoginname='" + userLoginname + '\'' +
                ", userPwd='" + userPwd + '\'' +
                ", userRealname='" + userRealname + '\'' +
                ", userGender='" + userGender + '\'' +
                ", userType=" + userType +
                ", userYearOfEntrance='" + userYearOfEntrance + '\'' +
                ", userCadasExamNum='" + userCadasExamNum + '\'' +
                ", userIdNum='" + userIdNum + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", userPhoneNum='" + userPhoneNum + '\'' +
                ", userAddress='" + userAddress + '\'' +
                ", userEduLevel='" + userEduLevel + '\'' +
                ", userWorkunit='" + userWorkunit + '\'' +
                ", userVerify=" + userVerify +
                ", userLoginstate=" + userLoginstate +
                ", userLoginnum=" + userLoginnum +
                ", userRemark='" + userRemark + '\'' +
                ", userDepartType=" + userDepartType +
                ", userCoverPictureId=" + userCoverPictureId +
                '}';
    }
}
