package com.kest.ediscover.my.settings;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.kest.ediscover.R;

/**
 * 举报
 * Created by Administrator on 2018/4/3 0003.
 */

public class ReportActivity  extends AppCompatActivity {

    ImageView iv_back;
    //手机号码
    EditText et_tel;
    //受骗账号
    EditText et_account;
    //举报对象
    EditText et_obj;
    //违规行为
    RelativeLayout rl_illegal;
    //举报描述
    RelativeLayout rl_describe;
    //举报截图
    LinearLayout ll_img;
    //提交
    LinearLayout ll_ok;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_report1);
        initView();
        setListener();
    }
    private void initView(){
        iv_back=findViewById(R.id.iv_back);
        et_tel=findViewById(R.id.et_tel);
        et_account=findViewById(R.id.et_account);
        et_obj=findViewById(R.id.et_obj);
        rl_illegal=findViewById(R.id.rl_illegal);
        rl_describe=findViewById(R.id.rl_describe);
        ll_img=findViewById(R.id.ll_img);
        ll_ok=findViewById(R.id.ll_ok);

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
