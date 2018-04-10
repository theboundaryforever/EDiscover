package com.kest.ediscover.my;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.kest.ediscover.R;
import com.kest.ediscover.my.bindbank.AddBank1Activity;
import com.kest.ediscover.my.settings.SettingsActivity;

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
    //个人信息
    RelativeLayout rl_person_info;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        initView();
        setListener();
    }
    private void initView(){
        tv_settings=findViewById(R.id.tv_settings);
        iv_cancel=findViewById(R.id.iv_cancel);
        rl_card=findViewById(R.id.rl_card);
        rl_authen=findViewById(R.id.rl_authen);
        rl_person_info=findViewById(R.id.rl_person_info);
        rl_count=findViewById(R.id.rl_count);
        rl_total_assets=findViewById(R.id.rl_total_assets);
        rl_line=findViewById(R.id.rl_line);
        rl_ed=findViewById(R.id.rl_ed);
        rl_wallet=findViewById(R.id.rl_wallet);

    }
    private void setListener(){
        tv_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyActivity.this, SettingsActivity.class));
            }
        });
        rl_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyActivity.this, AddBank1Activity.class));
            }
        });
    }

}
