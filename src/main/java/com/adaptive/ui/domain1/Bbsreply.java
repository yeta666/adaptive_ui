package com.adaptive.ui.domain1;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 与bbsreply表对应的实体类
 * Created by yeta on 2017/4/6/006.
 */
@Entity
public class Bbsreply {
	//id
	@Id
	@GeneratedValue
	private Integer bbreId;

	//帖子id
	private Integer bbreBbpoId;

	//回帖人id
	private Integer bbreUserId;

	//时间
	private Date bbreTime;

	//是否有附件
	private Boolean bbreHasattach;

	//回复内容
	private String bbreContent;

	//构造方法
	public Bbsreply() {
	}

	//getter和setter方法
	public Integer getBbreId() {
		return bbreId;
	}

	public void setBbreId(Integer bbreId) {
		this.bbreId = bbreId;
	}

	public Integer getBbreBbpoId() {
		return bbreBbpoId;
	}

	public void setBbreBbpoId(Integer bbreBbpoId) {
		this.bbreBbpoId = bbreBbpoId;
	}

	public Integer getBbreUserId() {
		return bbreUserId;
	}

	public void setBbreUserId(Integer bbreUserId) {
		this.bbreUserId = bbreUserId;
	}

	public Date getBbreTime() {
		return bbreTime;
	}

	public void setBbreTime(Date bbreTime) {
		this.bbreTime = bbreTime;
	}

	public Boolean getBbreHasattach() {
		return bbreHasattach;
	}

	public void setBbreHasattach(Boolean bbreHasattach) {
		this.bbreHasattach = bbreHasattach;
	}

	public String getBbreContent() {
		return bbreContent;
	}

	public void setBbreContent(String bbreContent) {
		this.bbreContent = bbreContent;
	}
}