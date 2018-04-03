package com.kest.ediscover.my.settings;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.kest.ediscover.R;

/**
 * Created by Administrator on 2018/4/3 0003.
 */
/*设置*/
public class SettingsActivity  extends AppCompatActivity {
    ImageView iv_back;
    //设置手机号
    RelativeLayout rl_tel;
    //设置密码
    RelativeLayout rl_pwd;
    //设置通知
     RelativeLayout rl_notice;
     //举报
    RelativeLayout rl_report;
    //联系客服
    RelativeLayout rl_contract;
    //退出登录
    LinearLayout ll_exit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        initView();
        setListener();
    }
    private void initView(){
        rl_tel=findViewById(R.id.rl_tel);
        rl_pwd=findViewById(R.id.rl_pwd);
        rl_notice=findViewById(R.id.rl_notice);
        rl_report=findViewById(R.id.rl_report);
        rl_contract=findViewById(R.id.rl_contract);
        iv_back=findViewById(R.id.iv_back);

    }
    private void setListener(){
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        rl_tel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingsActivity.this,ChangeTelActivity.class));
            }
        });
        rl_contract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingsActivity.this,ContractCustomerActivity.class));
            }
        });
        rl_pwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingsActivity.this,SettingsPWDActivity.class));
            }
        });
        rl_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingsActivity.this,ReportActivity.class));
            }
        });
    }

}
