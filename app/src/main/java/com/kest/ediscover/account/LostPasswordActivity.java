package com.kest.ediscover.account;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;
import android.widget.Toast;

import com.kest.ediscover.BaseActivity;
import com.kest.ediscover.Bean.Response;
import com.kest.ediscover.MyApplication;
import com.kest.ediscover.R;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.widget.Toast.makeText;
/**
 * TODO 用户忘记密码页面
 */
public class LostPasswordActivity extends BaseActivity {

    Activity context;
    private int flag = 60;
    private Timer timer;

    Response response;
    @BindView(R.id.getCodebtn)
    TextView getCodeTextView;
    @BindView(R.id.phonetxt)
    TextView phoneNumber;

    @BindView(R.id.smsCode)
    TextView smsCode;
    String code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lost_password);
        context = this;
        ButterKnife.bind(this);
    }

    @OnClick(R.id.back)
    void back() {
        finish();
    }

    @OnClick(R.id.getCodebtn)
    void getSmsCode() {
        if (phoneNumber.getText().toString().length()==0) {
            MyApplication.setToast("请输入手机号");
            return;
        }
        new Thread() {
            public void run() {
                Message message = Message.obtain();
                AccountBiz accountBiz = new AccountBiz(context);
                message.what = 1;
                response = accountBiz.getSmsCode(phoneNumber.getText().toString(), "forget");
                 handler.sendMessage(message);
            }
        }.start();
        getCodeTextView.setText("" + "60秒");
        getCodeTextView.setClickable(false);
        flag = 60;
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                flag--;
                timerHandler.sendEmptyMessage(flag);
            }
        }, new Date(), 1000);
    }

    @OnClick(R.id.ok_btn)
    void ok() {
        if (smsCode.getText().toString().length() != 4){
            MyApplication.setToast("验证码有误，请重新填写");
            return;
        }
        Intent intent = new Intent(context, LostPassword2Activity.class);
        intent.putExtra("mobile", phoneNumber.getText().toString());
        intent.putExtra("code", smsCode.getText().toString());
        startActivity(intent);
    }

    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {

                case 1:
                    Toast.makeText(LostPasswordActivity.this, response.getErrorMessage(),
                            Toast.LENGTH_SHORT).show();

                    break;


                default:
                    break;
            }
        };
    };
    private Handler timerHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {

            if (msg.what == -1) {
                getCodeTextView.setText("获取验证码");
                getCodeTextView.setClickable(true);
                if (null != timer) {
                    timer.cancel();
                    timer = null;
                }
            } else {
                getCodeTextView.setText("" + msg.what + "秒后获取");
            }
        };
    };
}
