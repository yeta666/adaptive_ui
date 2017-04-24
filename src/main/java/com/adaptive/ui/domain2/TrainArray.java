package com.adaptive.ui.domain2;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 与训练集表对应的实体类
 * Created by yeta on 2017/4/19/019.
 */
@Entity
public class TrainArray {
    //id
    @Id
    @GeneratedValue
    private Integer id;

    //性别
    private String gender;

    //入学时间
    private String entranceTime;

    //教育水平
    private String educationalLevel;

    //登陆次数
    private String loginNum;

    //发布讨论的次数
    private String bbsPostNum;

    //发布讨论的时间
    private String bbsPostTime;

    //发布讨论的质量
    private String bbsPostQuality;

    //回复讨论的次数
    private String bbsReplyNum;

    //回复讨论的时间
    private String bbsReplyTime;

    //学习所有课程的总次数
    private String learnAllCourseNum;

    //学习课程的开始时间
    private String learnCourseBeginTime;

    //参与测试的次数
    private String testNum;

    //参与测试的分数
    private String testScore;

    //参与测试的开始时间
    private String testBeginTime;

    //参与集中学习的次数
    private String massedLearningNum;

    //选课数
    private String chooseCourseNum;

    //选课分数各部分比例
    private String chooseCoursePartsProportion;

    //用户类型
    private String userType;

    //构造方法
    public TrainArray() {
    }

    //getter和setter方法
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEntranceTime() {
        return entranceTime;
    }

    public void setEntranceTime(String entranceTime) {
        this.entranceTime = entranceTime;
    }

    public String getEducationalLevel() {
        return educationalLevel;
    }

    public void setEducationalLevel(String educationalLevel) {
        this.educationalLevel = educationalLevel;
    }

    public String getLoginNum() {
        return loginNum;
    }

    public void setLoginNum(String loginNum) {
        this.loginNum = loginNum;
    }

    public String getBbsPostNum() {
        return bbsPostNum;
    }

    public void setBbsPostNum(String bbsPostNum) {
        this.bbsPostNum = bbsPostNum;
    }

    public String getBbsPostTime() {
        return bbsPostTime;
    }

    public void setBbsPostTime(String bbsPostTime) {
        this.bbsPostTime = bbsPostTime;
    }

    public String getBbsPostQuality() {
        return bbsPostQuality;
    }

    public void setBbsPostQuality(String bbsPostQuality) {
        this.bbsPostQuality = bbsPostQuality;
    }

    public String getBbsReplyNum() {
        return bbsReplyNum;
    }

    public void setBbsReplyNum(String bbsReplyNum) {
        this.bbsReplyNum = bbsReplyNum;
    }

    public String getBbsReplyTime() {
        return bbsReplyTime;
    }

    public void setBbsReplyTime(String bbsReplyTime) {
        this.bbsReplyTime = bbsReplyTime;
    }

    public String getLearnAllCourseNum() {
        return learnAllCourseNum;
    }

    public void setLearnAllCourseNum(String learnAllCourseNum) {
        this.learnAllCourseNum = learnAllCourseNum;
    }

    public String getLearnCourseBeginTime() {
        return learnCourseBeginTime;
    }

    public void setLearnCourseBeginTime(String learnCourseBeginTime) {
        this.learnCourseBeginTime = learnCourseBeginTime;
    }

    public String getTestNum() {
        return testNum;
    }

    public void setTestNum(String testNum) {
        this.testNum = testNum;
    }

    public String getTestScore() {
        return testScore;
    }

    public void setTestScore(String testScore) {
        this.testScore = testScore;
    }

    public String getTestBeginTime() {
        return testBeginTime;
    }

    public void setTestBeginTime(String testBeginTime) {
        this.testBeginTime = testBeginTime;
    }

    public String getMassedLearningNum() {
        return massedLearningNum;
    }

    public void setMassedLearningNum(String massedLearningNum) {
        this.massedLearningNum = massedLearningNum;
    }

    public String getChooseCourseNum() {
        return chooseCourseNum;
    }

    public void setChooseCourseNum(String chooseCourseNum) {
        this.chooseCourseNum = chooseCourseNum;
    }

    public String getChooseCoursePartsProportion() {
        return chooseCoursePartsProportion;
    }

    public void setChooseCoursePartsProportion(String chooseCoursePartsProportion) {
        this.chooseCoursePartsProportion = chooseCoursePartsProportion;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

}
