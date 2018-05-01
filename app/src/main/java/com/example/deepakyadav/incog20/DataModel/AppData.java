package com.example.deepakyadav.incog20.DataModel;

import android.graphics.Bitmap;

public class AppData {

    private String url;
    private String title;
    private Bitmap favicon;
    private int index;

    public String getURL(){
        return this.url;
    }

    public String getTitle(){
        return this.title;
    }

    public Bitmap getFavicon() {
        return favicon;
    }

    public int getIndex() {
        return index;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setFavicon(Bitmap favicon) {
        this.favicon = favicon;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
