package com.kest.ediscover.account;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Toast;

import com.kest.ediscover.R;
import com.kest.ediscover.utils.DebugLog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddAccountActivity extends AppCompatActivity {

    @BindView(R.id.username)
    EditText userNameTxt;

    @BindView(R.id.password)
    EditText passwordTxt;

    //@BindView(R.id.trade_password)
   // EditText tradePasswordTxt;

    private String platformId;
    private String userName;
    private String password;
    private String tradePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_account);
        ButterKnife.bind(this);
        platformId = getIntent().getStringExtra("platformId");
    }

    @OnClick(R.id.back)
    void back() {
        finish();
    }

    @OnClick(R.id.ok_btn)
    void addAccount() {
        final AccountBiz accountBiz = new AccountBiz(AddAccountActivity.this);
        userName = userNameTxt.getText().toString();
        password = passwordTxt.getText().toString();
        //tradePassword = tradePasswordTxt.getText().toString();
        if (userName == "" || password == "") {
            Toast.makeText(this, "账户名和密码为必填", Toast.LENGTH_SHORT).show();
            return;
        }
        new Thread() {
            public void run() {
                Message message = Message.obtain();
                DebugLog.i("aaaaaaaaaaaaaa=====" + platformId);

                if (accountBiz.bindAccount(platformId, userName, password)) {
                    message.what = 1;
                } else message.what = 0;

                handler.sendMessage(message);
            }

            ;
        }.start();
    }

    private Handler handler = new Handler() {
        public void dispatchMessage(android.os.Message msg) {
            switch (msg.what) {
                case 1:
                    Toast.makeText(AddAccountActivity.this, "绑定成功", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AddAccountActivity.this, AccountListActivity.class);
                    startActivity(intent);
                    break;
                case 0:
                    Toast.makeText(AddAccountActivity.this, "绑定失败", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }

        }

        ;
    };
}
