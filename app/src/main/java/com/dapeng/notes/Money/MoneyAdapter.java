package com.dapeng.notes.Money;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dapeng.notes.R;

import java.util.List;

/**
 * Created by 10341 on 2018/3/18.
 */

public class MoneyAdapter extends BaseAdapter{

    private List<MoneyInfo> lists;
    private Context context;
    private LinearLayout layout;
    public ImageView inImg,outImg;
    public TextView inWord,outWord,showtime,showMoney;

    public MoneyAdapter(List<MoneyInfo> lists, Context context) {
        this.lists = lists;
        this.context = context;
    }
    @Override
    public int getCount() {
        return lists.size();
    }
    @Override
    public Object getItem(int i) {
        return lists.get(i);
    }
    @Override
    public long getItemId(int i) {
        return i;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(context);
        layout = (LinearLayout) inflater.inflate(R.layout.cell_money,null);
        inImg = layout.findViewById(R.id.list_img_in);
        outImg = layout.findViewById(R.id.list_img_out);
        inWord = layout.findViewById(R.id.list_money_in);
        outWord = layout.findViewById(R.id.list_money_out);
        showMoney = layout.findViewById(R.id.list_money);
        showtime = layout.findViewById(R.id.list_money_time);
        try{
            //int size = lists.size();
            //System.out.println("啊哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈，size="+size);
            if(lists.get(i).getOut()==0){
                showMoney.setText(String.format("%.2f",lists.get(i).getIn()));
                showtime.setText(lists.get(i).getTime());
                showInImg();
                showInWord();
            }else if(lists.get(i).getIn()==0){
                showMoney.setText(String.format("%.2f",lists.get(i).getOut()));
                showtime.setText(lists.get(i).getTime());
                showOutImg();
                showOutWord();
            }
        }catch (Exception e){
            System.err.println("Exception caught:"+e.getMessage());
        }
        return layout;
    }
    public void showInImg(){
        inImg.setVisibility(View.VISIBLE);
        outImg.setVisibility(View.INVISIBLE);
    }
    public void showOutImg(){
        inImg.setVisibility(View.INVISIBLE);
        outImg.setVisibility(View.VISIBLE);
    }
    public void showInWord(){
        inWord.setVisibility(View.VISIBLE);
        outWord.setVisibility(View.INVISIBLE);
    }
    public void showOutWord(){
        inWord.setVisibility(View.INVISIBLE);
        outWord.setVisibility(View.VISIBLE);
    }
}
