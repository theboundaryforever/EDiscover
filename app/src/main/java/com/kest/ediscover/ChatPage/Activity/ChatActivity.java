package com.kest.ediscover.ChatPage.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.hyphenate.easeui.widget.EaseTitleBar;
import com.kest.ediscover.MyApplication;
import com.kest.ediscover.R;
import com.kest.ediscover.utils.SharePreferenceUtil;

/**
 * Created by Administrator on 2018\3\26 0026.
 * 会话页面
 */

public class ChatActivity extends FragmentActivity implements View.OnClickListener{

    private EaseChatFragment chatFragment;
    private EaseTitleBar easetitlebar;
    private String chattype="",chatid="";

    private TextView tv_title;
    private ImageView im_right;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chatlayout);

        chatFragment = new EaseChatFragment();
        //传入参数
        chatFragment.setArguments(getIntent().getExtras());
        getSupportFragmentManager().beginTransaction().add(R.id.chatlist_frament, chatFragment).commit();
        init();
    }

    /**初始化*/
    private void init(){


        this.findViewById(R.id.img_title1_left).setVisibility(View.VISIBLE);
        this.findViewById(R.id.img_title1_left).setOnClickListener(this);
        this.findViewById(R.id.img_title1_right).setOnClickListener(this);

        chattype = getIntent().getIntExtra("chatType",0)+"";
        chatid = getIntent().getStringExtra("userId");
        tv_title = this.findViewById(R.id.txt_title1_title);
        im_right = this.findViewById(R.id.img_title1_right);



        assigment();
    }

    /**赋值*/
    private void assigment(){

         try {
             if(chattype.equals("2")){
                 Log.d("聊天界面","群组聊天");
                 EMGroup group = EMClient.getInstance().groupManager().getGroup(chatid);
                 tv_title.setText(group.getGroupName());
                 im_right.setImageResource(R.drawable.ease_to_group_details_normal);

             }else if(chattype.equals("1")){
                 Log.d("聊天界面","单人聊天");
                 im_right.setImageResource(R.mipmap.person);

             }

         }catch(Exception e){
             e.printStackTrace();
             MyApplication.setToast("聊天界面查寻群信息出现异常");
         }
    }


    @Override
    public void onStateNotSaved() {
        super.onStateNotSaved();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.d("群详情","666");
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.img_title1_left:  //返回
                finish();
                break;
            case R.id.img_title1_right:  //查看群详情
                Intent intent = new Intent(ChatActivity.this,GroupDetailsActivity.class);
                intent.putExtra("groupId",chatid);
                startActivity(intent);
                break;

        }
    }
}
