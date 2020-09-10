package com.java.zhushuqi.backend;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;

public class EntityLoader {
    static String link = "https://innovaapi.aminer.cn/covid/api/v1/pneumonia/entityquery?entity=%s";

    static String GetEntityFromURL(String url) throws IOException {
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

    public static ArrayList<Entity> GetEntity(final String name) throws IOException, JSONException {
        String URL_String = new String(String.format(link, name));
        String body = GetEntityFromURL(URL_String);
        if (body.equals("")) {
            Log.d("warning", "No message received.");
        }
        JSONObject news_json = new JSONObject(body);
        return(GetEntityFromJSON(news_json));
    }

    public static ArrayList<Entity> GetEntityFromJSON(JSONObject src) throws JSONException{
        ArrayList<Entity> ent = new ArrayList<Entity>();
        JSONArray arr = src.optJSONArray("data");
        for(int i = 0; i < arr.length(); i++){
            Entity entity = new Entity();
            JSONObject obj = arr.optJSONObject(i);
            entity.hot = obj.optDouble("hot");
            entity.label = obj.optString("label");
            entity.url = obj.optString("url");
            JSONObject abstractinfo = obj.optJSONObject("abstractInfo");
            entity.enwiki = abstractinfo.optString("enwiki");
            entity.baidu = abstractinfo.optString("baidu");
            entity.zhwiki = abstractinfo.optString("zhwiki");
            JSONObject covid = abstractinfo.optJSONObject("COVID");
            JSONObject prop = covid.optJSONObject("properties");
            JSONArray rel = covid.optJSONArray("relations");
            Iterator<String> iterator = prop.keys();
            String key = "", value = "";
            while(iterator.hasNext()) {
                key = (String) iterator.next();
                value = prop.optString(key);
                entity.properties.put(key, value);
            }
            for(int j = 0; j < rel.length(); j++){
                JSONObject jj = rel.optJSONObject(j);
                key = jj.optString("label");
                value = jj.optString("relation");
                if(jj.optString("forward").equals("true")){
                    value = value += "->";
                }
                else{
                    value = value += "<-";
                }
                entity.relations.put(key, value);
            }
            ent.add(entity);
        }
        return ent;
    }


}
