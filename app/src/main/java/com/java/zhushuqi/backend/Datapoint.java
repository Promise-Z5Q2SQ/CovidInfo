package com.java.zhushuqi.backend;

public class Datapoint extends Object{
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
