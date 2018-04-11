package com.kest.ediscover.my;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kest.ediscover.BaseActivity;
import com.kest.ediscover.R;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * 收款码页面
 * Created by Administrator on 2018/4/11.
 */

public class ReceivablesActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView imageView;

    @BindView(R.id.ll_pay_type)
    LinearLayout ll_pay_type;

    @BindView(R.id.tv_setting_money)
    TextView tv_setting_money;

    @BindView(R.id.tv_save_pic)
    TextView tv_save_pic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receivables);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tv_setting_money.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ReceivablesActivity.this, SettingMoneyActivity.class));
            }
        });
        ll_pay_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ReceivablesActivity.this, SmallBillActivity.class));
            }
        });


    }
}
