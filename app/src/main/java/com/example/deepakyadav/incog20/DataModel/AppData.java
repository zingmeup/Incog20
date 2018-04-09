package com.example.deepakyadav.incog20.DataModel;

import android.graphics.Bitmap;
import android.os.Bundle;

public class AppData {

    private String url;
    private String title,user;
    private Bitmap favicon;
    private Bundle saved_state;

    public String getURL(){
        return this.url;
    }

    public String getTitle(){
        return this.title;
    }

    public Bitmap getFavicon() {
        return favicon;
    }

    public Bundle getSaved_state() {
        return saved_state;
    }

    public String getUser() {
        return user;
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

    public void setSaved_state(Bundle saved_state) {
        this.saved_state = saved_state;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
