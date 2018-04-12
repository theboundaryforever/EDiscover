package com.kest.ediscover.my.settings;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.kest.ediscover.R;

/**
 * 不记得支付密码密码
 * Created by Administrator on 2018/4/6 0006.
 */

public class SetForgetPWDActivity extends AppCompatActivity {
    TextView tv_title;
    ImageView iv_back;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_resetpay_forget_pwd);
        iv_back=findViewById(R.id.iv_back);
        tv_title=findViewById(R.id.tv_title);

    }
}
