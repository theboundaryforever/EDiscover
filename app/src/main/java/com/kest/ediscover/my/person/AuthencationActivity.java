package com.kest.ediscover.my.person;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kest.ediscover.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/4/3 0003.
 */
/*实名认证*/
public class AuthencationActivity extends AppCompatActivity {
    @BindView(R.id.iv_back)
    ImageView iv_back;
    //名字
    @BindView(R.id.et_real_name)
    EditText et_real_name;
    //身份证号码
    @BindView(R.id.et_card)
    EditText et_card;
    //手机号
    @BindView(R.id.et_tel)
    EditText et_tel;
    //发送验证码按钮
    @BindView(R.id.tv_send_code)
    TextView tv_send_code;
    //接收到的验证码
    @BindView(R.id.et_code)
    EditText et_code;
    //身份证正面
    @BindView(R.id.iv_card_positive)
    ImageView iv_card_positive;
    //身份证背面
    @BindView(R.id.iv_card_negative)
    ImageView iv_card_negative;
    //确定按钮
    @BindView(R.id.ll_ok)
    RelativeLayout ll_ok;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_person_authen);

    }


    @OnClick({R.id.iv_back,R.id.tv_send_code,R.id.iv_card_positive,R.id.iv_card_negative,R.id.ll_ok})
    void onclick(View view){
        switch (view.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_send_code:

                break;
            case R.id.iv_card_positive:
                break;
            case R.id.iv_card_negative:
                break;
            case R.id.ll_ok:
                break;
        }
    }
}
