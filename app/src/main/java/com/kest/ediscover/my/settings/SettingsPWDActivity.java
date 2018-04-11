package com.kest.ediscover.my.settings;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kest.ediscover.R;

/**
 * 设置密码主界面
 * Created by Administrator on 2018/4/3 0003.
 */
public class SettingsPWDActivity extends AppCompatActivity {
    ImageView iv_back;
    //支付密码
    RelativeLayout rl_pay_pwd;
    //登录密码
    RelativeLayout rl_login_pwd;
    TextView tv_title;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_reset);
        rl_pay_pwd=findViewById(R.id.rl_pay_pwd);
        rl_login_pwd=findViewById(R.id.rl_login_pwd);
        iv_back=findViewById(R.id.iv_back);
        tv_title=findViewById(R.id.tv_title);
        tv_title.setText("设置");
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        rl_login_pwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingsPWDActivity.this,ResetLoginPwdActivity.class));

            }
        });
        rl_pay_pwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(SettingsPWDActivity.this,ResetPWDActivity.class));
            }
        });


    }
}
