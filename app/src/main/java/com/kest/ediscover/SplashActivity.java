package com.kest.ediscover;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.kest.ediscover.account.LoginActivity;
import com.kest.ediscover.HomePage.Activity.HomeActivity;
import com.kest.ediscover.utils.SharePreferenceUtil;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import main.MainHomeActivity;

/**
 * 引导页
 **/
public class SplashActivity extends BaseActivity {

    SharePreferenceUtil sp;
    TextView splashTimer;
    private int flag = 5;
    private Timer timer = null;
    private Intent intent = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        sp = SharePreferenceUtil.getInstance(this);
        splashTimer = (TextView) findViewById(R.id.splash_timer_txt);
         findViewById(R.id.splash_skip).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelTimer();
                if(sp.getLoginStatus()){
                    //intent = new Intent(SplashActivity.this, MainActivity.class);
                    intent = new Intent(SplashActivity.this, HomeActivity.class);
                  //intent = new Intent(SplashActivity.this, MainHomeActivity .class);
                }else {
                    intent = new Intent(SplashActivity.this, LoginActivity.class);
                }
                startActivity(intent);
                finish();
            }
        });
        flag = 5;
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                flag--;
                timerHandler.sendEmptyMessage(flag);
            }
        }, new Date(), 1000);
    }

    private Handler timerHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            if (msg.what == -1) {
                if(sp.getLoginStatus()){
                   intent = new Intent(SplashActivity.this, HomeActivity.class);
                  //  intent = new Intent(SplashActivity.this, MainHomeActivity.class);
                }else {

                    intent = new Intent(SplashActivity.this, LoginActivity.class);
                }
                startActivity(intent);
                finish();
                cancelTimer();
            } else {
                splashTimer.setText("" + msg.what);
            }
        }

        ;
    };

    private void cancelTimer(){
        if (null != timer) {
            timer.cancel();
            timer = null;
        }
    }
}
