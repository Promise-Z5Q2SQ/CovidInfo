package com.java.zhushuqi.backend;

import android.graphics.Bitmap;

import java.util.ArrayList;

public class Scholar {
    public String avatar;//头像
    public Bitmap img;
    public String name;//名字（英文)
    public String position;//职位
    public String work;//科研工作
    public String affiliation;//研究所
    public String affiliation_zh;//单位
    public String bio;//简介
    public String edu;//受教育情况
    public int num_viewed;

    public int gindex;
    public int hindex;
    public String activity;
    public String sociability;
    public String diversity;
    public int citations;

    public boolean is_passedaway = false;

    public Scholar() { }

    @Override
    public String toString() {
        return "Scholar{" +
                "avatar='" + avatar + '\'' +
                ", name='" + name + '\'' +
                ", position='" + position + '\'' +
                ", work='" + work + '\'' +
                ", affiliation='" + affiliation + '\'' +
                ", affiliation_zh='" + affiliation_zh + '\'' +
                ", bio='" + bio + '\'' +
                ", edu='" + edu + '\'' +
                ", num_viewed=" + num_viewed +
                ", is_passedaway=" + is_passedaway +
                '}';
    }
}
