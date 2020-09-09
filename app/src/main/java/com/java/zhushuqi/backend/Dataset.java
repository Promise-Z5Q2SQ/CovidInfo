package com.java.zhushuqi.backend;

import java.util.ArrayList;
import java.util.Date;

class Datapoint extends Object{
    public int timefromstart;//从begin开始计算的时间
    public int confirmed;   //确诊者
    public int suspected;   //疑似患者
    public int cured;   //治愈病例
    public int dead;    //死亡病例
    public int risk;    //风险

    @Override
    public String toString() {
        return "Datapoint{" +
                "timefromstart=" + timefromstart +
                ", confirmed=" + confirmed +
                ", suspected=" + suspected +
                ", cured=" + cured +
                ", dead=" + dead +
                ", risk=" + risk +
                '}';
    }
}

public class Dataset{
    public Date start_date;
    public ArrayList<Datapoint> data;
    public Dataset(){
        data = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Dataset{" +
                "start_date=" + start_date +
                ", data=" + data +
                '}';
    }
}

