package com.kest.ediscover.my;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.kest.ediscover.BaseActivity;
import com.kest.ediscover.R;

import butterknife.BindView;
import butterknife.ButterKnife;



/**
 * 设置金额页面
 * Created by Administrator on 2018/4/11.
 */

public class SettingMoneyActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_money);
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

    }
}
