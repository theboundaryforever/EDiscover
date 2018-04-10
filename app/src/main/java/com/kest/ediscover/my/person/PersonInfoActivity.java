package com.kest.ediscover.my.person;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;

import com.kest.ediscover.R;

/**
 * Created by Administrator on 2018/4/3 0003.
 */
/*个人信息*/
public class PersonInfoActivity extends AppCompatActivity{
    //头像
    RelativeLayout rl_person_img;
    //生日
    RelativeLayout rl_birth;
    //名字
    RelativeLayout rl_name;
    //身份认证
    RelativeLayout rl_vertify;
    //我的二维码
    RelativeLayout rl_person_qr;
    //性别
    RelativeLayout rl_sex;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_person);

        initView();
        setListener();

    }
    private void initView(){
        rl_person_img=findViewById(R.id.rl_person_img);
        rl_person_qr=findViewById(R.id.rl_person_qr);
        rl_name=findViewById(R.id.rl_name);
        rl_sex=findViewById(R.id.rl_sex);
        rl_birth=findViewById(R.id.rl_birth);
        rl_vertify=findViewById(R.id.rl_vertify);
    }
    private void setListener(){
        rl_person_qr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PersonInfoActivity.this,MyQrCodeActivity.class));
            }
        });
        rl_vertify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PersonInfoActivity.this,AuthencationActivity.class));
            }
        });
        rl_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PersonInfoActivity.this,PersonInfoNameActivity.class));
            }
        });
    }
}
