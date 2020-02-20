package com.dapeng.notes.Study;

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
import com.dapeng.notes.Words.WordsInfo;

import java.util.ArrayList;
import java.util.List;

public class Study extends AppCompatActivity {
    public String id,title,content,time;
    public static List<StudyInfo> lists = new ArrayList<StudyInfo>();
    public ListView lv;
    public StudyAdapter adapter;
    public static StudyDB studyDB;
    public static SQLiteDatabase dbWriter,dbReader;
    public static int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study);

        studyDB = new StudyDB(this);
        dbWriter = studyDB.getWritableDatabase();
        dbReader = studyDB.getReadableDatabase();
        initLists();
        lv = findViewById(R.id.study_lv);
        adapter = new StudyAdapter(lists,this);
        lv.setAdapter(adapter);
        findViewById(R.id.addStudy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Study.this,AddStudy.class));
            }
        });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                position =i;
                startActivity(new Intent(Study.this, EditStudy.class));

            }
        });
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                position = i;
                AlertDialog.Builder builder = new AlertDialog.Builder(Study.this);
                builder.setTitle("提示信息");
                builder.setMessage("确认删除");
                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dbReader = studyDB.getReadableDatabase();
                        dbReader.delete(StudyDB.TABLE_NAME,"_id=?",new String[]{lists.get(position).getId()});
                        initLists();
                        adapter.notifyDataSetChanged();
                        Toast.makeText(Study.this,"删除成功",Toast.LENGTH_LONG).show();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(Study.this,"取消删除",Toast.LENGTH_LONG).show();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                return true;
            }
        });
    }

    @Override
    protected void onRestart() {
        initLists();
        adapter.notifyDataSetChanged();
        super.onRestart();
    }

    private void initLists() {
        Cursor cur = dbReader.query(
                "Study",
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
        while (cur.moveToPrevious()) {
            this.id = cur.getString(0);
            this.title = cur.getString(1);
            this.content = cur.getString(2);
            StudyInfo studyInfo = new StudyInfo(id, title, content);
            lists.add(studyInfo);
        }
    }
}
