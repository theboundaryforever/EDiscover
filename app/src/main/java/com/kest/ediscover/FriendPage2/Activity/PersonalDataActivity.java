package com.kest.ediscover.FriendPage2.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.kest.ediscover.BaseActivitys;
import com.kest.ediscover.R;

/**
 * Created by Administrator on 2018\4\4 0004.
 * 聊天个人资料页面
 */

public class PersonalDataActivity extends BaseActivitys{

    private TextView tv_title;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personaldata_layout);
        init();
    }

    /**初始化*/
    private void init(){

        this.findViewById(R.id.img_title1_left).setVisibility(View.VISIBLE);
        this.findViewById(R.id.img_title1_left).setOnClickListener(this);

        tv_title = (TextView)this.findViewById(R.id.txt_title1_title);

        assigment();
    }

    /**赋值*/
    private void assigment(){

        tv_title.setText("详细资料");

    }


    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch(view.getId()){
            case R.id.img_title1_left:  //返回
                finish();
                break;
        }
    }
}
