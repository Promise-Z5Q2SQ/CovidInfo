package com.java.zhushuqi.ui.news;

import android.util.Log;

import com.java.zhushuqi.backend.News;
import org.json.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

public class NewsLoader {
    static String link = "https://covid-dashboard.aminer.cn/api/events/list?type=%s&page=%d&size=%d";

    static String GetContentFromURL(String url) throws IOException {
        URL cs = new URL(url);
        URLConnection urlcnt = cs.openConnection();
        urlcnt.setConnectTimeout(10 * 1000);
        urlcnt.connect();
        BufferedReader in = new BufferedReader(new InputStreamReader(urlcnt.getInputStream()));
        String line, body = "";
        while ((line = in.readLine()) != null)
            body = body + line;
        in.close();
        return body;
    }

    public static void GetNews(final int page, final String type, final int size, ArrayList<News> newsl) throws IOException, JSONException {
        String URL_String = new String(String.format(link, type, page, size));
        String body = GetContentFromURL(URL_String);
        if (body.equals("")) {
            Log.d("warning", "No message received.");
        }
        JSONObject news_json = new JSONObject(body);
        GetNewsFromJSON(news_json, newsl);
    }

    public static void GetNewsFromJSON(JSONObject src, ArrayList<News> newsl) throws JSONException {
        JSONArray list;
        list = src.optJSONArray("data");
        for (int i = list.length() - 1; i >= 0; i--) {
            News news = new News();
            JSONObject obj = list.getJSONObject(i);//这一页里的第i条新闻
            news.id = obj.optString("_id");
            news.author = "";
            JSONArray authorlist = obj.optJSONArray("authors");
            if (authorlist != null) {
                for (int j = 0; j < authorlist.length(); i++) {
                    JSONObject author = authorlist.getJSONObject(j);//取出作者
                    news.author = news.author + " " + author.optString("name");//用空格分隔各作者的名字
                }
            }
            news.content = obj.optString("content");
            news.date = obj.optString("date");
            news.influence = obj.optString("influence");
            news.seg_text = obj.optString("seg_text").split(" ");
            news.source = obj.optString("source");
            news.title = obj.optString("title");
            news.type = obj.optString("type");
            news.lang = obj.optString("lang");
            newsl.add(0, news);
        }
    }

    public static int RenewNews(final int page, final String type, final int size, ArrayList<News> newsl) throws IOException, JSONException {
        String URL_String = new String(String.format(link, type, page, size));
        String body = GetContentFromURL(URL_String);
        if (body.equals("")) {
            Log.d("warning", "No message received.");
        }
        JSONObject news_json = new JSONObject(body);
        return (RenewNewsFromJSON(news_json, newsl));
    }

    public static int RenewNewsFromJSON(JSONObject src, ArrayList<News> newsl) throws JSONException {
        String latest_id = newsl.get(0).id;
        int num = 10;
        String id = "";
        JSONArray list;
        JSONObject obj;
        list = src.optJSONArray("data");
        for (int i = 0; i < list.length(); i++){
            obj = list.getJSONObject(i);
            id = obj.optString("_id");
            if(id.equals(latest_id)){
                num = i;
                break;
            }
        }
        for (int i = num - 1; i >= 0; i--) {
            News news = new News();
            obj = list.getJSONObject(i);//这一页里的第i条新闻
            news.id = obj.optString("_id");
            news.author = "";
            JSONArray authorlist = obj.optJSONArray("authors");
            if (authorlist != null) {
                for (int j = 0; j < authorlist.length(); i++) {
                    JSONObject author = authorlist.getJSONObject(j);//取出作者
                    news.author = news.author + " " + author.optString("name");//用空格分隔各作者的名字
                }
            }
            news.content = obj.optString("content");
            news.date = obj.optString("date");
            news.influence = obj.optString("influence");
            news.seg_text = obj.optString("seg_text").split(" ");
            news.source = obj.optString("source");
            news.title = obj.optString("title");
            news.type = obj.optString("type");
            news.lang = obj.optString("lang");
            newsl.add(0, news);
        }
        return num;
    }
}

