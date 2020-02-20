package com.dapeng.notes.Todo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dapeng.notes.R;

import java.util.List;

/**
 * Created by Dapeng on 2018/6/9.
 */

public class TodoAdapter extends BaseAdapter{
    private List<TodoInfo> lists;
    private Context context;
    private LinearLayout layout;
    private TextView thing,time;

    public TodoAdapter(List<TodoInfo> lists, Context context) {
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
        layout = (LinearLayout) inflater.inflate(R.layout.cell_todo,null);
        thing = layout.findViewById(R.id.todo_listview_tv);
        time = layout.findViewById(R.id.finishTime_lv);
        thing.setText(lists.get(i).getThing());
        time.setText(lists.get(i).getFinishTime());
        return layout;
    }
}
