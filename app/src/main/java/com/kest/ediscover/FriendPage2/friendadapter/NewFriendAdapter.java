package com.kest.ediscover.FriendPage2.friendadapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyphenate.chat.EMClient;
import com.kest.ediscover.CustomControl.Dialog1;
import com.kest.ediscover.FriendPage2.friendclass.UserClass;
import com.kest.ediscover.MyApplication;
import com.kest.ediscover.R;

import java.util.List;

/**
 * Created by Administrator on 2018\3\30 0030.
 * 新朋友adapter
 */

public class NewFriendAdapter extends BaseAdapter implements Dialog1.DialogOnItemClickListener{

    private List<UserClass> Slist;
    private Activity context;
    private Dialog1 dialog1;
    private int sign;

    public NewFriendAdapter(List<UserClass> slist, Activity context) {
        this.Slist = slist;
        this.context = context;
        dialog1 = new Dialog1(context);
        dialog1.setOnItemClickListener(this);
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
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        sign = i;
        if(view==null){
            view = View.inflate(context, R.layout.imlistview_layout_item,null);
            viewHolder = new ViewHolder();
            viewHolder.im_avatar = view.findViewById(R.id.img_avatar);
            viewHolder.tv_name = view.findViewById(R.id.tv_name);
            viewHolder.tv_state = view.findViewById(R.id.tv_state);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)view.getTag();
        }
        viewHolder.tv_name.setText(Slist.get(i).getUsername());
        if(Slist.get(i).getIs_friend()==1){
            viewHolder.tv_state.setVisibility(View.GONE);
        }else{
            viewHolder.tv_state.setVisibility(View.VISIBLE);
            viewHolder.tv_state.setText("添加");
        }

        viewHolder.tv_state.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("点击事件",""+Slist.get(i).getUsername());
                dialog1.setEditText("我是 "+Slist.get(i).getUsername());
                dialog1.showAtLocation(context.findViewById(R.id.layout_top), Gravity.CENTER,0,0);
            }
        });

        return view;
    }

    @Override
    public void setOnItemClick(View v) {
        dialog1.dismiss();
       switch(v.getId()){
           case R.id.tv_cancel:  //取消
               dialog1.dismiss();
               break;
           case R.id.tv_confirm:  //确认
               InsertFriend();
               break;
       }
    }

    private class ViewHolder{

        ImageView im_avatar;
        TextView tv_name;
        TextView tv_state;

    }

    /**添加好友*/
    private void InsertFriend(){

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Log.d("添加好友方法",""+6666);
                    EMClient.getInstance().contactManager().addContact(Slist.get(sign).getHx_username(), dialog1.getEditText().toString());
                }catch (Exception e){e.printStackTrace(); Log.d("添加好友异常","NewFriendAdapter"); }
            }
        }).start();
    }

}
