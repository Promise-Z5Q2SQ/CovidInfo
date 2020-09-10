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
    public ArrayList<News> P_NewsInShow;      //正出现在浏览界面的新闻（20）
    public ArrayList<News> N_NewsInShow;      //正出现在浏览界面的新闻（20）
    public ArrayList<News> NewsHistory;     //刷新记录
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

    public void InitNews() {//打开应用时刷新新闻列表
        try {
            try {
                NewsLoader.GetNews(news_page, "all", 10, NewsInShow);
                news_page -= 1;
                NewsLoader.GetNews(news_page, "all", 10, NewsInShow);
            } catch (IOException e) {System.out.println("IOException!");e.printStackTrace();
            }
        } catch (JSONException f) {System.out.println("JSONException!");
        }
    }

    public void InitNews_N() {//打开应用时刷新新闻列表
        try {
            try {
                NewsLoader.GetNews(N_news_page, "news", 10, N_NewsInShow);
                N_news_page -= 1;
                NewsLoader.GetNews(N_news_page, "news", 10, N_NewsInShow);
            } catch (IOException e) {System.out.println("IOException!");e.printStackTrace();
            }
        } catch (JSONException f) {System.out.println("JSONException!");
        }
    }

    public void InitNews_P() {//打开应用时刷新新闻列表
        try {
            try {
                NewsLoader.GetNews(P_news_page, "paper", 10, P_NewsInShow);
                P_news_page -= 1;
                NewsLoader.GetNews(P_news_page, "paper", 10, P_NewsInShow);
            } catch (IOException e) {System.out.println("IOException!");e.printStackTrace();
            }
        } catch (JSONException f) {System.out.println("JSONException!");
        }
    }


    public int GetLatestNews() {//下拉获取最新新闻,返回值是获取的新的新闻的数量
        try {
            try {
                int h = 0;
                if(news_page >= 1){
                    h = NewsLoader.RenewNews(news_page - 1, "all", 10, NewsInShow);//更新了多少条新闻
                    news_page -= 1;
                }
                else{
                    h = NewsLoader.RenewNews(0, "all", 10, NewsInShow);//更新了多少条新闻
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


    public void RetrievePresentNews(){//上拉查看更早的新闻(固定更新10条)
        try {
            try {
                NewsLoader.RetrieveNews(news_page + 2, "all", 10, NewsInShow);
                news_page += 1;
                while(NewsInShow.size() > 20){
                    NewsInShow.remove(0);
                }
            } catch (IOException e) {
            }
        }catch(JSONException j){}
    }

    public int GetLatestNews_N() {//下拉获取最新新闻,返回值是获取的新的新闻的数量
        try {
            try {
                int h = 0;
                if(N_news_page >= 1){
                    h = NewsLoader.RenewNews(N_news_page - 1, "news", 10, N_NewsInShow);//更新了多少条新闻
                    N_news_page -= 1;
                }
                else{
                    h = NewsLoader.RenewNews(0, "news", 10, N_NewsInShow);//更新了多少条新闻
                }
                for(int i = 0; i < h; i++){
                    N_NewsInShow.remove(N_NewsInShow.size() - 1);
                }
                return h;
            } catch (IOException e) {
                return 0;
            }
        } catch (JSONException f) {
            return 0;
        }
    }


    public void RetrievePresentNews_N(){//上拉查看更早的新闻(固定更新10条)
        try {
            try {
                NewsLoader.RetrieveNews(N_news_page + 2, "news", 10, N_NewsInShow);
                N_news_page += 1;
                while(N_NewsInShow.size() > 20){
                    N_NewsInShow.remove(0);
                }
            } catch (IOException e) {
            }
        }catch(JSONException j){}
    }

    public int GetLatestNews_P() {//下拉获取最新新闻,返回值是获取的新的新闻的数量
        try {
            try {
                int h = 0;
                if(P_news_page >= 1){
                    h = NewsLoader.RenewNews(P_news_page - 1, "paper", 10, P_NewsInShow);//更新了多少条新闻
                    P_news_page -= 1;
                }
                else{
                    h = NewsLoader.RenewNews(0, "paper", 10, P_NewsInShow);//更新了多少条新闻
                }
                for(int i = 0; i < h; i++){
                    P_NewsInShow.remove(P_NewsInShow.size() - 1);
                }
                return h;
            } catch (IOException e) {
                return 0;
            }
        } catch (JSONException f) {
            return 0;
        }
    }


    public void RetrievePresentNews_P(){//上拉查看更早的新闻(固定更新10条)
        try {
            try {
                NewsLoader.RetrieveNews(P_news_page + 2, "paper", 10, P_NewsInShow);
                P_news_page += 1;
                while(P_NewsInShow.size() > 20){
                    P_NewsInShow.remove(0);
                }
            } catch (IOException e) {
            }
        }catch(JSONException j){}
    }

    public void AddNewsToHistory(int v){
        ViewedHistory.add(NewsInShow.get(v));
    }

    public Server(){
        DataMap = new HashMap<String, Dataset>();
        ScholarInfo = new ArrayList<Scholar>();  //学者信息
        NewsInShow = new ArrayList<News>();      //正出现在浏览界面的新闻（20）
        P_NewsInShow = new ArrayList<News>();      //正出现在浏览界面的新闻（20）
        N_NewsInShow = new ArrayList<News>();      //正出现在浏览界面的新闻（20）
        NewsHistory = new ArrayList<News>();     //浏览记录
        WordsHistory = new ArrayList<String>();  //搜索历史记录
        ViewedHistory = new ArrayList<News>();
    }

    void Search_News(String key){
        for(int i = 0; i < ; i++){

        }
    }
}
