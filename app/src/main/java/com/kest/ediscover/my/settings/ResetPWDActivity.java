package com.kest.ediscover.my.settings;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kest.ediscover.R;

/**
 * Created by Administrator on 2018/4/2 0002.
 */
/*修改支付密码*/
public class ResetPWDActivity extends AppCompatActivity {
    ImageView iv_back;
    TextView tv_remember;
    TextView tv_forget;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_pay_pwd);
        iv_back=findViewById(R.id.iv_back);
        tv_remember=findViewById(R.id.tv_remember);
        tv_forget=findViewById(R.id.tv_forget);
        tv_forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        tv_remember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
