package com.kest.ediscover.my.settings;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.kest.ediscover.R;

/**
 * 换绑手机
 * Created by Administrator on 2018/4/3 0003.
 */


public class ChangeTelActivity extends AppCompatActivity {
    ImageView iv_back;
    //验证码
    EditText et_code;
    //手机号
    EditText et_tel;
    LinearLayout ll_default;
    LinearLayout ll_ok;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_change_tel);
        initView();
        setListener();
    }
    private void initView(){
        iv_back=findViewById(R.id.iv_back);

    }
    private void setListener(){
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
