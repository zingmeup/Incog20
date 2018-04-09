package com.example.deepakyadav.incog20;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.util.Base64;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.view.MenuItem;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    WebView webView;
    Toolbar toolbar;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView mBottomNav = findViewById(R.id.navigation);
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) mBottomNav.getLayoutParams();
        layoutParams.setBehavior(new BottomNavigationViewBehavior());
        mBottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                openDialog(item);
                return true;
            }
        });
        toolbar=findViewById(R.id.toolbar);
        progressBar=toolbar.findViewById(R.id.toolbar_progress);
        setSupportActionBar(toolbar);
        webView = findViewById(R.id.webview);
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {


                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        progressBar.setProgress(newProgress, true);
                    }else{
                    progressBar.setProgress(newProgress);
                }
                super.onProgressChanged(view, newProgress);
            }

            @Override
            public void onReceivedIcon(WebView view, Bitmap icon) {
                ImageView toolbarFavicon=toolbar.findViewById(R.id.toolbar_favicon);
                Bitmap favicon=webView.getFavicon();
                toolbarFavicon.setImageBitmap(favicon);
                super.onReceivedIcon(view, icon);
            }
        });
        webView.setWebViewClient(new WebViewClient(){

            @Override
            public void onLoadResource(WebView view, String url) {
                super.onLoadResource(view, url);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }
            
            @Override
            public void onPageFinished(WebView view, String url) {
                TextView toolbarTitle=toolbar.findViewById(R.id.toolbar_title);
                toolbarTitle.setText(webView.getTitle());
                TextView toolbarurl=toolbar.findViewById(R.id.toolbar_url);
                toolbarurl.setText(webView.getUrl());

                super.onPageFinished(view, url);
            }
        });

        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("http://www.youtube.com");



    }
    public void openURLDialog(View view){
        Dialog urldialog=new Dialog(this,R.style.urldialogTheme);
        urldialog.setContentView(R.layout.urldialog);
        urldialog.setCanceledOnTouchOutside(true);
        urldialog.show();
        final EditText currenturlET=urldialog.findViewById(R.id.toolbar_currentURL);
        currenturlET.setText(webView.getUrl());
        ImageButton clear=urldialog.findViewById(R.id.toolbar_clearURL);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currenturlET.setText("");
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.END)) {
            drawer.closeDrawer(GravityCompat.END);
        } else {
            super.onBackPressed();
        }
    }

    private void openDialog(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_home:
                Toast.makeText(MainActivity.this,R.string.text_home,Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_notifications:
                Toast.makeText(MainActivity.this,R.string.text_notifications,Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_search:
                Toast.makeText(MainActivity.this,R.string.text_search,Toast.LENGTH_SHORT).show();
                break;
        }
    }
}

