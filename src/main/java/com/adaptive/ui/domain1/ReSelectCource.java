package com.adaptive.ui.domain1;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * 与re_selectcourse表对应的实体类
 * Created by yeta on 2017/4/6/006.
 */
@Entity
public class ReSelectCource {
	//id
	@Id
	@GeneratedValue
	private Integer rscoId;

	//选课人id
	private Integer rscoUserId;

	//课程id
	private Integer rscoCourId;

	//标识课程是否通过
	private Integer rscoVerify;

	//选课时间
	private Date rscoTime;

	//集中学习分数
	private Float rscoMassedlearnscore;

	//学习次数
	private Float rscoLoginscore;

	//学习时间分数
	private Float rscoLearntimescore;

	//参与讨论分数
	private Float rscoBbsdiscussscore;

	//主观评价分数
	private Float rscoSubassessscore;

	//在线自测分数
	private Float rscoTestscore;

	//总分
	private Float rscoTotalscore;

	//课程是否完结
	private Integer rscoState;

	//
	private boolean scoreChanged = false;

	//选课是否有效
	private int rscoValid;

	//选课人学校名称
	private String rscoDepartName;

	//选课人班级名称
	private String rscoClassName;

	//选课人年级名称
	private String rscoGradeName;

	//选课人年级id
	private int rscoGradeId;

	//构造方法
	public ReSelectCource() {
	}

	//getter和setterf方法
    public String getRscoDepartName() {
        return rscoDepartName;
    }

    public void setRscoDepartName(String rscoDepartName) {
        this.rscoDepartName = rscoDepartName;
    }

    public String getRscoClassName() {
        return rscoClassName;
    }

    public void setRscoClassName(String rscoClassName) {
        this.rscoClassName = rscoClassName;
    }

    public String getRscoGradeName() {
        return rscoGradeName;
    }

    public void setRscoGradeName(String rscoGradeName) {
        this.rscoGradeName = rscoGradeName;
    }

    public int getRscoGradeId() {
        return rscoGradeId;
    }

    public void setRscoGradeId(int rscoGradeId) {
        this.rscoGradeId = rscoGradeId;
    }

    public boolean isScoreChanged() {
        return scoreChanged;
    }

    public void setScoreChanged(boolean scoreChanged) {
        this.scoreChanged = scoreChanged;
    }

    public int getRscoValid() {
        return rscoValid;
    }

    public void setRscoValid(int rscoValid) {
        this.rscoValid = rscoValid;
    }

	public Integer getRscoId() {
		return rscoId;
	}

	public void setRscoId(Integer rscoId) {
		this.rscoId = rscoId;
	}

	public Integer getRscoUserId() {
		return rscoUserId;
	}

	public void setRscoUserId(Integer rscoUserId) {
		this.rscoUserId = rscoUserId;
	}

	public Integer getRscoCourId() {
		return rscoCourId;
	}

	public void setRscoCourId(Integer rscoCourId) {
		this.rscoCourId = rscoCourId;
	}

	public Integer getRscoVerify() {
		return rscoVerify;
	}

	public void setRscoVerify(Integer rscoVerify) {
		this.rscoVerify = rscoVerify;
	}

	public Date getRscoTime() {
		return rscoTime;
	}

	public void setRscoTime(Date rscoTime) {
		this.rscoTime = rscoTime;
	}

	public Float getRscoMassedlearnscore() {
		return rscoMassedlearnscore;
	}

	public void setRscoMassedlearnscore(Float rscoMassedlearnscore) {
		this.rscoMassedlearnscore = rscoMassedlearnscore;
	}

	public Float getRscoLoginscore() {
		return rscoLoginscore;
	}

	public void setRscoLoginscore(Float rscoLoginscore) {
		this.rscoLoginscore = rscoLoginscore;
	}

	public Float getRscoLearntimescore() {
		return rscoLearntimescore;
	}

	public void setRscoLearntimescore(Float rscoLearntimescore) {
		this.rscoLearntimescore = rscoLearntimescore;
	}

	public Float getRscoBbsdiscussscore() {
		return rscoBbsdiscussscore;
	}

	public void setRscoBbsdiscussscore(Float rscoBbsdiscussscore) {
		this.rscoBbsdiscussscore = rscoBbsdiscussscore;
	}

	public Float getRscoSubassessscore() {
		return rscoSubassessscore;
	}

	public void setRscoSubassessscore(Float rscoSubassessscore) {
		this.rscoSubassessscore = rscoSubassessscore;
	}

	public Float getRscoTestscore() {
		return rscoTestscore;
	}

	public void setRscoTestscore(Float rscoTestscore) {
		this.rscoTestscore = rscoTestscore;
	}

	public Float getRscoTotalscore() {
		return rscoTotalscore;
	}

	public void setRscoTotalscore(Float rscoTotalscore) {
		this.rscoTotalscore = rscoTotalscore;
	}

	public Integer getRscoState() {
		return rscoState;
	}

	public void setRscoState(Integer rscoState) {
		this.rscoState = rscoState;
	}
}