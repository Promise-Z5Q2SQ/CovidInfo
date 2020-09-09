package com.java.zhushuqi.backend;
import java.util.HashMap;

public class Entity {
    public double hot;//热度
    public String label;//图谱节点名
    public String url;//链接
    public String enwiki;//英文维基
    public String baidu;//百度
    public String zhwiki;//中文维基
    public HashMap<String,String> properties;//特征，(key,value)=(特征,特征表现)
    public HashMap<String, String> relations;//相关词，(key,value)=(关系,相关词)
    public Entity(){
        properties = new HashMap<String, String>();
        relations = new HashMap<String, String>();
    }
}
