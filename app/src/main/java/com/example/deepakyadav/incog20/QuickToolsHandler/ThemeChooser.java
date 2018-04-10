package com.example.deepakyadav.incog20.QuickToolsHandler;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

public class ThemeChooser {
    Activity activity;
    private static ThemeChooser themeChooser;

    public ThemeChooser(Activity activity) {
        this.activity=activity;
    }

    public ThemeChooser getThemeChooser(Activity activity) {
        if(themeChooser==null){
            themeChooser=new ThemeChooser(activity);
        }
        return themeChooser;
    }
    public void setTheme(int colorId){
        activity.getActionBar().setBackgroundDrawable(new ColorDrawable(activity.getResources().getColor(colorId)));


    }

}
