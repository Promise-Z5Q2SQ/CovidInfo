package com.java.zhushuqi.backend;

import android.view.View;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ArrayDeque;
import java.util.HashSet;

public class Server {
    public static Server server;
    public HashMap<String, Dataset> DataMap;
    public ArrayList<Scholar> ScholarInfo;  //学者信息
    public ArrayList<News> ViewedHistory;    //浏览记录
    public ArrayList<String> WordsHistory;  //搜索历史记录
    public int news_page = 4;
    public int N_news_page = 4;
    public int P_news_page = 4;

    public void InitServer() {//初始化服务器:读入疫情数据、刷新新闻列表、读入学者数据
        try {
            try {
                DataMap = DataLoader.GetData();
            } catch (IOException e) {System.out.println("IOException!");}
        } catch (JSONException f) {System.out.println("JSONException!");}
        try {
            try {
                ScholarInfo = ScholarLoader.GetScholar(ScholarInfo);
            } catch (IOException e) {System.out.println("IOException!");}
        } catch (JSONException f) {System.out.println("JSONException!");}
    }

    public Server(){
        DataMap = new HashMap<String, Dataset>();
        ScholarInfo = new ArrayList<Scholar>();  //学者信息
        WordsHistory = new ArrayList<String>();  //搜索历史记录
        ViewedHistory = new ArrayList<News>();
    }

    /*void Search_News(String key){
        for(int i = 0; i < ; i++){

        }
    }*/
}
