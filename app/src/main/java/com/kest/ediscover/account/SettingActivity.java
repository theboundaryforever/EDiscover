package com.kest.ediscover.account;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.kest.ediscover.BaseActivity;
import com.kest.ediscover.R;
import com.kest.ediscover.utils.ActivityCollector;
import com.kest.ediscover.utils.CleanMessageUtil;
import com.kest.ediscover.utils.SharePreferenceUtil;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingActivity extends BaseActivity {

    Activity context;
    SharePreferenceUtil sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        context = this;
        ButterKnife.bind(this);
        sp = SharePreferenceUtil.getInstance(this);

    }

    @OnClick(R.id.back)
    void back(){
        finish();
    }

    @OnClick(R.id.logout_btn)
    void logout(){
        sp.setLoginStaus(false);
        ActivityCollector.finishAll();
        startActivity(new Intent(context, LoginActivity.class));
    }

    @OnClick(R.id.accountlist)
    void showAccountList(){
        startActivity(new Intent(context, AccountListActivity.class));
    }

    @OnClick(R.id.clearcache)
    void clear(){
        CleanMessageUtil.clearAllCache(getApplicationContext());
        Toast.makeText(SettingActivity.this, "清空缓存成功",Toast.LENGTH_SHORT).show();
    }
}
