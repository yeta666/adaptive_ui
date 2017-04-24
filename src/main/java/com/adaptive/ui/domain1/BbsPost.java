package com.adaptive.ui.domain1;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 与bbspost表对应的实体类
 * Created by yeta on 2017/4/6/006.
 */
@Entity
public class BbsPost {
    //id
    @Id
    @GeneratedValue
    private Integer bbpoId;

    //课程id
    private Integer bbpoCourId;

    //发帖人id
    private Integer bbpoUserId;

    //标题
    private String bbpoTitle;

    //内容长度
    private Integer bbpoContLen;

    //发帖时间
    private Date bbpoTime;

    //最近更新帖子时间
    private Date bbpoUpdatetime;

    //帖子访问次数
    private Integer bbpoVisitnum;

    //帖子回复次数
    private Integer bbpoReplynum;

    //是否为精品
    private Boolean bbpoIsbest;

    //是否置顶
    private Boolean bbpoIstop;

    //是否有附件
    private Boolean bbpoHasattach;

    //内容
    private String bbpoContent;

    //帖子所属部门名称
    private String bbpoUserName;

    //帖子所属部门id
    private Integer bbpoUserDepartId;

    //帖子所属课程名称
    private String bbpoCourName;

    //构造方法
    public BbsPost() {
    }

    //setter和setter方法
    public String getBbpoCourName() {
        return bbpoCourName;
    }

    public void setBbpoCourName(String bbpoCourName) {
        this.bbpoCourName = bbpoCourName;
    }

    public Integer getBbpoUserDepartId() {
        return bbpoUserDepartId;
    }

    public void setBbpoUserDepartId(Integer bbpoUserDepartId) {
        this.bbpoUserDepartId = bbpoUserDepartId;
    }

    public String getBbpoUserName() {
        return bbpoUserName;
    }

    public void setBbpoUserName(String bbpoUserName) {
        this.bbpoUserName = bbpoUserName;
    }

    public Integer getBbpoId() {
        return bbpoId;
    }

    public void setBbpoId(Integer bbpoId) {
        this.bbpoId = bbpoId;
    }

    public Integer getBbpoCourId() {
        return bbpoCourId;
    }

    public void setBbpoCourId(Integer bbpoCourId) {
        this.bbpoCourId = bbpoCourId;
    }

    public Integer getBbpoUserId() {
        return bbpoUserId;
    }

    public void setBbpoUserId(Integer bbpoUserId) {
        this.bbpoUserId = bbpoUserId;
    }

    public String getBbpoTitle() {
        return bbpoTitle;
    }

    public void setBbpoTitle(String bbpoTitle) {
        this.bbpoTitle = bbpoTitle;
    }

    public Integer getBbpoContLen() {
        return bbpoContLen;
    }

    public void setBbpoContLen(Integer bbpoContLen) {
        this.bbpoContLen = bbpoContLen;
    }

    public Date getBbpoTime() {
        return bbpoTime;
    }

    public void setBbpoTime(Date bbpoTime) {
        this.bbpoTime = bbpoTime;
    }

    public Date getBbpoUpdatetime() {
        return bbpoUpdatetime;
    }

    public void setBbpoUpdatetime(Date bbpoUpdatetime) {
        this.bbpoUpdatetime = bbpoUpdatetime;
    }

    public Integer getBbpoVisitnum() {
        return bbpoVisitnum;
    }

    public void setBbpoVisitnum(Integer bbpoVisitnum) {
        this.bbpoVisitnum = bbpoVisitnum;
    }

    public Integer getBbpoReplynum() {
        return bbpoReplynum;
    }

    public void setBbpoReplynum(Integer bbpoReplynum) {
        this.bbpoReplynum = bbpoReplynum;
    }

    public Boolean getBbpoIsbest() {
        return bbpoIsbest;
    }

    public void setBbpoIsbest(Boolean bbpoIsbest) {
        this.bbpoIsbest = bbpoIsbest;
    }

    public Boolean getBbpoIstop() {
        return bbpoIstop;
    }

    public void setBbpoIstop(Boolean bbpoIstop) {
        this.bbpoIstop = bbpoIstop;
    }

    public Boolean getBbpoHasattach() {
        return bbpoHasattach;
    }

    public void setBbpoHasattach(Boolean bbpoHasattach) {
        this.bbpoHasattach = bbpoHasattach;
    }

    public String getBbpoContent() {
        return bbpoContent;
    }

    public void setBbpoContent(String bbpoContent) {
        this.bbpoContent = bbpoContent;
    }
}