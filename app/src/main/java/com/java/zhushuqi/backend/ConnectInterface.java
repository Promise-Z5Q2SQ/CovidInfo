package com.java.zhushuqi.backend;

import android.content.Context;
import android.os.Build;
import android.os.FileUtils;
import android.provider.ContactsContract;

import androidx.annotation.RequiresApi;

import com.java.zhushuqi.R;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.Buffer;
import java.security.Key;
import java.util.*;
import java.util.concurrent.*;

import io.reactivex.*;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

import org.json.JSONArray;
import org.json.JSONObject;
import org.reactivestreams.*;

public class ConnectInterface {
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static boolean inited_A;
    public static boolean inited_P;
    public static boolean inited_N;

    //初始化新闻列表
    public static Single<List<News>> InitServer() {
        return Flowable.fromCallable(new Callable<List<News>>() {
            @Override
            public List<News> call() throws Exception {
                try {
                    Server.server.InitServer();
                    return Server.server.NewsInShow;
                } catch (Exception e) {
                    return new ArrayList<News>();
                }
            }
        }).flatMap(new Function<List<News>, Publisher<News>>() {
            @Override
            public Publisher<News> apply(List<News> Newses) {
                if (Newses.size() > 0) return Flowable.fromIterable(Newses);
                return Flowable.fromIterable(Server.server.NewsInShow);//fixme 如果运行了这一句代表网络出现问题没有正常返回
            }
        }).toList().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    //获取当前要显示的新闻
    public static Single<List<News>> GetCurrentNews(final String type) {
        return Flowable.fromCallable(new Callable<List<News>>() {
            @Override
            public List<News> call() throws Exception {
                if(type.equals("all")){
                    try {
                        if(inited_A == false) {
                            Server.server.InitNews();
                            System.out.println("finish");
                            inited_A = true;
                        }
                        System.out.println("A_size = " + Server.server.NewsInShow.size());
                        return Server.server.NewsInShow;
                    } catch (Exception e) {
                        System.out.println("Ex");
                        return new ArrayList<News>();
                    }
                }
                else if(type.equals("news")){
                    try {
                        if(inited_N == false)
                        {
                            Server.server.InitNews_N();
                            System.out.println("finish");
                            inited_N = true;
                        }
                        System.out.println("N_size = " + Server.server.N_NewsInShow.size());
                        return Server.server.N_NewsInShow;
                    } catch (Exception e) {
                        System.out.println("Ex");
                        return new ArrayList<News>();
                    }
                }
                else if(type.equals("paper")){
                    try {
                        if(inited_P == false){
                            Server.server.InitNews_P();
                            System.out.println("finish");
                            inited_P = true;
                        }
                        System.out.println("P_size = " + Server.server.P_NewsInShow.size());
                        return Server.server.P_NewsInShow;
                    } catch (Exception e) {
                        System.out.println("Ex");
                        return new ArrayList<News>();
                    }
                }
                else{
                    return new ArrayList<News>();
                }
            }
        }).flatMap(new Function<List<News>, Publisher<News>>() {
            @Override
            public Publisher<News> apply(List<News> Newses) {
                if (Newses.size() > 0) return Flowable.fromIterable(Newses);
                return Flowable.fromIterable(Server.server.NewsInShow);//fixme 如果运行了这一句代表网络出现问题没有正常返回
            }
        }).toList().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public static Single<List<News>> GetSearchAnswer(final String str) {
        return Flowable.fromCallable(new Callable<List<News>>() {
            @Override
            public List<News> call() throws Exception {
                try {
                    Server.server.Search_News(str);
                    return Server.server.SearchAnswer;
                } catch (Exception e) {
                    System.out.println("Ex");
                    return new ArrayList<News>();
                }
            }
        }).flatMap(new Function<List<News>, Publisher<News>>() {
            @Override
            public Publisher<News> apply(List<News> Newses) {
                return Flowable.fromIterable(Newses);
                //return Flowable.fromIterable(Server.server.NewsInShow);//fixme 如果运行了这一句代表网络出现问题没有正常返回
            }
        }).toList().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public static Single<Dataset> GetData(final String str) {
        return Flowable.fromCallable(new Callable<Dataset>() {
            @Override
            public Dataset call() throws Exception {
                try {
                    Dataset ds = Server.server.DataMap.get(str);
                    if(ds == null)return new Dataset();
                    else return ds;
                } catch (Exception e) {
                    System.out.println("Ex");
                    return new Dataset();
                }
            }
        }).firstOrError().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    //下拉获取最新新闻
    public static Single<List<News>> GetLatestNews(final String type) {
        return Flowable.fromCallable(new Callable<List<News>>() {
            @Override
            public List<News> call() throws Exception {
                if(type.equals("all")){
                    try {
                        Server.server.GetLatestNews();
                        return Server.server.NewsInShow;
                    } catch (Exception e) {
                        return new ArrayList<News>();
                    }
                }
                else if(type.equals("news")){
                    try {
                        Server.server.GetLatestNews_N();
                        return Server.server.N_NewsInShow;
                    } catch (Exception e) {
                        return new ArrayList<News>();
                    }
                }
                else if(type.equals("paper")){
                    try {
                        Server.server.GetLatestNews_P();
                        return Server.server.P_NewsInShow;
                    } catch (Exception e) {
                        return new ArrayList<News>();
                    }
                }
                else{
                    return new ArrayList<News>();
                }
            }
        }).flatMap(new Function<List<News>, Publisher<News>>() {
            @Override
            public Publisher<News> apply(List<News> Newses) {
                if (Newses.size() > 0) return Flowable.fromIterable(Newses);
                return Flowable.fromIterable(Server.server.NewsInShow);//fixme 如果运行了这一句代表网络出现问题没有正常返回
            }
        }).toList().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    //上拉获取旧新闻
    public static Single<List<News>> RetrievePresentNews(final String type) {
        return Flowable.fromCallable(new Callable<List<News>>() {
            @Override
            public List<News> call() throws Exception {
                if(type.equals("all")){
                    try {
                        Server.server.RetrievePresentNews();
                        return Server.server.NewsInShow;
                    } catch (Exception e) {
                        return new ArrayList<News>();
                    }
                }
                else if(type.equals("news")){
                    try {
                        Server.server.RetrievePresentNews_N();
                        return Server.server.N_NewsInShow;
                    } catch (Exception e) {
                        return new ArrayList<News>();
                    }
                }
                else if(type.equals("paper")){
                    try {
                        Server.server.RetrievePresentNews_P();
                        return Server.server.P_NewsInShow;
                    } catch (Exception e) {
                        return new ArrayList<News>();
                    }
                }
                else{
                    return new ArrayList<News>();
                }
            }
        }).flatMap(new Function<List<News>, Publisher<News>>() {
            @Override
            public Publisher<News> apply(List<News> Newses) {
                if (Newses.size() > 0) return Flowable.fromIterable(Newses);
                return Flowable.fromIterable(Server.server.NewsInShow);//fixme 如果运行了这一句代表网络出现问题没有正常返回
            }
        }).toList().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public static Single<List<Entity>> GetEntities(final String name) {
        return Flowable.fromCallable(new Callable<List<Entity>>() {
            @Override
            public List<Entity> call() throws Exception {
                return EntityLoader.GetEntity(name);
            }
        }).flatMap(new Function<List<Entity>, Publisher<Entity>>() {
            @Override
            public Publisher<Entity> apply(List<Entity> entities) {
                return Flowable.fromIterable(entities);//fixme 如果运行了这一句代表网络出现问题没有正常返回
            }
        }).toList().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public static Single<List<Scholar>> GetScholar() {
        return Flowable.fromCallable(new Callable<List<Scholar>>() {
            @Override
            public List<Scholar> call() throws Exception {
                return Server.server.ScholarInfo;
            }
        }).flatMap(new Function<List<Scholar>, Publisher<Scholar>>() {
            @Override
            public Publisher<Scholar> apply(List<Scholar> entities) {
                return Flowable.fromIterable(entities);//fixme 如果运行了这一句代表网络出现问题没有正常返回
            }
        }).toList().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public static Single<List<Scholar>> GetP_Scholar() {
        return Flowable.fromCallable(new Callable<List<Scholar>>() {
            @Override
            public List<Scholar> call() throws Exception {
                return Server.server.P_Scholar;
            }
        }).flatMap(new Function<List<Scholar>, Publisher<Scholar>>() {
            @Override
            public Publisher<Scholar> apply(List<Scholar> entities) {
                return Flowable.fromIterable(entities);//fixme 如果运行了这一句代表网络出现问题没有正常返回
            }
        }).toList().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public static Single<List<Keyword>> GetKeyword(int c, Context context) {
        return Flowable.fromCallable(new Callable<List<Keyword>>() {
            @Override
            public List<Keyword> call() throws Exception {
                try {
                    List<Keyword> ls = new ArrayList<Keyword>();
                    String msg = "";
                    String content = "";
                    BufferedReader reader = null;
                    if(c == 0){
                        reader = new BufferedReader(new InputStreamReader(context.getResources().openRawResource(R.raw.keyword0)));
                    }
                    else if(c == 1){
                        reader = new BufferedReader(new InputStreamReader(context.getResources().openRawResource(R.raw.keyword1)));
                    }
                    else if(c == 2){
                        reader = new BufferedReader(new InputStreamReader(context.getResources().openRawResource(R.raw.keyword2)));
                    }
                    else{
                        reader = new BufferedReader(new InputStreamReader(context.getResources().openRawResource(R.raw.keyword3)));
                    }
                    content = reader.readLine();
                    JSONArray arr = new JSONArray(content);
                    for(int i = 0; i < 4; i++){
                        JSONArray ar = arr.optJSONArray(i);
                        Keyword ky = new Keyword(ar.optString(0), ar.optInt(1));
                        ls.add(ky);
                    }
                    return ls;
                } catch (Exception e) {
                    return new ArrayList<Keyword>();
                }
            }
        }).flatMap(new Function<List<Keyword>, Publisher<Keyword>>() {
            @Override
            public Publisher<Keyword> apply(List<Keyword> entities) {
                return Flowable.fromIterable(entities);//fixme 如果运行了这一句代表网络出现问题没有正常返回
            }
        }).toList().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public static Single<List<News>> GetCluster(int c, Context context) {
        return Flowable.fromCallable(new Callable<List<News>>() {
            @Override
            public List<News> call() throws Exception {
                try {
                    String msg = "";
                    String content = "";
                    BufferedReader reader = null;
                    if(c == 0){
                        reader = new BufferedReader(new InputStreamReader(context.getResources().openRawResource(R.raw.category0)));
                    }
                    else if(c == 1){
                        reader = new BufferedReader(new InputStreamReader(context.getResources().openRawResource(R.raw.category1)));
                    }
                    else if(c == 2){
                        reader = new BufferedReader(new InputStreamReader(context.getResources().openRawResource(R.raw.category2)));
                    }
                    else{
                        reader = new BufferedReader(new InputStreamReader(context.getResources().openRawResource(R.raw.category3)));
                    }
                    content = reader.readLine();
                    JSONArray arr = new JSONArray(content);
                    ArrayList<News> lst = new ArrayList<News>();
                    Server.server.ReadCluster(arr, lst);
                    return lst;
                } catch (Exception e) {
                    return new ArrayList<News>();
                }
            }
        }).flatMap(new Function<List<News>, Publisher<News>>() {
            @Override
            public Publisher<News> apply(List<News> Newses) {
                //if (Newses.size() > 0)
                return Flowable.fromIterable(Newses);
                //return Flowable.fromIterable(Server.server.NewsInShow);//fixme 如果运行了这一句代表网络出现问题没有正常返回
            }
        }).toList().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
}
