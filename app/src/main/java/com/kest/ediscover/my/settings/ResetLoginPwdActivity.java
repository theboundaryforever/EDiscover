package com.kest.ediscover.my.settings;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kest.ediscover.R;

/**
 * Created by Administrator on 2018/4/3 0003.
 */
/*重置登录密码*/
public class ResetLoginPwdActivity  extends AppCompatActivity{
    TextView tv_title;
    ImageView iv_back;
    //旧密码
    EditText et_old_pwd;
    ImageView iv_old_see;
    //新密码
    EditText et_new_pwd;
    ImageView iv_new_see;
    EditText et_again_pwd;
    ImageView iv_again_see;
    //忘记旧密码
    TextView tv_forget_pwd;
    //保存密码
    RelativeLayout rl_save;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_resetlogin_pwd);
        tv_title=findViewById(R.id.tv_title);
        iv_back=findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tv_title.setText("重置登录密码");
    }
}
