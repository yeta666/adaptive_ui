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
    //outlook
    private String outlook;
    //temperature
    private String temperature;
    //humidity
    private String humidity;
    //windy
    private String windy;
    //play
    private String play;

    //构造方法
    public TrainArray() {
    }

    public TrainArray(String outlook, String temperature, String humidity, String windy, String play) {
        this.outlook = outlook;
        this.temperature = temperature;
        this.humidity = humidity;
        this.windy = windy;
        this.play = play;
    }

    //getter和setter方法
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOutlook() {
        return outlook;
    }

    public void setOutlook(String outlook) {
        this.outlook = outlook;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getWindy() {
        return windy;
    }

    public void setWindy(String windy) {
        this.windy = windy;
    }

    public String getPlay() {
        return play;
    }

    public void setPlay(String play) {
        this.play = play;
    }
}
