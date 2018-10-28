package com.example.deepakyadav.incog20.database;

import java.util.ArrayList;

public class Rawdata {
    ArrayList<String> adblockList;
    private static Rawdata rawdata;
    Rawdata(){
    adblockList=new ArrayList<>();
        adblockList.add("analytics.adpost.org");
        adblockList.add("analytics.google.com");
        adblockList.add("analytics.live.com");
        adblockList.add("analytics.newsinc.com");
        adblockList.add("analytics.yahoo.com");
        adblockList.add("google-analytics.com");
        adblockList.add("googleadservices.com");
        adblockList.add("googlesyndication.com");
    }

    public ArrayList<String> getAdblockList() {
        return adblockList;
    }

    public static Rawdata getRawdata() {
        if (rawdata==null){
            rawdata=new Rawdata();
        }
        return rawdata;
    }
}
