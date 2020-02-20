package com.dapeng.notes.Todo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Dapeng on 2018/6/4.
 */

public class ToDoDB extends SQLiteOpenHelper{
    public static final String TABLE_NAME = "todo";
    public static final String THING = "thing";
    public static final String FINISHTIME = "finishtime";
    public static final String LABLE = "lable";
    public static final String ID= "_id";
    public static final String TIME= "time";

    public ToDoDB(Context context) {
        super(context, "ToDo.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE "+  TABLE_NAME +"("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + THING + " TEXT NOT NULL,"
                + FINISHTIME + " TEXT ,"
                + LABLE + " TEXT NOT NULL,"
                + TIME + " TEXT NOT NULL)"
        );
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
