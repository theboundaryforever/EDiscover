package com.kest.ediscover;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.kest.ediscover.account.AccountBiz;
import com.kest.ediscover.account.LoginActivity;
import com.kest.ediscover.utils.ActivityCollector;
import com.kest.ediscover.utils.SharePreferenceUtil;
import com.kest.ediscover.utils.ThreadPoolManager;

/**
 * Created by dk on 2017/12/20.
 */

public class BaseActivity extends AppCompatActivity {

    SharePreferenceUtil sp1;
    Activity context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollector.addActivity(this);
        final AccountBiz account = new AccountBiz(this);
        sp1 = SharePreferenceUtil.getInstance(this);
        context = this;
        ThreadPoolManager.getNormalPool().execute(new Runnable() {
            @Override
            public void run() {
                Message message = Message.obtain();
                String oldToken = sp1.getToken();
                if ("0".equals(oldToken)) return;
                String newToken = account.refreshToken(oldToken);
                sp1.setToken(newToken);

                if ("error".equals(newToken)){
                    message.what = 0;
                    handler.sendMessage(message);
                }
            }
        });

    }

    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 0:
                    Toast.makeText(BaseActivity.this,"登录已失效，请重新登录",Toast.LENGTH_SHORT).show();
                    sp1.setToken("0");
                    sp1.setLoginStaus(false);
                    startActivity(new Intent(BaseActivity.this, LoginActivity.class));
                    ActivityCollector.finishAll();
                    break;

                default:
                    break;
            }
        }

        ;
    };
    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }
}
