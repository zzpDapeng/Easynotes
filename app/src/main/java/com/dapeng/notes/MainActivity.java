package com.dapeng.notes;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.dapeng.notes.Money.Money;
import com.dapeng.notes.Study.Study;
import com.dapeng.notes.Todo.Todo;
import com.dapeng.notes.Words.Words;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*启动学习笔记、单词本、待办事项、记账*/
        findViewById(R.id.StudyButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,Study.class));
                //startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://note.youdao.com/web/")));//跳转网站
            }
        });
        findViewById(R.id.WordsButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,Words.class));
            }
        });
        findViewById(R.id.ListButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,Todo.class));
            }
        });
        findViewById(R.id.MoneyButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,Money.class));
            }
        });
        //跳转反馈页面
        findViewById(R.id.more).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://wj.qq.com/s/2094627/b357")));
            }
        });
        //备份待开发
//        findViewById(R.id.Beifen).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });
        //恢复待开发
//        findViewById(R.id.Huifu).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });
    }
}
