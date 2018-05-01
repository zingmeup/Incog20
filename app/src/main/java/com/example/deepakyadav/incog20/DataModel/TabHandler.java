package com.example.deepakyadav.incog20.DataModel;

import java.util.ArrayList;

public class TabHandler {

    private static ArrayList<AppData> tabs = new ArrayList<>();

    public TabHandler(){
    }

    public static void newTab(AppData data){
        tabs.add(data);
    }

    public static void destroyTab(int index){
        tabs.remove(index);
    }

    public static ArrayList<AppData> showTabs(){
        return tabs;
    }

}
