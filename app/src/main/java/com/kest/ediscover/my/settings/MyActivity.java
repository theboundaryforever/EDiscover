package com.kest.ediscover.my.settings;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.kest.ediscover.R;

/**
 * Created by Administrator on 2018/4/3 0003.
 */

//我的
public class MyActivity extends AppCompatActivity {
    //设置
    ImageView tv_settings;
    //实名认证
    RelativeLayout rl_authen;
    ImageView iv_cancel;
    //账户明细
    RelativeLayout rl_count;
    //总资产
    RelativeLayout rl_total_assets;
    //线下订单记录
    RelativeLayout rl_line;
    //ED币
    RelativeLayout rl_ed;
    //钱包
    RelativeLayout rl_wallet;
    //银行卡
    RelativeLayout rl_card;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        initView();
        setListener();
    }
    private void initView(){
        tv_settings=findViewById(R.id.tv_settings);

    }
    private void setListener(){
        tv_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyActivity.this, SettingsActivity.class));
            }
        });
    }

}
