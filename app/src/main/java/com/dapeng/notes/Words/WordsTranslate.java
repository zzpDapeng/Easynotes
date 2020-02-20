package com.dapeng.notes.Words;


import com.dapeng.notes.BaiduTranslate.TransApi;
import com.dapeng.notes.R;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class WordsTranslate extends AppCompatActivity {

    public String source,result;
    public EditText sourceET;
    public TextView resultET;
    public Spinner spinner;
    public ArrayAdapter<String> adapter;
    public ArrayAdapter<CharSequence> AdapterXML;
    public List<String> lists;
    public String words,translation,reslanguage,desLanguage;
    public JSONObject root;
    public JSONArray  in;
    public  String str,temp;
    public  String query;
    public boolean isConnected;
    public Context context;
    private static final String APP_ID = "20180320000138223";
    private static final String SECURITY_KEY = "MJ_CsIYl0QGeIOT3Se9x";
    private static ConnectivityManager manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_words_translate);
       // context = this;
        sourceET = findViewById(R.id.translateSource);
        resultET = findViewById(R.id.translateResout);
        manager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo.State GprsState = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
        NetworkInfo.State WifiState = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
        if(isNetworkAvailable(context)==false){
            Toast.makeText(this,"请连接网络",Toast.LENGTH_LONG).show();
            Timer timer = new Timer();
            timer.schedule(new NoNetwork(),2000);
        }

        //isConnected = isNetworkAvailable(context);
        spinner = findViewById(R.id.spinner);
        //initByList();
        initByXML();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                temp = AdapterXML.getItem(i).toString();
                match();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        //监听翻译按钮
        findViewById(R.id.translate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isNetworkAvailable(context)==false){
                    Toast.makeText(WordsTranslate.this,"请连接网络",Toast.LENGTH_LONG).show();
                    Timer timer = new Timer();
                    timer.schedule(new NoNetwork(),2000);
                }else {
                    query = sourceET.getText().toString();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                TransApi api = new TransApi(APP_ID, SECURITY_KEY);
                                root = api.getTransResult(query, "auto", desLanguage);
                                in = root.getJSONArray("trans_result");
                                JSONObject lan = in.getJSONObject(0);
                                str = lan.getString("dst");
//                                System.out.println("哈哈哈哈哈哈哈啊哈哈哈哈哈哈哈哈啊哈哈 1 "+str);
                                System.out.println(api.getTransResult(query, "auto", desLanguage));
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                    try {
                        Thread.sleep(460);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
//                    System.out.println("哈哈哈哈哈哈哈啊哈哈哈哈哈哈哈哈啊哈哈 2 "+str);
                    resultET.setText(str);
                }
            }
        });
        //监听添加翻译结果至单词本按钮
        findViewById(R.id.AddWordsFromTranslate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if用于判断保证中文在ListView中始终在下面
                if(desLanguage=="zh"){
                    words = sourceET.getText().toString();
                    translation = resultET.getText().toString();
                }else{
                    translation = sourceET.getText().toString();
                    words = resultET.getText().toString();
                }
                if(words.equals("")){
                    Toast.makeText(getApplicationContext(),"输入为空",Toast.LENGTH_LONG).show();
                }else{
                    addWordsToDB();
                }
            }
        });
    }
    //Spinner通过XML文件初始化
    private void initByXML() {
        AdapterXML = ArrayAdapter.createFromResource(this,R.array.languagelist,android.R.layout.simple_list_item_1);
        spinner.setAdapter(AdapterXML);
    }
    //添加单词至数据库
    public void addWordsToDB(){
        ContentValues cv = new ContentValues();
        cv.put(WordsDB.WORDS,words);
        cv.put(WordsDB.TRANSLATION,translation);
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
    //检查网络状态
    public static boolean isNetworkAvailable(Context context) {
        NetworkInfo.State GprsState = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
        NetworkInfo.State WifiState = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
        if(!GprsState.equals(NetworkInfo.State.CONNECTED)&&!WifiState.equals(NetworkInfo.State.CONNECTED)){
            return false;
        }else{
            return true;
        }
    }
    class  NoNetwork extends TimerTask{
        @Override
        public void run() {
            Intent i=new Intent();
            i.setAction(Settings.ACTION_WIFI_SETTINGS);
            startActivity(i);
        }
    }
    //匹配语言
    private void match() {
        switch (temp){
            case "中文":
                desLanguage="zh";
                break;
            case "英语":
                desLanguage="en";
                break;
            case "粤语":
                desLanguage="yue";
                break;
            case "文言文":
                desLanguage="wyw";
                break;
            case "日语":
                desLanguage="jp";
                break;
            case "韩语":
                desLanguage="kor";
                break;
            case "法语":
                desLanguage="fra";
                break;
            case "西班牙语":
                desLanguage="spa";
                break;
            case "泰语":
                desLanguage="th";
                break;
            case "阿拉伯语":
                desLanguage="ara";
                break;
            case "俄语":
                desLanguage="ru";
                break;
            case "葡萄牙语":
                desLanguage="pt";
                break;
            case "德语":
                desLanguage="de";
                break;
            case "意大利语":
                desLanguage="it";
                break;
            case "希腊语":
                desLanguage="el";
                break;
            case "荷兰语":
                desLanguage="nl";
                break;
            case "波兰语":
                desLanguage="pl";
                break;
            case "保加利亚语":
                desLanguage="bul";
                break;
            case "爱沙尼亚语":
                desLanguage="est";
                break;
            case "丹麦语":
                desLanguage="dan";
                break;
            case "芬兰语":
                desLanguage="fin";
                break;
            case "捷克语":
                desLanguage="cs";
                break;
            case "罗马尼亚语":
                desLanguage="rom";
                break;
            case "斯洛文尼亚语":
                desLanguage="slo";
                break;
            case "瑞典语":
                desLanguage="swe";
                break;
            case "匈牙利语":
                desLanguage="hu";
                break;
            case "繁体中文":
                desLanguage="cht";
                break;
            case "越南语":
                desLanguage="vie";
                break;
        }
    }
    //Spinner初始化方法一
}
