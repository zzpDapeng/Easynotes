package com.dapeng.notes.Money;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.security.PublicKey;
import java.sql.SQLClientInfoException;

/**
 * Created by 10341 on 2018/3/17.
 */

public class MoneyDB extends SQLiteOpenHelper{

    public static final String TABLE_NAME = "Money";
    public static final String OUT = "outMoney";
//    public static final String OUT_ALL= "outAll";
//    public static final String IN_ALL= "inAll";
//    public static final String REAL_OUT= "realOut";
    public static final String ID= "_id";
    public static final String TIME= "time";
    public static final String MONTH= "month";
    public static final String IN= "inMoney";

    public MoneyDB(Context context) {
        super(context, "Money.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE "+TABLE_NAME+"("
                +ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"
                +IN+" DOUBLE,"
                +OUT+" DOUBLE,"
//                +IN_ALL+" DOUBLE,"
//                +OUT_ALL+" DOUBLE,"
//                +REAL_OUT+" DOUBLE,"
                +MONTH+" TEXT NOT NULL,"
                +TIME+" TEXT NOT NULL)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
