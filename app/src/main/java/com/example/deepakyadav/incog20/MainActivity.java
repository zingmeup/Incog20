package com.example.deepakyadav.incog20;

import android.os.Bundle;
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
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);

        WebView webView = findViewById(R.id.webview);
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("http://www.youtube.com");

//        FloatingActionButton fab = findViewById(R.id.fab);
//        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) fab.getLayoutParams();
//        layoutParams.setBehavior(new FABBehavior());

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

