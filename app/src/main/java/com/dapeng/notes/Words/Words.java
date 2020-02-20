package com.dapeng.notes.Words;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.dapeng.notes.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Words extends AppCompatActivity {
    public String id,words,translation,phrase,others;
    public static List<WordsInfo> lists = new ArrayList<WordsInfo>();
    public ListView lv;
    public WordsAdapter adapter;
    public static WordsDB wordsDB;
    public static SQLiteDatabase dbWriter,dbReader;
    public static int position;
    public View edit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_words);

        wordsDB = new WordsDB(this);   //数据库实例化
        dbWriter = wordsDB.getWritableDatabase();//数据库读取权限获取

        initlist();
        lv = findViewById(R.id.words_listView);
        adapter = new WordsAdapter(lists,this);
        lv.setAdapter(adapter);
        edit = Words.this.getLayoutInflater().inflate(R.layout.activity_words_edit,null);
        //跳转到添加单词页面WordsAdd
        findViewById(R.id.AddWords).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Words.this,WordsAdd.class));
            }
        });
        findViewById(R.id.toTranslate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Words.this,WordsTranslate.class));
            }
        });
        //实现点击Listview显示详情功能
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                position =i;
                startActivity(new Intent(Words.this,WordsEdit.class));
            }
        });
        //实现ListView的长按删除功能
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                position = i;
                //Toast.makeText(getApplicationContext(),"长按删除待开发",Toast.LENGTH_LONG).show();
                AlertDialog.Builder builder = new AlertDialog.Builder(Words.this);
                builder.setTitle("提示信息");
                builder.setMessage("确认删除");
                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dbReader = wordsDB.getReadableDatabase();
                        dbReader.delete(WordsDB.TABLE_NAME,"_id=?",new String[]{lists.get(position).getId()});
                        initlist();
                        adapter.notifyDataSetChanged();
                        Toast.makeText(Words.this,"删除成功",Toast.LENGTH_LONG).show();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(Words.this,"取消删除",Toast.LENGTH_LONG).show();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                return true;
            }
        });
    }

    public void initlist(){
        try{
            dbReader = wordsDB.getReadableDatabase();
            Cursor cur = dbReader.query(
                    "Words",
                    null,
                    null,
                    null,
                    null,
                    null,
                    null
            );
            lists.clear();
            cur.moveToLast();
            cur.moveToNext();//到达最后一条数据的后面一条数据，让后向前存入Lists,使得最近记录的最先读出
            while (cur.moveToPrevious()){
                this.id =cur.getString(0);
                this.words = cur.getString(1);
                this.translation = cur.getString(2);
                this.phrase = cur.getString(3);
                this.others = cur.getString(4);
                WordsInfo WordsInfo = new WordsInfo(id,words,translation,phrase,others);
                lists.add(WordsInfo);
            }
        }catch (Exception E){
            System.err.println("Exception caught:"+E.getMessage());
        }
    }

    //获取时间
    public static String getTime(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());
        String str = format.format(curDate);
        return str;
    }
    protected void onRestart() {
        initlist();
        adapter.notifyDataSetChanged();
        super.onRestart();
    }
}
