package com.java.zhushuqi.backend;

import android.graphics.BitmapFactory;
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

public class ScholarLoader {
    public static String link = "https://innovaapi.aminer.cn/predictor/api/v1/valhalla/highlight/get_ncov_expers_list?v=2";

    static String GetScholarFromURL(String url) throws IOException {
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

    public static ArrayList<Scholar> GetScholar(ArrayList<Scholar> scl) throws IOException, JSONException {
        String body = GetScholarFromURL(link);
        if (body.equals("")) {
            Log.d("warning", "No message received.");
            return null;
        }
        JSONObject scholar_json = new JSONObject(body);
        return GetScholarFromJSON(scholar_json, scl);
    }

    public static ArrayList<Scholar> GetScholarFromJSON(JSONObject src, ArrayList<Scholar> scl) throws JSONException, IOException {
        News news = new News();
        JSONArray obj = src.optJSONArray("data");
        for (int i = 0; i < obj.length(); i++) {
            Scholar p = new Scholar();
            JSONObject inf = obj.optJSONObject(i);
            p.avatar = inf.optString("avatar");
            if (p.avatar.equals("null"))
                p.img = null;
            else {
                URL url = new URL(p.avatar);
                p.img = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            }
            p.name = inf.optString("name");
            String name_zh = inf.optString("name_zh");
            if (!name_zh.equals("")) p.name = name_zh;
            p.num_viewed = inf.optInt("num_viewed");
            p.is_passedaway = inf.optString("is_passedaway");
            JSONObject indices = inf.optJSONObject("indices");
            p.activity = indices.optString("activity");
            p.citations = indices.optInt("citations");
            p.diversity = indices.optString("diversity");
            p.gindex = indices.optInt("gindex");
            p.hindex = indices.optInt("hindex");
            p.sociability = indices.optString("sociability");
            JSONObject profile = inf.optJSONObject("profile");
            p.affiliation = profile.optString("affiliation");
            p.affiliation_zh = profile.optString("affiliation_zh");
            p.bio = profile.optString("bio");
            p.edu = profile.optString("edu");
            p.position = profile.optString("position");
            p.work = profile.optString("work");
            scl.add(p);
        }
        return scl;
    }
}
