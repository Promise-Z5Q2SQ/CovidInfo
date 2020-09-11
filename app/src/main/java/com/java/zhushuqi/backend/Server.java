package com.java.zhushuqi.backend;

import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.ArrayDeque;
import java.util.HashSet;

public class Server {
    public static Server server;
    public HashMap<String, Dataset> DataMap;
    public ArrayList<Scholar> ScholarInfo;      //学者信息
    public ArrayList<Scholar> P_Scholar;        //已故学者信息
    public ArrayList<News> NewsInShow;          //正出现在浏览界面的新闻（20）
    public ArrayList<News> P_NewsInShow;        //正出现在浏览界面的新闻（20）
    public ArrayList<News> N_NewsInShow;        //正出现在浏览界面的新闻（20）
    public ArrayList<News> SearchPool;          //搜索范围（初始为100条新闻，之后会随着新闻加入浏览列表而扩充）
    public ArrayList<News> SearchAnswer;        //搜索结果
    public ArrayList<News> ViewedHistory;       //浏览记录
    public ArrayList<String> WordsHistory;      //搜索历史记录
    public ArrayList<Entity> Entities;          //用于图谱的数据集
    public ArrayList<News>[] Cluster = new ArrayList[4];        //聚类0
    public int news_page = 4;
    public int N_news_page = 4;
    public int P_news_page = 4;

    public void InitServer() {//初始化服务器:读入疫情数据、刷新新闻列表、读入学者数据
        try {
            try {
                DataMap = DataLoader.GetData();
            } catch (IOException e) {
                System.out.println("IOException!");
            }
        } catch (JSONException f) {
            System.out.println("JSONException!");
        }
        try {
            try {
                ScholarInfo = ScholarLoader.GetScholar(ScholarInfo);
                for (Scholar sc : ScholarInfo) {
                    if (sc.is_passedaway.equals("true")) {
                        P_Scholar.add(sc);
                    }
                }
                Collections.sort(ScholarInfo, new Comparator<Scholar>() {
                    @Override
                    public int compare(Scholar scholar, Scholar t1) {
                        return t1.num_viewed - scholar.num_viewed;
                    }
                });
            } catch (IOException e) {
                System.out.println("IOException!");
            }
        } catch (JSONException f) {
            System.out.println("JSONException!");
        }
    }

    public void InitNews() {//打开应用时刷新新闻列表
        try {
            try {
                NewsLoader.GetNews(news_page, "all", 10, NewsInShow);
                news_page -= 1;
                NewsLoader.GetNews(news_page, "all", 10, NewsInShow);
                for (int i = 5; i < 15; i++) {
                    NewsLoader.GetNews(i, "all", 20, SearchPool);
                }
            } catch (IOException e) {
                System.out.println("IOException!");
                e.printStackTrace();
            }
        } catch (JSONException f) {
            System.out.println("JSONException!");
        }
    }

    public void InitNews_N() {//打开应用时刷新新闻列表
        try {
            try {
                NewsLoader.GetNews(N_news_page, "news", 10, N_NewsInShow);
                N_news_page -= 1;
                NewsLoader.GetNews(N_news_page, "news", 10, N_NewsInShow);
            } catch (IOException e) {
                System.out.println("IOException!");
                e.printStackTrace();
            }
        } catch (JSONException f) {
            System.out.println("JSONException!");
        }
    }

    public void InitNews_P() {//打开应用时刷新新闻列表
        try {
            try {
                NewsLoader.GetNews(P_news_page, "paper", 10, P_NewsInShow);
                P_news_page -= 1;
                NewsLoader.GetNews(P_news_page, "paper", 10, P_NewsInShow);
            } catch (IOException e) {
                System.out.println("IOException!");
                e.printStackTrace();
            }
        } catch (JSONException f) {
            System.out.println("JSONException!");
        }
    }


    public int GetLatestNews() {//下拉获取最新新闻,返回值是获取的新的新闻的数量
        try {
            try {
                int h = 0;
                if (news_page >= 1) {
                    h = NewsLoader.RenewNews(news_page - 1, "all", 10, NewsInShow);//更新了多少条新闻
                    news_page -= 1;
                } else {
                    h = NewsLoader.RenewNews(0, "all", 10, NewsInShow);//更新了多少条新闻
                }
                for (int i = 0; i < h; i++) {
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


    public void RetrievePresentNews() {//上拉查看更早的新闻(固定更新10条)
        try {
            try {
                NewsLoader.RetrieveNews(news_page + 2, "all", 10, NewsInShow);
                news_page += 1;
                while (NewsInShow.size() > 20) {
                    NewsInShow.remove(0);
                }
            } catch (IOException e) {
            }
        } catch (JSONException j) {
        }
    }

    public int GetLatestNews_N() {//下拉获取最新新闻,返回值是获取的新的新闻的数量
        try {
            try {
                int h = 0;
                if (N_news_page >= 1) {
                    h = NewsLoader.RenewNews(N_news_page - 1, "news", 10, N_NewsInShow);//更新了多少条新闻
                    N_news_page -= 1;
                } else {
                    h = NewsLoader.RenewNews(0, "news", 10, N_NewsInShow);//更新了多少条新闻
                }
                for (int i = 0; i < h; i++) {
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


    public void RetrievePresentNews_N() {//上拉查看更早的新闻(固定更新10条)
        try {
            try {
                NewsLoader.RetrieveNews(N_news_page + 2, "news", 10, N_NewsInShow);
                N_news_page += 1;
                while (N_NewsInShow.size() > 20) {
                    N_NewsInShow.remove(0);
                }
            } catch (IOException e) {
            }
        } catch (JSONException j) {
        }
    }

    public int GetLatestNews_P() {//下拉获取最新新闻,返回值是获取的新的新闻的数量
        try {
            try {
                int h = 0;
                if (P_news_page >= 1) {
                    h = NewsLoader.RenewNews(P_news_page - 1, "paper", 10, P_NewsInShow);//更新了多少条新闻
                    P_news_page -= 1;
                } else {
                    h = NewsLoader.RenewNews(0, "paper", 10, P_NewsInShow);//更新了多少条新闻
                }
                for (int i = 0; i < h; i++) {
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


    public void RetrievePresentNews_P() {//上拉查看更早的新闻(固定更新10条)
        try {
            try {
                NewsLoader.RetrieveNews(P_news_page + 2, "paper", 10, P_NewsInShow);
                P_news_page += 1;
                while (P_NewsInShow.size() > 20) {
                    P_NewsInShow.remove(0);
                }
            } catch (IOException e) {
            }
        } catch (JSONException j) {
        }
    }

    public void ReadCluster(JSONArray src, ArrayList<News> newsl) throws JSONException {
        JSONObject obj;
        for (int i = 0; i < src.length(); i++) {
            News news = new News();
            obj = src.getJSONObject(i);//这一页里的第i条新闻
            news.id = obj.optString("_id");
            news.date = obj.optString("date");
            news.title = obj.optString("title");
            newsl.add(news);//把旧新闻一条一条加到新闻列表的末端
        }
    }

    public void AddNewsToHistory(int v) {
        ViewedHistory.add(NewsInShow.get(v));
    }

    public Server() {
        DataMap = new HashMap<String, Dataset>();
        ScholarInfo = new ArrayList<Scholar>();     //学者信息
        NewsInShow = new ArrayList<News>();         //正出现在浏览界面的新闻（20）
        P_NewsInShow = new ArrayList<News>();       //正出现在浏览界面的新闻（20）
        N_NewsInShow = new ArrayList<News>();       //正出现在浏览界面的新闻（20）
        SearchPool = new ArrayList<News>();         //搜索库
        SearchAnswer = new ArrayList<News>();       //搜索结果
        WordsHistory = new ArrayList<String>();     //搜索历史记录
        ViewedHistory = new ArrayList<News>();
        Entities = new ArrayList<Entity>();
        P_Scholar = new ArrayList<Scholar>();
        for (int i = 0; i < 3; i++) Cluster[i] = new ArrayList<News>();
    }

    void Search_News(String key) {
        SearchAnswer.clear();
        for (News n : SearchPool) {
            for (int i = 0; i < n.title.length() - key.length(); i++) {
                if (n.title.substring(i, i + key.length()).equals(key)) {
                    SearchAnswer.add(n);
                    break;
                }
            }
        }
    }
}
