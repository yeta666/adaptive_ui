package com.adaptive.ui.domain2;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * train_array_attribute表的实体类
 * Created by yeta on 2017/4/20/020.
 */
@Entity
public class TrainArrayAttribute {
    //id
    @Id
    @GeneratedValue
    private Integer id;

    //attribute1
    private String attribute1;

    //attribute2
    private String attribute2;

    //attribute3
    private String attribute3;

    //attribute4
    private String attribute4;

    //attribute5
    private String attribute5;

    //attribute6
    private String attribute6;

    //attribute7
    private String attribute7;

    //attribute8
    private String attribute8;

    //attribute9
    private String attribute9;

    //attribute10
    private String attribute10;

    //attribute11
    private String attribute11;

    //attribute12
    private String attribute12;

    //attribute13
    private String attribute13;

    //attribute14
    private String attribute14;

    //attribute15
    private String attribute15;

    //attribute16
    private String attribute16;

    //attribute17
    private String attribute17;

    //模型类型
    private String modelType;

    //构造方法
    public TrainArrayAttribute() {
    }

    //getter和setter方法
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAttribute1() {
        return attribute1;
    }

    public void setAttribute1(String attribute1) {
        this.attribute1 = attribute1;
    }

    public String getAttribute2() {
        return attribute2;
    }

    public void setAttribute2(String attribute2) {
        this.attribute2 = attribute2;
    }

    public String getAttribute3() {
        return attribute3;
    }

    public void setAttribute3(String attribute3) {
        this.attribute3 = attribute3;
    }

    public String getAttribute4() {
        return attribute4;
    }

    public void setAttribute4(String attribute4) {
        this.attribute4 = attribute4;
    }

    public String getAttribute5() {
        return attribute5;
    }

    public void setAttribute5(String attribute5) {
        this.attribute5 = attribute5;
    }

    public String getAttribute6() {
        return attribute6;
    }

    public void setAttribute6(String attribute6) {
        this.attribute6 = attribute6;
    }

    public String getAttribute7() {
        return attribute7;
    }

    public void setAttribute7(String attribute7) {
        this.attribute7 = attribute7;
    }

    public String getAttribute8() {
        return attribute8;
    }

    public void setAttribute8(String attribute8) {
        this.attribute8 = attribute8;
    }

    public String getAttribute9() {
        return attribute9;
    }

    public void setAttribute9(String attribute9) {
        this.attribute9 = attribute9;
    }

    public String getAttribute10() {
        return attribute10;
    }

    public void setAttribute10(String attribute10) {
        this.attribute10 = attribute10;
    }

    public String getAttribute11() {
        return attribute11;
    }

    public void setAttribute11(String attribute11) {
        this.attribute11 = attribute11;
    }

    public String getAttribute12() {
        return attribute12;
    }

    public void setAttribute12(String attribute12) {
        this.attribute12 = attribute12;
    }

    public String getAttribute13() {
        return attribute13;
    }

    public void setAttribute13(String attribute13) {
        this.attribute13 = attribute13;
    }

    public String getAttribute14() {
        return attribute14;
    }

    public void setAttribute14(String attribute14) {
        this.attribute14 = attribute14;
    }

    public String getAttribute15() {
        return attribute15;
    }

    public void setAttribute15(String attribute15) {
        this.attribute15 = attribute15;
    }

    public String getAttribute16() {
        return attribute16;
    }

    public void setAttribute16(String attribute16) {
        this.attribute16 = attribute16;
    }

    public String getAttribute17() {
        return attribute17;
    }

    public void setAttribute17(String attribute17) {
        this.attribute17 = attribute17;
    }

    public String getModelType() {
        return modelType;
    }

    public void setModelType(String modelType) {
        this.modelType = modelType;
    }
}
