package com.java.zhushuqi.backend;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.*;
import java.util.concurrent.*;

import io.reactivex.*;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

import org.reactivestreams.*;

public class ConnectInterface {

    @RequiresApi(api = Build.VERSION_CODES.N)

    //初始化服务器
    public static Single<List<News>> InitServer() {
        return Flowable.fromCallable(new Callable<List<News>>() {
            @Override
            public List<News> call() throws Exception {
                try {
                    Server.server.InitServer();
                    return Server.server.NewsInShow;
                } catch (Exception e) {
                    System.out.println("Ex");
                    return new ArrayList<News>();
                }
            }
        }).flatMap(new Function<List<News>, Publisher<News>>() {
            @Override
            public Publisher<News> apply(List<News> Newses) {
                if (Newses.size() > 0) return Flowable.fromIterable(Newses);
                return Flowable.fromIterable(Server.server.NewsHistory);//fixme 如果运行了这一句代表网络出现问题没有正常返回
            }
        }).toList().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    //初始化新闻列表
    public static void InitNews() {
        Single.fromCallable(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                Server.server.InitNews();
                return new Object();
            }
        }).subscribeOn(Schedulers.io()).subscribe();
    }

    //获取当前要显示的新闻
    public static Single<List<News>> GetCurrentNews() {
        return Flowable.fromCallable(new Callable<List<News>>() {
            @Override
            public List<News> call() throws Exception {
                try {
                    return Server.server.NewsInShow;
                } catch (Exception e) {
                    System.out.println("Ex");
                    return new ArrayList<News>();
                }
            }
        }).flatMap(new Function<List<News>, Publisher<News>>() {
            @Override
            public Publisher<News> apply(List<News> Newses) {
                if (Newses.size() > 0) return Flowable.fromIterable(Newses);
                return Flowable.fromIterable(Server.server.NewsHistory);//fixme 如果运行了这一句代表网络出现问题没有正常返回
            }
        }).toList().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }


    //下拉获取最新新闻
    public static Single<List<News>> GetLatestNews() {
        return Flowable.fromCallable(new Callable<List<News>>() {
            @Override
            public List<News> call() throws Exception {
                try {
                    Server.server.GetLatestNews();
                    return Server.server.NewsInShow;
                } catch (Exception e) {
                    return new ArrayList<News>();
                }
            }
        }).flatMap(new Function<List<News>, Publisher<News>>() {
            @Override
            public Publisher<News> apply(List<News> Newses) {
                if (Newses.size() > 0) return Flowable.fromIterable(Newses);
                return Flowable.fromIterable(Server.server.NewsHistory);//fixme 如果运行了这一句代表网络出现问题没有正常返回
            }
        }).toList().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    //上拉获取旧新闻
    public static Single<List<News>> RetrievePresentNews() {
        return Flowable.fromCallable(new Callable<List<News>>() {
            @Override
            public List<News> call() throws Exception {
                try {
                    Server.server.RetrievePresentNews();
                    return Server.server.NewsInShow;
                } catch (Exception e) {
                    return new ArrayList<News>();
                }
            }
        }).flatMap(new Function<List<News>, Publisher<News>>() {
            @Override
            public Publisher<News> apply(List<News> Newses) {
                if (Newses.size() > 0) return Flowable.fromIterable(Newses);
                return Flowable.fromIterable(Server.server.NewsHistory);//fixme 如果运行了这一句代表网络出现问题没有正常返回
            }
        }).toList().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public static void AddNewsToHistory(final int index) {
        Single.fromCallable(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                Server.server.AddNewsToHistory(index);
                return new Object();
            }
        }).subscribeOn(Schedulers.io()).subscribe();
    }


}
