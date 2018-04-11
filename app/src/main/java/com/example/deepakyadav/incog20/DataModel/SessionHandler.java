package com.example.deepakyadav.incog20.DataModel;

import java.util.ArrayList;

public class SessionHandler {

    String url;
    String title;
    private static ArrayList<SessionHandler> session = new ArrayList<>();
    private static String lastVisited;

    public static String getLastVisited() {
        return lastVisited;
    }

    public static void setLastVisited(String lastVisited) {
        SessionHandler.lastVisited = lastVisited;
    }

    public static ArrayList<SessionHandler> showSession(){
        return session;
    }

    public void setUrl(String url){
        this.url = url;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public String getTitle() {
        return title;
    }
}
