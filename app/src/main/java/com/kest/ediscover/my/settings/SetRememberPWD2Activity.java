package com.kest.ediscover.my.settings;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.kest.ediscover.R;

/**
 * 记得密码二
 * Created by Administrator on 2018/4/6 0006.
 */
public class SetRememberPWD2Activity extends AppCompatActivity {
    ImageView iv_back;
    TextView tv_title;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_resetpay_pwd);
        iv_back=findViewById(R.id.iv_back);
        tv_title=findViewById(R.id.tv_title);
        tv_title.setText("设置支付密码");
    }
}
