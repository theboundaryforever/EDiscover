package com.kest.ediscover.ChatPage.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMGroup;
import com.kest.ediscover.BaseActivitys;
import com.kest.ediscover.MyApplication;
import com.kest.ediscover.R;

/**
 * Created by Administrator on 2018\4\2 0002.
 * 群详情页面
 */

public class GroupDetailsActivity extends BaseActivitys{

    private TextView tv_title;
    private String groupId = "";

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

        tv_title = (TextView)this.findViewById(R.id.txt_title1_title);

        assigment();
    }

    /**赋值*/
    private void assigment(){


        groupId = getIntent().getStringExtra("groupId");

        try{
            EMGroup group = EMClient.getInstance().groupManager().getGroup(groupId);
            Log.d("查看群组信息","总人数:"+group.getMemberCount());
            tv_title.setText("群组信息 ("+group.getMemberCount()+")");

        }catch(Exception e){
            e.printStackTrace();
            MyApplication.setToast("群组信息有异常");
        }

    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.img_title1_left:  //返回
                finish();
                break;
        }
    }
}
