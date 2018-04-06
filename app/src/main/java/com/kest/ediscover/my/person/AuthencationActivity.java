package com.kest.ediscover.my.person;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kest.ediscover.R;

/**
 * Created by Administrator on 2018/4/3 0003.
 */
/*实名认证*/
public class AuthencationActivity extends AppCompatActivity {
    ImageView iv_back;
    //名字
    EditText et_real_name;
    //身份证号码
    EditText et_card;
    //手机号
    EditText et_tel;
    //发送验证码按钮
    TextView tv_send_code;
    //接收到的验证码
    EditText et_code;
    //身份证正面
    ImageView iv_card_positive;
    //身份证背面
    ImageView iv_card_negative;
    //确定按钮
    LinearLayout ll_ok;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_person_authen);
        initView();
        setListener();
    }
    private void initView(){
        iv_back=findViewById(R.id.iv_back);
        et_real_name=findViewById(R.id.et_real_name);
        et_card=findViewById(R.id.et_card);
        et_tel=findViewById(R.id.et_tel);
        tv_send_code=findViewById(R.id.tv_send_code);
        et_code=findViewById(R.id.et_code);
        iv_card_positive=findViewById(R.id.iv_card_positive);
        iv_card_negative=findViewById(R.id.iv_card_negative);
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
