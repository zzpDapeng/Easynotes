package com.dapeng.notes.Study;

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
 * Created by Dapeng on 2018/6/17.
 */

public class StudyAdapter extends BaseAdapter {
    private static List<StudyInfo> lists;
    private Context context;
    private LinearLayout layout;
    public TextView title,content;

    public StudyAdapter(List<StudyInfo> lists, Context context) {
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
        layout = (LinearLayout) inflater.inflate(R.layout.cell_study,null);
        title = layout.findViewById(R.id.title);
        title.setText(lists.get(i).getTitle());
        content = layout.findViewById(R.id.Content);
        content.setText(lists.get(i).getContent());
        return layout;
    }


}
