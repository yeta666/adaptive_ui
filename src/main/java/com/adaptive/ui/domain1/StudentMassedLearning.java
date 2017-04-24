package com.adaptive.ui.domain1;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 与studentmassedlearning表对应的实体类
 * Created by yeta on 2017/4/6/006.
 */
@Entity
public class StudentMassedLearning {
	//id
	@Id
	@GeneratedValue
	private Integer smleId;

	//学生id
	private Integer smleUserId;

	//集中学习id
	private Integer smleMaleId;

	//构造方法
	public StudentMassedLearning() {
	}

	//getter和setter方法
	public Integer getSmleId() {
		return smleId;
	}

	public void setSmleId(Integer smleId) {
		this.smleId = smleId;
	}

	public Integer getSmleUserId() {
		return smleUserId;
	}

	public void setSmleUserId(Integer smleUserId) {
		this.smleUserId = smleUserId;
	}

	public Integer getSmleMaleId() {
		return smleMaleId;
	}

	public void setSmleMaleId(Integer smleMaleId) {
		this.smleMaleId = smleMaleId;
	}
}