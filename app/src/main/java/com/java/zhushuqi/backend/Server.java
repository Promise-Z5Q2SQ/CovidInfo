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
    public ArrayList<News> NewsInShow;      //正出现在浏览界面的新闻（20）
    public ArrayDeque<News> NewsFromPaperInShow;      //正出现在浏览界面的新闻（20）
    public ArrayDeque<News> NewsFromNewsInShow;      //正出现在浏览界面的新闻（20）
    public ArrayList<News> NewsHistory;     //刷新记录
    public ArrayList<News> ViewedHistory;    //浏览记录
    public ArrayList<String> WordsHistory;  //搜索历史记录

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
        InitNews();
    }

    public void InitNews() {//打开应用时刷新新闻列表
        try {
            try {
                NewsLoader.GetNews(2, "all", 20, NewsInShow);
                //NewsLoader.GetNews(1, "news", 20, NewsFromNewsInShow);
                //NewsLoader.GetNews(1, "paper", 20, NewsFromPaperInShow);
            } catch (IOException e) {System.out.println("IOException!");e.printStackTrace();
            }
        } catch (JSONException f) {System.out.println("JSONException!");
        }
    }


    public int GetLatestNews() {//下拉获取最新新闻,返回值是获取的新的新闻的数量
        try {
            try {
                int h = NewsLoader.RenewNews(1, "all", 10, NewsInShow);//更新了多少条新闻
                for (int i = 0; i < h; i++) {
                     NewsHistory.add(0, NewsInShow.get(NewsInShow.size() - i - 1));
                }
                for(int i = 0; i < h; i++){
                    NewsInShow.remove(NewsInShow.size() - 1);
                }
                return h;
            } catch (IOException e) {
                return 0;
            }
        } catch (JSONException f) {
            return 0;
        }
    }


    public int RetrievePresentNews() {//上拉查看更早的新闻,返回更新了多少条旧新闻
        int h = 10;
        if(NewsHistory.equals(null))return 0;
        if(h < NewsHistory.size()){
            h = NewsHistory.size();
        }
        for (int i = 0; i < h; i++) {
            NewsInShow.add(NewsHistory.get(0));
            NewsHistory.remove(0);
            NewsInShow.remove(0);
        }
        return h;
    }

    public void AddNewsToHistory(int v){
        ViewedHistory.add(NewsInShow.get(v));
    }

    public Server(){
        DataMap = new HashMap<String, Dataset>();
        ScholarInfo = new ArrayList<Scholar>();  //学者信息
        NewsInShow = new ArrayList<News>();      //正出现在浏览界面的新闻（20）
        NewsFromPaperInShow = new ArrayDeque<News>();      //正出现在浏览界面的新闻（20）
        NewsFromNewsInShow = new ArrayDeque<News>();      //正出现在浏览界面的新闻（20）
        NewsHistory = new ArrayList<News>();     //浏览记录
        WordsHistory = new ArrayList<String>();  //搜索历史记录
        ViewedHistory = new ArrayList<News>();
    }
}
