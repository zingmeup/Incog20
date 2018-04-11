package com.example.deepakyadav.incog20;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.widget.SwipeRefreshLayout;
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
import android.webkit.URLUtil;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.deepakyadav.incog20.DataModel.SessionHandler;
import com.example.deepakyadav.incog20.DataModel.TabHandler;
import com.example.deepakyadav.incog20.DataModel.WebViewTabs;
import com.example.deepakyadav.incog20.QuickToolsHandler.ThemeChooser;

import java.net.URI;
import java.net.URL;
import java.util.List;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    public static ProgressBar progressBar;
    Dialog tabDialog;
    ListView tab_view, session_view;
    WebView webView;
    public Toolbar toolbar;
    public static SessionAdapter adapter;

    private boolean navIncognitoEnabled,navReadingModeEnabled,navNoImageEnabled,navFlashplayerEnabled,
            navNightModeEnabled,navProxyEnabled,navAdblockEnabled,navPopupEnabled,navTrackerEnabled;
    BottomNavigationView mBottomNav;
    Dialog urldialog;
    Dialog optionsDialog;
    Dialog themechooserDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar=findViewById(R.id.toolbar);
        webView = findViewById(R.id.webview);
        mBottomNav = findViewById(R.id.navigation);
        session_view = findViewById(R.id.session_history);
        progressBar=findViewById(R.id.toolbar_progress);

        adapter = new SessionAdapter(this,SessionHandler.showSession());
        session_view.setAdapter(adapter);

        tabDialog=new Dialog(this, R.style.urldialogTheme);
        tabDialog.setContentView(R.layout.tab_dialog);
        tabDialog.setCanceledOnTouchOutside(true);
        Window tab_window=tabDialog.getWindow();
        tab_window.setGravity(Gravity.CENTER);

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

        setSupportActionBar(toolbar);

        bottomNav();
        closeCurrentTab();

        new WebViewTabs(webView,toolbar,MainActivity.this);
        WebViewTabs.createNewTab();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.END))
            drawer.closeDrawer(GravityCompat.END);
        else if (webView.canGoBack())
            webView.goBack();
        else
            super.onBackPressed();
    }

    public void closeCurrentTab() {
        ImageButton close_tab = toolbar.findViewById(R.id.closetab);
        close_tab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(WebViewTabs.index==0){
                    WebViewTabs.startWebView("about:blank");
                }
                else
                    WebViewTabs.closeCurrentTab();
            }
        });
    }

    public void bottomNav(){
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) mBottomNav.getLayoutParams();
        layoutParams.setBehavior(new BottomNavigationViewBehavior());
        mBottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                openDialog(item);
                return true;
            }
        });
    }

    private void openDialog(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.new_tab:
                tabDialog.show();

                tab_view = tabDialog.findViewById(R.id.tab_list);
                CustomListAdapter adapter = new CustomListAdapter(this, TabHandler.showTabs());
                tab_view.setAdapter(adapter);

                break;
            case R.id.home:
                Toast.makeText(MainActivity.this,"Launch Home Screen",Toast.LENGTH_SHORT).show();
                break;
            case R.id.open_options:
                optionsDialog.show();
                break;
        }
    }

    public void openURLDialog(View view){
        final Dialog urldialog=new Dialog(this,R.style.urldialogTheme);
        urldialog.setContentView(R.layout.urldialog);
        urldialog.setCanceledOnTouchOutside(true);
        Window window=urldialog.getWindow();
        window.setGravity(Gravity.TOP);
        urldialog.show();
        final EditText currenturl=urldialog.findViewById(R.id.toolbar_currentURL);
        currenturl.setText(webView.getUrl());
        ImageButton clear=urldialog.findViewById(R.id.toolbar_clearURL);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currenturl.setText("");
            }
        });
        currenturl.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId== EditorInfo.IME_ACTION_GO){

                    WebViewTabs.startWebView(v.getText().toString());
                    urldialog.dismiss();
                }
                return false;
            }
        });
    }

    public void crateNewTab(View view){
        WebViewTabs.createNewTab();
        tabDialog.dismiss();
        tabDialog.show();
    }

    public void closeListTab(View view) {
        int position = tab_view.getPositionForView(view);

        if(TabHandler.showTabs().size()==1){
            WebViewTabs.startWebView("about:blank");
            TabHandler.showTabs().get(position).setTitle("about:blank");
            TabHandler.showTabs().get(position).setUrl("about:blank");
        }
        else
            WebViewTabs.closeTabFromList(position);

        tabDialog.dismiss();
        tabDialog.show();
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
        ThemeChooser.getThemeChooser(MainActivity.this, toolbar, mBottomNav, urldialog, optionsDialog)
                .setTheme(view.getId());
    }
}

