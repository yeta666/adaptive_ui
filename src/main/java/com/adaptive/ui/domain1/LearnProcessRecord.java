package com.adaptive.ui.domain1;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * 与learningprocessrecord表对应的实体类
 * Created by yeta on 2017/4/6/006.
 */
@Entity
public class LearnProcessRecord {
	//id
	@Id
	@GeneratedValue
	private Integer lpreId;

	//学生id
	private Integer lpreUserId;

	//课程章节id
	private Integer lpreChapId;

	//学习开始时间
	private Date lpreBegintime;

	//学习结束时间
	private Date lpreEndtime;

	//学习时长
	private Integer lpreLearntime;

	//学习内容
	private String lpreContent;

	//
	private Integer lpreResoId;

	//构造方法
	public LearnProcessRecord() {
	}

	//getter和setter方法
	public Integer getLpreResoId() {
		return lpreResoId;
	}

	public void setLpreResoId(Integer lpreResoId) {
		this.lpreResoId = lpreResoId;
	}

	public Integer getLpreId() {
		return lpreId;
	}

	public void setLpreId(Integer lpreId) {
		this.lpreId = lpreId;
	}

	public Integer getLpreUserId() {
		return lpreUserId;
	}

	public void setLpreUserId(Integer lpreUserId) {
		this.lpreUserId = lpreUserId;
	}

	public Integer getLpreChapId() {
		return lpreChapId;
	}

	public void setLpreChapId(Integer lpreChapId) {
		this.lpreChapId = lpreChapId;
	}

	public Date getLpreBegintime() {
		return lpreBegintime;
	}

	public void setLpreBegintime(Date lpreBegintime) {
		this.lpreBegintime = lpreBegintime;
	}

	public Date getLpreEndtime() {
		return lpreEndtime;
	}

	public void setLpreEndtime(Date lpreEndtime) {
		this.lpreEndtime = lpreEndtime;
	}

	public Integer getLpreLearntime() {
		return lpreLearntime;
	}

	public void setLpreLearntime(Integer lpreLearntime) {
		this.lpreLearntime = lpreLearntime;
	}

	public String getLpreContent() {
		return lpreContent;
	}

	public void setLpreContent(String lpreContent) {
		this.lpreContent = lpreContent;
	}

}