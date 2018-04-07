package com.example.deepakyadav.incog20.DataModel;

import android.content.Context;
import android.graphics.Bitmap;
import android.webkit.WebView;

import java.util.ArrayList;

public class WebViewTabs {
    static private WebViewTabs webViewTabs;
    private ArrayList<WebView> webViewList;
    private static int index;

    WebViewTabs(){
        if(webViewList==null){
            webViewList=new ArrayList<>();
            index=0;
        }
    }
    public void newTab(Context context){
        index++;
        WebView tempwebView=new WebView(context);
        tempwebView.setId(index);
        webViewList.add(tempwebView);
    }
    public WebView getWebView(int index){
        return webViewList.get(index);
    }
    public WebViewTabs getInstance(){
        if(webViewTabs==null){
            webViewTabs=new WebViewTabs();
        }
        return webViewTabs;
    }
    public ArrayList<WebView> getWebViewList() {
        return webViewList;
    }
    public Bitmap getFavicon(int index){
        return webViewList.get(index).getFavicon();
    }

    public String getTitle(int index){
    return webViewList.get(index).getTitle();
    }
    public String getUrl(int index){
        return webViewList.get(index).getUrl();
    }
}
