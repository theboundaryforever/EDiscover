package com.kest.ediscover.my;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.kest.ediscover.BaseActivity;
import com.kest.ediscover.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 小账本页面
 * Created by Administrator on 2018/4/11.
 */


public class SmallBillActivity extends BaseActivity {
    @BindView(R.id.iv_back)
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_small_bill);
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
