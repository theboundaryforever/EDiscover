package com.hyphenate.easeui.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.hyphenate.easeui.R;

/**
 * Created by Administrator on 2018\4\11 0011.
 * 红包详情界面（打开红包方）
 */

public class RedEnvelopesDetailsActivity extends Activity implements View.OnClickListener{


    private TextView tv_back,tv_title,tv_right;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.redenvelopesdetails_layout);
        init();
    }

    /**初始化*/
    private void init(){

        this.findViewById(R.id.tv_back).setOnClickListener(this);
        this.findViewById(R.id.tv_title).setOnClickListener(this);
        this.findViewById(R.id.tv_right).setOnClickListener(this);

        tv_back = (TextView)this.findViewById(R.id.tv_back);
        tv_title = (TextView)this.findViewById(R.id.tv_title);
        tv_right = (TextView)this.findViewById(R.id.tv_right);

        assigment();
    }

    /**赋值*/
    private void assigment(){

        tv_back.setText("返回");
        tv_title.setText("E信红包");
        tv_right.setText("红包记录");

    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.tv_back){  //返回
            finish();
        }else if(view.getId()==R.id.tv_right){   //红包记录
            Intent intent = new Intent(this,RedEnvelopesRecordActivity.class);
            startActivity(intent);
        }
    }
}
