package com.dapeng.notes.Money;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.dapeng.notes.R;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class Money extends AppCompatActivity {

    public double out,in,outAll,inAll,realOut;
    public String id,month,time;
    public TextView outAllText,inAllText,realOutText;
    public EditText editMoney;
    public Button inButton,outButton;
    public static MoneyDB moneyDB;
    public static SQLiteDatabase dbWriter,dbReader;
    public long max;
    public ListView lv;
    public MoneyAdapter adapter;
    public List<MoneyInfo> lists = new ArrayList<MoneyInfo>();
    public FrameLayout img,word;
    public ImageView inImg,outImg;
    public TextView inWord,outWord,showtime,showMoney;
    public static int position;
    public LinearLayout editlayout;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money);

        editMoney = findViewById(R.id.editMoeny);
        moneyDB = new MoneyDB(this);
        dbWriter = moneyDB.getWritableDatabase();
        dbReader= moneyDB.getReadableDatabase();

        //System.out.println("inAll:"+inAll+"\noutAll:"+outAll+"\nrealOut:"+realOut);

        outAllText = findViewById(R.id.outAllText);
        inAllText = findViewById(R.id.inAllText);
        realOutText = findViewById(R.id.realOutText);

        getLatest();
        initLists();
        lv = findViewById(R.id.list_view_money);
        adapter = new MoneyAdapter(lists,this);
        lv.setAdapter(adapter);

        //点击收入按钮
        findViewById(R.id.inButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String res = editMoney.getText().toString();
                    in = Double.parseDouble(res);
                    addInDB();
                    editMoney.getText().clear();
                    lists.clear();
                    getLatest();
                    initLists();
                    adapter.notifyDataSetChanged();
                }catch (Exception e){
                    System.err.println("Exception caught:"+e.getMessage());
                }
            }
        });
        //点击支出按钮
        findViewById(R.id.outButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String res = editMoney.getText().toString();
                    out = Double.parseDouble(res);
                    addOutDB();
                    editMoney.getText().clear();
                    lists.clear();
                    getLatest();
                    initLists();
                    adapter.notifyDataSetChanged();
                }catch (Exception e){
                    System.err.println("Exception caught:"+e.getMessage());
                }
            }
        });
        //长按删除功能
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                position = i;
                AlertDialog.Builder builder = new AlertDialog.Builder(Money.this);
                builder.setTitle("提示信息");
                builder.setMessage("确认删除");
                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dbReader.delete(MoneyDB.TABLE_NAME,"_id=?",new String[]{lists.get(position).getId()});
                        getLatest();
                        initLists();
                        adapter.notifyDataSetChanged();
                        Toast.makeText(Money.this,"删除成功",Toast.LENGTH_LONG).show();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(Money.this,"取消删除",Toast.LENGTH_LONG).show();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                return true;
            }
        });
        //点击修改
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                editlayout = (LinearLayout) getLayoutInflater().inflate(R.layout.edit_money,null);
                final EditText moneyInEdit = editlayout.findViewById(R.id.money_in_edit);
                getLatest();
                double money = 0;
                final String status;
                final int position = i;
                if(lists.get(i).getIn() == 0){ money = lists.get(i).getOut();status="out";}
                else {money = lists.get(i).getIn();status="in";}
                String mon = String.valueOf(money);
                moneyInEdit.setHint(mon);
                AlertDialog.Builder builder = new AlertDialog.Builder(Money.this);
                builder.setTitle("修改");
                builder.setView(editlayout);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(status.equals("in")){
                            Double temp = Double.parseDouble(moneyInEdit.getText().toString());
                            Money.this.in = temp;
                            Money.this.out = 0;
                        }else{
                            Double temp = Double.parseDouble(moneyInEdit.getText().toString());
                            Money.this.out = temp;
                            Money.this.in = 0;
                        }
                        ContentValues cv = new ContentValues();
                        cv.put(MoneyDB.OUT,Money.this.out);
                        cv.put(MoneyDB.IN,Money.this.in);
                        cv.put(MoneyDB.TIME,getTime());
                        cv.put(MoneyDB.MONTH,getMonth());
                        dbReader= moneyDB.getReadableDatabase();
                        dbReader.update(MoneyDB.TABLE_NAME,cv,"_id=?",new String[]{lists.get(position).getId()});
                        Toast.makeText(Money.this,"修改成功",Toast.LENGTH_LONG).show();
                        getLatest();
                        initLists();
                        adapter.notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("取消",null);
                builder.create();
                builder.show();
            }
        });
    }

    //获取最新的La
    public void getLatest(){
        //设置显示文字内容
        try{
            Money.dbReader = Money.moneyDB.getReadableDatabase();
            Cursor cur = Money.dbReader.query(
                    "Money",
                    null,
                    null,
                    null,
                    null,
                    null,
                    null
            );
            inAll=0;
            outAll=0;
            while(cur.moveToNext()){
                inAll += cur.getDouble(1);
                outAll+= cur.getDouble(2);
            }
            realOut=inAll-outAll;
            cur.moveToLast();
            this.id = cur.getString(0);
            this.in = cur.getDouble(1);
            this.out = cur.getDouble(2);
            this.month = cur.getString(3);
            this.time= cur.getString(4);
        }catch (Exception E){
            System.err.println("Exception caught:"+E.getMessage());
        }
        outAllText.setText(String.format("支出：%.2f元",outAll));
        inAllText.setText(String.format("收入：%.2f元",inAll));
        realOutText.setText(String.format("%.2f",realOut));
    }
    //更新Lists内容
    public void initLists(){
        //设置显示文字内容
        try{
            Money.dbReader = Money.moneyDB.getReadableDatabase();
            Cursor cur = Money.dbReader.query(
                    "Money",
                    null,
                    null,
                    null,
                    null,
                    null,
                    null
            );
            cur.moveToLast();
            cur.moveToNext();//到达最后一条数据的后面一条数据，从后向前存入Lists,使得最近记录的最先读出
            lists.clear();
            while (cur.moveToPrevious()){
                this.id = cur.getString(0);
                this.in = cur.getDouble(1);
                this.out = cur.getDouble(2);
//                this.inAll = cur.getDouble(3);
//                this.outAll = cur.getDouble(4);
//                this.realOut = cur.getDouble(5);
                this.month = cur.getString(3);
                this.time= cur.getString(4);
                MoneyInfo moneyInfo = new MoneyInfo(id,in,out,month,time);
                lists.add(moneyInfo);
            }
        }catch (Exception E){
            System.err.println("Exception caught:"+E.getMessage());
        }
    }

    public void addInDB(){
        ContentValues cv = new ContentValues();
        cv.put(MoneyDB.OUT,0);
        cv.put(MoneyDB.IN,in);
//        cv.put(MoneyDB.IN_ALL,inAll);
//        cv.put(MoneyDB.OUT_ALL,outAll);
//        cv.put(MoneyDB.REAL_OUT,realOut);
        cv.put(MoneyDB.TIME,getTime());
        cv.put(MoneyDB.MONTH,getMonth());
        long rowID = dbWriter.insert(MoneyDB.TABLE_NAME,null,cv);
        if(rowID!=-1){
            Toast.makeText(this,"收入："+in+"元 记账成功",Toast.LENGTH_LONG).show();
        }
    }
    public void addOutDB(){
        ContentValues cv = new ContentValues();
        cv.put(MoneyDB.OUT,out);
        cv.put(MoneyDB.IN,0);
//        cv.put(MoneyDB.IN_ALL,inAll);
//        cv.put(MoneyDB.OUT_ALL,outAll);
//        cv.put(MoneyDB.REAL_OUT,realOut);
        cv.put(MoneyDB.TIME,getTime());
        cv.put(MoneyDB.MONTH,getMonth());
        long rowID=dbWriter.insert(MoneyDB.TABLE_NAME,null,cv);
        if(rowID!=-1){
            Toast.makeText(this,"支出："+out+"元 记账成功",Toast.LENGTH_LONG).show();
        }
    }
    public String getTime(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());
        String str = format.format(curDate);
        return str;
    }
    public String getMonth(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月");
        Date curDate = new Date(System.currentTimeMillis());
        String str = format.format(curDate);
        return str;
    }
}
