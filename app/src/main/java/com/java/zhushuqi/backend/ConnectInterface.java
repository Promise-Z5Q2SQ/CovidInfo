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

    //初始化新闻列表
    public static Single<List<News>> InitServer() {
        return Flowable.fromCallable(new Callable<List<News>>() {
            @Override
            public List<News> call() throws Exception {
                try {
                    Server.server.InitServer();
                    return new ArrayList<News>();
                } catch (Exception e) {
                    return new ArrayList<News>();
                }
            }
        }).flatMap(new Function<List<News>, Publisher<News>>() {
            @Override
            public Publisher<News> apply(List<News> Newses) {
                if (Newses.size() > 0) return Flowable.fromIterable(Newses);
                return Flowable.fromIterable(Server.server.ViewedHistory);//fixme 如果运行了这一句代表网络出现问题没有正常返回
            }
        }).toList().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public static Single<List<News>> GetNews(final int page, final String type, final int size){
        return Flowable.fromCallable(new Callable<List<News>>() {
            @Override
            public List<News> call() throws Exception {
               return NewsLoader.GetNews(page, type, size);
            }
        }).flatMap(new Function<List<News>, Publisher<News>>() {
            @Override
            public Publisher<News> apply(List<News> Newses) {
                if (Newses.size() > 0) return Flowable.fromIterable(Newses);
                return Flowable.fromIterable(Server.server.ViewedHistory);//fixme 如果运行了这一句代表网络出现问题没有正常返回
            }
        }).toList().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

}
