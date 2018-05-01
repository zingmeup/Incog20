package com.example.deepakyadav.incog20.QuickToolsHandler;

import android.app.Activity;
import android.app.Dialog;
import android.content.res.ColorStateList;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.example.deepakyadav.incog20.R;

public class ThemeChooser {
    Activity activity;
    Toolbar toolbar;
    Dialog urldialog, optionDialog;
    BottomNavigationView bottomNavigationView;
    private static ThemeChooser themeChooser;

    public ThemeChooser(Activity activity, Toolbar toolbar, BottomNavigationView bottomNavigationView, Dialog urldialog, Dialog optionDialog)
    {
        this.activity=activity;
        this.toolbar=toolbar;
        this.bottomNavigationView=bottomNavigationView;
        this.urldialog=urldialog;
        this.optionDialog=optionDialog;

    }

    static public ThemeChooser getThemeChooser(Activity activity, Toolbar toolbar, BottomNavigationView bottomNavigationView, Dialog urldialog, Dialog optionsDialog) {
        if(themeChooser==null){
            themeChooser=new ThemeChooser(activity, toolbar, bottomNavigationView, urldialog, optionsDialog);
        }
        return themeChooser;
    }
    public void setTheme(int colorId){
        switch (colorId){
            case R.id.blue:
                colorChanger(R.color.theme_blue_colorPrimaryDark, R.color.theme_blue_colorPrimaryLight,
                        R.color.theme_blue_colorPrimary, R.color.theme_blue_colortext,
                        R.color.theme_blue_colorAccent, R.color.theme_blue_colorPrimaryText,
                        R.color.theme_blue_colorSecondaryText, R.color.theme_blue_colorDivider);
                break;
            case R.id.lightblue:                colorChanger(R.color.theme_lightblue_colorPrimaryDark, R.color.theme_lightblue_colorPrimaryLight,
                    R.color.theme_lightblue_colorPrimary, R.color.theme_lightblue_colortext,
                    R.color.theme_lightblue_colorAccent, R.color.theme_lightblue_colorPrimaryText,
                    R.color.theme_lightblue_colorSecondaryText, R.color.theme_lightblue_colorDivider);
                break;
            case R.id.grey:                colorChanger(R.color.theme_grey_colorPrimaryDark, R.color.theme_grey_colorPrimaryLight,
                    R.color.theme_grey_colorPrimary, R.color.theme_grey_colortext,
                    R.color.theme_grey_colorAccent, R.color.theme_grey_colorPrimaryText,
                    R.color.theme_grey_colorSecondaryText, R.color.theme_grey_colorDivider);
                break;
            case R.id.purple:                colorChanger(R.color.theme_purple_colorPrimaryDark, R.color.theme_purple_colorPrimaryLight,
                    R.color.theme_purple_colorPrimary, R.color.theme_purple_colortext,
                    R.color.theme_purple_colorAccent, R.color.theme_purple_colorPrimaryText,
                    R.color.theme_purple_colorSecondaryText, R.color.theme_purple_colorDivider);
                break;
            case R.id.deeppurple:                colorChanger(R.color.theme_deepurple_colorPrimaryDark, R.color.theme_deepurple_colorPrimaryLight,
                    R.color.theme_deepurple_colorPrimary, R.color.theme_deepurple_colortext,
                    R.color.theme_deepurple_colorAccent, R.color.theme_deepurple_colorPrimaryText,
                    R.color.theme_deepurple_colorSecondaryText, R.color.theme_deepurple_colorDivider);
                break;
            case R.id.orange:                colorChanger(R.color.theme_orange_colorPrimaryDark, R.color.theme_orange_colorPrimaryLight,
                    R.color.theme_orange_colorPrimary, R.color.theme_orange_colortext,
                    R.color.theme_orange_colorAccent, R.color.theme_orange_colorPrimaryText,
                    R.color.theme_orange_colorSecondaryText, R.color.theme_orange_colorDivider);
                break;
            case R.id.green:                colorChanger(R.color.theme_green_colorPrimaryDark, R.color.theme_green_colorPrimaryLight,
                    R.color.theme_green_colorPrimary, R.color.theme_green_colortext,
                    R.color.theme_green_colorAccent, R.color.theme_green_colorPrimaryText,
                    R.color.theme_green_colorSecondaryText, R.color.theme_green_colorDivider);
                break;
            case R.id.teal:                colorChanger(R.color.theme_teal_colorPrimaryDark, R.color.theme_teal_colorPrimaryLight,
                    R.color.theme_teal_colorPrimary, R.color.theme_teal_colortext,
                    R.color.theme_teal_colorAccent, R.color.theme_teal_colorPrimaryText,
                    R.color.theme_teal_colorSecondaryText, R.color.theme_teal_colorDivider);
                break;
            case R.id.yellow:                colorChanger(R.color.theme_yellow_colorPrimaryDark, R.color.theme_yellow_colorPrimaryLight,
                    R.color.theme_yellow_colorPrimary, R.color.theme_yellow_colortext,
                    R.color.theme_yellow_colorAccent, R.color.theme_yellow_colorPrimaryText,
                    R.color.theme_yellow_colorSecondaryText, R.color.theme_yellow_colorDivider);
                break;
        }
    }


    public void colorChanger(int dark,int light,int primary,int text,int accent,int primarytext,int secondarytext,int divider){
        toolbar.setBackgroundColor(activity.getResources().getColor(dark));
        TextView title = (TextView) toolbar.findViewById(R.id.toolbar_title);
        title.setTextColor(activity.getResources().getColor(text));
        TextView url = (TextView) toolbar.findViewById(R.id.toolbar_url);
        url.setTextColor(activity.getResources().getColor(divider));

        //bottom navigation
        bottomNavigationView.setBackground(new ColorDrawable(activity.getResources().getColor(primary)));
        bottomNavigationView.setItemIconTintList(ColorStateList.valueOf(activity.getResources().getColor(text)));
        bottomNavigationView.setItemTextColor(ColorStateList.valueOf(activity.getResources().getColor(text)));
        //side navigation
        activity.findViewById(R.id.nav_quickmodes_back).setBackground(new ColorDrawable(activity.getResources().getColor(dark)));
        activity.findViewById(R.id.nav_quickmodes_card).setBackground(new ColorDrawable(activity.getResources().getColor(primary)));
        activity.findViewById(R.id.session_history).setBackground(new ColorDrawable(activity.getResources().getColor(primary)));
        activity.findViewById(R.id.nav_hishtory).setBackground(new ColorDrawable(activity.getResources().getColor(dark)));
        //options dialog
        optionDialog.findViewById(R.id.options_head_container).setBackgroundColor(activity.getResources().getColor(primary));
        optionDialog.findViewById(R.id.options_dialog_back).setBackgroundColor(activity.getResources().getColor(light));
        //statusbar
        activity.getWindow().setStatusBarColor(activity.getResources().getColor(dark));




    }
    public void defaultTheme(){
        colorChanger(R.color.theme_default_colorPrimaryDark, R.color.theme_default_colorPrimaryLight,
                R.color.theme_default_colorPrimary, R.color.theme_default_colortext,
                R.color.theme_default_colorAccent, R.color.theme_default_colorPrimaryText,
                R.color.theme_default_colorSecondaryText, R.color.theme_default_colorDivider);

    }

}
