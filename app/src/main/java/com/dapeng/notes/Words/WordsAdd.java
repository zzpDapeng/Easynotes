package com.dapeng.notes.Words;

import android.content.ContentValues;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.dapeng.notes.R;

import java.sql.Date;
import java.text.SimpleDateFormat;

public class WordsAdd extends AppCompatActivity {

    public String words,translation,phrase,others;
    public EditText WORDS,TRANSLATION,PHRASE,OTHERS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_words_add);
//        wordsDB = new WordsDB(this);   //数据库实例化
//        dbWriter = wordsDB.getWritableDatabase();//数据库读取权限获取
        //EditText键值匹配
        WORDS = findViewById(R.id.editWords);
        TRANSLATION = findViewById(R.id.editTranslation);
        PHRASE = findViewById(R.id.editPhrase);
        OTHERS = findViewById(R.id.editOthers);
        //监听点击保存按钮事件
        findViewById(R.id.saveWords).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //赋值
                words = WORDS.getText().toString();
                if(words.equals("")){
                    Toast.makeText(getApplicationContext(),"输入为空",Toast.LENGTH_LONG).show();
                }else{
                    translation = TRANSLATION.getText().toString();
                    phrase = PHRASE.getText().toString();
                    others = OTHERS.getText().toString();
                    addWordsToDB();
                }

            }
        });
    }
    public void addWordsToDB(){
        ContentValues cv = new ContentValues();
        cv.put(WordsDB.WORDS,words);
        cv.put(WordsDB.TRANSLATION,translation);
        cv.put(WordsDB.PHRASE,phrase);
        cv.put(WordsDB.OTHERS,others);
        cv.put(WordsDB.TIME,getTime());
        long rowID = Words.dbWriter.insert(WordsDB.TABLE_NAME,null,cv);
        if(rowID!=-1){
            Toast.makeText(this,"单词"+words+"记录成功",Toast.LENGTH_LONG).show();
        }
    }
    //获取时间
    public String getTime(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());
        String str = format.format(curDate);
        return str;
    }
}
