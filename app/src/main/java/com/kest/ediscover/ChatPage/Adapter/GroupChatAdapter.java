package com.kest.ediscover.ChatPage.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyphenate.chat.EMGroup;
import com.kest.ediscover.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018\3\31 0031.
 * 群聊适配器
 */

public class GroupChatAdapter extends BaseAdapter{

    private Context context;
    private int layout;
    private List<EMGroup> Slist = new ArrayList<>();

    public GroupChatAdapter(Context context, int layout, List<EMGroup> slist) {
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
        ViewHolder viewHolder;
        if(view==null){
            view = View.inflate(context,layout,null);
            viewHolder = new ViewHolder();
            viewHolder.img_avatar = view.findViewById(R.id.img_avatar);
            viewHolder.tv_groupchatname = view.findViewById(R.id.tv_groupchatname);
            viewHolder.tv_content = view.findViewById(R.id.tv_content);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)view.getTag();
        }
        viewHolder.tv_groupchatname.setText(Slist.get(i).getGroupName());
        viewHolder.tv_content.setText(Slist.get(i).getDescription());
        return view;
    }

    private class ViewHolder{
        ImageView img_avatar;
        TextView tv_groupchatname;
        TextView tv_content;
    }

}
