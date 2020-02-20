package com.dapeng.notes.Todo;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.dapeng.notes.R;

import java.security.PublicKey;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class Done extends AppCompatActivity {
    public static String id,todo,finishtime,lable,time;
    public EditText TodoET,TimeEdit;
    public ToDoDB todoDB;
    public SQLiteDatabase dbWriter,dbReader;
    public ListView lv;
    public TodoAdapter adapter;
    public List<TodoInfo> lists = new ArrayList<TodoInfo>();
    public LinearLayout editlayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_done);

        todoDB = new ToDoDB(this);
        dbWriter = todoDB.getWritableDatabase();
        dbReader = todoDB.getWritableDatabase();
        lv =findViewById(R.id.done_lv);
        adapter = new TodoAdapter(lists,this);
        lv.setAdapter(adapter);
        initLists();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final int position = i;
                AlertDialog.Builder builder = new AlertDialog.Builder(Done.this);
                builder.setTitle("提示");
                builder.setMessage("修改状态为未完成？");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ContentValues cv = new ContentValues();
                        cv.put(ToDoDB.THING,lists.get(position).getThing());
                        cv.put(ToDoDB.FINISHTIME,lists.get(position).getFinishTime());
                        cv.put(ToDoDB.LABLE,0);
                        cv.put(ToDoDB.TIME,getTime());
                        dbReader= todoDB.getReadableDatabase();
                        dbReader.update(ToDoDB.TABLE_NAME,cv,"_id=?",new String[]{lists.get(position).getId()});
                        Toast.makeText(Done.this,"修改成功",Toast.LENGTH_LONG).show();
                        initLists();
                        adapter.notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("取消",null);
                builder.create();
                builder.show();
            }
        });
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                final int position = i;
                AlertDialog.Builder builder = new AlertDialog.Builder(Done.this);
                builder.setTitle("提示信息");
                builder.setMessage("确认删除？");
                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dbReader.delete(ToDoDB.TABLE_NAME,"_id=?",new String[]{lists.get(position).getId()});
                        initLists();
                        adapter.notifyDataSetChanged();
                        Toast.makeText(Done.this,"删除成功",Toast.LENGTH_LONG).show();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(Done.this,"取消删除",Toast.LENGTH_LONG).show();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                return true;
            }
        });
    }

    private void initLists() {
        String[] a = {"0"};
        Cursor cur = dbReader.query(
                "ToDo",
                null,
                "lable = ?",
                new String[]{"1"},
                null,
                null,
                null
        );
        cur.moveToLast();
        cur.moveToNext();//到达最后一条数据的后面一条数据，让后向前存入Lists,使得最近记录的最先读出
        lists.clear();
        while (cur.moveToPrevious()){
            this.id = cur.getString(0);
            this.todo = cur.getString(1);
            this.finishtime = cur.getString(2);
            this.lable = cur.getString(3);
            this.time = cur.getString(4);
            TodoInfo TodoInfo = new TodoInfo(id,todo,finishtime,lable,time);
            lists.add(TodoInfo);
        }
    }
    private void addTodoToDB() {
        ContentValues cv = new ContentValues();
        cv.put(ToDoDB.THING,todo);
        cv.put(ToDoDB.FINISHTIME,finishtime);
        cv.put(ToDoDB.LABLE,"0");
        cv.put(ToDoDB.TIME,getTime());
        long rowID = dbWriter.insert(ToDoDB.TABLE_NAME,null,cv);
        if(rowID != -1){
            Toast.makeText(this,"待办事项记录成功",Toast.LENGTH_LONG).show();
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
