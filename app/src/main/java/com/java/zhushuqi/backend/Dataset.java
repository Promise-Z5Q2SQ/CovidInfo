package com.java.zhushuqi.backend;

import java.util.ArrayList;
import java.util.Date;



public class Dataset{
    public Date start_date;
    public ArrayList<Datapoint> data;
    public Dataset(){
        data = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Dataset{" +
                "start_date=" + start_date +
                ", data=" + data +
                '}';
    }
}

