package com.kest.ediscover.my.bindbank;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.kest.ediscover.R;

/**
 * 解绑银行卡
 * Created by Administrator on 2018/4/4 0004.
 */
public class UnbindBankActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_bank_unbind);
    }
}
