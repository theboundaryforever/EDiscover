package com.kest.ediscover.FriendPage2.friendadapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyphenate.easeui.domain.UserClass2;
import com.kest.ediscover.R;

import java.util.List;

/**
 * Created by Administrator on 2018\3\31 0031.
 */

public class SelectCintactAdapter extends RecyclerView.Adapter{

    private Context context;
    private LayoutInflater mLayoutInflater;
    private List<UserClass2> Ilist;

    public SelectCintactAdapter(Context context, LayoutInflater mLayoutInflater, List<UserClass2> ilist) {
        this.context = context;
        this.mLayoutInflater = mLayoutInflater;
        Ilist = ilist;
    }

    public void setList(List<UserClass2> list){
        Ilist.removeAll(Ilist);
        for (int i = 0; i < list.size(); i++) {
            if(list.get(i).getSign().equals("1")){
                Ilist.add(list.get(i));
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.icon_item, parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.img_icon = (ImageView)view.findViewById(R.id.img_avatar);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return Ilist.size();
    }

    private class ViewHolder extends RecyclerView.ViewHolder{
        ImageView img_icon;
        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setTag(this);
        }
    }

}
