package com.kest.ediscover.ChatPage.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018\3\31 0031.
 * 群聊适配器
 */

public class GroupChatAdapter extends BaseAdapter{

    private Context context;
    private int layout;
    private List<String> Slist = new ArrayList<>();

    public GroupChatAdapter(Context context, int layout, List<String> slist) {
        this.context = context;
        this.layout = layout;
        Slist = slist;
    }

    @Override
    public int getCount() {
        return Slist.size();
    }

    @Override
    public Object getItem(int i) {
        return Slist.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view==null){
            view = View.inflate(context,layout,null);
        }
        return view;
    }
}
