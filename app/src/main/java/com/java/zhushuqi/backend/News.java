package com.java.zhushuqi.backend;

import java.util.Arrays;

public class News {
    public String id;//新闻id（基本用不上）
    public String type;//新闻类型，news/paper/event
    public String title;//新闻标题
    public String date;//时间
    public String lang;//语种
    public String influence;//影响力
    public String author;//作者（一个字符串，其中各个作者的名字用空格分隔）
    public String content;//内容
    public String[] seg_text;//关键字字符串，用空格分隔
    public String source;//来源（有些是空字符串）
    public boolean viewed = false;//是否被浏览过

    @Override
    public String toString() {
        return "News{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", title='" + title + '\'' +
                ", date='" + date + '\'' +
                ", lang='" + lang + '\'' +
                ", influence='" + influence + '\'' +
                ", author='" + author + '\'' +
                ", content='" + content + '\'' +
                ", seg_text=" + Arrays.toString(seg_text) +
                ", source='" + source + '\'' +
                '}';
    }
}

