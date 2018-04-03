package com.kest.ediscover.my.settings;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.kest.ediscover.R;

/**
 * Created by Administrator on 2018/4/3 0003.
 */
//重置登录密码，忘记密码接收验证码

public class ResetLoginVertifyCodeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_resetlogin_fortget_pwd);
    }
}
