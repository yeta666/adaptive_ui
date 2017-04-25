package com.adaptive.ui.domain2;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 与questionary_answers对应的实体类
 * Created by yeta on 2017/4/25/025.
 */
@Entity
public class QuestionaryAnswers {
    //id
    @Id
    @GeneratedValue
    private Integer id;
    //用户id
    private Integer userId;
    //回答1
    private String answer1;
    //回答2
    private String answer2;
    //回答3
    private String answer3;
    //回答4
    private String answer4;
    //回答5
    private String answer5;
    //回答6
    private String answer6;
    //回答7
    private String answer7;
    //回答8
    private String answer8;
    //回答9
    private String answer9;
    //回答10
    private String answer10;
    //回答11
    private String answer11;
    //回答12
    private String answer12;
    //回答13
    private String answer13;
    //回答14
    private String answer14;
    //回答15
    private String answer15;
    //回答16
    private String answer16;
    //回答17
    private String answer17;
    //回答18
    private String answer18;
    //回答19
    private String answer19;
    //回答20
    private String answer20;
    //回答21
    private String answer21;
    //回答22
    private String answer22;
    //用户类型
    private String userType;

    //构造方法
    public QuestionaryAnswers() {
    }

    //getter和setter方法
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getAnswer1() {
        return answer1;
    }

    public void setAnswer1(String answer1) {
        this.answer1 = answer1;
    }

    public String getAnswer2() {
        return answer2;
    }

    public void setAnswer2(String answer2) {
        this.answer2 = answer2;
    }

    public String getAnswer3() {
        return answer3;
    }

    public void setAnswer3(String answer3) {
        this.answer3 = answer3;
    }

    public String getAnswer4() {
        return answer4;
    }

    public void setAnswer4(String answer4) {
        this.answer4 = answer4;
    }

    public String getAnswer5() {
        return answer5;
    }

    public void setAnswer5(String answer5) {
        this.answer5 = answer5;
    }

    public String getAnswer6() {
        return answer6;
    }

    public void setAnswer6(String answer6) {
        this.answer6 = answer6;
    }

    public String getAnswer7() {
        return answer7;
    }

    public void setAnswer7(String answer7) {
        this.answer7 = answer7;
    }

    public String getAnswer8() {
        return answer8;
    }

    public void setAnswer8(String answer8) {
        this.answer8 = answer8;
    }

    public String getAnswer9() {
        return answer9;
    }

    public void setAnswer9(String answer9) {
        this.answer9 = answer9;
    }

    public String getAnswer10() {
        return answer10;
    }

    public void setAnswer10(String answer10) {
        this.answer10 = answer10;
    }

    public String getAnswer11() {
        return answer11;
    }

    public void setAnswer11(String answer11) {
        this.answer11 = answer11;
    }

    public String getAnswer12() {
        return answer12;
    }

    public void setAnswer12(String answer12) {
        this.answer12 = answer12;
    }

    public String getAnswer13() {
        return answer13;
    }

    public void setAnswer13(String answer13) {
        this.answer13 = answer13;
    }

    public String getAnswer14() {
        return answer14;
    }

    public void setAnswer14(String answer14) {
        this.answer14 = answer14;
    }

    public String getAnswer15() {
        return answer15;
    }

    public void setAnswer15(String answer15) {
        this.answer15 = answer15;
    }

    public String getAnswer16() {
        return answer16;
    }

    public void setAnswer16(String answer16) {
        this.answer16 = answer16;
    }

    public String getAnswer17() {
        return answer17;
    }

    public void setAnswer17(String answer17) {
        this.answer17 = answer17;
    }

    public String getAnswer18() {
        return answer18;
    }

    public void setAnswer18(String answer18) {
        this.answer18 = answer18;
    }

    public String getAnswer19() {
        return answer19;
    }

    public void setAnswer19(String answer19) {
        this.answer19 = answer19;
    }

    public String getAnswer20() {
        return answer20;
    }

    public void setAnswer20(String answer20) {
        this.answer20 = answer20;
    }

    public String getAnswer21() {
        return answer21;
    }

    public void setAnswer21(String answer21) {
        this.answer21 = answer21;
    }

    public String getAnswer22() {
        return answer22;
    }

    public void setAnswer22(String answer22) {
        this.answer22 = answer22;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
