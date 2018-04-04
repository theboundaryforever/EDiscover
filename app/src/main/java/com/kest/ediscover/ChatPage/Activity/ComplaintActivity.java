package com.kest.ediscover.ChatPage.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.kest.ediscover.BaseActivitys;
import com.kest.ediscover.R;

/**
 * Created by Administrator on 2018\4\3 0003.
 * 投诉页面
 */

public class ComplaintActivity extends BaseActivitys{

    private TextView tv_title;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.complaint_layout);
        init();
    }

    /**初始化*/
    private void init(){

        this.findViewById(R.id.tv_Pornographic).setOnClickListener(this);
        this.findViewById(R.id.tv_garbage).setOnClickListener(this);
        this.findViewById(R.id.tv_Illegal).setOnClickListener(this);
        this.findViewById(R.id.img_title1_left).setOnClickListener(this);
        this.findViewById(R.id.img_title1_left).setVisibility(View.VISIBLE);

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
        Intent intent = new Intent(ComplaintActivity.this,ComplaintDetailsActivity.class);
        switch(view.getId()){
            case R.id.tv_Pornographic:  //发布色情信息
                intent.putExtra("state","1");
                startActivity(intent);
                break;
            case R.id.tv_garbage:  //垃圾短信
                intent.putExtra("state","2");
                startActivity(intent);
                break;
            case R.id.tv_Illegal:  //发布违法信息
                intent.putExtra("state","3");
                startActivity(intent);
                break;
            case R.id.img_title1_left:  //返回
                finish();
                break;

        }
    }
}
