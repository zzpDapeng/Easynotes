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

public class EditStudy extends AppCompatActivity {

    private EditText titleET,contentET;
    private String title,content;
    private Button save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_study);
        StudyInfo thisStudy = Study.lists.get(Study.position);
        titleET = findViewById(R.id.title_inet);
        contentET = findViewById(R.id.content_inet);
        titleET.setText(thisStudy.getTitle());
        contentET.setText(thisStudy.getContent());
        save = findViewById(R.id.saveStudy_bt);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                title = titleET.getText().toString();
                content = contentET.getText().toString();
                ContentValues cv = new ContentValues();
                cv.put(StudyDB.TITLE,title);
                cv.put(StudyDB.CONTENT,content);
                cv.put(StudyDB.TIME,getTime());
                long rowID = Study.dbWriter.update(StudyDB.TABLE_NAME,cv,"_id=?",new String[]{Study.lists.get(Study.position).getId()});
                if(rowID!=-1){
                    Toast.makeText(EditStudy.this,"记录成功",Toast.LENGTH_LONG).show();
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
