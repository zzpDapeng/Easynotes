package com.dapeng.notes.Words;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by 10341 on 2018/3/20.
 */

public class WordsDB extends SQLiteOpenHelper{

    public static final String TABLE_NAME = "Words";
    public static final String WORDS = "words";
    public static final String TRANSLATION= "translation";
    public static final String PHRASE= "phrase";
    public static final String ID= "_id";
    public static final String OTHERS = "others";
    public static final String TIME= "time";

    public WordsDB(Context context) {
        super(context,"Words.db",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE "+ TABLE_NAME+"("
                + ID +" INTEGER PRIMARY KEY AUTOINCREMENT,"
                + WORDS +" TEXT NOT NULL,"
                + TRANSLATION +" TEXT NOT NULL,"
                + PHRASE +" TEXT,"
                + OTHERS +" TEXT,"
                + TIME + " TEXT NOT NULL)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
