package com.dapeng.notes.Study;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Dapeng on 2018/6/17.
 */

public class StudyDB extends SQLiteOpenHelper {

    public static final String TABLE_NAME = "Study";
    public static final String TITLE = "title";
    public static final String ID = "_id";
    public static final String CONTENT= "content";
    public static final String TIME= "time";

    public StudyDB(Context context) {
        super(context, "Study.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE "+TABLE_NAME+"("
                        +ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"
                        +TITLE+" TEXT,"
                        +CONTENT+" TEXT,"
                        +TIME+" TEXT NOT NULL)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
