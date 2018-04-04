package com.kest.ediscover.ChatPage.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.kest.ediscover.BaseActivitys;
import com.kest.ediscover.R;

/**
 * Created by Administrator on 2018\4\4 0004.
 * 投诉详情页
 */

public class ComplaintDetailsActivity extends BaseActivitys{

    private TextView tv_title;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.complaintdetails_layout);
        init();
    }

    /**初始化*/
    private void init(){

        this.findViewById(R.id.img_title1_left).setOnClickListener(this);
        this.findViewById(R.id.img_title1_left).setVisibility(View.VISIBLE);
        this.findViewById(R.id.btn_comite).setOnClickListener(this);

        tv_title = (TextView)this.findViewById(R.id.txt_title1_title);

        assigment();
    }

    /**赋值*/
    private void assigment(){

        tv_title.setText("投诉");

    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch(view.getId()){
            case R.id.img_title1_left:  //返回
                finish();
                break;
            case R.id.btn_comite:  //提交
                finish();
                break;
        }
    }
}
