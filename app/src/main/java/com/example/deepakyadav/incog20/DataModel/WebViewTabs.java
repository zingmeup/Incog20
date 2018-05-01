package com.example.deepakyadav.incog20.DataModel;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import com.example.deepakyadav.incog20.MainActivity;
import com.example.deepakyadav.incog20.R;

public class WebViewTabs {

    public static int index=-1;
    public static AppData data;
    private static WebView webView;
    private static Toolbar toolbar;
    public static Context context;

    public WebViewTabs(WebView view, Toolbar toolbar, Context context){
        this.webView = view;
        this.toolbar = toolbar;
        this.context = context;
    }

    public static void createNewTab(){
        index++;
        data = new AppData();
//        data.setFavicon(webView.getFavicon());
        data.setTitle("about:blank");
        data.setUrl("about:blank");
        data.setIndex(index);
        TabHandler.newTab(data);
        startWebView("about:blank");
    }

    public static void closeCurrentTab(){
        index--;
        TabHandler.destroyTab(data.getIndex());
        data = TabHandler.showTabs().get(index);
        startWebView(data.getURL());
    }

    public static void openTabFromList(int position){
        data = TabHandler.showTabs().get(position);
        startWebView(data.getURL());
        }

    public static void openSessionFromList(String url){
        startWebView(url);
    }

    public static void closeTabFromList(int position){
        index--;
        TabHandler.destroyTab(position);
        data = TabHandler.showTabs().get(index);
        startWebView(data.getURL());
    }

    public static void startWebView(String url){
        webView.setWebChromeClient(new WebChromeClient(){
            public final String lastVisited="";

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                Log.e("progress", ""+newProgress);
                MainActivity.progressBar.setProgress(newProgress);
                if(newProgress==100){
                    if(!SessionHandler.showSession().contains(view.getUrl()) && !view.getUrl().equals(SessionHandler.getLastVisited())){
                        SessionHandler.setLastVisited(view.getUrl());
                        SessionHandler sessionHandler = new SessionHandler();
                        sessionHandler.setTitle(webView.getTitle());
                        sessionHandler.setUrl(view.getUrl());
                        SessionHandler.showSession().add(sessionHandler);
                        MainActivity.adapter.notifyDataSetChanged();
                        Log.e("HISTORY",view.getUrl());
                    }
                    MainActivity.progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onReceivedIcon(WebView view, Bitmap icon) {
                super.onReceivedIcon(view, icon);
//                ImageView toolbarFavicon=toolbar.findViewById(R.id.toolbar_favicon);
//                Bitmap favicon=webView.getFavicon();
//                toolbarFavicon.setImageBitmap(favicon);
//                data.setFavicon(webView.getFavicon());
            }
        });
        webView.setWebViewClient(new WebViewClient(){

            @Override
            public void onReceivedSslError(WebView view, final SslErrorHandler handler, SslError error) {
                String message, title="Warning! ";
                Log.i("onSSLError", "SSL error recieved");
                AlertDialog.Builder builder=new AlertDialog.Builder(context);
                switch (error.getPrimaryError()){
                    case SslError.SSL_UNTRUSTED:
                        title+="SSL Untrusted";
                        message="The certificate for this site is Untrusted";
                        break;
                    case SslError.SSL_IDMISMATCH:
                        title+="SSL ID Mismatch";
                        message="The certificate for this site is Mismatched";
                        break;
                    case SslError.SSL_INVALID:
                        title+="SSL Invalid";
                        message="The sertificate for this site is Invalid";
                        break;
                    case SslError.SSL_EXPIRED:
                        title+="SSL Expired";
                        message="The sertificate for this site is Expired";
                        break;
                    default:
                        title+="SSL Unknown";
                        message="Unknown Error";
                        break;

                }
                builder.setTitle(title);
                builder.setMessage(message);
                builder.setPositiveButton("Continue", new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(context, "Unsafe", Toast.LENGTH_SHORT).show();
                        Log.i("proceeding resp", "asdgsajgdahhgdjasgdajhgdasja");
                        handler.proceed();
                    }
                });
                builder.setNegativeButton("Back to Safety", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startWebView("about:blank");
                    }
                });
                AlertDialog alertDialog=builder.create();
                alertDialog.show();

            }

            @Override
            public void onLoadResource(WebView view, String url) {
                super.onLoadResource(view, url);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                MainActivity.progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                TextView toolbarTitle=toolbar.findViewById(R.id.toolbar_title);
                toolbarTitle.setText(webView.getTitle());
                TextView toolbarurl=toolbar.findViewById(R.id.toolbar_url);
                toolbarurl.setText(webView.getUrl());

                data.setTitle(webView.getTitle());
                data.setUrl(webView.getUrl());
            }
        });

        webView.getSettings().setJavaScriptEnabled(true);

        if(url.equals("about:blank")){
            String html;
            webView.loadData("<!DOCTYPE html><html><head><title>Incog 2.0</title><style type=\"text/css\"> center {margin: auto;width: 100%; padding: 10px;margin-top: 80%;\n}</style></head><body><div class=\"center\" style=\"text-align: center; height:600px; margin-top:400px\"><h1 style=\"font-size: 50px;\">IᑎᑕOG 2.0</h1></div></body></html>", "text/html",null);
        }else {
            String currentURL=url.replace("http://", "")
                    .replace("https://", "")
                    .replace(" ", "").trim();
            String finalurl="https://"+currentURL;
            if(Patterns.WEB_URL.matcher(currentURL).matches()){

                webView.loadUrl(finalurl);
            }else{
                String googleSearch=url.replace("http://", "").replace("https://", "").replace(" ", "+");
                googleSearch="https://www.google.com/search?q="+googleSearch;
                webView.loadUrl(googleSearch);
            }
        }
    }
}
