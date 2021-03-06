package com.kest.ediscover.ChatPage.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMGroup;
import com.kest.ediscover.BaseActivitys;
import com.kest.ediscover.ChatPage.Adapter.GroupDetailsAdapter;
import com.kest.ediscover.CustomControl.Dialog3;
import com.kest.ediscover.CustomControl.Dialog4;
import com.kest.ediscover.FriendPage2.Activity.FriendListActivity;
import com.kest.ediscover.MyApplication;
import com.kest.ediscover.R;
import com.kest.ediscover.utils.SharePreferenceUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018\4\2 0002.
 * 群详情页面
 */

public class GroupDetailsActivity extends BaseActivitys implements Dialog3.DialogOnItemClickListener,Dialog4.DialogOnItemClickListener{

    private RecyclerView recyclerView;
    private GroupDetailsAdapter groupDetailsAdapter;
    private List<String> Slist = new ArrayList<>();

    private TextView tv_title,tv_groupname,tv_groupnotice,tv_groupnikname;
    private Switch seekbar_disturb;
    private RelativeLayout layout_disturb;
    private Button btn_delete;
    private String groupId = "",groupname="";
    private SharePreferenceUtil sp;
    private Dialog3 dialog3;
    private Dialog4 dialog4;

    private EMGroup group;
    private Boolean bBoolean,dialog4Boolean;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch(msg.what){
                case 1:  //页面赋值
                    assigment();
                    break;
                case 2:  //修改群名称
                    tv_groupname.setText(groupname);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.groupdetails_layout);
        init();
    }

    /**初始化*/
    private void init(){

        this.findViewById(R.id.img_title1_left).setOnClickListener(this);
        this.findViewById(R.id.img_title1_left).setVisibility(View.VISIBLE);
        this.findViewById(R.id.seekbar_disturb).setOnClickListener(this);
        this.findViewById(R.id.tv_members).setOnClickListener(this);
        this.findViewById(R.id.groupname_layout).setOnClickListener(this);
        this.findViewById(R.id.layout_groupannouncement).setOnClickListener(this);
        this.findViewById(R.id.layout_nikname).setOnClickListener(this);
        this.findViewById(R.id.layout_groupcomplaint).setOnClickListener(this);
        this.findViewById(R.id.layout_deleteconversation).setOnClickListener(this);
        this.findViewById(R.id.btn_delete).setOnClickListener(this);

        tv_title = (TextView)this.findViewById(R.id.txt_title1_title);
        recyclerView = (RecyclerView)this.findViewById(R.id.groupdetails_recyclerview);
        tv_groupname = (TextView)this.findViewById(R.id.tv_groupname);
        tv_groupnotice = (TextView)this.findViewById(R.id.tv_groupnotice);
        tv_groupnikname = (TextView)this.findViewById(R.id.tv_groupnikname);
        seekbar_disturb = (Switch)this.findViewById(R.id.seekbar_disturb);
        layout_disturb = (RelativeLayout)this.findViewById(R.id.layout_disturb);
        btn_delete = (Button)this.findViewById(R.id.btn_delete);

        sp = SharePreferenceUtil.getInstance(this);
        groupId = getIntent().getStringExtra("groupId");
        dialog3 = new Dialog3(this);
        dialog3.setOnItemClickListener(this);
        dialog4 = new Dialog4(this);
        dialog4.setOnItemClickListener(this);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    group = EMClient.getInstance().groupManager().getGroupFromServer(groupId);
                    Message mes = handler.obtainMessage();
                    mes.what = 1;
                    handler.sendMessage(mes);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }).start();

    }

    /**赋值*/
    private void assigment(){

        for (int i = 0; i < 7; i++) {
            Slist.add(""+i);
        }

        groupDetailsAdapter = new GroupDetailsAdapter(this,R.layout.groupdetails_item);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,5);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(groupDetailsAdapter);
        groupDetailsAdapter.setList(Slist);
        try{

            Log.d("查看群组信息","总人数:"+group.getMemberCount()+","+group.getOwner());
            tv_title.setText("群组信息 ("+group.getMemberCount()+")");
            tv_groupname.setText(group.getGroupName());  //群名称
            tv_groupnotice.setText(group.getAnnouncement());  //群公告
            tv_groupnikname.setText("本群昵称"); //自己在群昵称
            if(group.getOwner().equals(sp.getHX_username())){  //群主不可以屏蔽群消息
                layout_disturb.setVisibility(View.GONE);
                btn_delete.setText("解散该群");
            } else {
                seekbar_disturb.setChecked(group.isMsgBlocked());
                bBoolean = group.isMsgBlocked();  //是获取当前用户是否屏蔽该群消息
                btn_delete.setText("退出该群");
            }

        }catch(Exception e){
            e.printStackTrace();
            MyApplication.setToast("群组信息有异常");
        }

        groupDetailsAdapter.setOnItemClickListener(new GroupDetailsAdapter.MyOnItemClickListener() {
            @Override
            public void OnItemClick(View view, int postion) {
                Log.d("群组详情","点击事件");
                if(postion==(Slist.size()-2)){  //添加群成员按钮
                    Log.d("群组详情","添加成员");
                }else if(postion==(Slist.size()-1)){  //踢掉群成员按钮
                    Log.d("群组详情","踢掉成员");
                }
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.img_title1_left:  //返回
                finish();
                break;
            case R.id.seekbar_disturb:  //屏蔽群消息
                if(bBoolean){
                    BlockGroupMeg(false);
                }else{
                    BlockGroupMeg(true);
                }
                break;
            case R.id.tv_members:  //查看全部群成员
                Intent intent = new Intent(GroupDetailsActivity.this,GroupMembersActivity.class);
                intent.putExtra("groupId",groupId);
                startActivity(intent);
                break;
            case R.id.groupname_layout:  //修改群名称
                dialog3.setEditTextcontent(group.getGroupName());
                dialog3.showAtLocation(this.findViewById(R.id.layout_top), Gravity.CENTER,0,0);
                break;
            case R.id.layout_groupannouncement:  //群公告
                Intent intent1 = new Intent(GroupDetailsActivity.this,GroupAnnouncementActivity.class);
                intent1.putExtra("groupId",groupId);
                startActivity(intent1);
                break;
            case R.id.layout_nikname: //修改个人昵称
                dialog3.setEditTextcontent(tv_groupnikname.getText().toString());
                dialog3.setTexttitle("修改个人昵称");
                dialog3.setTextname("本群个人昵称");
                dialog3.showAtLocation(this.findViewById(R.id.layout_top), Gravity.CENTER,0,0);
                break;
            case R.id.layout_groupcomplaint:  //投诉
                Intent intent2 = new Intent(GroupDetailsActivity.this,ComplaintActivity.class);
                intent2.putExtra("groupId",groupId);
                startActivity(intent2);
                break;
            case R.id.layout_deleteconversation:  //清除聊天记录
                dialog4Boolean = false;
                getCoversation();
                dialog4.showAtLocation(this.findViewById(R.id.layout_top), Gravity.CENTER,0,0);
                break;
            case R.id.btn_delete:  //退出该群
                dialog4Boolean = true;
                getCoversation();
                dialog4.showAtLocation(this.findViewById(R.id.layout_top), Gravity.CENTER,0,0);
                break;
        }
    }

    /**屏蔽群消息*/
    private void BlockGroupMeg(final Boolean aBoolean){

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try{
                        if(aBoolean){
                            EMClient.getInstance().groupManager().blockGroupMessage(groupId);
                        }else{
                            EMClient.getInstance().groupManager().unblockGroupMessage(groupId);
                        }
                    }catch(Exception e){
                        e.printStackTrace();
                        Log.d("GroupDetailsActivity","屏蔽群消息出现异常");
                    }
                }
            }).start();
    }

    @Override
    public void setOnItemClick(View v) {
        dialog3.dismiss();
        switch(v.getId()){
            case R.id.tv_cancel:  //取消
                dialog3.dismiss();
                break;
            case R.id.tv_confirm:  //确认
                if(dialog3.getTexttitle().equals("修改个人昵称")){
                   tv_groupnikname.setText(dialog3.getEditTextcontent());
                }else {
                    groupname = dialog3.getEditTextcontent();
                    UpdateGroupname();
                }
                break;
        }
    }

    @Override
    public void setOnItemClick4(View v) {
        dialog4.dismiss();
        switch(v.getId()){
            case R.id.tv_bottom_txt2:  //2
                if(dialog4Boolean){  //退出或解散群组
                    if(!group.getOwner().equals(sp.getHX_username())) {
                        //退出群组

                    }else{
                        //解散群组
                        DeleteGroup();
                    }
                }else {  //清楚聊天记录

                }
                break;
            case R.id.tv_bottom_txt3:  //3
                dialog4.dismiss();
                break;
        }
    }

    /**判断清空聊天记录与退出群聊弹窗*/
    private void getCoversation(){
        if(dialog4Boolean){
            dialog4.setText1VisibilityVISIBLE();
            if(!group.getOwner().equals(sp.getHX_username())) {
                dialog4.setText1("退出后不会通知群聊中的其他成员，且不会在接收此群聊消息");
            }else{
                dialog4.setText1("确定解散该群");
            }
            dialog4.setText2("确定");
        }else {
            dialog4.setText1VisibilityGONE();
            dialog4.setText2("清空聊天记录");
        }
    }

    /**修改群名称方法*/
    private void UpdateGroupname(){

        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    EMClient.getInstance().groupManager().changeGroupName(groupId,groupname);//需异步处理
                    Message mes = handler.obtainMessage();
                    mes.what = 2;
                    handler.sendMessage(mes);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }).start();

    }


    /**解散群组方法*/
    private void DeleteGroup(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    EMClient.getInstance().groupManager().destroyGroup(groupId);//需异步处理
                    startActivity(new Intent(GroupDetailsActivity.this, FriendListActivity.class));
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

}
