package com.kest.ediscover.my.settings;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kest.ediscover.R;

/**
 * Created by Administrator on 2018/4/3 0003.
 */
/*设置密码的下一步*/

public class ResetPWD2Activity extends AppCompatActivity {
    ImageView iv_back;
    //人名
    TextView tv_card_num;
    //身份证号码
    EditText et_card_num;
    LinearLayout ll_next;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_pay_pwd2);
        iv_back=findViewById(R.id.iv_back);
        ll_next=findViewById(R.id.ll_next);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
