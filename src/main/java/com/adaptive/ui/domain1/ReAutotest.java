package com.adaptive.ui.domain1;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 与re_autotest表对应的实体类
 * Created by yeta on 2017/4/6/006.
 */
@Entity
public class ReAutotest {
	//id
	@Id
	@GeneratedValue
	private Integer rateId;

	//测试者的id
	private Integer rateUserId;

	//对应的考试id
	private Integer rateExinId;

	//测试分数
	private Float rateScore;

	//开始测试时间
	private Date rateStarttime;

	//提交测试时间
	private Date rateSubmittime;

	//是否完成测试
	private Boolean rateIsfinished;

	//构造方法
	public ReAutotest() {
	}

	//getter和setter方法
	public Integer getRateId() {
		return rateId;
	}

	public void setRateId(Integer rateId) {
		this.rateId = rateId;
	}

	public Integer getRateUserId() {
		return rateUserId;
	}

	public void setRateUserId(Integer rateUserId) {
		this.rateUserId = rateUserId;
	}

	public Integer getRateExinId() {
		return rateExinId;
	}

	public void setRateExinId(Integer rateExinId) {
		this.rateExinId = rateExinId;
	}

	public Float getRateScore() {
		return rateScore;
	}

	public void setRateScore(Float rateScore) {
		this.rateScore = rateScore;
	}

	public Date getRateStarttime() {
		return rateStarttime;
	}

	public void setRateStarttime(Date rateStarttime) {
		this.rateStarttime = rateStarttime;
	}

	public Date getRateSubmittime() {
		return rateSubmittime;
	}

	public void setRateSubmittime(Date rateSubmittime) {
		this.rateSubmittime = rateSubmittime;
	}

	public Boolean getRateIsfinished() {
		return rateIsfinished;
	}

	public void setRateIsfinished(Boolean rateIsfinished) {
		this.rateIsfinished = rateIsfinished;
	}
}