package com.example.deepakyadav.incog20.DataModel;

import android.database.sqlite.*;
import android.content.*;

public class IncogDB extends SQLiteOpenHelper {

    public static final String DB_NAME = "incogdb";
    public static final int DB_VER = 1;

    IncogDB(Context context){
        super(context,DB_NAME,null,DB_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        updateMyDatabase(db,0,DB_VER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int o, int n) {
        updateMyDatabase(db,o,n);
    }

    private static void insertData(SQLiteDatabase db, AppData data, String table) {
        ContentValues values = new ContentValues();
        values.put("URL", data.getURL());
        values.put("TITLE",data.getTitle());
//        values.put("FAVICON",data.getFavicon());
//        values.put("USER",data.getUser());
        db.insert("INCOG_TABLE", null, values);
    }

    private void updateMyDatabase(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 1) {
            db.execSQL("CREATE TABLE HISTORY (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "TIME_STAMP TEXT DEFAULT CURRENT_TIMESTAMP NOT NULL, " +
                    "URL TEXT," +
                    "TITLE TEXT," +
                    "FAVICON BLOB," +
                    "USER TEXT);");

            db.execSQL("CREATE TABLE BOOKMARKS (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "URL TEXT," +
                    "TITLE TEXT," +
                    "FAVICON BLOB," +
                    "USER TEXT);");

            db.execSQL("CREATE TABLE TABS (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "URL TEXT," +
                    "TITLE TEXT," +
                    "FAVICON BLOB," +
                    "SAVED_STATE BLOB," +
                    "USER TEXT);");
        }
        else if (oldVersion < 2) {

        }
    }
}