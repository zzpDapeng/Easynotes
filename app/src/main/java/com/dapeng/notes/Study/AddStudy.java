package com.dapeng.notes.Study;

import android.content.ContentValues;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dapeng.notes.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddStudy extends AppCompatActivity {
    private EditText titleET,contentET;
    private String title,content;
    private Button save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_study);

        titleET = findViewById(R.id.title_et);
        contentET = findViewById(R.id.content_et);
        save = findViewById(R.id.addStudy_bt);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                title = titleET.getText().toString();
                content = contentET.getText().toString();
                if(title.equals("")&&content.equals("")){
                    Toast.makeText(AddStudy.this,"输入为空",Toast.LENGTH_LONG).show();
                }else{
                    ContentValues cv = new ContentValues();
                    cv.put(StudyDB.TITLE,title);
                    cv.put(StudyDB.CONTENT,content);
                    cv.put(StudyDB.TIME,getTime());
                    long rowID = Study.dbWriter.insert(StudyDB.TABLE_NAME,null,cv);
                    if(rowID!=-1){
                        Toast.makeText(AddStudy.this,"记录成功",Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }
    public String getTime(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());
        String str = format.format(curDate);
        return str;
    }
}
