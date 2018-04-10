package com.kest.ediscover.ChatPage.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kest.ediscover.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018\4\3 0003.
 * 群成员展示列表的adapter
 */
public class GroupDetailsAdapter extends RecyclerView.Adapter{

    private Context context;
    private int layout;
    private List<String> Slist = new ArrayList<>();


    public GroupDetailsAdapter(Context context, int layout) {
        this.context = context;
        this.layout = layout;
    }

    public void setList(List<String> list){
        if(list.size()>0) {
            Slist.removeAll(Slist);
            Slist.addAll(list);
            notifyDataSetChanged();
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(layout,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.img_portrait = view.findViewById(R.id.img_portrait);
        viewHolder.tv_username = view.findViewById(R.id.tv_username);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder)holder;
        if(position==(Slist.size()-2)){
            viewHolder.tv_username.setText("添加");
            viewHolder.img_portrait.setImageResource(R.mipmap.add_platform);
        }else if(position==(Slist.size()-1)){
            viewHolder.tv_username.setText("减去");
            viewHolder.img_portrait.setImageResource(R.mipmap.phone);
        }else {
            viewHolder.tv_username.setText("名称");
            viewHolder.img_portrait.setImageResource(R.mipmap.loginlogo);
        }
        if(myOnItemClickListener != null){
            itemOnclick(holder);
        }
    }

    @Override
    public int getItemCount() {
        return Slist.size();
    }


    private class ViewHolder extends RecyclerView.ViewHolder {

        ImageView img_portrait;
        TextView tv_username;
        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setTag(this);
        }
    }

    public interface MyOnItemClickListener{
        void OnItemClick(View view, int postion);
    }

    private MyOnItemClickListener myOnItemClickListener;

    public void setOnItemClickListener(MyOnItemClickListener myOnItemClickListener){
        this.myOnItemClickListener = myOnItemClickListener;
    }

    private void itemOnclick(final RecyclerView.ViewHolder holder){
           holder.itemView.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   myOnItemClickListener.OnItemClick(view,holder.getLayoutPosition());
               }
           });
    }

}
