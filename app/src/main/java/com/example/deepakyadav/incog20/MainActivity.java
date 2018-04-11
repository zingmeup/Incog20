package com.example.deepakyadav.incog20;

import android.app.Dialog;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.util.Base64;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.deepakyadav.incog20.QuickToolsHandler.ThemeChooser;

public class MainActivity extends AppCompatActivity{
    private boolean navIncognitoEnabled,navReadingModeEnabled,navNoImageEnabled,navFlashplayerEnabled,
            navNightModeEnabled,navProxyEnabled,navAdblockEnabled,navPopupEnabled,navTrackerEnabled;
    WebView webView;
    Toolbar toolbar;
    ProgressBar progressBar;
    BottomNavigationView mBottomNav;
    Dialog urldialog;
    Dialog optionsDialog;
    Dialog themechooserDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBottomNav = findViewById(R.id.navigation);

        urldialog=new Dialog(this,R.style.urldialogTheme);
        urldialog.setContentView(R.layout.urldialog);
        urldialog.setCanceledOnTouchOutside(true);
        Window window=urldialog.getWindow();
        window.setGravity(Gravity.TOP);

        optionsDialog=new Dialog(this, R.style.urldialogTheme);
        optionsDialog.setContentView(R.layout.optionsdialog);
        optionsDialog.setCanceledOnTouchOutside(true);
        Window window2=optionsDialog.getWindow();
        window2.setGravity(Gravity.BOTTOM);


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
            if(webView.canGoBack()){
                webView.goBack();
            }else{
                super.onBackPressed();
            }
        }
    }

    private void openDialog(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.new_tab:
                Toast.makeText(MainActivity.this,"New Tab",Toast.LENGTH_SHORT).show();
                break;
            case R.id.home:
                Toast.makeText(MainActivity.this,"Launch Home Screen",Toast.LENGTH_SHORT).show();
                break;
            case R.id.open_options:
                optionsDialog.show();

                break;
        }
    }
    public void navigationMenuHandler(View view){
        Log.e("Onclick", view.getId()+"");
        View viewtemp = (View) findViewById(view.getId());
        switch (view.getId()){
            case R.id.nav_icognito_btn:
                if(navIncognitoEnabled){
                    navIncognitoEnabled=false;
                    viewtemp.setBackground(getDrawable(R.drawable.nav_icon_background_shape));


                }else{
                    navIncognitoEnabled=true;
                    viewtemp.setBackground(getDrawable(R.drawable.nav_icon_background_shape_toggled));


                }
                break;
            case R.id.nav_readmode_btn:
                if(navReadingModeEnabled){
                    navReadingModeEnabled=false;
                    viewtemp.setBackground(getDrawable(R.drawable.nav_icon_background_shape));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        View view1 = (View) findViewById(R.id.drawer_layout);
                        view1.setForeground(new ColorDrawable(getResources().getColor(R.color.transparent)));
                    }

                }else{
                    navReadingModeEnabled=true;
                    viewtemp.setBackground(getDrawable(R.drawable.nav_icon_background_shape_toggled));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        View view1 = (View) findViewById(R.id.drawer_layout);
                        view1.setForeground(new ColorDrawable(getResources().getColor(R.color.readingmode)));
                    }

                }
                break;
            case R.id.nav_noimage_btn:
                if(navNoImageEnabled){
                    navNoImageEnabled=false;
                    viewtemp.setBackground(getDrawable(R.drawable.nav_icon_background_shape));
                    Log.wtf("nav_noimage_btn", "navNoImageEnabled=false");
                    webView.getSettings().setLoadsImagesAutomatically(true);
                    webView.getSettings().setBlockNetworkImage(false);

                }else{
                    navNoImageEnabled=true;
                    viewtemp.setBackground(getDrawable(R.drawable.nav_icon_background_shape_toggled));
                    webView.getSettings().setLoadsImagesAutomatically(false);
                    webView.getSettings().setBlockNetworkImage(true);
                    Log.wtf("nav_noimage_btn", "navNoImageEnabled=true");

                }
                break;
            case R.id.nav_flashplayer_btn_btn:
                if(navFlashplayerEnabled){
                    navFlashplayerEnabled=false;
                    viewtemp.setBackground(getDrawable(R.drawable.nav_icon_background_shape));
                    webView.getSettings().setJavaScriptEnabled(false);
                    Log.wtf("nav_javascript_btn", "navJavaScriptEnabled=false");
                    webView.getSettings().setPluginState(WebSettings.PluginState.OFF);
                    Log.wtf("nav_flashplayer_btn_btn", "navFlashplayerEnabled=fasle");


                }else{
                    navFlashplayerEnabled=true;
                    viewtemp.setBackground(getDrawable(R.drawable.nav_icon_background_shape_toggled));
                    webView.getSettings().setJavaScriptEnabled(false);
                    Log.wtf("nav_javascript_btn", "navJavaScriptEnabled=true");
                    webView.getSettings().setPluginState(WebSettings.PluginState.ON);
                    Log.wtf("nav_flashplayer_btn_btn", "navFlashplayerEnabled=true");

                }
                break;
            case R.id.nav_nightmode_btn:
                if(navNightModeEnabled){
                    navNightModeEnabled=false;
                    viewtemp.setBackground(getDrawable(R.drawable.nav_icon_background_shape));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        View view1 = (View) findViewById(R.id.drawer_layout);
                        view1.setForeground(new ColorDrawable(getResources().getColor(R.color.transparent)));
                    }
                }else{
                    navNightModeEnabled=true;
                    viewtemp.setBackground(getDrawable(R.drawable.nav_icon_background_shape_toggled));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        View view1 = (View) findViewById(R.id.drawer_layout);
                        view1.setForeground(new ColorDrawable(getResources().getColor(R.color.nightmode)));
                    }
                }
                break;
            case R.id.nav_proxy_btn:
                if(navProxyEnabled){
                    navProxyEnabled=false;
                    viewtemp.setBackground(getDrawable(R.drawable.nav_icon_background_shape));

                }else{
                    navProxyEnabled=true;
                    viewtemp.setBackground(getDrawable(R.drawable.nav_icon_background_shape_toggled));

                }
                break;
            case R.id.nav_adblock_btn:
                if(navAdblockEnabled){
                    navAdblockEnabled=false;
                    viewtemp.setBackground(getDrawable(R.drawable.nav_icon_background_shape));

                }else{
                    navAdblockEnabled=true;
                    viewtemp.setBackground(getDrawable(R.drawable.nav_icon_background_shape_toggled));
                }
                break;

            case R.id.nav_popup_btn:
                if(navPopupEnabled){
                    navPopupEnabled=false;
                    viewtemp.setBackground(getDrawable(R.drawable.nav_icon_background_shape));
                    webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(false);

                }else{
                    navPopupEnabled=true;
                    viewtemp.setBackground(getDrawable(R.drawable.nav_icon_background_shape_toggled));
                    webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);

                }
                break;
            case R.id.nav_tracker_btn:
                if(navTrackerEnabled){
                    navTrackerEnabled=false;
                    viewtemp.setBackground(getDrawable(R.drawable.nav_icon_background_shape));

                }else{
                    navTrackerEnabled=true;
                    viewtemp.setBackground(getDrawable(R.drawable.nav_icon_background_shape_toggled));

                }
                break;

        }
    }
    public void setTheme(View view){
        ThemeChooser.getThemeChooser(MainActivity.this, toolbar, mBottomNav, urldialog, optionsDialog).setTheme(view.getId());

    }
    public void dialogOptionsHandler(View view){

        optionsDialog.hide();
        switch (view.getId()){
            case R.id.options_history:
                break;
            case R.id.options_bookmarks:
                break;
            case R.id.options_themes:
                optionsDialog.hide();
                themechooserDialog=new Dialog(this, R.style.urldialogTheme);
                Window window=themechooserDialog.getWindow();
                window.setGravity(Gravity.CENTER);
                themechooserDialog.setContentView(R.layout.dialog_themechooser);
                themechooserDialog.setCanceledOnTouchOutside(true);
                themechooserDialog.show();
                break;
            case R.id.options_findin:
                break;
            case R.id.options_downloads:
                break;
            case R.id.options_login:
                break;

        }
    }


}

