package com.kest.ediscover.account;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.kest.ediscover.BaseActivity;
import com.kest.ediscover.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LostPassword2Activity extends BaseActivity {

    Activity context;
    String mobile;
    String code;

    @BindView(R.id.passwordTxt)
    EditText password;

    @BindView(R.id.againPasswordTxt)
    EditText againPassword;
    @BindView(R.id.seepassword_btn)
    TextView seePasswordBtn;

    @BindView(R.id.seepassword_btn2)
    TextView seePasswordBtn2;

    boolean canSeePassword = false;

    @OnClick(R.id.see_password)
    void seePassword(){
        if (canSeePassword){
            seePasswordBtn.setBackgroundResource(R.mipmap.eye);
            password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }else{
            seePasswordBtn.setBackgroundResource(R.mipmap.eye_selected);
            password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        }
        canSeePassword = !canSeePassword;
    }

    boolean canSeePassword2 = false;

    @OnClick(R.id.see_password_again)
    void seePassword2(){
        if (canSeePassword2){
            seePasswordBtn2.setBackgroundResource(R.mipmap.eye);
            againPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }else{
            seePasswordBtn2.setBackgroundResource(R.mipmap.eye_selected);
            againPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        }
        canSeePassword2 = !canSeePassword2;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lost_password2);
        ButterKnife.bind(this);
        context = this;
        Intent intent = getIntent();
        mobile = intent.getStringExtra("mobile");
        code = intent.getStringExtra("code");
    }
    @OnClick(R.id.back)
    void back(){
        finish();
    }

    @OnClick(R.id.ok_btn)
    void ok(){
        new Thread() {
            public void run() {
                Message message = Message.obtain();
                AccountBiz accountBiz = new AccountBiz(context);

                if(accountBiz.findPassword(mobile, code, password.getText().toString(),"001")){
                    message.what = 1;
                }else  message.what = 0;
                 handler.sendMessage(message);
            }
        }.start();
        startActivity(new Intent(context, LoginActivity.class));
        finish();
    }

    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 1:
                    Toast.makeText(LostPassword2Activity.this, "重设密码成功",
                            Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(context, LoginActivity.class));
                    context.finish();
                    break;

                case 0:
                    Toast.makeText(LostPassword2Activity.this, "重设密码失败",
                            Toast.LENGTH_SHORT).show();
                    //(new ShowDialogHelper(LoginActivity.this)).showDialog("提示", "当前网络不可用");
                    break;
                default:
                    break;
            }
        };
    };
}
