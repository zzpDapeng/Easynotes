package com.dapeng.notes.Words;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dapeng.notes.R;

import java.util.List;

/**
 * Created by Zhipeng Zhang on 2018/3/20.
 */

public class WordsAdapter extends BaseAdapter{
    private static List<WordsInfo> lists;
    private Context context;
    private LinearLayout layout;
    public TextView words,translation;

    public WordsAdapter(List<WordsInfo> lists, Context context) {
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
        layout = (LinearLayout) inflater.inflate(R.layout.cell_words,null);
        words = layout.findViewById(R.id.Words_ListView_tv);
        words.setText(lists.get(i).getWords());
        translation = layout.findViewById(R.id.Translatin_ListView_tv);
        translation.setText(lists.get(i).getTranslation());

        return layout;
    }


}
