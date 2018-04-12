package com.kest.ediscover.my;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.kest.ediscover.BaseActivity;
import com.kest.ediscover.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 付款码页面
 * Created by Administrator on 2018/4/11.
 */

public class PaymentActivity extends BaseActivity {
    @BindView(R.id.iv_back)
    ImageView imageView;

    @BindView(R.id.ll_pay_type)
    LinearLayout linearLayout_pay_type;


    private PayTypeSelectDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
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

        linearLayout_pay_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog = new PayTypeSelectDialog();
                dialog.show(getSupportFragmentManager(), "");

            }
        });


    }
}
