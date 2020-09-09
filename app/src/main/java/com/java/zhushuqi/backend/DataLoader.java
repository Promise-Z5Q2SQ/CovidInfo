package com.java.zhushuqi.backend;

import android.provider.ContactsContract;
import android.text.format.DateUtils;
import android.util.Log;

import java.lang.reflect.Array;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.json.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.sql.DataSource;

public class DataLoader {
    static HashMap<String, Dataset> datamap = new HashMap<String, Dataset>();
    static String link = "https://covid-dashboard.aminer.cn/api/dist/epidemic.json";
    static String GetDataFromURL(String url) throws IOException {
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
    public static HashMap<String, Dataset> GetData() throws IOException, JSONException {
        String body = GetDataFromURL(link);
        if(body.equals("")) {
            Log.d("warning","No message received.");
        }
        JSONObject data_json = new JSONObject(body);
        try{return GetDataFromJSON(data_json);}
        catch(ParseException e){System.out.println("Parse Exception!");return null;}
    }
    public static HashMap<String, Dataset> GetDataFromJSON(JSONObject src) throws JSONException, ParseException{
        JSONObject list;
        Iterator<String> iterator = src.keys();
        String key;
        String date;
        while(iterator.hasNext()){
            key = (String) iterator.next();
            list = src.optJSONObject(key);
            Dataset newset = new Dataset();
            date = list.optString("begin");
            newset.start_date = new SimpleDateFormat("yyyy-MM-dd").parse(date);
            JSONArray arr = list.optJSONArray("data");
            for(int i = 0; i < arr.length(); i++){
                JSONArray darr = arr.optJSONArray(i);
                Datapoint dp = new Datapoint();
                dp.confirmed = darr.optInt(0);
                dp.suspected = darr.optInt(1);
                dp.cured = darr.optInt(2);
                dp.dead = darr.optInt(3);
                dp.risk = darr.optInt(5);
                dp.timefromstart = i;
                if(!dp.equals(null)) {
                    newset.data.add(dp);
                }
            }
            if(newset != null){
                datamap.put(key, newset);
            }
        }
        return datamap;
    }
}

