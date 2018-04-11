package com.kest.ediscover.my.person;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.kest.ediscover.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 我的二维码
 * Created by Administrator on 2018/4/4 0004.
 */
public class MyQrCodeActivity extends AppCompatActivity {
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_person_qrcode);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.iv_back})
    void onClick(View view){
        switch (view.getId()){
            case R.id.iv_back:
                finish();
                break;
        }
    }
}
